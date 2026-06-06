package com.webofpolitics.api;

/**
 * DTO representing politician biography from Parliament.uk API.
 */
public class BiographyText {
    
    private String biographyText;
    
    public static class Builder {
        private String politicianId;
        private String biographyText;
        
        private Builder(String politicianId) { this.politicianId = politicianId; }
        
        public Builder withBiographyText(String biographyText) { 
            this.biographyText = biographyText; return this; 
        }
        
        public BiographyText build() {
            return new BiographyText(this);
        }
    }
    
    private final String politicianId;
    private BiographyText(Builder builder) {
        this.politicianId = builder.politicianId;
        this.biographyText = builder.biographyText;
    }
    
    public String getBiographyText() { return biographyText; }
}
