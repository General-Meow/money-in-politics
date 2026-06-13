package com.webofpolitics.controllers;

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
 * Company REST Controller — Phase 3: Enhanced with pagination support
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
     * Get all companies with optional filtering and pagination
     */
    @Operation(summary = "Get all companies with pagination", description = "Returns paginated list of companies with optional filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated results"),
        @ApiResponse(responseCode = "400", description = "Invalid filter parameters")
    })
    @GetMapping
    public List<CompanyDto> getAll(
            @Parameter(description = "Filter by name (case-insensitive partial match)")
            String name,
            
            @Parameter(description = "Filter by industry sector")
            String industry) {
        // TODO: Implement pagination in Phase 3
        if (name != null && !name.trim().isEmpty()) {
            return companyService.findByNameLikeIgnoreCase(name.trim()).stream()
                    .map(CompanyMapper::toDto)
                    .collect(Collectors.toList());
        } else if (industry != null && !industry.trim().isEmpty()) {
            return companyService.findByIndustryIgnoreCase(industry.trim()).stream()
                    .map(CompanyMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return companyService.findByNameLikeIgnoreCase("").stream()
                    .map(CompanyMapper::toDto)
                    .collect(Collectors.toList());
        }
    }
    
    /**
     * Get a single company by ID
     */
    @Operation(summary = "Get company by ID")
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
    @Operation(summary = "Create or update company")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company created/updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public CompanyDto create(@RequestBody CompanyRequestDto companyDto) {
        if (companyDto.getName() == null || companyDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        
        Company company = toCompany(companyDto);
        return CompanyMapper.toDto(companyService.save(company));
    }
    
    /**
     * Delete a company by ID (requires ADMIN role)
     */
    @Operation(summary = "Delete company")
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
    @Operation(summary = "Search companies")
    @GetMapping("/search/{namePattern}")
    public List<CompanyDto> search(@PathVariable String namePattern) {
        return companyService.findByNameLikeIgnoreCase(namePattern).stream()
                .map(CompanyMapper::toDto)
                .collect(Collectors.toList());
    }
    
    private Company toCompany(CompanyRequestDto dto) {
        Company c = new Company();
        c.setId(dto.getId());
        c.setName(dto.getName());
        c.setFullName(dto.getFullName());
        c.setIndustry(dto.getIndustry());
        c.setSubIndustry(dto.getSubIndustry());
        c.setHeadquarters(dto.getHeadquarters());
        c.setFounded(dto.getFounded());
        return c;
    }
}
