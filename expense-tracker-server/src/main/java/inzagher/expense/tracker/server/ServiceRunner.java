package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.dto.BackupDataDTO;
import inzagher.expense.tracker.server.outbox.BackupDataOutbox;
import inzagher.expense.tracker.server.outbox.FileBackupDataOutbox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@SpringBootApplication
public class ServiceRunner {
    @Value("${backup.directory}")
    private String backupDirectoryPath;

    public static void main(String[] args) {
        SpringApplication.run(ServiceRunner.class, args);
    }

    @Bean
    @Profile("default | production")
    public BackupDataOutbox backupDataOutbox() {
        return new FileBackupDataOutbox(backupDirectoryPath);
    }

    @Bean
    public JAXBContext jaxbContext() {
        var classes = new Class[]{ BackupDataDTO.class };
        try { return JAXBContext.newInstance(classes); }
        catch(JAXBException e) { throw new RuntimeException(e); }
    }
}
