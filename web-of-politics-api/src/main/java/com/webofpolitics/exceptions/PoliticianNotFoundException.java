package com.webofpolitics.exceptions;

/**
 * Exception thrown when a politician with the specified ID is not found.
 * <p>
 * Used in PoliticianService.findById() and deleteById() methods.
 */
public class PoliticianNotFoundException extends RuntimeException {
    public PoliticianNotFoundException(String message) {
        super(message);
    }
}
