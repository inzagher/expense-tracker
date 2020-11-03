package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.service.BackupService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final BackupService backupService;
    
    @Autowired
    public Scheduler(BackupService backupService) {
        this.backupService = backupService;
    }
    
    @Scheduled(initialDelay = 15 * 1000, fixedRate = 15 * 60 * 1000)
    public void performDatabaseBackupTask() {
        Optional<BackupMetadataDTO> metadata = backupService.getLastBackupInfo();
        LocalDateTime lastBackupTime = metadata.isPresent()
                ? metadata.get().getTime()
                : LocalDateTime.MIN;
        if (lastBackupTime.plusHours(24 * 7).isAfter(LocalDateTime.now())) {
            backupService.createDatabaseBackup();
        }
    }
}
