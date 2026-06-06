# STORY-003: Electoral Commission API Integration (TDD Approach)

## Story Summary

**Priority:** P1 (Donation data transparency)  
**Story Points:** 5 points  
**Acceptance Criteria ID:** AC-STORY-003  

Build Java/Spring Boot service to fetch donation data from Electoral Commission API.

---

## Files Created (TDD Approach - Red Phase: Tests)

### Integration Tests (4 classes, ~1,280 lines):

1. **ElectoralCommissionApiIntegrationTest.java** — Donation data ingestion
   - Fetch donation ledger from Electoral Commission
   - Map donors to Neo4j nodes

2. **BatchDonationProcessingIntegrationTest.java** — Multi-politician processing
   - Process donations for multiple politicians with rate limits
   - Handle pagination for large datasets

3. **ErrorHandlingIntegrationTest.java** — Retry logic implementation
   - Implement exponential backoff for failed API calls
   - Handle invalid donation IDs gracefully

4. **PaginationIntegrationTest.java** — Response pagination
   - Handle paginated donation responses

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (11 classes, ~2,800 lines):

#### Core API Client:
1. **ElectoralCommissionApiClient.java** — HTTP client methods for all API operations

#### Data Extractors and DTOs:
2. **DonationDataExtractor.java** — JSON parsing and field extraction logic
3. **Donor.java** — Donor DTO
4. **DonationLedger.java** — Donation ledger DTO
5. **DisclosureDocument.java** — Disclosure document DTO
6. **DonationRecord.java** — Individual donation record
7. **DonationApiResponse.java** — API response wrapper

#### Import and Processing:
8. **DonationImporter.java** — Neo4j node/edge creation from extracted data
9. **DonationBatchProcessor.java** — Batch processing for multiple politicians
10. **DonationErrorHandling.java** — Retry logic with exponential backoff
11. **DonationResponsePaginator.java** — Pagination utilities

---

## Integration Testing Setup

### Seed Data Configuration:
- Neo4j connection settings in docker-compose.yml
- Sample donation data available at: `src/test/resources/sample-donation-data.json`

---

## Next Steps

1. ✅ Implement ElectoralCommissionApiClient HTTP methods
2. ⏳ Configure API authentication (stubbed)
3. ⏳ Add retry logic and pagination handling
4. ⏳ Integrate with Neo4j donation graph schema (STORY-001 complete)

