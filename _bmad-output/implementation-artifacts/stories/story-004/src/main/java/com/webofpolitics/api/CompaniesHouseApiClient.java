package com.webofpolitics.api;

import org.springframework.stereotype.Component;

/**
 * Client for fetching company data from Companies House API.
 */
@Component
public class CompaniesHouseApiClient {
    
    /**
     * Fetch company profile from Companies House (via web interface or official API).
     * 
     * @param companyNumber Company registration number (e.g., "12345678")
     * @return CompanyProfile with full company details
     */
    public CompanyProfile fetchCompanyProfile(String companyNumber) {
        // TODO: Implement HTTP client for Companies House API
        throw new UnsupportedOperationException("STORY-004: Not yet implemented");
    }
    
    /**
     * Search companies by name.
     */
    public java.util.List<String> searchCompaniesByName(String companyName) {
        // TODO: Implement company search
        throw new UnsupportedOperationException("STORY-004: Not yet implemented");
    }
}
