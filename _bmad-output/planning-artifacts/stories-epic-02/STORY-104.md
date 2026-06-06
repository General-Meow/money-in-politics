# STORY-104: Mobile Responsive Design

**Epic:** EPIC-02 (Citizen-Facing Search and Discovery)  
**Priority:** P1  
**Story Points:** 5  
**Assignee:** [TBD]

## As A

Mobile user (iPhone, Android user)

## I Want

A fully responsive website that works well on all screen sizes

## So That

I can use The Web of Politics anywhere without frustration

---

## Acceptance Criteria

### Given a desktop layout
**When** the viewport width drops below 1024px  
**Then** the layout shifts to:
- Centered, full-width content
- Graph displayed with reduced node size (if <50 nodes) or pagination hints (if >50 nodes)
- Search bar spans full screen width
- Donor grid becomes single column

### Given a tablet landscape mode (768px - 1024px)
**Then** the layout adjusts to:
- Smaller font sizes (0.9x of desktop)
- Graph takes top half of viewport; donors list below
- Legend appears as floating overlay on hover
- Touch gestures enabled for graph exploration

### Given a mobile portrait mode (<768px)
**Then** the layout simplifies to:
- Single-column card layout (search → results → profile)
- Graph collapses to scrollable vertical container with mini-graphs
- Legend appears as hamburger menu or inline toggle switches
- Zoom controls become +/- button stack
- All interactive elements have large touch targets (min 44px)

---

## Responsive Breakpoints

```css
/* Tailwind-like CSS classes */
.container {
  @media (max-width: 768px) {
    padding: 1rem;
  }
}

.graph-container {
  width: 100%;
  height: calc(100vh - 200px);
  
  @media (min-width: 768px) {
    height: auto;
    min-height: 500px;
  }
}

.legend-toggle {
  /* Mobile-only toggle for legend */
  display: none;
  
  @media (max-width: 768px) {
    display: block;
  }
}
```

### Graph Container for Mobile

```jsx
// components/graph/ResponsiveGraphContainer.jsx
export default function ResponsiveGraphContainer({ children, className }) {
  return (
    <div className={`graph-container ${className}`}>
      {/* Desktop view */}
      <div className="graph-view desktop">
        {children}
      </div>
      
      {/* Mobile view with scrolling */}
      <div className="graph-view mobile">
        <div 
          className="scrollable-graph-area"
          style={{ overflowX: 'auto', overflowY: 'visible' }}
        >
          {children}
        </div>
      </div>
    </div>
  );
}
```

---

## Touch Gesture Support

### Minimum Touch Targets

```jsx
// Ensure all interactive elements meet WCAG touch target requirements
const MIN_TOUCH_TARGET_SIZE = 48; // pixels (WCAG recommends 44px)

function LargeTouchTarget(props) {
  return (
    <div 
      className="touch-target" 
      style={{ 
        width: MIN_TOUCH_TARGET_SIZE, 
        height: MIN_TOUCH_TARGET_SIZE,
        minWidth: MIN_TOUCH_TARGET_SIZE,
        minHeight: MIN_TOUCH_TARGET_SIZE,
      }}
      {...props}
    />
  );
}

// Apply to buttons
<button className="touch-target">+</button>
<button className="touch-target">−</button>
```

### Touch-Specific Graph Controls

```jsx
// components/graph/TouchGraphControls.jsx
import { useState } from 'react';

export default function TouchGraphControls({ zoomLevel, setZoomLevel }) {
  const [showLegends, setShowLegends] = useState(false);
  
  return (
    <div className="touch-controls">
      {/* + and - buttons for zoom */}
      <LargeTouchTarget 
        onClick={() => setZoomLevel(prev => Math.min(prev + 0.1, 5))}
        aria-label="Zoom in"
      >+</LargeTouchTarget>
      
      <LargeTouchTarget 
        onClick={() => setZoomLevel(prev => Math.max(prev - 0.1, 0.5))}
        aria-label="Zoom out"
      >−</LargeTouchTarget>
      
      {/* Toggle legend visibility */}
      <LargeTouchTarget 
        onClick={() => setShowLegends(!showLegends)}
        aria-label={showLegends ? "Hide legend" : "Show legend"}
      >
        {showLegends ? 'Hide Legend' : 'Show Legend'}
      </LargeTouchTarget>
    </div>
  );
}
```

---

## Performance on Mobile

### Optimizations

- **Image Lazy Loading:** All politician photos lazy-loaded with intersection observer
- **Graph Rendering:** Only render nodes visible in viewport (SVG fragment)
- **Code Splitting:** Graph component loaded only when "View Full Graph" clicked
- **Font Loading:** Use system fonts for mobile; load custom fonts last

### Code Splitting Strategy

```jsx
// pages/Profile/:id.jsx
import { Suspense, lazy } from 'react';

const RelationshipGraph = lazy(() => import('./components/graph/RelationshipGraph'));

export default function PoliticianProfile() {
  return (
    <Suspense fallback={<LoadingSpinner message="Loading graph..." />}>
      <RelationshipGraph politicianId={politicianId} />
    </Suspense>
  );
}
```

---

## Testing Requirements

### Device Matrix

| Device Type | Minimum Screen Size | Resolution | Priority |
|-------------|---------------------|------------|----------|
| iPhone SE (2nd gen) | 375×667 | High | P0 |
| Samsung Galaxy S21 | 360×800 | Medium | P0 |
| iPad (9th gen) | 768×1024 | Medium | P1 |
| MacBook Air M1 | 1260×856 | High | P1 |
| Desktop Chrome | 1440×900 | High | P2 |

### Accessibility Testing

- All graph nodes have keyboard focus indicators (visible outline)
- Screen reader announces node type on hover/selection
- Alternative text for politician photos
- Color contrast ratios meet WCAG AA (minimum 4.5:1)

---

## Risks

- **Risk:** D3.js force-directed layout doesn't scale well to mobile  
  - **Mitigation:** Implement simplified layout for <768px viewport (center node, reduce edge count)

- **Risk:** Graph is too complex for small screens  
  - **Mitigation:** Provide "simplified view" toggle showing only top connections

---

## Definition of Done

- [ ] All breakpoints tested on physical devices
- [ ] Touch targets meet minimum size requirements (44px equivalent)
- [ ] Graph scrolls vertically on mobile without losing context
- [ ] All interactive elements have accessible labels for screen readers
- [ ] Images lazy-load successfully
- [ ] Performance: Profile page loads <5 seconds on 3G network
- [ ] No CSS/media queries cause layout breaks

---

## Success Metrics

- Mobile adoption: >60% sessions from mobile devices supported gracefully
- Touch interaction success rate: 100% of touch gestures work correctly
- Page load time mobile: 95th percentile <5 seconds on 3G network
