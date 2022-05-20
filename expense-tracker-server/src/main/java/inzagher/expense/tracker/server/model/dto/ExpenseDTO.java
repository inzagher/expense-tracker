package inzagher.expense.tracker.server.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "Expense")
public class ExpenseDTO {

    @Schema(description = "Identifier")
    @JsonProperty(value = "id")
    private Long id;

    @Schema(description = "Expense owner")
    @JsonProperty(value = "person")
    private PersonDTO person;

    @Schema(description = "Expense category")
    @JsonProperty(value = "category")
    private CategoryDTO category;

    @Schema(description = "Creation date")
    @JsonProperty(value = "date")
    private LocalDate date;

    @Schema(description = "Amount")
    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @Schema(description = "Short description")
    @JsonProperty(value = "description")
    private String description;
}
