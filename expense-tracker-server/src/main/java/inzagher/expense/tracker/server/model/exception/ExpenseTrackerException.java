package inzagher.expense.tracker.server.model.exception;

import lombok.Getter;

@Getter
public class ExpenseTrackerException extends RuntimeException {
    private final String errorCode;

    public ExpenseTrackerException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ExpenseTrackerException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "UNKNOWN";
    }

    public ExpenseTrackerException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ExpenseTrackerException(String message) {
        super(message);
        this.errorCode = "UNKNOWN";
    }
}
