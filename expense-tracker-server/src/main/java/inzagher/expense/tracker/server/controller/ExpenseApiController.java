package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.service.ExpenseService;
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
    public ExpenseDTO get(String id) {
        return expenseService.get(id).orElse(null);
    }
}
