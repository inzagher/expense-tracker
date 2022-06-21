package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@Tag(name = "Expense operations")
public class ExpenseApiController {
    private final ExpenseService service;

    @GetMapping(path = "/api/expenses")
    @Operation(summary = "Find expenses")
    public Page<ExpenseDTO> find(
            @RequestParam(value = "persons[]", required = false) Long[] persons,
            @RequestParam(value = "categories[]", required = false) Long[] categories,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "dateExact", required = false) LocalDate dateExact,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
            @RequestParam(value = "amountExact", required = false) BigDecimal amountExact,
            @RequestParam(value = "amountFrom", required = false) BigDecimal amountFrom,
            @RequestParam(value = "amountTo", required = false) BigDecimal amountTo,
            @RequestParam(value = "description", required = false) String description,
            @NonNull final Pageable pageable) {
        var criteria = new ExpenseSearchCriteria();
        if (persons != null) {
            Arrays.asList(persons).forEach(criteria.getPersonIdentifiers()::add);
        }
        if (categories != null) {
            Arrays.asList(categories).forEach(criteria.getCategoryIdentifiers()::add);
        }
        criteria.setDateExact(dateExact);
        criteria.setDateFrom(dateFrom);
        criteria.setDateTo(dateTo);
        criteria.setAmountExact(amountExact);
        criteria.setAmountFrom(amountFrom);
        criteria.setAmountTo(amountTo);
        criteria.setDescriptionLike(description);
        return service.findExpenses(criteria, pageable);
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

    @PutMapping(path = "/api/expenses/{id}")
    @Operation(summary = "Edit expense")
    public void edit(@PathVariable Long id,
                     @RequestBody ExpenseDTO dto) {
        service.editExpense(id, dto);
    }
    
    @DeleteMapping(path = "/api/expenses/{id}")
    @Operation(summary = "Delete expense by id")
    public void deleteById(@PathVariable Long id) {
        service.deleteExpenseById(id);
    }
}
