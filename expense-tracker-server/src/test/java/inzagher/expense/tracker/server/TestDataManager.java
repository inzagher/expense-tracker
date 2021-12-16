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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    
    public Optional<Expense> findExpenseById(Integer id) {
        return expenseRepository.findById(id);
    }
    
    public Optional<Category> findCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }
    
    public Optional<Person> findPersonById(Integer id) {
        return personRepository.findById(id);
    }
    
    public Expense storeExpense(int year, int month, int day, Person person,
            Category category, Double amount, String description) {
        Expense expense = new Expense();
        expense.setDate(LocalDate.of(year, month, day));
        expense.setAmount(BigDecimal.valueOf(amount));
        expense.setPerson(person);
        expense.setCategory(category);
        expense.setDescription(description);
        return expenseRepository.saveAndFlush(expense);
    }
    
    public Category storeCategory(String name, String description,
            int r, int g, int b, Boolean obsolete
    ) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setColor(new Color(r, g, b));
        category.setObsolete(obsolete);
        return categoryRepository.saveAndFlush(category);
    }
    
    public Person storePerson(String name) {
        Person person = new Person();
        person.setName(name);
        return personRepository.saveAndFlush(person);
    }
    
    public BackupMetadata storeBackupMetadata(LocalDateTime created, int expenses,
            int persons, int categories) {
        BackupMetadata bm = new BackupMetadata();
        bm.setCreated(created);
        bm.setExpenses(expenses);
        bm.setPersons(persons);
        bm.setCategories(categories);
        return backupMetadataRepository.saveAndFlush(bm);
    }
}
