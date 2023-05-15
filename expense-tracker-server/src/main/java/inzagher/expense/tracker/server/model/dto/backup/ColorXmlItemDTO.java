package inzagher.expense.tracker.server.model.dto.backup;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ColorXmlItemDTO {
    private Integer red;
    private Integer green;
    private Integer blue;
}
