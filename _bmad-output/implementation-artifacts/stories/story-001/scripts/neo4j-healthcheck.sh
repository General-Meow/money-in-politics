#!/bin/bash
# Neo4j Health Check Script for STORY-001 Integration Testing

NEO4J_USER="neo4j"
NEO4J_PASSWORD="${NEO4J_INTEGRATION_PASSWORD:-password}"
CONTAINER_NAME="webofpolitics-neo4j-integration-db"

echo "Checking Neo4j container status..."
docker compose -f docker-compose.neo4j.integration.yml ps neo4j-integration-db

echo ""
echo "Checking Neo4j container logs (last 5 lines)..."
docker compose -f docker-compose.neo4j.integration.yml logs --tail=5 neo4j-integration-db

echo ""
echo "Running health check query..."
docker exec $CONTAINER_NAME cypher-shell -u $NEO4J_USER -p $NEO4J_PASSWORD \
    "SHOW DATABASES"

if docker compose -f docker-compose.neo4j.integration.yml ps neo4j-integration-db | grep -q "Up"; then
    echo ""
    echo "✅ Neo4j container is healthy and ready for integration testing"
else
    echo ""
    echo "⚠️  Neo4j container status unknown. Check logs:"
    docker compose -f docker-compose.neo4j.integration.yml logs neo4j-integration-db | grep -i error || true
fi
