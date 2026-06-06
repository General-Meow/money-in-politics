# STORY-003: Electoral Commission API Integration

**Epic:** EPIC-01 (Graph Engine and Data Ingestion Pipeline)  
**Priority:** P0  
**Story Points:** 13  
**Assignee:** [TBD]

## As A

Backend Developer

## I Want

Spring Boot service that ingests donation data from Electoral Commission

## So That

Donation ledger is complete with amounts, dates, donors, and disclosure references

---

## Acceptance Criteria

### Given the Electoral Commission API is available
**When** the ingestion service runs  
**Then** for each disclosed donation:
- Recipient (politician) is identified by name/constituency
- Donor name and type (individual/company/trust) are captured
- Amount in GBP is recorded
- Date of disclosure is logged
- Disclosure reference number is stored

### Given company donor
**When the record is processed  
**Then** a Company node is created or linked if not already in graph

### When donation exceeds threshold (>£7,500 for individuals, >£10k for corporates)
**Then** mandatory disclosure flag is set and data prioritized for ingestion

---

## Electoral Commission Disclosure Categories

| Category | Examples |
|----------|----------|
| Corporate donations | Companies Ltd, PLCs |
| Industrial associations | Chambers of commerce, trade unions |
| Political parties | Party donation accounts |
| Trusts/Foundations | Private foundations making political donations |
| Individual donations | Named individual donors |

### Donation Types

- **Direct donation** — Money given directly to politician/candidate
- **Indirect donation** — Money given to party that benefits politician
- **In-kind contribution** — Services/goods provided (value recorded)

---

## Data Mapping

| EC Field | Neo4j Property | Notes |
|----------|----------------|-------|
| `donor_name` | Donor node properties | Lookup or create Company/Individual node |
| `donation_amount` | Edge property `amount` | Currency always GBP for UK disclosures |
| `disclosure_date` | Edge property `date` | ISO-8601 format |
| `disclosure_reference_number` | Edge property `disc_ref` | Unique identifier |
| `category_of_disclosure` | Relationship type | Links donor to recipient |

---

## Implementation Notes

### Graph Connection Logic

```java
// Link donation to politician and create Company node if needed
public void processDonation(Disclosure disclosure) {
    Politician politician = findByOrConstituency(disclosure.getRecipient());
    Company company = null;
    
    // Check if donor is already a known company
    if (isCompanyType(disclosure.getDonorName())) {
        company = findOrCreateCompany(disclosure.getDonorName());
    } else {
        // Create individual/trust node
        createIndividualNode(disclosure.getDonorName());
    }
    
    // Create donation edge
    EdgeProperties donationEdge = new EdgeProperties();
    donationEdge.amount = disclosure.getAmount();
    donationEdge.date = disclosure.getDate();
    donationEdge.discRef = disclosure.getDiscRef();
    donationEdge.donorType = determineDonorType(disclosure);
    
    matchAndCreateEdge(politician, company ?: createRecipient(), donationEdge);
}
```

---

## Historical Data Challenges

### 2005-2010: EC Website Scraping Required

- Before EC API matured, donations were on website pages
- Need to build scraper for historical disclosures (robots.txt aware)
- Archive scraped data before site changes

### 2010-Present: API Available

- Official API endpoints available via Electoral Commission developer portal
- Rate limits apply (check current documentation)

---

## Risks

- **Risk:** EC API rate limits prevent complete historical ingestion  
  - **Mitigation:** Schedule nightly batches; implement retry logic with backoff

- **Risk:** Historical donation records incomplete or missing fields  
  - **Mitigation:** Accept partial data; flag for manual review if >20% fields null

- **Risk:** Donor name matching is challenging (names changed, typos)  
  - **Mitigation:** Use fuzzy matching; allow community correction via EPIC-04

---

## Definition of Done

- [ ] Service connects to Electoral Commission data source(s)
- [ ] Donation records ingested for current disclosure period
- [ ] Historical data scraped where API unavailable
- [ ] Donor entities created or linked in graph
- [ ] Null fields flagged appropriately
- [ ] Unit tests cover parsing edge cases
- [ ] Documentation includes EC field mapping table

---

## Success Metrics

- Donations ingested: Target 50k+ records from 2005-present
- Data completeness: >75% of required fields populated
- Duplicate detection: <5% flagged for manual review
- Processing time: All donations processed within scheduled window
