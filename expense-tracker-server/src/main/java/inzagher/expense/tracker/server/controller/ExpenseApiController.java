package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("expenses")
public class ExpenseApiController {
    private final ExpenseService expenseService;
    
    @Autowired
    public ExpenseApiController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    
    @GetMapping("/{id}")
    public ExpenseDTO read(@PathVariable String id) {
        return expenseService.getExpense(id).orElse(null);
    }

    @PostMapping("/")
    public void save(@RequestBody ExpenseDTO expense) {
        expenseService.storeExpense(expense);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }
}
