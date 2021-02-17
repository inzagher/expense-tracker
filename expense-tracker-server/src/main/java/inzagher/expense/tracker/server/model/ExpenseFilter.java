package inzagher.expense.tracker.server.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseFilter {
    private final List<Integer> categoryIdentifiers = new ArrayList<>();
    private final List<Integer> personIdentifiers = new ArrayList<>();
    private BigDecimal amountExact;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private LocalDate dateExact;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String descriptionLike;

    public List<Integer> getCategoryIdentifiers() {
        return categoryIdentifiers;
    }

    public List<Integer> getPersonIdentifiers() {
        return personIdentifiers;
    }

    public BigDecimal getAmountExact() {
        return amountExact;
    }

    public void setAmountExact(BigDecimal amountExact) {
        this.amountExact = amountExact;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

    public LocalDate getDateExact() {
        return dateExact;
    }

    public void setDateExact(LocalDate dateExact) {
        this.dateExact = dateExact;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescriptionLike() {
        return descriptionLike;
    }

    public void setDescriptionLike(String descriptionLike) {
        this.descriptionLike = descriptionLike;
    }
}
