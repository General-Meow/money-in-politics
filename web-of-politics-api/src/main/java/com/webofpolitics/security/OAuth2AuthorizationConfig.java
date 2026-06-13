package com.webofpolitics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

/**
 * OAuth2 Authorization Configuration — Token-based authentication endpoints
 * <p>
 * Phase 3 Implementation: OAuth2 password grant flow for initial login.
 */
@Configuration
@EnableWebSecurity
public class OAuth2AuthorizationConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Allow token request without authentication (for initial login)
                .requestMatchers("/oauth/token").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API
            .httpBasic(basic -> basic.realm("API Authentication"));
        
        return http.build();
    }
    
    /**
     * Handle OAuth2 token requests (password grant flow)
     * 
     * POST /oauth/token
     * Content-Type: application/x-www-form-urlencoded
     * Body: username={user}, password={pass}, grant_type=password
     */
    public Jwt authenticateUser(String username, String password, String grantType) {
        // TODO: Integrate with Spring Security OAuth2 resourceserver
        // For now, this is a placeholder implementation
        
        if (grantType == null || !grantType.equals("password")) {
            throw new AuthenticationServiceException(
                "Unsupported grant_type: " + grantType);
        }
        
        // TODO: Authenticate against Neo4j or user database
        // This would check credentials and return JWT token
        
        // Example (placeholder):
        if ("admin".equals(username) && "admin".equals(password)) {
            JwtTokenProvider tokenProvider = 
                new JwtTokenProvider("your-secret-key", 3600000L);
            String token = tokenProvider.generateToken(username, new String[] {"ADMIN"});
            
            // Return token in response (handled by Spring's OAuth2 resourceserver)
            return buildJwtToken(username, new String[] {"ADMIN"}, token);
        }
        
        throw new AuthenticationException("Invalid credentials");
    }
    
    /**
     * Build JWT token from claims
     */
    private Jwt buildJwtToken(String username, String[] roles, String token) {
        return org.springframework.security.oauth2.jwt.Jwt.builder()
            .subject(username)
            .claims(claims -> {
                Set<String> roleSet = java.util.stream.Stream.of(roles)
                    .map(SimpleGrantedAuthority::new)
                    .map(SimpleGrantedAuthority::getAuthority)
                    .distinct()
                    .collect(java.util.stream.Collectors.toSet());
                
                claims.put("roles", Collections.unmodifiableSet(roleSet));
            })
            .tokenType(Jwt.TokenType.BEARER.value())
            .build();
    }
    
    /**
     * Extract username from token (for authorization headers)
     */
    public String extractUsername(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        
        // Extract the actual token (without "Bearer " prefix)
        String tokenPart = token.substring(7);
        return extractClaims(tokenPart).getSubject();
    }
    
    /**
     * Extract roles from token (for authorization headers)
     */
    public String[] extractRoles(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return new String[0];
        }
        
        // Extract the actual token (without "Bearer " prefix)
        String tokenPart = token.substring(7);
        
        try {
            org.springframework.security.oauth2.jwt.Jwt jwt = 
                JwtParser.parse(tokenPart, true); // Simplified for Phase 3
            
            return jwt.getClaimAsStringSet("roles").toArray(new String[0]);
        } catch (Exception e) {
            return new String[0];
        }
    }
    
    /**
     * Extract claims from token (simplified for Phase 3)
     */
    private Claims extractClaims(String tokenPart) {
        // Placeholder - actual JWT extraction in real implementation
        return null;
    }
}
