package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.outbox.BackupDataOutbox;
import inzagher.expense.tracker.server.outbox.MemoryBackupDataOutbox;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestingConfiguration {
    @Bean
    @Profile("test")
    public BackupDataOutbox backupDataOutbox() {
        return new MemoryBackupDataOutbox();
    }
}
