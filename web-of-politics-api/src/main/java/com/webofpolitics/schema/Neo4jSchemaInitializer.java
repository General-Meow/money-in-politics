package com.webofpolitics.schema;

import org.neo4j.driver.v1.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Neo4j Schema Initializer - Creates indexes and constraints on startup
 */
@Component
public class Neo4jSchemaInitializer {
    
    @Value("${spring.data.neo4j.uri:}")
    private String uri;
    
    @Value("${spring.data.neo4j.username:neo4j}")
    private String username;
    
    @Value("${spring.data.neo4j.password:neo4j}")
    private String password;
    
    /**
     * Initialize Neo4j database with indexes and constraints for performance
     */
    public void initializeSchema() {
        try (Session session = Session.openSession(uri, username, password)) {
            session.run("MATCH (n:Politician) WHERE n.id IS NOT NULL RETURN count(n)").consume();
            session.run("MATCH (n:Company) WHERE n.id IS NOT NULL RETURN count(n)").consume();
            
            // Create indexes for frequently queried properties
            session.run("""
                CREATE INDEX IF NOT EXISTS idx_politician_name 
                FOR (p:Politician) ON (p.name)
                """);
            
            session.run("""
                CREATE INDEX IF NOT EXISTS idx_company_name
                FOR (c:Company) ON (c.name)
                """);
            
            // Create indexes for relationship traversals
            session.run("""
                CREATE INDEX IF NOT EXISTS idx_politician_party 
                FOR (p:Politician) ON (p.party)
                """);
            
            session.run("""
                CREATE INDEX IF NOT EXISTS idx_relationship_type 
                FOR ()-[r:LOBBYING]->(n) ON (r.type)
                """);
            
            System.out.println("Neo4j schema initialized successfully.");
        } catch (Exception e) {
            System.err.println("Failed to initialize Neo4j schema: " + e.getMessage());
        }
    }
}
