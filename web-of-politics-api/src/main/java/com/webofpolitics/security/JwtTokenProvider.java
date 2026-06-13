package com.webofpolitics.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Token Provider — Generates and validates JWT tokens
 * <p>
 * Phase 3 Implementation: OAuth2 token-based authentication for REST API.
 */
@Component
public class JwtTokenProvider {
    
    private final String secret;
    private final long jwtExpirationInMilliseconds;
    
    /**
     * Constructor with configuration values
     * 
     * @param secret JWT signing key (use environment variable in production)
     * @param jwtExpirationInMilliseconds Token expiration time (default: 1 hour)
     */
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration.milliseconds:3600000}") long jwtExpirationInMilliseconds) {
        this.secret = secret;
        this.jwtExpirationInMilliseconds = jwtExpirationInMilliseconds;
    }
    
    /**
     * Generate JWT token for user with roles
     * 
     * @param username User's unique identifier (e.g., email or ID)
     * @param roles User's roles (e.g., "ADMIN", "USER")
     * @return JWT token string
     */
    public String generateToken(String username, String[] roles) {
        Map<String, Object> claims = new HashMap<>();
        
        if (roles != null && roles.length > 0) {
            claims.put("roles", roles);
        }
        
        return createToken(claims, username, Date.now() + jwtExpirationInMilliseconds);
    }
    
    /**
     * Generate token without specific roles
     * 
     * @param username User identifier
     * @return JWT token string
     */
    public String generateToken(String username) {
        return generateToken(username, null);
    }
    
    /**
     * Create JWT token with claims and expiration
     */
    private String createToken(
            Map<String, Object> claims, 
            String subject, 
            long expiryTime) {
        
        Date now = new Date();
        Date expiryDate = new Date(expiryTime);
        
        // Use secret key for better security than string-based signing
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }
    
    /**
     * Extract username from JWT token
     * 
     * @param token JWT token string
     * @return Username (subject claim), or null if invalid
     */
    public String getUserNameFromToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Unable to parse JWT token: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Extract roles from JWT token
     * 
     * @param token JWT token string
     * @return Array of role strings, or null if not present/invalid
     */
    public String[] getRolesFromToken(String token) {
        try {
            Claims claims = parseClaims(token);
            Object rolesObj = claims.get("roles");
            
            if (rolesObj != null && rolesObj instanceof java.util.List) {
                java.util.List<?> roleList = (java.util.List<?>) rolesObj;
                return roleList.stream()
                    .map(role -> {
                        if (role instanceof String) {
                            return (String) role;
                        } else if (role instanceof Number) {
                            // Handle cases where role might be a number
                            return ((Number) role).intValue();
                        }
                        return "UNKNOWN";
                    })
                    .toArray(String[]::new);
            }
            
            return null;
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Unable to extract roles from JWT token: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Extract role by name from JWT token
     * 
     * @param token JWT token string
     * @param roleName Role name to search for (e.g., "ADMIN", "USER")
     * @return true if user has the specified role, false otherwise
     */
    public boolean hasRole(String token, String roleName) {
        try {
            Claims claims = parseClaims(token);
            Object rolesObj = claims.get("roles");
            
            if (rolesObj instanceof java.util.List) {
                java.util.List<?> roleList = (java.util.List<?>) rolesObj;
                for (Object role : roleList) {
                    if (role instanceof String && roleName.equals(role)) {
                        return true;
                    }
                }
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Validate JWT token without extracting claims
     * 
     * @param token JWT token string to validate
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Parse JWT claims for internal use
     */
    private Claims parseClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    /**
     * Check if token is expired
     * 
     * @param token JWT token string
     * @return true if expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true; // Assume expired if we can't validate
        }
    }
}
