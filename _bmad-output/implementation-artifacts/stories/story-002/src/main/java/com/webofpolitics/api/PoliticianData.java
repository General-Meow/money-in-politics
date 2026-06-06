package com.webofpolitics.api;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO representing a politician profile from Parliament.uk API.
 */
public class PoliticianData {
    
    private String idPoliticianId;
    private String constituency;
    private String sex;
    private boolean active;
    private String biographyText;
    
    public static class Builder {
        private final String idPoliticianId;
        private String constituency;
        private String sex;
        private boolean active;
        private String biographyText;
        
        private Builder(String idPoliticianId) { this.idPoliticianId = idPoliticianId; }
        
        public Builder withConstituency(String constituency) { this.constituency = constituency; return this; }
        public Builder withSex(String sex) { this.sex = sex; return this; }
        public Builder setActive(boolean active) { this.active = active; return this; }
        public Builder withBiographyText(String biographyText) { this.biographyText = biographyText; return this; }
        
        public PoliticianData build() {
            return new PoliticianData(this);
        }
    }
    
    private PoliticianData(Builder builder) {
        this.idPoliticianId = builder.idPoliticianId;
        this.constituency = builder.constituency;
        this.sex = builder.sex;
        this.active = builder.active;
        this.biographyText = builder.biographyText;
    }
    
    // Getters and Setters...
    public String getIdPoliticianId() { return idPoliticianId; }
    public String getConstituency() { return constituency; }
    public String getSex() { return sex; }
    public boolean isActive() { return active; }
    public String getBiographyText() { return biographyText; }
}
