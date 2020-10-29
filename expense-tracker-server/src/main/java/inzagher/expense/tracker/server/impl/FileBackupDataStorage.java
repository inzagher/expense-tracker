package inzagher.expense.tracker.server.impl;

import inzagher.expense.tracker.server.model.BackupMetadata;
import org.springframework.stereotype.Component;
import inzagher.expense.tracker.server.core.BackupDataStorage;

@Component
public class FileBackupDataStorage implements BackupDataStorage {
    @Override
    public void store(BackupMetadata metadata, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
