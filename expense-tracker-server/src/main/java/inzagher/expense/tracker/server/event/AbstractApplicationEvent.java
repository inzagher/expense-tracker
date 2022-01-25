package inzagher.expense.tracker.server.event;

import org.springframework.context.ApplicationEvent;

public class AbstractApplicationEvent extends ApplicationEvent {
    protected AbstractApplicationEvent() {
        super(new Object());
    }
}
