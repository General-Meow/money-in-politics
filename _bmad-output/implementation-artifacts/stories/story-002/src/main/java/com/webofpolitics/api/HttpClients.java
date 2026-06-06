package com.webofpolitics.api;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Component;

/**
 * HTTP clients for Parliament.uk API with OAuth/OIDC authentication support.
 */
@Component
public class HttpClients {
    
    private static final String BASE_URL = "https://services.parliament.uk/develop/api/v3.1";
    
    /**
     * Create WebClient with OAuth/OIDC credentials.
     * 
     * TODO: When OAuth/OIDC tokens available, replace placeholders with actual values.
     */
    public WebClient createClientWithOAuth() {
        // TODO: Initialize with actual OAuth/OIDC configuration
        // Example:
        // return WebClient.create(BASE_URL)
        //         .header(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);
        throw new UnsupportedOperationException("OAuth credentials not configured yet");
    }
    
    /**
     * Create WebClient for testing with mocked/placeholder responses.
     */
    public WebClient createTestClient() {
        return WebClient.create(BASE_URL);
    }
}
