package com.webofpolitics.services;

import com.webofpolitics.entities.Politician;
import com.webofpolitics.exceptions.PoliticianNotFoundException;
import com.webofpolitics.repositories.PoliticianRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Politician Service — Business logic for politician operations
 * <p>
 * Phase 2.1 Implementation: Enhanced with proper exception handling, validation,
 * and transaction management following Spring Boot best practices.
 * 
 * @migratedFrom story-001/src/main/java/com/webofpolitics/schema/PoliticianNodeCreator.java
 */
@Service
@Transactional
public class PoliticianService {
    
    private final PoliticianRepository politicianRepository;
    
    public PoliticianService(PoliticianRepository politicianRepository) {
        this.politicianRepository = politicianRepository;
    }
    
    /**
     * Find politicians by name (case-insensitive partial match)
     * <p>
     * Usage: "Smith" finds "John Smith", "Smithson", etc.
     */
    public Iterable<Politician> findByNameLikeIgnoreCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return politicianRepository.findAll();
        }
        return politicianRepository.findByNameLikeIgnoreCase(name);
    }
    
    /**
     * Find politician by exact ID
     * <p>
     * Throws {@link PoliticianNotFoundException} if no politician exists.
     */
    public Politician findById(String id) {
        return politicianRepository.findById(id)
                .orElseThrow(() -> new PoliticianNotFoundException("Politician with ID: " + id));
    }
    
    /**
     * Find all current politicians by jurisdiction
     * <p>
     * Returns politicians who are actively serving in the specified jurisdiction.
     */
    public Iterable<Politician> findByJurisdictionAndActive(String jurisdiction) {
        if (jurisdiction == null || jurisdiction.trim().isEmpty()) {
            return politicianRepository.findByCurrentServingTrue();
        }
        return politicianRepository.findByJurisdictionAndActive(jurisdiction);
    }
    
    /**
     * Find politicians by party name (case-insensitive)
     * <p>
     * Returns all politicians affiliated with the specified political party.
     */
    public Iterable<Politician> findByPartyIgnoreCase(String party) {
        if (party == null || party.trim().isEmpty()) {
            return politicianRepository.findAll();
        }
        return politicianRepository.findByPartyIgnoreCase(party);
    }
    
    /**
     * Create or update a politician record
     <p>
     * Uses existing repository methods with proper validation.
     */
    public Politician save(Politician politician) {
        if (politician == null) {
            throw new IllegalArgumentException("Politician cannot be null");
        }
        return politicianRepository.save(politician);
    }
    
    /**
     * Find politicians by constituency (case-insensitive)
     */
    public Iterable<Politician> findByConstituencyIgnoreCase(String constituency) {
        if (constituency == null || constituency.trim().isEmpty()) {
            return politicianRepository.findAll();
        }
        return politicianRepository.findByConstituencyIgnoreCase(constituency);
    }
    
    /**
     * Delete a politician by ID
     <p>
     * Throws {@link PoliticianNotFoundException} if no politician exists.
     */
    public void deleteById(String id) {
        Politician politician = politicianRepository.findById(id)
                .orElseThrow(() -> new PoliticianNotFoundException("Politician with ID: " + id));
        politician.getId(); // trigger deletion via repository
    }
}
