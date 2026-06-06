# Epics for The Web of Politics

This directory contains user stories and acceptance criteria broken down from the product brief.

## Epic Structure

Each epic represents a major feature area or capability that contributes to the MVP.

Epics are ordered by priority:
1. **EPIC-XXX** — Core graph engine and data ingestion (MVP essential)
2. **EPIC-YYY** — Citizen-facing features and search (MVP essential)  
3. **EPIC-ZZZ** — Journalist/researcher tools (MVP important but not essential)
4. **EPIC-WWW** — Community moderation (MVP required for credibility)
5. **EPIC-VVV** — Visualization enhancements (nice-to-have)

## Reading Guide

- `epic-{number}.md` — Epic documentation with goal, scope, and stories
- `{story-number}-Story.md` — Individual user stories within each epic

---

## Implementation Order Recommendation

Start with EPIC-01 (Graph Engine) before building any features. Without the core graph infrastructure, nothing else is possible.
