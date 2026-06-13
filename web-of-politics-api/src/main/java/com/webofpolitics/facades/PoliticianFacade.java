package com.webofpolitics.facades;

import com.webofpolitics.dto.PoliticianDto;

/**
 * Politician Facade - High-level interface for politician operations
 * 
 * Facades provide a simplified interface over multiple business services,
 * often combining related operations or providing domain-specific views.
 */
public class PoliticianFacade {
    
    private final com.webofpolitics.services.PoliticianService politicianService;
    private final com.webofpolitics.services.CompanyService companyService;
    
    public PoliticianFacade(
            com.webofpolitics.services.PoliticianService politicianService,
            com.webofpolitics.services.CompanyService companyService) {
        this.politicianService = politicianService;
        this.companyService = companyService;
    }
    
    /**
     * Get all active politicians with basic info
     */
    public Iterable<PoliticianDto> getAllActive() {
        return politicianService.findAllActiveByJurisdiction("WESTMINSTER");
    }
}
