package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import inzagher.expense.tracker.server.model.query.ExpenseQueryFilter;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseSearchRepositoryImpl implements ExpenseSearchRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<ExpenseEntity> find(@NonNull ExpenseQueryFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(ExpenseEntity.class);
        var expense = criteria.from(ExpenseEntity.class);
        var predicates = createPredicates(cb, expense, filter);
        criteria.where(predicates.toArray(Predicate[]::new));
        criteria.select(expense);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public BigDecimal sumAmount(@NonNull ExpenseQueryFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(BigDecimal.class);
        var expense = criteria.from(ExpenseEntity.class);
        var predicates = createPredicates(cb, expense, filter);
        criteria.where(predicates.toArray(Predicate[]::new));
        criteria.select(cb.sum(expense.get("amount")));
        var result = entityManager.createQuery(criteria).getSingleResult();
        return result == null ? BigDecimal.ZERO : result;
    }
    
    private List<Predicate> createPredicates(CriteriaBuilder cb, Root<ExpenseEntity> expense, ExpenseQueryFilter filter) {
        var predicates = new ArrayList<Predicate>();
        if (filter.getCategoryIdentifiers() != null && !filter.getCategoryIdentifiers().isEmpty()) {
            var in = cb.in(expense.get("category").get("id"));
            filter.getCategoryIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (filter.getPersonIdentifiers() != null && !filter.getPersonIdentifiers().isEmpty()) {
            var in = cb.in(expense.get("person").get("id"));
            filter.getPersonIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (filter.getDateExact() != null) {
            predicates.add(cb.equal(expense.get("date"), filter.getDateExact()));
        }
        if (filter.getDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(expense.get("date"), filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(expense.get("date"), filter.getDateTo()));
        }
        if (filter.getAmountExact() != null) {
            predicates.add(cb.equal(expense.get("amount"), filter.getAmountExact()));
        }
        if (filter.getAmountFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(expense.get("amount"), filter.getAmountFrom()));
        }
        if (filter.getAmountTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(expense.get("amount"), filter.getAmountTo()));
        }
        if (filter.getDescriptionLike() != null) {
            predicates.add(cb.equal(expense.get("description"), filter.getDescriptionLike()));
        }
        return predicates;
    }
}
