package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.service.BackupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class BackupServiceTests {
    private static final String TEST_DESCRIPTION = "BACKUP TEST";
    private static final String TEST_FILE_NAME = "backup.zip";
    
    @Autowired
    private BackupService service;
    @Autowired
    private TestDataManager manager;
    
    @BeforeEach
    public void beforeEachTest() {
        var p1 = manager.storePerson("P1");
        var p2 = manager.storePerson("P2");
        var p3 = manager.storePerson("P3");
        
        var c1 = manager.storeCategory("C1", TEST_DESCRIPTION, 0, 0, 0, false);
        var c2 = manager.storeCategory("C2", TEST_DESCRIPTION, 64, 64, 64, false);
        var c3 = manager.storeCategory("C3", TEST_DESCRIPTION, 128, 128, 128, false);
        
        manager.storeExpense(2020, 1, 1, p1, c1, 0D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 1, 2, p1, c2, 10D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 2, 3, p1, c3, 20D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 3, 4, p1, c1, 30D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 4, 5, p2, c1, 40D, TEST_DESCRIPTION);
        manager.storeExpense(2020, 5, 6, p3, c1, 50D, TEST_DESCRIPTION);
        
        manager.storeBackupMetadata(LocalDateTime.now().minusDays(3), 0, 1, 1, TEST_FILE_NAME);
        manager.storeBackupMetadata(LocalDateTime.now().minusDays(2), 2, 1, 1, TEST_FILE_NAME);
        manager.storeBackupMetadata(LocalDateTime.now().minusDays(1), 4, 3, 2, TEST_FILE_NAME);
    }
    
    @AfterEach
    public void afterEachTest() {
        manager.truncateAllTables();
    }
    
    @Test
    void lastBackupInfoTest() {
        var last = service.findLastMetadataRecord();
        assertTrue(last.isPresent());
        assertNotNull(last.get().getFileName());
        assertEquals(4, last.get().getExpenses());
        assertEquals(3, last.get().getPersons());
        assertEquals(2, last.get().getCategories());
    }
    
    @Test
    void backupInfoListTest() {
        var pageable = PageRequest.of(0, 50);
        var list = service.findAllMetadataRecords(pageable);
        assertEquals(3, list.getTotalElements());
    }
    
    @Test
    void backupDatabaseTest() {
        var metadata = service.createDatabaseBackup();
        assertNotNull(metadata);
        assertNotNull(metadata.getFileName());
        assertEquals(6, metadata.getExpenses());
        assertEquals(3, metadata.getPersons());
        assertEquals(3, metadata.getCategories());
        assertEquals(4, manager.countBackups());
    }
    
    @Test
    void restoreDatabaseTest() {
        var zip = loadTestResource("backup_for_tests.zip");
        service.restoreDatabaseFromBackup(zip);
        assertEquals(3, manager.countExpenses());
        assertEquals(2, manager.countCategories());
        assertEquals(1, manager.countPersons());
        assertEquals(3, manager.countBackups());
    }
    
    private byte[] loadTestResource(String name) {
        var url = getClass().getClassLoader().getResource(name);
        if (url == null) {
            throw new RuntimeException("Resource url is null");
        }
        try (InputStream is = url.openStream()) {
            var size = is.available();
            var result = new byte[size];
            if (is.read(result) == size) {
                return result;
            } else {
                throw new RuntimeException("Invalid buffer size");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
