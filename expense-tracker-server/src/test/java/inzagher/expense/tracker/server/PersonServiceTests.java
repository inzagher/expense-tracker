package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import inzagher.expense.tracker.server.service.PersonService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest(classes = {ServiceRunner.class})
public class PersonServiceTests {
    @Autowired
    private PersonService personService;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PersonRepository personRepository;
    
    private Person bob;
    private Person stan;
    private Category clothes;
    private Expense purchase;
    
    @BeforeEach
    public void beforeEachTest() {
        bob = new Person();
        bob.setName("BOB");
        bob = personRepository.saveAndFlush(bob);
        
        stan = new Person();
        stan.setName("STAN");
        stan = personRepository.saveAndFlush(stan);
        assertEquals(personRepository.count(), 2L);
        
        clothes = new Category();
        clothes.setName("CLOTHES");
        clothes.setColor(new Color((byte)0, (byte)0, (byte)0));
        clothes.setDescription("CLOTHES PURCHASE");
        clothes = categoryRepository.saveAndFlush(clothes);
        
        purchase = new Expense();
        purchase.setDate(LocalDate.now());
        purchase.setAmount(10.1F);
        purchase.setPerson(stan);
        purchase.setCategory(clothes);
        purchase.setDescription("EXPENSE TESTING");
        purchase = expenseRepository.saveAndFlush(purchase);
    }
    
    @AfterEach
    public void afterEachTest() {
        expenseRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
        bob = null;
        stan = null;
        clothes = null;
        purchase = null;
    }
    
    @Test
    public void personListTest() {
        assertEquals(personService.getAllPersons().size(), 2);
    }
    
    @Test
    public void personLoadingTest() {
        String id = bob.getId().toString();
        Optional<PersonDTO> loaded = personService.getPersonById(id);
        assertTrue(loaded.isPresent());
        assertEquals(loaded.get().getId(), id);
        assertEquals(loaded.get().getName(), "BOB");
    }
    
    @Test
    public void personCreationTest() {
        PersonDTO alice = new PersonDTO();
        alice.setName("ALICE");
        String storedRecordID = personService.storePerson(alice);
        assertNotNull(storedRecordID);
        assertEquals(personRepository.count(), 3L);
        assertEquals(personRepository.getOne(UUID.fromString(storedRecordID)).getName(), "ALICE");
    }
    
    @Test
    public void personEditingTest() {
        PersonDTO person = stan.toDTO();
        person.setName("STANLEY");
        String storedRecordID = personService.storePerson(person);
        assertEquals(storedRecordID, stan.getId().toString());
        assertEquals(personRepository.count(), 2L);
        assertEquals(personRepository.getOne(stan.getId()).getName(), "STANLEY");
    }
    
    @Test
    public void personDeletionTest() {
        String id = bob.getId().toString();
        personService.deletePerson(id);
        assertEquals(personRepository.count(), 1L);
    }
    
    @Test
    public void dependentPersonDeletionTest() {
        String id = stan.getId().toString();
        personService.deletePerson(id);
        assertEquals(personRepository.count(), 1L);
        assertEquals(expenseRepository.count(), 1L);
        assertNull(expenseRepository.getOne(purchase.getId()).getPerson());
    }
}
