package com.webofpolitics.entities;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Politician Entity - Migrated from PoliticianNodeCreator.java (story-001)
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/PoliticianNodeCreator.java
 */
@Builder
@Data
public class Politician {
    
    private String id; // Primary key for Neo4j node ID
    
    // Basic properties (from PoliticianData)
    private String name; // Short name for display
    private String fullName; // Complete name
    private String role; // Current parliamentary role
    private String constituency; // Constituency represented (if applicable)
    
    // Party affiliation
    private String party;
    private String partyShortName; // Abbreviation
    
    // Tenure tracking
    private LocalDate tenureStart; // Elected date / Start of term
    private LocalDate tenureEnd; // Leave office date (null for current MPs)
    private boolean isCurrent; // Flag for currently serving MPs
    
    // Photo and media
    private String photoUrl; // Headshot URL
    private String twitterHandle;
    private String website;
    
    // Party position info
    private String shadowMinisterRole;
    private boolean isShadowMinister;
    
    /**
     * Related politicians (for family relationships, shared positions)
     */
    @Builder.Default
    private final Set<Politician> relatedPoliticians = new HashSet<>();
    
    public void addRelatedPolitician(Politician politician) {
        this.relatedPoliticians.add(politician);
    }
    
    public List<Politician> getRelatedPoliticians() {
        if (relatedPoliticians.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.relatedPoliticians);
    }
    
    /**
     * Check if politician is currently serving
     */
    public boolean isCurrentlyServing() {
        // No tenureEnd means current, or if both dates exist check current date
        return this.tenureEnd == null || 
               (this.tenureEnd != null && this.isCurrent) ||
               this.isCurrent;
    }
    
    /**
     * Get active status for API responses
     */
    public boolean isActive() {
        return isCurrentlyServing() || 
               // Allow past politicians if they were relevant to searches
               (this.tenureStart != null && !tenureEnd.equals(this.tenureStart));
    }
}
