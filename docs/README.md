# The Web of Politics

## Overview

**The Web of Politics** is a UK-focused network visualization platform that democratizes access to political finance relationships. Built with Java/Spring Boot and React/D3.js, it reveals the complex web of connections between politicians and donors — from Westminster to local councils across England, Scotland, Wales, and Northern Ireland.

## Mission

To make visible what's hidden: the relationships that shape policy. The Web of Politics maps how politicians connect to donors through family ties, board seats, shared education, lobbying networks, campaign events, think tank memberships, media appearances, and social connections (Twitter/LinkedIn). It empowers journalists, researchers, and citizens to trace influence, understand voting motivations, and hold power accountable.

## Core Vision

> "A world where every political transaction is visible, traceable, and understood — where the relationships that shape policy are no longer opaque but mapped in real-time for anyone willing to explore."

---

## What The Web of Politics Is

### A UK-Wide Political Finance Network Graph
The platform builds a comprehensive Neo4j graph database containing:
- Politicians: Westminster MPs, Lords, Scottish Parliament members, Welsh Senedd members, Northern Ireland Assembly members, and local councilors
- Companies and donors with full relationship mappings
- Historical data from 2005 onwards
- All UK jurisdictions (England, Scotland, Wales, Northern Ireland)

### Multiple Entity Connection Types
The graph captures relationships across multiple dimensions:
- **Family** — Spouses, siblings, parents of politicians
- **Board seats** — Directorships, company affiliations, board memberships
- **Shared education** — Schools and universities attended together
- **Social connections** — Twitter/X and LinkedIn networks
- **Lobbying relationships** — Campaign contributors and lobbyists
- **Campaign events** — Shared political activities and appearances
- **Think tank memberships** — Policy institute affiliations
- **Media appearances** — Co-occurrences in news coverage

### Dynamic Relationship Timeline
Unlike static databases, The Web of Politics shows how connections evolve over time:
- When relationships formed and dissolved
- Historical changes in board memberships
- Career progression and role transitions
- Longitudinal analysis of influence patterns

---

## What You Can Do on The Web of Politics

### For Citizens and Voters

1. **Search Politicians** — Find any MP by name or constituency across all UK jurisdictions
2. **View Donation Ledgers** — See who gave to your representative, total amounts, and top donors
3. **Explore Connections** — Visualize immediate connections (family, current party, recent board seats)
4. **Understand Influence** — See how relationships evolved over decades of data

### For Journalists and Researchers

1. **Export Data** — Download graph snapshots as JSON/GDV for analysis
2. **Compare Politicians** — Side-by-side voting record comparisons with donor alignment analysis
3. **Find Hidden Links** — Entity-to-entity pathfinding ("Who connects A to B?")
4. **Historical Analysis** — Timeline slider showing relationship evolution

### For the Community

1. **Submit Data** — Add new relationships with source documentation
2. **Verify Connections** — Help maintain data quality through moderation
3. **Track Impact** — See recent validations and community contributions

---

## Key Features

| Feature | Description | User Benefit |
|---------|-------------|--------------|
| Search Interface | Find politicians by name or constituency across UK jurisdictions | Quick lookup of representatives |
| Donor Ledger | Complete donation history with entity linking | Transparency on funding sources |
| Network Graph Explorer | Interactive D3.js visualization with zoom/pan/filtering | Visual exploration of relationships |
| Timeline Slider | Historical view from 2005 to present | Context for relationship evolution |
| Entity Detail Pages | In-depth profiles with bio, positions, and connection history | Comprehensive information source |
| Data Export APIs | JSON/GDV/CSV formats for offline analysis | Journalist workflow integration |
| Voting Analysis Tools | Issue-based filtering and donor alignment scoring (80% threshold) | Policy accountability research |
| Community Moderation | Volunteer verification system with automated validation | Data quality and credibility |

---

## Technical Architecture

### Stack Summary
- **Backend:** Java 17+ with Spring Boot — enterprise-grade relationship modeling and data processing
- **Frontend:** React + TypeScript with D3.js — rich visualization ecosystem
- **Graph Database:** Neo4j (industry-leading for relationship queries)
- **Relational DB:** PostgreSQL for transactional data (donations, submissions)
- **Cache Layer:** Redis for high-traffic graph rendering performance
- **Infrastructure:** Docker containerized deployment for local hosting and production migration

### Data Ingestion Strategy
Primary sources from trusted government APIs:
- Parliament.uk API — MP profiles, voting records, committee memberships
- Electoral Commission API — Donation disclosures and donation records
- Companies House API — Company information, director listings, registered addresses
- Twitter/X Developer API — Social connection data (if permissions available)
- LinkedIn Developer API — Professional network connections

Fallback mechanisms:
- Web scraping with respect for robots.txt and rate limiting
- Automated retry logic with exponential backoff
- Historical archive fetching for legacy periods

---

## Implementation Status

### Current Phase: Documentation Complete ✅

The project planning artifacts are fully documented in `_bmad-output/planning-artifacts/`:

| Artifact | Location | Description |
|----------|----------|-------------|
| Product Brief | `brief-web-of-politics-2026-06-06.md` | 14KB comprehensive product specification |
| Epic 01 (Foundation) | `epic-01.md` + 6 stories | Graph engine and data ingestion pipeline |
| Epic 02 (Citizen UI) | `epic-02.md` + 5 stories | Search, profiles, graph explorer MVP |
| Epic 03 (Journalist Tools) | `epic-03.md` + 4 stories | Data export and analysis tools |
| Epic 04 (Moderation) | `epic-04.md` + 3 stories | Community contribution system |
| Epic 05 (Enhancements) | `epic-05.md` + 3 stories | Visualization polish features |

**Total:** 7 epic documents, 21 user stories with acceptance criteria and Java/React implementation examples

### Estimated Implementation Timeline

| Phase | Focus | Duration | Priority |
|-------|-------|----------|----------|
| Phase 1: Foundation Building | EPIC-01 (Graph engine + data ingestion) | 8-12 weeks | P0 Critical |
| Phase 2: MVP Launch | EPIC-02 (Citizen-facing search + graph explorer) | 4-6 weeks | P1 Essential |
| Phase 3: Power User Tools | EPIC-03 (Data export APIs + comparison views) | 3-4 weeks | P2 Important |
| Phase 4: Community System | EPIC-04 (Moderation queue + submission form) | 3-4 weeks | P2 Credibility |
| Phase 5: Polish & Enhance | EPIC-05 (Timeline slider + influence scores) | 2-3 weeks | P3 Nice-to-have |

**Total Timeline:** 16-28 weeks depending on overlap strategy and team size

---

## Data Coverage

### Geographic Scope
- Westminster (UK Parliament) — Full coverage
- Scottish Parliament — Full coverage  
- Welsh Senedd — Full coverage
- Northern Ireland Assembly — Full coverage
- Local Councils (all UK councils) — In scope for initial build

### Historical Depth
- **Earliest data:** 2005 onwards (earliest publicly available records)
- **Data completeness target:** >90% of expected entities from 2005-present
- **Historical limitations:** Early disclosure requirements were less stringent; pre-2010 data may be incomplete by design

### Entity Counts (Target at MVP Launch)
| Entity Type | Target Count | Data Sources |
|-------------|--------------|--------------|
| Politicians | ~600+ current/former MPs + Lords + councillors | Parliament.uk API, Electoral Commission |
| Companies | ~10,000+ unique entities from donation records | Companies House API, Community submissions |
| Donations | ~200,000+ records from 2005-present | Electoral Commission disclosures |
| Relationships | ~150,000+ connections (edges) | Multi-source integration |

---

## Success Metrics

### Adoption
- Unique visitors per month
- Active users signing up for features
- Community submissions (valid + invalid)
- Graph interaction rate

### Impact
- Citations in journalism and policy research papers
- Media mentions referencing The Web of Politics
- Public engagement with donation data on politician pages

### Data Quality
- Historical data coverage >90% of expected entities (2005-present)
- Neo4j query response time <500ms for neighbor lookups
- Automated validation success rate >70% for routine submissions

---

## Legal and Ethical Considerations

### GDPR Compliance
The platform handles personal data of public figures (politicians, donors, company directors). All data is:
- **Publicly disclosed only** — No private communications or non-disclosed information
- **Source-attributed** — Every entry logged with source URL/document reference
- **Right to erasure respected** — Process removal requests in compliance with UK law

### Legal Disclaimer
The Web of Politics does not provide legal, financial, or policy advice. All data is presented for informational purposes only. The platform is not a substitute for professional legal counsel or official government disclosures.

---

## Project Values

1. **Transparency** — Making political finance accessible to all citizens
2. **Accountability** — Helping voters understand who influences their representatives  
3. **Evidence-Based Journalism** — Providing tools for rigorous investigative work
4. **Community Engagement** — Enabling citizens to contribute and verify data
5. **Data Integrity** — Committing to accuracy through automated validation and human review

---

## Get Involved

### For Contributors
This project welcomes open-source contributions from:
- Frontend developers (React + TypeScript)
- Backend engineers (Java/Spring Boot)
- Data engineers (Neo4j schema optimization)
- Community moderators (volunteer data verification)

See `_bmad-output/planning-artifacts/README-EPICS.md` for epic documentation and contribution guidelines.

### For Data Submissions
Community members can submit missing relationships through the moderation queue, always with proper source documentation links.

---

## Credits & Acknowledgments

This project is a passion initiative to democratize political finance data in the UK. Built with community input and volunteer moderation support where possible.

---

*Last updated: 2026-06-06 | Product Brief Version: 1.0*
