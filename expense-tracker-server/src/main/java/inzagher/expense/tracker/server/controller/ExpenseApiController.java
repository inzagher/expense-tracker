package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.dto.ExpenseFilterDTO;
import inzagher.expense.tracker.server.service.ExpenseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseApiController {
    private final ExpenseService expenseService;
    
    @Autowired
    public ExpenseApiController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    
    @GetMapping(path = "/api/expenses/{id}")
    public ExpenseDTO read(@PathVariable Integer id) {
        return expenseService.getExpenseById(id).orElse(null);
    }

    @PostMapping(path = "/api/expenses/find")
    public List<ExpenseDTO> find(ExpenseFilterDTO filter) {
        return expenseService.findExpenses(filter);
    }

    @PostMapping(path = "/api/expenses")
    public void save(@RequestBody ExpenseDTO expense) {
        expenseService.storeExpense(expense);
    }
    
    @DeleteMapping(path = "/api/expenses/{id}")
    public void delete(@PathVariable Integer id) {
        expenseService.deleteExpense(id);
    }
}
