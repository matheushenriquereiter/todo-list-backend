package io.github.matheushenriquereiter.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ValidationErrorResponse> handleDuplicateEmailException(DuplicateEmailException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.addError(exception.getField(), exception.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidAttributeException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidAttributeException(InvalidAttributeException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.addError(exception.getField(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
