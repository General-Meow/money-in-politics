package com.webofpolitics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * Web of Politics API - Spring Boot Application Entry Point
 * 
 * Layered Architecture:
 * - Controllers: REST API endpoints
 * - Services: Business logic layer  
 * - Facades: Interface abstraction layer (when needed)
 * - Dtos: Data Transfer Objects for API contracts
 * - Repositories: Neo4j data access abstraction
 * - Mappers: DTO <==> Entity conversion
 * - Entities: Neo4j/JPJ models
 */
@SpringBootApplication
@EnableNeo4jRepositories("com.webofpolitics.repositories")
public class WebOfPoliticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebOfPoliticsApplication.class, args);
    }
}
