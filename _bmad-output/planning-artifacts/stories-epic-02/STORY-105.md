# STORY-105: Historical Timeline for Graph Exploration

**Epic:** EPIC-02 (Citizen-Facing Search and Discovery)  
**Priority:** P2 (Nice-to-have)  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Political Researcher/Investigative Journalist/Policy Analyst

## I Want

A timeline slider to explore how relationships evolved over time

## So That

I can see when connections formed, dissolved, or changed character

---

## Acceptance Criteria

### Given a politician profile page
**When** I click "View Historical Graph"  
**Then** the graph explorer opens with timeline controls

### When I drag the timeline slider left/right
**Then** the graph updates to show relationships for that time period:
- Nodes dimmed if no active connection at that point in time
- Connection edges colored by era (e.g., 2005-2010: blue, 2010-2015: purple, 2015+: green)
- Hover tooltip shows relationship dates ("Shared board seat 2015–2019")

### Given the timeline range is wide (e.g., 20 years)
**When** I move slider quickly  
**Then** graph animation shows node/edge morphing to new state

### When the historical view reaches earliest data (2005)
**Then** a note appears: "Data coverage from 2005 onwards; earlier records may be incomplete"

---

## Component Structure

```jsx
// components/graph/TimelineGraph.jsx
import * as d3 from 'd3';
import { useState } from 'react';

export default function TimelineGraph({ 
  politicianId, 
  connectionsByYear,
  onHistoryChange
}) {
  const [selectedYear, setSelectedYear] = useState(2024);
  const minYear = 2005;
  const maxYear = new Date().getFullYear();
  
  // Generate timeline markers from available data
  const years = Array.from(
    { length: maxYear - minYear + 1 },
    (_, i) => minYear + i
  );
  
  return (
    <div className="timeline-graph">
      {/* Timeline header */}
      <div className="timeline-header">
        <h3>Historical View</h3>
        <span className="year-range">
          {minYear} – {maxYear}
        </span>
      </div>
      
      {/* Timeline slider with year markers */}
      <div className="timeline-container">
        <input 
          type="range"
          min={minYear}
          max={maxYear}
          value={selectedYear}
          onChange={(e) => setSelectedYear(parseInt(e.target.value))}
          aria-label="Select year to view historical graph"
          className="timeline-slider"
        />
        
        {/* Year markers every 5 years */}
        {years.filter(y => y % 5 === 0).map(year => (
          <div 
            key={year}
            className="year-marker"
            style={{ left: `${((year - minYear) / (maxYear - minYear)) * 100}%` }}
          >
            {year}
          </div>
        ))}
      </div>
      
      {/* Current year indicator */}
      <div 
        className="current-year-indicator"
        style={{ left: `${((maxYear - minYear) / (maxYear - minYear)) * 100}%` }}
      >
        ← Now →
      </div>
      
      {/* Graph visualization for selected year */}
      <Suspense fallback={<LoadingSpinner message="Rendering historical graph..." />}>
        <RelationshipGraph 
          politicianId={politicianId}
          connections={connectionsByYear[selectedYear]}
          filterState={{}}  // Show all by default in historical view
        />
      </Suspense>
      
      {/* Data coverage disclaimer */}
      {selectedYear < minYear + 5 && (
        <div className="data-disclaimer">
          <WarningIcon /> Limited data available for this period. Some relationships may be missing from records.
        </div>
      )}
    </div>
  );
}
```

### Year-Based Connection Fetching Service

```java
// services/GraphHistoryService.java
@Service
public class GraphHistoryService {
    
    /**
     * Returns connections between entities for a specific year range.
     * For simplicity, treat as "as of this year" with start_date <= year.
     */
    public Map<String, List<Relationship>> getConnectionsByYear(
            Politician politician,
            int targetYear) {
        
        // Query Neo4j for active relationships where:
        // - end_date is NULL (still active) OR end_date >= targetYear
        // - start_date <= targetYear
        
        String cypherQuery = """
            MATCH (p:Politician {id: $politicianId})-[r]-(connected:Node)
            WHERE p.tenure_end IS NULL 
              OR (r.end_date IS NOT NULL AND r.end_date >= date($targetYear))
              AND r.start_date <= date($targetYear)
            RETURN connected.id as nodeId,
                   connected.name as nodeName,
                   type(r) as relationshipType,
                   r.start_date,
                   r.end_date,
                   properties(r) as edgeProperties
        """;
        
        // Execute query and return connections by entity type (politician, company, party...)
        return neo4jTemplate.execute(cypherQuery, Map.of(
            "politicianId", politician.id,
            "targetYear", targetYear
        ))
        .stream()
        .collect(Collectors.groupingBy(r -> r.getNodeType()));
    }
}
```

---

## Timeline UI Design

### Slider Range and Markers

```css
.timeline-container {
  position: relative;
  width: 100%;
  margin: 1rem 0;
}

.timeline-slider {
  width: 100%;
  height: 8px;
  background: linear-gradient(to right, 
    #c8102e 0%, 
    #ff7f0e 50%,  
    #2ca02c 100%);
  border-radius: 4px;
  outline: none;
}

.year-marker {
  position: absolute;
  transform: translateX(-50%);
  font-size: 0.7rem;
  color: #6c757d;
  background: white;
  padding: 2px 6px;
  border-radius: 10px;
  pointer-events: none;
}

.current-year-indicator {
  position: absolute;
  transform: translateX(-50%);
  background: #212529;
  color: white;
  font-size: 0.6rem;
  padding: 4px 8px;
  border-radius: 10px;
  pointer-events: none;
}
```

### Node Color Encoding by Era (if applicable)

```javascript
// Color scale based on relationship formation era
const eraColors = {
  '2005-2010': '#636e72',   // Grey - early data, may be incomplete
  '2010-2015': '#fd7e14',   // Orange - pre-parliamentary-reform era
  '2015-2020': '#90be6d',   // Light green - modern digital disclosure
};

function getEraColor(startYear, endYear) {
  if (endYear === null || endYear >= 2015) {
    return eraColors['2015-2020'];
  } else if (endYear >= 2010) {
    return eraColors['2010-2015'];
  } else {
    return eraColors['2005-2010'];
  }
}
```

---

## Risks

- **Risk:** Historical graph is too sparse in early years (many missing relationships)  
  - **Mitigation:** Accept sparsity; show "limited data" disclaimer prominently

- **Risk:** Timeline slider jumps awkwardly when data is sparse in certain periods  
  - **Mitigation:** Add smooth animation for node fade-in/out instead of hard appearance/disappearance

---

## Definition of Done

- [ ] Timeline slider renders with year markers every 5 years
- [ ] Dragging slider updates graph visualization with animations
- [ ] Hover tooltips show relationship date ranges when available
- [ ] Current year indicator visible on timeline
- [ ] Data coverage disclaimer shown for early periods (<2010)
- [ ] All edge cases handled: empty result for year, no active connections

---

## Success Metrics

- Historical exploration rate: >30% of users interact with timeline control
- Session duration: Users spend additional time exploring history
- Data coverage trust: Users understand data quality by era (via disclaimer clicks)
