package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseSearchRepository {
    List<Expense> find(ExpenseFilter filter);
    BigDecimal sum(ExpenseFilter filter);
}
