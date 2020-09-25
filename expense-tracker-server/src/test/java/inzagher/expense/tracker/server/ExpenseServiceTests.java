package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.model.State;
import inzagher.expense.tracker.server.repository.PersonRepository;
import inzagher.expense.tracker.server.repository.StateRepository;
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
    private StateRepository stateRepository;
    @Autowired
    private PersonRepository personRepository;
    
    private UUID tomID;
    private UUID foodStateID;
    
    @Before
    public void beforeEach() {
        State food = new State();
        food.setName("FOOD");
        food.setColor(new Color((byte)0, (byte)0, (byte)0));
        food.setDescription("DAILY FOOD EXPENSES");
        foodStateID = stateRepository.saveAndFlush(food).getId();
        
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
        expense.setStateId(foodStateID.toString());
        expense.setDescription("EXPENSE TESTING");
        assertNotNull(expenseService.create(expense));
    }
}