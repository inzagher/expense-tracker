package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends
        JpaRepository<ExpenseEntity, Long>,
        JpaSpecificationExecutor<ExpenseEntity> {

    @NonNull
    @Override
    @EntityGraph(attributePaths = { "category", "person" })
    Optional<ExpenseEntity> findById(@NonNull Long id);

    @NonNull
    @Override
    @EntityGraph(attributePaths = { "category", "person" })
    Page<ExpenseEntity> findAll(@NonNull Specification<ExpenseEntity> spec,
                                @NonNull Pageable pageable);

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
