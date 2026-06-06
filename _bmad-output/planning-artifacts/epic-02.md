# EPIC-02: Citizen-Facing Search and Discovery

**Status:** Not Started  
**Priority:** P1 (MVP Essential)  
**Effort Estimate:** 4-6 weeks  
**Team Size:** Solo initially  

## Goal

Build the public-facing website that allows citizens to explore political finance relationships with intuitive search and visualization.

## Scope

### In Scope
- React frontend application with responsive design
- Entity search by name or constituency (MP, Lord, councillor)
- Politician profile pages showing:
  - Current party affiliation
  - Top donors (visual summary)
  - Connection graph overview
  - Latest donation history
- Graph exploration interface:
  - Zoomable pan/zoom network graph
  - Entity-type filtering toggle
  - Timeline slider for historical view
- Mobile-responsive design with touch-friendly controls

### Out of Scope
- Journalist-specific export features (handled by EPIC-03)
- Community submission portal (EPIC-04)
- Moderation queue interface (EPIC-04)
- Voting analysis tools (EPIC-03)

---

## User Stories

See `stories-epic-02/` directory for detailed user stories.

### Summary of Stories
- **STORY-101:** As a citizen, I want to search politicians by name or constituency so that I can find who represents me
- **STORY-102:** As a voter, I want to see top donors on an MP's profile so I understand their funding sources
- **STORY-103:** As a journalist, I want to explore the relationship graph visually so I can find connections between entities
- **STORY-104:** As any user, I want responsive design that works on mobile devices so I can access the platform anywhere
- **STORY-105:** As a researcher, I want timeline controls to see historical changes in relationships so I can analyze evolution over time

---

## Technical Requirements

### React Component Structure

```
src/
├── components/
│   ├── search/
│   │   └── PoliticianSearch.jsx
│   ├── profiles/
│   │   └── PoliticianProfile.jsx
│   ├── graph/
│   │   └── RelationshipGraph.jsx    # D3.js visualization
│   ├── donors/
│   │   └── DonationSummary.jsx
│   └── layout/
│       └── AppLayout.jsx
├── hooks/
│   └── usePoliticianData.ts         # React hook for API calls
└── pages/
    ├── Home.jsx
    ├── Search.jsx
    └── Profile/:id.jsx
```

### D3.js Graph Configuration

```javascript
// components/graph/RelationshipGraph.jsx
import * as d3 from 'd3';

function RelationshipGraph({ politicians, relationships }) {
  const svg = useRef(null);
  
  useEffect(() => {
    // Setup force simulation
    const simulation = forceSimulation()
      .force('link', linkForce(relationships))
      .force('charge', chargeForce())
      .force('center', centerForce());
    
    // Node encoding (colors by entity type)
    const colorScale = scaleOrdinal([
      { label: 'Politician', color: '#1f77b4' },
      { label: 'Company', color: '#ff7f0e' },
      { label: 'Party', color: '#2ca02c' },
    ]);
    
    // Render on mount and update
  }, [politicians, relationships]);
}
```

---

## Acceptance Criteria

### Core Functionality
1. **Search Works**
   - Search box accepts name or constituency input
   - Results display as clickable cards
   - Fuzzy matching for misspellings enabled

2. **Profile Pages Load Correctly**
   - Politician profile shows: photo, name, party, constituency
   - Top donors displayed with company logos if available
   - Connection graph renders with all relationships from 2015-present by default
   - Timeline slider moves between historical states

3. **Graph Visualization Performs**
   - Graph responds to mouse wheel for zoom (0.5x to 5x)
   - Click on entity highlights connected nodes within 3 hops
   - Legend visible showing entity type color coding
   - Mobile touch gestures supported (two-finger pan/zoom)

4. **Responsive Design**
   - Desktop: Full horizontal scrolling disabled; centered content
   - Tablet: Reduced graph node size to fit landscape view
   - Mobile: Single-column layout; simplified legend

---

## Implementation Order

### Phase 1: Core UI (Weeks 1-2)
- STORY-101: Search interface + results page
- STORY-102: Profile page with donor summary
- Basic D3 graph placeholder (static data display first)

### Phase 2: Graph Integration (Weeks 3-4)
- Replace static graph with live D3 visualization
- Implement filtering controls (toggle entity types)
- Add timeline slider functionality

### Phase 3: Performance & Polish (Weeks 5-6)
- Implement caching for politician profile pages
- Optimize graph rendering for large networks (>100 nodes)
- Accessibility audit and fixes
- Mobile responsive refinement

---

## Risks

- **Risk:** D3.js learning curve delays timeline slider implementation  
  - **Mitigation:** Start with static graph; add timeline later as iteration

- **Risk:** Graph performance degrades with large networks (1000+ nodes)  
  - **Mitigation:** Implement virtualization for distant node rendering

- **Risk:** D3 visualization breaks on mobile touch devices  
  - **Mitigation:** Test early with device lab or responsive testing tool

---

## Definition of Done

- [ ] Search page loads and displays results for all current/former MPs
- [ ] Politician profile page renders with graph, donors, bio
- [ ] D3 graph renders with correct node/edge encoding
- [ ] Timeline slider updates graph state across historical years
- [ ] Responsive breakpoints tested on iPhone SE to MacBook Pro
- [ ] Accessibility: all interactive elements have keyboard focus states
- [ ] Performance: 95th percentile profile page load < 2s on 3G connection

---

## Success Metrics

- User engagement: Average time spent on graph view > 30 seconds
- Search success rate: Users find intended politician within 1 click
- Graph interaction rate: Users zoom/pan filter at least once per session
- Mobile adoption: >60% sessions from mobile devices supported gracefully
