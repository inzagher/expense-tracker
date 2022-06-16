package inzagher.expense.tracker.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Backup metadata")
public class BackupMetadataDTO {

    @Schema(description = "Identifier")
    @JsonProperty(value = "id")
    private Long id;

    @Schema(description = "Creation time")
    @JsonProperty(value = "created")
    private LocalDateTime created;

    @Schema(description = "Backup file name")
    @JsonProperty(value = "fileName")
    private String fileName;

    @Schema(description = "Expense count")
    @JsonProperty(value = "expenses")
    private Integer expenses;

    @Schema(description = "Category count")
    @JsonProperty(value = "categories")
    private Integer categories;

    @Schema(description = "Person count")
    @JsonProperty(value = "persons")
    private Integer persons;
}
