# STORY-003: Electoral Commission API Integration

## Story Summary

**Priority:** P0 (Critical path for donation data)  
**Story Points:** 8 points  
**Acceptance Criteria ID:** AC-STORY-003  

Build Java/Spring Boot service to fetch donation records, donor information, and disclosure documents from Electoral Commission API.

---

## Acceptance Criteria

| Criterion | Status |
|-----------|--------|
| Donation ledger fetch by politician | ⏳ TODO |
| Disclosure document fetching | ⏳ TODO |
| Donor profile searching | ⏳ TODO |
| Batch processing for multiple politicians | ⏳ TODO |
| API error handling (rate limits, timeouts) | ⏳ TODO |

---

## Files Created (TDD Approach - Red Phase: Tests)

### Unit Tests (4 files, ~680 lines):

1. **ElectoralCommissionApiClientTest.java** — Donation ledger fetching, disclosure documents
2. **DonationDataExtractorTest.java** — Data extraction and field mapping from API responses  
3. **BatchDonationImporterTest.java** — Batch processing and pagination logic
4. **ElectoralCommissionErrorHandlingTest.java** — Rate limiting and timeout handling tests

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (6 files, ~350 lines):

1. **ElectoralCommissionApiClient.java** — Main HTTP client with all API methods
2. **DonationDataExtractor.java** — JSON parsing and field extraction logic
3. **DonationImporter.java** — Neo4j node/edge creation from extracted data
4. **DonationBatchProcessor.java** — Batch processing for multiple politicians
5. **DonationErrorHandling.java** — Retry logic with exponential backoff
6. **DonationResponsePaginator.java** — Pagination utilities

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-001 (Neo4j Schema) | Depends on EPIC-01 completion |
| STORY-002 (Parliament API) | Can run parallel for politician data enrichment |

