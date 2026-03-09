package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LogsDemoService {

    private static final Logger logger = LoggerFactory.getLogger(LogsDemoService.class);

    /**
     * Runs a structured logging demo across all 5 SLF4J log levels, using MDC to attach
     * a correlation ID to every log line emitted during the request.
     *
     * @param fail when {@code true} the service simulates an error path (WARN + ERROR);
     *             otherwise it follows the happy path.
     * @return a map describing every log entry that was emitted, suitable for JSON serialisation.
     */
    public Map<String, Object> runDemo(boolean fail) {
        String requestId = UUID.randomUUID().toString();
        List<Map<String, String>> loggedEvents = new ArrayList<>();

        MDC.put("requestId", requestId);
        MDC.put("endpoint", "/api/logs-demo");

        try {
            // ── Step 1: TRACE ────────────────────────────────────────────────────────────
            MDC.put("step", "1");
            logger.trace("[step=1] TRACE – entering runDemo, fail={}", fail);
            loggedEvents.add(entry("TRACE", "step=1", "Entering runDemo, fail=" + fail));

            // ── Step 2: DEBUG ────────────────────────────────────────────────────────────
            MDC.put("step", "2");
            logger.debug("[step=2] DEBUG – resolved requestId={}", requestId);
            loggedEvents.add(entry("DEBUG", "step=2", "Resolved requestId=" + requestId));

            // ── Step 3: INFO ─────────────────────────────────────────────────────────────
            MDC.put("step", "3");
            logger.info("[step=3] INFO  – processing request, endpoint=/api/logs-demo, requestId={}", requestId);
            loggedEvents.add(entry("INFO", "step=3", "Processing request at /api/logs-demo"));

            if (fail) {
                // ── Step 4 (warn path): WARN ─────────────────────────────────────────────
                MDC.put("step", "4");
                logger.warn("[step=4] WARN  – ?fail=true detected; simulating degraded operation, requestId={}", requestId);
                loggedEvents.add(entry("WARN", "step=4", "?fail=true detected; simulating degraded operation"));

                // ── Step 5 (warn path): ERROR ────────────────────────────────────────────
                MDC.put("step", "5");
                RuntimeException simulatedEx = new RuntimeException("Simulated failure triggered by ?fail=true");
                logger.error("[step=5] ERROR – operation failed for requestId={}: {}", requestId, simulatedEx.getMessage(), simulatedEx);
                loggedEvents.add(entry("ERROR", "step=5", "Operation failed: " + simulatedEx.getMessage()));

            } else {
                // ── Step 4 (success path): INFO ──────────────────────────────────────────
                MDC.put("step", "4");
                logger.info("[step=4] INFO  – business logic completed successfully, requestId={}", requestId);
                loggedEvents.add(entry("INFO", "step=4", "Business logic completed successfully"));

                // ── Step 5 (success path): DEBUG ─────────────────────────────────────────
                MDC.put("step", "5");
                logger.debug("[step=5] DEBUG – preparing success response, requestId={}", requestId);
                loggedEvents.add(entry("DEBUG", "step=5", "Preparing success response"));
            }

        } finally {
            MDC.clear();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("requestId", requestId);
        result.put("endpoint", "/api/logs-demo");
        result.put("path", fail ? "warn/error" : "success");
        result.put("note", "TRACE may be suppressed unless logging.level.com.example.demo=TRACE");
        result.put("loggedEvents", loggedEvents);
        return result;
    }

    private static Map<String, String> entry(String level, String step, String message) {
        Map<String, String> e = new LinkedHashMap<>();
        e.put("level", level);
        e.put("step", step);
        e.put("message", message);
        return e;
    }
}
