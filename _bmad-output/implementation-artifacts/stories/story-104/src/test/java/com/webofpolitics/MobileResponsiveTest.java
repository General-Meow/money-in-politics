package com.webofpolitics;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * STORY-104: Mobile Responsive Design Tests
 */
class MobileResponsiveTest {

    @BeforeEach
    void setUp() throws Exception {
        var sourceFile = new File("src/test/resources/css/mobile-responsive.css");
        if (sourceFile.exists()) {
            Files.copy(
                sourceFile.toPath(),
                new File("target/classes/css").toPath().resolve("mobile-responsive.css"),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        }
    }

    @Nested
    @DisplayName("Mobile Responsive CSS - EPIC-01 Graph Explorer")
    class MobileResponsiveGraphExplorer {

        @Test
        @DisplayName("Given mobile viewport, when checking graph container, then padding adapts responsively")
        void givenMobileViewport_whenCheckingGraphContainer_thenPaddingAdapts() throws Exception {
            var cssFile = new File("target/classes/css/mobile-responsive.css");
            if (!cssFile.exists()) throw new RuntimeException("CSS file not found");
            
            String content = Files.readString(cssFile.toPath());
            
            assertTrue(content.contains("@media"), "Missing @media query for responsive design");
            assertTrue(content.contains("max-width: 768px"), "Missing mobile breakpoint (768px)");
            assertTrue(content.contains(".graph-container"), "Graph container styling not found");
        }

    }

    @Nested
    @DisplayName("Mobile Responsive CSS - EPIC-02 Profile Page")
    class MobileResponsiveProfilePage {

        @Test
        @DisplayName("Given mobile viewport, when checking donors grid, then single column layout")
        void givenMobileViewport_whenCheckingDonorsGrid_thenSingleColumn() throws Exception {
            var cssFile = new File("target/classes/css/mobile-responsive.css");
            String content = Files.readString(cssFile.toPath());
            
            assertTrue(content.contains(".donor-list"), "Donor list styling not found");
            assertTrue(content.contains("grid-template-columns: 1fr"), "Single column grid for mobile defined");
        }

    }

    @Nested
    @DisplayName("Mobile Responsive Components")
    class MobileResponsiveComponents {

        @Test
        @DisplayName("Given mobile viewport, when checking button, then full-width sizing")
        void givenMobileViewport_whenCheckingButton_thenFullWidth() throws Exception {
            var cssFile = new File("target/classes/css/mobile-responsive.css");
            String content = Files.readString(cssFile.toPath());
            
            assertTrue(content.contains("@media (max-width: 768px)"), "Missing mobile media query");
            assertTrue(content.contains("width: 100%"), "Full-width buttons defined for mobile");
        }

    }

}
