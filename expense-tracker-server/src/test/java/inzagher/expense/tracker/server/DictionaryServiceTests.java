package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import inzagher.expense.tracker.server.service.DictionaryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class DictionaryServiceTests {
    @Autowired
    private DictionaryService service;
    @Autowired
    private TestDataManager manager;

    private CategoryEntity phone;
    private PersonEntity eric;

    @BeforeEach
    public void beforeEachTest() {
        eric = manager.storePerson("ERIC");
        phone = manager.storeCategory("PHONE", "MONTHLY PHONE BILL", 0, 0, 0, false);
    }

    @AfterEach
    public void afterEachTest() {
        manager.truncateAllTables();
        phone = null;
        eric = null;
    }

    @Test
    void expenseDescriptionsTest() {
        storeExpenseWithDescription("Test111");
        storeExpenseWithDescription("Test222");
        storeExpenseWithDescription("Test222");
        storeExpenseWithDescription("Test333");
        storeExpenseWithDescription("Test333");
        storeExpenseWithDescription("Test333");
        findOptionsAndAssert("test", 2, "Test222", "Test333");
    }

    private void storeExpenseWithDescription(String description) {
        manager.storeExpense(2000, 1, 1, eric, phone, 10D, description);
    }

    private void findOptionsAndAssert(String pattern, Integer minCount, String... expectedOptions) {
        var options = service.findExpenseDescriptions(pattern, minCount);
        assertArrayEquals(expectedOptions, options.toArray());
    }
}
