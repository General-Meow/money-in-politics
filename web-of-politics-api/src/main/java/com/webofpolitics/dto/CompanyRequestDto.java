package com.webofpolitics.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Company Request DTO — Validation-enabled DTO for API requests
 * <p>
 * Phase 3 Implementation: Enhanced DTOs with validation annotations for request bodies.
 */
public class CompanyRequestDto {
    
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
     * Complete legal company name
     */
    @Size(max = 200, message = "FullName must not exceed 200 characters")
    private String fullName;
    
    /**
     * Industry sector
     */
    @Size(max = 100, message = "Industry must not exceed 100 characters")
    private String industry;
    
    /**
     * Sub-industry (optional)
     */
    @Pattern(regexp = "[a-zA-Z0-9_ -]+", message = "SubIndustry must contain only letters, numbers, underscores, or hyphens")
    private String subIndustry;
    
    /**
     * Company headquarters country code
     */
    @Pattern(regexp = "[A-Z]{2}", message = "Country code must be 2-letter ISO code")
    private String headquarters;
    
    /**
     * Founded year
     */
    @NotBlank(message = "Founded is required")
    @Pattern(regexp = "^\\d{4}$", message = "Founded must be a 4-digit year (e.g., 1995)")
    private String founded;
    
    /**
     * Headquarters city
     */
    @Size(max = 100, message = "Address must not exceed 100 characters")
    private String address;
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    
    public String getSubIndustry() { return subIndustry; }
    public void setSubIndustry(String subIndustry) { this.subIndustry = subIndustry; }
    
    public String getHeadquarters() { return headquarters; }
    public void setHeadquarters(String headquarters) { this.headquarters = headquarters; }
    
    public String getFounded() { return founded; }
    public void setFounded(String founded) { this.founded = founded; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
