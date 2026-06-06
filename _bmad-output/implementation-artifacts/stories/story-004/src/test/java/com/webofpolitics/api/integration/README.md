# STORY-004: Companies House API Integration (TDD Approach)

## Story Summary

**Priority:** P1 (Company donor matching)  
**Story Points:** 5 points  
**Acceptance Criteria ID:** AC-STORY-004  

Build Java/Spring Boot service to fetch company data from Companies House API for donor matching.

---

## Files Created (TDD Approach - Red Phase: Tests)

### Integration Tests (3 classes, ~1,280 lines):

1. **CompaniesHouseApiIntegrationTest.java** — Company profile creation and SIC categorization
   - Fetch company profile from Companies House
   - Map SIC codes to industry categories

2. **BatchCompanyImportIntegrationTest.java** — Multi-company processing
   - Process multiple companies with rate limiting
   - Handle invalid company IDs gracefully

3. **SicCodeCategorizationIntegrationTest.java** — Industry classification
   - Map 2-digit SIC codes to industry categories
   - Map 4-digit SIC codes with sub-categories

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (8 classes, ~2,200 lines):

#### Core API Client:
1. **CompaniesHouseApiClient.java** — HTTP client methods for all API operations

#### Data Matching and DTOs:
2. **DonorCompanyMatcher.java** — Donor matching logic with SIC code categorization
3. **CompanyProfile.java** — Company profile DTO  
4. **SicCodeCategory.java** — SIC code industry classification
5. **DonationRecipientCompany.java** — Donation recipient company DTO

#### Import and Processing:
6. **CompanyBatchImporter.java** — Batch processing for multiple companies
7. **CompanyErrorHandling.java** — Retry logic with exponential backoff
8. **CompanyApiCall.java** — Company API call operation representation

---

## Integration Testing Setup

### Seed Data Configuration:
- Neo4j connection settings in docker-compose.yml
- Sample company data available at: `src/test/resources/sample-company-data.json`

---

## Next Steps

1. ✅ Implement CompaniesHouseApiClient HTTP methods
2. ⏳ Configure API authentication (stubbed)
3. ⏳ Add retry logic and pagination handling
4. ⏳ Integrate with Neo4j company/donor graph schema (STORY-001 complete)

