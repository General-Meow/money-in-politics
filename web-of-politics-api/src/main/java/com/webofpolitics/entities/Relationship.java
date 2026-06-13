package com.webofpolitics.entities;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Relationship Entity - Migrated from RelationshipBuilder.java (story-001)
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/RelationshipBuilder.java
 */
@Builder
@Data
public class Relationship {
    
    private String id; // Primary key for Neo4j relationship ID
    
    // Relationship classification
    private RelationshipType type; // FAMILY, BOARD_SEAT, LOBBYING, etc.
    private RelationshipSubType subType; // Spouse, sibling, director, etc.
    
    // Entity connection details
    private RelationshipEntityType sourceType; // POLITICIAN or COMPANY
    private String sourceId; // ID of source entity
    private RelationshipEntityType targetType; // OPPOSITE of sourceType
    private String targetId; // ID of target entity
    
    // Financial relationship properties
    private BigDecimal amount; // Monetary value (donations, transaction amounts)
    private Currency currency; // GBP, EUR, USD, etc.
    
    // Temporal boundaries
    private LocalDate startDate; // Relationship start date
    private LocalDate endDate; // Relationship end date (null for ongoing)
    
    // Descriptive information
    private String description; // Free text description of relationship
    private String evidenceSource; // URL to proof document or source
    private String relationshipOrigin; // How discovered (API, manual, etc.)
    
    /**
     * Enum values matching Neo4j relationship properties
     */
    public enum RelationshipType {
        FAMILY("FAMILY"),
        BOARD_SEAT("BOARD_SEAT"),
        SHARED_EDUCATION("SHARED_EDUCATION"),
        LOBBYING("LOBBYING"),
        CAMPAIGN_CONTRIBUTION("CAMPAIGN_CONTRIBUTION"),
        THINK_TANK("THINK_TANK"),
        MEMBERSHIP("MEMBERSHIP"),
        OTHER("OTHER");
        
        private final String value;
        
        RelationshipType(String value) {
            this.value = value;
        }
    }
    
    public enum RelationshipSubType {
        SPOUSE("Spouse"),
        SIBLING("Sibling"),
        PARENT("Parent"),
        CHILD("Child"),
        DIRECTOR("Director"),
        TRUSTEE("Trustee"),
        SHAREHOLDER("Shareholder"),
        EMPLOYEE("Employee"),
        ADVISOR("Advisor"),
        CAMPAIGNER("Campaigner"),
        OTHER("Other");
    }
    
    public enum RelationshipEntityType {
        POLITICIAN, COMPANY, INDIVIDUAL, ORGANIZATION
    }
    
    public enum Currency {
        GBP, EUR, USD, CAD, AUD, CHF
    }
}
