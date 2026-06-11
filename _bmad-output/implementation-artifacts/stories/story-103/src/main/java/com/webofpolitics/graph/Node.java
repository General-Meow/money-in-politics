package com.webofpolitics.graph;

import java.time.LocalDate;

public interface Node {
    String getId();
    String getLabel();
    String getEntityType();
    LocalDate getStartDate();
    LocalDate getEndDate();
    String getAdditionalMetadata();
    int getSize();
    boolean isHasTooltip();
}
