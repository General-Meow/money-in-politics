# STORY-203: Side-by-Side Voting Records Comparison

**Epic:** EPIC-03 (Journalist and Researcher Tools)  
**Priority:** P2  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Investigative Journalist/Political Analyst

## I Want

A comparison view showing voting records side-by-side for multiple politicians

## So That

I can quickly identify agreement/disagreement patterns and spot potential donor alignment

---

## Acceptance Criteria

### Given two politician IDs
**When** I request a comparison  
**Then** the following is displayed:
- Two-column layout with politician photos and names
- List of voting topics/issues (e.g., "Trade Policy", "Healthcare Reform")
- Each row shows both politicians' votes (+1, -1, or abstain)
- Highlighted rows where they voted differently

### Given the 80% donor alignment threshold
**When** comparing a politician to their top donors  
**Then** each voting record displays:
- Donor's known voting position (if available)
- Visual indicator if politician vote matches donor (>80% aligned)
- Color-coded agreement meter

### Given more than two politicians for comparison
**When** I select additional comparison targets  
**Then** the layout adjusts to show up to 4 politicians side-by-side
(5th+ shown as dropdown selector)

---

## Component Structure

```jsx
// components/analysis/VotingComparison.jsx
import { useState, useEffect } from 'react';

export default function VotingComparison({ politicianIds }) {
  const [comparisonData, setComparisonData] = useState(null);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    async function fetchVotingData() {
      try {
        // Fetch voting records for all selected politicians
        const responses = await Promise.all(
          politicianIds.map(id => 
            fetch(`/api/voting/records?politicianId=${id}`).then(r => r.json())
          )
        );
        
        setComparisonData(computeComparisons(responses));
      } finally {
        setLoading(false);
      }
    }
    
    if (politicianIds.length > 0) fetchVotingData();
  }, [politicianIds]);
  
  if (loading || !comparisonData) {
    return <LoadingSpinner message="Loading voting records..." />;
  }
  
  return (
    <div className="voting-comparison">
      {/* Header with politician summaries */}
      <div className="politician-headers">
        {politicianIds.map((id, idx) => (
          <PoliticianSummary 
            key={id}
            politicianId={id}
            index={idx}
          />
        ))}
      </div>
      
      {/* Voting records table */}
      <table className="voting-table">
        <thead>
          <tr>
            <th className="vote-issue-column">Issue/Topic</th>
            {politicianIds.map((id) => (
              <th key={id}>{getPoliticianName(id)}</th>
            ))}
            <th className="donor-alignment-column">Donor Alignment</th>
          </tr>
        </thead>
        
        <tbody>
          {comparisonData.votingRecords.map((record, idx) => (
            <VotingRecordRow 
              key={idx}
              record={record}
              donorAlignment={record.donorAlignment?.percentage || null}
            />
          ))}
        </tbody>
      </table>
      
      {/* Alignment threshold disclaimer */}
      <div className="threshold-disclaimer">
        <WarningIcon /> This analysis uses an 80% alignment threshold for donor voting patterns.
      </div>
    </div>
  );
}

function VotingRecordRow({ record, donorAlignment }) {
  const agree = record.votes.every(v => v === record.votes[0]);
  
  return (
    <tr 
      className={agree ? 'agreement-row' : 'disagreement-row'}
    >
      <td className="vote-issue-column">
        <span className="issue-name">{record.issueName}</span>
        <div className="issue-summary">{record.summary}</div>
      </td>
      
      {record.votes.map((vote, voteIdx) => (
        <td key={voteIdx} className="vote-cell">
          {vote?.sign ? 
            <>
              <span 
                className={`vote-sign ${vote.sign === 1 ? 'agree' : 'disagree'}`}
              >
                {vote.sign}
              </span>
              {vote.timestamp && (
                <span className="vote-date">
                  {formatDate(vote.timestamp)}
                </span>
              )}
            </>
          : 
            <span className="no-vote">No vote recorded</span>
          }
        </td>
      ))}
      
      <td className="donor-alignment-column" style={{ opacity: donorAlignment ? 1 : 0.5 }}>
        {donorAlignment && (
          <div className="alignment-indicator">
            <ProgressBar value={donorAlignment.percentage} max={100} />
            <span>{donorAlignment.percentage}% aligned</span>
          </div>
        )}
      </td>
    </tr>
  );
}
```

### Alignment Meter Component

```jsx
// components/analysis/AlignmentMeter.jsx
import { useState } from 'react';

export default function AlignmentMeter({ percentage, donorName, politicianName }) {
  const [showDetails, setShowDetails] = useState(false);
  
  // Calculate agreement color based on threshold
  const isHighAgreement = percentage >= 80;
  
  return (
    <div className="alignment-meter" style={{ 
      backgroundColor: isHighAgreement ? '#d4edda' : '#f8d7da' 
    }}>
      <h5>Donor Alignment Analysis</h5>
      
      {/* Progress bar */}
      <div className="meter-container">
        <div 
          className="meter-fill" 
          style={{ 
            width: `${percentage}%`,
            backgroundColor: isHighAgreement ? '#28a745' : '#dc3545'
          }}
        ></div>
      </div>
      
      <span className="meter-value">{percentage}% alignment</span>
      
      {/* Detailed breakdown */}
      {showDetails && (
        <details className="meter-details">
          <summary>View breakdown ({donorName} voting pattern vs. {politicianName})</summary>
          <div className="alignment-breakdown">
            <h6>Agreement: {Math.round((percentage/100) * donorAlignmentSamples.length)}</h6>
            <h6>Disagreement: {Math.round((1 - percentage/100) * donorAlignmentSamples.length)}</h6>
          </div>
        </details>
      )}
    </div>
  );
}
```

---

## API Design for Voting Comparison

### Endpoint to Fetch Multiple Politicians' Voting Records

```java
@RestController
@RequestMapping("/api/voting")
public class VotingController {
    
    @GetMapping("/comparison")
    public MultiPoliticianVotingRecords comparePoliticians(
            @RequestParam List<String> politicianIds) {
        
        return votingService.getComparison(politicianIds);
    }
}

@Service
public class VotingService {
    
    public MultiPoliticianVotingRecords getComparison(List<String> politicianIds) {
        
        // Fetch records for each politician
        Map<String, PoliticianVotingRecord[]> politicianRecords = 
            politicianIds.stream()
                .map(id -> new PoliticianVotingRecord[]())  // Implementation details
                .collect(Collectors.toMap(
                    id -> id,
                    id -> fetchVotingRecordsForPolitician(id)
                ));
        
        // Compute voting record intersections
        VotingRecord[] commonIssues = findCommonVotingTopics(politicianRecords.values());
        
        return new MultiPoliticianVotingRecords(commonIssues, politicianRecords);
    }
}
```

### Response Structure

```json
{
  "politician_ids": ["UK-CHS-LAB", "UK-SND-CNV"],
  "common_issues": [
    {
      "issue_id": "TRADE-TARIFS-2024",
      "issue_name": "Trade Tariffs on Steel Imports",
      "committee": "Business and Trade Committee",
      "vote_date": "2024-11-15",
      "summary": "Motion to reduce tariffs by 10%"
    }
  ],
  "politicians": {
    "UK-CHS-LAB": [
      {
        "issue_id": "TRADE-TARIFS-2024",
        "vote_sign": 1,
        "timestamp": "2024-11-15T14:30:00Z"
      }
    ],
    "UK-SND-CNV": [
      {
        "issue_id": "TRADE-TARIFS-2024",
        "vote_sign": -1,
        "timestamp": "2024-11-15T14:30:00Z"
      }
    ]
  }
}
```

---

## Risks

- **Risk:** Voting records are incomplete or sparsely populated  
  - **Mitigation:** Show empty cells with "no record" indicator; don't break layout

- **Risk:** Donor alignment calculation is complex and data-intensive  
  - **Acceptance:** Use simplified heuristic (same party + same committee membership overlap); document assumption

---

## Definition of Done

- [ ] Comparison view loads within 3 seconds for 2 politicians
- [ ] Agreement/disagreement rows color-coded correctly
- [ ] Donor alignment meter displays when data available
- [ ] Empty votes handled gracefully with placeholder text
- [ ] Layout adjusts to 4-column maximum width
