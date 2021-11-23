package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.service.BackupService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BackupApiController {
    private final BackupService backupService;
    
    @Autowired
    public BackupApiController(BackupService backupService) {
        this.backupService = backupService;
    }
    
    @GetMapping(path = "/api/backups")
    public List<BackupMetadataDTO> list() {
        return backupService.getAllBackupInfo();
    }
    
    @GetMapping(path = "/api/backup-database")
    public BackupMetadataDTO backupDatabase() {
        return backupService.createDatabaseBackup();
    }
    
    @PostMapping(path = "/api/restore-database")
    public void restoreDatabase(@RequestParam MultipartFile file) {
        try { backupService.restoreDatabaseFromBackup(file.getBytes()); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
