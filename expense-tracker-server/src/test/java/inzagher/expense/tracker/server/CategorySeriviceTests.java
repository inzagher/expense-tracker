package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.dto.ColorDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import inzagher.expense.tracker.server.service.CategoryService;
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
public class CategorySeriviceTests {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PersonRepository personRepository;
    
    private Expense payment;
    private Category phone;
    private Category rent;
    private Person eric;
    
    @BeforeEach
    public void beforeEachTest() {
        eric = new Person();
        eric.setName("ERIC");
        eric = personRepository.saveAndFlush(eric);
        assertEquals(1L, personRepository.count());
        
        phone = new Category();
        phone.setName("PHONE");
        phone.setDescription("MONTHLY PHONE BILL");
        phone.setColor(new Color((byte)0, (byte)0, (byte)0));
        phone = categoryRepository.saveAndFlush(phone);
        assertEquals(1L, categoryRepository.count());
        
        rent = new Category();
        rent.setName("RENT");
        rent.setDescription("MONTHLY RENT BILL");
        rent.setColor(new Color((byte)128, (byte)128, (byte)128));
        rent = categoryRepository.saveAndFlush(rent);
        assertEquals(2L, categoryRepository.count());
        
        payment = new Expense();
        payment.setDate(LocalDate.now());
        payment.setAmount(1000.5F);
        payment.setPerson(eric);
        payment.setCategory(phone);
        payment.setDescription("TEST BILL PAYMENT");
        payment = expenseRepository.saveAndFlush(payment);
        assertEquals(1L, expenseRepository.count());
    }
    
    @AfterEach
    public void afterEachTest() {
        expenseRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
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
        assertEquals(3L, categoryRepository.count());
        assertEquals("EDUCATION", categoryRepository.getOne(storedRecordID).getName());
    }
    
    @Test
    public void categoryEditingTest() {
        CategoryDTO category = phone.toDTO();
        category.setColor(new ColorDTO((byte)16, (byte)16, (byte)16));
        UUID storedRecordID = categoryService.storeCategory(category);
        assertEquals(phone.getId(), storedRecordID);
        assertEquals(2L, categoryRepository.count());
        assertEquals(16, categoryRepository.getOne(phone.getId()).getColor().getRed());
    }
    
    @Test
    public void categoryDeletionTest() {
        categoryService.deleteCategory(phone.getId());
        assertEquals(1L, personRepository.count());
    }
    
    @Test
    public void dependentCategoryDeletionTest() {
        categoryService.deleteCategory(phone.getId());
        assertEquals(1L, personRepository.count());
        assertEquals(1L, expenseRepository.count());
        assertNull(expenseRepository.getOne(payment.getId()).getCategory());
    }
}
