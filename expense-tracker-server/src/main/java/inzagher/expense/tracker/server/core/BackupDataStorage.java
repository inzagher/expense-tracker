package inzagher.expense.tracker.server.core;

import inzagher.expense.tracker.server.model.BackupMetadata;
import java.io.OutputStream;

public interface BackupDataStorage {
    OutputStream createOutputStream(BackupMetadata metadata);
}
