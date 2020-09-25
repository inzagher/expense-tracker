package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;
    
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    
    public Optional<Expense> get(UUID id) {
        return expenseRepository.findById(id);
    }
    
    public void create(Expense expense) {
        throw new UnsupportedOperationException();
    }
    
    public void edit(Expense expense) {
        throw new UnsupportedOperationException();
    }
    
    public void delete(UUID id) {
        throw new UnsupportedOperationException();
    }
}
