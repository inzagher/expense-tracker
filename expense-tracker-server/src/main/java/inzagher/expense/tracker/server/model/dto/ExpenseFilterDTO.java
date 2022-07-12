package inzagher.expense.tracker.server.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Expense search params")
public class ExpenseFilterDTO implements Serializable {
    @Schema(description = "Category identifiers")
    private List<Long> categories;

    @Schema(description = "Person identifiers")
    private List<Long> persons;

    @Schema(description = "Min expense amount")
    private BigDecimal amountFrom;

    @Schema(description = "Max expense amount")
    private BigDecimal amountTo;

    @Schema(description = "Period start")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateFrom;

    @Schema(description = "Period end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTo;

    @Schema(description = "Description substring")
    private String description;
}
