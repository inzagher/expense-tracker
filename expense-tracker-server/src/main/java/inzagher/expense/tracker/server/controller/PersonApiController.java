package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.service.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonApiController {
    private final PersonService personService;
    
    @Autowired
    public PersonApiController(PersonService personService) {
        this.personService = personService;
    }
    
    @GetMapping(path = "/api/persons")
    public List<PersonDTO> list() {
        return personService.getAllPersons();
    }
    
    @GetMapping(path = "/api/persons/{id}")
    public PersonDTO read(@PathVariable Integer id) {
        return personService.getPersonById(id).orElse(null);
    }

    @PostMapping(path = "/api/persons")
    public void save(@RequestBody PersonDTO person) {
        personService.storePerson(person);
    }
    
    @DeleteMapping(path = "/api/persons/{id}")
    public void delete(@PathVariable Integer id) {
        personService.deletePerson(id);
    }
}
