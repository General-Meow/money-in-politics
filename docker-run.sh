#!/bin/bash
# Docker Compose Run Script for Web of Politics
# Usage: ./docker-run.sh [up|down|logs|health]

set -e

cd "$(dirname "$0")"

echo "=========================================="
echo "Web of Politics - Docker Management"
echo "=========================================="
echo ""

case "${1:-up}" in
  up)
    echo "Starting all services..."
    docker compose up -d --build
    echo ""
    echo "Services started. Waiting 30 seconds for initialization..."
    sleep 30
    
    echo "Running health checks..."
    ./docker-healthcheck.sh
    ;;
    
  down)
    echo "Stopping all services..."
    docker compose down
    ;;
    
  logs)
    LOGS=${2:-all}
    case "$LOGS" in
      api)
        docker compose logs -f api
        ;;
      neo4j)
        docker compose logs -f neo4j
        ;;
      frontend)
        docker compose logs -f frontend
        ;;
      all|*)
        docker compose logs -f
        ;;
    esac
    ;;
    
  health)
    echo "Running health checks..."
    ./docker-healthcheck.sh
    ;;
    
  stop)
    docker compose down
    ;;
    
  start)
    docker compose up -d
    ;;
    
  *)
    echo "Usage: $0 {up|down|logs|health|stop|start}"
    echo ""
    echo "Commands:"
    echo "  up      - Start all services with health checks"
    echo "  down    - Stop all services"
    echo "  logs    - View logs (optionally: api, neo4j, frontend)"
    echo "  health  - Run automated health checks"
    echo "  stop    - Alias for down"
    echo "  start   - Alias for up (without final health check)"
    ;;
esac

echo ""
echo "=========================================="
echo "Access URLs:"
echo "=========================================="
echo "Neo4j Browser:     http://localhost:7474"
echo "API Service:       http://localhost:8080"
echo "Frontend App:      http://localhost:3000"
echo "=========================================="
