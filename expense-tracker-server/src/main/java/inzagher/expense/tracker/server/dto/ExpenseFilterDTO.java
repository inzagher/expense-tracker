package inzagher.expense.tracker.server.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExpenseFilterDTO {
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
