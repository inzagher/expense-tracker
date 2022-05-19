package inzagher.expense.tracker.server.model.specification;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

@RequiredArgsConstructor
public class ExpenseSpecification implements Specification<ExpenseEntity> {
    private final ExpenseSearchCriteria criteria;

    @Override
    public Predicate toPredicate(@NonNull Root<ExpenseEntity> expense,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {
        var predicates = new ArrayList<Predicate>();
        if (!CollectionUtils.isEmpty(criteria.getCategoryIdentifiers())) {
            var in = builder.in(expense.get("category").get("id"));
            criteria.getCategoryIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (!CollectionUtils.isEmpty(criteria.getPersonIdentifiers())) {
            var in = builder.in(expense.get("person").get("id"));
            criteria.getPersonIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (criteria.getDateExact() != null) {
            predicates.add(builder.equal(expense.get("date"), criteria.getDateExact()));
        }
        if (criteria.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(expense.get("date"), criteria.getDateFrom()));
        }
        if (criteria.getDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(expense.get("date"), criteria.getDateTo()));
        }
        if (criteria.getAmountExact() != null) {
            predicates.add(builder.equal(expense.get("amount"), criteria.getAmountExact()));
        }
        if (criteria.getAmountFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(expense.get("amount"), criteria.getAmountFrom()));
        }
        if (criteria.getAmountTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(expense.get("amount"), criteria.getAmountTo()));
        }
        if (criteria.getDescriptionLike() != null) {
            predicates.add(builder.equal(expense.get("description"), criteria.getDescriptionLike()));
        }
        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
