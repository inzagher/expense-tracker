package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.BackupDTO;
import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import inzagher.expense.tracker.server.core.BackupDataStorage;
import inzagher.expense.tracker.server.dto.OperationResultDTO;
import inzagher.expense.tracker.server.model.BackupMetadata;
import inzagher.expense.tracker.server.repository.BackupMetadataRepository;
import java.util.Optional;

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
        
        Class[] classes = new Class[]{ BackupDTO.class };
        try { jaxbContext = JAXBContext.newInstance(classes); }
        catch(JAXBException e) { throw new RuntimeException(e); }
    }
    
    public OperationResultDTO backupDatabase(boolean forced) {
        Optional<BackupMetadata> lastBackupMetadata = backupMetadataRepository.last();
        if (isBackupRequired(lastBackupMetadata)) {
            BackupDTO backup = createDatabaseBackup();
            byte[] data = serialize(backup);
            backupDataStorage.store(new BackupMetadata(), data);
            return OperationResultDTO.succeeded();
        } else {
            return OperationResultDTO.failed("Nothing to store");
        }
    }
    
    public OperationResultDTO restoreDatabase(byte[] data) {
        return OperationResultDTO.succeeded();
    }
    
    private BackupDTO createDatabaseBackup() {
        BackupDTO dto = new BackupDTO();
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
    
    private byte[] serialize(BackupDTO dto) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ZipOutputStream zos = new ZipOutputStream(bos)) {
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(dto, zos);
                return bos.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private boolean isBackupRequired(Optional<BackupMetadata> last) {
        return !last.isPresent();
    }
}
