package inzagher.expense.tracker.server.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BackupDataDTO {
    @XmlElement(name="person")
    @XmlElementWrapper(name="persons")
    private List<PersonDTO> persons;
    @XmlElement(name="category")
    @XmlElementWrapper(name="categories")
    private List<CategoryDTO> categories;
    @XmlElement(name="expense")
    @XmlElementWrapper(name="expenses")
    private List<ExpenseDTO> expenses;
}
