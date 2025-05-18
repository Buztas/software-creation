package org.example.pskurimaslab1.exception;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleOptimisticLockException(OptimisticLockException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Update failed due to a version conflict. Please refresh and try again.");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Element was not found.");
    }
}
