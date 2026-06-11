package com.webofpolitics.api;

import java.util.*;

/**
 * Repository interface for politician nodes in Neo4j graph.
 */
public interface PoliticianRepository {

    /**
     * Get politician by ID.
     */
    PoliticianSummary findById(String id);

    /**
     * Get all politicians (for listing).
     */
    List<PoliticianSummary> findAll();

}

/**
 * Mock implementation for testing (replace with Neo4j queries in production).
 */
class PoliticianRepositoryImpl implements PoliticianRepository {

    private final Map<String, PoliticianSummary> politicianMap = new HashMap<>();

    public PoliticianRepositoryImpl() {
        // Pre-populate mock data for testing
        populateTestData();
    }

    @Override
    public PoliticianSummary findById(String id) {
        return Optional.ofNullable(politicianMap.get(id))
            .orElse(null);
    }

    @Override
    public List<PoliticianSummary> findAll() {
        // Mock: return all politicians in database
        return new ArrayList<>(politicianMap.values());
    }

    private void populateTestData() {
        // Populate test data for mock repository
        politicianMap.put("UK-CHS-LAB", new PoliticianSummary(
            "UK-CHS-LAB",
            "Sir Keir Rodney Starmer",
            "Keir Starmer",
            "Labour",
            "Holborn and St Pancras",
            "https://example.com/starmer.jpg",
            "Leader of the House of Commons",
            false,
            "2024-05-05"
        ));
    }
}
