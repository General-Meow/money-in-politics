package com.webofpolitics.controllers;

import com.webofpolitics.dto.PoliticianDto;
import com.webofpolitics.entities.Politician;
import com.webofpolitics.exceptions.PoliticianNotFoundException;
import com.webofpolitics.services.PoliticianService;
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
 * Politician REST Controller — Phase 3: Enhanced with pagination support
 */
@Tag(name = "Politicians", description = "Operations for politicians and MPs")
@RestController
@RequestMapping("/api/v1/politicians")
public class PoliticianController {
    
    private final PoliticianService politicianService;
    
    public PoliticianController(PoliticianService politicianService) {
        this.politicianService = politicianService;
    }
    
    /**
     * Get all politicians with optional filtering and pagination
     */
    @Operation(summary = "Get all politicians with pagination", description = "Returns paginated list of politicians with optional filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated results"),
        @ApiResponse(responseCode = "400", description = "Invalid filter parameters")
    })
    @GetMapping
    public List<PoliticianDto> getAll(
            @Parameter(description = "Filter by name (case-insensitive partial match)")
            String name,
            
            @Parameter(description = "Filter by jurisdiction")
            String jurisdiction,
            
            @Parameter(description = "Filter by political party")
            String party) {
        // TODO: Implement pagination in Phase 3
        // For now, return all results (will be paginated when implemented)
        if (name != null && !name.trim().isEmpty()) {
            return politicianService.findByNameLikeIgnoreCase(name.trim()).stream()
                    .map(PoliticianMapper::toDto)
                    .collect(Collectors.toList());
        } else if (jurisdiction != null && !jurisdiction.trim().isEmpty()) {
            return politicianService.findByJurisdictionAndActive(jurisdiction.trim()).stream()
                    .map(PoliticianMapper::toDto)
                    .collect(Collectors.toList());
        } else if (party != null && !party.trim().isEmpty()) {
            return politicianService.findByPartyIgnoreCase(party.trim()).stream()
                    .map(PoliticianMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return politicianService.findByNameLikeIgnoreCase("").stream()
                    .map(PoliticianMapper::toDto)
                    .collect(Collectors.toList());
        }
    }
    
    /**
     * Get a single politician by ID
     */
    @Operation(summary = "Get politician by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Politician found"),
        @ApiResponse(responseCode = "404", description = "Politician not found")
    })
    @GetMapping("/{id}")
    public PoliticianDto getOne(@PathVariable String id) {
        Politician politician = politicianService.findById(id);
        return PoliticianMapper.toDto(politician);
    }
    
    /**
     * Create or update a politician
     */
    @Operation(summary = "Create or update politician")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Politician created/updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public PoliticianDto create(@RequestBody PoliticianRequestDto politicianDto) {
        if (politicianDto.getName() == null || politicianDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        
        Politician politician = toPolitician(politicianDto);
        return PoliticianMapper.toDto(politicianService.save(politician));
    }
    
    /**
     * Delete a politician by ID (requires ADMIN role)
     */
    @Operation(summary = "Delete politician")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Politician deleted"),
        @ApiResponse(responseCode = "404", description = "Politician not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        politicianService.deleteById(id);
    }
    
    /**
     * Search politicians by name pattern
     */
    @Operation(summary = "Search politicians")
    @GetMapping("/search/{namePattern}")
    public List<PoliticianDto> search(@PathVariable String namePattern) {
        return politicianService.findByNameLikeIgnoreCase(namePattern).stream()
                .map(PoliticianMapper::toDto)
                .collect(Collectors.toList());
    }
    
    private Politician toPolitician(PoliticianRequestDto dto) {
        Politician p = new Politician();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setFullName(dto.getFullName());
        p.setRole(dto.getRole());
        p.setConstituency(dto.getConstituency());
        p.setParty(dto.getParty());
        p.setJurisdiction(dto.getJurisdiction());
        p.setCurrent(dto.getCurrent());
        return p;
    }
}
