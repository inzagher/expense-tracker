package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.dto.backup.BackupXmlDataDTO;
import inzagher.expense.tracker.server.service.SerializationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class SerializationServiceTests {
    private final static String ENTRY_NAME = "test.xml";

    @Autowired
    private SerializationService service;

    @Test
    void serializeThenDeserialize() {
        var dto = new BackupXmlDataDTO();
        dto.setCategories(new ArrayList<>());
        dto.setPersons(new ArrayList<>());
        dto.setExpenses(new ArrayList<>());
        var bytes = service.serializeAndZip(dto, ENTRY_NAME);
        var serialized = service.deserializeZippedData(BackupXmlDataDTO.class, bytes, ENTRY_NAME);
        assertEquals(dto, serialized);
    }
}
