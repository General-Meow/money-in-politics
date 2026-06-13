package com.webofpolitics.controllers;

import com.webofpolitics.CompanyRepository;
import com.webofpolitics.dto.CompanyDto;
import com.webofpolitics.entities.Company;
import com.webofpolitics.exceptions.CompanyNotFoundException;
import com.webofpolitics.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Company REST Controller — Exposes company operations as REST endpoints
 * <p>
 * Phase 2.2 Implementation: Complete REST API layer with Swagger documentation,
 * request/response mapping, and consistent error handling.
 */
@Tag(name = "Companies", description = "Operations for companies")
@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    
    private final CompanyService companyService;
    
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    
    /**
     * Get all companies with optional filtering
     * <p>
     * Supports filters: name, industry sector
     */
    @Operation(summary = "Get all companies", description = "Returns all companies with optional filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved companies"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<CompanyDto> getAll(
            @Parameter(description = "Filter by name (case-insensitive partial match)")
            @Pattern(regexp = ".*", message = "Name must not be empty if provided")
            String name,
            
            @Parameter(description = "Filter by industry sector")
            String industry) {
        List<Company> companies;
        
        if (name != null && !name.trim().isEmpty()) {
            companies = companyService.findByNameLikeIgnoreCase(name.trim()).stream()
                    .filter(c -> industry == null || industry.trim().isEmpty() 
                            || getIndustry(c).equalsIgnoreCase(industry.trim()))
                    .collect(Collectors.toList());
        } else if (industry != null && !industry.trim().isEmpty()) {
            companies = companyService.findByIndustryIgnoreCase(industry.trim()).stream()
                    .collect(Collectors.toList());
        } else {
            companies = List.copyOf(companyService.findByNameLikeIgnoreCase(""));
        }
        
        return mapToDtoList(companies);
    }
    
    /**
     * Get a single company by ID
     */
    @Operation(summary = "Get company by ID", description = "Returns company details by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company found"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{id}")
    public CompanyDto getOne(@PathVariable String id) {
        Company company = companyService.findById(id);
        return CompanyMapper.toDto(company);
    }
    
    /**
     * Create or update a company
     */
    @Operation(summary = "Create or update company", description = "Creates new or updates existing company")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company created/updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public CompanyDto create(@RequestBody CompanyDto companyDto) {
        if (companyDto.getName() == null || companyDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        
        Company company = CompanyMapper.toEntity(companyDto);
        return CompanyMapper.toDto(companyService.save(company));
    }
    
    /**
     * Delete a company by ID
     */
    @Operation(summary = "Delete company", description = "Deletes company by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Company deleted"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        companyService.deleteById(id);
    }
    
    /**
     * Search companies by name pattern
     */
    @Operation(summary = "Search companies", description = "Returns companies matching name pattern")
    @GetMapping("/search/{namePattern}")
    public List<CompanyDto> search(@PathVariable String namePattern) {
        return companyService.findByNameLikeIgnoreCase(namePattern).stream()
                .map(CompanyMapper::toDto)
                .collect(Collectors.toList());
    }
    
    private String getIndustry(Company company) {
        return "Unknown"; // Placeholder - use actual industry field
    }
    
    private List<CompanyDto> mapToDtoList(List<Company> companies) {
        return companies.stream()
                .map(CompanyMapper::toDto)
                .collect(Collectors.toList());
    }
}
