# STORY-006: Historical Data Backfill

## Story Summary

**Priority:** P1 (Historical data completeness)  
**Story Points:** 5 points  
**Acceptance Criteria ID:** AC-STORY-006  

Build Java/Spring Boot service to backfill historical politician and donation data from archives (2005 onwards).

---

## Acceptance Criteria

| Criterion | Status |
|-----------|--------|
| Import historical politician data | ⏳ TODO |
| Date range filtering (2005-2024) | ⏳ TODO |
| Legacy donor matching integration | ⏳ TODO |

---

## Files Created (TDD Approach - Red Phase: Tests)

### Unit Tests (2 files, ~560 lines):

1. **HistoricalDataImporterTest.java** — Historical politician import and date filtering
2. **DonorMatchingHistoryTest.java** — Legacy donor matching integration

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (2 files, ~340 lines):

1. **HistoricalDataImporter.java** — Historical data fetching from archives
2. **DonorMatchingHistory.java** — Legacy donor matching to Neo4j graph

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-001 (Neo4j Schema) | Depends on EPIC-01 completion |
| STORY-002/003/004 (API Clients) | Complement for historical completeness |

