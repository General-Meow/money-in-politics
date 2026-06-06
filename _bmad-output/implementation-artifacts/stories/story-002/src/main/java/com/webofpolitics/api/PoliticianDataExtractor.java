package com.webofpolitics.api;

import org.springframework.stereotype.Component;

/**
 * Extracts and normalizes data from Parliament.uk API responses.
 */
@Component
public class PoliticianDataExtractor {
    
    /**
     * Extract politician profile fields from API response.
     */
    public PoliticianData extractFromProfileResponse(String jsonResponse) {
        // TODO: Implement JSON parsing and field extraction
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Extract voting records from API response.
     */
    public VotingRecords extractVotingRecords(String json) {
        // TODO: Implement voting records extraction
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Extract biography text from API response.
     */
    public BiographyText extractBiography(String json) {
        // TODO: Implement biography extraction
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
}
