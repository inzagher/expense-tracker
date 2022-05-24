package inzagher.expense.tracker.server.model.exception;

public class SerializationException extends ExpenseTrackerException {
    public SerializationException(String message, Throwable cause) {
        super(message, "SERIALIZATION_FAILED", cause);
    }

    public SerializationException(String message) {
        super(message, "SERIALIZATION_FAILED");
    }
}
