package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.core.BackupDataOutbox;
import inzagher.expense.tracker.server.impl.FileBackupDataOutbox;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ServerConfiguration {
    @Autowired
    Environment environment;
    
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver_class_name"));
        dataSource.setUrl(environment.getProperty("db.database_url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }
    
    @Bean
    public BackupDataOutbox fileBackupDataOutbox() {
        String directory = environment.getProperty("backup.directory");
        return new FileBackupDataOutbox(directory);
    }
}
