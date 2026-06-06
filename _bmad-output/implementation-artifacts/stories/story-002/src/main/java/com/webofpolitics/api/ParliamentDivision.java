package com.webofpolitics.api;

import java.time.LocalDate;

/**
 * DTO representing a division (vote outcome) from Parliament.uk API.
 */
public class ParliamentDivision {
    
    private String motionShortTitle;
    private LocalDate voteDate;
    private int totalVotingMembers;
    private int ayes;
    private int nos;
    
    public static class Builder {
        private String motionShortTitle;
        private LocalDate voteDate;
        private int totalVotingMembers;
        private int ayes;
        private int nos;
        
        public Builder withMotionShortTitle(String title) { this.motionShortTitle = title; return this; }
        public Builder withVoteDate(LocalDate date) { this.voteDate = date; return this; }
        public Builder withTotalVotingMembers(int total) { this.totalVotingMembers = total; return this; }
        public Builder withAyes(int ayes) { this.ayes = ayes; return this; }
        public Builder withNos(int nos) { this.nos = nos; return this; }
        
        public ParliamentDivision build() {
            return new ParliamentDivision(this);
        }
    }
    
    private ParliamentDivision(Builder builder) {
        this.motionShortTitle = builder.motionShortTitle;
        this.voteDate = builder.voteDate;
        this.totalVotingMembers = builder.totalVotingMembers;
        this.ayes = builder.ayes;
        this.nos = builder.nos;
    }
    
    public String getMotionShortTitle() { return motionShortTitle; }
    public LocalDate getVoteDate() { return voteDate; }
    public int getTotalVotingMembers() { return totalVotingMembers; }
    public int getAyes() { return ayes; }
    public int getNos() { return nos; }
}
