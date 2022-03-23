package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.command.CreatePersonCommand;
import inzagher.expense.tracker.server.model.command.EditPersonCommand;
import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import inzagher.expense.tracker.server.model.exception.ExpenseTrackerException;
import inzagher.expense.tracker.server.model.exception.NotFoundException;
import inzagher.expense.tracker.server.model.mapper.PersonMapper;
import inzagher.expense.tracker.server.model.specification.ExpenseSpecification;
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
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Integer createPerson(@NonNull CreatePersonCommand command) {
        log.info("Create person. Command: {}", command);
        var entity = new PersonEntity(command);
        return personRepository.save(entity).getId();
    }

    @Transactional
    public void editPerson(@NonNull EditPersonCommand command) {
        log.info("Edit person. Command: {}", command);
        var entity = personRepository
                .findById(command.getId())
                .orElseThrow(NotFoundException::new);
        entity.edit(command);
        personRepository.save(entity);
    }

    @Transactional
    public void deletePersonById(@NonNull Integer id) {
        log.info("Delete person with id {}", id);
        if (isAnyCorrelatedExpensePresent(id)) {
            var message = String.format("Person with id %d has expenses", id);
            throw new ExpenseTrackerException(message);
        }
        personRepository.deleteById(id);
    }

    private boolean isAnyCorrelatedExpensePresent(Integer personId) {
        log.info("Find expenses with person id {}", personId);
        var criteria = new ExpenseSearchCriteria();
        var specification = new ExpenseSpecification(criteria);
        criteria.getPersonIdentifiers().add(personId);
        return !expenseRepository.findAll(specification).isEmpty();
    }
}
