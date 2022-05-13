package inzagher.expense.tracker.server.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BackupMetadataDTO {
    private Long id;
    private LocalDateTime created;
    private Integer expenses;
    private Integer categories;
    private Integer persons;
}
