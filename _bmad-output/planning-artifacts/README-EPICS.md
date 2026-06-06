# The Web of Politics - Epics & Stories Breakdown

## Overview

This directory contains the complete **epic/story breakdown** for The Web of Politics product, decomposed from the Product Brief (`brief-web-of-politics-2026-06-06.md`).

## Structure

```
planning-artifacts/
├── brief-web-of-politics-2026-06-06.md      # Main product brief (14KB)
├── README-EPICS.md                          # This file - navigation guide
├── epic-INDEX.md                            # Quick link to all epics
├── epics/
│   ├── epic-01.md                           # Graph Engine & Data Ingestion
│   ├── epic-02.md                           # Citizen-Facing Search
│   ├── epic-03.md                           # Journalist Tools
│   ├── epic-04.md                           # Community Moderation
│   ├── epic-05.md                           # Visualization Enhancements
│   ├── SUMMARY.md                           # Epic roadmap and dependencies
│   └── README.md                            # Instructions for using epics
└── stories-epic-{number}/                   # User story files
    ├── STORY-XXX.md                         # Individual story specs
    └── SUMMARY.md                           # Story completion checklist
```

## Quick Start

1. **Start with the Product Brief** at `brief-web-of-politics-2026-06-06.md` to understand the overall product vision
2. **Read EPIC-01** (`epics/epic-01.md`) — foundation work, must be complete first
3. **Review stories** for your current working epic in `stories-epic-{number}/` directories

## How to Use This Breakdown

Each epic document includes:
- Goal and scope definitions
- Acceptance criteria
- Technical requirements
- Risks and mitigations
- Links to related stories

Each story file includes:
- User persona (As A... I Want... So That...)
- Detailed acceptance criteria
- Component implementation examples
- API specifications where relevant
- Risk analysis
- Definition of Done checklist

## Next Steps

1. Review all epic documentation
2. Confirm the Java/Spring Boot tech stack decision
3. Set up development environment (Docker with Neo4j/PostgreSQL/Redis)
4. Begin implementation with EPIC-01, STORY-001 (Neo4j Schema)

---

*Part of The Web of Politics Project | Last updated: 2026-06-06*
