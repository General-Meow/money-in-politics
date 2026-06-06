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
| Node labels exist (:Politician, :Company, :Donation, :Relationship, :Party) | ⏳ Integration tests pending |
| Relationship types created (DONATION_RECEIVED, RELATED_TO, WORKS_AT, MEMBER_OF, APPEARED_WITH) | ⏳ Integration tests pending |
| Indexes created on high-selectivity query fields | ⏳ Integration tests pending |
| Schema initialization script tested in Docker container | ✅ Partial - TDD scaffolding done |
| Documentation updated with Cypher examples | ✅ Complete |

---

## Files Created (TDD + Integration Testing)

### Unit Tests (5 files, ~729 lines)
1. `Neo4jSchemaValidationTest.java` — Validates all node labels, relationships, indexes
2. `PoliticianNodeCreatorTest.java` — Tests Politician node creation and lookup
3. `CompanyNodeCreatorTest.java` — Tests Company node creation and lookup  
4. `RelationshipBuilderTest.java` — Tests all 5 relationship types
5. `GraphIndexCreatorTest.java` — Tests index creation on query fields

### Integration Tests (6 files, ~580 lines)
1. `Neo4jSchemaIntegrationTest.java` — Complete suite of integration tests
2. `PoliticianLookupIntegrationTest.java` — Node retrieval operations by name/constituency/party
3. `RelationshipValidationIntegrationTest.java` — Relationship edge creation validation
4. `CompanyLookupIntegrationTest.java` — Company entity operations
5. `IndexCreationIntegrationTest.java` — Query plan analysis for indexed lookups
6. `SchemaInitializerValidationTest.java` — End-to-end workflow validation

### Implementation (5 files, ~278 lines)
1. `Neo4jSchemaInitializer.java` — Orchestrates complete schema initialization
2. `PoliticianNodeCreator.java` — Create/query Politician nodes with Cypher
3. `CompanyNodeCreator.java` — Create/query Company nodes with legal details
4. `RelationshipBuilder.java` — Build all relationship edges
5. `GraphIndexCreator.java` — Create indexes for high-selectivity queries

### Infrastructure (6 files, ~280 lines)
1. `schema-initialization.cypher` — Complete Neo4j schema script
2. `docker-compose.neo4j.yml` — Neo4j 5.20 Community Edition container (dev/testing)
3. `docker-compose.neo4j.integration.yml` — Integration testing configuration
4. `pom.xml` — Maven build with Spring Boot, Neo4j driver, JUnit 5
5. `scripts/schema-validation.sh` — Manual validation script
6. `scripts/neo4j-healthcheck.sh` — Health monitoring utility

### Documentation (3 files, ~700 lines)
1. `README.md` — Story summary, acceptance criteria, implementation notes
2. `INTEGRATION-README.md` — Integration testing guide with setup instructions
3. `schema/validation-queries.cypher` — Manual validation query examples

---

## Architecture Decision

### Approach: TDD + Integration Testing

**Phase 1 (Red): Unit tests written first**  
✅ All 5 test classes created with TODO assertions covering acceptance criteria

**Phase 2 (Green): Stub implementations created**  
⏳ Implementation stubs ready, awaiting integration validation

**Phase 3 (Refactor): Integration testing**  
⏳ Integration tests ready to validate against live Neo4j instance

---

## Quick Start Guide

### Run Unit Tests Locally
```bash
cd _bmad-output/implementation-artifacts/stories/story-001
mvn test -pl pom.xml
```

### Start Neo4j for Integration Testing
```bash
docker compose -f docker-compose.neo4j.integration.yml up -d
```

### Apply Schema Script
```bash
docker compose exec neo4j-integration-db cypher-shell -u neo4j -p password < schema-initialization.cypher
```

### Run Integration Tests
```bash
mvn test -Dtest=com.webofpolitics.schema.integration.*Tests -pl pom.xml
```

### Manual Validation
```bash
./scripts/schema-validation.sh
```

---

## Definition of Done (Partial)

```bash
✅ All unit tests written and committed locally  
✅ Integration tests written with live Neo4j validation  
✅ Acceptance criteria mapped to test methods  
✅ Implementation stubs created with clear TODO markers  
✅ Docker Compose configuration ready for development  
✅ Documentation updated with examples and scripts  
⏳ Tests passing — Pending integration test execution
```

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-002 (Parliament.uk API) | Depends on EPIC-01 completion, can run parallel after schema init |

---

## References

- [Neo4j Graph Database Basics](https://neo4j.com/graph-database/)
- [UK Parliament API Documentation](https://services.parliament.uk/develop/api.html)
- [Neo4j Index Management Guide](https://neo4j.com/docs/cypher-manual/current/indexes/)

