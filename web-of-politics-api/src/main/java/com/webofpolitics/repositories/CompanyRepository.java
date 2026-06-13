package com.webofpolitics.repositories;

import com.webofpolitics.entities.Company;
import org.springframework.data.neo4j.repository.Repository;
import org.springframework.data.neo4j.core CypherQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Company Repository - Migrated from CompanyNodeCreator.java data access layer (story-001)
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/CompanyNodeCreator.java
 */
@Repository
public interface CompanyRepository extends Repository<Company, String> {
    
    /**
     * Find companies by name (case-insensitive partial match)
     * 
     * @param nameName search query
     * @return list of companies matching the name
     */
    Iterable<Company> findByNameLikeIgnoreCase(String nameName);
    
    /**
     * Find company by exact ID
     * 
     * @param id company node ID
     * @return Optional containing company if found
     */
    Optional<Company> findById(String id);
    
    /**
     * Find companies by industry sector
     * 
     * @param industry industry name
     * @return list of companies in that industry
     */
    Iterable<Company> findByIndustryIgnoreCase(String industry);
    
    /**
     * Find companies by registered address (contains)
     * 
     * @param address partial address string
     * @return list of companies with matching address
     */
    Iterable<Company> findByRegisteredAddressContains(String address);
    
    /**
     * Find active companies by status
     * 
     * @paramStatus company status (ACTIVE, DORMANT, etc.)
     * @return list of companies with given status
     */
    Iterable<Company> findByStatusIgnoreCase(String status);
    
    /**
     * Custom query for advanced search
     * 
     * @param cypherQuery Cypher query string
     * @return result of custom query
     */
    Optional<Object[]> findByCustom(CypherQuery cypherQuery);
}
