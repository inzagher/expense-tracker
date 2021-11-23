package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategorySummaryItem {
    private Category category;
    private BigDecimal amount;
    
    public CategoryReportItemDTO toDTO() {
        CategoryReportItemDTO dto = new CategoryReportItemDTO();
        dto.setCategory(category == null ? null : category.toDTO());
        dto.setAmount(amount);
        return dto;
    }
}
