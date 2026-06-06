# STORY-202: CSV Relationship List Export

**Epic:** EPIC-03 (Journalist and Researcher Tools)  
**Priority:** P2  
**Story Points:** 5  
**Assignee:** [TBD]

## As A

Political Researcher/Statistician

## I Want

Download relationship lists as CSV files for import into statistical software

## So That

I can use tools like R, Python/pandas, or SPSS to analyze patterns in political finance

---

## Acceptance Criteria

### Given a politician profile
**When** I click "Export Relationships" button  
**Then** a CSV file downloads with:
- Filename: `relationships-{politicianId}-{dateRange}.csv`
- Headers matching spreadsheet conventions
- UTF-8 encoding with BOM for Excel compatibility

### Given the downloaded CSV file
**When** I open it in Excel, Google Sheets, or R  
**Then** all columns parse correctly without import errors

### When viewing the CSV in a text editor
**Then** the following columns exist:
| Column | Description | Example |
|--------|-------------|---------|
| source_id | Source entity ID | "UK-CHS-LAB" |
| source_name | Human-readable name | "Keir Starmer" |
| target_id | Connected entity ID | "COMP-XYZ-001" |
| target_name | Human-readable name | "BL plc" |
| relationship_type | Type of connection | "board_seat" |
| start_date | When relationship began | "2015-06-01" |
| end_date | When relationship ended (null if ongoing) | "2019-12-31" |
| node_source | Data source for nodes | "parliament_api" or "scraper" |
| edge_source | Data source for relationship | "companies_house" |

---

## Component Implementation

### CSV Export Button

```jsx
// components/profile/ExportButtons.jsx (continued)
import { useState } from 'react';

export default function ExportButtons({ politicianId, dateRange }) {
  const [downloadingCsv, setDownloadingCsv] = useState(false);
  
  const exportCsv = async () => {
    try {
      setDownloadingCsv(true);
      
      const response = await fetch(`/api/export/relationships?politicianId=${politicianId}&dateRange=${dateRange}`);
      
      if (!response.ok) throw new Error('Export failed');
      
      const csvText = await response.text();
      
      // Trigger download with BOM for Excel compatibility
      const blob = new Blob(['\ufeff', csvText], { type: 'text/csv;charset=utf-8;' });
      
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `relationships-${politicianId}-${dateRange}.csv`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      
      setDownloadingCsv(false);
    } catch (error) {
      console.error('Export error:', error);
      alert('Failed to export relationships. Please try again.');
      setDownloadingCsv(false);
    }
  };
  
  return (
    <>
      <button 
        onClick={exportJson}
        disabled={downloadingJson}
        className="btn btn-secondary"
      >
        {downloadingJson ? 'Downloading...' : 'Download Graph (JSON)'}
      </button>
      
      <button 
        onClick={exportCsv}
        disabled={downloadingCsv}
        className="btn btn-success"
      >
        {downloadingCsv ? 'Downloading...' : 'Export Relationships (CSV)'}
      </button>
    </>
  );
}
```

### CSV Service Layer

```java
// services/RelationshipExporterService.java
@Service
public class RelationshipExporterService {
    
    public String exportRelationshipsAsCsv(
            String politicianId, 
            int startYear,
            int endYear) {
        
        // Query Neo4j for relationships in date range
        String cypher = """
            MATCH (p:Politician {id: $politicianId})-[r]-(n:Node)
            WHERE p.tenure_end IS NULL 
              OR r.end_date <= date($endYear) + duration(0, 0, 365 * (1 - $endYear/2024))
              AND r.start_date >= date($startYear) - duration(0, 0, 365)
            RETURN 
                p.name as source_name,
                p.id as source_id,
                n.name as target_name,
                n.id as target_id,
                type(r) as relationship_type,
                r.start_date,
                r.end_date,
                'parliament_api' as node_source
        """;
        
        List<Object[]> rows = neo4jTemplate.execute(cypher, Map.of(
            "politicianId", politicianId,
            "startYear", startYear,
            "endYear", endYear
        ));
        
        // Build CSV with BOM header
        StringBuilder csv = new StringBuilder();
        csv.append("\ufeff").append("source_name,source_id,target_name,target_id," +
                        "relationship_type,start_date,end_date,node_source\n");
        
        for (Object[] row : rows) {
            String line = escapeCsvValue(row[0].toString()) 
                + ","
                + escapeCsvValue(row[1].toString())
                + ","
                + escapeCsvValue(row[2].toString())
                + ","
                + escapeCsvValue(row[3].toString())
                + ","
                + escapeCsvValue(row[4].toString())
                + ","
                + escapeCsvValue(row[5].toString())
                + ","
                + escapeCsvValue(row[6].toString());
            
            csv.append(line).append("\n");
        }
        
        return csv.toString();
    }
    
    private String escapeCsvValue(String value) {
        if (value == null || value.isEmpty()) return "";
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
```

---

## Risks

- **Risk:** CSV exceeds 5MB and takes long to download  
  - **Mitigation:** Add row limit (100k); offer "async export" for larger datasets
  
- **Risk:** Special characters in names break CSV parsing  
  - **Mitigation:** Use proper CSV escaping with quotes around commas

---

## Definition of Done

- [ ] CSV file downloads without page reload
- [ ] Excel opens file without import errors
- [ ] All required columns present with correct data types
- [ ] UTF-8 encoding confirmed (works on Mac, Linux, Windows)
- [ ] BOM header included for Excel compatibility
