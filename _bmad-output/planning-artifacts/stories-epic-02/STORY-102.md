# STORY-102: Politician Profile with Donor Summary

**Epic:** EPIC-02 (Citizen-Facing Search and Discovery)  
**Priority:** P1  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Voter/Citizen/Investigative Journalist

## I Want

A politician profile page showing top donors and connection overview

## So That

I can quickly understand who funds this politician without navigating multiple pages

---

## Acceptance Criteria

### Given a politician ID
**When** I visit `/profile/{id}`  
**Then** the following are displayed:

#### Header Section
- Politician photo (400x400px, lazy-loaded)
- Full name and primary title (e.g., "MP for Holborn and St Pancras")
- Party affiliation badge (colored by party)
- Current/former status indicator

#### Overview Statistics
- Total donations received: £X,XXX,XXX (currency formatted)
- Number of unique donors
- Most recent donation date
- Data coverage period (e.g., "2015–present")

#### Top Donors Section
**Given** the top 10 donors by amount  
**Then** each displays:
- Donor name (company or individual)
- Total amount donated over data period
- Number of donations made
- Donor type indicator (Corporate | Individual | Trust)
- Link to donor detail page (if available in graph)

#### Connection Overview Graph
- Miniature relationship graph showing:
  - Politician at center
  - Top connections (family, current party, recent board seats)
  - Hover tooltip shows connection type and dates
- "View Full Graph" link expands to full exploration view

#### Recent Activity Timeline
**Given** donations from last 12 months  
**Then** display as scrollable horizontal list:
- Date | Donor Name | Amount | Relationship Type

---

## Component Structure

```jsx
// pages/Profile/:id.jsx
export default function PoliticianProfile({ politicianId }) {
  const { data, loading, error } = usePoliticianData(politicianId);
  
  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage error={error} />;
  
  return (
    <div className="profile-page">
      {/* Header */}
      <ProfileHeader politician={data.politician} />
      
      {/* Top Donors */}
      <Section title="Top Donors" icon={<CurrencyIcon />}>
        <DonorSummary donors={data.topDonors} politiciansId={politicianId} />
      </Section>
      
      {/* Connection Overview */}
      <Section 
        title="Connections" 
        icon={<NetworkIcon />}
        compact
      >
        <MiniGraph connections={data.connections} politicianId={politicianId} />
      </Section>
      
      {/* Recent Activity */}
      <Section title="Recent Donations (12 months)">
        <DonationTimeline donations={data.recentDonations} />
      </Section>
    </div>
  );
}
```

### Donor Summary Component

```jsx
// components/profilers/DonorSummary.jsx
export default function DonorSummary({ donors, politicianId }) {
  return (
    <div className="donor-summary">
      <h3>Total Donations Received: £{formatCurrency(donors.totalAmount)}</h3>
      
      <div className="donor-grid">
        {donors.list.map(donor => (
          <DonorCard 
            key={donor.id}
            donor={donor}
            politicianId={politicianId}
            showLink={true}
          />
        ))}
      </div>
    </div>
  );
}
```

### Donor Card with Link

```jsx
// components/donors/DonorCard.jsx
export default function DonorCard({ donor, politicianId }) {
  return (
    <article className="donor-card" style={{ backgroundColor: donor.type === 'Corporate' ? '#f0f8ff' : '#f5f5f5' }}>
      <div className="donor-header">
        <h4>{donor.name}</h4>
        <span className={`type-badge type-${donor.type.toLowerCase()}`} title={donor.type}>
          {donor.type}
        </span>
      </div>
      
      <div className="donor-stats">
        <div className="stat-item">
          <span className="value">{formatCurrency(donor.totalAmount)}</span>
          <span className="label">Total Donated</span>
        </div>
        <div className="stat-item">
          <span className="value">{donor.count}</span>
          <span className="label">Donations</span>
        </div>
      </div>
      
      <Link 
        to={`/donors/${donor.id}`}
        className={`view-link ${!donor.hasDetail ? 'disabled' : ''}`}
      >
        {donor.hasDetail ? 'View Details →' : 'No details available'}
      </Link>
    </article>
  );
}
```

---

## API Response Structure

```json
{
  "politician": {
    "id": "UK-CHS-LAB",
    "fullName": "Sir Keir Rodney Starmer",
    "name": "Keir Starmer",
    "party": "Labour",
    "constituency": "Holborn and St Pancras",
    "photoUrl": "https://example.com/starmer.jpg",
    "role": "Leader of the House of Commons",
    "former": false,
    "currentSince": "2024-05-05"
  },
  
  "topDonors": {
    "totalAmount": 1234567,
    "uniqueDonorCount": 42,
    "mostRecentDate": "2025-12-15",
    "coveragePeriod": {
      "startYear": 2024,
      "endYear": 2025
    },
    
    "list": [
      {
        "id": "COMP-BL-001",
        "name": "BL plc",
        "totalAmount": 25000,
        "count": 3,
        "type": "Corporate",
        "hasDetail": true
      },
      {
        "id": "IND-JD-001",
        "name": "John Doe Trust",
        "totalAmount": 15000,
        "count": 2,
        "type": "Trust",
        "hasDetail": false
      }
    ]
  },
  
  "connections": [
    {
      "nodeId": "UK-SUS-LAB",
      "nodeName": "Susanne Starmer",
      "nodeType": "Politician",
      "relationshipType": "spouse",
      "startDate": "2024-01-01"
    },
    {
      "nodeId": "UK-LAB-PARTY",
      "nodeName": "Labour Party",
      "nodeType": "Party",
      "relationshipType": "member",
      "startDate": "2003-06-01",
      "endDate": null
    }
  ],
  
  "recentDonations": [
    {
      "date": "2025-12-15",
      "donorName": "BL plc",
      "amount": 10000,
      "relationshipType": "corporate"
    }
  ]
}
```

---

## Styling Requirements (CSS)

### Party Badge Colors

```css
.party-badge {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
}

.party-labour { background-color: #c8102e; color: white; }
.party-conservative { background-color: #f59e0b; color: black; }
.party-lib-dems { background-color: #434a54; color: white; }
.party-green { background-color: #228435; color: white; }

.former-badge {
  display: inline-block;
  background-color: #6c757d;
  color: white;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.7rem;
}
```

---

## Risks

- **Risk:** Some politicians have very few donors (<3)  
  - **Acceptance:** Display "limited data" note; still show available donors

- **Risk:** Donor amounts in millions make cards hard to read  
  - **Mitigation:** Use abbreviated format (M for million) or tooltip with full number

---

## Definition of Done

- [ ] Profile page loads within 2 seconds
- [ ] Politician photo displays with fallback image if loading fails
- [ ] Top donors displayed as grid with amount and count
- [ ] Miniature graph shows key connections before full view
- [ ] Recent donations list scrolls horizontally
- [ ] Mobile layout: donors stack vertically on portrait mode
- [ ] Loading state shows skeleton screens
- [ ] Error page explains if politician not found

---

## Success Metrics

- Profile page views per session: Target >100% of search-to-profile conversion
- Time on profile page: Target >45 seconds
- Graph exploration rate from profile: Target >60% click "View Full Graph"
