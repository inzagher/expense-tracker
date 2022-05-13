package inzagher.expense.tracker.server.model.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyReportItemDTO {
    private int month;
    private BigDecimal amount;
}
