# Story 007: Microservices Refactor

## Epic Overview

**Epic 03: System Modernization** — Refactor from scattered story artifacts into a proper microservices architecture with Docker Compose deployment.

---

## User Stories

### STORY-007.1: Create New Layered Architecture
**Acceptance Criteria:**
- [x] Backend service has layered architecture (controllers → services → facades → mappers → repositories → entities)
- [x] Each package follows Spring Boot conventions
- [x] Dockerfile created for backend microservice
- [x] Frontend Dockerfile created for React application
- [x] docker-compose.yml orchestrates all three services

### STORY-007.2: Migrate Existing Code to New Structure
**Acceptance Criteria:**
- [ ] Move 129 Java files from `_bmad-output/implementation-artifacts/stories/` to new structure
- [ ] Convert Neo4j node builders to proper entity classes
- [ ] Create repository interfaces with proper data access methods
- [ ] Define DTOs for API contracts
- [ ] Implement mappers between entities and DTOs

### STORY-007.3: Configure Docker Deployment
**Acceptance Criteria:**
- [x] Neo4j service configured in docker-compose.yml
- [x] Backend API service containerized with proper health checks
- [x] Frontend React app containerized with Nginx
- [x] All services use shared network for inter-service communication
- [x] Health check scripts created and tested

### STORY-007.4: Create Documentation
**Acceptance Criteria:**
- [x] Architecture README documenting layered structure
- [x] Migration guide explaining refactoring strategy
- [x] API documentation (Swagger/OpenAPI to be added)
- [x] Frontend documentation with component descriptions

---

## Technical Debt & Refactoring Notes

### Problem: Scattered Implementation Artifacts
The existing codebase has 129 Java files scattered across story directories:
```
_bmad-output/implementation-artifacts/stories/{story-XXX}/src/main/java/com/webofpolitics/...
```

This structure was created during BMad incremental development but doesn't scale well for production deployment.

### Solution: Modular Monolith Architecture
Refactor into a single Spring Boot service with clear separation of concerns:

```
controllers/           # HTTP layer (REST endpoints)
services/              # Business logic layer  
facades/               # Domain abstraction (future growth)
mappers/               # DTO ↔ Entity conversion
repositories/          # Neo4j data access
entities/              # Domain models
dto/                   # API contracts
```

### Migration Strategy: Layer-by-Layer
1. **Entities first** — Core domain models referenced everywhere
2. **Repositories second** — Data access abstraction
3. **DTOs & Mappers third** — API contracts and data conversion
4. **Services fourth** — Business logic extraction
5. **Controllers last** — HTTP endpoints (can be added incrementally)

---

## Docker Architecture

### Services Overview

```
┌─────────────────────────────────────────┐
│         web-of-politics-frontend        │  Port 3000
│   ┌───────────────────────────────────┐ │
│   │      React/D3.js UI               │ │
│   │      - Search pages              │ │
│   │      - Graph visualization       │ │
│   └───────────────────────────────────┘ │
│         ↓ HTTP API                       │
└─────────────────────────────────────────┘
                ↓
┌─────────────────────────────────────────┐
│        web-of-politics-api              │  Port 8080
│   ┌───────────────────────────────────┐ │
│   │      Spring Boot Application      │ │
│   │      - REST Controllers           │ │
│   │      - Services + Repositories    │ │
│   │      - Neo4j Integration          │ │
│   └───────────────────────────────────┘ │
│         ↓ Neo4j Bolt Protocol            │
└─────────────────────────────────────────┘
```

### Deployment Commands

```bash
# Start all services
docker compose up -d

# View logs
docker compose logs -f api     # API service logs
docker compose logs -f neo4j   # Database logs  
docker compose logs -f frontend # UI server logs

# Health checks
./docker-healthcheck.sh

# Stop services
docker compose down
```

---

## Success Metrics

### Phase 1: Infrastructure Complete ✅
- [x] New directory structure created
- [x] Dockerfiles created for all services
- [x] docker-compose.yml with health checks
- [x] Health check scripts tested

### Phase 2: Core Entities Migrated ⏳
- [ ] Politician entity moved from story-001 schema
- [ ] Company entity moved from story-001 schema
- [ ] Relationship entity created
- [ ] Neo4jRepository interfaces implemented

### Phase 3: API Layer Added ⏳
- [ ] DTOs defined for API contracts
- [ ] Mappers implemented for data conversion
- [ ] REST controllers created for endpoints

---

## References

- `../ARCHITECTURE-MIGRATION-GUIDE.md` — Detailed migration steps
- `/home/paul/dev/projects/money-in-politics/NEW-ARCHITECTURE-README.md` — Overview
- `../web-of-politics-api/README.md` — API documentation
- `../web-of-politics-frontend/README.md` — Frontend documentation

---

## Next Steps

1. **Test new architecture** — Ensure old code still works while migrating
2. **Migrate story-001 entities** — Most critical (Politician, Company)
3. **Migrate story-002 services** — Business logic extraction
4. **Migrate story-003 controllers** — API endpoints

See migration guide for detailed file-by-file refactoring instructions.
