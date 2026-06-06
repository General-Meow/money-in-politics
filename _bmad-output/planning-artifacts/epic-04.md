# EPIC-04: Community Moderation System

**Status:** Not Started  
**Priority:** P2 (MVP Required for Credibility)  
**Effort Estimate:** 3-4 weeks  
**Team Size:** Solo initially  

## Goal

Build a community moderation system that allows volunteers to verify data submissions, flag suspicious connections, and add corrections while maintaining data quality standards.

## Scope

### In Scope
- Community submission portal:
  - Form to submit missing relationships (politician-company links, family ties, etc.)
  - Source documentation requirement for all submissions
  - Automatic categorization by relationship type
  
- Moderation queue interface:
  - Review flagged submissions against trusted sources
  - Approve/reject with reasoning
  - Request additional source documentation
  
- Data quality tools:
  - Duplicate detection and merge suggestions
  - Automated validation against government APIs
  - Confidence scoring for edge cases

### Out of Scope (Future)
- Public-facing moderation feed/transparency layer
- Volunteer recruitment portal
- Moderator performance dashboards

---

## User Stories

See `stories-epic-04/` directory.

### Summary:
- **STORY-301:** As a volunteer moderator, I want to review submissions so that I can validate data quality
- **STORY-302:** As a contributor, I want to submit relationships with source links so that my contributions are verifiable
- **STORY-303:** As a developer, I want automated API validation so that obvious errors are caught automatically

---

## Acceptance Criteria

### Submission Form
1. Form requires:
   - Relationship type (dropdown: family/board/social/etc.)
   - Source URL or document (mandatory)
   - Confidence level (1-5 scale)
2. Preview shows how data will appear in graph

### Moderation Queue
3. Queue shows:
   - Source documentation preview
   - Auto-validation status (API confirmed/needs review)
   - Related existing connections
4. Approve/reject buttons with reasoning field

---

## Risks

- **Risk:** Moderation queue backlog grows too large  
  - **Mitigation:** Automate obvious cases; focus manual review on edge cases

- **Risk:** Contributors submit without proper sourcing  
  - **Mitigation:** Reject submissions without verifiable source links

