package com.pahappa.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for phone validation and SMS sending
 */
@Schema(description = "Phone validation and SMS request")
public class PhoneValidationRequest {

    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String fullName;

    @Schema(description = "Phone number to validate and send SMS to", example = "256751000355", required = true)
    private String phoneNumber;

    public PhoneValidationRequest() {}

    public PhoneValidationRequest(String fullName, String phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}