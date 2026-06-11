package com.webofpolitics.graph.explorer;

import java.nio.charset.StandardCharsets;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webofpolitics.graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * STORY-103: Graph Explorer REST Controller
 */
@RestController
@RequestMapping("/api/graph")
public class GraphExplorerRestController {

    private final com.webofpolitics.graph.GraphExplorerService explorerService;

    public GraphExplorerRestController(com.webofpolitics.graph.GraphExplorerService explorerService) {
        this.explorerService = explorerService;
    }

    /**
     * GET /api/graph/{politicianId}
     */
    @GetMapping("/{politicianId}")
    public ResponseEntity<?> getGraphData(@PathVariable String politicianId) {
        var nodes = explorerService.fetchConnections(politicianId, 2);
        return ResponseEntity.ok(Map.of(
            "nodes", new ArrayList<>(nodes),
            "links", new ArrayList<>(),
            "filters", Map.of("all", true, "politician", false, "company", false, "party", false)
        ));
    }

    /**
     * GET /api/graph/{politicianId}/html
     */
    @GetMapping("/{politicianId}/html")
    public ResponseEntity<Resource> getGraphPage(@PathVariable String politicianId) {
        var resource = new org.springframework.core.io.ClassPathResource(
            "templates/graph-explorer.html", 
            GraphExplorerRestController.class.getClassLoader()
        );
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE).body(resource);
    }

}
