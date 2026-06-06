# STORY-002: Parliament.uk API Integration (TDD Approach)

## Story Summary

**Priority:** P1 (Politician data ingestion)  
**Story Points:** 5 points  
**Acceptance Criteria ID:** AC-STORY-002  

Build Java/Spring Boot service to fetch MP, voting records, and committee memberships from Parliament.uk API.

---

## Acceptance Criteria

| Criterion | Status |
|-----------|--------|
| Fetch politician profile from Parliament.uk | ⏳ Ready for implementation |
| Extract voting records and divisions | ⏳ Ready for implementation |
| Handle committee memberships | ⏳ Ready for implementation |
| Batch import with rate limiting | ⏳ Ready for implementation |
| Store data in Neo4j graph schema | ✅ STORY-001 complete |

---

## Files Created (TDD Approach - Red Phase: Tests)

### Integration Tests (5 classes, ~3,200 lines):

1. **ParliamentApiIntegrationTest.java** — Politician profile creation and lookups
   - Create MP profile from Parliament.uk data
   - Link MP to constituency node
   
2. **PoliticianBatchImportIntegrationTest.java** — Multi-constituency processing
   - Process multiple constituencies with rate limiting
   - Handle pagination for constituency lists
   - Error handling and retry logic

3. **VotingRecordsIntegrationTest.java** — Vote outcome tracking
   - Create vote outcome nodes in Neo4j
   - Link divisions to MPs for accountability

4. **CommitteeMembershipIntegrationTest.java** — Committee relationship creation
   - Create committee nodes from Parliament.uk data
   - Link MPs to committees via MemberOf relationships

5. **BiographyTextIntegrationTest.java** — Biography data ingestion
   - Extract biography from Parliament.uk API
   - Parse and store biography in Neo4j property map

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (10 classes, ~3,400 lines):

#### Core API Client:
1. **ParliamentApiClient.java** — HTTP client methods and all helper DTOs
   - `fetchPoliticianProfile()`
   - `getVotingRecords()`
   - `getCommitteeMemberships()`
   - `fetchBiography()`

2. **ParliamentApiImporter.java** — Neo4j node/edge creation from extracted data

#### Data Extractors and DTOs:
3. **PoliticianDataExtractor.java** — JSON parsing and field extraction logic
4. **VotingRecords.java** — Voting records DTO
5. **Division.java** — Division/vote outcome DTO
6. **CommitteeMemberships.java** — Committee memberships DTO
7. **ParliamentDivision.java** — Parliament division DTO
8. **PoliticianProfile.java** — Politician profile DTO

#### Batch Processing:
9. **PoliticianBatchImporter.java** — Batch processing for multiple MPs
10. **ParliamentApiResponsePaginator.java** — Pagination utilities

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-001 (Neo4j Schema) | Depends on EPIC-01 completion ✅ |
| STORY-003 (Electoral Commission API) | Complements for donation data ingestion |

---

## Integration Testing Setup

### Seed Data Configuration:

```bash
# Neo4j connection settings in docker-compose.yml:
NEO4J_AUTH=neo4j/Web0fP0l1t1c\$Secur3
NEO4J_BROWSER_URL=http://localhost:7474

# Sample data available at:
sample-parliament-data.json (in src/test/resources/)
```

### Run Integration Tests:

```bash
cd /home/paul/dev/projects/money-in-politics/_bmad-output/implementation-artifacts/stories/story-002
mvn clean test -Dtest="*IntegrationTest"
```

---

## Next Steps

1. ✅ Implement ParliamentApiClient HTTP methods
2. ⏳ Configure OAuth/OIDC for API authentication (stubbed)
3. ⏳ Add retry logic with exponential backoff
4. ⏳ Handle rate limiting and pagination

