package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.BackupService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = ServiceRunner.class)
public class BackupServiceTests {
    private static final String TEST_DESCRIPTION = "BACKUP TEST";
    
    @Autowired
    private BackupService backupService;
    @Autowired
    private TestDataManager manager;
    
    @BeforeEach
    public void beforeEachTest() {
        Person p1 = manager.storePerson("P1");
        Person p2 = manager.storePerson("P2");
        Person p3 = manager.storePerson("P3");
        
        Category c1 = manager.storeCategory("C1", TEST_DESCRIPTION, 0, 0, 0, false);
        Category c2 = manager.storeCategory("C2", TEST_DESCRIPTION, 64, 64, 64, false);
        Category c3 = manager.storeCategory("C3", TEST_DESCRIPTION, 128, 128, 128, false);
        
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
        Optional<BackupMetadataDTO> last = backupService.getLastBackupInfo();
        assertTrue(last.isPresent());
        assertEquals(4, last.get().getExpenses());
        assertEquals(3, last.get().getPersons());
        assertEquals(2, last.get().getCategories());
    }
    
    @Test
    public void backupInfoListTest() {
        List<BackupMetadataDTO> list = backupService.getAllBackupInfo();
        assertEquals(3, list.size());
    }
    
    @Test
    public void backupDatabaseTest() {
        BackupMetadataDTO bm = backupService.createDatabaseBackup();
        assertNotNull(bm);
        assertEquals(6, bm.getExpenses());
        assertEquals(3, bm.getPersons());
        assertEquals(3, bm.getCategories());
        assertEquals(4, manager.countBackups());
    }
    
    @Test
    public void restoreDatabaseTest() {
        byte[] zip = loadTestResource("backup_for_tests.zip");
        backupService.restoreDatabaseFromBackup(zip);
        assertEquals(3, manager.countExpenses());
        assertEquals(2, manager.countCategories());
        assertEquals(1, manager.countPersons());
        assertEquals(1, manager.countBackups());
    }
    
    private byte[] loadTestResource(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        if (url == null) {
            throw new RuntimeException("Resource url is null");
        }
        try (InputStream is = url.openStream()) {
            byte[] result = new byte[is.available()];
            is.read(result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
