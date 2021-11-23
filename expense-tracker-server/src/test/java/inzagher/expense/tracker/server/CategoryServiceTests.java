package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.dto.ColorDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.CategoryService;
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
        phone = manager.storeCategory("PHONE", "MONTHLY PHONE BILL", 0, 0, 0, false);
        rent = manager.storeCategory("RENT", "MONTHLY RENT BILL", 128, 128, 128, false);
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
        education.setColor(new ColorDTO(30, 30, 30));
        education.setObsolete(false);
        Integer storedRecordID = categoryService.storeCategory(education);
        assertNotNull(storedRecordID);
        assertEquals(3L, manager.countCategories());
        assertEquals("EDUCATION", manager.getCategory(storedRecordID).get().getName());
    }
    
    @Test
    public void categoryEditingTest() {
        CategoryDTO category = phone.toDTO();
        category.setColor(new ColorDTO(16, 16, 16));
        Integer storedRecordID = categoryService.storeCategory(category);
        assertEquals(phone.getId(), storedRecordID);
        assertEquals(2L, manager.countCategories());
        assertEquals(16, manager.getCategory(phone.getId()).get().getColor().getRed());
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
        assertFalse(manager.getCategory(payment.getId()).isPresent());
    }
}
