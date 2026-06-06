# STORY-301: Moderation Queue Interface

**Epic:** EPIC-04 (Community Moderation System)  
**Priority:** P2  
**Story Points:** 5  
**Assignee:** [TBD]

## As A

Volunteer Moderator/Community Steward

## I Want

A queue interface to review pending submissions and validate data quality

## So That

I can maintain accurate relationships in the graph before they reach production

---

## Acceptance Criteria

### Given a moderation queue page
**When** I load the page  
**Then** I see:
- List of pending submissions (sorted by date/newest first)
- Submission metadata: submitter, submission date, relationship type
- Auto-validation status indicator (API confirmed/needs manual review)
- "Approve", "Reject", "Request Info" buttons

### Given I click on a submission
**When** I view the details  
**Then** the following are displayed:
- Proposed connection visual preview in graph layout
- Source documentation (if provided as URL, show preview)
- Related existing connections from trusted sources
- Confidence score from submitter

### When I approve a submission
**When** relationship passes validation checks  
**Then**:
- Data imported into Neo4j graph
- Submitter receives confirmation notification
- Submission marked as complete in queue

### When I reject a submission
**When** I provide reasoning and/or require additional info  
**Then**:
- Submission archived to review history (not deleted)
- Submitter notified with rejection reason
- If source links provided: suggest corrections or confirm existing connection

---

## Component Implementation

```jsx
// components/moderation/QueueList.jsx
export default function ModerationQueue({ submissions, onAction }) {
  return (
    <div className="queue-list">
      {submissions.map(submission => (
        <article 
          key={submission.id} 
          className={`queue-item ${submission.autoValidated ? 'auto-validated' : ''}`}
        >
          <div className="queue-header">
            <h4>{submission.relationshipType}</h4>
            <span className="submitter">{submission.submitterName}</span>
            <span className="date">{formatDate(submission.submittedAt)}</span>
          </div>
          
          <div className={`validation-status ${submission.autoValidationStatus}`}>
            {submission.autoValidationStatus === 'confirmed' && '✓ API validated'}
            {submission.autoValidationStatus === 'needs-review' && '⚠ Needs manual review'}
            {submission.autoValidationStatus === 'error' && '✗ Validation failed'}
          </div>
          
          <div className="queue-actions">
            <button onClick={() => onAction('approve', submission.id)}>Approve</button>
            <button onClick={() => onAction('reject', submission.id)}>Reject</button>
            <button onClick={() => onAction('request-info', submission.id)}>Request Info</button>
          </div>
        </article>
      ))}
    </div>
  );
}
```

### Approval Modal

```jsx
// components/moderation/ApprovalModal.jsx
export default function ApprovalModal({ submission, onSubmit }) {
  const [reason, setReason] = useState('');
  const [additionalNote, setAdditionalNote] = useState(false);
  
  return (
    <Modal onClose={onClose} title="Approve Submission">
      <div className="approval-form">
        <h5>Validation Notes</h5>
        
        <textarea 
          placeholder="Optional: Brief explanation for this approval..."
          value={reason}
          onChange={(e) => setReason(e.target.value)}
          rows="3"
        />
      </div>
      
      {additionalNote && (
        <details className="additional-validation">
          <summary>Add additional validation notes</summary>
          <textarea 
            placeholder="Detailed technical notes (internal only)"
            rows="4"
          />
        </details>
      )}
      
      <div className="approval-actions">
        <button 
          onClick={onSubmit}
          className="btn btn-primary"
          disabled={!reason && !additionalNote}
        >
          Approve Connection
        </button>
        
        <button onClick={() => onSubmit('auto-approve')} className="btn btn-secondary">
          Auto-Approve (if API validated)
        </button>
      </div>
    </Modal>
  );
}
```

---

## Risks

- **Risk:** Moderators don't use the interface  
  - **Mitigation:** Public recognition program for active volunteers; showcase contributions on transparency page

- **Risk:** Automated validation produces false positives  
  - **Acceptance:** All auto-validations still require human confirmation before production

