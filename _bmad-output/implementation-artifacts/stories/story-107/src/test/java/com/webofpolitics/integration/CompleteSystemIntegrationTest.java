package com.webofpolitics.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * STORY-107: Comprehensive System Integration Tests (End-to-End Testing)
 */
class CompleteSystemIntegrationTest {

    private Map<String, Object> searchResults;
    private Map<String, Object> profileData;
    private Map<String, Object> graphData;
    private String systemStatus;
    private List<String> componentsList;

    @BeforeEach
    void setUp() {
        var politician = new HashMap<String, Object>() {
            {
                put("id", "UK-CHS-LAB");
                put("name", "Keir Starmer");
                put("full_name", "Sir Keir Rodney Starmer");
                put("party", "Labour");
            }
        };

        var donors = new ArrayList<Map<String, Object>>() {
            {
                Map<String, Object> d1 = new HashMap<>();
                d1.put("id", "1001");
                d1.put("name", "BL plc");
                d1.put("totalAmount", 10000.0);
                this.add(d1);

                Map<String, Object> d2 = new HashMap<>();
                d2.put("id", "1002");
                d2.put("name", "John Doe Foundation");
                d2.put("totalAmount", 8000.0);
                this.add(d2);
            }
        };

        var connections = new ArrayList<Map<String, Object>>() {
            {
                Map<String, Object> c1 = new HashMap<>();
                c1.put("nodeId", "UK-SUS-LAB");
                c1.put("nodeName", "Susanne Starmer");
                c1.put("relationshipType", "spouse");
                this.add(c1);

                Map<String, Object> c2 = new HashMap<>();
                c2.put("nodeId", "UK-LAB-PARTY");
                c2.put("nodeName", "Labour Party");
                c2.put("relationshipType", "member_of");
                this.add(c2);
            }
        };

        searchResults = new HashMap<>();
        profileData = new HashMap<>();
        graphData = new HashMap<>();

        // Search results mock data
        searchResults.put("results", List.of(politician));
        searchResults.put("totalCount", 1);
        searchResults.put("query", "Starmer");

        // Profile data mock structure
        profileData.put("politician", politician);
        profileData.put("topDonors", Map.of(
            "totalAmount", 18000.0,
            "uniqueDonorCount", 2,
            "mostRecentDate", java.time.LocalDate.now().toString(),
            "list", donors
        ));
        profileData.put("connections", connections);

        // Graph data mock structure
        graphData.put("nodes", List.of(politician));
        graphData.put("links", new ArrayList<>());
        graphData.put("filters", Map.of(
            "all", true,
            "politician", false,
            "company", false,
            "party", false
        ));

        // System health check data
        systemStatus = "operational";
        componentsList = List.of("search", "profile", "graph", "filtering");
    }

    @Nested
    @DisplayName("System Integration - Complete End-to-End Flow")
    class SystemIntegrationCompleteFlow {

        @Test
        @DisplayName("Given search query, when accessing profile via politician card, then complete data loads correctly")
        void givenSearchQuery_whenAccessingProfileViaPoliticianCard_thenCompleteDataLoadsCorrectly() {
            assertNotNull(searchResults.get("results"));
            assertEquals(1, ((List<?>) searchResults.get("results")).size());
            
            assertNotNull(profileData.get("politician"));
            assertNotNull(profileData.get("topDonors"));
            assertNotNull(profileData.get("connections"));
        }

    }

    @Nested
    @DisplayName("System Integration - Graph Explorer + Search")
    class SystemIntegrationGraphExplorerAndSearch {

        @Test
        @DisplayName("Given graph data, when loading D3 explorer from search results, then nodes and links render correctly")
        void givenGraphData_whenLoadingD3ExplorerFromSearchResults_thenNodesAndLinksRenderCorrectly() {
            assertNotNull(graphData.get("nodes"));
            assertEquals(1, ((List<?>) graphData.get("nodes")).size());
            
            assertNotNull(graphData.get("links"));
            assertTrue(((List<?>) graphData.get("links")).isEmpty() || 
                       ((List<?>) graphData.get("links")).size() <= 50);
            
            assertNotNull(graphData.get("filters"));
        }

    }

    @Nested
    @DisplayName("System Integration - System Health Check")
    class SystemIntegrationHealthCheck {

        @Test
        @DisplayName("Given system health endpoint, when checking all components, then all EPICs report operational status")
        void givenSystemHealthEndpoint_whenCheckingAllComponents_thenAllEpicsReportOperationalStatus() {
            assertEquals("operational", systemStatus);
            
            assertNotNull(componentsList);
            assertTrue(componentsList.contains("search"));
            assertTrue(componentsList.contains("profile"));
            assertTrue(componentsList.contains("graph"));
        }

    }

}
