package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import inzagher.expense.tracker.server.service.ReportService;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest(classes = {ServiceRunner.class})
public class ReportServiceTests {
    @Autowired
    private ReportService reportService;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PersonRepository personRepository;
    
    @BeforeEach
    public void beforeEachTest() {
    }
    
    @AfterEach
    public void afterEachTest() {
        expenseRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
    }
    
    @Test
    @Disabled
    public void monthlyReportTest() {
    }
    
    @Test
    @Disabled
    public void yearlyReportTest() {
        
    }
}
