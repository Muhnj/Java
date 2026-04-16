package com.pahappa.challenge.controller;

import com.pahappa.challenge.dto.PhoneValidationRequest;
import com.pahappa.challenge.dto.PhoneValidationResponse;
import com.pahappa.challenge.service.PhoneValidationService;
import com.pahappa.challenge.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Phone Validation and SMS functionality
 */
@RestController
@RequestMapping("/api/v1/phone-validation")
@Tag(name = "Phone Validation", description = "API for validating phone numbers and sending SMS messages")
public class PhoneValidationController {

    private static final Logger logger = LoggerFactory.getLogger(PhoneValidationController.class);

    @Autowired
    private PhoneValidationService phoneValidationService;

    @Autowired
    private SmsService smsService;

    /**
     * Validates a phone number and sends SMS if valid and matches target number
     * @param request the phone validation request
     * @return the validation response
     */
    @PostMapping("/validate-and-send")
    @Operation(summary = "Validate phone number and send SMS",
               description = "Validates a Ugandan phone number and sends a personalized SMS message if valid and matches the target number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Phone validation completed",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = PhoneValidationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<PhoneValidationResponse> validateAndSendSms(@RequestBody PhoneValidationRequest request) {
        try {
            logger.info("Processing phone validation request for: {}", request.getFullName());

            String fullName = request.getFullName() != null ? request.getFullName().trim() : "";
            String phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber().trim() : "";

            // Validate input
            if (fullName.isEmpty() || phoneNumber.isEmpty()) {
                PhoneValidationResponse response = new PhoneValidationResponse(
                    fullName, phoneNumber, "Invalid", false,
                    "Name and phone number are required", null
                );
                return ResponseEntity.badRequest().body(response);
            }

            // Step 2: Validate the Phone Number
            boolean isValid = phoneValidationService.validatePhoneNumber(phoneNumber);
            String validationResult = isValid ? "Valid" : "Invalid";

            // Step 3: Send Personalized Message (Only to specific number)
            boolean messageSent = false;
            String errorMessage = isValid ? "None" : "Invalid phone number format";
            String sentMessage = null;

            if (isValid) {
                String message = String.format("Hello %s (%s), welcome to the Pahappa Programming Challenge Hackathon",
                                             fullName, phoneNumber);
                messageSent = smsService.sendSms(phoneNumber, message);
                if (messageSent) {
                    sentMessage = message;
                    logger.info("SMS sent successfully to: {}", phoneNumber);
                } else {
                    errorMessage = "Failed to send SMS via API";
                    logger.warn("Failed to send SMS to: {}", phoneNumber);
                }
            }

            PhoneValidationResponse response = new PhoneValidationResponse(
                fullName, phoneNumber, validationResult, messageSent, errorMessage, sentMessage
            );

            logger.info("Phone validation completed for {}: {}", fullName, validationResult);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Unexpected error during phone validation", e);
            PhoneValidationResponse response = new PhoneValidationResponse(
                request.getFullName(), request.getPhoneNumber(), "Invalid", false,
                "Internal server error: " + e.getMessage(), null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Validates a phone number without sending SMS
     * @param phoneNumber the phone number to validate
     * @return validation result
     */
    @GetMapping("/validate/{phoneNumber}")
    @Operation(summary = "Validate phone number only",
               description = "Validates a Ugandan phone number without sending SMS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Validation completed",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = PhoneValidationResponse.class)))
    })
    public ResponseEntity<PhoneValidationResponse> validatePhoneOnly(@PathVariable String phoneNumber) {
        boolean isValid = phoneValidationService.validatePhoneNumber(phoneNumber);
        String validationResult = isValid ? "Valid" : "Invalid";
        String errorMessage = isValid ? "None" : "Invalid phone number format";

        PhoneValidationResponse response = new PhoneValidationResponse(
            "", phoneNumber, validationResult, false, errorMessage, null
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     * @return service status
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns the service health status")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"UP\",\"service\":\"Pahappa Challenge API\"}");
    }
}