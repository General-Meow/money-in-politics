# STORY-204: Issue/Topic-Based Analysis Overlay

**Epic:** EPIC-03 (Journalist and Researcher Tools)  
**Priority:** P3 (Nice-to-have)  
**Story Points:** 13  
**Assignee:** [TBD]

## As A

Political Science Researcher/Data Analyst

## I Want

A way to filter the graph by specific policy issues or topics

## So That

I can study how relationships cluster around particular policy domains and influence patterns

---

## Acceptance Criteria

### Given a politician profile page
**When** I select "Issue Analysis" from the menu  
**Then** the following is displayed:
- Dropdown of available voting topics/issues
- Filtered graph showing only politicians/companies involved in those issues
- Color coding by primary issue affiliation

### When I select multiple issues
**When** I apply a multi-issue filter  
**Then** the graph shows overlapping influence networks with nodes colored to show their primary issue domain

### Given an issue-based analysis request
**When** requesting historical data  
**Then** dataset includes metadata about:
- Which committees or parliamentary sessions discussed the issue
- Timeline of when issues gained prominence in legislation
- Key players who consistently appear in this issue's network

---

## Component Implementation

### Issue Filter Toggle Component

```jsx
// components/graph/IssueFilter.jsx
import { useState } from 'react';

export default function IssueFilter({ 
  selectedIssues, 
  onIssueSelect,
  graphData 
}) {
  const issues = [
    { id: 'healthcare', label: 'Healthcare & Welfare' },
    { id: 'economy', label: 'Economic Policy & Trade' },
    { id: 'environment', label: 'Climate Change & Environment' },
    { id: 'justice', label: 'Justice & Policing Reform' },
    { id: 'education', label: 'Education Reform' },
    { id: 'foreign-policy', label: 'Foreign Policy & Defense' },
  ];
  
  return (
    <div className="issue-filter">
      <h4>Filter by Issue Area</h4>
      
      <div className="issue-checkboxes">
        {issues.map(issue => (
          <label key={issue.id} className="issue-checkbox">
            <input 
              type="checkbox"
              checked={selectedIssues.has(issue.id)}
              onChange={() => onIssueSelect(issue.id)}
            />
            <span>{issue.label}</span>
            {graphData.issues[issue.id].politicians?.length} politicians involved
          </label>
        ))}
      </div>
      
      <button 
        onClick={() => onIssueSelect(null)}
        disabled={selectedIssues.size === 0}
      >
        Reset filters
      </button>
    </div>
  );
}
```

### Issue-Based Graph Coloring

```javascript
// Color nodes based on primary issue affiliation
function colorNodeByIssue(nodeData) {
  const primaryIssue = nodeData.primaryIssue;
  
  return {
    healthcare: '#28a745',
    economy: '#ffc107',
    environment: '#17a2b8',
    justice: '#6f42c1',
    education: '#fd7e14',
    foreign_policy: '#dc3545',
  }[primaryIssue] || '#6c757d'; // Default grey if no issue assigned
}

function applyIssueFilter(graphData, selectedIssues) {
  // Show only nodes connected to selected issues
  const issueConnectedNodes = new Set();
  
  for (const [issueId, connections] of Object.entries(graphData.issues)) {
    if (selectedIssues.has(issueId)) {
      connections.nodes.forEach(node => issueConnectedNodes.add(node.id));
    }
  }
  
  return {
    ...graphData,
    visibleNodeIds: Array.from(issueConnectedNodes),
    filteredByIssue: true,
    selectedIssues: selectedIssues
  };
}
```

### Issue Timeline Visualization

```jsx
// components/analysis/IssueTimeline.jsx
export default function IssueTimeline({ issueId, connections }) {
  return (
    <div className="issue-timeline">
      <h4>Timeline for {getIssueLabel(issueId)}</h4>
      
      <svg width="100%" height="200">
        {/* Timeline axis */}
        <line x1="0" y1="50" x2="100%" y2="50" stroke="#ccc" />
        
        {/* Events as points */}
        {connections.events.map((event, idx) => (
          <g key={idx} style={{ transform: `translate(${event.year * 2}% , ${event.positionY}px)` }}>
            <circle 
              r="4" 
              fill={event.type === 'legislation' ? '#007bff' : '#28a745'}
            />
            
            {/* Event label */}
            <text y="-25">
              {event.name.substring(0, 30) + (event.name.length > 30 ? '...' : '')}
            </text>
          </g>
        ))}
      </svg>
      
      {/* Legend */}
      <div className="timeline-legend">
        <span style={{ color: '#007bff' }}>Legislation</span>
        <span style={{ color: '#28a745' }}>Major Policy Statement</span>
      </div>
    </div>
  );
}
```

---

## API Endpoint Design

### Issue-Based Graph Query

```java
@RestController
@RequestMapping("/api/graph/issues")
public class IssueGraphController {
    
    @GetMapping("/{politicianId}/issues/{issueId}")
    public ResponseEntity<IssueConnections> getIssueConnections(
            @PathVariable String politicianId,
            @PathVariable String issueId) throws InterruptedException {
        
        // Neo4j Cypher query for issue-specific connections
        String cypher = """
          MATCH (p:Politician {id: $politicianId})-[:RELATED_TO*1..3]-(n)
          WHERE n.issue_tag = $issueId OR n.in_issue_committee = true
          RETURN n.id as nodeId, 
                 n.name as nodeName,
                 type(n) as nodeType,
                 type(r) as relationshipType,
         """.concat("r.start_date as startDate");
        
        IssueConnections connections = neo4jTemplate.execute(cypher, Map.of(
            "politicianId", politicianId,
            "issueId", issueId
        ));
        
        return ResponseEntity.ok(connections);
    }
}
```

---

## Risks

- **Risk:** Issue classification is subjective and requires manual labeling  
  - **Mitigation:** Start with parliamentary committee affiliations as proxy; allow community refinement
  
- **Risk:** Issue clustering changes over time (different issues dominant in different eras)  
  - **Mitigation:** Tag nodes with era-specific issue metadata

---

## Definition of Done

- [ ] Issue filter dropdown displays all available categories
- [ ] Graph filters correctly when single issue selected
- [ ] Multi-issue overlay shows overlapping networks
- [ ] Timeline visualization renders events chronologically
- [ ] Issue-based dataset includes source attribution (committee vs. policy statement)
