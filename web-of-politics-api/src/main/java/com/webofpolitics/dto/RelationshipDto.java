package com.webofpolitics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Relationship DTO - API contract for relationship data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipDto {
    
    // Relationship identification
    private String id;
    
    // Classification
    private String type;
    private String subType;
    
    // Entity connections
    private String sourceType; // POLITICIAN, COMPANY, INDIVIDUAL, ORGANIZATION
    private String targetType; // OPPOSITE of sourceType
    private String sourceId;
    private String targetId;
    
    // Financial details
    private BigDecimal amount;
    private String currency;
    
    // Temporal boundaries
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Descriptive information
    private String description;
    private String evidenceSource; // URL to proof document
    private String relationshipOrigin; // How discovered
}
