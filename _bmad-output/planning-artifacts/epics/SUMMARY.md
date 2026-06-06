# The Web of Politics - Complete Epic Summary

## Overview

This document contains 5 epics broken down from the Product Brief for **The Web of Politics** — a UK-wide network visualization platform mapping political finance relationships.

---

## Epic Priority Order

Epics are ordered by dependency and implementation priority:

1. **EPIC-01:** Graph Engine and Data Ingestion (Foundation)
2. **EPIC-02:** Citizen-Facing Search and Discovery (Frontend MVP)
3. **EPIC-03:** Journalist and Researcher Tools (Power User Features)
4. **EPIC-04:** Community Moderation System (Credibility Feature)
5. **EPIC-05:** Visualization Enhancements (Nice-to-Have)

---

## Epic-by-Epic Summary

### EPIC-01: Graph Engine and Data Ingestion Pipeline
**Priority:** P0 (Critical Path)  
**Effort:** 8-12 weeks  
**Team Size:** Solo (initially)  

**Goal:** Build the core graph infrastructure that powers all visualization and analysis features.

**Key Components:**
- Neo4j graph database with relationship schema
- Java/Spring Boot data ingestion services for UK government APIs
- Historical data backfill from 2005 onwards
- Automated scraper pipelines with fallback mechanisms

**User Stories:**
1. STORY-001: Neo4j Graph Schema (P0, 5 points)
2. STORY-002: Parliament.uk API Integration (P0, 8 points)
3. STORY-003: Electoral Commission API Integration (P0, 13 points)
4. STORY-004: Companies House API Integration (P0, 8 points)
5. STORY-005: Fallback Scrapers for API Failures (P0, 8 points)
6. STORY-006: Historical Data Backfill 2005+ (P0, 13 points)

---

### EPIC-02: Citizen-Facing Search and Discovery
**Priority:** P1 (MVP Essential)  
**Effort:** 4-6 weeks  
**Team Size:** Solo initially  

**Goal:** Build the public-facing website allowing citizens to explore political finance relationships with intuitive search and visualization.

**Key Components:**
- React frontend application with responsive design
- Entity search by name or constituency
- Politician profile pages with donor summaries
- D3.js relationship graph exploration
- Mobile-responsive design with touch controls

**User Stories:**
1. STORY-101: Search Interface (P1, 5 points)
2. STORY-102: Profile with Donor Summary (P1, 8 points)
3. STORY-103: Interactive Graph Explorer (P1, 13 points)
4. STORY-104: Mobile Responsive Design (P1, 5 points)
5. STORY-105: Historical Timeline Slider (P2, 8 points)

---

### EPIC-03: Journalist and Researcher Tools
**Priority:** P2 (MVP Important but not Essential)  
**Effort:** 3-4 weeks  
**Team Size:** Solo or with frontend specialist  

**Goal:** Build advanced data export, analysis, and comparison tools for journalists and academic researchers.

**Key Components:**
- Data export features (JSON/GDV/CSV files)
- Advanced filtering controls
- Side-by-side voting record comparisons
- Issue/topic-based analysis overlay

**User Stories:**
1. STORY-201: JSON Graph Snapshot Export (P2, 5 points)
2. STORY-202: CSV Relationship List Export (P2, 5 points)
3. STORY-203: Voting Records Comparison View (P2, 8 points)
4. STORY-204: Issue Filtering Overlay (P3, 13 points)

---

### EPIC-04: Community Moderation System
**Priority:** P2 (MVP Required for Credibility)  
**Effort:** 3-4 weeks  
**Team Size:** Solo initially  

**Goal:** Build a community moderation system allowing volunteers to verify data submissions and maintain quality.

**Key Components:**
- Community submission portal with source documentation
- Moderation queue interface
- Automated API validation pipeline
- Duplicate detection and merge suggestions

**User Stories:**
1. STORY-301: Moderation Queue Interface (P2, 5 points)
2. STORY-302: Community Submission Form (P2, 8 points)
3. STORY-303: Automated API Validation Pipeline (P2, 8 points)

---

### EPIC-05: Visualization Enhancements
**Priority:** P3 (Nice-to-Have)  
**Effort:** 2-3 weeks  
**Team Size:** Frontend specialist recommended  

**Goal:** Add advanced visualization features for power users.

**Key Components:**
- Relationship timeline slider
- Influence score calculations
- Network density heatmaps
- Animated graph transitions

**User Stories:**
1. STORY-401: Timeline Slider (P3, 8 points)
2. STORY-402: Influence Score Calculations (P3, 5 points)
3. STORY-403: Network Density Heatmap Overlay (P3, 13 points)

---

## Implementation Roadmap

### Phase 1: Foundation (Weeks 1-12)
**Focus:** EPIC-01 only

Deliverables:
- Neo4j graph engine running locally
- API integrations complete (Parliament.uk, Electoral Commission, Companies House)
- Historical dataset backfilled to launch date
- Fallback scrapers operational and tested

### Phase 2: Frontend MVP (Weeks 13-18)
**Focus:** EPIC-02 (can overlap with end of Phase 1)

Deliverables:
- Search interface working on localhost
- Profile pages render with graph visualization
- Mobile responsive design complete
- Beta testers can explore the platform

### Phase 3: Power User Features (Weeks 19-22)
**Focus:** EPIC-03 (can overlap with end of Phase 2)

Deliverables:
- Data export APIs functional
- Voting comparison tools available
- Issue filtering overlays implemented

### Phase 4: Community & Moderation (Weeks 23-26)
**Focus:** EPIC-04 (can start after Phase 2 or overlap with Phase 3)

Deliverables:
- Submission form online and collecting data
- Moderation queue operational
- Automated validation pipeline running

### Phase 5: Polish and Enhancements (Weeks 27-30+)
**Focus:** EPIC-05 (optional, depends on timeline/budget)

Deliverables:
- Timeline slider for historical exploration
- Influence score visualizations
- Network density heatmaps

---

## Technical Stack Summary

| Layer | Technology | Reason |
|-------|-----------|--------|
| Backend | Java 17 + Spring Boot | Enterprise-grade data processing, relationship modeling |
| Frontend | React + TypeScript | Rich ecosystem for visualization libraries |
| Graph DB | Neo4j | Industry-leading graph database; industry-standard for relationship queries |
| Relational | PostgreSQL | Transactional data (donations, submissions, metadata) |
| Cache | Redis | High-traffic graph rendering performance |
| Build Tooling | Maven/Gradle | Standard Java build tools with Docker Compose support |
| Testing | JUnit/Mockito + Playwright | Industry-standard testing frameworks |

---

## Estimated Total Effort

| Epic | Duration (weeks) | Team Size | Notes |
|------|------------------|-----------|-------|
| EPIC-01 | 8-12 | Solo | Foundation work; must be complete before features |
| EPIC-02 | 4-6 | Solo or +frontend | Can overlap with end of EPIC-01 |
| EPIC-03 | 3-4 | Solo or +frontend | Can overlap with EPIC-02 |
| EPIC-04 | 3-4 | Solo | Best done after frontend is stable |
| EPIC-05 | 2-3 | Frontend specialist | Nice-to-have; optional |

**Total Timeline:** 16-28 weeks depending on overlap strategy

---

## Success Metrics (by Epic)

### EPIC-01 (Graph Engine)
- Historical data coverage: >90% of expected entities (2005-present)
- Graph query response time: <500ms for neighbor lookups
- API uptime during ingestion cycles: >99%

### EPIC-02 (Citizen-Facing)
- Search success rate: 95% find intended politician within 1 click
- Profile page views per session: >100% search-to-profile conversion
- Graph interaction rate: Users zoom/pan/filter at least once per session

### EPIC-03 (Power User Tools)
- Export file generation: <5 seconds for datasets <100k edges
- Comparison view loads within 3 seconds for typical datasets

### EPIC-04 (Moderation)
- Submission to moderation queue latency: <1 minute
- Auto-validation success rate: >70% for routine submissions
- Queue backlog maintained below 50 items at any time

---

## Definition of Done (Project-Level)

All epics must be complete when MVP is launched. Additionally:

- [ ] All acceptance criteria met per story
- [ ] Code reviewed and merged to main branch
- [ ] Unit tests written for new features
- [ ] Documentation updated with examples
- [ ] Performance benchmarks achieved
- [ ] Deployment pipeline operational for local hosting

---

## Next Steps

1. **Read each epic document** (`epic-{number}.md`) in detail
2. **Review user stories** in `stories-epic-{number}/` directories
3. **Begin implementation** with EPIC-01 (foundation)
4. **Set up development environment:** Docker, Neo4j, PostgreSQL, Redis

---

*Generated: 2026-06-06 | Product Brief Version: 1.0*
