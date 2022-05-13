package inzagher.expense.tracker.server.model.dto.backup;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ColorXmlItemDTO {
    private Integer red;
    private Integer green;
    private Integer blue;
}
