package org.example.rideshare.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("NOT_FOUND", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("BAD_REQUEST", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String msg = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation error");

        return new ResponseEntity<>(
                new ErrorResponse("VALIDATION_ERROR", msg),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("VALIDATION_ERROR", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherErrors(Exception ex) {

        return new ResponseEntity<>(
                new ErrorResponse("SERVER_ERROR", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
