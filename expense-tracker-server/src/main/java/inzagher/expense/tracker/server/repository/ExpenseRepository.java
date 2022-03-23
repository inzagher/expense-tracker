package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends
        JpaRepository<ExpenseEntity, Integer>,
        JpaSpecificationExecutor<ExpenseEntity> {

    @Query("SELECT SUM(e.amount) FROM ExpenseEntity e " +
           "WHERE e.category.id = :categoryId " +
           "AND e.date >= :from AND e.date < :to")
    Optional<BigDecimal> getTotalAmountForCategory(Integer categoryId, LocalDate from, LocalDate to);

    @Query("SELECT SUM(e.amount) FROM ExpenseEntity e " +
           "WHERE e.date >= :from AND e.date < :to")
    Optional<BigDecimal> getTotalAmount(LocalDate from, LocalDate to);

    @Query("SELECT e.description FROM ExpenseEntity e " +
           "WHERE UPPER(e.description) LIKE UPPER(:pattern || '%')" +
           "GROUP BY e.description HAVING COUNT(*) >= :minCount")
    List<String> findDescriptionsByPattern(String pattern, long minCount);
}
