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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ServiceRunner.class})
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
    public void expenseStoreTest() {
        ExpenseDTO expense = createTestExpense(tom, food, 500.0F).toDTO();
        assertNotNull(expenseService.storeExpense(expense));
        assertEquals(expenseRepository.count(), 2L);
        assertEquals(categoryRepository.count(), 2L);
        assertEquals(personRepository.count(), 1L);
    }
    
    @Test
    public void expenseEditTest() {
        ExpenseDTO expense = purchase.toDTO();
        expense.setAmmount(900F);
        expense.setCategoryId(phone.getId().toString());
        String storedRecordId = expenseService.storeExpense(expense);
        assertEquals(storedRecordId, purchase.getId().toString());
        assertEquals(expenseRepository.count(), 1L);
        assertEquals(categoryRepository.count(), 2L);
        assertEquals(personRepository.count(), 1L);
    }
    
    @Test
    public void expenseLoadingTest() {
        String id = purchase.getId().toString();
        Optional<ExpenseDTO> loaded = expenseService.getExpense(id);
        Assertions.assertTrue(loaded.isPresent());
        Assertions.assertEquals(loaded.get().getId(), id);
        Assertions.assertEquals(loaded.get().getAmmount(), Float.valueOf(12.1F));
    }
    
    @Test
    public void expenseDeletionTest() {
        String id = purchase.getId().toString();
        expenseService.deleteExpense(id);
        assertEquals(expenseRepository.count(), 0L);
        assertEquals(categoryRepository.count(), 2L);
        assertEquals(personRepository.count(), 1L);
    }
    
    private Expense createTestExpense(Person person, Category category, Float ammount) {
        Expense expense = new Expense();
        expense.setDate(LocalDate.now());
        expense.setAmmount(ammount);
        expense.setPerson(person);
        expense.setCategory(category);
        expense.setDescription("EXPENSE TESTING");
        return expense;
    }
}