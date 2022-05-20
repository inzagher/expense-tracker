package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Person operations")
public class PersonApiController {
    private final PersonService service;

    @GetMapping(path = "/api/persons")
    @Operation(summary = "Find all persons")
    public List<PersonDTO> findAll() {
        return service.findAllPersons();
    }
    
    @GetMapping(path = "/api/persons/{id}")
    @Operation(summary = "Get person by id")
    public PersonDTO getById(@PathVariable Long id) {
        return service.getPersonById(id);
    }

    @PostMapping(path = "/api/persons")
    @Operation(summary = "Create person")
    public void create(@RequestBody PersonDTO dto) {
        service.createPerson(dto);
    }

    @PutMapping(path = "/api/persons")
    @Operation(summary = "Edit person")
    public void edit(@RequestBody PersonDTO dto) {
        service.editPerson(dto);
    }
    
    @DeleteMapping(path = "/api/persons/{id}")
    @Operation(summary = "Delete person by id")
    public void deleteById(@PathVariable Long id) {
        service.deletePersonById(id);
    }
}
