package inzagher.expense.tracker.server.model.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EditExpenseCommand {
    private Integer id;
    private Integer personId;
    private Integer categoryId;
    private LocalDate date;
    private BigDecimal amount;
    private String description;
}
