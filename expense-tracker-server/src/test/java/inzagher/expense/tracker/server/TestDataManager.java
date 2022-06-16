package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.entity.*;
import inzagher.expense.tracker.server.repository.BackupMetadataRepository;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Transactional
public class TestDataManager {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;
    private final BackupMetadataRepository backupMetadataRepository;

    @Autowired
    public TestDataManager(
            ExpenseRepository expenseRepository,
            CategoryRepository categoryRepository,
            PersonRepository personRepository,
            BackupMetadataRepository backupMetadataRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
        this.backupMetadataRepository = backupMetadataRepository;
    }
    
    public long countBackups() {
        return backupMetadataRepository.count();
    }
    
    public long countExpenses() {
        return expenseRepository.count();
    }
    
    public long countPersons() {
        return personRepository.count();
    }
    
    public long countCategories() {
        return categoryRepository.count();
    }
    
    public void truncateAllTables() {
        expenseRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
        backupMetadataRepository.deleteAllInBatch();
    }
    
    public Optional<ExpenseEntity> findExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
    
    public Optional<CategoryEntity> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public Optional<PersonEntity> findPersonById(Long id) {
        return personRepository.findById(id);
    }
    
    public ExpenseEntity storeExpense(int year, int month, int day, PersonEntity person,
                                      CategoryEntity category, Double amount, String description) {
        ExpenseEntity expense = new ExpenseEntity();
        expense.setDate(LocalDate.of(year, month, day));
        expense.setAmount(BigDecimal.valueOf(amount));
        expense.setPerson(person);
        expense.setCategory(category);
        expense.setDescription(description);
        return expenseRepository.saveAndFlush(expense);
    }
    
    public CategoryEntity storeCategory(String name, String description,
                                        int r, int g, int b, Boolean obsolete
    ) {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setDescription(description);
        category.setColor(new ColorEntity(r, g, b));
        category.setObsolete(obsolete);
        return categoryRepository.saveAndFlush(category);
    }
    
    public PersonEntity storePerson(String name) {
        PersonEntity person = new PersonEntity();
        person.setName(name);
        return personRepository.saveAndFlush(person);
    }
    
    public BackupMetadataEntity storeBackupMetadata(LocalDateTime created, int expenses,
                                                    int persons, int categories, String fileName) {
        BackupMetadataEntity bm = new BackupMetadataEntity();
        bm.setCreated(created);
        bm.setFileName(fileName);
        bm.setExpenses(expenses);
        bm.setPersons(persons);
        bm.setCategories(categories);
        return backupMetadataRepository.saveAndFlush(bm);
    }
}
