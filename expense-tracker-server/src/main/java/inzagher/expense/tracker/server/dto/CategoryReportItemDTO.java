package inzagher.expense.tracker.server.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryReportItemDTO {
    private CategoryDTO category;
    private BigDecimal amount;
}
