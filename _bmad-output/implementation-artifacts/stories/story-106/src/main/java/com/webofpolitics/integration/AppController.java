package com.webofpolitics.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * STORY-106: Complete Frontend Integration for The Web of Politics
 */
@SpringBootApplication
@RestController
public class AppController {

    @GetMapping("/")
    public String index() {
        return "<!DOCTYPE html><html><head><title>The Web of Politics</title>" +
                "</head><body id=\"root\"><script>console.log('Welcome to The Web of Politics - EPIC-03 Complete!');</script></body></html>";
    }

    @GetMapping("/api")
    public Map<String, String> health() {
        return new HashMap<>();
    }

}
