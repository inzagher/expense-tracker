package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Expense implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
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
    @Column(name="ammount")
    private Float ammount;
    @Column(name="description")
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Float getAmmount() {
        return ammount;
    }

    public void setAmmount(Float ammount) {
        this.ammount = ammount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public ExpenseDTO toDTO() {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(id == null ? null : id.toString());
        dto.setDate(date);
        dto.setAmmount(ammount);
        dto.setPersonId(person == null ? null: person.getId().toString());
        dto.setCategoryId(category == null ? null : category.getId().toString());
        dto.setDescription(description);
        return dto;
    }
}
