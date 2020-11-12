package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.BackupMetadata;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.BackupMetadataRepository;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
    
    public Expense storeExpense(int year, int month, int day,
            Person person, Category category, Float amount) {
        Expense expense = new Expense();
        expense.setDate(LocalDate.of(year, month, day));
        expense.setAmount(amount);
        expense.setPerson(person);
        expense.setCategory(category);
        expense.setDescription("REPORT TEST");
        return expenseRepository.saveAndFlush(expense);
    }
    
    public Category storeCategory(String name, String description,
            byte r, byte g, byte b) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setColor(new Color(r, g, b));
        return categoryRepository.saveAndFlush(category);
    }
    
    public Person storePerson(String name) {
        Person person = new Person();
        person.setName(name);
        return personRepository.saveAndFlush(person);
    }
    
    public BackupMetadata storeBackupMetadata(LocalDateTime time, int expenses,
            int persons, int categories) {
        BackupMetadata bm = new BackupMetadata();
        bm.setTime(time);
        bm.setExpenses(expenses);
        bm.setPersons(persons);
        bm.setCategories(categories);
        return backupMetadataRepository.saveAndFlush(bm);
    }
}
