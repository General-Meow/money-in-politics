# The Web of Politics - New Microservices Architecture

## 🎯 Overview

This document describes the new modular monolith architecture for **The Web of Politics**, designed as a single Spring Boot microservice with proper layered structure, ready for Docker Compose deployment.

---

## 📦 Project Structure

```
money-in-politics/
├── docker-compose.yml                 ← Orchestrates all services
├── docker-healthcheck.sh              ← Automated health checks
├── .env.example                       ← Environment configuration template
├── NEW-ARCHITECTURE-README.md         ← This file
├── ARCHITECTURE-MIGRATION-GUIDE.md    ← Detailed migration plan
│
├── web-of-politics-api/               ← Backend Spring Boot service
│   ├── build.gradle                   ← Gradle build configuration
│   ├── Dockerfile                     ← Container definition
│   ├── README.md                      ← API documentation
│   └── src/main/java/com/webofpolitics/
│       ├── WebOfPoliticsApplication.java  # Entry point
│       ├── api/                        # External API integrations
│       ├── controller/                 # REST endpoints
│       ├── dto/                        # Data Transfer Objects
│       ├── entities/                   # Domain models (Neo4j)
│       ├── facades/                    # Interface abstraction layer
│       ├── mappers/                    # DTO ↔ Entity conversion
│       ├── repositories/               # Neo4j data access
│       ├── schema/                     # Database setup
│       └── services/                   # Business logic
│
└── web-of-politics-frontend/          ← React/D3.js frontend service
    ├── package.json                   ← Node dependencies
    ├── nginx.conf                     ← Nginx configuration
    ├── Dockerfile                     ← Container definition
    ├── README.md                      ← Frontend documentation
    └── src/
        ├── App.js                     # Main app component
        ├── components/
            ├── HomePage.jsx          # Landing page
            ├── PoliticianSearch.jsx  # Search UI
            └── GraphView.jsx         # D3.js visualization
        └── public/index.html          # HTML template
```

---

## 🚀 Quick Start

### Option A: Docker Compose (Recommended)

```bash
# Navigate to project root
cd /home/paul/dev/projects/money-in-politics

# Create Neo4j database (optional - for fresh start)
docker run -d \
  --name neo4j-init \
  -e NEO4J_AUTH=neo4j/neo4j123 \
  neo4j:5.17-latest && docker compose rm neo4j-init

# Start all services
docker compose up -d

# Wait for services to initialize
sleep 30

# Run health checks
./docker-healthcheck.sh

# Access services
#   - Neo4j Browser: http://localhost:7474
#   - API Service:   http://localhost:8080
#   - Frontend App:  http://localhost:3000
```

### Option B: Local Development

```bash
# Terminal 1: Start Neo4j
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/neo4j123 \
  neo4j:5.17-latest

# Terminal 2: Start API
cd web-of-politics-api
gradle bootRun

# Terminal 3: Start Frontend (React dev server)
cd web-of-politics-frontend
npm start
```

---

## 🏗️ Architecture Layers

### Backend Service (`web-of-politics-api`)

```
┌─────────────────────────────────────────┐
│         REST Controllers                 │  HTTP layer (Endpoints)
│         - PoliticianController           │
│         - CompanyController              │
└─────────────────────────────────────────┘
                       ↓
┌─────────────────────────────────────────┐
│            Facades                       │  Domain abstraction
│         - PoliticianFacade               │
│         - CompanyFacade                  │
└─────────────────────────────────────────┘
                       ↓
┌─────────────────────────────────────────┐
│             Services                     │  Business logic
│         - PoliticianService              │
│         - CompanyService                 │
└─────────────────────────────────────────┘
                       ↓
┌─────────────────────────────────────────┐
│             Mappers                      │  Data conversion
│       - PoliticianMapper                 │
│       - CompanyMapper                    │
└─────────────────────────────────────────┘
                       ↓
┌─────────────────────────────────────────┐
│         Repositories                     │  Neo4j data access
│     - PoliticianRepository               │
│     - CompanyRepository                  │
└─────────────────────────────────────────┘
                       ↓
┌─────────────────────────────────────────┐
│          Entities (Domain Models)        │  Data objects
│       - Politician, Company, Relationship│
└─────────────────────────────────────────┘
```

### Frontend Service (`web-of-politics-frontend`)

```
┌─────────────────────────────────────────┐
│           React App                     │  UI framework
├─────────────────────────────────────────┤
│  / HomePage.jsx                         │  Landing page
│  / PoliticianSearch.jsx                 │  Search interface
│  / GraphView.jsx                        │  D3.js visualization
└─────────────────────────────────────────┘
          ↓
┌─────────────────────────────────────────┐
│      Nginx (Production Serving)         │  Static file server
└─────────────────────────────────────────┘
```

---

## 📋 API Endpoints

### Politician API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/politicians` | List all active politicians by jurisdiction |
| GET | `/api/politicians/search?query=<name>` | Search politicians by name |

### Company API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/companies` | List all companies |

---

## 🔧 Configuration

### Environment Variables (`.env`)

```bash
# Neo4j Database
NEO4J_URI=bolt://localhost:7687
NEO4J_USERNAME=neo4j
NEO4J_PASSWORD=change_me_from_neo4j123
NEO4J_DATABASE=neo4j

# Application Settings
API_BASE_URL=http://localhost:8080
FRONTEND_PORT=3000

# Production toggle
NODE_ENV=development
```

### Application Properties (`application.properties`)

```properties
# Server Configuration
server.port=8080

# Neo4j Database Configuration
spring.data.neo4j.uri=bolt://localhost:7687
spring.data.neo4j.auth.enabled=true
spring.data.neo4j.username=neo4j
spring.data.neo4j.password=neo4j123

# Actuator for health checks
management.endpoints.web.exposure.include=health,info,prometheus
management.endpoint.health.show-details=always
```

---

## 🐳 Docker Compose Services

| Service | Port | Description |
|---------|------|-------------|
| `neo4j` | 7474, 7687 | Neo4j graph database |
| `api` | 8080 | Spring Boot backend API |
| `frontend` | 3000 | React/D3.js frontend application |

### Docker Compose Command

```bash
# Start all services
docker compose up -d

# Stop all services
docker compose down

# View logs
docker compose logs -f api
docker compose logs -f neo4j
docker compose logs -f frontend

# Run health checks
./docker-healthcheck.sh
```

---

## 📊 Database Schema (Neo4j)

### Node Labels

- `:Politician` — UK politicians across all jurisdictions
- `:Company` — Companies and organizations
- `:Organization` — Political parties, think tanks, etc.

### Relationship Types

- `FAMILY` — Family connections (spouses, siblings)
- `BOARD_SEAT` — Directorships/board memberships  
- `SHARED_EDUCATION` — Schools/universities attended together
- `LOBBYING` — Lobbying relationships
- `CAMPAIGN_CONTRIBUTION` — Donation relationships

### Database Indexes (Auto-created on startup)

```cypher
// Politician indexes
CREATE INDEX FOR (p:Politician) ON (p.name)
CREATE INDEX FOR (p:Politician) ON (p.party)

// Company indexes
CREATE INDEX FOR (c:Company) ON (c.name)

// Relationship indexes
CREATE INDEX FOR ()-[r:LOBBYING]->() ON (r.type)
```

---

## 🔄 Migration Path from Old Structure

### Phase 1: Foundation (Week 1-2)
- [x] Create entity layer (Politician, Company, Relationship)
- [x] Create repository layer (Neo4j data access)
- [x] Configure Neo4j schema initialization

### Phase 2: Data Layer (Week 3-4)  
- [ ] Create DTOs for API contracts
- [ ] Implement mappers between DTOs and entities
- [ ] Define Swagger/OpenAPI documentation

### Phase 3: Business Logic (Week 5-6)
- [ ] Implement service layer interfaces
- [ ] Extract business logic from test files
- [ ] Add exception handling and validation

### Phase 4: API Layer (Week 7-8)
- [ ] Create REST controllers for endpoints
- [ ] Add request/response validation
- [ ] End-to-end integration tests

See `ARCHITECTURE-MIGRATION-GUIDE.md` for detailed migration steps.

---

## ✅ Success Criteria

Backend:
- [x] Service builds with Gradle
- [ ] Neo4j database initializes successfully  
- [ ] Politician search endpoint works
- [ ] Company list endpoint works
- [ ] Health checks pass for all services
- [ ] Migration of existing 129 Java files complete

Frontend:
- [x] React app builds without errors
- [ ] D3.js graph visualization renders
- [ ] Politician search UI functional
- [ ] Frontend communicates with API
- [ ] Docker container serves production build

---

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| `NEW-ARCHITECTURE-README.md` (this file) | High-level overview and quick start |
| `ARCHITECTURE-MIGRATION-GUIDE.md` | Detailed migration plan from old to new structure |
| `web-of-politics-api/README.md` | Backend API documentation |
| `web-of-politics-frontend/README.md` | Frontend application documentation |

---

## 🛠️ Tech Stack Summary

### Backend
- Java 17+ with Spring Boot 3.2.1
- Neo4j 5.x Graph Database Driver
- Gradle 8.5 for build automation
- Lombok for boilerplate reduction
- Jackson for JSON serialization

### Frontend
- React 18.2+ with functional components
- D3.js 7.x for graph visualization
- React Router v6 for navigation
- Axios for HTTP client
- Nginx for production serving

---

## 📞 Support

For migration questions, see `ARCHITECTURE-MIGRATION-GUIDE.md`.
For architecture decisions, review the layered structure documentation above.

---

**Last Updated:** 2026-06-13  
**Architecture Version:** 1.0 (Modular Monolith)  
**Target Architecture Version:** 2.0 (Microservices - Future Evolution)
