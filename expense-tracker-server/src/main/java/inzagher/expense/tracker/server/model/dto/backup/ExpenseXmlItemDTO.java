package inzagher.expense.tracker.server.model.dto.backup;

import inzagher.expense.tracker.server.util.LocalDateXmlAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpenseXmlItemDTO {
    private Long id;
    private Long personId;
    private Long categoryId;
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate date;
    private BigDecimal amount;
    private String description;
}
