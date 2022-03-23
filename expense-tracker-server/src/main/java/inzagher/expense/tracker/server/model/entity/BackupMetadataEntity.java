package inzagher.expense.tracker.server.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "backups")
public class BackupMetadataEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name="created")
    private LocalDateTime created;

    @Column(name="expenses")
    private Integer expenses;

    @Column(name="categories")
    private Integer categories;

    @Column(name="persons")
    private Integer persons;
}
