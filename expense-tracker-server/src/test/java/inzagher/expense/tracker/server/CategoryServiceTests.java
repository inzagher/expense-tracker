package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.dto.ColorDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.CategoryService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@SpringBootTest(classes = {TestingConfiguration.class})
@TestPropertySource(locations="classpath:test.properties")
public class CategoryServiceTests {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TestDataManager manager;
    
    private Expense payment;
    private Category phone;
    private Category rent;
    private Person eric;
    
    @BeforeEach
    public void beforeEachTest() {
        eric = manager.storePerson("ERIC");
        phone = manager.storeCategory("PHONE", "MONTHLY PHONE BILL", (byte)0, (byte)0, (byte)0);
        rent = manager.storeCategory("RENT", "MONTHLY RENT BILL", (byte)128, (byte)128, (byte)128);
        payment = manager.storeExpense(2020, 10, 10, eric, phone, 1000.50D, "TEST BILL PAYMENT");
        assertEquals(1L, manager.countPersons());
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countExpenses());
    }
    
    @AfterEach
    public void afterEachTest() {
        manager.truncateAllTables();
        payment = null;
        phone = null;
        rent = null;
        eric = null;
    }
    
    @Test
    public void categoryListTest() {
        assertEquals(2, categoryService.getAllCategories().size());
    }
    
    @Test
    public void categoryLoadingTest() {
        Optional<CategoryDTO> loaded = categoryService.getCategoryById(rent.getId());
        assertTrue(loaded.isPresent());
        assertEquals(rent.getId(), loaded.get().getId());
        assertEquals("RENT", loaded.get().getName());
    }
    
    @Test
    public void categoryCreationTest() {
        CategoryDTO education = new CategoryDTO();
        education.setName("EDUCATION");
        education.setDescription("YEARLY EDUCATION BILL");
        education.setColor(new ColorDTO((byte)30, (byte)30, (byte)30));
        UUID storedRecordID = categoryService.storeCategory(education);
        assertNotNull(storedRecordID);
        assertEquals(3L, manager.countCategories());
        assertEquals("EDUCATION", manager.getCatetory(storedRecordID).get().getName());
    }
    
    @Test
    public void categoryEditingTest() {
        CategoryDTO category = phone.toDTO();
        category.setColor(new ColorDTO((byte)16, (byte)16, (byte)16));
        UUID storedRecordID = categoryService.storeCategory(category);
        assertEquals(phone.getId(), storedRecordID);
        assertEquals(2L, manager.countCategories());
        assertEquals(16, manager.getCatetory(phone.getId()).get().getColor().getRed());
    }
    
    @Test
    public void categoryDeletionTest() {
        categoryService.deleteCategory(phone.getId());
        assertEquals(1L, manager.countCategories());
    }
    
    @Test
    public void dependentCategoryDeletionTest() {
        categoryService.deleteCategory(phone.getId());
        assertEquals(1L, manager.countCategories());
        assertEquals(1L, manager.countExpenses());
        assertFalse(manager.getCatetory(payment.getId()).isPresent());
    }
}
