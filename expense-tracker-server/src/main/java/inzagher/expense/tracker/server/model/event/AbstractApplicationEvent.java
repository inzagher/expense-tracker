package inzagher.expense.tracker.server.model.event;

import org.springframework.context.ApplicationEvent;

public class AbstractApplicationEvent extends ApplicationEvent {
    protected AbstractApplicationEvent() {
        super(new Object());
    }
}
