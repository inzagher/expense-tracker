package inzagher.expense.tracker.server.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

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
}
