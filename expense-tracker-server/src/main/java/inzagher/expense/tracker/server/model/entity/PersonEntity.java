package inzagher.expense.tracker.server.model.entity;

import inzagher.expense.tracker.server.model.command.CreatePersonCommand;
import inzagher.expense.tracker.server.model.command.EditPersonCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class PersonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name="name")
    private String name;

    public PersonEntity(CreatePersonCommand command) {
        this.name = command.getName();
    }

    public void edit(EditPersonCommand command) {
        this.name = command.getName();
    }
}
