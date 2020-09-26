package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import inzagher.expense.tracker.server.service.ExpenseService;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServerConfiguration.class})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ExpenseServiceTests {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PersonRepository personRepository;
    
    private UUID tomID;
    private UUID foodCategoryID;
    
    @Before
    public void beforeEach() {
        Category food = new Category();
        food.setName("FOOD");
        food.setColor(new Color((byte)0, (byte)0, (byte)0));
        food.setDescription("DAILY FOOD EXPENSES");
        foodCategoryID = categoryRepository.saveAndFlush(food).getId();
        
        Person tom = new Person();
        tom.setName("TOM");
        tomID = personRepository.saveAndFlush(tom).getId();
    }
    
    @Test
    public void expenseStoreTest() {
        ExpenseDTO expense = new ExpenseDTO();
        expense.setDate(LocalDate.now());
        expense.setAmmount(12.1f);
        expense.setPersonId(tomID.toString());
        expense.setCategoryId(foodCategoryID.toString());
        expense.setDescription("EXPENSE TESTING");
        assertNotNull(expenseService.storeExpense(expense));
    }
}