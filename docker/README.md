# EPIC-01 Integration Testing Environment

## Setup Instructions

### Prerequisites

```bash
# Install Docker Desktop (or Docker Engine)
# https://docs.docker.com/get-docker/

# Verify installation:
docker --version
docker-compose --version
```

### Start Services

```bash
cd /home/paul/dev/projects/money-in-politics/docker

# Build and start all services:
docker-compose up -d

# View logs:
docker-compose logs -f

# Stop services:
docker-compose down
```

### Database Configuration

**Neo4j (Graph Database):**
- Browser: http://localhost:7474
- Bolt: bolt://localhost:7687
- Default credentials: `neo4j/secret`
- First run creates default database with basic schema

**PostgreSQL:**
- Host: localhost
- Port: 5432
- Database: web_of_politics
- User: webofpolitics
- Password: secret

**Redis:**
- Host: localhost
- Port: 6379
- Max memory: 256MB with LRU eviction

### EPIC-01 Integration Testing

Once services are running, you can test EPIC-01 stories against live databases:

```bash
# Run STORY-001 tests (Neo4j Schema validation)
cd /home/paul/dev/projects/money-in-politics/_bmad-output/implementation-artifacts/stories/story-001
mvn clean test -Dtest="*IntegrationTest"

# Run all EPIC-01 integration tests:
# STORY-001 through STORY-006 with live Neo4j instance
```

### Environment Variables (Optional)

Edit `docker-compose.yml` to customize:

```yaml
services:
  neo4j:
    environment:
      - NEO4J_AUTH=neo4j/<your-password>
```

## Services Overview

| Service | Purpose | Port | Tech |
|---------|---------|------|------|
| Neo4j | Graph database (primary) | 7474/7687 | Java/Neo4j |
| PostgreSQL | Relational fallback | 5432 | Postgres |
| Redis | Caching layer | 6379 | Redis |

## Next Steps

1. Start services: `docker-compose up -d`
2. Wait for Neo4j to initialize (~2-3 minutes)
3. Run STORY-001 integration tests with live Neo4j
4. Validate schema creation and node/edge creation
5. Proceed to API integration testing

## Monitoring

```bash
# Check service health:
docker-compose ps

# View service logs:
docker-compose logs neo4j
docker-compose logs postgresql
docker-compose logs redis
```

## Cleanup

```bash
# Stop all services:
docker-compose down

# Remove volumes (persistent data):
docker-compose down -v
```

