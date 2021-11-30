package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.PersonDTO;
import lombok.Getter;
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
@Entity
@Table(name = "persons")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Column(name="name")
    private String name;
    
    public PersonDTO toDTO() {
        PersonDTO dto = new PersonDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}
