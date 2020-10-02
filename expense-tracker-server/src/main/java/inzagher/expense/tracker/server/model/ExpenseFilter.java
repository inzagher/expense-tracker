package inzagher.expense.tracker.server.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpenseFilter {
    private final List<UUID> categoryIdentifiers = new ArrayList<>();
    private final List<UUID> personIdentifiers = new ArrayList<>();
    private Float amountExact;
    private Float amountFrom;
    private Float amountTo;
    private LocalDate dateExact;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String descriptionLike;

    public List<UUID> getCategoryIdentifiers() {
        return categoryIdentifiers;
    }

    public List<UUID> getPersonIdentifiers() {
        return personIdentifiers;
    }

    public Float getAmountExact() {
        return amountExact;
    }

    public void setAmountExact(Float amountExact) {
        this.amountExact = amountExact;
    }

    public Float getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Float amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Float getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Float amountTo) {
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
