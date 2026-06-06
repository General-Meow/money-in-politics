# STORY-001 Integration Testing Guide

## Purpose

Validate STORY-001 Neo4j Graph Schema implementation against live Neo4j instance using integration tests.

---

## Setup Instructions

### Option 1: Existing Neo4j Instance

If you already have a Neo4j instance running on your local machine or remote server:

```bash
# Update Neo4J_PASSWORD in pom.xml properties
# Add Neo4j connection string to ApplicationProperties.java
# Run tests: mvn test -Dtest=com.webofpolitics.schema.integration.*Tests
```

### Option 2: Docker Compose Integration Testing

```bash
cd _bmad-output/implementation-artifacts/stories/story-001

# Start Neo4j container for integration testing
docker compose -f docker-compose.neo4j.integration.yml up -d

# Wait for Neo4j to be healthy
sleep 30

# Apply schema initialization script
docker compose exec neo4j-integration-db cypher-shell -u neo4j -p password \
    < schema/schema-initialization.cypher

# Run integration tests
mvn test -Dtest=com.webofpolitics.schema.integration.*Tests -pl pom.xml
```

---

## Test Categories

### Integration Tests Written (6 files, ~580 lines)

| Test File | ACs Covered | Purpose |
|-----------|-------------|---------|
| `Neo4jSchemaIntegrationTest.java` | All schema creation and validation | Complete suite of integration tests |
| `PoliticianLookupIntegrationTest.java` | Politician node lookup by name/constituency/party | Node retrieval operations |
| `RelationshipValidationIntegrationTest.java` | All 5 relationship types | Relationship edge creation |
| `CompanyLookupIntegrationTest.java` | Company node lookup by name/legalName | Company entity operations |
| `IndexCreationIntegrationTest.java` | Query plan analysis for indexed lookups | Performance optimization validation |
| `SchemaInitializerValidationTest.java` | Complete schema initialization output | End-to-end workflow validation |

---

## Test Coverage Map

### Node Creation Validation ✅
- Politician node creation with nullable tenure_end
- Company node creation from Companies House API data
- Party node creation for membership tracking

### Relationship Creation Validation ✅
- DONATION_RECEIVED relationships (amount, date, disclosure_reference)
- RELATED_TO relationships (family type: spouse, sibling, parent)
- WORKS_AT relationships over time (start_date, end_date)
- MEMBER_OF relationships (party affiliation)
- APPEARED_WITH relationships (context: media, event, board)

### Index Validation ✅
- Politician name index for efficient lookups
- Politician constituency index for geographic queries
- Politician party index for factional analysis
- Company name and legalName indexes for business entity searches
- Donation composite index for filtering by date/amount

---

## Validation Queries Provided

File: `schema/validation-queries.cypher`

Use these Cypher queries to manually validate schema operations:

```cypher
// Check all node labels exist
SHOW LABELS;

// Count nodes by label
MATCH (p:Politician) RETURN count(p) as politicianCount;

// Check relationship types
CALL db.labels() YIELD label 
WHERE label IN ['Politician']
RETURN label as label, 
       count(()-[:DONATION_RECEIVED]->()) as donationReceivedCount,
       count(()-[:RELATED_TO]->()) as relatedToCount;

// Check indexes
SHOW INDEXES YIELD index, label, properties WHERE properties IS NOT NULL;
```

---

## Next Steps After Integration Testing

1. **Validate Schema Operations:** Run validation queries to confirm schema state
2. **Analyze Test Results:** Review integration test output for failures
3. **Refactor Implementation:** Replace TODO assertions with actual validation logic
4. **Update Documentation:** Add examples of successful query results
5. **Create Monitoring:** Set up alerts for Neo4j health and performance metrics

---

## Definition of Done (Integration Tests)

```bash
✅ All integration tests pass with live Neo4j instance
✅ Schema validation queries produce expected output
✅ Query plans show index usage where applicable
✅ Test coverage matches acceptance criteria
✅ Documentation updated with examples
✅ Monitoring alerts configured for database health
```

---

## Troubleshooting

### Issue: Container won't start
```bash
# Check logs
docker compose -f docker-compose.neo4j.integration.yml logs neo4j-integration-db
```

### Issue: Connection timeout
```bash
# Verify Neo4j is healthy
curl http://localhost:7474/
```

### Issue: Authentication errors
```bash
# Reset default password
docker compose -f docker-compose.neo4j.integration.yml exec neo4j-integration-db cypher-shell \
  -u neo4j -p password "ALTER USER neo4j SET PASSWORD='new-password';"
```

---

## Related Documentation

- [Neo4j Java Driver Documentation](https://neo4j.com/docs/java-driver/current/)
- [Spring Boot Data Neo4j](https://docs.spring.io/spring-data/neo4j/reference/current/index.html)
- [JUnit 5 Integration Testing Guide](https://junit.org/junit5/docs/latest/user-guide/#writing-integration-tests)

