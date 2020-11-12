package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.CategorySummaryItem;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import java.util.List;

public interface ExpenseRepositoryExtension {
    List<Expense> find(ExpenseFilter filter);
    List<CategorySummaryItem> getCategorySummary(int year, int month);
    Float getTotalMonthAmount(int year, int month);
}
