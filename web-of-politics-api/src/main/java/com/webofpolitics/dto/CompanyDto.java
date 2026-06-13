package com.webofpolitics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Company DTO - API contract for company data (complete version)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    
    // Basic identity
    private String id;
    private String name;
    private String officialName;
    private String tradingName;
    
    // Industry and sector
    private String industry;
    private String sicCode;
    private String naicsCode;
    
    // Location information
    private String registeredAddress;
    private String website;
    
    // Incorporation details
    private LocalDate incorporatedDate;
    private String registeredCountry;
    private String companyNumber;
    
    // Organization type and status
    private String companyType;
    private String status;
}
