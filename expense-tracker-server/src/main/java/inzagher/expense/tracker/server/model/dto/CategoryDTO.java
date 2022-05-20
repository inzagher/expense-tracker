package inzagher.expense.tracker.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Category")
public class CategoryDTO {

    @Schema(description = "Identifier")
    @JsonProperty(value = "id")
    private Long id;

    @Schema(description = "Short name")
    @JsonProperty(value = "name")
    private String name;

    @Schema(description = "Color")
    @JsonProperty(value = "color")
    private ColorDTO color;

    @Schema(description = "Usage description")
    @JsonProperty(value = "description")
    private String description;

    @Schema(description = "Not available for usage")
    @JsonProperty(value = "obsolete")
    private Boolean obsolete;
}
