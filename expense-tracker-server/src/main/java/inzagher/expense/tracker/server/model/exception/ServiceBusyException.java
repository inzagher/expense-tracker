package inzagher.expense.tracker.server.model.exception;

public class ServiceBusyException extends ExpenseTrackerException {
    public ServiceBusyException(String message) {
        super(message, "SERVICE_BUSY");
    }
}
