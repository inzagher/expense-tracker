package inzagher.expense.tracker.server.model.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDTO {
    private Integer id;
    private String name;
}
