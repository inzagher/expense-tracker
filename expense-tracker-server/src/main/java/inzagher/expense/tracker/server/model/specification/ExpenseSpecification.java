package inzagher.expense.tracker.server.model.specification;

import inzagher.expense.tracker.server.model.dto.ExpenseFilterDTO;
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
    private final ExpenseFilterDTO filter;

    @Override
    public Predicate toPredicate(@NonNull Root<ExpenseEntity> expense,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {
        var predicates = new ArrayList<Predicate>();
        if (!CollectionUtils.isEmpty(filter.getCategories())) {
            var in = builder.in(expense.get("category").get("id"));
            filter.getCategories().forEach(in::value);
            predicates.add(in);
        }
        if (!CollectionUtils.isEmpty(filter.getPersons())) {
            var in = builder.in(expense.get("person").get("id"));
            filter.getPersons().forEach(in::value);
            predicates.add(in);
        }
        if (filter.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(expense.get("date"), filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(expense.get("date"), filter.getDateTo()));
        }
        if (filter.getAmountFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(expense.get("amount"), filter.getAmountFrom()));
        }
        if (filter.getAmountTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(expense.get("amount"), filter.getAmountTo()));
        }
        if (filter.getDescription() != null) {
            var pattern = "%" + filter.getDescription() + "%";
            predicates.add(builder.like(expense.get("description"), pattern));
        }
        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
