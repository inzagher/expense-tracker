package inzagher.expense.tracker.server.outbox;

import inzagher.expense.tracker.server.model.BackupMetadata;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.OutputStream;

public class MemoryBackupDataOutbox  implements BackupDataOutbox {
    @Override
    public OutputStream createOutputStream(BackupMetadata metadata) {
        return new ByteArrayOutputStream();
    }
}
