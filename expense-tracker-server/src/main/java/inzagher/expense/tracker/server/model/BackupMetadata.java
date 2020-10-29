package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BackupMetadata implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name="time")
    private LocalDateTime time;
    @Column(name="expenses")
    private Integer expenses;
    @Column(name="categories")
    private Integer categories;
    @Column(name="persons")
    private Integer persons;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getExpenses() {
        return expenses;
    }

    public void setExpenses(Integer expenses) {
        this.expenses = expenses;
    }

    public Integer getCategories() {
        return categories;
    }

    public void setCategories(Integer categories) {
        this.categories = categories;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }
    
    public BackupMetadataDTO toDTO() {
        BackupMetadataDTO dto = new BackupMetadataDTO();
        dto.setId(id == null ? null : id.toString());
        dto.setTime(time);
        dto.setExpenses(expenses);
        dto.setCategories(categories);
        dto.setPersons(persons);
        return dto;
    }
}
