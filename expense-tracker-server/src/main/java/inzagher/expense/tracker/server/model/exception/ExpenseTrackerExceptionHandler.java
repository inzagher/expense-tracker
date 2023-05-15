package inzagher.expense.tracker.server.model.exception;

import inzagher.expense.tracker.server.model.dto.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExpenseTrackerExceptionHandler {

    @ExceptionHandler(ExpenseTrackerException.class)
    public ResponseEntity<ErrorDTO> handleRequestExcerptException(ExpenseTrackerException e) {
        ErrorDTO body = new ErrorDTO(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleMessageReadingException(HttpMessageNotReadableException e) {
        ErrorDTO body = new ErrorDTO("DESERIALIZATION_FAILED", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorDTO body = new ErrorDTO("ENTITY_NOT_FOUND", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
