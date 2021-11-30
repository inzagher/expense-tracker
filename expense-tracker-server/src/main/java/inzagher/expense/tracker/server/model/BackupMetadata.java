package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "backups")
public class BackupMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Column(name="time")
    private LocalDateTime time;
    @Column(name="expenses")
    private Integer expenses;
    @Column(name="categories")
    private Integer categories;
    @Column(name="persons")
    private Integer persons;
    
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
