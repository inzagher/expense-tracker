package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.PersonDTO;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persons")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, columnDefinition = "UUID")
    private UUID id;
    @Column(name="name")
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public PersonDTO toDTO() {
        PersonDTO dto = new PersonDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}
