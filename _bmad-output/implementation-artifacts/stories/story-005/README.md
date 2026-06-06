# STORY-005: Web Scraper Fallback Mechanism

## Story Summary

**Priority:** P1 (Fallback for API failures)  
**Story Points:** 3 points  
**Acceptance Criteria ID:** AC-STORY-005  

Build Java/Spring Boot web scraper as fallback mechanism for politicians and donation data when APIs are unavailable or rate-limited.

---

## Acceptance Criteria

| Criterion | Status |
|-----------|--------|
| Web scraper for politician profiles | ⏳ TODO |
| HTML parsing for field extraction | ⏳ TODO |
| Schema mapping to Neo4j | ⏳ TODO |

---

## Files Created (TDD Approach - Red Phase: Tests)

### Unit Tests (2 files, ~560 lines):

1. **WebScraperTest.java** — Politician and donation web scraping tests
2. **HtmlParserTest.java** — Field extraction and schema mapping tests

---

## Files Created (TDD Approach - Green Phase: Stubs)

### Implementation Classes (2 files, ~340 lines):

1. **WebScraper.java** — Main web scraping logic with DTOs
2. **HtmlParser.java** — HTML parsing utility using Jsoup

---

## Related Stories

| Story | Relationship |
|-------|--------------|
| STORY-001 (Neo4j Schema) | Depends on EPIC-01 completion |
| STORY-002/003/004 (API Clients) | Fallback when APIs fail or rate-limited |

