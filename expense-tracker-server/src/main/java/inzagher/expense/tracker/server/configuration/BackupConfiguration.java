package inzagher.expense.tracker.server.configuration;

import inzagher.expense.tracker.server.dto.BackupDataDTO;
import inzagher.expense.tracker.server.outbox.BackupDataOutbox;
import inzagher.expense.tracker.server.outbox.FileBackupDataOutbox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Component
public class BackupConfiguration {
    @Value("${backup.directory}")
    private String backupDirectoryPath;

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
