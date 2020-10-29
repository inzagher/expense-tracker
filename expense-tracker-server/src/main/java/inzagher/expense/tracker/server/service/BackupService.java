package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.BackupDataDTO;
import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import inzagher.expense.tracker.server.core.BackupDataStorage;
import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.BackupMetadata;
import inzagher.expense.tracker.server.repository.BackupMetadataRepository;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.zip.ZipEntry;

@Service
public class BackupService {
    private final JAXBContext jaxbContext;
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;
    private final BackupMetadataRepository backupMetadataRepository;
    private final BackupDataStorage backupDataStorage;
    
    @Autowired
    public BackupService(
            ExpenseRepository expenseRepository,
            CategoryRepository categoryRepository,
            PersonRepository personRepository,
            BackupMetadataRepository backupMetadataRepository,
            BackupDataStorage backupDataStorage
    ) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
        this.backupMetadataRepository = backupMetadataRepository;
        this.backupDataStorage = backupDataStorage;
        
        Class[] classes = new Class[]{ BackupDataDTO.class };
        try { jaxbContext = JAXBContext.newInstance(classes); }
        catch(JAXBException e) { throw new RuntimeException(e); }
    }
    
    public List<BackupMetadataDTO> getAllBackups() {
        return backupMetadataRepository.findAll().stream()
                .map(BackupMetadata::toDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<BackupMetadataDTO> getLastBackup() {
        return backupMetadataRepository.last().map(BackupMetadata::toDTO);
    }
    
    public BackupMetadataDTO backupDatabase() {
        BackupDataDTO data = createDatabaseBackup();
        BackupMetadata metadata = createBackupMetadata(data);
        storeBackupData(metadata, data);
        return metadata.toDTO();
    }
    
    public void restoreDatabase(byte[] data) {
    }
    
    private BackupDataDTO createDatabaseBackup() {
        BackupDataDTO dto = new BackupDataDTO();
        List<CategoryDTO> categories = categoryRepository.findAll()
                .stream()
                .map(Category::toDTO)
                .collect(Collectors.toList());
        dto.setCategories(categories);
        List<PersonDTO> persons = personRepository.findAll()
                .stream()
                .map(Person::toDTO)
                .collect(Collectors.toList());
        dto.setPersons(persons);
        List<ExpenseDTO> expenses = expenseRepository.findAll()
                .stream()
                .map(Expense::toDTO)
                .collect(Collectors.toList());
        dto.setExpenses(expenses);
        return dto;
    }
    
    private void storeBackupData(BackupMetadata metadata, BackupDataDTO dto) {
        backupMetadataRepository.saveAndFlush(metadata);
        try (OutputStream sos = backupDataStorage.createOutputStream(metadata)) {
            try (ZipOutputStream zos = new ZipOutputStream(sos)) {
                zos.putNextEntry(new ZipEntry("expenses.xml"));
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(dto, zos);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private BackupMetadata createBackupMetadata(BackupDataDTO dto) {
        BackupMetadata metadata = new BackupMetadata();
        metadata.setTime(LocalDateTime.now());
        metadata.setExpenses(dto.getExpenses().size());
        metadata.setCategories(dto.getCategories().size());
        metadata.setPersons(dto.getPersons().size());
        return metadata;
    }
}
