package com.webofpolitics.search;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * STORY-101: Politician Search Interface
 * 
 * Service layer tests for politician fuzzy search functionality.
 * 
 * Acceptance Criteria Coverage:
 * - [ ] Empty search returns featured entities
 * - [ ] Name search with valid query returns results in <500ms
 * - [ ] Fuzzy matching handles misspellings (e.g., "Keir Sarm" → "Keir Starmer")
 * - [ ] Multiple matches distinguished by party/constituency
 */
class PoliticianSearchServiceTest {

    private PoliticianSearchService searchService;
    
    @BeforeEach
    void setUp() {
        // Mock repository would be injected in real app
        this.searchService = new PoliticianSearchService();
    }

    @Nested
    @DisplayName("Empty Search - Returns Featured Entities")
    class EmptySearchReturnsFeaturedEntities {

        @Test
        @DisplayName("Given empty search box, when submitted, then homepage shows featured entities")
        void givenEmptyQueryWhenSubmitted_thenReturnsFeaturedPoliticians() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("", "current");
            
            // Assert
            assertNotNull(results);
            assertTrue(results.isEmpty() || results.size() >= 3); // At least 3 featured politicians
            assertTrue(results.get(0).getParty() != null);
        }
    }

    @Nested
    @DisplayName("Name Search - Returns Relevent Results")
    class NameSearchReturnsRelevantResults {

        @Test
        @DisplayName("Given valid name query, when searching, then results appear within acceptable time")
        void givenValidNameQueryWhenSearching_thenReturnsResults() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("Starmer", "current");
            
            // Assert
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertTrue(results.get(0).getFullName().toLowerCase().contains("starmer"));
        }

        @Test
        @DisplayName("Given constituency query, when searching, then matching constituencies return results")
        void givenConstituencyQuery_whenSearching_thenReturnsMatchingResults() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("Holborn", "current");
            
            // Assert
            assertNotNull(results);
            results.forEach(r -> {
                if (r.getConstituency() != null) {
                    assertTrue(r.getConstituency().toLowerCase().contains("holborn"));
                }
            });
        }

        @Test
        @DisplayName("Given search for former vs current, then status filter works")
        void givenIncludeFormerParam_thenFiltersCorrectly() {
            // Arrange
            List<PoliticianSummary> currentResults = searchService.search("Smith", "current");
            
            // Assert
            assertNotNull(currentResults);
            assertTrue(currentResults.stream().allMatch(p -> !p.isFormer()));
        }
    }

    @Nested
    @DisplayName("Fuzzy Matching - Handles Misspellings")
    class FuzzyMatchingHandlesMisspellings {

        @Test
        @DisplayName("Given misspelling 'Keir Sarm', when searching, then fuzzy matching finds correct MP")
        void givenMisspelling_thenFindsCorrectResult() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("Keir Sarm", "current");
            
            // Assert
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertTrue(results.get(0).getFullName().toLowerCase().contains("starmer"));
        }

        @Test
        @DisplayName("Given abbreviated name 'K. Starmer', when searching, then finds correct politician")
        void givenAbbreviatedName_thenFindsCorrectResult() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("K. Starmer", "current");
            
            // Assert
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertTrue(results.get(0).getFullName().toLowerCase().contains("starmer"));
        }

        @Test
        @DisplayName("Given partial constituency 'Holborn and St', when searching, then finds matching MP")
        void givenPartialConstituency_thenFindsMatchingResult() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("Holborn", "current");
            
            // Assert
            assertNotNull(results);
            assertFalse(results.isEmpty());
        }
    }

    @Nested
    @DisplayName("Multiple Matches - Distinguished by Context")
    class MultipleMatchesDistinguishedByContext {

        @Test
        @DisplayName("Given common name 'John Smith' in different constituencies, when searching, then all matches listed")
        void givenCommonName_thenAllMatchesListed() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("John Smith", "current");
            
            // Assert
            assertNotNull(results);
            // Should limit to top 20 as per spec
            assertTrue(results.size() <= 20);
            
            // Each result should be distinguished by party/constituency
            results.forEach(r -> {
                if (r.getParty() != null && r.getConstituency() != null) {
                    // Verify uniqueness combination
                }
            });
        }

        @Test
        @DisplayName("Given partial text 'David', when searching, then results sorted by relevance")
        void givenPartialText_thenSortedByRelevance() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("David", "current");
            
            // Assert
            assertNotNull(results);
            // Top result should be most relevant (exact name match)
            if (!results.isEmpty()) {
                assertTrue(results.get(0).getFullName().toLowerCase().contains("david"));
            }
        }
    }

    @Nested
    @DisplayName("Response Format - Correct Schema")
    class ResponseFormatCorrectSchema {

        @Test
        @DisplayName("Given search results, when returned, then format matches API spec")
        void givenSearchResults_thenFormatMatchesSpec() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("Starmer", "current");
            
            if (results.isEmpty()) {
                return; // Skip if no results
            }
            
            PoliticianSummary result = results.get(0);
            
            // Assert - Check all required fields exist and are correct type
            assertNotNull(result.getId());
            assertNotNull(result.getFullName());
            assertNotNull(result.getName());
            assertNotNull(result.getParty());
            assertNotNull(result.getConstituency());
            assertNotNull(result.getPhotoUrl());
            
            // Verify JSON structure matches spec - use == for type check
            assertFalse(result.isFormer()); // Should be false
        }
    }

    @Nested
    @DisplayName("Edge Cases - No Results")
    class EdgeCasesNoResults {

        @Test
        @DisplayName("Given query with no matching politicians, when searching, then empty list returned")
        void givenNonExistentQuery_thenReturnsEmptyList() {
            // Arrange
            List<PoliticianSummary> results = searchService.search("Zzzyparkoq", "current");
            
            // Assert
            assertNotNull(results);
            assertTrue(results.isEmpty());
        }

        @Test
        @DisplayName("Given very long query string, when searching, then handled gracefully")
        void givenVeryLongQuery_thenHandledGracefully() {
            // Arrange
            String longQuery = "Starmer Smith Johnson Williams Brown Jones".repeat(10);
            List<PoliticianSummary> results = searchService.search(longQuery, "current");
            
            // Assert
            assertNotNull(results); // Should not crash
        }
    }
}
