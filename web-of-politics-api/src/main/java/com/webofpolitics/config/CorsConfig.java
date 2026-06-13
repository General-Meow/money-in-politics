package com.webofpolitics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationRegistry;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * CORS Configuration — Enables cross-origin requests for the REST API
 * <p>
 * Phase 2.2 Implementation: Allows browsers to make requests from different origins.
 */
@Configuration
public class CorsConfig {
    
    /**
     * Configure CORS filter with allowed origins and headers
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow common frontend origins (add your specific origins in production)
        List<String> allowedOrigins = Arrays.asList(
            "http://localhost:3000",
            "http://127.0.0.1:3000"
        );
        
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowCredentials(true);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList(
            "Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With"
        ));
        config.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationRegistry registry = 
            new UrlBasedCorsConfigurationRegistry();
        registry.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(registry);
    }
}
