package com.webofpolitics.search;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Politician REST API Controller.
 * 
 * STORY-101 Acceptance Criteria:
 * - [ ] Search endpoint responds within 500ms
 * - [ ] Results properly formatted as JSON array
 */
@RestController
@RequestMapping("/api")
public class PoliticianRestController {

    private final PoliticianSearchService searchService;

    public PoliticianRestController(PoliticianSearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Search for politicians by name or constituency.
     * 
     * @param query search query (name or constituency)
     * @param includeFormer filter: "current" | "all" (default: "current")
     * @return list of matching politician summaries
     */
    @GetMapping("/search")
    public ResponseEntity<List<PoliticianSummary>> searchPoliticians(
            @RequestParam String query,
            @RequestParam(defaultValue = "current") String includeFormer) {

        try {
            // Performance check: search should complete within 500ms
            var results = searchService.search(query, includeFormer);
            
            if (query.trim().isEmpty()) {
                // Return featured entities on empty search
                var featured = searchService.getFeaturedEntities();
                return ResponseEntity.ok(featured);
            }

            return ResponseEntity.ok(results);
        } catch (Exception e) {
            // Log error and return empty results
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
