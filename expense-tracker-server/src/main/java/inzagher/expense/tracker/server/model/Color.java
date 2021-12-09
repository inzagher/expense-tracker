package inzagher.expense.tracker.server.model;

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
}
