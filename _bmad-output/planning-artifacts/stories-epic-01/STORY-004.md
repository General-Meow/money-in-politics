# STORY-004: Companies House API Integration

**Epic:** EPIC-01 (Graph Engine and Data Ingestion Pipeline)  
**Priority:** P0  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Backend Developer

## I Want

Spring Boot service that integrates with Companies House API to map company connections

## So That

Company nodes have complete profiles and CEO/board connections are captured

---

## Acceptance Criteria

### Given a list of companies from donation records
**When** the ingestion service runs  
**Then** for each company:
- Company name is extracted (official registered name)
- Industry sector is identified (SIC code mapped to readable industry)
- Headquarters location is captured
- CEO/Director names are linked to politician nodes if applicable
- Incorporation date is recorded
- Registered office address is stored

### Given a company with known CEO
**When the data is processed  
**Then** relationship edge exists between CEO and Company node

### When donation comes from company
**Then** Company node already exists in graph before donation edge created

---

## Companies House Data Available

### Core Company Information (FREE API)
- Company name and registration number
- Incorporated on date
- Registered office address
- SIC codes (industry classification)
- Company status (active/liquidation/dissolved)

### Officers/Directors (Semi-paid tier needed for full history)
- Director names and service addresses
- Appointed and ceased dates
- Occupation if provided
- Nationality (if disclosed)

### Address History
- All registered office changes
- Correspondence address history
- Service address history

---

## Data Mapping

| CH Field | Neo4j Property | Notes |
|----------|----------------|-------|
| `company_name` | Company node properties | Use official name, not trading name |
| `sic_codes` | Company node property `industry` | Map SIC to readable category |
| `registered_office_address` | Company node property | Street address + postcode |
| `incorporation_date` | Company node property `incorporation_date` | ISO-8601 |
| `officers.name` | Director properties | Create Person node for each director |
| `appointment.start_on` | Relationship property | Start date of board seat |
| `appointment.end_on` | Relationship property | End date (null if current) |

---

## Implementation Notes

### Industry Mapping from SIC Codes

```java
public Map<String, String> mapSicToIndustry(String sicCode) {
    // Example mappings
    // "47190" -> "Retail - Non-store sales"  
    // "64200" -> "Banking"
    // "70220" -> "Business management consulting"
    return sicIndustryMap.getOrDefault(sicCode, "Other Services");
}
```

### Relationship Detection

- **CEO link:** If director has role = "Managing Director" or is sole director
- **Board member:** All directors are board members
- **Politician-company relation:** Already exists from donation record; add bidirectional edge

### Address Normalization

```java
// Companies House returns multiple address components
public Address normalizeAddress(AddressData chAddress) {
    String street = chAddress.getPremiseNumber() + " " 
                  + chAddress.getBuildingName() + (chAddress.getStreetName() != null ? " " + chAddress.getStreetName() : "") 
                  + (chAddress.locality() != null ? ", " + chAddress.locality() : "")
                  + (chAddress.postTown() != null ? " " + chAddress.postTown() : "")
                  + " " + chAddress.postCode();
    
    return new Address(street, chAddress.getPostCode());
}
```

---

## Risks

- **Risk:** Companies House API has tiered access; free tier limited  
  - **Mitigation:** Start with company-specific lookups; expand to full history later
  
- **Risk:** Company name variations (trading names vs registered)  
  - **Metigation:** Store both if available; use registered name for graph canonical ID

- **Risk:** Politician is not a listed director but has informal influence  
  - **Acceptance:** Only record formal relationships; informal connections are community-submitted

---

## Definition of Done

- [ ] Service connects to Companies House API with valid credentials
- [ ] Company profiles created from donation-linked companies
- [ ] Director/officer nodes created and linked as board members
- [ ] Industry classification applied via SIC mapping
- [ ] Historical appointments captured where available (paid tier)
- [ ] Unit tests cover address parsing edge cases
- [ ] Integration test verifies end-to-end company ingestion

---

## Success Metrics

- Companies ingested: Target 10k+ unique companies from donation records
- Data completeness: >85% of required fields populated
- Industry mapping: >90% SIC codes mapped to readable industry names
- Duplicate detection: <3% flagged for manual review
