package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.model.query.ExpenseQueryFilter;
import inzagher.expense.tracker.server.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseApiController {
    private final ExpenseService service;
    private final ExpenseMapper mapper;

    @GetMapping(path = "/api/expenses")
    public List<ExpenseDTO> find(
            @RequestParam(value = "persons", required = false) List<Integer> persons,
            @RequestParam(value = "categories", required = false) List<Integer> categories,
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
        var filter = new ExpenseQueryFilter();
        if (persons != null) {
            persons.forEach(filter.getPersonIdentifiers()::add);
        }
        if (categories != null) {
            categories.forEach(filter.getCategoryIdentifiers()::add);
        }
        filter.setDateExact(dateExact);
        filter.setDateFrom(dateFrom);
        filter.setDateTo(dateTo);
        filter.setAmountExact(amountExact);
        filter.setAmountFrom(amountFrom);
        filter.setAmountTo(amountTo);
        filter.setDescriptionLike(description);
        return service.findExpenses(filter);
    }

    @GetMapping(path = "/api/expenses/{id}")
    public ExpenseDTO getById(@PathVariable Integer id) {
        return service.getExpenseById(id);
    }

    @PostMapping(path = "/api/expenses")
    public void create(@RequestBody ExpenseDTO expense) {
        service.createExpense(mapper.toCreateCommand(expense));
    }

    @PutMapping(path = "/api/expenses")
    public void edit(@RequestBody ExpenseDTO expense) {
        service.editExpense(mapper.toEditCommand(expense));
    }
    
    @DeleteMapping(path = "/api/expenses/{id}")
    public void deleteById(@PathVariable Integer id) {
        service.deleteExpenseById(id);
    }
}
