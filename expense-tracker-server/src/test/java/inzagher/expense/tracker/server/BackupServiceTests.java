package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.service.BackupService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Transactional
@SpringBootTest(classes = {ServiceRunner.class})
@TestPropertySource(locations="classpath:test.properties")
public class BackupServiceTests {
    @Autowired
    private BackupService backupService;
    @Autowired
    private TestDataManager manager;
    
    @BeforeEach
    public void beforeEachTest() {
        Person p1 = manager.storePerson("P1");
        Person p2 = manager.storePerson("P2");
        Person p3 = manager.storePerson("P3");
        
        Category c1 = manager.storeCategory("C1", "BACKUP TEST", (byte)0, (byte)0, (byte)0);
        Category c2 = manager.storeCategory("C2", "BACKUP TEST", (byte)64, (byte)64, (byte)64);
        Category c3 = manager.storeCategory("C3", "BACKUP TEST", (byte)128, (byte)128, (byte)128);
        
        manager.storeExpense(2020, 1, 1, p1, c1, 0F);
        manager.storeExpense(2020, 1, 2, p1, c2, 10F);
        manager.storeExpense(2020, 2, 3, p1, c3, 20F);
        manager.storeExpense(2020, 3, 4, p1, c1, 30F);
        manager.storeExpense(2020, 4, 5, p2, c1, 40F);
        manager.storeExpense(2020, 5, 6, p3, c1, 50F);
        
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
        List<BackupMetadataDTO> list = backupService.getAllBackupInfos();
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
    @Disabled
    public void restoreDatabaseTest() {
    }
}
