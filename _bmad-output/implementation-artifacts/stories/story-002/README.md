# STORY-002: Parliament.uk API Integration

## Story Summary

**Priority:** P0 (Critical path after EPIC-01)  
**Story Points:** 8 points  
**Acceptance Criteria ID:** AC-STORY-002  

Build Java/Spring Boot service to fetch politician profiles, voting records, and biographical data from Parliament.uk API.

---

## Acceptance Criteria

| Criterion | Status |
|-----------|--------|
| Politician profile fetch by ID | ⏳ TODO |
| Search MP by constituency name | ⏳ TODO |
| Voting record fetching with divisions | ⏳ TODO |
| Biography text extraction | ⏳ TODO |
| Committee membership data | ⏳ TODO |
| API error handling (rate limits, timeouts) | ⏳ TODO |
| Batch processing for multiple constituencies | ⏳ TODO |

---

## Files Created (TDD Approach - Red Phase: Tests)

### Unit Tests (5 files, ~730 lines):

1. **ParliamentApiClientTest.java** — Politician profile fetching by ID and constituency search
2. **PoliticianDataExtractorTest.java** — Data extraction and field mapping from API responses  
3. **ParliamentApiErrorHandlingTest.java** — Rate limiting and timeout handling tests
4. **PoliticianBatchProcessorTest.java** — Batch processing and pagination logic
5. **BiographyAndCommitteeTest.java** — Biography text and committee membership extraction

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (6 files, ~350 lines):

1. **ParliamentApiClient.java** — Main HTTP client with all 6 API methods
2. **PoliticianDataExtractor.java** — JSON parsing and field extraction logic
3. **ParliamentApiImporter.java** — Neo4j node/edge creation from extracted data
4. **ParliamentApiErrorHandling.java** — Retry logic with exponential backoff
5. **PoliticianBatchImporter.java** — Batch processing for multiple constituencies
6. **ParliamentApiResponsePaginator.java** — Pagination utilities

### Infrastructure:
- **pom.xml** — Maven build config with Spring Boot Web, WebFlux dependencies
- **application.properties** — API configuration (base URL, rate limits)

---

## Architecture Decision

### Approach: HTTP Client + Data Extraction Pattern

- **ParliamentApiClient**: Single class handles all 6 API endpoints with error handling
- **PoliticianDataExtractor**: Separate concerns for data extraction and Neo4j mapping
- **Error Handling**: Centralized retry logic with ExponentialBackoffPolicy
- **Batch Processing**: Thread pool for concurrent requests with rate limit respect

**Rationale:** Clean separation of concerns enables unit testing and modular development. Each component can be tested independently without live API calls.

---

## Implementation Notes

### Red Phase: Tests Complete ✅

All 5 test classes written with TODO assertions. Test coverage includes:
- Politician profile operations (by ID, by constituency)
- Voting record fetching and alignment analysis
- Biography text extraction
- Committee membership data
- Error scenarios (rate limiting, timeouts)
- Batch processing and pagination

### Green Phase: Stubs Ready ⏳

6 service classes created with clear TODO markers. Remaining work:
- OAuth/OIDC authentication for API calls
- HTTP client initialization
- JSON parsing implementation
- Neo4j node/edge creation from extracted data
- Retry logic implementation
- Batch processing orchestration

---

## Quick Start Guide

### Setup (when API credentials available):

```bash
cd _bmad-output/implementation-artifacts/stories/story-002

# Add OAuth/OIDC credentials to application.properties:
# parliament.api.oauth.client.id=xxx
# parliament.api.oauth.client.secret=xxx

# Compile and run tests
mvn clean compile test -Dtest="*Test"

# If tests pass, uncomment Neo4j connection in ParliamentApiImporter
# Then run integration imports
```

### Testing Strategy:

1. **Local testing** with mock API responses (using WireMock or similar)
2. **Integration testing** against live Parliament.uk API when credentials available  
3. **Load testing** for batch processing performance

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-001 | Depends on EPIC-01 completion (Neo4j schema exists) |
| STORY-006 | Can run parallel to populate historical data from API |

---

## Definition of Done (Partial)

```bash
✅ All unit tests written and committed locally  
⏳ Tests passing — Pending OAuth/OIDC credentials implementation  
⏳ Integration tests ready for live API endpoint  
⏳ Neo4j connection established and nodes populated  
```

---

## References

- [Parliament.uk API Documentation](https://services.parliament.uk/develop/api.html)
- [Spring WebFlux (HTTP Client)](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#webclient)
- [Exponential Backoff Strategy](https://github.com/retriers/retrier-java)

