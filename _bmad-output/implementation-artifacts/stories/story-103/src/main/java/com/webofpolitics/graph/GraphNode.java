package com.webofpolitics.graph;

import java.time.LocalDate;

/**
 * Graph Node DTO for graph visualization.
 */
public class GraphNode implements Node {

    private final String id;
    private final String label;
    private final String entityType; // Politician, Company, Party, etc.
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String additionalMetadata;
    private final int size; // For visual hierarchy
    private final boolean hasTooltip;

    public GraphNode(String id, String label, String entityType, 
                     LocalDate startDate, LocalDate endDate, 
                     String additionalMetadata, int size, boolean hasTooltip) {
        this.id = id;
        this.label = (label != null && !label.isEmpty()) ? label : "";
        this.entityType = entityType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.additionalMetadata = (additionalMetadata != null) ? additionalMetadata : "";
        this.size = size;
        this.hasTooltip = hasTooltip;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getLabel() { return label; }

    @Override
    public String getEntityType() { return entityType; }

    @Override
    public LocalDate getStartDate() { return startDate; }

    @Override
    public LocalDate getEndDate() { return endDate; }

    @Override
    public String getAdditionalMetadata() { return additionalMetadata; }

    @Override
    public int getSize() { return size; }

    @Override
    public boolean isHasTooltip() { return hasTooltip; }

}
