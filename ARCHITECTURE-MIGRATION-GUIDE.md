# Architecture Migration Guide: From Scattered Stories to Modular Monolith

## Current State Analysis

**Existing Structure:** 129 Java files scattered across `_bmad-output/implementation-artifacts/stories/{story-XXX}/src/main/java/`

**Target Architecture:** Single Spring Boot microservice with layered architecture in `web-of-politics-api/`

---

## Migration Strategy: Phased Approach

### Phase 1: Core Entities & Models (Priority: High)
Move first, as they define the data model and are referenced everywhere.

#### Step 1.1: Create Entity Layer
```
Destination: web-of-politics-api/src/main/java/com/webofpolitics/entities/

Files to migrate:
- PoliticianNodeCreator.java → Politician.java
- CompanyNodeCreator.java → Company.java  
- RelationshipBuilder.java → Relationship.java
- GraphIndexCreator.java → IndexConfiguration.java
```

**Migration Checklist:**
1. Remove Neo4j-specific node builder annotations
2. Add Lombok `@Data`, `@Builder` annotations
3. Convert node builders to JPO/Neo4j entity patterns
4. Update relationship mapping strategy

#### Step 1.2: Create Repository Layer  
```
Destination: web-of-politics-api/src/main/java/com/webofpolitics/repositories/

Files to migrate:
- PoliticianNodeCreator.java → PoliticianRepository.java
- CompanyNodeCreator.java → CompanyRepository.java
- RelationshipBuilder.java → RelationshipRepository.java
```

**Migration Checklist:**
1. Convert node creation logic to repository methods
2. Add Neo4j relationship type mappings
3. Implement proper CRUD operations

### Phase 2: DTOs & Mappers (Priority: Medium)

#### Step 2.1: Create DTO Layer
```
Destination: web-of-politics-api/src/main/java/com/webofpolitics/dto/

Files to create/migrate:
- PoliticianDto.java (new - API contract)
- CompanyDto.java (new - API contract)
- RelationshipDto.java (new - API contract)
- SearchQueryDto.java (new - for search endpoints)
```

**Migration Checklist:**
1. Define JSON serialization contracts
2. Add validation annotations (@NotNull, @Size, etc.)
3. Create immutable DTOs where appropriate

#### Step 2.2: Create Mapper Layer
```
Destination: web-of-politics-api/src/main/java/com/webofpolitics/mappers/

Files to create/migrate:
- PoliticianMapper.java (new - Entity ↔ DTO conversion)
- CompanyMapper.java (new - Entity ↔ DTO conversion)
- RelationshipMapper.java (new - Entity ↔ DTO conversion)
```

**Migration Checklist:**
1. Convert node builder patterns to DTO mapping methods
2. Handle bidirectional conversions
3. Add type conversion logic for enum mappings

### Phase 3: Services & Business Logic (Priority: Medium)

#### Step 3.1: Create Service Layer
```
Destination: web-of-politics-api/src/main/java/com/webofpolitics/services/

Files to migrate from existing stories:
- Story002Application.java → PoliticianService.java
- BiographyAndCommitteeTest.java → SearchService.java (business logic)
- ParliamentApiClientTest.java → ParliamentClient.java
```

**Migration Checklist:**
1. Extract business logic from test files
2. Create service interfaces and implementations
3. Inject repositories via Spring dependency injection

#### Step 3.2: Create Facade Layer (Optional - Future Growth)
```
Destination: web-of-politics-api/src/main/java/com/webofpolitics/facades/

Files to create:
- PoliticianFacade.java
- CompanyFacade.java
- SearchFacade.java (combines multiple services)
```

**Migration Checklist:**
1. Define high-level domain operations
2. Compose multiple service calls
3. Add response enrichment logic

### Phase 4: Controllers & API Endpoints (Priority: Low - First Use Cases)

#### Step 4.1: Create Controller Layer
```
Destination: web-of-politics-api/src/main/java/com/webofpolitics/controller/

Files to create/migrate based on priority:
- PoliticianController.java (search, list endpoints)
- CompanyController.java (search, list endpoints)
- RelationshipController.java (graph traversal endpoints)
```

**Migration Checklist:**
1. Define REST endpoint contracts
2. Add request validation
3. Implement error handling
4. Add proper response status codes

---

## File Mapping Guide

### Existing Story Files → New Architecture

| Existing Location | Maps To New Location | Transformation |
|-------------------|----------------------|----------------|
| `story-001/Story001Application.java` | Removed | Spring Boot app becomes entry point only |
| `story-001/*.java` in schema/ | `entities/*.java`, `repositories/*.java` | Split into layers |
| `story-002/BiographyAndCommitteeTest.java` | `services/BiographyService.java` | Extract logic to service |
| `story-003/*.java` in api/integration/ | `controller/`, `repositories/*.java` | Split concerns |
| Test files starting with `/test/java/` | Remove test-specific code, extract business logic | Pure implementation only |

---

## Migration Execution Plan

### Week 1: Foundation (Entities & Repositories)
- [ ] Create entity classes (Politician, Company, Relationship)
- [ ] Create repository interfaces
- [ ] Configure Neo4j schema initialization
- [ ] Unit tests for entities/repositories

### Week 2: Data Layer (DTOs & Mappers)
- [ ] Create DTO classes with validation
- [ ] Implement mapper utilities
- [ ] Integration tests for mapping logic
- [ ] API contract documentation (Swagger/OpenAPI)

### Week 3: Business Layer (Services)
- [ ] Implement service interfaces and implementations
- [ ] Add business logic from test files
- [ ] Unit tests for services
- [ ] Service layer documentation

### Week 4: API Layer & Integration
- [ ] Implement REST controllers
- [ ] Add request/response validation
- [ ] Error handling and exception mapping
- [ ] End-to-end integration tests
- [ ] Update `docker-compose.yml` with migration scripts

---

## Benefits of New Architecture

### ✅ Clear Separation of Concerns
```
controllers/      ← HTTP layer, request handling
facades/          ← Domain abstraction
services/         ← Business logic
mappers/          ← Data conversion
repositories/     ← Data access
entities/         ← Domain models
dtos/             ← API contracts
```

### ✅ Docker Compose Deployment Ready
- `web-of-politics-api`: Single microservice artifact
- `web-of-politics-frontend`: Frontend container
- `neo4j`: Database service
- All orchestrated in one docker-compose.yml

### ✅ Maintainable and Scalable
- Standard layered architecture patterns
- Easy to add new features without affecting existing code
- Clear boundaries between layers

### ✅ Production-Ready
- Health checks configured
- Proper dependency injection
- Clean API contract with DTOs

---

## Quick Start Migration Commands

### Step 1: Copy Entities First
```bash
# Create entity from existing node creator
cp -r story-001/src/main/java/com/webofpolitics/schema \
      web-of-politics-api/src/main/java/com/webofpolitics/entities/
```

### Step 2: Copy Repositories  
```bash
# Extract repository logic
mvn copy-resources -Dsource=story-002/src/main/java/com/webofpolitics/api \
                        -dest=web-of-politics-api/src/main/java/com/webofpolitics/repositories/
```

### Step 3: Build and Test New Structure
```bash
cd web-of-politics-api
gradle bootJar
docker compose up -d neo4j
# Then import data using migration scripts
```

---

## Notes for Migration

1. **Keep Old Code Running Until Migration Complete**: Don't delete old story directories until new structure is verified working.

2. **Database Migration Path**: Keep Neo4j schema creation separate from business logic. Use dedicated initialization jobs.

3. **Test First, Refactor Later**: Ensure all tests pass before moving a file to the new location.

4. **Documentation Updates**: Update API documentation (Swagger/OpenAPI) as endpoints are implemented.

---

## Success Criteria

✅ Backend service builds and runs with `docker compose up`  
✅ Neo4j database initialized and accepts queries  
✅ Politician search endpoint returns data  
✅ Company search endpoint returns data  
✅ Docker Compose health checks pass for all services  
✅ Frontend can communicate with backend API  

---

**Ready to start migration? Let me know which phase you'd like to begin with!**
