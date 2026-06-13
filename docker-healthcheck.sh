#!/bin/bash
# Health Check Script for Web of Politics Docker Compose Services
# Usage: ./docker-healthcheck.sh

set -e  # Exit on any error

echo "=========================================="
echo "Web of Politics - Health Check"
echo "=========================================="

# Wait for Neo4j to start
echo "[Neo4j] Waiting for database..."
until docker compose exec neo4j \
  wget --spider --quiet http://localhost:7474 || true; do
    echo "  Neo4j not ready yet..."; sleep 3
done
echo "  [Neo4j] Ready"

# Wait for API to start
echo "[API] Waiting for backend service..."
until docker compose exec api \
  wget --spider --quiet http://localhost:8080/actuator/health || true; do
    echo "  API not ready yet..."; sleep 3
done
echo "  [API] Ready"

# Wait for Frontend to start
echo "[Frontend] Waiting for frontend service..."
until docker compose exec frontend \
  wget --spider --quiet http://localhost:80 || true; do
    echo "  Frontend not ready yet..."; sleep 3
done
echo "  [Frontend] Ready"

# Run all health checks
echo ""
echo "Running detailed health checks..."

# Neo4j Health Check
echo "[Neo4j] Checking database status..."
docker compose exec neo4j wget --spider --quiet http://localhost:7474 && echo "  ✓ Neo4j is healthy" || echo "  ✗ Neo4j failed"

# API Health Check
echo "[API] Checking backend health..."
docker compose exec api wget --spider --quiet http://localhost:8080/actuator/health \
  && echo "  ✓ API is healthy" \
  || echo "  ✗ API failed"

# Frontend Health Check  
echo "[Frontend] Checking frontend health..."
docker compose exec frontend wget --spider --quiet http://localhost/health \
  && echo "  ✓ Frontend is healthy" \
  || echo "  ✗ Frontend failed"

# API Endpoints Check
echo ""
echo "[API Endpoints] Testing REST endpoints..."

# Politicians endpoint
curl -s http://localhost:8080/api/politicians > /dev/null 2>&1 && \
  echo "  ✓ GET /api/politicians responds" || \
  echo "  ✗ GET /api/politicians failed (will be populated after data import)"

# Companies endpoint
curl -s http://localhost:8080/api/companies > /dev/null 2>&1 && \
  echo "  ✓ GET /api/companies responds" || \
  echo "  ✗ GET /api/companies failed (will be populated after data import)"

# Neo4j Browser Link
echo ""
echo "=========================================="
echo "Access URLs:"
echo "=========================================="
echo "Neo4j Browser:     http://localhost:7474"
echo "API Service:       http://localhost:8080"
echo "Frontend App:      http://localhost:3000"
echo "=========================================="

echo ""
echo "✓ Health check complete!"
