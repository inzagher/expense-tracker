package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.dto.backup.BackupXmlDataDTO;
import inzagher.expense.tracker.server.model.entity.BackupMetadataEntity;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import inzagher.expense.tracker.server.model.event.BackupCreatedEvent;
import inzagher.expense.tracker.server.model.event.BackupRestoredEvent;
import inzagher.expense.tracker.server.model.exception.ExpenseTrackerException;
import inzagher.expense.tracker.server.model.exception.NotFoundException;
import inzagher.expense.tracker.server.model.exception.ServiceBusyException;
import inzagher.expense.tracker.server.model.mapper.BackupMetadataMapper;
import inzagher.expense.tracker.server.model.mapper.CategoryMapper;
import inzagher.expense.tracker.server.model.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.model.mapper.PersonMapper;
import inzagher.expense.tracker.server.repository.BackupMetadataRepository;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackupService {
    @Value("${backup.directory}")
    private String backupDirectory;

    private static final int STATE_IDLE = 0;
    private static final int STATE_BUSY = 1;
    private static final String ZIP_ENTRY_NAME = "expenses.xml";

    private final AtomicInteger serviceState = new AtomicInteger();
    private final ApplicationEventPublisher eventPublisher;
    private final SerializationService serializationService;
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;
    private final BackupMetadataRepository backupMetadataRepository;
    private final BackupMetadataMapper backupMetadataMapper;
    private final ExpenseMapper expenseMapper;
    private final CategoryMapper categoryMapper;
    private final PersonMapper personMapper;

    @Transactional
    public Page<BackupMetadataDTO> findAllMetadataRecords(@NonNull Pageable pageable) {
        log.info("Find all backup metadata records");
        return backupMetadataRepository.findAll(pageable)
                .map(backupMetadataMapper::toDTO);
    }

    @Transactional
    public BackupMetadataDTO getMetadataRecordById(@NonNull Long id) {
        log.info("Find backup metadata record with id {}", id);
        return backupMetadataRepository.findById(id)
                .map(backupMetadataMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Optional<BackupMetadataDTO> findLastMetadataRecord() {
        log.info("Find last backup metadata record");
        var request = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "created"));
        var backups =  backupMetadataRepository.findAll(request).toList();
        return backups.stream().map(backupMetadataMapper::toDTO).findFirst();
    }

    @Transactional
    public FileInputStream downloadBackupFile(@NonNull Long id) {
        log.info("Download backup file with id {}", id);
        try {
            var metadata = backupMetadataRepository.findById(id)
                    .orElseThrow(NotFoundException::new);
            var path = Paths.get(backupDirectory, metadata.getFileName());
            return new FileInputStream(path.toString());
        } catch (FileNotFoundException e) {
            throw new ExpenseTrackerException("Backup file not found", "FILE_NOT_FOUND", e);
        }
    }

    @Transactional
    public BackupMetadataDTO createDatabaseBackup() {
        log.info("Create database backup");
        if (serviceState.compareAndSet(STATE_IDLE, STATE_BUSY)) {
            try {
                var fileName = formatBackupFileName();
                var data = loadBackupDataFromDatabase();
                var metadata = createBackupMetadata(data);
                var zip = serializationService.serializeAndZip(data, ZIP_ENTRY_NAME);
                writeBackupToFile(fileName, zip);
                metadata.setFileName(fileName);
                metadata = backupMetadataRepository.save(metadata);
                eventPublisher.publishEvent(new BackupCreatedEvent());
                return backupMetadataMapper.toDTO(metadata);
            } finally {
                serviceState.set(STATE_IDLE);
            }
        } else {
            throw new ServiceBusyException("Backup service is busy");
        }
    }

    @Transactional
    public void restoreDatabaseFromBackup(byte[] data) {
        log.info("Restore database from backup");
        if (serviceState.compareAndSet(STATE_IDLE, STATE_BUSY)) {
            try {
                var dto = serializationService.deserializeZippedData(
                        BackupXmlDataDTO.class, data, ZIP_ENTRY_NAME);
                truncateAllTables();
                storeBackupDataInDatabase(dto);
                eventPublisher.publishEvent(new BackupRestoredEvent());
            } finally {
                serviceState.set(STATE_IDLE);
            }
        } else {
            throw new ServiceBusyException("Backup service is busy");
        }
    }
    
    private BackupMetadataEntity createBackupMetadata(BackupXmlDataDTO dto) {
        var metadata = new BackupMetadataEntity();
        metadata.setCreated(LocalDateTime.now());
        metadata.setExpenses(dto.getExpenses().size());
        metadata.setCategories(dto.getCategories().size());
        metadata.setPersons(dto.getPersons().size());
        return metadata;
    }
    
    private BackupXmlDataDTO loadBackupDataFromDatabase() {
        var dto = new BackupXmlDataDTO();
        var categories = categoryRepository.findAll().stream()
                .map(categoryMapper::toXmlDTO)
                .toList();
        dto.setCategories(categories);
        var persons = personRepository.findAll().stream()
                .map(personMapper::toXmlDTO)
                .toList();
        dto.setPersons(persons);
        var expenses = expenseRepository.findAll().stream()
                .map(expenseMapper::toXmlDTO)
                .toList();
        dto.setExpenses(expenses);
        return dto;
    }
    
    private void storeBackupDataInDatabase(BackupXmlDataDTO dto) {
        var persons = new HashMap<Long, PersonEntity>();
        dto.getPersons().forEach(p -> {
            var entity = personMapper.toEntity(p);
            entity = personRepository.save(entity);
            persons.put(p.getId(), entity);
        });
        var categories = new HashMap<Long, CategoryEntity>();
        dto.getCategories().forEach(c -> {
            var entity = categoryMapper.toEntity(c);
            entity = categoryRepository.save(entity);
            categories.put(c.getId(), entity);
        });
        dto.getExpenses().forEach(e -> {
            var entity = expenseMapper.toEntity(e);
            entity.setPerson(persons.get(e.getPersonId()));
            entity.setCategory(categories.get(e.getCategoryId()));
            expenseRepository.save(entity);
        });
    }
    
    private void truncateAllTables() {
        expenseRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
    }

    private void writeBackupToFile(String fileName, byte[] data) {
        try {
            var backupFilePath = Paths.get(backupDirectory, fileName);
            if (Files.exists(backupFilePath)) {
                Files.delete(backupFilePath);
            }
            Files.createDirectories(Paths.get(backupDirectory));
            Files.write(backupFilePath, data);
        } catch (IOException e) {
            throw new ExpenseTrackerException("Failed to save backup to file", e);
        }
    }

    private String formatBackupFileName() {
        var pattern = "yyyy_MM_dd_HH_mm_ss";
        var formatter = DateTimeFormatter.ofPattern(pattern);
        return "backup_" + LocalDateTime.now().format(formatter) + ".zip";
    }
}
