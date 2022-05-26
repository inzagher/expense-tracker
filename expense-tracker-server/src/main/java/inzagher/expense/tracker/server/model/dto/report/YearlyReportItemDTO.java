package inzagher.expense.tracker.server.model.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Yearly report record")
public class YearlyReportItemDTO {

    @Schema(description = "Month of year (1-12)")
    @JsonProperty(value = "month")
    private Integer month;

    @Schema(description = "Total monthly expenses")
    @JsonProperty(value = "total")
    private BigDecimal total;
}
