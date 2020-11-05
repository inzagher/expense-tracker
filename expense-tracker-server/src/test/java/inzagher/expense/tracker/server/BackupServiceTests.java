package inzagher.expense.tracker.server;

import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Transactional
@SpringBootTest(classes = {ServiceRunner.class})
@TestPropertySource(locations="classpath:test.properties")
public class BackupServiceTests {
    
    @BeforeEach
    public void beforeEachTest() {
    }
    
    @AfterEach
    public void afterEachTest() {
    }
    
    @Test
    @Disabled
    public void lastBackupInfoTest() {
        
    }
    
    @Test
    @Disabled
    public void backupInfoListTest() {
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
