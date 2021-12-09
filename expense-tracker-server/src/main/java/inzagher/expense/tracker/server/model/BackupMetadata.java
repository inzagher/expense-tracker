package inzagher.expense.tracker.server.model;

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
}
