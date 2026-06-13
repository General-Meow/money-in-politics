# Web of Politics API

## Overview

The Web of Politics API is a Spring Boot microservice with layered architecture that exposes REST endpoints for exploring UK political finance relationships.

### Technology Stack

- **Framework**: Spring Boot 3.2.1
- **Database**: Neo4j 5.x (Graph Database)
- **Build Tool**: Gradle 8.5
- **Language**: Java 17+
- **Testing**: JUnit 5 + Spring Boot Test
- **API Contract**: JSON with Jackson

---

## Architecture Layers

```
┌─────────────────────────────────────────┐
│           Controllers                    │  ← REST API endpoints
│           (web layer)                    │
└─────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────┐
│            Facades                       │  ← Domain abstraction
└─────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────┐
│            Services                      │  ← Business logic
└─────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────┐
│           Mappers                        │  ← DTO ↔ Entity conversion
└─────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────┐
│        Repositories                      │  ← Neo4j data access
└─────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────┐
│         Entities (Domain Models)         │  ← Data objects
└─────────────────────────────────────────┘
```

---

## Package Structure

```
src/main/java/com/webofpolitics/
├── WebOfPoliticsApplication.java          # Spring Boot entry point
├── api/                                   # API integrations (Parliament API, etc.)
├── controller/                            # REST endpoints
│   ├── PoliticianController.java
│   └── CompanyController.java
├── dto/                                   # Data Transfer Objects
│   ├── PoliticianDto.java
│   └── CompanyDto.java
├── entities/                              # Domain models (Neo4j)
│   ├── Politician.java
│   ├── Company.java
│   └── Relationship.java
├── facades/                               # Domain abstraction layer
│   ├── PoliticianFacade.java
│   └── CompanyFacade.java
├── mappers/                               # DTO ↔ Entity mapping
│   ├── PoliticianMapper.java
│   └── CompanyMapper.java
├── repositories/                          # Neo4j data access
│   ├── PoliticianRepository.java
│   ├── CompanyRepository.java
│   └── RelationshipRepository.java
├── schema/                                # Database setup
│   └── Neo4jSchemaInitializer.java
└── services/                              # Business logic
    ├── PoliticianService.java
    └── CompanyService.java
```

---

## Quick Start

### Local Development

1. **Start Neo4j** (or use Docker Compose):
   ```bash
   docker run -d \
     --name neo4j \
     -p 7474:7474 -p 7687:7687 \
     -e NEO4J_AUTH=neo4j/neo4j123 \
     neo4j:5.17-latest
   ```

2. **Configure application.properties**:
   ```properties
   spring.data.neo4j.uri=bolt://localhost:7687
   spring.data.neo4j.auth.enabled=true
   spring.data.neo4j.username=neo4j
   spring.data.neo4j.password=neo4j123
   ```

3. **Build and Run**:
   ```bash
   gradle bootRun
   ```

4. **Access API**: http://localhost:8080

### Docker Compose

```bash
cd /home/paul/dev/projects/money-in-politics
docker compose up -d
```

---

## API Endpoints

### Politicians

- `GET /api/politicians` — List all active politicians by jurisdiction
- `GET /api/politicians/search?query=<name>` — Search politicians by name

### Companies

- `GET /api/companies` — List all companies
- (Additional endpoints to be implemented)

---

## Database Schema

The Neo4j graph database contains:

**Node Types:**
- `Politician` — UK politicians across all jurisdictions
- `Company` — Companies and organizations
- `Relationship` — Connections between entities

**Relationship Types:**
- `FAMILY` — Family ties (spouses, siblings)
- `BOARD_SEAT` — Directorships/board memberships
- `SHARED_EDUCATION` — Schools/universities attended together
- `LOBBYING` — Lobbying relationships
- `CAMPAIGN_CONTRIBUTION` — Donation relationships

---

## Development Notes

### Layered Architecture Principles

This project follows the layered architecture pattern:

1. **Controllers**: Handle HTTP requests/responses
2. **Facades**: Provide domain abstraction over services
3. **Services**: Contain business logic
4. **Mappers**: Convert between DTOs and entities
5. **Repositories**: Abstract data access to Neo4j
6. **Entities**: Domain models stored in the database

### Why This Structure?

- ✅ Clear separation of concerns
- ✅ Easy to test each layer independently
- ✅ Simple to add new features
- ✅ Scalable for microservices evolution

---

## Migration Status

This API service has been designed as a clean, layered architecture ready for migration from the previous scattered structure. See `ARCHITECTURE-MIGRATION-GUIDE.md` for detailed migration steps.

---

## License

See project root LICENSE file.
