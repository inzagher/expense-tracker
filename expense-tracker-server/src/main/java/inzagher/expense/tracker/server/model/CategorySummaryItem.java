package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;
import java.math.BigDecimal;

public class CategorySummaryItem {
    private Category category;
    private BigDecimal amount;

    public CategorySummaryItem(Category category, BigDecimal amount) {
        this.category = category;
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public CategoryReportItemDTO toDTO() {
        CategoryReportItemDTO dto = new CategoryReportItemDTO();
        dto.setCategory(category == null ? null : category.toDTO());
        dto.setAmount(amount);
        return dto;
    }
}
