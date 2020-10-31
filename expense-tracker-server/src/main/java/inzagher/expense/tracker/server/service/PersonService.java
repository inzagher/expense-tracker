package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final ExpenseRepository expenseRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(ExpenseRepository expenseRepository,
            PersonRepository personRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.personRepository = personRepository;
    }
    
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(Person::toDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<PersonDTO> getPersonById(UUID id) {
        return personRepository.findById(id).map(Person::toDTO);
    }
    
    public UUID storePerson(PersonDTO dto) {
        Person model;
        if (dto.getId() != null) {
            Optional<Person> loadedPerson = personRepository.findById(dto.getId());
            model = loadedPerson.orElseThrow(() -> new RuntimeException("PERSON NOT FOUND"));
        } else {
            model = new Person();
        }
        model.setName(dto.getName());
        return personRepository.saveAndFlush(model).getId();
    }
    
    public void deletePerson(UUID id) {
        resetDependentExpenses(id);
        personRepository.deleteById(id);
    }
    
    private void resetDependentExpenses(UUID personID) {
        ExpenseFilter filter = new ExpenseFilter();
        filter.getPersonIdentifiers().add(personID);
        List<Expense> list = expenseRepository.find(filter);
        list.forEach(e -> e.setPerson(null));
        expenseRepository.saveAll(list);
        expenseRepository.flush();
    }
}
