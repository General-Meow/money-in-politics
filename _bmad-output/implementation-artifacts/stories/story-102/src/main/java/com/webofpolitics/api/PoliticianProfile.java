package com.webofpolitics.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Complete politician profile data structure.
 */
public class PoliticianProfile {

    private final boolean politicianNotNull;
    private final PoliticianSummary politician;
    private final TopDonors topDonors;
    private final List<ConnectionSummary> connections;
    private final List<RecentDonation> recentDonations;
    private final CoveragePeriod coveragePeriod;

    public PoliticianProfile(PoliticianSummary politician, TopDonors topDonors, 
                            List<ConnectionSummary> connections, 
                            List<RecentDonation> recentDonations, 
                            CoveragePeriod coveragePeriod) {
        this.politicianNotNull = (politician != null);
        this.politician = politician;
        this.topDonors = topDonors;
        this.connections = (connections != null) ? connections : new ArrayList<>();
        this.recentDonations = (recentDonations != null) ? recentDonations : new ArrayList<>();
        this.coveragePeriod = coveragePeriod;
    }

    public boolean isPoliticianNotNull() { return politicianNotNull; }
    public PoliticianSummary getPolitician() { return politician; }
    public TopDonors getTopDonors() { return topDonors; }
    public List<ConnectionSummary> getConnections() { return connections; }
    public List<RecentDonation> getRecentDonations() { return recentDonations; }
    public CoveragePeriod getCoveragePeriod() { return coveragePeriod; }

}
