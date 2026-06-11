package com.webofpolitics.search;

import java.util.Date;

/**
 * Politician Summary - DTO for search results.
 * 
 * STORY-101 Acceptance Criteria:
 * - [ ] Results show politician photo, full name, party affiliation
 * - [ ] Current/former status clearly indicated
 * - [ ] Constituency included in result
 */
public class PoliticianSummary {

    private final String id;
    private final String fullName;
    private final String name;
    private final String party;
    private final String constituency;
    private final String photoUrl;
    private final String role;
    private final boolean former;
    private final String currentSince;

    public PoliticianSummary(String id, String fullName, String name, String party, 
                            String constituency, String photoUrl, String role, boolean former, String currentSince) {
        this.id = id;
        this.fullName = fullName;
        this.name = name;
        this.party = party;
        this.constituency = (constituency != null && !constituency.isEmpty()) ? constituency : "";
        this.photoUrl = photoUrl;
        this.role = role;
        this.former = former;
        this.currentSince = currentSince;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public String getConstituency() {
        return constituency;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getRole() {
        return role;
    }

    public boolean isFormer() {
        return former;
    }

    public String getCurrentSince() {
        return currentSince;
    }
}
