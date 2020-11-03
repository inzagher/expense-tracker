package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;

public class CategorySummaryItem {
    private Category category;
    private Float amount;

    public CategorySummaryItem(Category category, Double amount) {
        this.category = category;
        this.amount = amount.floatValue();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
    
    public CategoryReportItemDTO toDTO() {
        CategoryReportItemDTO dto = new CategoryReportItemDTO();
        dto.setCategory(category == null ? null : category.toDTO());
        dto.setAmount(amount);
        return dto;
    }
}
