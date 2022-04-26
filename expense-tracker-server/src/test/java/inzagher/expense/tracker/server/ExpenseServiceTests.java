package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import inzagher.expense.tracker.server.model.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import inzagher.expense.tracker.server.service.ExpenseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class ExpenseServiceTests {
    @Autowired
    private ExpenseService service;
    @Autowired
    private TestDataManager manager;
    @Autowired
    private ExpenseMapper mapper;
    
    private PersonEntity tom;
    private CategoryEntity food;
    private CategoryEntity phone;
    private ExpenseEntity purchase;
    
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
    void expenseLoadingTest() {
        var entity = service.getExpenseById(purchase.getId());
        assertEquals(purchase.getId(), entity.getId());
        assertAmountEquals(BigDecimal.valueOf(12.10D), entity.getAmount());
    }
    
    @Test
    void expenseCreationTest() {
        ExpenseDTO expense = new ExpenseDTO();
        expense.setDate(LocalDate.now());
        expense.setPersonId(tom.getId());
        expense.setCategoryId(food.getId());
        expense.setAmount(BigDecimal.valueOf(51.20D));
        expense.setDescription("ANOTHER FOOD PURCHASE");
        var id = service.createExpense(mapper.toCreateCommand(expense));
        assertNotNull(id);
        assertEquals(2L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
        var entity = manager.findExpenseById(id);
        assertTrue(entity.isPresent());
        assertAmountEquals(BigDecimal.valueOf(51.20D), entity.get().getAmount());
    }
    
    @Test
    void expenseEditingTest() {
        ExpenseDTO expense = mapper.toDTO(purchase);
        expense.setAmount(BigDecimal.valueOf(90.00D));
        expense.setCategoryId(phone.getId());
        service.editExpense(mapper.toEditCommand(expense));
        assertEquals(1L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
        var entity = manager.findExpenseById(purchase.getId());
        assertTrue(entity.isPresent());
        assertEquals("TOM", entity.get().getPerson().getName());
        assertEquals("PHONE", entity.get().getCategory().getName());
        assertAmountEquals(BigDecimal.valueOf(90.00D), entity.get().getAmount());
    }
    
    @Test
    void expenseDeletionTest() {
        service.deleteExpenseById(purchase.getId());
        assertEquals(0L, manager.countExpenses());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countPersons());
    }

    private void assertAmountEquals(BigDecimal expected, BigDecimal actual) {
        assertEquals(0, expected.compareTo(actual), "AMOUNT MISMATCH");
    }
}