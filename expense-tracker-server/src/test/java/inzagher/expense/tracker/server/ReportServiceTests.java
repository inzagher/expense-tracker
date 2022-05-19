package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.dto.report.CategoryReportItemDTO;
import inzagher.expense.tracker.server.model.dto.report.YearlyReportItemDTO;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import inzagher.expense.tracker.server.service.ReportService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class ReportServiceTests {
    private static final String TEST_DESCRIPTION = "REPORT TEST";
    
    @Autowired
    private ReportService reportService;
    @Autowired
    private TestDataManager manager;
    
    private CategoryEntity phone;
    private CategoryEntity rent;
    private CategoryEntity food;
    
    private PersonEntity bob;
    private PersonEntity alice;
    
    @BeforeEach
    void beforeEachTest() {
        phone = manager.storeCategory("PHONE", "MONTHLY PHONE BILL", 0, 0, 0, false);
        rent = manager.storeCategory("RENT", "MONTHLY RENT BILL", 128, 128, 128, false);
        food = manager.storeCategory("FOOD", "DAILY FOOD PURCHASE", 255, 255, 255, false);
        assertEquals(3L, manager.countCategories());
        
        bob = manager.storePerson("BOB");
        alice = manager.storePerson("ALICE");
        assertEquals(2L, manager.countPersons());
        
        // JANUARY
        manager.storeExpense(2020, 1, 1, alice, rent, 500.00D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 1, alice, food, 15.30D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 1, bob, food, 35.40D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 2, alice, phone, 100.00D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 2, alice, food, 61.33D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 2, bob, phone, 280.00D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 2, bob, food, 58.90D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 2, alice, food, 15.45D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 3, alice, food, 56.94D, TEST_DESCRIPTION);
        
        //FEBRUARY
        manager.storeExpense(2020, 2, 1, alice, rent, 600.00D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 1, alice, food, 45.24D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 1, bob, food, 44.73D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 2, alice, phone, 130.00D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 2, alice, food, 62.47D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 2, bob, phone, 230.00D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 2, bob, food, 72.50D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 2, alice, food, 34.77D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 3, alice, food, 78.00D, TEST_DESCRIPTION);
    }
    
    @AfterEach
    void afterEachTest() {
        manager.truncateAllTables();
        phone = null;
        rent = null;
        food = null;
        bob = null;
        alice = null;
    }
    
    @Test
    void monthlyCategoryReportTest() {
        List<CategoryReportItemDTO> jan = reportService.createCategoryReport(2020, 1);
        assertEquals(3, jan.size());
        assertMonthlyCategoryReportItem(500.00D, jan, rent);
        assertMonthlyCategoryReportItem(380.00D, jan, phone);
        assertMonthlyCategoryReportItem(243.32D, jan, food);
        
        List<CategoryReportItemDTO> feb = reportService.createCategoryReport(2020, 2);
        assertEquals(3, feb.size());
        assertMonthlyCategoryReportItem(600.00D, feb, rent);
        assertMonthlyCategoryReportItem(360.00D, feb, phone);
        assertMonthlyCategoryReportItem(337.71D, feb, food);
        
        List<CategoryReportItemDTO> mar = reportService.createCategoryReport(2020, 3);
        assertEquals(3, mar.size());
        assertMonthlyCategoryReportItem(0.00D, mar, rent);
        assertMonthlyCategoryReportItem(0.00D, mar, phone);
        assertMonthlyCategoryReportItem(0.00D, mar, food);
    }
    
    @Test
    void yearlyReportTest() {
        List<YearlyReportItemDTO> r2020 = reportService.createYearlyReport(2020);
        assertEquals(12, r2020.size());
        assertYearlyReportItem(1123.32D, r2020, 1);
        assertYearlyReportItem(1297.71D, r2020, 2);
        assertYearlyReportItem(0.00D, r2020, 4);
        assertYearlyReportItem(0.00D, r2020, 5);
        assertYearlyReportItem(0.00D, r2020, 6);
        assertYearlyReportItem(0.00D, r2020, 7);
        assertYearlyReportItem(0.00D, r2020, 8);
        assertYearlyReportItem(0.00D, r2020, 9);
        assertYearlyReportItem(0.00D, r2020, 10);
        assertYearlyReportItem(0.00D, r2020, 11);
        assertYearlyReportItem(0.00D, r2020, 12);
        
        List<YearlyReportItemDTO> r2021 = reportService.createYearlyReport(2021);
        assertEquals(12, r2021.size());
        assertYearlyReportItem(0.00D, r2021, 1);
        assertYearlyReportItem(0.00D, r2021, 2);
        assertYearlyReportItem(0.00D, r2021, 4);
        assertYearlyReportItem(0.00D, r2021, 5);
        assertYearlyReportItem(0.00D, r2021, 6);
        assertYearlyReportItem(0.00D, r2021, 7);
        assertYearlyReportItem(0.00D, r2021, 8);
        assertYearlyReportItem(0.00D, r2021, 9);
        assertYearlyReportItem(0.00D, r2021, 10);
        assertYearlyReportItem(0.00D, r2021, 11);
        assertYearlyReportItem(0.00D, r2021, 12);
    }
    
    private void assertMonthlyCategoryReportItem(Double expectedAmount,
            List<CategoryReportItemDTO> report,
            CategoryEntity category) {
        BigDecimal actualAmount = report.stream()
                .filter(item -> item.getCategory().getId().equals(category.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("CATEGORY NOT FOUND"))
                .getTotal();
        boolean areEqual = BigDecimal.valueOf(expectedAmount).compareTo(actualAmount) == 0;
        assertTrue(areEqual, "AMOUNT MISMATCH FOR CATEGORY: " + category.getName());
    }
    
    private void assertYearlyReportItem(Double expectedAmount,
            List<YearlyReportItemDTO> report, int month) {
        BigDecimal actualAmount = report.get(month - 1).getAmount();
        boolean areEqual = BigDecimal.valueOf(expectedAmount).compareTo(actualAmount) == 0;
        assertTrue(areEqual, "AMOUNT MISMATCH FOR MONTH: " + month);
    }
}
