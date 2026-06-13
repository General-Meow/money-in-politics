package com.webofpolitics.repositories;

import com.webofpolitics.entities.Relationship;
import org.springframework.data.neo4j.repository.Repository;
import org.springframework.stereotype.Repository;

/**
 * Relationship Repository - Migrated from RelationshipBuilder.java data access layer (story-001)
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/RelationshipBuilder.java
 */
@Repository
public interface RelationshipRepository extends Repository<Relationship, String> {
    
    /**
     * Find relationships by type
     * 
     * @param type relationship type (FAMILY, BOARD_SEAT, etc.)
     * @return list of relationships of given type
     */
    Iterable<Relationship> findByType(Relationship.RelationshipType type);
    
    /**
     * Find relationships by source entity ID
     * 
     * @param id source entity ID (politician or company)
     * @return list of relationships connected to source
     */
    Iterable<Relationship> findBySourceId(String id);
    
    /**
     * Find relationships by target entity ID
     * 
     * @param id target entity ID (politician or company)
     * @return list of relationships connected to target
     */
    Iterable<Relationship> findByTargetId(String id);
    
    /**
     * Find relationships by amount range
     * 
     * @param min minimum amount
     * @param max maximum amount
     * @return list of relationships within amount range
     */
    Iterable<Relationship> findByAmountBetween(BigDecimal min, BigDecimal max);
    
    /**
     * Find active relationships (ongoing)
     * 
     * @return list of relationships that are currently active
     */
    Iterable<Relationship> findActive();
}
