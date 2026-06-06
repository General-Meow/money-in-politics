# STORY-103: Interactive Relationship Graph Explorer

**Epic:** EPIC-02 (Citizen-Facing Search and Discovery)  
**Priority:** P1  
**Story Points:** 13  
**Assignee:** [TBD]

## As A

Investigative Journalist/Researcher/Concerned Citizen

## I Want

An interactive, zoomable relationship graph with filtering controls

## So That

I can visually explore connections between politicians, companies, and other entities without getting lost in complexity

---

## Acceptance Criteria

### Given a politician profile page
**When** I click "View Full Graph"  
**Then** the graph explorer opens with:
- The selected politician at center or as starting node
- All visible relationships rendered (default: current + last 5 years)
- Node colors encode entity type (politician, company, party, etc.)
- Edge colors encode relationship type

### Given I hover over any node
**Then** a tooltip displays:
- Entity name
- Type (Politician/Company/Party/etc.)
- Key metadata (party affiliation for politicians, industry for companies)
- Date range if connection has temporal bounds

### When I click on a node
**Then**:
- That node is highlighted in current color
- All connected nodes are highlighted with lighter color
- Click again to expand connections by 1 hop
- Click a third time to collapse back

### Given I'm viewing the graph
**When** I scroll the mouse wheel  
**Then** the view zooms smoothly between 0.5x and 5x

### When I pan with mouse drag (desktop) or two-finger swipe (mobile)
**Then** the graph follows my input without snapping

### Given entity type filter toggle
**When** I check/uncheck filters  
**Then** only nodes of selected types are shown; edges reconnected accordingly

---

## Component Structure

```jsx
// components/graph/RelationshipGraph.jsx
import * as d3 from 'd3';

export default function RelationshipGraph({ 
  politicianId, 
  connections,
  filterState,
  onNodeClick,
  onZoomLevelChange 
}) {
  const svg = useRef(null);
  
  useEffect(() => {
    // Initialize or update simulation
    const simulation = forceSimulation()
      .force('link', linkForce(connections))
      .force('charge', chargeForce())
      .force('center', centerForce(0.3));
    
    const g = d3.select(svg.current);
    
    // Setup scales, tooltips, event handlers
    setupGraphVisualisation(g, simulation, onNodeClick, onZoomLevelChange);
    
  }, [connections, filterState]);
  
  return (
    <div className="graph-container">
      <h2>Relationship Network for {politicianId}</h2>
      
      {/* Legend */}
      <Legend filters={filterState} onUpdate={setFilterState} />
      
      {/* Graph SVG */}
      <svg 
        ref={svg}
        className="graph-svg"
        style={{ cursor: 'move', overflow: 'visible' }}
      />
      
      {/* Zoom controls (mobile-friendly) */}
      <div className="zoom-controls">
        <button onClick={() => zoomIn()} aria-label="Zoom in">+</button>
        <span id="zoom-level">{zoomLevel.toFixed(1)}x</span>
        <button onClick={() => zoomOut()} aria-label="Zoom out">−</button>
      </div>
    </div>
  );
}
```

### Legend Component with Filters

```jsx
// components/graph/Legend.jsx
export default function Legend({ filters, onUpdate }) {
  return (
    <div className="legend" role="group" aria-label="Graph filters">
      <span className="legend-title">Show:</span>
      
      <label className="filter-item">
        <input 
          type="checkbox" 
          checked={filters.politician}
          onChange={() => onUpdate('politician', !filters.politician)}
        />
        <span className={`legend-dot ${colors.politician}`}></span>
        Politicians
      </label>
      
      <label className="filter-item">
        <input 
          type="checkbox" 
          checked={filters.company}
          onChange={() => onUpdate('company', !filters.company)}
        />
        <span className={`legend-dot ${colors.company}`}></span>
        Companies
      </label>
      
      <label className="filter-item">
        <input 
          type="checkbox" 
          checked={filters.party}
          onChange={() => onUpdate('party', !filters.party)}
        />
        <span className={`legend-dot ${colors.party}`}></span>
        Parties
      </label>
      
      {/* Additional filters for family, board, etc. */}
    </div>
  );
}
```

---

## D3.js Configuration Details

### Force Simulation Setup

```javascript
// Graph visualization initialization
function setupGraphVisualisation(svg, simulation, onNodeClick) {
  const width = svg.attr('width');
  const height = svg.attr('height');
  
  // Color scale for entity types
  const colorScale = scaleOrdinal([
    { label: 'Politician', color: '#1f77b4' },
    { label: 'Company', color: '#ff7f0e' },
    { label: 'Party', color: '#2ca02c' },
  ]);
  
  // Link colors for relationship types
  const linkColorScale = scaleOrdinal([
    { type: 'family', color: '#9467bd' },
    { type: 'board', color: '#85c1e9' },
    { type: 'donation', color: '#ed7f32' },
    { type: 'social', color: '#7fcdbb' },
  ]);
  
  // Node click handler
  svg.selectAll('.node')
    .on('click', (event, d) => {
      // Highlight selected node and its neighbors
      highlightSelected(d);
      onNodeClick?.(d);
    });
}
```

### Touch/Mobile Support

```javascript
// Touch gesture handling for mobile devices
function setupTouchGestures(svg) {
  const touchStart = { x: 0, y: 0 };
  let isDragging = false;
  
  svg.on('touchstart', (event) => {
    const touch = event.touches[0];
    touchStart.x = touch.clientX;
    touchStart.y = touch.clientY;
    isDragging = true;
  });
  
  svg.on('touchmove', (event) => {
    if (!isDragging) return;
    
    const touch = event.touches[0];
    const deltaX = touch.clientX - touchStart.x;
    const deltaY = touch.clientY - touchStart.y;
    
    // Pan the graph
    d3.select(svg)
      .attr('transform', `translate(${deltaX}, ${deltaY})`);
    
    touchStart.x = touch.clientX;
    touchStart.y = touch.clientY;
  });
  
  svg.on('touchend', () => {
    isDragging = false;
  });
}
```

---

## Performance Considerations

### Large Graph Optimization (100+ nodes)

- **Node Rendering:** Use SVG circles with simple `r` attribute; avoid complex glyphs
- **Link Rendering:** Use straight lines initially; curved edges only on hover
- **Tooltip:** Defer until mouse moves to node position
- **Caching:** Cache computed forces for repeated render cycles

### Virtualization for Very Large Graphs (500+ nodes)

- **Option 1:** Render distant nodes as simplified dots with expandable detail
- **Option 2:** Paginate connections; show "expand" buttons to load more hops

---

## Risks

- **Risk:** Graph becomes too crowded for smaller screens  
  - **Mitigation:** Implement auto-layout that prioritizes center node and recent connections

- **Risk:** D3.js library size affects page load time  
  - **Mitigation:** Use CDN with caching; consider lazy-loading the graph component

---

## Definition of Done

- [ ] Graph renders initial state with correct node/edge encoding
- [ ] Hover tooltips display accurate entity metadata
- [ ] Node highlighting works on single-click
- [ ] Zoom controls function smoothly between 0.5x and 5x
- [ ] Touch gestures work on mobile devices (pan/swipe)
- [ ] Entity type filters toggle nodes correctly
- [ ] Graph performance maintains 60fps at current data volume

---

## Success Metrics

- Graph exploration time: Users spend >30 seconds exploring relationships
- Filter usage rate: >40% of sessions use at least one filter
- Node clicking rate: Users click to expand connections >70% of the time
