package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.PersonRepository;
import inzagher.expense.tracker.server.service.PersonService;
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
    private PersonRepository personRepository;
    
    private Person bob;
    private Person stan;
    
    @BeforeEach
    public void beforeEachTest() {
        bob = new Person();
        bob.setName("BOB");
        bob = personRepository.saveAndFlush(bob);
        
        stan = new Person();
        stan.setName("STAN");
        stan = personRepository.saveAndFlush(stan);
        assertEquals(personRepository.count(), 2L);
    }
    
    @AfterEach
    public void afterEachTest() {
        personRepository.deleteAllInBatch();
        bob = null;
        stan = null;
    }
    
    @Test
    public void personLoadingTest() {
        String id = bob.getId().toString();
        Optional<PersonDTO> loaded = personService.getPerson(id);
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
}
