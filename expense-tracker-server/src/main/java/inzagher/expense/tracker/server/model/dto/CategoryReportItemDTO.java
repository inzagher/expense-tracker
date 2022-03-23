package inzagher.expense.tracker.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReportItemDTO {
    private CategoryDTO category;
    private BigDecimal amount;
}
