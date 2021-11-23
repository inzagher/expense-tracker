package inzagher.expense.tracker.server.outbox;

import inzagher.expense.tracker.server.model.BackupMetadata;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileBackupDataOutbox implements BackupDataOutbox {
    private final String directory;

    public FileBackupDataOutbox(String directory) {
        this.directory = directory;
    }
    
    @Override
    public OutputStream createOutputStream(BackupMetadata metadata) {
        File file = Paths.get(directory, formatBackupFileName()).toFile();
        
        if (file.exists()) { file.delete(); }
        else { new File(directory).mkdirs(); }
        
        try { return new FileOutputStream(file); }
        catch (FileNotFoundException e) { throw new RuntimeException(e); }
    }
    
    private String formatBackupFileName() {
        String pattern = "yyyy_MM_dd_HH_mm_ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return "backup_" + LocalDateTime.now().format(formatter) + ".zip";
    }
}
