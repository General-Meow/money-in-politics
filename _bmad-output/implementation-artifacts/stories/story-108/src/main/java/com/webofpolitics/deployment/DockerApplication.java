package com.webofpolitics.deployment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class DockerApplication {

    @GetMapping("/")
    public String index() {
        return "<!DOCTYPE html><html><head>" +
                "<title>The Web of Politics - EPIC Complete</title></head>" +
                "<body id=\"root\">";
    }

    @GetMapping("/api")
    public Map<String, String> health() {
        return new HashMap<>();
    }

}
