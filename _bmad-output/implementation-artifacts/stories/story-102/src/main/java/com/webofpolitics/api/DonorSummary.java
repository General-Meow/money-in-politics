package com.webofpolitics.api;

/**
 * Donor summary data object for API responses.
 */
public class DonorSummary {

    private final String id;
    private final String name;
    private final double totalAmount;
    private final int count;
    private final String type; // Corporate, Individual, Trust
    private final boolean hasDetail;

    public DonorSummary(String id, String name, double totalAmount, int count, 
                       String type, boolean hasDetail) {
        this.id = id;
        this.name = (name != null && !name.isEmpty()) ? name : "";
        this.totalAmount = totalAmount;
        this.count = count;
        this.type = (type != null) ? type : "";
        this.hasDetail = hasDetail;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getTotalAmount() { return totalAmount; }
    public int getCount() { return count; }
    public String getType() { return type; }
    public boolean isHasDetail() { return hasDetail; }

}
