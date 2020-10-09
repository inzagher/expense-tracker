package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    
    @GetMapping(path = "/api/expenses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExpenseDTO read(@PathVariable String id) {
        return expenseService.getExpenseById(id).orElse(null);
    }

    @PostMapping(path = "/api/expenses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody ExpenseDTO expense) {
        expenseService.storeExpense(expense);
    }
    
    @DeleteMapping(path = "/api/expenses/{id}")
    public void delete(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }
}
