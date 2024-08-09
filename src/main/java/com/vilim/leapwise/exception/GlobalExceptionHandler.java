package com.vilim.leapwise.exception;

import com.vilim.leapwise.exception.model.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String DATABASE_INTEGRITY_VIOLATION_EXCEPTION = "Data integrity violation exception - ";
    public static final String VALIDATION_ERROR = "Validation failed: ";
    public static final String GENERIC_EXCEPTION = "An unexpected error occurred - ";
    public static final String LOGICAL_EXPRESSION_EXCEPTION = "Error while evaluating a logical expression - ";

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        log.error(DATABASE_INTEGRITY_VIOLATION_EXCEPTION, ex);
        return ResponseEntity.badRequest().body(new ErrorMessage(DATABASE_INTEGRITY_VIOLATION_EXCEPTION + ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder(VALIDATION_ERROR);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            errorMessage.append(fieldName).append(" - ").append(defaultMessage).append("; ");
        });
        log.error(VALIDATION_ERROR, ex);
        return ResponseEntity.badRequest().body(new ErrorMessage(errorMessage.toString()));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(final EntityNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<ErrorMessage> handleIllegalStateException(final IllegalStateException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler({IllegalAccessException.class})
    public ResponseEntity<ErrorMessage> handleIllegalAccessException(final IllegalAccessException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler({ExpressionEvaluationException.class})
    public ResponseEntity<ErrorMessage> handleExpressionEvaluationException(final ExpressionEvaluationException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(LOGICAL_EXPRESSION_EXCEPTION + ex.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> handleGlobalException(final Exception ex) {
        log.error(GENERIC_EXCEPTION, ex);
        return ResponseEntity.internalServerError().body(new ErrorMessage(GENERIC_EXCEPTION + ex.getMessage()));
    }
}
