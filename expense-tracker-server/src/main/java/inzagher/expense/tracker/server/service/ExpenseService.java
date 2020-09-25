package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    
    public Optional<ExpenseDTO> get(String id) {
        UUID uuid = UUID.fromString(id);
        return expenseRepository.findById(uuid).map(this::toDTO);
    }
    
    public String create(ExpenseDTO dto) {
        Expense model = toModel(dto);
        return this.expenseRepository.saveAndFlush(model).getId().toString();
    }
    
    public void edit(ExpenseDTO dto) {
        Expense model = toModel(dto);
        this.expenseRepository.saveAndFlush(model);
    }
    
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        this.expenseRepository.deleteById(uuid);
    }
    
    private ExpenseDTO toDTO(Expense model) {
        ExpenseDTO dto = new ExpenseDTO();
        return dto;
    }
    
    private Expense toModel(ExpenseDTO dto) {
        Expense model = new Expense();
        return model;
    }
}
