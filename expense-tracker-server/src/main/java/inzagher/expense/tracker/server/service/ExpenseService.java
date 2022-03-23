package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.command.CreateExpenseCommand;
import inzagher.expense.tracker.server.model.command.EditExpenseCommand;
import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import inzagher.expense.tracker.server.model.exception.NotFoundException;
import inzagher.expense.tracker.server.model.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.model.query.ExpenseQueryFilter;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Transactional
    public List<ExpenseDTO> findExpenses(@NonNull ExpenseQueryFilter filter) {
        log.info("Query expenses with filter");
        return expenseRepository.find(filter).stream()
                .map(expenseMapper::toDTO)
                .toList();
    }

    @Transactional
    public ExpenseDTO getExpenseById(@NonNull Integer id) {
        log.info("Find expense with id {}", id);
        return expenseRepository.findById(id)
                .map(expenseMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Integer createExpense(@NonNull CreateExpenseCommand command) {
        log.info("Create expense. Command: {}", command);
        var entity = new ExpenseEntity(command);
        return expenseRepository.save(entity).getId();
    }

    @Transactional
    public void editExpense(@NonNull EditExpenseCommand command) {
        log.info("Edit expense. Command: {}", command);
        var entity = expenseRepository
                .findById(command.getId())
                .orElseThrow(NotFoundException::new);
        entity.edit(command);
        expenseRepository.save(entity);
    }

    @Transactional
    public void deleteExpenseById(@NonNull Integer id) {
        log.info("Delete expense with id {}", id);
        expenseRepository.deleteById(id);
    }
}
