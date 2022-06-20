package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.exception.ExpenseTrackerException;
import inzagher.expense.tracker.server.service.BackupService;
import inzagher.expense.tracker.server.util.ControllerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Backup and restore")
public class BackupApiController {
    private final BackupService service;
    
    @GetMapping(path = "/api/backups")
    @Operation(summary = "Find all created backups")
    public List<BackupMetadataDTO> findAll() {
        return service.findAllMetadataRecords();
    }

    @GetMapping(path = "/api/backups/download/{id}")
    @Operation(summary = "Download backup file")
    public ResponseEntity<InputStreamResource> download(@PathVariable Long id) {
        try {
            var metadata = service.getMetadataRecordById(id);
            var stream = service.downloadBackupFile(id);
            return ControllerUtils.createFileResponse(stream, metadata.getFileName());
        } catch (IOException e) {
            throw new ExpenseTrackerException("File reading failed", e);
        }
    }

    @PostMapping(path = "/api/backups/create")
    @Operation(summary = "Create database backup")
    public BackupMetadataDTO backupDatabase() {
        return service.createDatabaseBackup();
    }
    
    @PostMapping(path = "/api/backups/restore")
    @Operation(summary = "Restore database from backup")
    public void restoreDatabase(@RequestParam MultipartFile file) {
        try { service.restoreDatabaseFromBackup(file.getBytes()); }
        catch (IOException e) { throw new ExpenseTrackerException("File reading failed", e); }
    }
}
