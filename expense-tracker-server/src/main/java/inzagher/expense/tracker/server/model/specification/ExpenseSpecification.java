package inzagher.expense.tracker.server.model.specification;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import lombok.NonNull;
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
    public Predicate toPredicate(@NonNull Root<ExpenseEntity> expense,
                                 @NonNull CriteriaQuery<?> criteria,
                                 @NonNull CriteriaBuilder builder) {
        var predicates = new ArrayList<Predicate>();
        if (this.criteria.getCategoryIdentifiers() != null && !this.criteria.getCategoryIdentifiers().isEmpty()) {
            var in = builder.in(expense.get("category").get("id"));
            this.criteria.getCategoryIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (this.criteria.getPersonIdentifiers() != null && !this.criteria.getPersonIdentifiers().isEmpty()) {
            var in = builder.in(expense.get("person").get("id"));
            this.criteria.getPersonIdentifiers().forEach(in::value);
            predicates.add(in);
        }
        if (this.criteria.getDateExact() != null) {
            predicates.add(builder.equal(expense.get("date"), this.criteria.getDateExact()));
        }
        if (this.criteria.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(expense.get("date"), this.criteria.getDateFrom()));
        }
        if (this.criteria.getDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(expense.get("date"), this.criteria.getDateTo()));
        }
        if (this.criteria.getAmountExact() != null) {
            predicates.add(builder.equal(expense.get("amount"), this.criteria.getAmountExact()));
        }
        if (this.criteria.getAmountFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(expense.get("amount"), this.criteria.getAmountFrom()));
        }
        if (this.criteria.getAmountTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(expense.get("amount"), this.criteria.getAmountTo()));
        }
        if (this.criteria.getDescriptionLike() != null) {
            predicates.add(builder.equal(expense.get("description"), this.criteria.getDescriptionLike()));
        }
        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
