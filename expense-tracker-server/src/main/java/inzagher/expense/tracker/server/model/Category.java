package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import lombok.Data;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Column(name="name")
    private String name;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "red", column = @Column( name = "color_red" )),
        @AttributeOverride(name = "green", column = @Column( name = "color_green" )),
        @AttributeOverride(name = "blue", column = @Column( name = "color_blue" ))
    })
    private Color color;
    @Column(name="description")
    private String description;
    @Column(name="obsolete")
    private Boolean obsolete;
    
    public CategoryDTO toDTO() {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setColor(color.toDTO());
        dto.setDescription(description);
        dto.setObsolete(obsolete);
        return dto;
    }
}
