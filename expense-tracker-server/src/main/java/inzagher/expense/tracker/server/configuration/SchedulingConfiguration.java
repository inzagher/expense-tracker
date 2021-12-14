package inzagher.expense.tracker.server.configuration;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("production")
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfiguration {
    private final BackupService backupService;
    
    @Scheduled(initialDelay = 15 * 1000, fixedRate = 15 * 60 * 1000)
    public void performDatabaseBackupTask() {
        var metadata = backupService.findLastMetadataRecord();
        var lastBackupTime = metadata.map(BackupMetadataDTO::getCreated).orElse(LocalDateTime.MIN);
        if (lastBackupTime.plusHours(24 * 7).isBefore(LocalDateTime.now())) {
            backupService.createDatabaseBackup();
        }
    }
}