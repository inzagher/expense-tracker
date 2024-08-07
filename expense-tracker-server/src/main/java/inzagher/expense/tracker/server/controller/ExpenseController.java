package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.dto.ExpenseFilterDTO;
import inzagher.expense.tracker.server.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
@Tag(name = "Expense operations")
public class ExpenseController {
    private final ExpenseService service;

    @GetMapping
    @Operation(summary = "Find expenses")
    public Page<ExpenseDTO> find(
            @NonNull final ExpenseFilterDTO filter,
            @NonNull final Pageable pageable) {
        return service.findExpenses(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get expense by id")
    public ExpenseDTO getById(@PathVariable Long id) {
        return service.getExpenseById(id);
    }

    @PostMapping
    @Operation(summary = "Create expense")
    public void create(@RequestBody ExpenseDTO dto) {
        service.createExpense(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit expense")
    public void edit(@PathVariable Long id,
                     @RequestBody ExpenseDTO dto) {
        service.editExpense(id, dto);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete expense by id")
    public void deleteById(@PathVariable Long id) {
        service.deleteExpenseById(id);
    }
}
