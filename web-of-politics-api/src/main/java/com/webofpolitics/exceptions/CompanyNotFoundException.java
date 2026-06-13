package com.webofpolitics.exceptions;

/**
 * Exception thrown when a company with the specified ID is not found.
 * <p>
 * Used in CompanyService.findById() and deleteById() methods.
 */
public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
