package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.model.Expense;
import java.util.List;

public interface ExpenseRepositoryExtension {
    List<Expense> find(ExpenseFilter filter);
}
