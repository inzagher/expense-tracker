package inzagher.expense.tracker.server.model;

public class MonthlyReportItem {
    private Category category;
    private Float amount;

    public MonthlyReportItem(Category category, Double amount) {
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
}
