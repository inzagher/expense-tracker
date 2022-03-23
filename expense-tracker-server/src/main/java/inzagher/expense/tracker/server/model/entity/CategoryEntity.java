package inzagher.expense.tracker.server.model.entity;

import inzagher.expense.tracker.server.model.command.CreateCategoryCommand;
import inzagher.expense.tracker.server.model.command.EditCategoryCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name="name")
    private String name;

    @Embedded
    @AttributeOverride(name = "red", column = @Column(name = "color_red"))
    @AttributeOverride(name = "green", column = @Column(name = "color_green"))
    @AttributeOverride(name = "blue", column = @Column(name = "color_blue"))
    private ColorEntity color;

    @Column(name="description")
    private String description;

    @Column(name="obsolete")
    private Boolean obsolete;

    public CategoryEntity(CreateCategoryCommand command) {
        name = command.getName();
        color = new ColorEntity();
        color.setRed(command.getColorRed());
        color.setGreen(command.getColorGreen());
        color.setBlue(command.getColorBlue());
        description = command.getDescription();
        obsolete = false;
    }

    public void edit(EditCategoryCommand command) {
        name = command.getName();
        color = new ColorEntity();
        color.setRed(command.getColorRed());
        color.setGreen(command.getColorGreen());
        color.setBlue(command.getColorBlue());
        description = command.getDescription();
        obsolete = command.getObsolete();
    }
}
