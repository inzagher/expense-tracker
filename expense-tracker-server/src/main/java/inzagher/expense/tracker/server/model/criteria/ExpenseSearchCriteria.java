package inzagher.expense.tracker.server.model.criteria;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ExpenseSearchCriteria implements Serializable {
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
