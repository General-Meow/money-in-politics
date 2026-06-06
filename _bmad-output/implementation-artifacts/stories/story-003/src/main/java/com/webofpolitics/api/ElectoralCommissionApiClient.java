package com.webofpolitics.api;

import org.springframework.stereotype.Component;

/**
 * Client for fetching donation data from Electoral Commission.
 */
@Component
public class ElectoralCommissionApiClient {
    
    /**
     * Fetch donation ledger for politicians.
     * 
     * @param politicianName Politician name to fetch donations for
     * @return Donation ledger with all donations
     */
    public DonationLedger fetchDonationLedger(String politicianName) {
        // TODO: Implement HTTP client for Electoral Commission API
        throw new UnsupportedOperationException("STORY-003: Not yet implemented");
    }
    
    /**
     * Fetch disclosure documents for a donor.
     */
    public DisclosureDocument fetchDisclosureDocument(Donor donor) {
        // TODO: Implement disclosure document fetching
        throw new UnsupportedOperationException("STORY-003: Not yet implemented");
    }
}
