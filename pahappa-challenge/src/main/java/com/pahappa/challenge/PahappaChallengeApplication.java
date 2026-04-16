package com.pahappa.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application for Pahappa Programming Challenge
 * Provides REST API for phone validation and SMS sending
 *
 * @author Pahappa Challenge Team
 * @version 1.0
 */
@SpringBootApplication
public class PahappaChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PahappaChallengeApplication.class, args);
    }
}