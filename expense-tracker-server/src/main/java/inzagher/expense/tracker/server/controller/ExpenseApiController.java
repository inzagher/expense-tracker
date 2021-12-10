package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.dto.ExpenseFilterDTO;
import inzagher.expense.tracker.server.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseApiController {
    private final ExpenseService service;
    private final ExpenseMapper mapper;

    @GetMapping(path = "/api/expenses")
    public List<ExpenseDTO> find(ExpenseFilterDTO filter) {
        return service.findExpenses(filter);
    }

    @GetMapping(path = "/api/expenses/{id}")
    public ExpenseDTO getById(@PathVariable Integer id) {
        return service.getExpenseById(id);
    }

    @PostMapping(path = "/api/expenses")
    public void create(@RequestBody ExpenseDTO expense) {
        service.createExpense(mapper.toCreateCommand(expense));
    }

    @PutMapping(path = "/api/expenses")
    public void edit(@RequestBody ExpenseDTO expense) {
        service.editExpense(mapper.toEditCommand(expense));
    }
    
    @DeleteMapping(path = "/api/expenses/{id}")
    public void deleteById(@PathVariable Integer id) {
        service.deleteExpenseById(id);
    }
}
