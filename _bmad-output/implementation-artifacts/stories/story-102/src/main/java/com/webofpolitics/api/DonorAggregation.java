package com.webofpolitics.api;

/**
 * Donor aggregation helper for processing donations.
 */
public class DonorAggregation {

    private String id;
    private String name;
    private double totalAmount = 0;
    private int count = 0;
    private String type; // Corporate, Individual, Trust
    private String mostRecentDate;
    private String startDate;

    public DonorAggregation(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void addDonation(Donation donation) {
        totalAmount += donation.getAmount();
        count++;
        
        String dateStr = null;
        if (donation.getDate() != null && donation.getDate().toString() != null) {
            dateStr = donation.getDate().toString();
        }
        
        if (startDate == null || dateStr != null && Integer.parseInt(dateStr) < 
            (startDate != null ? Integer.parseInt(startDate) : Integer.MAX_VALUE)) {
            startDate = dateStr;
        }
        if (mostRecentDate == null || dateStr != null && 
            Integer.parseInt(dateStr) > (mostRecentDate != null ? 
                Integer.parseInt(mostRecentDate) : Integer.MIN_VALUE)) {
            mostRecentDate = dateStr;
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getTotalAmount() { return totalAmount; }
    public int getCount() { return count; }
    public String getType() { return type; }
    public String getMostRecentDate() { return mostRecentDate; }
    public String getStartDate() { return startDate; }

}
