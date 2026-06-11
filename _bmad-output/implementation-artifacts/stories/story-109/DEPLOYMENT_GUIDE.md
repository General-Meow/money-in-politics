# STORY-109: CI/CD Pipeline & Complete Deployment Guide

**Complete Production Deployment for The Web of Politics (All 3 Epics)**

---

## Table of Contents
1. [Quick Start](#quick-start)
2. [Production Architecture](#production-architecture)
3. [Deployment Steps](#deployment-steps)
4. [Monitoring & Maintenance](#monitoring--maintenance)

---

## Quick Start

### Deploy to Production:
```bash
# Pull Neo4j database layer (EPIC-01 data)
docker-compose up -d neo4j

# Build and deploy application container (all 3 epics)
cd story-108
mvn clean package
docker build -t webofpolitics-app:_latest .

# Run complete deployment with all services
docker-compose up -d app node

# Verify deployment
curl localhost:8080/api
```

---

## Production Architecture

### Component Layers:

**EPIC-01 (Graph Engine & Data Layer):**
```yaml
Neo4j Database          → Stores politicians, companies, donations
Schema loaded via       → STORY-002/STORY-003/STORY-005 Python scripts
Sample data imported    → EPIC-01 completion scripts
OAuth/OIDC configured   → Authentication ready (optional)
```

**EPIC-02 (Search & Discovery Layer):**
```yaml
/api/search?q=...       → Fuzzy politician search (STORY-101)
/api/profile/{id}       → Complete profiles with donors (STORY-102)  
/api/graph/{id}         → Interactive D3.js explorer (STORY-103)
/mobile-responsive.css   → Mobile-first responsive layout (STORY-104)
/api/search/advanced    → Advanced filtering enhancement (STORY-105)
```

**EPIC-03 (Frontend Integration Layer):**
```yaml
SPA Controller          → Entry point for complete application (STORY-106)
Integration Tests       → End-to-end validation suite (STORY-107)
Docker Containers       → Multi-container production setup (STORY-108)
CI/CD Pipeline          → GitHub Actions automation (STORY-109)
```

---

## Deployment Steps

### Step 1: Deploy Neo4j Database Layer (EPIC-01)
```bash
docker-compose up -d neo4j

# Wait for database to initialize
sleep 30

# Verify Neo4j is operational
curl http://localhost:7474/login?neo4j=neo4j%2Fpassword123
```

### Step 2: Load EPIC-01 Data into Neo4j
```bash
cd story-005/neo4j-data-import-scripts
python data_import.py  # Loads historical donations, companies
# Verify data loaded successfully
```

### Step 3: Deploy Complete Application (All 3 Epics)
```bash
docker-compose up -d app node

# Wait for application to start
sleep 10

# Access API endpoints
curl localhost:8080/api           # Health check
curl 'localhost:8080/api/search?q=starmer'  # Search test
curl 'localhost:8080/api/profile/UK-CHS-LAB'  # Profile test
```

### Step 4: Verify All Components
```bash
# Test Neo4j connection
docker-compose logs -f neo4j | grep "Started"

# Test application API
curl localhost:8080/              # Home page
curl localhost:8080/api           # Health check (all APIs accessible)

# Test frontend
open http://localhost:8081        # Graph explorer frontend
```

---

## Monitoring & Maintenance

### View Application Logs:
```bash
docker-compose logs -f app        # Java application logs
docker-compose logs -f neo4j      # Database logs
docker-compose logs -f node       # Frontend logs
```

### Health Check Endpoints:
```bash
curl localhost:8080/api           # System health
```

### Reload Neo4j Browser (if needed):
```bash
curl http://localhost:7474/login?neo4j=neo4j%2Fpassword123
```

---

## Complete Endpoint List

| Endpoint | Purpose | EPIC | Status |
|----------|---------|------|--------|
| `/` | Home page entry point | EPIC-03 | ✅ |
| `/api` | System health check | EPIC-03 | ✅ |
| `/api/search?q={query}` | Politician search with fuzzy matching | EPIC-02 | ✅ |
| `/api/profile/{id}` | Complete politician profiles | EPIC-02 | ✅ |
| `/api/graph/{id}` | Interactive D3.js graph explorer | EPIC-02 | ✅ |
| `/api/search/advanced` | Advanced filtering | EPIC-02 | ✅ |
| `/7474/login` | Neo4j Browser (database UI) | EPIC-01 | ✅ |

---

## Production Deployment Checklist

- [x] Neo4j database container deployed
- [x] Application container with all APIs operational
- [x] Frontend graph explorer running
- [x] All integration tests passing
- [x] Health check endpoint functional
- [ ] OAuth/OIDC authentication configured (optional)
- [ ] Monitoring/logging setup (Prometheus/Grafana)
- [ ] SSL/TLS certificates installed

---

## Troubleshooting

### Neo4j Not Starting:
```bash
docker-compose logs neo4j
# Check for authentication or port binding errors
```

### API Returns Empty Response:
```bash
docker-compose logs app
# Verify EPIC-01 data loaded successfully in Neo4j
```

### Frontend Not Rendering Graph:
```bash
docker-compose exec node npm start
# Verify Node.js container running correctly
```

---

*Complete deployment guide for The Web of Politics - All 3 Epics integrated and production-ready*

