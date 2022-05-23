package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.dto.PersonDTO;
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
    public PersonDTO getPersonById(@NonNull Long id) {
        log.info("Find person with id {}", id);
        return personRepository.findById(id)
                .map(personMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Long createPerson(@NonNull PersonDTO dto) {
        log.info("Create person. Data: {}", dto);
        var entity = personMapper.toEntity(dto);
        return personRepository.save(entity).getId();
    }

    @Transactional
    public void editPerson(@NonNull Long id, @NonNull PersonDTO dto) {
        log.info("Edit person. Id: {}. Data: {}", id, dto);
        var entity = personRepository.getById(id);
        personMapper.mergeToExistingEntity(entity, dto);
        personRepository.save(entity);
    }

    @Transactional
    public void deletePersonById(@NonNull Long id) {
        log.info("Delete person with id {}", id);
        if (isAnyCorrelatedExpensePresent(id)) {
            var message = String.format("Person with id %d has expenses", id);
            throw new ExpenseTrackerException(message);
        }
        personRepository.deleteById(id);
    }

    private boolean isAnyCorrelatedExpensePresent(Long personId) {
        log.info("Find expenses with person id {}", personId);
        var criteria = new ExpenseSearchCriteria();
        var specification = new ExpenseSpecification(criteria);
        criteria.getPersonIdentifiers().add(personId);
        return !expenseRepository.findAll(specification).isEmpty();
    }
}
