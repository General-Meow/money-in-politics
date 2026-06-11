package com.webofpolitics.api;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository interface for donor relationships in Neo4j graph.
 */
public interface DonorRepository {

    /**
     * Get all donor IDs associated with a politician.
     */
    List<Integer> findByPoliticianId(String politicianId);

    /**
     * Get donations from last 12 months.
     */
    List<Donation> findByPoliticianIdAndDateRange(String politicianId, LocalDate startDate, LocalDate endDate);

    /**
     * Get donation by ID.
     */
    Optional<Donation> findById(Integer id);

}

/**
 * Mock implementation for testing (replace with Neo4j queries in production).
 */
class DonorRepositoryImpl implements DonorRepository {
    
    private final Map<String, List<Integer>> politicianDonors = new HashMap<>();
    private final Map<Integer, Donation> donationsMap = new HashMap<>();

    public DonorRepositoryImpl() {
        populateTestData();
    }

    @Override
    public List<Integer> findByPoliticianId(String politicianId) {
        return Optional.ofNullable(politicianDonors.get(politicianId))
            .orElse(new ArrayList<>());
    }

    @Override
    public List<Donation> findByPoliticianIdAndDateRange(String politicianId, LocalDate startDate, LocalDate endDate) {
        var donors = findByPoliticianId(politicianId);
        return donors.stream()
            .map(this::getDonationById)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Donation> findById(Integer id) {
        return Optional.ofNullable(donationsMap.get(id));
    }

    private Donation getDonationById(Integer donorId) {
        var politicianDonors = this.politicianDonors;
        List<Integer> ids = politicianDonors.get("UK-CHS-LAB");
        Integer donationId = null;
        for (Integer i : ids) {
            if (i == donorId) {
                donationId = i;
                break;
            }
        }
        return Optional.ofNullable(donationId).map(i -> donationsMap.get(i)).orElse(null);
    }

    private void populateTestData() {
        politicianDonors.put("UK-CHS-LAB", List.of(1001, 1002, 1003));
        donationsMap.put(1001, new Donation(20250115, 10000, 3, "COMP-BL-001", true));
        donationsMap.put(1002, new Donation(20240920, 8000, 2, "IND-JD-001", false));
    }
}
