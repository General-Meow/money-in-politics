package com.webofpolitics.api;

/**
 * Data coverage period for donations.
 */
public class CoveragePeriod {

    private final int startYear;
    private final int endYear;

    public CoveragePeriod(int startYear, int endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public int getStartYear() { return startYear; }
    public int getEndYear() { return endYear; }

}
