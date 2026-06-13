package com.webofpolitics.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration — JWT-based authentication and authorization
 * <p>
 * Phase 3 Implementation: Configure security rules, endpoints, and filters.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    private final String jwtSecret;
    private final Long jwtExpirationMs;
    @Value("${spring.profiles.active}")
    private String profile;
    
    /**
     * Constructor with configuration values
     */
    public SecurityConfig(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration.milliseconds:3600000}") Long jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }
    
    /**
     * Configure HTTP security rules
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless JWT auth
            .authorizeHttpRequests(auth -> auth
                // Public endpoints (no authentication required)
                .requestMatchers("/api/v1/politicians/**").permitAll()
                .requestMatchers("/api/v1/companies/**").permitAll()
                
                // OAuth2 token endpoint (for initial login)
                .requestMatchers("/oauth/token").permitAll()
                
                // Admin endpoints (require ADMIN role)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**/{id}").hasRole("ADMIN")
                
                // Write operations (CREATE/UPDATE - require USER role minimum)
                .requestMatchers(HttpMethod.POST, "/api/v1/politicians/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/politicians/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/companies/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/companies/**").hasAnyRole("ADMIN", "USER")
                
                // All other requests require USER role minimum
                .anyRequest().hasAnyRole("ADMIN", "USER", "GUEST")
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .authenticationManagerResolver(authenticationManagerResolver())
                    .tokenServices(token -> null) // Custom token handling in filter
                )
            );
        
        return http.build();
    }
    
    /**
     * BCrypt password encoder for user registration/login
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Authentication manager resolver for OAuth2 JWT resources server
     */
    private org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationManager 
            authenticationManagerResolver() {
        // This would be configured with actual Spring Security OAuth2 JWT settings
        // For Phase 3, custom JwtAuthenticationFilter handles this
        return null; // Placeholder - use JwtAuthenticationFilter instead
    }
    
    /**
     * Additional security rules for Swagger/OpenAPI documentation
     */
    @Bean
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}
