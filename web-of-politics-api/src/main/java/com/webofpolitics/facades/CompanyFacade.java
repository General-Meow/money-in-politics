package com.webofpolitics.facades;

import com.webofpolitics.dto.CompanyDto;

/**
 * Company Facade - High-level interface for company operations
 */
public class CompanyFacade {
    
    private final com.webofpolitics.services.CompanyService companyService;
    
    public CompanyFacade(com.webofpolitics.services.CompanyService companyService) {
        this.companyService = companyService;
    }
    
    /**
     * Get all companies
     */
    public Iterable<CompanyDto> getAll() {
        return companyService.findByIndustry("All");
    }
}
