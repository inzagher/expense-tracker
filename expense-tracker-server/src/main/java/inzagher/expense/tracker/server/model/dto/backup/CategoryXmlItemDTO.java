package inzagher.expense.tracker.server.model.dto.backup;

import inzagher.expense.tracker.server.model.dto.ColorDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

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
