package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends
        JpaRepository<ExpenseEntity, Long>,
        JpaSpecificationExecutor<ExpenseEntity> {

    @Query("SELECT c.id, SUM(e.amount) " +
           "FROM ExpenseEntity e RIGHT JOIN e.category c " +
           "WHERE e.date >= :from AND e.date < :to " +
           "GROUP BY c.id ORDER BY c.id")
    List<Object[]> getCategoryReport(LocalDate from, LocalDate to);

    @Query("SELECT extract(month from e.date), SUM(e.amount) " +
           "FROM ExpenseEntity e WHERE e.date >= :yearStart AND e.date < :yearEnd " +
           "GROUP BY extract(month from e.date)")
    List<Object[]> getYearlyReport(LocalDate yearStart, LocalDate yearEnd);

    @Query("SELECT e.description FROM ExpenseEntity e " +
           "WHERE UPPER(e.description) LIKE UPPER(:pattern || '%')" +
           "GROUP BY e.description HAVING COUNT(*) >= :minCount ORDER BY COUNT(*) DESC")
    List<String> findDescriptionsByPattern(String pattern, long minCount);
}
