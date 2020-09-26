package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Expense {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name="date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
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
        dto.setId(id.toString());
        dto.setDate(date);
        dto.setAmmount(ammount);
        dto.setPersonId(category.getId().toString());
        dto.setCategoryId(person.getId().toString());
        dto.setDescription(description);
        return dto;
    }
}
