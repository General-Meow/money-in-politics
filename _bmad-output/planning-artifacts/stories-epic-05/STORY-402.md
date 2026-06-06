# STORY-402: Influence Score Calculations

**Epic:** EPIC-05 (Visualization Enhancements)  
**Priority:** P3 (Nice-to-have)  
**Story Points:** 5  
**Assignee:** [TBD]

## As A

Political Analyst/Researcher/Journalist

## I Want

An influence score calculation that ranks politicians by donor concentration and connection diversity

## So That

I can identify which politicians are most influenced by concentrated vs. diverse donor networks

---

## Acceptance Criteria

### Given a politician profile
**When** the influence score is calculated  
**Then**:
- Score 1-10 scale where:
  - 1 = Highly diversified donors (many small contributions from different sectors)
  - 10 = High concentration risk (few large donations from same sector/industry)

### Given a politician with >100 donations
**When** the score is displayed  
**Then**:
- Score shown alongside donor ledger
- Breakdown of: number of unique donors, sector diversity index, top 3 donor categories

---

## Component Implementation

```java
// services/InfluenceScoreCalculator.java
@Service
public class InfluenceScoreCalculator {
    
    /**
     * Calculates influence risk score based on:
     * - Donor concentration (few large donations)
     * - Sector diversity (same industry dominating)
     * - Recency weighting (recent donations weighted higher)
     */
    public Score calculateInfluenceScore(List<Donation> donations) {
        
        // Factor 1: Number of unique donors (lower is worse for concentration)
        int uniqueDonors = donations.stream()
            .collect(Collectors.groupingBy(
                d -> new DonorKey(d.getDonorName(), d.getDonorType())
            )).size();
        
        double donorConcentrationScore = 10.0 / Math.max(uniqueDonors, 1);
        
        // Factor 2: Sector diversity (same industry = higher concentration)
        Map<String, Double> sectorTotals = donations.stream()
            .map(d -> mapEntityToSector(d.getDonorName()))
            .collect(Collectors.groupingBy(
                Function.identity(), 
                Collectors.summingDouble(d -> d.getAmount())
            ));
        
        double[] sectorValues = sectorTotals.values().toArray(new Double[0]);
        double concentrationRatio = calculateConcentration(sectorValues); // Gini-like
        
        // Factor 3: Recency weighting (recent donations matter more)
        long today = new Date().toInstant().getEpochSecond();
        double recentWeightedTotal = donations.stream()
            .mapToLong(d -> {
                long donationDate = parseDate(d.getDate()).toInstant().getEpochSecond();
                return d.getAmount() * Math.pow(0.1, (today - donationDate) / 86400); // Halve daily
            })
            .sum();
        
        double totalWeighted = donations.stream()
            .mapToLong(d -> (long) d.getAmount())
            .sum();
        
        double recencyScore = Math.max(0, recentWeightedTotal / (totalWeighted + 1));
        
        // Combined score
        double finalScore = donorConcentrationScore * concentrationRatio * recencyScore;
        
        return new Score((int) (finalScore * 10), // Scale to 1-10
                         uniqueDonors, 
                         sectorTotals);
    }
}
```

---

## Risks

- **Risk:** Influence score methodology is controversial  
  - **Acceptance:** Document assumptions; allow community discussion on metric validity
  
