package com.webofpolitics.api;

/**
 * Connection relationship summary for politician profile.
 */
public class ConnectionSummary {

    private final String nodeId;
    private final String nodeName;
    private final String nodeType; // Politician, Company, Party
    private final String relationshipType;
    private final String startDate;
    private final String endDate;
    private final String sourcePoliticianId;

    public ConnectionSummary(String nodeId, String nodeName, String nodeType, 
                            String relationshipType, String startDate, String endDate,
                            String sourcePoliticianId) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.nodeType = nodeType;
        this.relationshipType = relationshipType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sourcePoliticianId = sourcePoliticianId;
    }

    public String getNodeId() { return nodeId; }
    public String getNodeName() { return nodeName; }
    public String getNodeType() { return nodeType; }
    public String getRelationshipType() { return relationshipType; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getSourcePoliticianId() { return sourcePoliticianId; }

}
