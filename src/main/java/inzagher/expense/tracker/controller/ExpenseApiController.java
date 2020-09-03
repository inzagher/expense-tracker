package inzagher.expense.tracker.controller;

import inzagher.expense.tracker.model.Expense;
import inzagher.expense.tracker.service.ExpenseService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("expenses")
public class ExpenseApiController {
    private final ExpenseService expenseService;
    
    @Autowired
    public ExpenseApiController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    
    @GetMapping("get")
    public Expense get(UUID id) {
        return expenseService.get(id).orElse(null);
    }
}
