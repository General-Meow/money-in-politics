---
title: The Web of Politics
status: draft
created: 2026-06-06
updated: 2026-06-06
author: Paul
version: 1.0
---

# The Web of Politics

## Purpose & Problem

**Problem:** Political finance in the UK operates in an informationally asymmetric environment. Journalists and citizens lack access to a comprehensive, dynamic map of how politicians connect to donors — through family, board seats, shared education, lobbying networks, campaign events, social connections, think tank memberships, media appearances, and more. This complexity makes it difficult to hold power accountable or understand the motivations behind voting decisions.

**Purpose:** The Web of Politics provides a transparent, visual network graph of UK political finance relationships — from Westminster to local councils across England, Scotland, Wales, and Northern Ireland. It democratizes access to who gives, who receives, how they're connected, and whether their votes align with their donors' interests. Built for journalists investigating deep connections and for citizens seeking clarity on the politics shaping their lives.

**Goal:** Create a comprehensive, historically-rich network visualization platform that reveals the complex network of connections around UK politicians, empowering users to trace influence from multiple angles.

---

## Vision & Mission

**Vision:** A world where every political transaction is visible, traceable, and understood — where the relationships that shape policy are no longer opaque but mapped in real-time for anyone willing to explore.

**Mission:** To build The Web of Politics, the definitive public map of UK political finance relationships, continuously updated from government sources and enriched by community contributions, making the complex network of influence accessible to journalists, researchers, and every citizen.

---

## Scope (Initial Build)

### In Scope
- UK political figures: Westminster MPs, Lords, Scottish Parliament members, Welsh Senedd members, Northern Ireland Assembly members, and local councilors
- Historical data from 2005 onwards (earliest publicly available records)
- Data sources: UK government websites (Parliament.uk, Electoral Commission, Companies House, etc.)
- Network graph visualization showing all connection types: family ties, board seats, shared education, lobbying relationships, campaign events, think tank memberships, media appearances, social connections (Twitter/LinkedIn)
- Donation ledger: who gave, how much, to whom
- Voting records with donor alignment analysis (80% threshold)
- Community data submission with moderation queue for validation
- Mobile-responsive web application
- Local hosting with planned migration path

### Out of Scope (Future)
- Real-time alerting on new donation disclosures
- Commercial partnerships or monetization features
- International political finance data

---

## User Personas

### 1. The Investigator Journalist

> **Profile:** Senior journalist at a broadsheet investigating a politician's business connections  
> **Goals:** Exportable data, cross-referencing multiple entities, finding patterns across time periods  
> **Key Features:**
> - Filter and search by entity type (companies, individuals, parties)
> - Download graph snapshots or relationship lists for articles
> - Compare voting patterns across politicians
> - Deep dive into historical donation records

### 2. The Concerned Citizen

> **Profile:** Voter preparing to cast their next vote, wanting transparency  
> **Goals:** "Who gave to my MP?", understand why decisions are made, check specific claims  
> **Key Features:**
> - Search by name or constituency
> - See donation totals from organizations they support/oppose
> - Quick overview of top donors (visual summary)
> - Simple explanations of connections

### 3. The Academic Researcher

> **Profile:** Political science PhD student studying the evolution of campaign finance  
> **Goals:** Longitudinal analysis, data integrity verification, comparative studies  
> **Key Features:**
> - Exportable historical datasets
> - Comparison tools across time periods
> - Cross-reference between different entity types
> - Ability to validate or suggest corrections

### 4. The Community Moderator

> **Profile:** Volunteer who helps maintain data quality and enrich the platform  
> **Goals:** Verify community submissions, flag suspicious connections, add missing links  
> **Key Features:**
> - Review moderation queue with flagged items
> - Validate community-submitted data against trusted sources
> - Add corrections or new relationship suggestions
> - Track verification statistics

---

## Key Features & Capabilities

### Citizen-Focused Features
1. **Entity Search:** Search for any politician by name or constituency across all UK jurisdictions
2. **Donation Overview:** See who has given to an MP/Member (total amounts, top donors)
3. **Quick Connection Graph:** Visual overview showing immediate connections (family, top donors, current party)

### Journalist/Researcher Features
4. **Advanced Graph Exploration:** Zoomable pan/zoom network graph with entity-type filtering and timeline slider
5. **Data Export:** Export graph snapshots as JSON/GDV, download relationship lists, export historical datasets
6. **Voting Analysis Tools:** Compare politicians' votes, filter by issue/topic, see donor alignment percentages
7. **Entity Detail Pages:** Deep dive into any connected entity (company profiles, lobbying firm pages)
8. **Relationship Timeline:** Visualize how connections between entities evolved over time (e.g., shared board seat from 2015-2019, then dissolved)
9. **Influence Score:** Rank politicians by donor concentration and connection diversity

### Shared Platform Features
10. **Responsive Design:** Fully functional on mobile devices with touch-friendly controls
11. **Community Submission Portal:** Form to submit missing connections with required fields
12. **Moderation Queue:** Admin panel for moderators to review and validate submissions
13. **Public Data Sources Integration:** Automated ingestion from UK government websites
14. **Entity-to-Entity Pathfinding:** Find relationships between any two entities ("who connects A to B?") with customizable hop limits
15. **Validation Transparency Layer:** Public feed of recent validations, corrections, and community contributions

---

## Technical Considerations

### Recommended Full Stack (Start Lean)

**Backend:** Java 17+ + Spring Boot
- Industry-standard for enterprise applications and data processing
- Excellent libraries for API development, web scraping, and data modeling
- Strong typing and compile-time safety for complex relationship logic

**Frontend:** React + TypeScript
- Industry-standard component-based architecture
- Rich ecosystem for data visualization libraries
- Mobile-responsive with touch-friendly controls

**Visualization Libraries:**
- **D3.js**: Industry-standard for interactive graph network visualizations
- Flexible, declarative approach to building custom graph layouts
- Strong community support and documentation

**Database Technologies:**
- **Neo4j Graph Database**: Primary storage for relationships (industry-leading graph DB)
- **PostgreSQL**: Transactional data (donations, submissions, metadata)
- **Redis**: Caching layer for performance

**Infrastructure:**
- **Docker**: Containerization for local development and deployment
- **Systemd service management**: For local hosting environment
- **Git version control**: Code repository

**Testing Frameworks:**
- **JUnit/Mockito**: Java backend testing (industry standard)
- **Jest/Vitest**: Frontend component testing (React ecosystem)
- **Playwright**: E2E testing for scraping workflows and critical paths

**Security & Compliance:**
- GDPR-compliant data handling (UK jurisdiction)
- OWASP ZAP: Automated vulnerability scanning
- Spring Security for authentication/authorization

---

### Rationale

- **Java 17/Spring Boot** chosen over Python for enterprise-grade data processing and relationship modeling
- **D3.js** provides maximum flexibility for custom graph visualizations
- **Industry-standard stack** ensures ease of finding developers if needed
- This stack can run on a single VPS initially, then migrate components as needed

### Development Environment
- IntelliJ IDEA or Eclipse IDE
- Maven or Gradle for build management
- Local Docker Compose for Neo4j, PostgreSQL, Redis containers

---

## Success Metrics & KPIs

### Adoption Metrics
- Unique visitors per month
- Active users signing up for features (journalists, researchers)
- Community submissions (valid + invalid)
- Graph interaction rate (zoom, filter, pathfinding queries executed)

### Impact Metrics
- Citations in journalism/policy research papers
- Media mentions referencing The Web of Politics
- Public engagement with donation data on politician pages
- Time spent exploring graphs vs. simple searches

---

## Risks & Mitigations

### Data Availability Risks
- **Risk:** Government APIs change or stop being available
  - **Mitigation:** Build scraper fallbacks; maintain multiple access methods per source
  
- **Risk:** Private data sources become inaccessible (LinkedIn API restrictions)
  - **Mitigation:** Use cached social connections; prioritize government-sourced relationships

### Legal Risks
- **Risk:** Data scraping challenged by third parties
  - **Mitigation:** Respect robots.txt, use official APIs first, document all data provenance
  
- **Risk:** GDPR complaints from individuals whose data appears
  - **Mitigation:** Only use publicly disclosed information; clear disclaimers

### Platform Risks
- **Risk:** Moderation queue backlog
  - **Mitigation:** Clear contributor guidelines; public recognition for volunteers
  
- **Risk:** Graph performance degradation with scale
  - **Mitigation:** Caching layer, Neo4j indexing strategy, load testing

---

## Launch Strategy & Rollout Plan

### Phase 1: Foundation Building
- Build core graph engine and data ingestion pipeline
- Load test with historical dataset (2005-present)
- Beta access for journalist network and researcher partners (manual outreach as needed)
- Refine UX based on deep-dive feedback

### Phase 2: Public Beta
- Open public submissions with moderation queue
- Release roadmap and community guidelines
- Recruit first moderators/volunteers through manual outreach
- Monitor performance under real-world load

### Phase 3: Full Launch
- General availability to all UK citizens
- Press outreach targeting journalists and policy wonks (as opportunities arise)
- Community spotlight featuring early adopters' stories

---

## Team & Roles (Initial)

### Lead Architect & Developer
- **Role:** Design architecture, build scraper integrations, implement graph engine
- **Current:** Solo development (with volunteer moderators later)
- **Future:** Open-source contributors as project gains traction

### Community Moderators
- **Role:** Verify community submissions, flag suspicious connections, add corrections
- **Recruitment:** Manual outreach once platform reaches sustainable growth

### Data Stewards (Volunteer)
- **Role:** Monitor scraper health, update fallback logic, maintain data quality docs
- **Future role:** Add as trusted contributors if needed

---

## Appendices

### Appendix A: Glossary of Terms

| Term | Definition |
|------|------------|
| Entity | Any node in the graph (politician, company, donor, party, etc.) |
| Edge | Relationship between two entities (family, donation, board seat, etc.) |
| Donor Alignment Score | Percentage threshold (80%) indicating shared political interest |
| Graph Snapshot | Exportable state of the network at a point in time |
| Hop Limit | Maximum number of relationship steps when finding connections between entities |

---

### Appendix B: Data Dictionary

| Entity Type | Key Fields | Source(s) |
|-------------|------------|-----------|
| Politician | name, role, constituency, party, tenure | Parliament.uk, Electoral Commission |
| Company | name, industry, headquarters, ceo | Companies House |
| Donation | donor_id, recipient_id, amount, date, purpose | Electoral Commission |
| Relationship | type (family/board/media/social), entities A/B, start_date, end_date | Government APIs + community |

---

### Appendix C: Legal References

- Political Parties, Elections and Referendums Act 2000
- Data Protection Act 2018 / GDPR
- Companies House data usage terms
- Electoral Commission disclosure requirements

---

## Brief Summary

The Web of Politics is a UK-wide network visualization platform mapping political finance relationships from Westminster to local councils. It reveals how politicians connect to donors through family ties, board seats, shared education, lobbying, campaign events, think tank memberships, media appearances, and social connections. Built on Neo4j with government APIs as primary data sources, it supports both citizen exploration (search by name, donation overviews) and journalist investigation (exportable graphs, voting analysis, entity detail pages). The platform features a responsive design, community submission system with moderation queue, pathfinding tools to find relationships between any two entities, and historical data from 2005 onwards.

---

*Brief created through coaching session with Paul on 2026-06-06. This document serves as the canonical product brief for The Web of Politics project.*
