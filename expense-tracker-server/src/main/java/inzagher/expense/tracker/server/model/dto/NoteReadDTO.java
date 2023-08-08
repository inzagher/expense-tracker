package inzagher.expense.tracker.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Note read data")
public class NoteReadDTO {

    @Schema(description = "Note identifier")
    @JsonProperty(value = "id")
    private Long id;

    @Schema(description = "Person data")
    @JsonProperty(value = "person")
    private PersonDTO person;

    @Schema(description = "Selected date")
    @JsonProperty(value = "date")
    private LocalDate date;

    @Schema(description = "Note subject")
    @JsonProperty(value = "subject")
    private String subject;

    @Schema(description = "Note content")
    @JsonProperty(value = "content")
    private String content;

    @Schema(description = "Resolution flag")
    @JsonProperty(value = "resolved")
    private Boolean resolved;
}
