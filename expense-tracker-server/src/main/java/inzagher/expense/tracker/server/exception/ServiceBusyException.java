package inzagher.expense.tracker.server.exception;

public class ServiceBusyException extends RuntimeException {
    public ServiceBusyException(String message) {
        super(message);
    }
}
