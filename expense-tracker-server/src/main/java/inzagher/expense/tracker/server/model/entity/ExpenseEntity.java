package inzagher.expense.tracker.server.model.entity;

import inzagher.expense.tracker.server.model.command.CreateExpenseCommand;
import inzagher.expense.tracker.server.model.command.EditExpenseCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expenses")
public class ExpenseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name="date")
    private LocalDate date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="description")
    private String description;

    public ExpenseEntity(CreateExpenseCommand command) {
        date = command.getDate();
        category = new CategoryEntity();
        category.setId(command.getCategoryId());
        person = new PersonEntity();
        person.setId(command.getPersonId());
        amount = command.getAmount();
        description = command.getDescription();
    }

    public void edit(EditExpenseCommand command) {
        date = command.getDate();
        category = new CategoryEntity();
        category.setId(command.getCategoryId());
        person = new PersonEntity();
        person.setId(command.getPersonId());
        amount = command.getAmount();
        description = command.getDescription();
    }
}
