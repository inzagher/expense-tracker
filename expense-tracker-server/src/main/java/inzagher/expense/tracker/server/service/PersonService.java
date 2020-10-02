package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.PersonRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public Optional<PersonDTO> getPerson(String id) {
        UUID uuid = UUID.fromString(id);
        return personRepository.findById(uuid).map(Person::toDTO);
    }
    
    public String storePerson(PersonDTO dto) {
        Person model;
        if(dto.getId() != null) {
            UUID id = UUID.fromString(dto.getId());
            Optional<Person> loadedPerson = personRepository.findById(id);
            model = loadedPerson.orElseThrow(() -> new RuntimeException("PERSON NOT FOUND"));
        } else {
            model = new Person();
        }
        model.setName(dto.getName());
        return personRepository.saveAndFlush(model).getId().toString();
    }
    
    public void deletePerson(String id) {
        UUID uuid = UUID.fromString(id);
        personRepository.deleteById(uuid);
    }
}
