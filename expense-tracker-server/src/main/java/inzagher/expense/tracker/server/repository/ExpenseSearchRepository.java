package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import inzagher.expense.tracker.server.model.query.ExpenseQueryFilter;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseSearchRepository {
    List<ExpenseEntity> find(ExpenseQueryFilter filter);
    BigDecimal sumAmount(ExpenseQueryFilter filter);
}
