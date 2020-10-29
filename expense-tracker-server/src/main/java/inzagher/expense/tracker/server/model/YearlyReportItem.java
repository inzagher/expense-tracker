package inzagher.expense.tracker.server.model;

public class YearlyReportItem {
    private int month;
    private Float amount;

    public YearlyReportItem(int month, Float amount) {
        this.month = month;
        this.amount = amount;
    }

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
