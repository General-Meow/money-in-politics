package com.webofpolitics.api;

import java.util.List;

/**
 * DTO representing voting records from Parliament.uk API.
 */
public class VotingRecords {
    
    private String politicianId;
    private List<Vote> votes;
    
    public static class Builder {
        private String politicianId;
        private List<Vote> votes;
        
        private Builder(String politicianId) { this.politicianId = politicianId; }
        
        public Builder withVotes(List<Vote> votes) { this.votes = votes; return this; }
        
        public VotingRecords build() {
            return new VotingRecords(this);
        }
    }
    
    private VotingRecords(Builder builder) {
        this.politicianId = builder.politicianId;
        this.votes = builder.votes;
    }
}

class Vote {
    private String motionShortTitle;
    private String voteType; // "Aye", "No", "Abstain"
    private java.time.LocalDate voteDate;
    
    public String getMotionShortTitle() { return motionShortTitle; }
    public String getVoteType() { return voteType; }
    public java.time.LocalDate getVoteDate() { return voteDate; }
}

class Division {
    private String memberParty;
    private boolean memberVotedAye;
    
    public String getMemberParty() { return memberParty; }
    public boolean isMemberVotedAye() { return memberVotedAye; }
}
