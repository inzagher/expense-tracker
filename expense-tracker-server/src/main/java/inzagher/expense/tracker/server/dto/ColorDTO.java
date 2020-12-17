package inzagher.expense.tracker.server.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ColorDTO {
    private Integer red;
    private Integer green;
    private Integer blue;
    
    public ColorDTO() {
    }
    
    public ColorDTO(Integer red, Integer green, Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }
}
