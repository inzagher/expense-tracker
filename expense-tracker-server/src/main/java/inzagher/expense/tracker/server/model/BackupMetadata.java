package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.converter.LocalDateTimeConverter;
import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "backups")
public class BackupMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Column(name="time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime time;
    @Column(name="expenses")
    private Integer expenses;
    @Column(name="categories")
    private Integer categories;
    @Column(name="persons")
    private Integer persons;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        dto.setId(id);
        dto.setTime(time);
        dto.setExpenses(expenses);
        dto.setCategories(categories);
        dto.setPersons(persons);
        return dto;
    }
}
