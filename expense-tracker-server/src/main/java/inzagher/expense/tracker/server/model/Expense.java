package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.command.CreateExpenseCommand;
import inzagher.expense.tracker.server.command.EditExpenseCommand;
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
public class Expense implements Serializable {

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
    private Category category;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="description")
    private String description;

    public Expense(CreateExpenseCommand command) {
        date = command.getDate();
        category = new Category();
        category.setId(command.getCategoryId());
        person = new Person();
        person.setId(command.getPersonId());
        amount = command.getAmount();
        description = command.getDescription();
    }

    public void edit(EditExpenseCommand command) {
        date = command.getDate();
        category = new Category();
        category.setId(command.getCategoryId());
        person = new Person();
        person.setId(command.getPersonId());
        amount = command.getAmount();
        description = command.getDescription();
    }
}
