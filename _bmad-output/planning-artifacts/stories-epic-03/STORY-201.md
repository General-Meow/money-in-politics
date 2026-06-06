# STORY-201: Graph Data Export as JSON

**Epic:** EPIC-03 (Journalist and Researcher Tools)  
**Priority:** P2  
**Story Points:** 5  
**Assignee:** [TBD]

## As A

Investigative Journalist/Political Data Analyst

## I Want

Download graph snapshots as JSON files for my own analysis tools

## So That

I can export visualizations and relationship data to continue investigation offline or in custom applications

---

## Acceptance Criteria

### Given a politician profile page
**When** I click "Export Graph" button  
**Then** the following occurs:
- Browser opens download dialog
- Filename is: `graph-{politicianId}-{startYear}-{endYear}.json`
- File size displayed before download (<10MB warning if >25MB)

### Given the downloaded JSON file
**When** I open it in a text editor or JSON viewer  
**Then** valid JSON structure with:
- Export metadata (politician info, date range, node/edge counts)
- Arrays of nodes and edges following schema
- All relationship properties included

### Given an API endpoint request
**When** POST to `/api/export/graph-snapshot`  
**Then** response includes same format as button click
**And when the response body is large (>5MB)**
**Then** Content-Length header set appropriately for HTTP client handling

---

## Component Implementation

### Export Button Component

```jsx
// components/profile/ExportButtons.jsx
import { useState } from 'react';

export default function ExportButtons({ politicianId, dateRange }) {
  const [downloadingGraph, setDownloadingGraph] = useState(false);
  
  const exportJson = async () => {
    try {
      setDownloadingGraph(true);
      
      const response = await fetch(`/api/export/graph-snapshot?politicianId=${politicianId}&dateRange=${dateRange}`);
      
      if (!response.ok) throw new Error('Export failed');
      
      const blob = await response.blob();
      
      // Trigger download
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `graph-${politicianId}-${dateRange}.json`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      
      setDownloadingGraph(false);
    } catch (error) {
      console.error('Export error:', error);
      alert('Failed to export graph. Please try again.');
      setDownloadingGraph(false);
    }
  };
  
  return (
    <div className="export-buttons">
      <button 
        onClick={exportJson}
        disabled={downloadingGraph}
        className="btn btn-secondary"
      >
        {downloadingGraph ? 'Downloading...' : 'Download Graph (JSON)'}
      </button>
      
      {/* Additional export buttons for CSV, etc. */}
    </div>
  );
}
```

### Export Modal with Format Options

```jsx
// components/export/ExportModal.jsx
export default function ExportModal({ isOpen, onClose, politicianId }) {
  const [selectedFormat, setSelectedFormat] = useState('json');
  
  if (!isOpen) return null;
  
  return (
    <Modal onClose={onClose} title="Export Graph Data">
      <div className="export-options">
        <label className="format-radio">
          <input 
            type="radio" 
            name="format" 
            value="json" 
            checked={selectedFormat === 'json'}
            onChange={() => setSelectedFormat('json')}
          />
          <span>JSON (for programming/analysis)</span>
        </label>
        
        <label className="format-radio">
          <input 
            type="radio" 
            name="format" 
            value="gdv"
            checked={selectedFormat === 'gdv'}
            onChange={() => setSelectedFormat('gdv')}
          />
          <span>GDV (graph database visualization)</span>
        </label>
        
        <div className="date-range-inputs">
          <div className="input-group">
            <label>Start Year:</label>
            <select value={startYear} onChange={(e) => setStartYear(e.target.value)}>
              {[...Array(30)].map((_, i) => (
                <option key={i} value={2005 + i}>{2005 + i}</option>
              ))}
            </select>
          </div>
          
          <div className="input-group">
            <label>End Year:</label>
            <select value={endYear} onChange={(e) => setEndYear(e.target.value)}>
              {[...Array(20)].map((_, i) => (
                <option key={i} value={new Date().getFullYear() - 19 + i}>{new Date().getFullYear() - 19 + i}</option>
              ))}
            </select>
          </div>
        </div>
      </div>
      
      {/* Format documentation */}
      <details className="format-docs">
        <summary>Show format specification</summary>
        <div className="format-specification">
          <h4>JSON Structure:</h4>
          <pre>{jsonExample}</pre>
          
          <h4>Suggested Tools:</h4>
          <ul>
            <li>Python with pandas for data analysis</li>
            <li>Neo4j Browser for visualization</li>
            <li>D3.js for custom graph layouts</li>
          </ul>
        </div>
      </details>
    </Modal>
  );
}
```

---

## API Endpoint Documentation

### Export Graph Snapshot

**Endpoint:** `POST /api/export/graph-snapshot`

**Parameters:**
- `politicianId` (required): e.g., "UK-CHS-LAB"
- `dateRange` (optional, default: "2015-2024"): Format "{startYear}-{endYear}"

**Response:** Application/JSON with graph data structure

**Error Handling:**
```json
{
  "error": "politician_not_found",
  "message": "Politician ID 'UK-CHS-LAB' not in database"
}
```

---

## Risks

- **Risk:** Browser rejects large downloads (>50MB)  
  - **Mitigation:** Chunked transfer encoding; async download with email option for large files

---

## Definition of Done

- [ ] Export button triggers download without page reload
- [ ] Filename includes politician ID and date range
- [ ] JSON file validates when opened in standard viewers
- [ ] Modal shows format specification on request
- [ ] Large downloads (>25MB) show estimated progress indicator
