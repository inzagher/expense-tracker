package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.dto.ColorDTO;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import inzagher.expense.tracker.server.model.mapper.CategoryMapper;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import inzagher.expense.tracker.server.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
public class CategoryServiceTests {
    @Autowired
    private CategoryService service;
    @Autowired
    private TestDataManager manager;
    @Autowired
    private CategoryMapper mapper;
    
    private ExpenseEntity payment;
    private CategoryEntity phone;
    private CategoryEntity rent;
    private PersonEntity eric;
    
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
        assertEquals(2, service.findAllCategories().size());
    }
    
    @Test
    public void categoryLoadingTest() {
        var category = service.getCategoryById(rent.getId());
        assertEquals(rent.getId(), category.getId());
        assertEquals("RENT", category.getName());
    }
    
    @Test
    public void categoryCreationTest() {
        CategoryDTO education = new CategoryDTO();
        education.setName("EDUCATION");
        education.setDescription("YEARLY EDUCATION BILL");
        education.setColor(new ColorDTO(30, 30, 30));
        education.setObsolete(false);
        var command = mapper.toCreateCommand(education);
        var id = service.createCategory(command);
        assertEquals(3L, manager.countCategories());
        var entity = manager.findCategoryById(id);
        assertTrue(entity.isPresent());
        assertEquals("EDUCATION", entity.get().getName());
    }
    
    @Test
    public void categoryEditingTest() {
        var category = mapper.toDTO(phone);
        category.setColor(new ColorDTO(16, 16, 16));
        var command = mapper.toEditCommand(category);
        service.editCategory(command);
        assertEquals(2L, manager.countCategories());
        var entity = manager.findCategoryById(phone.getId());
        assertTrue(entity.isPresent());
        assertEquals(16, entity.get().getColor().getRed());
        assertEquals(16, entity.get().getColor().getGreen());
        assertEquals(16, entity.get().getColor().getBlue());
    }
    
    @Test
    public void categoryDeletionTest() {
        service.deleteCategoryById(rent.getId());
        assertEquals(1L, manager.countCategories());
    }
    
    @Test
    public void dependentCategoryDeletionTest() {
        Executable deletion = () -> service.deleteCategoryById(phone.getId());
        assertThrows(RuntimeException.class, deletion);
        assertEquals(2L, manager.countCategories());
        assertEquals(1L, manager.countExpenses());
        var expense = manager.findExpenseById(payment.getId());
        assertNotNull(expense.orElseThrow().getCategory());
    }
}
