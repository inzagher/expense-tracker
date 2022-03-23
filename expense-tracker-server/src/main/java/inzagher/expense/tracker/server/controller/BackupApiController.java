package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.exception.ExpenseTrackerException;
import inzagher.expense.tracker.server.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BackupApiController {
    private final BackupService service;
    
    @GetMapping(path = "/api/backups")
    public List<BackupMetadataDTO> findAll() {
        return service.findAllMetadataRecords();
    }

    @PostMapping(path = "/api/backups/create")
    public BackupMetadataDTO backupDatabase() {
        return service.createDatabaseBackup();
    }
    
    @PostMapping(path = "/api/backups/restore")
    public void restoreDatabase(@RequestParam MultipartFile file) {
        try { service.restoreDatabaseFromBackup(file.getBytes()); }
        catch (IOException e) { throw new ExpenseTrackerException("File reading failed", e); }
    }
}
