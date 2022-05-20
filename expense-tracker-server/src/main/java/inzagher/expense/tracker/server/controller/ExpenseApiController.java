package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Expense operations")
public class ExpenseApiController {
    private final ExpenseService service;

    @GetMapping(path = "/api/expenses")
    @Operation(summary = "Find expenses")
    public List<ExpenseDTO> find(
            @RequestParam(value = "persons", required = false) List<Long> persons,
            @RequestParam(value = "categories", required = false) List<Long> categories,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "date_exact", required = false) LocalDate dateExact,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "date_from", required = false) LocalDate dateFrom,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "date_to", required = false) LocalDate dateTo,
            @RequestParam(value = "amount_exact", required = false) BigDecimal amountExact,
            @RequestParam(value = "amount_from", required = false) BigDecimal amountFrom,
            @RequestParam(value = "amount_to", required = false) BigDecimal amountTo,
            @RequestParam(value = "description", required = false) String description) {
        var criteria = new ExpenseSearchCriteria();
        if (persons != null) {
            persons.forEach(criteria.getPersonIdentifiers()::add);
        }
        if (categories != null) {
            categories.forEach(criteria.getCategoryIdentifiers()::add);
        }
        criteria.setDateExact(dateExact);
        criteria.setDateFrom(dateFrom);
        criteria.setDateTo(dateTo);
        criteria.setAmountExact(amountExact);
        criteria.setAmountFrom(amountFrom);
        criteria.setAmountTo(amountTo);
        criteria.setDescriptionLike(description);
        return service.findExpenses(criteria);
    }

    @GetMapping(path = "/api/expenses/{id}")
    @Operation(summary = "Get expense by id")
    public ExpenseDTO getById(@PathVariable Long id) {
        return service.getExpenseById(id);
    }

    @PostMapping(path = "/api/expenses")
    @Operation(summary = "Create expense")
    public void create(@RequestBody ExpenseDTO dto) {
        service.createExpense(dto);
    }

    @PutMapping(path = "/api/expenses")
    @Operation(summary = "Edit expense")
    public void edit(@RequestBody ExpenseDTO dto) {
        service.editExpense(dto);
    }
    
    @DeleteMapping(path = "/api/expenses/{id}")
    @Operation(summary = "Delete expense by id")
    public void deleteById(@PathVariable Long id) {
        service.deleteExpenseById(id);
    }
}
