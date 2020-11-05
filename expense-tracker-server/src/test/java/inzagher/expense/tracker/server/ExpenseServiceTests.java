package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import inzagher.expense.tracker.server.service.ExpenseService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Transactional
@SpringBootTest(classes = {ServiceRunner.class})
@TestPropertySource(locations="classpath:test.properties")
public class ExpenseServiceTests {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    
    private Person tom;
    private Category food;
    private Category phone;
    private Expense purchase;
    
    @BeforeEach
    public void beforeEachTest() {
        food = new Category();
        food.setName("FOOD");
        food.setColor(new Color((byte)0, (byte)0, (byte)0));
        food.setDescription("FOOD PURCHASE");
        food = categoryRepository.saveAndFlush(food);
        
        phone = new Category();
        phone.setName("PHONE");
        phone.setColor(new Color((byte)127, (byte)127, (byte)127));
        phone.setDescription("PHONE BILL");
        phone = categoryRepository.saveAndFlush(phone);
        
        tom = new Person();
        tom.setName("TOM");
        tom = personRepository.saveAndFlush(tom);
        
        purchase = createTestExpense(tom, food, 12.1f);
        purchase = expenseRepository.saveAndFlush(purchase);
    }
    
    @AfterEach
    public void afterEachTest() {
        expenseRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
        
        tom = null;
        food = null;
        phone = null;
        purchase = null;
    }
    
    @Test
    public void expenseLoadingTest() {
        Optional<ExpenseDTO> loaded = expenseService.getExpenseById(purchase.getId());
        assertTrue(loaded.isPresent());
        assertEquals(loaded.get().getId(), purchase.getId());
        assertEquals(loaded.get().getAmount(), Float.valueOf(12.1F));
    }
    
    @Test
    public void expenseCreationTest() {
        ExpenseDTO expense = createTestExpense(tom, food, 500.0F).toDTO();
        assertNotNull(expenseService.storeExpense(expense));
        assertEquals(expenseRepository.count(), 2L);
        assertEquals(categoryRepository.count(), 2L);
        assertEquals(personRepository.count(), 1L);
    }
    
    @Test
    public void expenseEditingTest() {
        ExpenseDTO expense = purchase.toDTO();
        expense.setAmount(900F);
        expense.setCategoryId(phone.getId());
        UUID storedRecordID = expenseService.storeExpense(expense);
        assertEquals(storedRecordID, purchase.getId());
        assertEquals(expenseRepository.count(), 1L);
        assertEquals(categoryRepository.count(), 2L);
        assertEquals(personRepository.count(), 1L);
    }
    
    @Test
    public void expenseDeletionTest() {
        expenseService.deleteExpense(purchase.getId());
        assertEquals(expenseRepository.count(), 0L);
        assertEquals(categoryRepository.count(), 2L);
        assertEquals(personRepository.count(), 1L);
    }
    
    private Expense createTestExpense(Person person, Category category, Float amount) {
        Expense expense = new Expense();
        expense.setDate(LocalDate.now());
        expense.setAmount(amount);
        expense.setPerson(person);
        expense.setCategory(category);
        expense.setDescription("EXPENSE TESTING");
        return expense;
    }
}