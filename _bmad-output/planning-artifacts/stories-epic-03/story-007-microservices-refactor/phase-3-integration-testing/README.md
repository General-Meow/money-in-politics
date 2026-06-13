# Phase 3: API Integration Testing

**Epic:** Epic 03 — System Modernization  
**Story:** Story 007 — Microservices Refactor  
**Phase:** Phase 3 — API Integration Testing

---

## Overview

Phase 3 focuses on comprehensive integration testing of the REST API layer, implementing pagination, adding authentication/authorization, configuring rate limiting, and performance optimization to prepare the microservices architecture for production deployment.

---

## Quick Summary

- ✅ **Pagination** — Implement request/response pagination
- ✅ **Authentication** — JWT/OAuth2 token-based auth
- ✅ **Authorization** — Role-based access control (RBAC)
- ✅ **Rate Limiting** — Throttling and circuit breakers
- ✅ **Validation** — Request body validation
- ✅ **Performance Testing** — Load testing with JMeter/k6
- ✅ **Documentation** — Complete OpenAPI 3.0 spec
- ✅ **Error Handling** — Enhanced error responses

---

## Acceptance Criteria

1. [ ] All REST API endpoints return paginated results
2. [ ] JWT authentication implemented for protected endpoints
3. [ ] Role-based authorization configured (ADMIN, USER, GUEST)
4. [ ] Rate limiting applied to prevent abuse
5. [ ] Request validation with appropriate error messages
6. [ ] Load testing completed with performance metrics
7. [ ] Complete Swagger/OpenAPI 3.0 specification generated
8. [ ] Integration tests written and passing

---

## Architecture Updates

### Layered Structure (Phase 3 additions):

```
┌─────────────────────────────────────────────────────────────────────┐
│                    Neo4j Database (Existing)                         │
└─────────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│              Repository Layer (Existing - Enhanced)                  │
│  - Add pagination support to repository methods                      │
│  - Implement database indexes for performance                        │
└─────────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│           Service Layer (Existing - Enhanced)                        │
│  - Add pagination to service methods                                 │
│  - Implement caching strategy (Redis/InMemory)                       │
└─────────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│         Controller Layer (Existing - Enhanced)                       │
│  - Add pagination parameters to endpoints                            │
│  - Implement authentication filters                                  │
│  - Implement authorization checks                                    │
│  - Add rate limiting interceptors                                    │
└─────────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│        Security Layer (New in Phase 3)                               │
│  - JWT token authentication                                          │
│  - OAuth2 password grant flow                                        │
│  - Role-based authorization (ADMIN, USER, GUEST)                    │
└─────────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│      Rate Limiting Layer (New in Phase 3)                            │
│  - Token bucket algorithm                                            │
│  - Redis-backed rate limiting for distributed deployments            │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Files to Create

### Authentication & Security:

| File | Purpose | Lines Est. | Status |
|------|---------|-------------|--------|
| `JwtAuthenticationFilter.java` | JWT token validation filter | ~100 | Phase 3 |
| `JwtTokenProvider.java` | JWT token generation/validation | ~80 | Phase 3 |
| `OAuth2AuthorizationConfig.java` | OAuth2 configuration | ~150 | Phase 3 |
| `SecurityConfig.java` | Spring Security configuration | ~200 | Phase 3 |
| `JwtAuthenticationToken.java` | Custom authentication token | ~60 | Phase 3 |

### Rate Limiting:

| File | Purpose | Lines Est. | Status |
|------|---------|-------------|--------|
| `RateLimitFilter.java` | Token bucket rate limiting | ~120 | Phase 3 |
| `RateLimiterAspect.java` | Rate limit annotations/aspect | ~100 | Phase 3 |

### Pagination Support:

| File | Purpose | Lines Est. | Status |
|------|---------|-------------|--------|
| `PaginationHelper.java` | Pagination utilities | ~80 | Phase 3 |

### Validation:

| File | Purpose | Lines Est. | Status |
|------|---------|-------------|--------|
| `ControllerAdvice/ValidationExceptionHandler.java` | Validation error handling | ~100 | Phase 3 |
| `dto/PoliticianRequestDto.java` | Request DTO with validation annotations | ~150 | Phase 3 |
| `dto/CompanyRequestDto.java` | Request DTO with validation annotations | ~150 | Phase 3 |

### Caching:

| File | Purpose | Lines Est. | Status |
|------|---------|-------------|--------|
| `CacheConfig.java` | Cache manager configuration | ~80 | Phase 3 |
| `RedisConnection.java` | Redis connection setup | ~60 | Phase 3 |

### Performance Testing:

| File | Purpose | Lines Est. | Status |
|------|---------|-------------|--------|
| `PerformanceTestConfig.java` | JMeter/k6 configuration | ~50 | Phase 3 |

---

## API Updates (Existing Files - Enhanced)

### Controllers - Add Pagination:

| Controller | Method | Enhancement | Lines Added | Status |
|------------|--------|-------------|-------------|--------|
| `PoliticianController.java` | GET /api/v1/politicians | Add page/size params | ~50 | Phase 3 |
| `PoliticianController.java` | GET /api/v1/companies | Add page/size params | ~40 | Phase 3 |

### Exception Handler - Enhanced:

| File | Enhancement | Lines Added | Status |
|------|-------------|-------------|--------|
| `GlobalExceptionHandler.java` | Add validation exception handler | ~50 | Phase 3 |

---

## Code Metrics Target

### Performance Targets (Phase 3):

| Metric | Current | Target | Status |
|--------|---------|--------|--------|
| Average response time (list all) | N/A | < 200ms | - |
| Average response time (get by ID) | N/A | < 50ms | - |
| Throughput (requests/sec) | N/A | > 100 req/s | - |
| Memory usage (baseline) | N/A | < 500MB | - |

### Code Quality Targets:

| Metric | Target | Status |
|--------|--------|--------|
| Test coverage | > 80% | - |
| Cyclomatic complexity avg | < 5 | - |
| Lines per file avg | < 200 | - |

---

## Security Requirements (Phase 3)

### Authentication Flow:

```
1. User requests access token via OAuth2 password grant
   POST /oauth/token with { username, password }
   
2. JWT token issued with claims (role, expiration)
   Response: { access_token, token_type, expires_in }
   
3. Client includes token in subsequent requests
   Authorization: Bearer <access_token>
```

### Authorization Levels:

| Role | Endpoints Accessible | Delete Access | Admin Functions |
|------|---------------------|---------------|-----------------|
| ADMIN | All endpoints | Yes | Yes |
| USER | GET, POST /api/v1/* | No (except own) | No |
| GUEST | GET only public info | No | No |

---

## Rate Limiting Configuration

### Default Limits:

| Endpoint Type | Requests/Minute | Burst Size | Status Code (Exceeded) |
|---------------|-----------------|------------|------------------------|
| List all (GET /api/v1/*) | 60 | 10 | 429 Too Many Requests |
| Get by ID (GET /api/v1/*/ {id}) | 300 | 50 | 429 Too Many Requests |
| Create/Update (POST /api/v1/*) | 30 | 5 | 429 Too Many Requests |
| Delete (DELETE /api/v1/*/{id}) | 10 | 2 | 429 Too Many Requests |

---

## Testing Strategy

### Unit Tests:

- [ ] JwtTokenProviderTest — Token encoding/decoding tests
- [ ] SecurityConfigTest — Security rules verification
- [ ] RateLimitFilterTest — Rate limit enforcement tests

### Integration Tests:

- [ ] AuthenticationIntegrationTest — OAuth2 flow tests
- [ ] AuthorizationIntegrationTest — Role-based access tests
- [ ] PaginationIntegrationTest — Paginated response tests

### Load Tests:

- [ ] PoliticianApiLoadTest — Concurrent list operations
- [ ] CompanyApiLoadTest — Concurrent get/create/delete
- [ ] StressTest — System limits and bottlenecks

---

## Expected Timeline (Phase 3)

| Week | Focus Area | Deliverables |
|------|------------|--------------|
| Week 1 | Authentication & Security | JWT, OAuth2, RBAC implemented |
| Week 2 | Rate Limiting & Validation | Rate limiting, validation handlers |
| Week 3 | Pagination & Caching | Paginated responses, Redis caching |
| Week 4 | Performance Testing | Load tests, optimization reports |

**Estimated Duration:** 1 month (4 weeks)

---

## Next Phase: Production Deployment Preparation

After completing Phase 3, the next steps will be:

1. **Phase 4: Production Readiness** — Health checks, monitoring
2. **Phase 5: Performance Optimization** — Index tuning, query optimization
3. **Phase 6: Documentation & Training** — API docs, admin guides
4. **Phase 7: Deployment** — CI/CD pipeline, production rollout

---

## Success Metrics (Phase 3)

| Metric | Target | Status |
|--------|--------|--------|
| Integration tests passing | 100% | - |
| Code coverage | > 80% | - |
| Authentication working | JWT valid | - |
| Authorization enforced | Role-based active | - |
| Rate limiting functional | Limits applied | - |
| Pagination working | Page/size parameters | - |
| Performance within targets | < 200ms average | - |

**Target Completion Date:** 1 month from start

---

**Phase 3 Status:** Not Started  
**Ready to Begin:** ✅ All prerequisites complete