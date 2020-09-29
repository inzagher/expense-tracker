package inzagher.expense.tracker.server.model;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public CategoryDTO toDTO() {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(id == null ? null : id.toString());
        dto.setName(name);
        dto.setColor(color.toDTO());
        dto.setDescription(description);
        return dto;
    }
}
