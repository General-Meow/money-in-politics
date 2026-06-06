#!/bin/bash
# STORY-001 Neo4j Schema Validation Scripts

NEO4J_USER="neo4j"
NEO4J_PASSWORD="${NEO4J_INTEGRATION_PASSWORD:-password}"
CONTAINER_NAME="webofpolitics-neo4j-integration-db"

echo "Validating Neo4j container health..."
docker compose -f docker-compose.neo4j.integration.yml up --abort-on-container-exit || true

echo ""
echo "Validating node labels..."
docker exec $CONTAINER_NAME cypher-shell -u $NEO4J_USER -p $NEO4J_PASSWORD "SHOW LABELS;"

echo ""
echo "Checking relationship types..."
docker exec $CONTAINER_NAME cypher-shell -u $NEO4J_USER -p $NEO4J_PASSWORD \
    "MATCH (p)-[r:RELATED_TO|WORKS_AT|MEMBER_OF|DONATION_RECEIVED]->() RETURN count(r) as totalRelationships;"

echo ""
echo "Checking indexes..."
docker exec $CONTAINER_NAME cypher-shell -u $NEO4J_USER -p $NEO4J_PASSWORD \
    "SHOW INDEXES YIELD index, label WHERE properties IS NOT NULL;"

echo ""
echo "Schema validation complete."
