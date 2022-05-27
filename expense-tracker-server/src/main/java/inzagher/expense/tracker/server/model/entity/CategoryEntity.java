package inzagher.expense.tracker.server.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Cacheable
@NoArgsConstructor
@Table(name = "categories")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
