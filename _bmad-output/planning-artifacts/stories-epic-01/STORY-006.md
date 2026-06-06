# STORY-006: Historical Data Backfill (2005+)

**Epic:** EPIC-01 (Graph Engine and Data Ingestion Pipeline)  
**Priority:** P0  
**Story Points:** 13  
**Assignee:** [TBD]

## As A

Data Engineer

## I Want

Automated historical data ingestion process that backfills all records from 2005 onwards

## So That

The Web of Politics launches with comprehensive historical context, not just current state

---

## Acceptance Criteria

### Given the graph engine is ready
**When** the backfill job runs  
**Then** for each time period (2005, 2006, ..., present):
- Historical MP profiles are ingested for that period
- Donation records are captured where disclosed
- Company connections exist for historical roles

### Given different data availability per year
**When the backfill runs  
**Then** data completeness is reported by year:
- Year 2015-present: API or full coverage (>95% complete)
- Year 2010-2014: Partial coverage via scraper fallback (70-85% complete)
- Year 2005-2009: Limited coverage from available sources (40-60% complete)

### When ingestion completes
**Then** a dataset manifest is generated with:
- Total records per entity type
- Coverage percentages by year and source
- Missing data gaps documented

---

## Backfill Strategy

### Time Period Planning

| Period | Recommended Data Source | Expected Coverage | Notes |
|--------|------------------------|-------------------|-------|
| 2015-Present | Parliament.uk API + EC API | >95% | Full coverage with APIs |
| 2010-2014 | API + Historical Scrapers | 70-85% | Mixed sources; accept gaps |
| 2005-2009 | Scrapers only | 40-60% | Early disclosure regime; partial data |

### Historical Data Limitations to Accept

**Donations:** Pre-2010 disclosures may be incomplete due to:
- Weaker disclosure requirements before 2010 reforms
- Different filing formats on EC website
- Some donations reported as "undisclosed" historically

**Voting Records:** Before parliamentary committee system improved, some votes not publicly tracked

**Board Memberships:** Company director data from Companies House pre-2010 may be incomplete

---

## Implementation Approach

### Batch Processing Strategy

```java
// BackfillService.java
@Service
public class HistoricalBackfillService {
    
    private final PoliticianIngestionService politicianService;
    private final DonationIngestionService donationService;
    private final CompanyIngestionService companyService;
    
    @Scheduled(fixedDelayString = "${backfill.delay.minutes:60}")  // Hourly batches
    public void runHistoricalBackfill() {
        List<Year> yearsToProcess = getYearsSince(2005);
        
        for (Year year : yearsToProcess) {
            log.info("Starting backfill for year: {}", year);
            
            // Check if already complete for this year
            if (isYearComplete(year)) {
                continue;
            }
            
            try {
                YearStatus status = ingestHistoricalData(year);
                
                // Update progress tracking
                updateBackfillProgress(status.getRecordsProcessed(), 
                                       status.getYearsCovered());
                   
                if (status.hasErrors()) {
                    alertOnPartialYearCoverage(status.getErrorSummary());
                }
            } catch (Exception e) {
                log.error("Failed to ingest year {}: {}", year, e.getMessage());
                updateBackfillStatusForFailure(year);
            }
        }
    }
}
```

### Progress Tracking and Manifest Generation

```json
{
  "backfill_manifest": {
    "generated_at": "2026-06-06T19:30:00Z",
    "coverage_by_year": [
      {"year": 2024, "records_ingested": 523, "completeness_pct": 98},
      {"year": 2023, "records_ingested": 487, "completeness_pct": 96},
      ...
      {"year": 2009, "records_ingested": 156, "completeness_pct": 45}
    ],
    "entity_counts": {
      "politicians": 892,
      "companies": 4521,
      "donations": 125430,
      "relationships": 78234
    },
    "data_sources_used": [
      {"name": "Parliament.uk API", "records_from_source": 452},
      {"name": "Electoral Commission API", "records_from_source": 9820},
      {"name": "Companies House API", "records_from_source": 1234},
      {"name": "Historical scraper", "records_from_source": 4523}
    ]
  }
}
```

---

## Monitoring and Alerting

### Dashboard Metrics to Display

- **Backfill Progress:** Current year being processed, ETA for completion
- **Coverage Heatmap:** Visual calendar showing data availability by year
- **Gap Identification:** Years or quarters with <50% expected coverage
- **Data Quality Alerts:** Years flagged for manual review due to high null field rates

### Alert Conditions

```yaml
# alerting.rules.yml
groups:
  - name: the-web-of-politics-alerts
    rules:
      - alert: HistoricalBackfillIncomplete
        expr: backfill_year_completeness < 50
        for: 1h
        labels:
          severity: warning
        annotations:
          summary: "Historical year has <50% expected data coverage"
          description: "{{ $value }}% of {{ $labels.year }} records have been ingested"
      - alert: BackfillFailure
        expr: up[5m] == 0 and process_start_time_seconds > 0
        for: 1h
        labels:
          severity: critical
        annotations:
          summary: "Historical backfill job is down"
```

---

## Risks

- **Risk:** Historical data is significantly incomplete  
  - **Acceptance:** Document gaps; users can still explore available data

- **Risk:** Legal changes affect historical disclosures (retrospective compliance)  
  - **Mitigation:** Archive original sources; keep raw scraped HTML for audit trail

- **Risk:** Backfill takes longer than anticipated  
  - **Mitigation:** Accept partial backfill at launch; commit to ongoing improvement

---

## Definition of Done

- [ ] Historical ingestion pipeline processes each year independently
- [ ] Progress tracking captures records processed per entity type and source
- [ ] Completeness metrics calculated and displayed per year
- [ ] Backfill manifest generated after completion (or partial completion)
- [ ] Alerting configured for years with <50% coverage
- [ ] Documentation includes known data gaps by historical period
- [ ] Unit tests cover year-based filtering logic

---

## Success Metrics

- Historical records ingested: Target 200k+ donation records from 2005-present
- Completeness trend: Year-over-year increase in data availability
- Data provenance: Every record traceable to source (API vs scraper)
- Performance: Backfill completes within scheduled maintenance window
