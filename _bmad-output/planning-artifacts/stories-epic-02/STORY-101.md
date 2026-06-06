# STORY-101: Politician Search Interface

**Epic:** EPIC-02 (Citizen-Facing Search and Discovery)  
**Priority:** P1  
**Story Points:** 5  
**Assignee:** [TBD]

## As A

Citizen/Voter/Investigator

## I Want

A search interface to find politicians by name or constituency

## So That

I can quickly locate who represents me or investigate a specific politician

---

## Acceptance Criteria

### Given an empty search box
**When** I submit the search  
**Then** homepage loads with featured entities (current PM, Speaker, party leaders)

### Given a valid name or constituency query
**When** I type and press Enter or click Search  
**Then** within 500ms results appear showing:
- Politician photo
- Full name
- Party affiliation
- Current/former status indicator
- Constituency (if applicable)

### Given partial text input (misspelling, abbreviation)
**When** I search for "Keir Sarm" instead of "Keir Starmer"  
**Then** fuzzy matching finds the correct MP with spelling suggestion

### Given multiple matches (e.g., "John Smith" in different constituencies)
**When** I search  
**Then** results list all matches sorted by relevance, distinguished by:
- Party name
- Constituency
- Former vs current status

---

## UI Requirements

### Search Bar Component

```jsx
// components/search/PoliticianSearch.jsx
import { useState } from 'react';
import { usePoliticianData } from '../../hooks/usePoliticianData';

export default function PoliticianSearch() {
  const [query, setQuery] = useState('');
  const { politicians, isLoading, error } = usePoliticianData();
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!query.trim()) {
      // Show featured entities
      return;
    }
    
    const results = await searchPoliticians(query);
    setResults(results);
  };
  
  return (
    <div className="search-container">
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Search politicians by name or constituency..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          className="search-input"
          disabled={isLoading}
        />
        <button 
          type="submit" 
          disabled={!query.trim() || isLoading}
          className="search-button"
        >
          {isLoading ? 'Searching...' : 'Search'}
        </button>
      </form>
      
      {/* Results list */}
      {results && <PoliticianResults results={results} />}
      
      {/* Error state */}
      {error && <ErrorMessage error={error} />}
    </div>
  );
}
```

### Results Card Component

```jsx
// components/search/PoliticianResultCard.jsx
export default function PoliticianResultCard({ politician }) {
  return (
    <div className="politician-card">
      <img 
        src={politician.photoUrl} 
        alt={`${politician.fullName}`}
        className="politician-photo"
      />
      
      <h3>{politician.fullName}</h3>
      
      <div className="meta-info">
        <span className={`party-badge party-${politician.party.toLowerCase()} `}>
          {politician.party}
        </span>
        {politician.former && (
          <span className="former-badge">Former MP</span>
        )}
        <span>{politician.constituency}</span>
      </div>
      
      <Link to={`/profile/${politician.id}`} className="view-profile-button">
        View Full Profile →
      </Link>
    </div>
  );
}
```

### Featured Entities Display (when search is empty)

```jsx
// components/search/FeaturedEntities.jsx
export default function FeaturedEntities() {
  return (
    <div className="featured-entities">
      <h2>Featured Politicians</h2>
      
      {featuredPoliticians.map(p => (
        <Link key={p.id} to={`/profile/${p.id}`} className="featured-card">
          <img src={p.photoUrl} alt={p.fullName} />
          <div className="card-content">
            <h3>{p.fullName}</h3>
            <span>{p.party}</span>
          </div>
        </Link>
      ))}
    </div>
  );
}
```

---

## API Integration

### Search Endpoint Design

```java
@RestController
@RequestMapping("/api")
public class PoliticianRestController {
    
    @GetMapping("/search")
    public List<PoliticianSummary> searchPoliticians(
            @RequestParam String query,
            @RequestParam(defaultValue = "current|former") 
            String includeFormer) {
        
        // Fuzzy search by name or constituency
        // Limit to 20 results for performance
        
        return politicianRepository
            .searchByNameOrConstituency(query.toLowerCase(), includeFormer);
    }
}
```

### Response Format

```json
[
  {
    "id": "UK-CHS-LAB",
    "fullName": "Sir Keir Rodney Starmer",
    "name": "Keir Starmer",
    "party": "Labour",
    "constituency": "Holborn and St Pancras",
    "photoUrl": "https://example.com/starmer.jpg",
    "role": "Leader of the House of Commons",
    "former": false,
    "currentSince": "2024-05-05"
  }
]
```

---

## Risks

- **Risk:** Search returns too many results for common names  
  - **Mitigation:** Limit to top 20; add constituency disambiguation

- **Risk:** Fuzzy matching produces false positives  
  - **Mitigation:** Configure fuzzy threshold conservatively (e.g., edit distance ≤3)

---

## Definition of Done

- [ ] Search input accepts name or constituency text
- [ ] Results display politician photo, name, party, constituency
- [ ] Fuzzy matching finds correct results for common misspellings
- [ ] Multiple matches distinguished by party and constituency
- [ ] Former/current status clearly indicated
- [ ] Clicking result navigates to profile page
- [ ] Empty search shows featured entities
- [ ] Error handling displays helpful message if no results found

---

## Success Metrics

- Search success rate: 95% of searches return relevant results within first page
- Time to find politician: <10 seconds from search box to profile page
- Mobile usability: All users can complete search on touchscreen devices
