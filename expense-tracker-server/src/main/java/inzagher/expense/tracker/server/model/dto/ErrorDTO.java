package inzagher.expense.tracker.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Operation error")
public class ErrorDTO {

    @Schema(description = "Error code")
    @JsonProperty(value = "code")
    private String code;

    @Schema(description = "Error description")
    @JsonProperty(value = "message")
    private String message;
}
