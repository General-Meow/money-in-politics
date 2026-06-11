package com.webofpolitics.graph;

import java.time.LocalDate;

/**
 * Relationship DTO for graph edges (connections between nodes).
 */
public class GraphRelationship {

    private final String id;
    private final String fromNodeId;
    private final String toNodeId;
    private final String relationshipType; // family, board, donation, member_of, etc.
    private final boolean isBidirectional;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public GraphRelationship(String id, String fromNodeId, String toNodeId, 
                            String relationshipType, boolean isBidirectional, 
                            LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.relationshipType = relationshipType;
        this.isBidirectional = isBidirectional;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() { return id; }
    public String getFromNodeId() { return fromNodeId; }
    public String getToNodeId() { return toNodeId; }
    public String getRelationshipType() { return relationshipType; }
    public boolean isBidirectional() { return isBidirectional; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

}
