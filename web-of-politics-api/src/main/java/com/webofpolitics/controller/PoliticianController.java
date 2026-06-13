package com.webofpolitics.controller;

import com.webofpolitics.dto.PoliticianDto;
import com.webofpolitics.facades.PoliticianFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Politician Controller - REST API endpoints for politicians
 */
@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {
    
    private final PoliticianFacade politicianFacade;
    
    public PoliticianController(PoliticianFacade politicianFacade) {
        this.politicianFacade = politicianFacade;
    }
    
    @GetMapping
    public ResponseEntity<List<PoliticianDto>> getAll() {
        List<PoliticianDto> politicians = new ArrayList<>();
        for (PoliticianDto p : politicianFacade.getAllActive()) {
            politicians.add(p);
        }
        return ResponseEntity.ok(politicians);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PoliticianDto>> search(@RequestParam("query") String query) {
        List<PoliticianDto> politicians = new ArrayList<>();
        for (PoliticianDto p : politicianFacade.searchByName(query)) {
            politicians.add(p);
        }
        return ResponseEntity.ok(politicians);
    }
}
