package inzagher.expense.tracker.server.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryDTO {
    private Integer id;
    private String name;
    private ColorDTO color;
    private String description;
    private Boolean obsolete;
}
