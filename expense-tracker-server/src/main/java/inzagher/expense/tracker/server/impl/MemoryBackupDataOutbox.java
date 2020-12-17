package inzagher.expense.tracker.server.impl;

import inzagher.expense.tracker.server.core.BackupDataOutbox;
import inzagher.expense.tracker.server.model.BackupMetadata;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.OutputStream;

public class MemoryBackupDataOutbox  implements BackupDataOutbox {
    @Override
    public OutputStream createOutputStream(BackupMetadata metadata) {
        return new ByteArrayOutputStream();
    }
}
