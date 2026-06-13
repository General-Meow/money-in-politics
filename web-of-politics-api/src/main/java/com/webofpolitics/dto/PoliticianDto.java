package com.webofpolitics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Politician DTO - API contract for politician data (complete version)
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/PoliticianNodeCreator.java
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoliticianDto {
    
    // Basic identity
    private String id;
    private String name;
    private String fullName;
    
    // Parliamentary role
    private String role;
    private String constituency;
    
    // Party affiliation
    private String party;
    private String partyShortName;
    
    // Tenure tracking
    private LocalDate tenureStart;
    private LocalDate tenureEnd;
    private boolean isCurrent;
    
    // Media and social
    private String photoUrl;
    private String twitterHandle;
    private String website;
    
    // Shadow minister role (if applicable)
    private String shadowMinisterRole;
    private boolean isShadowMinister;
}
