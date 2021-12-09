package inzagher.expense.tracker.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseDescriptionRepository {
    @Query("SELECT e.description FROM Expense e " +
           "WHERE e.description LIKE '%' || :pattern " +
           "GROUP BY e.description HAVING COUNT(*) >= :minCount")
    List<String> findDescriptionsByPattern(String pattern, int minCount);
}
