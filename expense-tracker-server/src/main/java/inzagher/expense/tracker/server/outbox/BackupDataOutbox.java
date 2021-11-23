package inzagher.expense.tracker.server.outbox;

import inzagher.expense.tracker.server.model.BackupMetadata;
import java.io.OutputStream;

public interface BackupDataOutbox {
    OutputStream createOutputStream(BackupMetadata metadata);
}
