package com.webofpolitics.api;

import org.springframework.stereotype.Component;
import org.neo4j.driver.*;

/**
 * Client for fetching MP data from Parliament.uk API.
 */
@Component
public class ParliamentApiClient {
    
    private final Session session;
    public ParliamentApiClient(DriverConfig config, Driver driver) {
        this.session = driver.session(config);
    }
    
    /**
     * Fetch politician profile from Parliament.uk (https://api.parliament.uk/women-and-people/the-houses/people).
     */
    public PoliticianProfile fetchPoliticianProfile(String mpId) {
        // TODO: Fetch from Parliament.uk API
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Get voting records for an MP.
     */
    public VotingRecords getVotingRecords(String mpId, java.time.LocalDate from) {
        // TODO: Fetch voting data
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Get committee memberships for an MP.
     */
    public CommitteeMemberships getCommitteeMemberships(String mpId) {
        // TODO: Fetch committee data
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
    
    /**
     * Fetch biography text from Parliament.uk.
     */
    public BiographyText fetchBiography(String mpId) {
        // TODO: Fetch biography
        throw new UnsupportedOperationException("STORY-002: Not yet implemented");
    }
}
