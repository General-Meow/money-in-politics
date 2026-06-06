package com.webofpolitics.api;

import org.springframework.stereotype.Component;

/**
 * HTML parsing utility for web scraping.
 */
@Component
public class HtmlParser {
    
    // TODO: Implement DOM parser (Jsoup or similar)
    
    /**
     * Extract politician name from HTML content.
     */
    public String extractPoliticianName(String htmlContent) {
        // TODO: Implement HTML parsing
        throw new UnsupportedOperationException("STORY-005: Not yet implemented");
    }
    
    /**
     * Extract donation amount from HTML content.
     */
    public Long extractDonationAmount(String htmlContent) {
        // TODO: Implement HTML parsing
        throw new UnsupportedOperationException("STORY-005: Not yet implemented");
    }
}
