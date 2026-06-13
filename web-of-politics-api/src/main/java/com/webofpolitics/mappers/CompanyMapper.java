package com.webofpolitics.mappers;

import com.webofpolitics.CompanyNodeCreator;
import com.webofpolitics.dto.CompanyDto;
import com.webofpolitics.entities.Company;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Company Entity ↔ DTO Mapper — Converts between entity and DTO representations
 */
public class CompanyMapper {
    
    /**
     * Convert company entity to DTO
     */
    public static CompanyDto toDto(Company company) {
        if (company == null) {
            return new CompanyDto();
        }
        
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setFullName(company.getFullName());
        dto.setIndustry(company.getIndustry());
        dto.setSubIndustry(company.getSubIndustry());
        dto.setAddress(company.getAddress());
        dto.setWebsite(company.getWebsite());
        dto.setEmployees(company.getEmployees());
        dto.setFounded(company.getFounded());
        dto.setHeadquarters(countryToCountryCode(company.getHeadquarters()));
        dto.setRevenue(company.getRevenue());
        dto.setStockSymbol(company.getStockSymbol());
        
        return dto;
    }
    
    /**
     * Convert DTO to company entity
     */
    public static Company toEntity(CompanyDto dto) {
        if (dto == null) {
            return new Company();
        }
        
        Company company = new Company();
        company.setId(dto.getId());
        company.setName(dto.getName());
        company.setFullName(dto.getFullName());
        company.setIndustry(dto.getIndustry());
        company.setSubIndustry(dto.getSubIndustry());
        company.setAddress(dto.getAddress());
        company.setWebsite(dto.getWebsite());
        company.setEmployees(dto.getEmployees());
        company.setFounded(dto.getFounded());
        company.setHeadquarters(companyCodeToCountry(dto.getHeadquarters()));
        company.setRevenue(dto.getRevenue());
        company.setStockSymbol(dto.getStockSymbol());
        
        return company;
    }
    
    /**
     * Convert list of companies to list of DTOs
     */
    public static List<CompanyDto> toDtoList(List<Company> companies) {
        if (companies == null) {
            return List.of();
        }
        return companies.stream()
                .map(CompanyMapper::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert list of DTOs to list of companies
     */
    public static List<Company> toEntityList(List<CompanyDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream()
                .map(CompanyMapper::toEntity)
                .collect(Collectors.toList());
    }
    
    private static String countryToCountryCode(String headquarters) {
        if (headquarters == null || headquarters.isEmpty()) {
            return null;
        }
        
        // Map common country names to ISO codes
        switch (headquarters.toLowerCase()) {
            case "united states": return "US";
            case "usa": return "US";
            case "china": return "CN";
            case "germany": return "DE";
            case "uk": return "GB";
            case "britain": return "GB";
            case "france": return "FR";
            case "japan": return "JP";
            default: return headquarters.toUpperCase();
        }
    }
    
    private static String companyCodeToCountry(String countryCode) {
        if (countryCode == null || countryCode.isEmpty()) {
            return null;
        }
        
        // Map ISO codes to common country names
        switch (countryCode) {
            case "US": return "United States";
            case "CN": return "China";
            case "DE": return "Germany";
            case "GB": return "United Kingdom";
            case "FR": return "France";
            case "JP": return "Japan";
            default: return countryCode;
        }
    }
}
