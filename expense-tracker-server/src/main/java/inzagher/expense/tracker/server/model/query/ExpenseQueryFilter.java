package inzagher.expense.tracker.server.model.query;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExpenseQueryFilter {
    private final List<Integer> categoryIdentifiers = new ArrayList<>();
    private final List<Integer> personIdentifiers = new ArrayList<>();
    private BigDecimal amountExact;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private LocalDate dateExact;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String descriptionLike;
}
