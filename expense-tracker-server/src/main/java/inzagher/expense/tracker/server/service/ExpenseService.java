package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.exception.NotFoundException;
import inzagher.expense.tracker.server.model.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.model.specification.ExpenseSpecification;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Transactional
    public Page<ExpenseDTO> findExpenses(@NonNull ExpenseSearchCriteria criteria,
                                         @NonNull Pageable pageable) {
        log.info("Query expenses with filter");
        var specification = new ExpenseSpecification(criteria);
        return expenseRepository.findAll(specification, pageable)
                .map(expenseMapper::toDTO);
    }

    @Transactional
    public ExpenseDTO getExpenseById(@NonNull Long id) {
        log.info("Find expense with id {}", id);
        return expenseRepository.findById(id)
                .map(expenseMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Long createExpense(@NonNull ExpenseDTO dto) {
        log.info("Create expense. Data: {}", dto);
        var entity = expenseMapper.toEntity(dto);
        return expenseRepository.save(entity).getId();
    }

    @Transactional
    public void editExpense(@NonNull Long id, @NonNull ExpenseDTO dto) {
        log.info("Edit expense. Id: {}. Data: {}", id, dto);
        var entity = expenseRepository.getById(id);
        expenseMapper.mergeToExistingEntity(entity, dto);
        expenseRepository.save(entity);
    }

    @Transactional
    public void deleteExpenseById(@NonNull Long id) {
        log.info("Delete expense with id {}", id);
        expenseRepository.deleteById(id);
    }
}
