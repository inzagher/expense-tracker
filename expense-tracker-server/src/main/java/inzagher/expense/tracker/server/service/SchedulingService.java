package inzagher.expense.tracker.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulingService {
    private final BackupService backupService;

    @Scheduled(cron = "0 0 1 1,15 * *")
    public void createDatabaseBackup() {
        backupService.createDatabaseBackup();
    }
}
