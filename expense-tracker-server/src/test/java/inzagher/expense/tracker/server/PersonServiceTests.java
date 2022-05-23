package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import inzagher.expense.tracker.server.model.mapper.PersonMapper;
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
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class PersonServiceTests {
    @Autowired
    private PersonService service;
    @Autowired
    private TestDataManager manager;
    @Autowired
    private PersonMapper mapper;
    
    private PersonEntity bob;
    private PersonEntity stan;
    private CategoryEntity clothes;
    private ExpenseEntity purchase;
    
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
    void personListTest() {
        assertEquals(2, service.findAllPersons().size());
    }
    
    @Test
    void personLoadingTest() {
        var person = service.getPersonById(bob.getId());
        assertEquals(bob.getId(), person.getId());
        assertEquals("BOB", person.getName());
    }
    
    @Test
    void personCreationTest() {
        var dto = new PersonDTO(null, "ALICE");
        var id = service.createPerson(dto);
        assertEquals(3L, manager.countPersons());
        assertStoredPersonData(id, "ALICE");
    }
    
    @Test
    void personEditingTest() {
        var dto = new PersonDTO(stan.getId(), "STANLEY");
        service.editPerson(stan.getId(), dto);
        assertEquals(2L, manager.countPersons());
        assertStoredPersonData(dto.getId(), "STANLEY");
    }
    
    @Test
    void personDeletionTest() {
        service.deletePersonById(bob.getId());
        assertEquals(1L, manager.countPersons());
    }
    
    @Test
    void dependentPersonDeletionTest() {
        Executable deletion = () -> service.deletePersonById(stan.getId());
        assertThrows(RuntimeException.class, deletion);
        assertEquals(2L, manager.countPersons());
        assertEquals(1L, manager.countExpenses());
        var expense = manager.findExpenseById(purchase.getId());
        assertNotNull(expense.orElseThrow().getPerson());
    }

    public void assertStoredPersonData(Long id, String expectedName) {
        var entity = manager.findPersonById(id);
        assertTrue(entity.isPresent());
        assertEquals(expectedName, entity.get().getName());
    }
}
