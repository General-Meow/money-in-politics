# STORY-002: Parliament.uk API Integration

**Epic:** EPIC-01 (Graph Engine and Data Ingestion Pipeline)  
**Priority:** P0  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Backend Developer

## I Want

Spring Boot service that integrates with Parliament.uk API to auto-populate MP profiles

## So That

Politician data is captured automatically from trusted government source without manual entry

---

## Acceptance Criteria

### Given a valid Parliament.uk API key
**When** the ingestion service runs  
**Then** for each constituency in UK:
- MP profile data is extracted (name, role, party, tenure dates)
- Profile photo URL is captured
- Parliamentary office email/phone if available
- Committee memberships are recorded

### When an MP leaves office
**Then** tenure_end date is recorded and node status set to "former"

### Given partial or incomplete API data  
**When the scraper runs  
**Then** null fields are flagged with `source_status: "incomplete"` for review

---

## Technical Implementation

### API Endpoints Used

- `/api/v2/people` — List of all current/former MPs
- `/api/v2/people/{person_id}` — Detailed MP profile
- `/api/v2/people/{person_id}/votes` — Voting records (for future voting analysis)
- `/api/v2/departments/{person_id}/committees` — Committee memberships

### Example Code Structure

```java
// ParliamentApiService.java
@Service
public class ParliamentApiService {
    
    private final RestTemplate restTemplate;
    private String apiKey = System.getenv("PARLIAMENT_API_KEY");
    
    @PostMapping("/ingest/mps")
    public void ingestAllMps() {
        Map<String, Object> mpsResponse = getMpsList();
        
        for (Object mpData : mpsResponse.get("results")) {
            MpProfile profile = parseMp(mpData);
            upsertPoliticianToNeo4j(profile);
        }
    }
    
    private MpProfile parseMp(Object apiData) {
        // Extract name, party, constituency from JSON
        // Handle null/empty fields gracefully
        return new MpProfile(...);
    }
}
```

---

## Data Mapping

| API Field | Neo4j Property | Notes |
|-----------|----------------|-------|
| `name` | `name`, `fullName` | Use full name if available |
| `constituencyPartyMembership.name` | `party` | Capitalize party name |
| `constituencyName` | `constituency` | For former MPs, use current designation |
| `dateJoinedHouseOfCommons` | `tenure_start` | Format: ISO-8601 |
| `status` (active/former) | `tenure_end` | null if active |
| `officeHolderPhotoUrl` | `photo_url` | Public domain URL |

---

## Risks

- **Risk:** Parliament.uk API rate limits restrict scraping frequency  
  - **Mitigation:** Implement exponential backoff; respect rate limit headers

- **Risk:** Historical data not available via current API  
  - **Mitigation:** Accept this as out-of-scope; archive API responses for future work

---

## Definition of Done

- [ ] Service connects to Parliament.uk API with valid credentials
- [ ] MP profiles ingested for all current and recent former MPs
- [ ] Null fields logged to monitoring endpoint
- [ ] Service handles API errors gracefully (timeout, 429)
- [ ] Unit tests cover API response parsing
- [ ] Integration test verifies end-to-end ingestion

---

## Success Metrics

- MPs ingested: Target 600+ current/former MPs from 2005-present
- Data completeness: >80% of required fields populated from API
- Error rate: <1% requests failing or timing out
