# EPIC-01: Graph Engine and Data Ingestion Pipeline

**Status:** Not Started  
**Priority:** P0 (Critical Path)  
**Effort Estimate:** 8-12 weeks  
**Team Size:** Solo (initially), then 1-2 developers

## Goal

Build the core graph infrastructure that powers all visualization and analysis features. This includes:
- Neo4j graph database integration and schema design
- Java/Spring Boot data ingestion services for UK government APIs
- Historical data backfill from 2005 onwards
- Automated scraper pipelines with fallback mechanisms

## Scope

### In Scope
- Neo4j container setup and relationship schema (politicians, companies, donations, relationships)
- Spring Boot REST API endpoints for graph queries
- Data ingestion services for:
  - Parliament.uk API (MP profiles, voting records)
  - Electoral Commission API (donation data)
  - Companies House API (company/CEO data)
  - Social media APIs (Twitter/X, LinkedIn) where available
- Fallback scraper implementations for un-API'd sources
- Historical dataset ingestion and validation
- Database indexing strategy for fast relationship queries

### Out of Scope
- Frontend integration (handled by subsequent epics)
- User authentication/authorization (handled by EPIC-04)
- Community submission system (EPIC-04)

## Acceptance Criteria

1. **Neo4j Schema Complete**
   - Politician nodes with: name, role, constituency, party, tenure_start, tenure_end
   - Company nodes with: name, industry, headquarters, ceo_id, registered_office
   - Donation edges linking donors to recipients with: amount, date, purpose
   - Relationship edges with type (family/board/media/social/education), start_date, end_date
   - All entity types indexable for fast graph traversal

2. **API Integration Functional**
   - Spring Boot REST API accepts GET `/api/entities/:id` queries
   - Graph endpoints support: `POST /api/graph/neighbors`, `GET /api/graph/path`
   - Response includes connection type and metadata

3. **Historical Data Complete**
   - All records from 2005 onwards ingested for Westminster MPs
   - Electoral Commission donation data complete through latest filing date
   - Companies House data includes all incorporated entities with politician connections

4. **Fallback Mechanisms Working**
   - Scrapy/BeautifulSoup scrapers implemented for any API-unavailable sources
   - Rate limiting and robots.txt respect enforced
   - Error handling logs failures to monitoring endpoint

5. **Data Quality Validated**
   - Automated validation checks for duplicate entities
   - Data provenance logged for every entry (source URL, extraction timestamp)
   - Empty/null fields flagged for review

---

## User Stories

See `stories-epic-01/` directory for detailed user stories.

### Summary of Stories
- **STORY-001:** As a developer, I want Neo4j graph schema so that relationship queries perform efficiently
- **STORY-002:** As a backend engineer, I want Parliament.uk API integration so that MP profiles are auto-populated
- **STORY-003:** As a data engineer, I want Electoral Commission API so that donation records are captured
- **STORY-004:** As a scraper, I want Companies House integration so that company connections are mapped
- **STORY-005:** As a developer, I want fallback scrapers so that API failures don't break ingestion
- **STORY-006:** As a data steward, I want historical backfill so that 2005+ data is available at launch

---

## Technical Dependencies

- Neo4j Community Edition (or Neo4j Docker container)
- Spring Boot 3.x + Java 17
- Maven/Gradle build tooling
- Docker Compose for local development
- Scrapy or BeautifulSoup for web scraping
- Redis cache (optional, post-MVP)

---

## Risks

- **Risk:** Government API schemas change without notice  
  - **Mitigation:** Modular scraper design; fallback to manual HTML parsing if APIs break
  
- **Risk:** Historical data incomplete or missing  
  - **Mitigation:** Accept partial history; focus on completeness from earliest available records
  
- **Risk:** Neo4j license cost at scale  
  - **Mitigation:** Start with self-hosted Community Edition; plan migration path

---

## Success Metrics

- Graph query response time < 500ms for neighbor lookups
- Historical data coverage > 90% of expected entities (2005-present)
- API uptime during ingestion cycles > 99%
