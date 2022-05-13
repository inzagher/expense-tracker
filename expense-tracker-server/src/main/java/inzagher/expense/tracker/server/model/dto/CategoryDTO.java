package inzagher.expense.tracker.server.model.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private ColorDTO color;
    private String description;
    private Boolean obsolete;
}
