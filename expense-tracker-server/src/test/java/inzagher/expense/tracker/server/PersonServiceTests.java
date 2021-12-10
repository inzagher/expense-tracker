package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.command.CreatePersonCommand;
import inzagher.expense.tracker.server.command.EditPersonCommand;
import inzagher.expense.tracker.server.mapper.PersonMapper;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ServiceRunner.class)
public class PersonServiceTests {
    @Autowired
    private PersonService service;
    @Autowired
    private TestDataManager manager;
    @Autowired
    private PersonMapper mapper;
    
    private Person bob;
    private Person stan;
    private Category clothes;
    private Expense purchase;
    
    @BeforeEach
    public void beforeEachTest() {
        bob = manager.storePerson("BOB");
        stan = manager.storePerson("STAN");
        clothes = manager.storeCategory("CLOTHES", "CLOTHES PURCHASE", 0, 0, 0, false);
        purchase = manager.storeExpense(2020, 10, 10, stan, clothes, 10.10D, "TEST CLOTHES PURCHASE");
        assertEquals(2L, manager.countPersons());
        assertEquals(1L, manager.countCategories());
        assertEquals(1L, manager.countExpenses());
    }
    
    @AfterEach
    public void afterEachTest() {
        manager.truncateAllTables();
        bob = null;
        stan = null;
        clothes = null;
        purchase = null;
    }
    
    @Test
    public void personListTest() {
        assertEquals(2, service.findAllPersons().size());
    }
    
    @Test
    public void personLoadingTest() {
        var person = service.getPersonById(bob.getId());
        assertEquals(bob.getId(), person.getId());
        assertEquals("BOB", person.getName());
    }
    
    @Test
    public void personCreationTest() {
        var command = new CreatePersonCommand("ALICE");
        var id = service.createPerson(command);
        assertEquals(3L, manager.countPersons());
        assertStoredPersonData(id, "ALICE");
    }
    
    @Test
    public void personEditingTest() {
        var command = new EditPersonCommand(stan.getId(), "STANLEY");
        service.editPerson(command);
        assertEquals(2L, manager.countPersons());
        assertStoredPersonData(command.getId(), "STANLEY");
    }
    
    @Test
    public void personDeletionTest() {
        service.deletePerson(bob.getId());
        assertEquals(1L, manager.countPersons());
    }
    
    @Test
    public void dependentPersonDeletionTest() {
        Executable deletion = () -> service.deletePerson(stan.getId());
        assertThrows(RuntimeException.class, deletion);
        var expense = manager.findExpenseById(purchase.getId());
        assertNotNull(expense.orElseThrow().getPerson());
    }

    public void assertStoredPersonData(Integer id, String expectedName) {
        var entity = manager.findPersonById(id);
        assertTrue(entity.isPresent());
        assertEquals(expectedName, entity.get().getName());
    }
}
