package inzagher.expense.tracker.server.configuration;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Profile("production")
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfiguration {
    private final BackupService backupService;
    
    @Scheduled(initialDelay = 15 * 1000, fixedRate = 15 * 60 * 1000)
    public void performDatabaseBackupTask() {
        Optional<BackupMetadataDTO> metadata = backupService.getLastBackupInfo();
        LocalDateTime lastBackupTime = metadata.isPresent()
                ? metadata.get().getTime()
                : LocalDateTime.MIN;
        if (lastBackupTime.plusHours(24 * 7).isBefore(LocalDateTime.now())) {
            backupService.createDatabaseBackup();
        }
    }
}
