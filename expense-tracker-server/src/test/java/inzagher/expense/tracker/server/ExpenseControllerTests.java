package inzagher.expense.tracker.server;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class ExpenseControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataManager manager;

    @Test
    void findAllFromEmptyDatabase() throws Exception {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/api/expenses")
                .queryParam("dateFrom", "2020-01-01")
                .queryParam("dateTo", "2020-01-31")
                .queryParam("amountFrom", 100)
                .queryParam("amountTo", 1000)
                .queryParam("description", "food")
                .queryParam("categories", 2)
                .queryParam("categories", 1)
                .queryParam("persons", 1)
                .build();
        mockMvc.perform(get(uriComponents.toUriString())).andDo(print())
                .andExpect(status().isOk());
    }
}
