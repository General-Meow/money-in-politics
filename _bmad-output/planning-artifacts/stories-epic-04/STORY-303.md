# STORY-303: Automated API Validation Pipeline

**Epic:** EPIC-04 (Community Moderation System)  
**Priority:** P2  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Backend Developer/Data Engineer

## I Want

Automated validation checks that run against government APIs before human review

## So That

Routine errors are caught automatically, and moderators only review edge cases

---

## Acceptance Criteria

### Given a new submission
**When** the validation service runs  
**Then** the following checks occur:

1. **Entity Existence Check:**
   - Politician exists in Parliament.uk database?
   - Company exists in Companies House?
   
2. **Duplicate Detection:**
   - Relationship already exists in graph?
   - If yes, flag for review (possible duplicate submission)
   
3. **Source Verification:**
   - Source URL accessible?
   - Content matches submitted data? (parse HTML if possible)

4. **Confidence Score Adjustment:**
   - If auto-validated: suggest increasing confidence score
   - If validation fails: reduce default confidence score

### When submission passes all checks
**Then**:
- `autoValidationStatus: 'confirmed'` set on submission record
- Moderation queue can mark as "Auto-approved if accepted"

### When submission needs manual review
**Then**:
- `autoValidationStatus: 'needs-review'` set
- Submission moves to manual review queue with flag icon

---

## Service Implementation

```java
// services/ModerationValidationService.java
@Service
public class ModerationValidationService {
    
    /**
     * Validates community submission against trusted data sources.
     * Returns validation report with status and findings.
     */
    public ValidationReport validateSubmission(ModificationRequest request) {
        
        ValidationReport report = new ValidationReport();
        
        // Check 1: Entity existence
        report.setEntityAExists(entityService.existsByName(request.getEntityAName()));
        report.setEntityBExists(companyService.existsByName(request.getEntityBName()));
        
        // Check 2: Duplicate detection
        List<Relationship> existingRelations = relationshipRepository.findByEntities(
            request.getEntityAName(), 
            request.getEntityBName()
        );
        if (existingRelations.size() > 0) {
            report.setHasDuplicates(true);
            report.setDuplicateCount(existingRelations.size());
        }
        
        // Check 3: Source URL verification
        try {
            Optional<WebPageContent> pageContent = webScraper.fetchUrl(request.getSourceUrl());
            if (pageContent.isPresent()) {
                report.setSourceAccessible(true);
                report.setSourceContentType("URL");
                report.setContentSummary(pageContent.get().getText());
            } else {
                report.setSourceAccessible(false);
                report.addValidationIssue("Source URL returned 404 or similar error");
            }
        } catch (Exception e) {
            report.setSourceAccessible(false);
            report.addValidationIssue("Unable to fetch source: " + e.getMessage());
        }
        
        // Check 4: Data consistency
        if (request.getRelationshipType().equals("board_seat")) {
            Company company = findOrCreateCompany(request.getEntityBName());
            List<Director> knownDirectors = directorService.getDirectors(company.getId());
            
            boolean isKnownDirector = knownDirectors.stream()
                .anyMatch(d -> d.getName().equalsIgnoreCase(request.getEntityAName()));
            
            if (!isKnownDirector) {
                report.addValidationIssue(
                    "Relationship type is 'board_seat' but entity A is not listed as director"
                );
                report.setConfidenceScoreAdjustment(-1);  // Lower confidence
            }
        }
        
        return report;
    }
    
    /**
     * Updates submission status based on validation results.
     */
    public void updateSubmissionStatus(ModificationRequest request, ValidationReport report) {
        
        Submission submission = submissionRepository.findBySourceUrl(request.getSourceUrl());
        
        if (report.getEntityAExists() && report.getEntityBExists() && 
            !report.getHasDuplicates()) {
            
            // Auto-approve if confidence high and source verified
            boolean canAutoApprove = report.getSourceAccessible() && 
                                     request.getConfidenceScore() >= 4;
            
            if (canAutoApprove) {
                submission.setStatus("auto_approved");
                submission.setAutoValidationStatus("confirmed");
                submissionRepository.save(submission);
            } else {
                submission.setStatus("needs_review");
                submission.setAutoValidationStatus("needs-review");
                submission.setAutoReviewReason(report.getValidationIssues().toString());
                submissionRepository.save(submission);
            }
            
        } else if (report.getHasDuplicates()) {
            // Duplicate detected - suggest merge or update existing
            Submission relatedSubmission = findRelatedExistingConnection(
                request.getEntityAName(), 
                request.getEntityBName()
            );
            submission.setMergeTargetSubmissionId(relatedSubmission.getId());
            submission.setStatus("duplicate_found");
            submissionRepository.save(submission);
        } else {
            // Other issues flagged for manual review
            submission.setStatus("needs_review");
            submission.setAutoValidationStatus("needs-review");
            submission.setValidationIssues(report.getValidationIssues());
            submissionRepository.save(submission);
        }
    }
}
```

---

## Risks

- **Risk:** Automated validation produces too many false positives  
  - **Mitigation:** Only auto-approve if all checks pass; otherwise flag for review

- **Risk:** Government APIs are unavailable during scraping window  
  - **Mitigation:** Queue submissions for retry later; log failures for alerting

