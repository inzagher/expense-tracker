package inzagher.expense.tracker.server.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "notes")
public class NoteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name="date")
    private LocalDate date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name="subject")
    private String subject;

    @Column(name="content")
    private String content;

    @Column(name="resolved")
    private Boolean resolved;
}
