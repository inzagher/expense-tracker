package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.core.BackupDataOutbox;
import inzagher.expense.tracker.server.impl.MemoryBackupDataOutbox;
import org.springframework.context.annotation.Bean;

public class TestingConfiguration extends ServiceRunner {
    @Bean
    @Override
    public BackupDataOutbox backupDataOutbox() {
        return new MemoryBackupDataOutbox();
    }
}
