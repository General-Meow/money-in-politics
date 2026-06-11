package com.webofpolitics.cicd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * STORY-109: CI/CD Pipeline Configuration for Complete EPIC Deployment
 */
@SpringBootApplication
@RestController
public class CicdConfiguration {

    @GetMapping("/")
    public String index() {
        return "<!DOCTYPE html><html>" +
                "<head><title>CI/CD Pipeline - The Web of Politics</title></head>" +
                "<body id=\"root\">";
    }

    @GetMapping("/api")
    public Map<String, String> health() {
        return new HashMap<>();
    }

}
