# EPIC-03: Journalist and Researcher Tools

**Status:** Not Started  
**Priority:** P2 (MVP Important but not essential)  
**Effort Estimate:** 3-4 weeks  
**Team Size:** Solo or with frontend specialist  

## Goal

Build advanced data export, analysis, and comparison tools for journalists and academic researchers who need deep investigative capabilities.

## Scope

### In Scope
- Data export features:
  - Export graph snapshots as JSON/GDV files
  - Download relationship lists (CSV/JSON)
  - Export historical datasets with metadata
- Advanced filtering:
  - Filter by entity type, relationship type, date range
  - Combine multiple filters for complex queries
- Comparison tools:
  - Side-by-side voting records comparison
  - Issue/topic-based analysis overlay
  - Donor alignment scoring across politicians

### Out of Scope (Future)
- API access program for custom journalistic tools
- Data warehouse-style bulk export scheduler
- Automated alerting on new relationships matching criteria

---

## User Stories

See `stories-epic-03/` directory for detailed user stories.

### Summary of Stories
- **STORY-201:** As a journalist, I want to export graph data as JSON so I can analyze it in my own tools
- **STORY-202:** As a researcher, I want downloadable CSV files so I can import into statistical software
- **STORY-203:** As an investigative reporter, I want side-by-side voting comparisons so I can spot patterns
- **STORY-204:** As a data analyst, I want issue/topic filtering so I can study policy alignment

---

## Technical Requirements

### Export Endpoint Design

```java
// controllers/ExportController.java
@RestController
@RequestMapping("/api/export")
public class ExportController {
    
    @PostMapping("/graph-snapshot")
    public ResponseEntity<byte[]> exportGraphSnapshot(
            @RequestParam String politicianId,
            @RequestParam(defaultValue = "2015-2024") 
            String dateRange) throws IOException {
        
        GraphData graphData = graphService.getGraphSnapshot(
            politicianId, 
            parseDateRange(dateRange)
        );
        
        // Serialize to JSON
        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonBytes = mapper.writeValueAsBytes(graphData);
        
        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .header("Content-Disposition", 
                "attachment; filename=\"graph-" + politicianId + "-" + dateRange + ".json\"")
            .body(jsonBytes);
    }
    
    @PostMapping("/relationships")
    public ResponseEntity<byte[]> exportRelationships(
            @RequestParam String politicianId,
            @RequestParam List<String> relationshipTypes) throws IOException {
        
        // Returns CSV of relationships for analysis
        return ResponseEntity.ok()
            .header("Content-Type", "text/csv")
            .header("Content-Disposition", 
                "attachment; filename=\"relationships.csv\"")
            .body(csvData);
    }
}
```

### Export Data Format Specification

**JSON Graph Snapshot Structure:**

```json
{
  "export_metadata": {
    "politician_id": "UK-CHS-LAB",
    "politician_name": "Keir Starmer",
    "generated_at": "2026-06-06T19:30:00Z",
    "date_range": {
      "start_year": 2015,
      "end_year": 2024
    },
    "node_count": 145,
    "edge_count": 892
  },
  
  "nodes": [
    {
      "id": "COMP-XYZ-001",
      "type": "Company",
      "name": "BL plc",
      "properties": {...}
    }
  ],
  
  "edges": [
    {
      "source": "UK-CHS-LAB",
      "target": "COMP-XYZ-001",
      "type": "board_seat",
      "start_date": "2015-06-01",
      "end_date": "2019-12-31"
    }
  ]
}
```

---

## Acceptance Criteria

### Export Functionality
1. **JSON Export Works**
   - Click button or use API endpoint
   - Download completes within 5 seconds for datasets <100k edges
   - File format valid and parseable by standard JSON parsers
   
2. **CSV Export Works**
   - Headers include: source, target, relationship_type, start_date, end_date
   - UTF-8 encoding with BOM for Excel compatibility
   - Maximum 100k rows per file (alert if larger)

3. **Historical Dataset Export**
   - Includes entity counts per year
   - Source attribution for each record (API/scraper)
   - Data completeness flags included

### Filtering Functionality
4. **Multi-Type Filtering**
   - Checkbox or dropdown filter by entity type
   - Apply to node display AND export
   - Filter state preserved in URL (?filter=company|party)

5. **Date Range Filtering**
   - Start/end year inputs with calendar picker
   - Apply to relationship edges
   - Nodes without connections in range dimmed

### Comparison Tools
6. **Voting Records Compare View**
   - Side-by-side tables for two politicians
   - Checkbox rows to highlight agreement/disagreement
   - Donor alignment percentage calculated (80% threshold)

---

## Implementation Order

### Phase 1: Export APIs (Weeks 1-2)
- STORY-201: JSON graph snapshot endpoint
- STORY-202: CSV relationship list endpoint

### Phase 2: Advanced Filtering UI (Weeks 3-3.5)
- Add filter controls to frontend
- Connect filters to Neo4j query parameters

### Phase 3: Comparison Tools (Weeks 4-4.5)
- STORY-203: Voting records comparison view
- STORY-204: Issue/topic analysis overlay

---

## Risks

- **Risk:** Export files too large for browser download  
  - **Mitigation:** Add pagination to export or async job with email notification

- **Risk:** Journalists need custom data formats not covered here  
  - **Acceptance:** Document API; users can request features via future epics

---

## Definition of Done

- [ ] Export endpoints return correctly formatted data
- [ ] Download files parse without errors in standard viewers
- [ ] Filter controls update visualization in real-time
- [ ] Comparison view loads within 3 seconds for typical datasets
- [ ] Documentation includes export format specification
