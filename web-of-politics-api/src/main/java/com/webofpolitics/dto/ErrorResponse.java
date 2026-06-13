package com.webofpolitics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * Error Response DTO — Standard error response format for API endpoints
 */
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;
    
    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
