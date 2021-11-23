package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "expenses")
public class Expense implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Column(name="date")
    private LocalDate date;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "person_id")
    private Person person;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(name="description")
    private String description;
    
    public ExpenseDTO toDTO() {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(id);
        dto.setDate(date);
        dto.setAmount(amount);
        dto.setPersonId(person == null ? null: person.getId());
        dto.setCategoryId(category == null ? null : category.getId());
        dto.setDescription(description);
        return dto;
    }
}
