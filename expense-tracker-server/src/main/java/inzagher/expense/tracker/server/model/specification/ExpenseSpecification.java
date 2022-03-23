package inzagher.expense.tracker.server.model.specification;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

@RequiredArgsConstructor
public class ExpenseSpecification implements Specification<ExpenseEntity> {
    private final ExpenseSearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<ExpenseEntity> expense, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates = new ArrayList<Predicate>();
        if (criteria.getCategoryIdentifiers() != null && !criteria.getCategoryIdentifiers().isEmpty()) {
            var in = cb.in(expense.get("category").get("id"));
            criteria.getCategoryIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (criteria.getPersonIdentifiers() != null && !criteria.getPersonIdentifiers().isEmpty()) {
            var in = cb.in(expense.get("person").get("id"));
            criteria.getPersonIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (criteria.getDateExact() != null) {
            predicates.add(cb.equal(expense.get("date"), criteria.getDateExact()));
        }
        if (criteria.getDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(expense.get("date"), criteria.getDateFrom()));
        }
        if (criteria.getDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(expense.get("date"), criteria.getDateTo()));
        }
        if (criteria.getAmountExact() != null) {
            predicates.add(cb.equal(expense.get("amount"), criteria.getAmountExact()));
        }
        if (criteria.getAmountFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(expense.get("amount"), criteria.getAmountFrom()));
        }
        if (criteria.getAmountTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(expense.get("amount"), criteria.getAmountTo()));
        }
        if (criteria.getDescriptionLike() != null) {
            predicates.add(cb.equal(expense.get("description"), criteria.getDescriptionLike()));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
