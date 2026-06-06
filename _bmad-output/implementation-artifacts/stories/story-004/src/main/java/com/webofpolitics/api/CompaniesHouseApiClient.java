package com.webofpolitics.api;

import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Companies House API client for fetching company and donor data.
 */
@Component
public class CompaniesHouseApiClient {
    
    // TODO: Implement HTTP client initialization with authentication
    
    /**
     * Fetch a company profile from Companies House API.
     */
    public CompanyProfile fetchCompanyProfile(String companyNumber) {
        // TODO: Implement HTTP GET request to Companies House API
        throw new UnsupportedOperationException("STORY-004: Not yet implemented");
    }
    
    /**
     * Search for companies by name pattern.
     */
    public List<CompanyProfile> searchCompaniesByName(String namePattern) {
        // TODO: Implement search API call
        throw new UnsupportedOperationException("STORY-004: Not yet implemented");
    }
}

// Helper DTO class
class CompanyProfile {
    private String companyNumber;
    private String companyName;
    private String sicCodes;
    private java.time.Date incorporationDate;
    private List<Address> addresses;
    
    // Getters...
}

class Address {
    private String postalAddress;
    
    // Getters...
}
