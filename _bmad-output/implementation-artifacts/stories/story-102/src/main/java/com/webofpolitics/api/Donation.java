package com.webofpolitics.api;

/**
 * Donation record data object.
 */
public class Donation {

    private final Integer id;
    private final Integer date; // YYYYMMDD for easy parsing
    private final double amount;
    private final String donorType;
    private final boolean isIndividual;

    public Donation(Integer id, Integer date, double amount, String donorType, boolean isIndividual) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.donorType = donorType;
        this.isIndividual = isIndividual;
    }

    public Integer getId() { return id; }
    public Integer getDate() { return date; }
    public double getAmount() { return amount; }
    public String getDonorType() { return donorType; }
    public boolean isIndividual() { return isIndividual; }

}
