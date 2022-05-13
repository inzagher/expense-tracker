package inzagher.expense.tracker.server.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseDTO {
    private Long id;
    private PersonDTO person;
    private CategoryDTO category;
    private LocalDate date;
    private BigDecimal amount;
    private String description;
}
