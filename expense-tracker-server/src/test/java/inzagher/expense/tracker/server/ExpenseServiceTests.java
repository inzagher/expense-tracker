package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
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
    private TestDataManager manager;
    
    private Person tom;
    private Category food;
    private Category phone;
    private Expense purchase;
    
    @BeforeEach
    public void beforeEachTest() {
        tom = manager.storePerson("TOM");
        food = manager.storeCategory("FOOD", "FOOD PURCHASE", (byte)0, (byte)0, (byte)0);
        phone = manager.storeCategory("PHONE", "PHONE BILL", (byte)127, (byte)127, (byte)127);
        purchase = manager.storeExpense(2020, 10, 10, tom, food, 12.1f, "FOOD PURCHASE");
        assertEquals(1L, manager.countPersons());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countExpenses());
    }
    
    @AfterEach
    public void afterEachTest() {
        manager.truncateAllTables();
        tom = null;
        food = null;
        phone = null;
        purchase = null;
    }
    
    @Test
    public void expenseLoadingTest() {
        Optional<ExpenseDTO> loaded = expenseService.getExpenseById(purchase.getId());
        assertTrue(loaded.isPresent());
        assertEquals(purchase.getId(), loaded.get().getId());
        assertEquals(12.1F, loaded.get().getAmount());
    }
    
    @Test
    public void expenseCreationTest() {
        ExpenseDTO expense = new ExpenseDTO();
        expense.setDate(LocalDate.now());
        expense.setPersonId(tom.getId());
        expense.setCategoryId(food.getId());
        expense.setAmount(51.20F);
        expense.setDescription("ANOTHER FOOD PURCHASE");
        UUID storedRecordID = expenseService.storeExpense(expense);
        assertNotNull(storedRecordID);
        assertEquals(2L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
        assertEquals(51.20F, manager.getExpense(storedRecordID).get().getAmount());
    }
    
    @Test
    public void expenseEditingTest() {
        ExpenseDTO expense = purchase.toDTO();
        expense.setAmount(90.0F);
        expense.setCategoryId(phone.getId());
        UUID storedRecordID = expenseService.storeExpense(expense);
        assertEquals(purchase.getId(), storedRecordID);
        assertEquals(1L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
        assertEquals(90.0F, manager.getExpense(storedRecordID).get().getAmount());
    }
    
    @Test
    public void expenseDeletionTest() {
        expenseService.deleteExpense(purchase.getId());
        assertEquals(0L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
    }
}