package inzagher.expense.tracker.server.model.exception;

public class ServiceBusyException extends RuntimeException {
    public ServiceBusyException(String message) {
        super(message);
    }
}
