package com.rudy.TMBackend.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), "Resource Not Found");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), "Bad Request");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), "Unauthorized");
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), "Access Denied");
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
    // ErrorDetails class
    static class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;

        public ErrorDetails(Date timestamp, String message, String details) {
            super();
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        // Getters and Setters
        public Date getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public String getDetails() {
            return details;
        }
        public void setDetails(String details) {
            this.details = details;
        }
    }
}
