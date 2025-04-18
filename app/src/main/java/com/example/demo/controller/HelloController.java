package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        logger.info("Received request to /api/hello endpoint");

        // Add artificial delay to make traces more visible
        logger.info("Adding artificial delay of 500ms");
        Thread.sleep(500);

        logger.info("Returning response from /api/hello endpoint");
        return "Hello from Spring Boot with OpenTelemetry!";
    }
}