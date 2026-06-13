package com.webofpolitics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler — Provides consistent error responses for all API endpoints
 * <p>
 * Phase 2.2 Implementation: Centralized exception handling for REST API layer.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handle PoliticianNotFoundException
     */
    @ExceptionHandler(PoliticianNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePoliticianNotFound(PoliticianNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "POLITICIAN_NOT_FOUND",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * Handle CompanyNotFoundException
     */
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCompanyNotFound(CompanyNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "COMPANY_NOT_FOUND",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * Handle IllegalArgumentException (validation errors)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Handle any other RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_ERROR",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    /**
     * Handle Exception (default handler)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "UNKNOWN_ERROR",
            ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    /**
     * ErrorResponse DTO for consistent error responses
     */
    public static class ErrorResponse {
        private final int status;
        private final String code;
        private final String message;
        
        public ErrorResponse(int status, String code, String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }
        
        public int getStatus() {
            return status;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
