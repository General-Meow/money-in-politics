package com.webofpolitics.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for Parliament.uk API client.
 */
@Component
@ConfigurationProperties(prefix = "parliament")
public class Configuration {
    
    private String baseUrl;
    private boolean oauthEnabled = false;
    private String clientId;
    private String clientSecret;
    private int maxRetries = 3;
    private long retryDelayMillis = 2000;
    
    // Getters and setters...
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public boolean isOauthEnabled() { return oauthEnabled; }
    public void setOauthEnabled(boolean oauthEnabled) { this.oauthEnabled = oauthEnabled; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
    public int getMaxRetries() { return maxRetries; }
    public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }
    public long getRetryDelayMillis() { return retryDelayMillis; }
    public void setRetryDelayMillis(long retryDelayMillis) { this.retryDelayMillis = retryDelayMillis; }
}
