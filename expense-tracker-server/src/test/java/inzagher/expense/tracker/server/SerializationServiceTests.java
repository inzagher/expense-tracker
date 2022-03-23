package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.dto.BackupDataDTO;
import inzagher.expense.tracker.server.service.SerializationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
public class SerializationServiceTests {
    private final static String ENTRY_NAME = "test.xml";

    @Autowired
    private SerializationService service;

    @Test
    public void serializeThenDeserialize() {
        var dto = new BackupDataDTO();
        dto.setCategories(new ArrayList<>());
        dto.setPersons(new ArrayList<>());
        dto.setExpenses(new ArrayList<>());
        var bytes = service.serializeAndZip(dto, ENTRY_NAME);
        var serialized = service.deserializeZippedData(BackupDataDTO.class, bytes, ENTRY_NAME);
        assertEquals(dto, serialized);
    }
}
