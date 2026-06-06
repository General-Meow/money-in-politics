# EPIC-01 Integration Testing Environment - Fedora Installation Guide

## ⚠️ Neo4j Password Requirements

Neo4j requires passwords that are:
- **At least 8 characters**
- **Contain uppercase letters** (A-Z)
- **Contain lowercase letters** (a-z)
- **Contain numbers** (0-9)
- **Contain special characters** (!, @, #, $, %, ^, &, *)

**Example valid passwords:** `Web0fP0l1t1c$Secur3`, `Ne0wY0rk!Secure@2024`

---

## Step 1: Install Docker Engine on Fedora

```bash
# Set up Docker repository
sudo dnf config-manager --add-repo \
    https://download.docker.com/linux/fedora/docker-ce.repo

# Install Docker Engine and Compose plugin
sudo dnf install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Start Docker service
sudo systemctl start docker
sudo systemctl enable docker

# Verify installation
docker --version
docker compose version
```

## Step 2: Add User to Docker Group (Optional)

```bash
sudo usermod -aG docker paul

# Log out and back in for group membership to take effect
```

## Step 3: Build and Start EPIC-01 Services

```bash
cd /home/paul/dev/projects/money-in-politics/docker

# Build and start all services with strong passwords
sudo docker-compose up -d --build
```

## Step 4: Verify Services are Running

```bash
# Check if containers started successfully
sudo docker-compose ps

# Should show: neo4j, postgresql, redis all healthy (Up)

# View Neo4j logs (check for startup success)
sudo docker-compose logs neo4j | tail -20
```

## Step 5: Access Neo4j Browser

- **URL:** http://localhost:7474
- **Username:** `neo4j`
- **Password:** `Web0fP0l1t1c$Secur3`

## Step 6: Run Integration Tests (STORY-001)

```bash
cd /home/paul/dev/projects/money-in-politics/_bmad-output/implementation-artifacts/stories/story-001
sudo mvn clean test -Dtest="*IntegrationTest"
```

---

## Troubleshooting

### If Neo4j fails to start:

```bash
# Check logs for specific error
sudo docker-compose logs neo4j

# Common issues and solutions:

# 1. Password too weak:
#    Use password with 8+ chars, mixed case, numbers, special chars

# 2. License not accepted:
#    Ensure NEO4J_ACCEPT_LICENSE_AGREEMENT=yes is set

# 3. Container already exists with different credentials:
sudo docker-compose down
sudo rm -rf neo4j_volume_*
sudo docker-compose up -d --build
```

### Quick Start Commands:

```bash
# Clean start (remove existing containers)
cd /home/paul/dev/projects/money-in-politics/docker
sudo docker-compose down -v

# Start fresh
sudo docker-compose up -d --build

# Check status
sudo docker-compose ps
```

---

## Service Summary

| Service | Port | Purpose | Credentials |
|---------|------|---------|-------------|
| Neo4j | 7474/7687 | Graph database (primary storage) | `neo4j / Web0fP0l1t1c$Secur3` |
| PostgreSQL | 5432 | Relational fallback | `webofpolitics / W3b0fP0l1t1c$Secur3` |
| Redis | 6379 | Caching layer | `redis / redis` (no password needed) |

---

## Next Steps After Installation

1. ✅ Start services: `docker-compose up -d --build`
2. ✅ Verify Neo4j health: Check logs and browser access
3. ✅ Run STORY-001 integration tests with live Neo4j
4. ✅ Validate schema creation and node/edge creation
5. ✅ Proceed to API integration testing

