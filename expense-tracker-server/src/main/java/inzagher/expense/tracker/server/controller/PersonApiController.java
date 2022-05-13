package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.model.mapper.PersonMapper;
import inzagher.expense.tracker.server.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonApiController {
    private final PersonService service;
    private final PersonMapper mapper;
    
    @GetMapping(path = "/api/persons")
    public List<PersonDTO> findAll() {
        return service.findAllPersons();
    }
    
    @GetMapping(path = "/api/persons/{id}")
    public PersonDTO getById(@PathVariable Long id) {
        return service.getPersonById(id);
    }

    @PostMapping(path = "/api/persons")
    public void create(@RequestBody PersonDTO dto) {
        service.createPerson(dto);
    }

    @PutMapping(path = "/api/persons")
    public void edit(@RequestBody PersonDTO dto) {
        service.editPerson(dto);
    }
    
    @DeleteMapping(path = "/api/persons/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deletePersonById(id);
    }
}
