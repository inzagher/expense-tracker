package inzagher.expense.tracker.server.configuration;

import inzagher.expense.tracker.server.model.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Profile("scheduling-enabled")
public class SchedulingConfiguration {
    private final BackupService backupService;
    
    @Scheduled(initialDelay = 15 * 1000, fixedRate = 15 * 60 * 1000)
    public void performDatabaseBackupTask() {
        var nextBackupCreationTime = backupService.findLastMetadataRecord()
                .map(BackupMetadataDTO::getCreated)
                .orElse(LocalDateTime.MIN)
                .plusHours(24L * 7L);
        if (nextBackupCreationTime.isBefore(LocalDateTime.now())) {
            backupService.createDatabaseBackup();
        }
    }
}
