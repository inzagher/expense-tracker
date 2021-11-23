package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.ColorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    private Integer red;
    private Integer green;
    private Integer blue;
    
    public ColorDTO toDTO() {
        ColorDTO dto = new ColorDTO();
        dto.setRed(red);
        dto.setGreen(green);
        dto.setBlue(blue);
        return dto;
    }
}
