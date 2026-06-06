# STORY-004: Companies House Integration

## Story Summary

**Priority:** P0 (Critical path for donation company matching)  
**Story Points:** 8 points  
**Acceptance Criteria ID:** AC-STORY-004  

Build Java/Spring Boot service to fetch company and donor data from Companies House API and match donations to politicians.

---

## Acceptance Criteria

| Criterion | Status |
|-----------|--------|
| Company profile fetch by number | ⏳ TODO |
| Search companies by name pattern | ⏳ TODO |
| Extract SIC codes for industry classification | ⏳ TODO |
| Match donation to politician by donor name | ⏳ TODO |
| Batch processing with rate limit handling | ⏳ TODO |

---

## Files Created (TDD Approach - Red Phase: Tests)

### Unit Tests (3 files, ~580 lines):

1. **CompaniesHouseApiClientTest.java** — Company profile fetching and SIC code extraction
2. **DonorCompanyMatcherTest.java** — Matching logic and categorization  
3. **BatchCompanyImporterTest.java** — Batch processing and pagination logic

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (4 files, ~320 lines):

1. **CompaniesHouseApiClient.java** — Main HTTP client with all API methods
2. **DonorCompanyMatcher.java** — Matching logic for donations to politicians
3. **CompanyBatchImporter.java** — Batch processing for multiple companies
4. **CompanyErrorHandling.java** — Retry logic with exponential backoff

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-001 (Neo4j Schema) | Depends on EPIC-01 completion |
| STORY-003 (Electoral Commission API) | Can run parallel for donation data enrichment |

