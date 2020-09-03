package inzagher.expense.tracker.repository;

import inzagher.expense.tracker.model.Expense;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
