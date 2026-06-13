package com.webofpolitics.controller;

import com.webofpolitics.dto.CompanyDto;
import com.webofpolitics.facades.CompanyFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Company Controller - REST API endpoints for companies
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    
    private final CompanyFacade companyFacade;
    
    public CompanyController(CompanyFacade companyFacade) {
        this.companyFacade = companyFacade;
    }
    
    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAll() {
        List<CompanyDto> companies = new ArrayList<>();
        for (CompanyDto c : companyFacade.getAll()) {
            companies.add(c);
        }
        return ResponseEntity.ok(companies);
    }
}
