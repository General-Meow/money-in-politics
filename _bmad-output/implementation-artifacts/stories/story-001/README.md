# STORY-001: Neo4j Graph Schema Implementation

## Story Summary

**Priority:** P0 (Foundation work)  
**Story Points:** 5 points  
**Acceptance Criteria ID:** AC-STORY-001  

Creates a properly schema-modelled Neo4j graph database with all entity types and relationship definitions.

---

## Acceptance Criteria

| Criterion | Status |
|-----------|--------|
| Node labels exist (:Politician, :Company, :Donation, :Relationship, :Party) | 🟡 TODO |
| Relationship types created (DONATION_RECEIVED, RELATED_TO, WORKS_AT, MEMBER_OF, APPEARED_WITH) | 🟡 TODO |
| Indexes created on high-selectivity query fields | 🟡 TODO |
| Schema initialization script tested in Docker container | 🟡 TODO |
| Documentation updated with Cypher examples | ⚪ DONE |

---

## Implementation Files

### Java Service Classes (Red/Green/Refactor Target)

1. **PoliticianNodeCreator.java** — Create and query Politician nodes
2. **CompanyNodeCreator.java** — Create and query Company nodes  
3. **RelationshipBuilder.java** — Create all relationship edge types
4. **GraphIndexCreator.java** — Create indexes for query optimization
5. **Neo4jSchemaInitializer.java** — Orchestrate complete schema initialization

### Cypher Schema Script

- **schema-initialization.cypher** — Complete Neo4j graph schema with example nodes/relationships

---

## Test Files (TDD Approach)

| Test File | Purpose |
|-----------|---------|
| Neo4jSchemaValidationTest.java | Validate all node labels, relationships, and indexes |
| PoliticianNodeCreatorTest.java | Test Politician node creation and lookup |
| CompanyNodeCreatorTest.java | Test Company node creation and lookup |
| RelationshipBuilderTest.java | Test all relationship types |
| GraphIndexCreatorTest.java | Test index creation queries |

---

## Architecture Decision

### Approach: Layered Service Pattern

- **Data Access Layer:** Neo4jSchemaInitializer orchestrates schema creation
- **Node Creator Layer:** Politician/Company creators handle specific entity operations
- **Relationship Builder Layer:** Single class handles all relationship types
- **Indexing Layer:** GraphIndexCreator handles query optimization

**Rationale:** Separation of concerns enables unit testing and modular development. Each service can be tested independently.

---

## Implementation Notes

### Red Phase (Tests Written)

✅ All 5 test classes created with TODO assertions  
📋 Acceptance criteria mapped to test methods  

### Green Phase (Implementation Complete)

⏳ Java service classes implemented as stubs  
⏳ Cypher schema script ready for Neo4j execution  
⏳ Docker integration pending  

### Refactor Phase (Optimization)

- Add integration tests with live Neo4j container
- Optimize Cypher queries for bulk operations
- Consider CYPHER text blocks for cleaner syntax

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-002 | Depends on EPIC-01 completion (Parliament API) |
| STORY-003 | Can run parallel after STORY-001 schema complete |

---

## Definition of Done

```bash
✅ All tests pass locally with test container
✅ Integration tests pass with live Neo4j instance  
✅ Schema matches acceptance criteria specification
✅ Documentation updated in repo README
✅ Code reviewed and merged to main branch
✅ Unit tests written for all parsing logic (N/A - schema only)
```

---

## References

- [Neo4j Graph Database Basics](https://neo4j.com/graph-database/)
- [UK Parliament API Documentation](https://services.parliament.uk/develop/api.html)
- [Neo4j Index Management Guide](https://neo4j.com/docs/cypher-manual/current/indexes/)

