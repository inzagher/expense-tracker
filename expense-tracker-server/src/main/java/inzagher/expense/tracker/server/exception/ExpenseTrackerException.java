package inzagher.expense.tracker.server.exception;

public class ExpenseTrackerException extends RuntimeException {
    public ExpenseTrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpenseTrackerException(String message) {
        super(message);
    }
}
