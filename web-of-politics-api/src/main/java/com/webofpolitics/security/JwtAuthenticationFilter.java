package com.webofpolitics.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * JWT Authentication Filter — Validates JWT tokens and sets security context
 * <p>
 * Phase 3 Implementation: Token-based authentication for REST API endpoints.
 */
public class JwtAuthenticationFilter extends PreAuthenticatedProcessingFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    
    /**
     * Constructor with JwtTokenProvider
     * 
     * @param authenticationManager Spring Security's AuthenticationManager
     * @param jwtTokenProvider JWT token provider for decoding tokens
     */
    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        setRequiresAuthenticationRequestMatcher(jwtTokenMatcher());
    }
    
    /**
     * Create JWT token matcher for Authorization header
     */
    private String jwtTokenMatcher() {
        return "Bearer";
    }
    
    @Override
    public void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain chain) 
            throws IOException, ServletException {
        final String authHeader = extractAuthToken(request);
        
        if (StringUtils.hasText(authHeader)) {
            try {
                // Validate token and get username from JWT claims
                String username = jwtTokenProvider.getUserNameFromToken(authHeader);
                
                if (username != null) {
                    // Decode roles from JWT token
                    String[] roles = jwtTokenProvider.getRolesFromToken(authHeader);
                    
                    // Create pre-authenticated user with roles
                    PreAuthenticatedAuthenticationToken token = 
                        new PreAuthenticatedAuthenticationToken(username, authHeader);
                    
                    // Set authorities from roles (e.g., "ADMIN", "USER")
                    if (roles != null && roles.length > 0) {
                        for (String role : roles) {
                            token.setAuthorities(java.util.Collections.singletonList(
                                new org.springframework.security.core.userdetails.UserAuthorityHolder(role)));
                        }
                    }
                    
                    // Authenticate and set security context
                    Authentication authResult = getAuthenticationManager().authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                }
            } catch (Exception e) {
                logger.error("Could not set user authentication: " + e.getMessage());
            }
        }
        
        chain.doFilter(request, response);
    }
    
    /**
     * Extract JWT token from Authorization header
     * 
     * @param request HTTP request
     * @return JWT token string without "Bearer " prefix, or null if not present
     */
    private String extractAuthToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authHeader)) {
            // Return the token part after "Bearer " prefix
            return authHeader.substring(7);
        }
        return null;
    }
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
}
