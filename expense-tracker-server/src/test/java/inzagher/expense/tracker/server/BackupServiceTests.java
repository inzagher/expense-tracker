package inzagher.expense.tracker.server;

import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest(classes = {ServiceRunner.class})
public class BackupServiceTests {
    
    @BeforeEach
    public void beforeEachTest() {
    }
    
    @AfterEach
    public void afterEachTest() {
    }
    
    @Test
    @Disabled
    public void lastBackupTest() {
        
    }
    
    @Test
    @Disabled
    public void backupListTest() {
    }
    
    @Test
    @Disabled
    public void backupDatabaseTest() {
    }
    
    @Test
    @Disabled
    public void restoreDatabaseTest() {
    }
}
