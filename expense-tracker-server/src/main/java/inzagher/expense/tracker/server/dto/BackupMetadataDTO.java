package inzagher.expense.tracker.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BackupMetadataDTO {
    private Integer id;
    private LocalDateTime time;
    private Integer expenses;
    private Integer categories;
    private Integer persons;
}
