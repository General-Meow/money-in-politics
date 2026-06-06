# STORY-302: Community Submission Form

**Epic:** EPIC-04 (Community Moderation System)  
**Priority:** P2  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Community Member/Concerned Citizen

## I Want

A form to submit relationship data with proper source documentation

## So That

I can contribute to improving The Web of Politics while ensuring verifiability

---

## Acceptance Criteria

### Given the submission form
**When** I start filling it out  
**Then** required fields are clearly marked:
- Relationship type (dropdown: family/board/social/education/media)
- Entity A and Entity B names (with autocomplete from existing graph)
- Source URL or document attachment (required)
- Confidence level (1-5 scale with tooltips explaining each level)

### When I submit the form
**When** all required fields are filled  
**Then**:
- Submission queued for moderation
- Confirmation page with submission ID displayed
- Email notification sent to contributor
- If source is a URL: preview generated and stored

### Given an incomplete submission
**When** I try to submit without source documentation  
**Then** form shows error message explaining why source links are mandatory

---

## Component Implementation

```jsx
// components/moderation/SubmissionForm.jsx
import { useState } from 'react';

export default function SubmissionForm({ onSubmit }) {
  const [formData, setFormData] = useState({
    relationshipType: '',
    entityAName: '',
    entityBName: '',
    sourceUrl: '',
    confidenceLevel: 1
  });
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Validate required fields
    if (!formData.relationshipType || 
        !formData.entityAName || 
        !formData.entityBName || 
        !formData.sourceUrl) {
      alert('Please fill in all required fields');
      return;
    }
    
    // Create submission
    try {
      await onSubmit({
        ...formData,
        submittedAt: new Date().toISOString()
      });
      
      // Reset form and redirect
      setFormData({
        relationshipType: '',
        entityAName: '',
        entityBName: '',
        sourceUrl: '',
        confidenceLevel: 1
      });
    } catch (error) {
      alert('Submission failed. Please check your connection and try again.');
    }
  };
  
  return (
    <form onSubmit={handleSubmit} className="submission-form">
      <h2>Submit New Relationship</h2>
      
      <div className="form-group">
        <label htmlFor="relationship-type">Relationship Type * <span aria-hidden="true" className="required">*</span></label>
        <select 
          id="relationship-type"
          value={formData.relationshipType}
          onChange={(e) => setFormData({...formData, relationshipType: e.target.value})}
          required
        >
          <option value="">Select relationship type...</option>
          <option value="family">Family/Spouse</option>
          <option value="board_seat">Board Seat / Company Directorship</option>
          <option value="social">Social Connection (Twitter/LinkedIn)</option>
          <option value="education">Shared Education Institution</option>
          <option value="media">Media Co-Appearance / Interview</option>
          <option value="other">Other Relationship</option>
        </select>
      </div>
      
      {/* Entity name inputs with autocomplete */}
      <div className="form-group">
        <label htmlFor="entity-a">Entity A Name *</label>
        <input 
          id="entity-a"
          type="text"
          value={formData.entityAName}
          onChange={(e) => setFormData({...formData, entityAName: e.target.value})}
          placeholder="e.g., 'Keir Starmer' or 'BL plc'"
          required
        />
      </div>
      
      <div className="form-group">
        <label htmlFor="entity-b">Entity B Name *</label>
        <input 
          id="entity-b"
          type="text"
          value={formData.entityBName}
          onChange={(e) => setFormData({...formData, entityBName: e.target.value})}
          placeholder="e.g., 'John Smith' or 'XYZ Corporation'"
          required
        />
      </div>
      
      <div className="form-group">
        <label htmlFor="source-url">Source URL or Document *</label>
        <input 
          id="source-url"
          type="url"
          value={formData.sourceUrl}
          onChange={(e) => setFormData({...formData, sourceUrl: e.target.value})}
          placeholder="https://..."
          required
        />
      </div>
      
      {/* Confidence level selector with tooltip */}
      <div className="form-group">
        <label htmlFor="confidence-level">Confidence Level (1-5)</label>
        <div className="confidence-selector">
          {[1, 2, 3, 4, 5].map(level => (
            <button 
              key={level}
              type="button"
              onClick={() => setFormData({...formData, confidenceLevel: level})}
              className={`confidence-option ${formData.confidenceLevel === level ? 'selected' : ''}`}
              title={`Confidence level ${level}: ${getConfidenceDescription(level)}`}
            >
              {level}
            </button>
          ))}
        </div>
      </div>
      
      <button type="submit" className="btn btn-primary">Submit for Review</button>
    </form>
  );
}

function getConfidenceDescription(level) {
  const descriptions = [
    "Very uncertain",
    "Somewhat uncertain - needs verification",
    "Reasonable confidence",
    "Highly confident based on source",
    "Absolutely certain; source is definitive"
  ];
  return descriptions[level - 1];
}
```

---

## Risks

- **Risk:** Contributors submit duplicate relationships  
  - **Mitigation:** Check for existing connection before submission; merge into existing if same entities already connected

---

## Definition of Done

- [ ] Form displays required field indicators (*)
- [ ] Submit button disabled until all required fields filled
- [ ] Success page confirms submission with ID and timestamp
- [ ] Source URL validation (must be valid HTTPS URL)
