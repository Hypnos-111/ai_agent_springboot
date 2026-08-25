package com.example.rag.exception;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse(LocalDateTime.now(), 500, ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
