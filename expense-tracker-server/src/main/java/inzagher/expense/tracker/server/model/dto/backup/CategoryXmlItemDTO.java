package inzagher.expense.tracker.server.model.dto.backup;

import inzagher.expense.tracker.server.model.dto.ColorDTO;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryXmlItemDTO {
    private Long id;
    private String name;
    private ColorDTO color;
    private String description;
    private Boolean obsolete;
}
