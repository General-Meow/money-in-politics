package com.webofpolitics.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Politician Request DTO — Validation-enabled DTO for API requests
 * <p>
 * Phase 3 Implementation: Enhanced DTOs with validation annotations for request bodies.
 */
public class PoliticianRequestDto {
    
    /**
     * Required unique identifier (Neo4j node ID)
     */
    @NotBlank(message = "ID is required")
    @Pattern(regexp = "[a-zA-Z0-9_-]+", message = "ID must contain only alphanumeric characters, underscores, or hyphens")
    private String id;
    
    /**
     * Short name for display
     */
    @NotBlank(message = "Name is required")
    private String name;
    
    /**
     * Complete legal name
     */
    @Size(max = 200, message = "FullName must not exceed 200 characters")
    private String fullName;
    
    /**
     * Parliamentary role/title
     */
    @Size(max = 100, message = "Role must not exceed 100 characters")
    private String role;
    
    /**
     * Constituency represented
     */
    @Pattern(regexp = "[a-zA-Z ]+", message = "Constituency must contain only letters and spaces")
    private String constituency;
    
    /**
     * Political party
     */
    @Size(max = 100, message = "Party must not exceed 100 characters")
    private String party;
    
    /**
     * Jurisdiction (e.g., Senate, House of Representatives)
     */
    @Pattern(regexp = "[a-zA-Z ]+", message = "Jurisdiction must contain only letters and spaces")
    private String jurisdiction;
    
    /**
     * Current MP status
     */
    @Size(max = 10, message = "Current must be one of: true, false")
    private Boolean current;
    
    /**
     * Tenure end date (null if currently serving)
     */
    private LocalDate tenureEnd;
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getConstituency() { return constituency; }
    public void setConstituency(String constituency) { this.constituency = constituency; }
    
    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }
    
    public String getJurisdiction() { return jurisdiction; }
    public void setJurisdiction(String jurisdiction) { this.jurisdiction = jurisdiction; }
    
    public Boolean getCurrent() { return current; }
    public void setCurrent(Boolean current) { this.current = current; }
    
    public LocalDate getTenureEnd() { return tenureEnd; }
    public void setTenureEnd(LocalDate tenureEnd) { this.tenureEnd = tenureEnd; }
}
