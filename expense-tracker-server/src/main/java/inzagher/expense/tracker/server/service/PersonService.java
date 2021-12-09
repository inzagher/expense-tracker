package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.mapper.PersonMapper;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {
    private final ExpenseRepository expenseRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(personMapper::toDTO)
                .toList();
    }
    
    public Optional<PersonDTO> getPersonById(Integer id) {
        return personRepository.findById(id).map(personMapper::toDTO);
    }
    
    public Integer storePerson(PersonDTO dto) {
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
    
    public void deletePerson(Integer id) {
        resetDependentExpenses(id);
        personRepository.deleteById(id);
    }
    
    private void resetDependentExpenses(Integer personID) {
        ExpenseFilter filter = new ExpenseFilter();
        filter.getPersonIdentifiers().add(personID);
        List<Expense> list = expenseRepository.find(filter);
        list.forEach(e -> e.setPerson(null));
        expenseRepository.saveAll(list);
        expenseRepository.flush();
    }
}
