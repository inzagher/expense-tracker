package inzagher.expense.tracker.server.model.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Category report record")
public class CategoryReportItemDTO {

    @Schema(description = "Category")
    @JsonProperty(value = "category")
    private CategoryDTO category;

    @Schema(description = "Total category expenses")
    @JsonProperty(value = "total")
    private BigDecimal total;
}
