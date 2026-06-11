package com.webofpolitics.search;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * Politician Search Service - Handles fuzzy matching and search logic.
 * 
 * STORY-101 Acceptance Criteria:
 * - [ ] Empty search shows featured entities (current PM, Speaker, party leaders)
 * - [ ] Valid query returns results in <500ms
 * - [ ] Fuzzy matching handles misspellings (e.g., "Keir Sarm" → "Keir Starmer")
 * - [ ] Multiple matches distinguished by party/constituency
 */
@Service
public class PoliticianSearchService {

    // Mock database of UK politicians for search functionality
    private final Map<String, PoliticianSummary> politicianDb = new HashMap<>();

    public PoliticianSearchService() {
        initializeDatabase();
    }

    /**
     * Initialize mock politician database.
     * In production: Load from Neo4j graph via STORY-002/003 data pipelines.
     */
    private void initializeDatabase() {
        addPolitician("UK-PM-LAB", "Sir Keir Rodney Starmer", "Keir Starmer", 
                      "Labour", "Holborn and St Pancras", 
                      "https://example.com/starmer.jpg", 
                      "Leader of the House of Commons", false, null);
        
        addPolitician("UK-PM-SOC", "Rishi Suresh Sunak", "Rishi Sunak", 
                      "Conservative", "Epsom and Ewell", 
                      "https://example.com/sunak.jpg", 
                      "Former Prime Minister", true, null);
        
        addPolitician("UK-SP-LIB", "Nicola Sturgeon", "Nicola Sturgeon", 
                      "Scottish National Party", "Edinburgh Central", 
                      "https://example.com/sturgeon.jpg", 
                      "First Minister of Scotland", false, "2023-06-28");
        
        addPolitician("UK-CHS-LAB", "Sir Edward Desmond Rees", "Ed Rees", 
                      "Labour", "Eynhafon", 
                      "https://example.com/rees.jpg", 
                      "MP for Eynhafon", false, "2019-12-13");
        
        addPolitician("UK-YKS-LAB", "John Smith", "John Smith", 
                      "Labour", "York Outer", 
                      "https://example.com/smith1.jpg", 
                      "MP for York Outer", false, "2019-12-13");
        
        addPolitician("UK-WYL-LAB", "John Smith", "John Smith", 
                      "Liberal Democrats", "Newport West", 
                      "https://example.com/smith2.jpg", 
                      "MP for Newport West", false, "2019-12-13");
    }

    /**
     * Search for politicians by name or constituency.
     * Implements fuzzy matching for common misspellings.
     * 
     * @param query search query (name or constituency)
     * @param includeFormer whether to include former politicians
     * @return list of matching politicians
     */
    public List<PoliticianSummary> search(String query, String includeFormer) {
        if (query == null || query.trim().isEmpty()) {
            return getFeaturedEntities();
        }

        // Normalize query for fuzzy matching
        String normalizedQuery = query.toLowerCase().trim();

        // Build candidate list based on fuzzy matching
        List<String> candidates = getCandidatesForQuery(normalizedQuery);
        
        if (candidates.isEmpty()) {
            return new ArrayList<>();
        }

        // Apply filters
        var filtered = candidates.stream()
            .map(id -> politicianDb.get(id))
            .filter(Objects::nonNull)
            .filter(p -> includeFormer.equals("current") ? !p.isFormer() : true)
            .limit(20)
            .collect(Collectors.toList());

        // Sort by relevance (exact match first, then fuzzy)
        filtered.sort(Comparator.comparingInt(
            p -> calculateRelevanceScore(query, p.getFullName(), p.getName())
        ));

        return filtered;
    }

    /**
     * Get featured entities for empty search.
     */
    public List<PoliticianSummary> getFeaturedEntities() {
        return politicianDb.values().stream()
            .filter(p -> isFeaturedEntity(p.getId()))
            .limit(3)
            .collect(Collectors.toList());
    }

    /**
     * Get candidate politicians for a query.
     * Includes fuzzy matching for common misspellings.
     */
    private List<String> getCandidatesForQuery(String query) {
        Set<String> candidates = new LinkedHashSet<>();

        // Exact match on name
        politicianDb.entrySet().stream()
            .filter(e -> e.getValue().getName().toLowerCase().equals(query))
            .map(Map.Entry::getKey)
            .forEach(candidates::add);

        // Fuzzy matching: allow edit distance of up to 3 characters
        politicianDb.entrySet().stream()
            .filter(e -> calculateEditDistance(query, e.getValue().getName()) <= 3)
            .map(Map.Entry::getKey)
            .forEach(candidates::add);

        // Constituency match
        politicianDb.entrySet().stream()
            .filter(e -> e.getValue().getConstituency().toLowerCase().contains(query))
            .map(Map.Entry::getKey)
            .forEach(candidates::add);

        return new ArrayList<>(candidates);
    }

    /**
     * Calculate relevance score for sorting results.
     */
    private int calculateRelevanceScore(String query, String fullName, String name) {
        if (query.isEmpty()) {
            return 1000; // Featured entities are most relevant
        }

        // Exact match on name = highest priority
        if (fullName.toLowerCase().equals(query)) {
            return 0;
        }

        if (name.toLowerCase().equals(query)) {
            return 1;
        }

        // Fuzzy match bonus
        int editDistance = calculateEditDistance(query, name);
        return -editDistance; // Lower distance = higher relevance
    }

    /**
     * Calculate Levenshtein edit distance.
     */
    private int calculateEditDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[s1.length()][s2.length()];
    }

    /**
     * Check if politician is a featured entity.
     */
    private boolean isFeaturedEntity(String id) {
        return List.of("UK-PM-LAB", "UK-SP-SOC", "UK-CHS-LAB").contains(id);
    }

    /**
     * Add a politician to the mock database (for testing).
     */
    public void addPolitician(String id, String fullName, String name, String party,
                              String constituency, String photoUrl, String role, 
                              boolean former, String currentSince) {
        politicianDb.put(id, new PoliticianSummary(
            id, fullName, name, party, constituency, photoUrl, role, former, currentSince));
    }

}
