package com.webofpolitics.api;

import java.util.List;

/**
 * Top donors summary data structure.
 */
public class TopDonors {

    private final double totalAmount;
    private final int uniqueDonorCount;
    private final String mostRecentDate;
    private final List<DonorSummary> list;

    public TopDonors(double totalAmount, int uniqueDonorCount, String mostRecentDate, 
                     List<DonorSummary> list) {
        this.totalAmount = totalAmount;
        this.uniqueDonorCount = uniqueDonorCount;
        this.mostRecentDate = mostRecentDate;
        this.list = list;
    }

    public double getTotalAmount() { return totalAmount; }
    public int getUniqueDonorCount() { return uniqueDonorCount; }
    public String getMostRecentDate() { return mostRecentDate; }
    public List<DonorSummary> getList() { return list; }

}
