package com.pahappa.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for phone validation and SMS sending results
 */
@Schema(description = "Phone validation and SMS response")
public class PhoneValidationResponse {

    @Schema(description = "Name that was entered", example = "John Doe")
    private String nameEntered;

    @Schema(description = "Phone number that was entered", example = "256751000355")
    private String phoneNumberEntered;

    @Schema(description = "Validation result", example = "Valid", allowableValues = {"Valid", "Invalid"})
    private String validationResult;

    @Schema(description = "Whether the message was sent", example = "true")
    private boolean messageSent;

    @Schema(description = "Error message if any", example = "None")
    private String errorMessage;

    @Schema(description = "Full message that was sent (if applicable)", example = "Hello John Doe (256751000355), welcome to the Pahappa Programming Challenge Hackathon")
    private String sentMessage;

    public PhoneValidationResponse() {}

    public PhoneValidationResponse(String nameEntered, String phoneNumberEntered,
                                 String validationResult, boolean messageSent,
                                 String errorMessage, String sentMessage) {
        this.nameEntered = nameEntered;
        this.phoneNumberEntered = phoneNumberEntered;
        this.validationResult = validationResult;
        this.messageSent = messageSent;
        this.errorMessage = errorMessage;
        this.sentMessage = sentMessage;
    }

    // Getters and setters
    public String getNameEntered() {
        return nameEntered;
    }

    public void setNameEntered(String nameEntered) {
        this.nameEntered = nameEntered;
    }

    public String getPhoneNumberEntered() {
        return phoneNumberEntered;
    }

    public void setPhoneNumberEntered(String phoneNumberEntered) {
        this.phoneNumberEntered = phoneNumberEntered;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }

    public boolean isMessageSent() {
        return messageSent;
    }

    public void setMessageSent(boolean messageSent) {
        this.messageSent = messageSent;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(String sentMessage) {
        this.sentMessage = sentMessage;
    }
}