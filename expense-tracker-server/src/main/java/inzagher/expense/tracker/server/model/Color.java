package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.ColorDTO;
import javax.persistence.Embeddable;

@Embeddable
public class Color {
    private Integer red;
    private Integer green;
    private Integer blue;
    
    public Color(Integer red, Integer green, Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public Color() {
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
    
    public ColorDTO toDTO() {
        ColorDTO dto = new ColorDTO();
        dto.setRed(red);
        dto.setGreen(green);
        dto.setBlue(blue);
        return dto;
    }
}
