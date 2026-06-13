package com.webofpolitics.entities;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Company Entity - Migrated from CompanyNodeCreator.java (story-001)
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/CompanyNodeCreator.java
 */
@Builder
@Data
public class Company {
    
    private String id; // Primary key for Neo4j node ID
    
    // Basic company information
    private String name; // Company name
    private String officialName; // Legal registered name
    private String tradingName; // Trading/doing business as name
    
    // Industry and sector
    private String industry; // Primary industry sector
    private String sicCode; // Standard Industrial Classification code
    private String naicsCode; // North American Industry Classification (if applicable)
    
    // Location information
    private String registeredAddress; // Official registered address
    private String website; // Company website URL
    
    // Incorporation details
    private LocalDate incorporatedDate; // Company formation date
    private String registeredCountry; // Country of incorporation
    private String companyNumber; // Companies House number (UK) or equivalent
    
    // Organization type
    private String companyType; // Limited, unlimited, PLC, etc.
    private String status; // Active, dissolved, admin, etc.
    
    /**
     * Related companies (for holding/subsidiary relationships)
     */
    @Builder.Default
    private final Set<Company> relatedCompanies = new HashSet<>();
    
    public void addRelatedCompany(Company company) {
        this.relatedCompanies.add(company);
    }
    
    public List<Company> getRelatedCompanies() {
        if (relatedCompanies.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.relatedCompanies);
    }
}
