package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.model.MonthlyReportItem;
import inzagher.expense.tracker.server.model.YearlyReportItem;
import java.util.List;

public interface ExpenseRepositoryExtension {
    List<Expense> find(ExpenseFilter filter);
    List<MonthlyReportItem> createMonthlyReport(int year, int month);
    List<YearlyReportItem> createYearlyReport(int year);
}
