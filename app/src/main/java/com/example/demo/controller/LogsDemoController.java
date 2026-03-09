package com.example.demo.controller;

import com.example.demo.service.LogsDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Exposes GET /api/logs-demo to demonstrate all 5 SLF4J log levels with MDC context.
 *
 * <p>Usage:
 * <ul>
 *   <li>{@code GET /api/logs-demo}           – happy path (TRACE, DEBUG, INFO x2, DEBUG)</li>
 *   <li>{@code GET /api/logs-demo?fail=true}  – warn/error path (TRACE, DEBUG, INFO, WARN, ERROR)</li>
 * </ul>
 *
 * <p>The OTel Java agent (attached via JAVA_TOOL_OPTIONS) auto-instruments Logback/SLF4J
 * and exports every log record to the OTLP collector configured in docker-compose.yml.
 */
@RestController
@RequestMapping("/api")
public class LogsDemoController {

    private static final Logger logger = LoggerFactory.getLogger(LogsDemoController.class);

    private final LogsDemoService logsDemoService;

    public LogsDemoController(LogsDemoService logsDemoService) {
        this.logsDemoService = logsDemoService;
    }

    @GetMapping("/logs-demo")
    public ResponseEntity<Map<String, Object>> logsDemo(
            @RequestParam(name = "fail", defaultValue = "false") boolean fail) {

        logger.info("GET /api/logs-demo invoked, fail={}", fail);

        Map<String, Object> result = logsDemoService.runDemo(fail);

        logger.info("GET /api/logs-demo completed, path={}", result.get("path"));

        return ResponseEntity.ok(result);
    }
}
