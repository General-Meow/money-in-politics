package com.webofpolitics.api;

import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Batch imports company data from Companies House API.
 */
@Component
public class CompanyBatchImporter {
    
    private final CompaniesHouseApiClient apiClient;
    private final DonorCompanyMatcher matcher;
    
    public CompanyBatchImporter(
            CompaniesHouseApiClient apiClient,
            DonorCompanyMatcher matcher) {
        this.apiClient = apiClient;
        this.matcher = matcher;
    }
    
    /**
     * Batch import companies with rate limit handling.
     */
    public void batchImportCompanies(List<String> companyNumbers) {
        // TODO: Implement batch processing with rate limit handling
        throw new UnsupportedOperationException("STORY-004: Not yet implemented");
    }
}
