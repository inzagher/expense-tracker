package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer>, ExpenseRepositoryExtension {
}
