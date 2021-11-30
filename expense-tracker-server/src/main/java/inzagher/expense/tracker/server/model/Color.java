package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.ColorDTO;
import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
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
