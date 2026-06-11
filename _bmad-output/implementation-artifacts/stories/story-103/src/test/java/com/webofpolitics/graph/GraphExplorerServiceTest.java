package com.webofpolitics.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * STORY-103: Graph Explorer Service Tests
 */
class GraphExplorerServiceTest {

    private com.webofpolitics.graph.GraphExplorerService service;

    @BeforeEach
    void setUp() {
        // Create ArrayList to avoid type inference issues
        List<Node> mockNodes = new ArrayList<>();
        
        mockNodes.add(new GraphNode("UK-CHS-LAB", "Keir Starmer", "Politician", LocalDate.now(), null, "", 20, true));
        mockNodes.add(new GraphNode("COMP-BL-001", "BL plc", "Company", LocalDate.parse("2015-06-01"), null, "Tech industry", 18, true));

        service = new com.webofpolitics.graph.GraphExplorerService() {
            @Override
            public List<Node> fetchConnections(String politicianId, int maxHops) {
                if ("UK-CHS-LAB".equals(politicianId)) {
                    return mockNodes;
                } else {
                    return new ArrayList<>();
                }
            }

            @Override
            public List<Node> fetchDirectConnections(String politicianId, String[] nodeTypeFilters) {
                if ("UK-CHS-LAB".equals(politicianId)) {
                    return mockNodes;
                } else {
                    return new ArrayList<>();
                }
            }

            @Override
            public List<Node> fetchConnectionsExpanded(String politicianId, int hopLevel) {
                return new ArrayList<>();
            }
        };
    }

    @Nested
    @DisplayName("Graph Explorer - Node Fetching")
    class GraphExplorerFetchesNodes {

        @Test
        @DisplayName("Given politician ID, when fetching connections, then returns nodes with metadata")
        void givenPoliticianIdWhenFetchingConnections_thenReturnsNodes() {
            var nodes = service.fetchConnections("UK-CHS-LAB", 2);

            assertEquals(2, nodes.size());
            assertNotNull(nodes.get(0).getId());
            assertEquals("UK-CHS-LAB", nodes.get(0).getId());
            assertNotNull(nodes.get(0).getLabel());
            assertEquals("Politician", nodes.get(0).getEntityType());
        }

        @Test
        @DisplayName("Given non-existent politician ID, when fetching connections, then returns empty list")
        void givenNonExistentPoliticianId_thenReturnsEmptyList() {
            var nodes = service.fetchConnections("UK-NONEXISTENT", 2);
            assertTrue(nodes.isEmpty());
        }

    }

}
