package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.service.BackupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
public class BackupServiceTests {
    private static final String TEST_DESCRIPTION = "BACKUP TEST";
    
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
        
        manager.storeBackupMetadata(LocalDateTime.now().minusDays(3), 0, 1, 1);
        manager.storeBackupMetadata(LocalDateTime.now().minusDays(2), 2, 1, 1);
        manager.storeBackupMetadata(LocalDateTime.now().minusDays(1), 4, 3, 2);
    }
    
    @AfterEach
    public void afterEachTest() {
        manager.truncateAllTables();
    }
    
    @Test
    public void lastBackupInfoTest() {
        var last = service.findLastMetadataRecord();
        assertTrue(last.isPresent());
        assertEquals(4, last.get().getExpenses());
        assertEquals(3, last.get().getPersons());
        assertEquals(2, last.get().getCategories());
    }
    
    @Test
    public void backupInfoListTest() {
        var list = service.findAllMetadataRecords();
        assertEquals(3, list.size());
    }
    
    @Test
    public void backupDatabaseTest() {
        var metadata = service.createDatabaseBackup();
        assertNotNull(metadata);
        assertEquals(6, metadata.getExpenses());
        assertEquals(3, metadata.getPersons());
        assertEquals(3, metadata.getCategories());
        assertEquals(4, manager.countBackups());
    }
    
    @Test
    public void restoreDatabaseTest() {
        var zip = loadTestResource("backup_for_tests.zip");
        service.restoreDatabaseFromBackup(zip);
        assertEquals(3, manager.countExpenses());
        assertEquals(2, manager.countCategories());
        assertEquals(1, manager.countPersons());
        assertEquals(1, manager.countBackups());
    }
    
    private byte[] loadTestResource(String name) {
        var url = getClass().getClassLoader().getResource(name);
        if (url == null) {
            throw new RuntimeException("Resource url is null");
        }
        try (InputStream is = url.openStream()) {
            var result = new byte[is.available()];
            is.read(result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
