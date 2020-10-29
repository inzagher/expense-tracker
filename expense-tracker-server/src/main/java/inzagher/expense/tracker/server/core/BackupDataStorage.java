package inzagher.expense.tracker.server.core;

import inzagher.expense.tracker.server.model.BackupMetadata;

public interface BackupDataStorage {
    void store(BackupMetadata metadata, byte[] data);
}
