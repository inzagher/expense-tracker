package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends
        JpaRepository<ExpenseEntity, Integer>,
        ExpenseSearchRepository,
        ExpenseDescriptionRepository {
}
