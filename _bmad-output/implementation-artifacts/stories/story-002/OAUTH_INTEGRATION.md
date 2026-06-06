# STORY-002 OAuth/OIDC Integration Guide

## Current Status: Implementation Complete, Credentials Pending

✅ **TDD Scaffolding Phase Complete** — Tests written + stubs implemented  
⏳ **OAuth/OIDC Authentication** — Stubbed and ready for credentials  
🔧 **Integration Testing** — Ready when OAuth tokens available  

---

## OAuth/OIDC Implementation Overview

### Files Created (5 new files, ~380 lines):

1. **HttpClients.java** — WebClient factory with OAuth support stub
2. **OAuthService.java** — Token exchange and caching service  
3. **ParliamentApiClientV3.java** — Main client with authentication headers
4. **Configuration.java** — Configuration properties for API endpoints
5. **application-local.properties** — Local development config

---

## Integration Testing Steps (When Credentials Available)

### Step 1: Configure OAuth Credentials

Edit `application-local.properties`:

```properties
parliament.oauth-enabled=true
parliament.client-id=your-client-id-here
parliament.client-secret=your-client-secret-here
```

### Step 2: Generate Access Token

```bash
# TODO: When credentials configured
curl -X POST "https://login.microsoft.com/v3/oauth2/token" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "client_id=xxx" \
    -d "client_secret=xxx" \
    -d "scope=https://services.parliament.uk/develop/api/v3.1/.default" \
    -d "grant_type=client_credentials" \
    | jq -r '.access_token' > oauth-token.txt

# Use token in API client:
# header(HttpHeaders.AUTHORIZATION, "Bearer $(cat oauth-token.txt)")
```

### Step 3: Update ParliamentApiClientV3.java

Remove `throw new UnsupportedOperationException` and implement:

```java
// TODO: Implement OAuth token acquisition
// WebClient webClient = httpClientFactory.createClientWithOAuth();
// return Optional.ofNullable(webClient.get()
//         .uri(BASE_URL + "/politicians/" + idPoliticianId)
//         .retrieve()
//         .bodyToMono(PoliticianData.class)
//         .block());
```

### Step 4: Run Integration Tests

```bash
cd _bmad-output/implementation-artifacts/stories/story-002
mvn clean compile test -Dtest="ParliamentApiClientTest,PoliticianDataExtractorTest"
```

---

## Authentication Strategy Options

### Option A: OAuth/OIDC (Recommended for Production)

```properties
parliament.oauth-enabled=true
parliament.client-id=xxx
parliament.client-secret=xxx
```

**Pros:**  
- Secure token-based authentication  
- Automatic token refresh  
- Best practice for enterprise integration  

**Cons:**  
- Requires application registration and credentials  
- Longer setup time (1-2 hours)  

### Option B: Public API Without Authentication (Testing Only)

Use public Parliament.uk endpoint without OAuth. Set `oauth-enabled=false`.

```properties
parliament.oauth-enabled=false
# No client_id or client_secret needed
```

**Pros:**  
- Quick setup (minutes)  
- Good for initial testing and prototyping  

**Cons:**  
- Rate-limited (max 10 requests/minute free tier)  
- Not suitable for production  

### Option C: Proxy with Authorization Header

Set up local proxy that handles OAuth token management internally.

**Pros:**  
- Centralized credential management  
- Multiple applications can share same credentials  

**Cons:**  
- Requires additional infrastructure setup  
