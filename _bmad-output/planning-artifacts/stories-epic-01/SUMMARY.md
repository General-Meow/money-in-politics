# EPIC-01 Stories Summary

All 6 stories in this epic contribute to building the core graph infrastructure.

## Story Priority Order (Suggested Implementation Sequence)

| Story | Description | Priority | Reason |
|-------|-------------|----------|--------|
| STORY-001 | Neo4j Graph Schema | P0 | Foundation - everything else depends on this |
| STORY-002 | Parliament.uk API Integration | P0 | Primary data source for politician profiles |
| STORY-006 | Historical Data Backfill | P0 | Critical path to MVP completeness |
| STORY-003 | Electoral Commission API Integration | P1 | Essential but can be parallelized |
| STORY-004 | Companies House API Integration | P1 | Can run after politician data exists |
| STORY-005 | Fallback Scrapers | P2 | Safety net; useful for partial API coverage |

## Implementation Recommendation

**Weeks 1-3:** Complete STORY-001 (Schema) + STORY-002 (Parliament API)  
**Weeks 4-6:** Parallelize STORY-003, STORY-004, STORY-005  
**Weeks 7-8:** Execute STORY-006 (Historical backfill in background)

## Acceptance Criteria Checklist

All stories must pass their definition-of-done before marking epic complete:

### Story Completion
- [ ] STORY-001: Neo4j Graph Schema Complete
- [ ] STORY-002: Parliament.uk API Integration Functional  
- [ ] STORY-003: Electoral Commission API Integrated
- [ ] STORY-004: Companies House API Integrated
- [ ] STORY-005: Fallback Scrapers Operational
- [ ] STORY-006: Historical Backfill Complete (2005+)

### Epic Completion
- [ ] Neo4j container running and accessible locally
- [ ] All API integrations tested with live endpoints
- [ ] Data quality reports available for each ingestion pipeline
- [ ] Monitoring dashboards show ingestion metrics
- [ ] Documentation updated with field mappings and examples
- [ ] Code reviewed and merged to main branch
- [ ] Unit tests written for all parsing/extraction logic

## Estimated Completion: 8-12 Weeks from Project Start

Dependencies: None (this is the foundation epic)

Next Epic: EPIC-02 (Citizen-Facing Search and Discovery)
