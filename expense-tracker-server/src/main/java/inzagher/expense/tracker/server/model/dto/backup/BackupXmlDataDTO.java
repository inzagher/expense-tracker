package inzagher.expense.tracker.server.model.dto.backup;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlType
@XmlRootElement(name = "backup")
@XmlAccessorType(XmlAccessType.FIELD)
public class BackupXmlDataDTO {

    @XmlElement(name="person")
    @XmlElementWrapper(name="persons")
    private List<PersonXmlItemDTO> persons;

    @XmlElement(name="category")
    @XmlElementWrapper(name="categories")
    private List<CategoryXmlItemDTO> categories;

    @XmlElement(name="expense")
    @XmlElementWrapper(name="expenses")
    private List<ExpenseXmlItemDTO> expenses;
}
