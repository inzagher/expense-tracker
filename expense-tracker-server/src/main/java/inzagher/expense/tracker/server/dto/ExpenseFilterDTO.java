package inzagher.expense.tracker.server.dto;

import inzagher.expense.tracker.server.xml.LocalDateXmlAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpenseFilterDTO {
    private final List<UUID> categoryIdentifiers = new ArrayList<>();
    private final List<UUID> personIdentifiers = new ArrayList<>();
    private BigDecimal amountExact;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate dateExact;
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate dateFrom;
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate dateTo;
    private String descriptionLike;

    public List<UUID> getCategoryIdentifiers() {
        return categoryIdentifiers;
    }

    public List<UUID> getPersonIdentifiers() {
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
