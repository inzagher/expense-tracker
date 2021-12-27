package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.query.ExpenseQueryFilter;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseSearchRepository {
    List<Expense> find(ExpenseQueryFilter filter);
    BigDecimal sumAmount(ExpenseQueryFilter filter);
}
