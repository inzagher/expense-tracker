package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.ExpenseService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = ServiceRunner.class)
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
        food = manager.storeCategory("FOOD", "FOOD PURCHASE", 0, 0, 0, false);
        phone = manager.storeCategory("PHONE", "PHONE BILL", 127, 127, 127, false);
        purchase = manager.storeExpense(2020, 10, 10, tom, food, 12.10D, "FOOD PURCHASE");
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
        assertAmountEquals(BigDecimal.valueOf(12.10D), loaded.get().getAmount());
    }
    
    @Test
    public void expenseCreationTest() {
        ExpenseDTO expense = new ExpenseDTO();
        expense.setDate(LocalDate.now());
        expense.setPersonId(tom.getId());
        expense.setCategoryId(food.getId());
        expense.setAmount(BigDecimal.valueOf(51.20D));
        expense.setDescription("ANOTHER FOOD PURCHASE");
        Integer storedRecordID = expenseService.storeExpense(expense);
        assertNotNull(storedRecordID);
        assertEquals(2L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
        assertAmountEquals(BigDecimal.valueOf(51.20D), manager.getExpense(storedRecordID).get().getAmount());
    }
    
    @Test
    public void expenseEditingTest() {
        ExpenseDTO expense = purchase.toDTO();
        expense.setAmount(BigDecimal.valueOf(90.00D));
        expense.setCategoryId(phone.getId());
        Integer storedRecordID = expenseService.storeExpense(expense);
        assertEquals(purchase.getId(), storedRecordID);
        assertEquals(1L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
        assertAmountEquals(BigDecimal.valueOf(90.00D), manager.getExpense(storedRecordID).get().getAmount());
    }
    
    @Test
    public void expenseDeletionTest() {
        expenseService.deleteExpense(purchase.getId());
        assertEquals(0L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
    }

    private void assertAmountEquals(BigDecimal expected, BigDecimal actual) {
        assertTrue(expected.compareTo(actual) == 0, "AMOUNT MISMATCH");
    }
}