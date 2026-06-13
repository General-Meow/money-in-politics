package com.webofpolitics.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * OAuth2 Password Grant Handler — Handles authentication endpoint for REST API
 * <p>
 * Phase 3 Implementation: Password grant flow for token-based authentication.
 */
@RestController
@RequestMapping("/oauth")
public class OAuth2PasswordGrant {
    
    /**
     * Handle password grant token request
     * 
     * POST /oauth/token
     * Content-Type: application/x-www-form-urlencoded
     * 
     * Body: username={user}, password={pass}, grant_type=password
     * 
     * Response: { access_token, token_type, expires_in }
     */
    @PostMapping("/token")
    public ResponseEntity<AuthTokenResponse> authenticate(
            @RequestParam String username,
            @RequestParam(required = false) String password,
            @RequestParam(defaultValue = "password") String grantType) {
        
        // For Phase 3: Simplified authentication (will integrate with actual auth in future)
        if (grantType.equals("password")) {
            // TODO: Validate credentials against Neo4j/user database
            
            // Example hardcoded admin for testing (remove in production)
            if ("admin".equals(username) && password != null) {
                JwtTokenProvider tokenProvider = 
                    new JwtTokenProvider(System.getenv("JWT_SECRET") || "default-secret", 3600000L);
                
                String[] roles;
                // TODO: Determine role from user database
                if (password.equals("admin")) {
                    roles = new String[] {"ADMIN"};
                } else {
                    roles = new String[] {"USER"};
                }
                
                String token = tokenProvider.generateToken(username, roles);
                
                AuthTokenResponse authResponse = new AuthTokenResponse();
                authResponse.setAccessToken(token);
                authResponse.setTokenType("Bearer");
                authResponse.setExpiresIn(3600); // 1 hour
                authResponse.setUsername(username);
                authResponse.setGrantedAuthorities(java.util.Arrays.asList(roles));
                
                return ResponseEntity.ok(authResponse);
            } else {
                throw new org.springframework.security.core.AuthenticationException("Invalid credentials");
            }
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    /**
     * AuthToken response DTO for password grant
     */
    public static class AuthTokenResponse {
        private String accessToken;
        private String tokenType;
        private int expiresIn;
        private String username;
        private java.util.List<String> grantedAuthorities;
        
        // Getters and setters
        public String getAccessToken() { return accessToken; }
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
        
        public String getTokenType() { return tokenType; }
        public void setTokenType(String tokenType) { this.tokenType = tokenType; }
        
        public int getExpiresIn() { return expiresIn; }
        public void setExpiresIn(int expiresIn) { this.expiresIn = expiresIn; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public java.util.List<String> getGrantedAuthorities() { 
            return grantedAuthorities; 
        }
        public void setGrantedAuthorities(java.util.List<String> authorities) { 
            this.grantedAuthorities = authorities; 
        }
    }
}
