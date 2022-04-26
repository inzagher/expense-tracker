package inzagher.expense.tracker.server.model.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ColorEntity implements Serializable {
    private Integer red;
    private Integer green;
    private Integer blue;
}