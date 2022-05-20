package inzagher.expense.tracker.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Person")
public class PersonDTO {

    @Schema(description = "Identifier")
    @JsonProperty(value = "id")
    private Long id;

    @Schema(description = "Short name")
    @JsonProperty(value = "name")
    private String name;
}
