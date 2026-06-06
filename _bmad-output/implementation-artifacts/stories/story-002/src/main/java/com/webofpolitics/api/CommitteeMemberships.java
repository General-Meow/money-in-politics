package com.webofpolitics.api;

import java.util.List;

/**
 * DTO representing committee memberships from Parliament.uk API.
 */
public class CommitteeMemberships {
    
    private List<Membership> memberships;
    
    public static class Builder {
        private String politicianId;
        private List<Membership> memberships;
        
        private Builder(String politicianId) { this.politicianId = politicianId; }
        
        public Builder withMemberships(List<Membership> memberships) { 
            this.memberships = memberships; return this; 
        }
        
        public CommitteeMemberships build() {
            return new CommitteeMemberships(this);
        }
    }
    
    private final String politicianId;
    private CommitteeMemberships(Builder builder) {
        this.politicianId = builder.politicianId;
        this.memberships = builder.memberships;
    }
}

class Membership {
    private String committeeShortTitle;
    private int meetingNumber;
    
    public String getCommitteeShortTitle() { return committeeShortTitle; }
    public int getMeetingNumber() { return meetingNumber; }
}
