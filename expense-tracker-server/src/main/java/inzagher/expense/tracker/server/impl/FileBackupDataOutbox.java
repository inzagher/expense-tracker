package inzagher.expense.tracker.server.impl;

import inzagher.expense.tracker.server.core.BackupDataOutbox;
import inzagher.expense.tracker.server.model.BackupMetadata;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class FileBackupDataOutbox implements BackupDataOutbox {
    private final String directory;

    public FileBackupDataOutbox(String directory) {
        this.directory = directory;
    }
    
    @Override
    public OutputStream createOutputStream(BackupMetadata metadata) {
        File file = Paths.get(directory, "backup.zip").toFile();
        if (file.exists()) { file.delete(); }
        
        try { return new FileOutputStream(file); }
        catch (FileNotFoundException e) { throw new RuntimeException(e); }
    }
}
