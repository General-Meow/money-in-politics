package com.webofpolitics.api;

import org.springframework.stereotype.Component;

/**
 * Import donation data into Neo4j graph.
 */
@Component
public class DonationImporter {
    
    /**
     * Create Donor nodes and DonationReceived relationships in Neo4j.
     */
    public void importDonationToNeo4j(DonationRecord record) {
        // TODO: Implement Neo4j node/edge creation
        throw new UnsupportedOperationException("STORY-003: Not yet implemented");
    }
}
