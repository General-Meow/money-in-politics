package com.webofpolitics.api;

/**
 * Recent donation record for timeline display.
 */
public class RecentDonation {

    private final int date; // YYYYMMDD format for easy parsing
    private final String donorName;
    private final double amount;
    private final String relationshipType;

    public RecentDonation(int date, String donorName, double amount, String relationshipType) {
        this.date = date;
        this.donorName = donorName;
        this.amount = amount;
        this.relationshipType = relationshipType;
    }

    public int getDate() { return date; }
    public String getDonorName() { return donorName; }
    public double getAmount() { return amount; }
    public String getRelationshipType() { return relationshipType; }

}
