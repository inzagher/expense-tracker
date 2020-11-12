package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.CategorySummaryItem;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepositoryExtension {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Expense> find(ExpenseFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);
        Root<Expense> root = cq.from(Expense.class);
        
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (filter != null) {
            buildPredicates(predicates, cb, root, filter);
        }
        
        cq.select(root);
        cq.where(predicates.toArray(new Predicate[0]));
        
        TypedQuery<Expense> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    
    @Override
    public List<CategorySummaryItem> getCategorySummary(int year, int month) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategorySummaryItem> cq = cb.createQuery(CategorySummaryItem.class);
        Root<Expense> root = cq.from(Expense.class);
        
        ExpenseFilter filter = new ExpenseFilter();
        filter.setDateFrom(LocalDate.of(year, month, 1));
        filter.setDateTo(filter.getDateFrom().plusMonths(1).minusDays(1));
        
        ArrayList<Predicate> predicates = new ArrayList<>();
        buildPredicates(predicates, cb, root, filter);
        cq.where(predicates.toArray(new Predicate[0]));
        cq.groupBy(root.get("category").get("id"));
        cq.multiselect(root.get("category"), cb.sum(root.get("amount")));
        
        TypedQuery<CategorySummaryItem> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    
    @Override
    public Float getTotalMonthAmount(int year, int month) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Float> cq = cb.createQuery(Float.class);
        Root<Expense> root = cq.from(Expense.class);
        
        ExpenseFilter filter = new ExpenseFilter();
        filter.setDateFrom(LocalDate.of(year, month, 1));
        filter.setDateTo(filter.getDateFrom().plusMonths(1).minusDays(1));
        
        ArrayList<Predicate> predicates = new ArrayList<>();
        buildPredicates(predicates, cb, root, filter);
        cq.where(predicates.toArray(new Predicate[0]));
        cq.select(cb.sum(root.get("amount")));
            
        TypedQuery<Float> query = entityManager.createQuery(cq);
        return query.getSingleResult();
    }
    
    private void buildPredicates(
            ArrayList<Predicate> predicates,
            CriteriaBuilder cb,
            Root<Expense> root,
            ExpenseFilter filter
    ) {
        if (filter.getCategoryIdentifiers() != null && filter.getCategoryIdentifiers().size() > 0) {
            In<UUID> in = cb.in(root.get("category").get("id"));
            filter.getCategoryIdentifiers().forEach((id) -> { in.value(id); });
            predicates.add(in);
        }
        if (filter.getPersonIdentifiers() != null && filter.getPersonIdentifiers().size() > 0) {
            In<UUID> in = cb.in(root.get("person").get("id"));
            filter.getPersonIdentifiers().forEach((id) -> { in.value(id); });
            predicates.add(in);
        }
        if (filter.getDateExact() != null) {
            predicates.add(cb.equal(root.get("date"), filter.getDateExact()));
        }
        if (filter.getDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("date"), filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("date"), filter.getDateTo()));
        }
        if (filter.getAmountExact() != null) {
            predicates.add(cb.equal(root.get("amount"), filter.getAmountExact()));
        }
        if (filter.getAmountFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filter.getAmountFrom()));
        }
        if (filter.getAmountTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filter.getAmountTo()));
        }
        if (filter.getDescriptionLike() != null) {
            predicates.add(cb.equal(root.get("description"), filter.getDescriptionLike()));
        }
    }
}
