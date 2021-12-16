package inzagher.expense.tracker.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseDescriptionRepository {
    @Query("SELECT e.description FROM Expense e " +
           "WHERE UPPER(e.description) LIKE UPPER(:pattern || '%')" +
           "GROUP BY e.description HAVING COUNT(*) >= :minCount")
    List<String> findDescriptionsByPattern(String pattern, long minCount);
}
