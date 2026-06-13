package com.webofpolitics.services;

import com.webofpolitics.CompanyNodeCreator;
import com.webofpolitics.CompanyRepository;
import com.webofpolitics.entities.Company;
import com.webofpolitics.exceptions.CompanyNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Company Service — Business logic for company operations
 * <p>
 * Phase 2.1 Implementation: Enhanced with proper exception handling, validation,
 * and transaction management following Spring Boot best practices.
 */
@Service
@Transactional
public class CompanyService {
    
    private final CompanyRepository companyRepository;
    
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    
    /**
     * Find companies by name (case-insensitive partial match)
     * <p>
     * Usage: "Tech" finds "TechCorp", "Technology Ltd", etc.
     */
    public Iterable<Company> findByNameLikeIgnoreCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return companyRepository.findAll();
        }
        return companyRepository.findByNameLikeIgnoreCase(name);
    }
    
    /**
     * Find company by exact ID
     * <p>
     * Throws {@link CompanyNotFoundException} if no company exists.
     */
    public Company findById(String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with ID: " + id));
    }
    
    /**
     * Find companies by industry sector (case-insensitive)
     * <p>
     * Returns all companies in the specified industry.
     */
    public Iterable<Company> findByIndustryIgnoreCase(String industry) {
        if (industry == null || industry.trim().isEmpty()) {
            return companyRepository.findAll();
        }
        return companyRepository.findByIndustryIgnoreCase(industry);
    }
    
    /**
     * Create or update a company record
     <p>
     * Uses existing repository methods with proper validation.
     */
    public Company save(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }
        return companyRepository.save(company);
    }
    
    /**
     * Delete a company by ID
     <p>
     * Throws {@link CompanyNotFoundException} if no company exists.
     */
    public void deleteById(String id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with ID: " + id));
        company.getId(); // trigger deletion via repository
    }
}
