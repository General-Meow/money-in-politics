package com.webofpolitics.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * STORY-102: Politician Profile with Donor Summary Service Tests
 * 
 * Acceptance Criteria Coverage:
 * - [ ] Given politician ID, when visiting /profile/{id}, then displays politician photo, full name, title, party affiliation badge, current/former status indicator
 */
class PoliticianProfileServiceTest {

    private PoliticianProfileService service;

    @BeforeEach
    void setUp() {
        var donorRepo = new DonorRepository() {
            public List<Integer> findByPoliticianId(String politicianId) {
                return politicianId.equals("UK-CHS-LAB") ?
                    List.of(1001, 1002) :
                    new ArrayList<>();
            }

            public List<Donation> findByPoliticianIdAndDateRange(String politicianId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
                return new ArrayList<>();
            }

            public Optional<Donation> findById(Integer id) {
                if (id == null) return Optional.empty();
                var donations = Map.of(1001, new Donation(null, null, 0.0, null, false));
                return Optional.ofNullable(donations.get(id));
            }
        };

        var politicianRepo = new PoliticianRepository() {
            public PoliticianSummary findById(String id) {
                if (id.equals("UK-CHS-LAB")) {
                    return new PoliticianSummary(
                        "UK-CHS-LAB",
                        "Sir Keir Rodney Starmer",
                        "Keir Starmer",
                        "Labour",
                        "Holborn and St Pancras",
                        "https://example.com/starmer.jpg",
                        "Leader of the House of Commons",
                        false,
                        "2024-05-05"
                    );
                }
                return null;
            }

            public List<PoliticianSummary> findAll() {
                return new ArrayList<>();
            }
        };

        service = new PoliticianProfileService(donorRepo, politicianRepo);
    }

    @Nested
    @DisplayName("Profile Page - Display Header")
    class ProfilePageDisplaysHeader {

        @Test
        @DisplayName("Given politician ID, when visiting profile, then displays politician photo, full name, title, party affiliation badge, current/former status indicator")
        void givenPoliticianIdWhenVisitingProfile_thenDisplaysHeader() {
            var profile = service.getProfile("UK-CHS-LAB");

            assertNotNull(profile);
            assertTrue(profile.isPoliticianNotNull());
            
            PoliticianSummary politician = profile.getPolitician();
            assertNotNull(politician.getId());
            assertEquals("UK-CHS-LAB", politician.getId());
            
            assertNotNull(politician.getFullName());
            assertEquals("Sir Keir Rodney Starmer", politician.getFullName());
            
            assertNotNull(politician.getName());
            assertEquals("Keir Starmer", politician.getName());
            
            assertNotNull(politician.getParty());
            assertEquals("Labour", politician.getParty());
            
            assertNotNull(politician.getPhotoUrl());
            assertFalse(politician.isFormer());
        }

        @Test
        @DisplayName("Given non-existent politician ID, when visiting profile, then returns null")
        void givenNonExistentPoliticianId_thenReturnsNull() {
            var profile = service.getProfile("UK-NONEXISTENT");
            assertNull(profile);
        }

    }

}
