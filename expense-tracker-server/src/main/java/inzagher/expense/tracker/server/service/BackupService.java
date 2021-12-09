package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.*;
import inzagher.expense.tracker.server.mapper.*;
import inzagher.expense.tracker.server.model.*;
import inzagher.expense.tracker.server.outbox.BackupDataOutbox;
import inzagher.expense.tracker.server.repository.BackupMetadataRepository;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BackupService {
    private static final int STATE_IDLE = 0;
    private static final int STATE_BUSY = 1;
    private static final AtomicInteger serviceState = new AtomicInteger();
    
    private final JAXBContext jaxbContext;
    private final BackupDataOutbox backupDataOutbox;
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;
    private final BackupMetadataRepository backupMetadataRepository;
    private final BackupMetadataMapper backupMetadataMapper;
    private final ExpenseMapper expenseMapper;
    private final CategoryMapper categoryMapper;
    private final PersonMapper personMapper;
    
    public List<BackupMetadataDTO> getAllBackupInfo() {
        return backupMetadataRepository.findAll().stream()
                .map(backupMetadataMapper::toDTO)
                .toList();
    }
    
    public Optional<BackupMetadataDTO> getLastBackupInfo() {
        var request = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "time"));
        var backups =  backupMetadataRepository.findAll(request).toList();
        return backups.stream().map(backupMetadataMapper::toDTO).findFirst();
    }

    public BackupMetadataDTO createDatabaseBackup() {
        if (serviceState.compareAndSet(STATE_IDLE, STATE_BUSY)) {
            var data = loadBackupDataFromDatabase();
            var metadata = createBackupMetadata(data);
            serializeBackupData(metadata, data);
            metadata = backupMetadataRepository.saveAndFlush(metadata);
            serviceState.set(STATE_IDLE);
            return backupMetadataMapper.toDTO(metadata);
        } else {
            throw new RuntimeException("Backup service is busy");
        }
    }
    
    public void restoreDatabaseFromBackup(byte[] data) {
        if (serviceState.compareAndSet(STATE_IDLE, STATE_BUSY)) {
            BackupDataDTO dto = deserializeBackupData(data);
            truncateAllTables();
            storeBackupDataInDatabase(dto);
            serviceState.set(STATE_IDLE);
        } else {
            throw new RuntimeException("Backup service is busy");
        }
    }
    
    private Person createPerson(PersonDTO dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setName(dto.getName());
        return person;
    }
    
    private Category createCategory(CategoryDTO dto) {
        Integer red = dto.getColor().getRed();
        Integer green = dto.getColor().getGreen();
        Integer blue = dto.getColor().getBlue();
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setColor(new Color(red, green, blue));
        category.setObsolete(dto.getObsolete());
        return category;
    }
    
    private Expense createExpense(ExpenseDTO dto, List<Person> persons, List<Category> categories) {
        Optional<Person> person = persons.stream()
                .filter(c -> c.getId().equals(dto.getPersonId()))
                .findFirst();
        
        Optional<Category> category = categories.stream()
                .filter(c -> c.getId().equals(dto.getCategoryId()))
                .findFirst();
        
        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setDate(dto.getDate());
        expense.setAmount(dto.getAmount());
        expense.setPerson(person.orElse(null));
        expense.setCategory(category.orElse(null));
        expense.setDescription(dto.getDescription());
        return expense;
    }
    
    private BackupMetadata createBackupMetadata(BackupDataDTO dto) {
        BackupMetadata metadata = new BackupMetadata();
        metadata.setTime(LocalDateTime.now());
        metadata.setExpenses(dto.getExpenses().size());
        metadata.setCategories(dto.getCategories().size());
        metadata.setPersons(dto.getPersons().size());
        return metadata;
    }
    
    private void serializeBackupData(BackupMetadata metadata, BackupDataDTO dto) {
        try (OutputStream sos = backupDataOutbox.createOutputStream(metadata)) {
            try (ZipOutputStream zos = new ZipOutputStream(sos)) {
                zos.putNextEntry(new ZipEntry("expenses.xml"));
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(dto, zos);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private BackupDataDTO deserializeBackupData(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            try (ZipInputStream zis = new ZipInputStream(bis)) {
                ZipEntry zipEntry = zis.getNextEntry();
                if (zipEntry != null && zipEntry.getName().equals("expenses.xml")) {
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    return (BackupDataDTO)unmarshaller.unmarshal(zis);
                } else {
                    throw new RuntimeException("Zip archive is not backup.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private BackupDataDTO loadBackupDataFromDatabase() {
        BackupDataDTO dto = new BackupDataDTO();
        List<CategoryDTO> categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
        dto.setCategories(categories);
        List<PersonDTO> persons = personRepository.findAll()
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
        dto.setPersons(persons);
        List<ExpenseDTO> expenses = expenseRepository.findAll()
                .stream()
                .map(expenseMapper::toDTO)
                .collect(Collectors.toList());
        dto.setExpenses(expenses);
        return dto;
    }
    
    private void storeBackupDataInDatabase(BackupDataDTO dto) {
        final List<Person> persons = personRepository.saveAll(
            dto.getPersons().stream()
                .map(this::createPerson)
                .collect(Collectors.toList())
        );
        final List<Category> categories = categoryRepository.saveAll(
            dto.getCategories().stream()
                .map(this::createCategory)
                .collect(Collectors.toList())
        );
        expenseRepository.saveAll(
            dto.getExpenses().stream()
                .map(e -> createExpense(e, persons, categories))
                .collect(Collectors.toList())
        );
        BackupMetadata metadata = createBackupMetadata(dto);
        backupMetadataRepository.saveAndFlush(metadata);
    }
    
    private void truncateAllTables() {
        expenseRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
        backupMetadataRepository.deleteAllInBatch();
    }
}
