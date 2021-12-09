package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.mapper.PersonMapper;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.PersonService;
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
public class PersonServiceTests {
    @Autowired
    private PersonService personService;
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
        assertEquals(2, personService.getAllPersons().size());
    }
    
    @Test
    public void personLoadingTest() {
        Optional<PersonDTO> loaded = personService.getPersonById(bob.getId());
        assertTrue(loaded.isPresent());
        assertEquals(bob.getId(), loaded.get().getId());
        assertEquals("BOB", loaded.get().getName());
    }
    
    @Test
    public void personCreationTest() {
        PersonDTO alice = new PersonDTO();
        alice.setName("ALICE");
        Integer storedRecordID = personService.storePerson(alice);
        assertNotNull(storedRecordID);
        assertEquals(3L, manager.countPersons());
        assertEquals("ALICE", manager.getPerson(storedRecordID).get().getName());
    }
    
    @Test
    public void personEditingTest() {
        PersonDTO person = mapper.toDTO(stan);
        person.setName("STANLEY");
        Integer storedRecordID = personService.storePerson(person);
        assertEquals(stan.getId(), storedRecordID);
        assertEquals(2L, manager.countPersons());
        assertEquals("STANLEY", manager.getPerson(stan.getId()).get().getName());
    }
    
    @Test
    public void personDeletionTest() {
        personService.deletePerson(bob.getId());
        assertEquals(1L, manager.countPersons());
    }
    
    @Test
    public void dependentPersonDeletionTest() {
        personService.deletePerson(stan.getId());
        assertEquals(1L, manager.countPersons());
        assertEquals(1L, manager.countExpenses());
        assertNull(manager.getExpense(purchase.getId()).get().getPerson());
    }
}
