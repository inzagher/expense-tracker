package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.command.CreatePersonCommand;
import inzagher.expense.tracker.server.command.EditPersonCommand;
import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.mapper.PersonMapper;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final ExpenseRepository expenseRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Transactional
    public List<PersonDTO> findAllPersons() {
        log.info("Find all persons");
        return personRepository.findAll().stream()
                .map(personMapper::toDTO)
                .toList();
    }

    @Transactional
    public PersonDTO getPersonById(@NonNull Integer id) {
        log.info("Find person with id {}", id);
        return personRepository.findById(id)
                .map(personMapper::toDTO)
                .orElseThrow();
    }

    @Transactional
    public Integer createPerson(@NonNull CreatePersonCommand command) {
        log.info("Create person. Command: {}", command);
        var entity = new Person(command);
        return personRepository.save(entity).getId();
    }

    @Transactional
    public void editPerson(@NonNull EditPersonCommand command) {
        log.info("Edit person. Command: {}", command);
        var entity = personRepository
                .findById(command.getId())
                .orElseThrow();
        entity.edit(command);
        personRepository.save(entity);
    }

    @Transactional
    public void deletePersonById(@NonNull Integer id) {
        log.info("Delete person with id {}", id);
        if (isAnyExpensePresent(id)) {
            var message = String.format("Person with id %d has expenses", id);
            throw new RuntimeException(message);
        }
        personRepository.deleteById(id);
    }

    private Boolean isAnyExpensePresent(Integer personId) {
        log.info("Find expenses with person id {}", personId);
        var filter = new ExpenseFilter();
        filter.getPersonIdentifiers().add(personId);
        return expenseRepository.find(filter).size() > 0;
    }
}
