package com.webofpolitics.repositories;

import com.webofpolitics.entities.Politician;
import org.springframework.data.neo4j.repository.Repository;
import org.springframework.data.neo4j.core CypherQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Politician Repository - Migrated from PoliticianNodeCreator.java data access layer (story-001)
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/PoliticianNodeCreator.java
 */
@Repository
public interface PoliticianRepository extends Repository<Politician, String> {
    
    /**
     * Find politicians by name (case-insensitive partial match)
     * 
     * @param nameName search query
     * @return list of politicians matching the name
     */
    Iterable<Politician> findByNameLikeIgnoreCase(String nameName);
    
    /**
     * Find politician by exact ID
     * 
     * @param id politician node ID
     * @return Optional containing politician if found
     */
    Optional<Politician> findById(String id);
    
    /**
     * Find current politicians by jurisdiction
     * 
     * @param jurisdiction WESTMINSTER, SCOTTISH_PARLIAMENT, etc.
     * @return list of active politicians in jurisdiction
     */
    Iterable<Politician> findByJurisdictionAndActive(String jurisdiction);
    
    /**
     * Find politicians by party name
     * 
     * @param party party name
     * @return list of politicians from that party
     */
    Iterable<Politician> findByPartyIgnoreCase(String party);
    
    /**
     * Find current politician by constituency
     * 
     * @param constituency constituency name
     * @return Optional containing politician if found
     */
    Optional<Politician> findByConstituencyIgnoreCase(String constituency);
    
    /**
     * Find politicians by tenure end date (current MPs with no end date)
     */
    Iterable<Politician> findByTenureEndIsNull();
    
    /**
     * Custom query for advanced search
     * 
     * @param cypherQuery Cypher query string
     * @return result of custom query
     */
    Optional<Object[]> findByCustom(CypherQuery cypherQuery);
}
