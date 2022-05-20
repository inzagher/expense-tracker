package inzagher.expense.tracker.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Color as RGB")
public class ColorDTO {

    @Schema(description = "Red (0-255)")
    @JsonProperty(value = "red")
    private Integer red;

    @Schema(description = "Green (0-255)")
    @JsonProperty(value = "red")
    private Integer green;

    @Schema(description = "Blue (0-255)")
    @JsonProperty(value = "red")
    private Integer blue;
}
