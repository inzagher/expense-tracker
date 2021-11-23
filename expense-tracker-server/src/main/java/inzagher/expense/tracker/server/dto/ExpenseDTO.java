package inzagher.expense.tracker.server.dto;

import inzagher.expense.tracker.server.serialization.LocalDateXmlAdapter;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpenseDTO {
    private Integer id;
    private Integer personId;
    private Integer categoryId;
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate date;
    private BigDecimal amount;
    private String description;
}
