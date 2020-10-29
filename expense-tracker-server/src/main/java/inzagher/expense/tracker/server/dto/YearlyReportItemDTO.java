package inzagher.expense.tracker.server.dto;

public class YearlyReportItemDTO {
    private int month;
    private Float amount;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
