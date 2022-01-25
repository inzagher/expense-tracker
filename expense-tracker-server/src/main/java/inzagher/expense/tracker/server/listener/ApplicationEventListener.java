package inzagher.expense.tracker.server.listener;

import inzagher.expense.tracker.server.event.AbstractApplicationEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApplicationEventListener {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @TransactionalEventListener(AbstractApplicationEvent.class)
    public <E extends AbstractApplicationEvent> void handle(E event) {
        var payload = event.getClass().getSimpleName();
        simpMessagingTemplate.convertAndSend("/queue/messages", payload);
    }
}
