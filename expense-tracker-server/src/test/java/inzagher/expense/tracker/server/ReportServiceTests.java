package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;
import inzagher.expense.tracker.server.dto.YearlyReportItemDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.ReportService;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Transactional
@SpringBootTest(classes = {ServiceRunner.class})
@TestPropertySource(locations="classpath:test.properties")
public class ReportServiceTests {
    @Autowired
    private ReportService reportService;
    @Autowired
    private TestDataManager manager;
    
    private Category phone;
    private Category rent;
    private Category food;
    
    private Person bob;
    private Person alice;
    
    @BeforeEach
    public void beforeEachTest() {
        phone = manager.storeCategory("PHONE", "MONTHLY PHONE BILL", (byte)0, (byte)0, (byte)0);
        rent = manager.storeCategory("RENT", "MONTHLY RENT BILL", (byte)128, (byte)128, (byte)128);
        food = manager.storeCategory("FOOD", "DAILY FOOD PURCHASE", (byte)255, (byte)255, (byte)255);
        assertEquals(3L, manager.countCategories());
        
        bob = manager.storePerson("BOB");
        alice = manager.storePerson("ALICE");
        assertEquals(2L, manager.countPersons());
        
        // JANUARY
        manager.storeExpense(2020, 1, 1, alice, rent, 500.0F);
        manager.storeExpense(2020, 1, 1, alice, food, 15.30F);
        manager.storeExpense(2020, 1, 1, bob, food, 35.40F);
        manager.storeExpense(2020, 1, 2, alice, phone, 100.0F);
        manager.storeExpense(2020, 1, 2, alice, food, 61.33F);
        manager.storeExpense(2020, 1, 2, bob, phone, 280.0F);
        manager.storeExpense(2020, 1, 2, bob, food, 58.90F);
        manager.storeExpense(2020, 1, 2, alice, food, 15.45F);
        manager.storeExpense(2020, 1, 3, alice, food, 56.94F);
        
        //FEBRUARY
        manager.storeExpense(2020, 2, 1, alice, rent, 600.0F);
        manager.storeExpense(2020, 2, 1, alice, food, 45.24F);
        manager.storeExpense(2020, 2, 1, bob, food, 44.73F);
        manager.storeExpense(2020, 2, 2, alice, phone, 130.0F);
        manager.storeExpense(2020, 2, 2, alice, food, 62.47F);
        manager.storeExpense(2020, 2, 2, bob, phone, 230.0F);
        manager.storeExpense(2020, 2, 2, bob, food, 72.50F);
        manager.storeExpense(2020, 2, 2, alice, food, 34.77F);
        manager.storeExpense(2020, 2, 3, alice, food, 78.0F);
    }
    
    @AfterEach
    public void afterEachTest() {
        manager.truncateAllTables();
        phone = null;
        rent = null;
        food = null;
        bob = null;
        alice = null;
    }
    
    @Test
    public void monthlyCategoryReportTest() {
        List<CategoryReportItemDTO> jan = reportService.createMonthlyCategoryReport(2020, 1);
        assertEquals(3, jan.size());
        assertMonthlyCategoryReportItem(500.0F, jan, rent);
        assertMonthlyCategoryReportItem(380.0F, jan, phone);
        assertMonthlyCategoryReportItem(243.32F, jan, food);
        
        List<CategoryReportItemDTO> feb = reportService.createMonthlyCategoryReport(2020, 2);
        assertEquals(3, feb.size());
        assertMonthlyCategoryReportItem(600.0F, feb, rent);
        assertMonthlyCategoryReportItem(360.0F, feb, phone);
        assertMonthlyCategoryReportItem(337.71F, feb, food);
        
        List<CategoryReportItemDTO> mar = reportService.createMonthlyCategoryReport(2020, 3);
        assertEquals(3, mar.size());
        assertMonthlyCategoryReportItem(0.0F, mar, rent);
        assertMonthlyCategoryReportItem(0.0F, mar, phone);
        assertMonthlyCategoryReportItem(0.0F, mar, food);
    }
    
    @Test
    public void yearlyReportTest() {
        List<YearlyReportItemDTO> r2020 = reportService.createYearlyReport(2020);
        assertEquals(12, r2020.size());
        assertYearlyReportItem(1123.32F, r2020, 1);
        assertYearlyReportItem(1297.71F, r2020, 2);
        assertYearlyReportItem(0.00F, r2020, 4);
        assertYearlyReportItem(0.00F, r2020, 5);
        assertYearlyReportItem(0.00F, r2020, 6);
        assertYearlyReportItem(0.00F, r2020, 7);
        assertYearlyReportItem(0.00F, r2020, 8);
        assertYearlyReportItem(0.00F, r2020, 9);
        assertYearlyReportItem(0.00F, r2020, 10);
        assertYearlyReportItem(0.00F, r2020, 11);
        assertYearlyReportItem(0.00F, r2020, 12);
        
        List<YearlyReportItemDTO> r2021 = reportService.createYearlyReport(2021);
        assertEquals(12, r2021.size());
        assertYearlyReportItem(0.00F, r2021, 1);
        assertYearlyReportItem(0.00F, r2021, 2);
        assertYearlyReportItem(0.00F, r2021, 4);
        assertYearlyReportItem(0.00F, r2021, 5);
        assertYearlyReportItem(0.00F, r2021, 6);
        assertYearlyReportItem(0.00F, r2021, 7);
        assertYearlyReportItem(0.00F, r2021, 8);
        assertYearlyReportItem(0.00F, r2021, 9);
        assertYearlyReportItem(0.00F, r2021, 10);
        assertYearlyReportItem(0.00F, r2021, 11);
        assertYearlyReportItem(0.00F, r2021, 12);
    }
    
    private void assertMonthlyCategoryReportItem(Float expectedAmount,
            List<CategoryReportItemDTO> report,
            Category category) {
        Float actualAmount = report.stream()
                .filter(item -> item.getCategory().getId().equals(category.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("CATEGORY NOT FOUND"))
                .getAmount();
        assertEquals(expectedAmount, actualAmount, "AMOUNT MISMATCH FOR CATEGORY: " + category.getName());
    }
    
    private void assertYearlyReportItem(Float expectedAmount,
            List<YearlyReportItemDTO> report, int month) {
        Float actualAmount = report.get(month - 1).getAmount();
        assertEquals(expectedAmount, actualAmount, "AMOUNT MISMATCH FOR MONTH: " + month);
    }
}
