package com.webofpolitics.api;

import org.springframework.stereotype.Component;

/**
 * Client for fetching MP data from Parliament.uk API.
 */
@Component
public class ParliamentApiClient {
    
    // TODO: Initialize Neo4j session and driver with proper credentials
    
    /**
     * Fetch politician profile from Parliament.uk API.
     */
    public PoliticianProfile fetchPoliticianProfile(String mpId) {
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Get voting records for an MP from Parliament.uk.
     */
    public VotingRecords getVotingRecords(String mpId, java.time.LocalDate from) {
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Get committee memberships for an MP.
     */
    public CommitteeMemberships getCommitteeMemberships(String mpId) {
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Fetch biography text from Parliament.uk API.
     */
    public BiographyText fetchBiography(String mpId) {
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
}
