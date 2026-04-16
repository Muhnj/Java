package com.pahappa.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for validating Ugandan phone numbers
 */
@Service
public class PhoneValidationService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneValidationService.class);

    // Phone validation constants
    private static final String COUNTRY_CODE = "256";
    private static final int REQUIRED_LENGTH = 12;

    /**
     * Validates a Ugandan phone number
     * @param phone the phone number to validate
     * @return true if valid, false otherwise
     */
    public boolean validatePhoneNumber(String phone) {
        logger.debug("Validating phone number: {}", phone);

        if (phone == null || phone.trim().isEmpty()) {
            logger.warn("Phone number validation failed: null or empty");
            return false;
        }

        String trimmedPhone = phone.trim();

        // Must start with country code
        if (!trimmedPhone.startsWith(COUNTRY_CODE)) {
            logger.warn("Phone number validation failed: does not start with {}", COUNTRY_CODE);
            return false;
        }

        // Must be exactly required length
        if (trimmedPhone.length() != REQUIRED_LENGTH) {
            logger.warn("Phone number validation failed: length {} is not {}", trimmedPhone.length(), REQUIRED_LENGTH);
            return false;
        }

        // Must contain only numeric characters
        if (!trimmedPhone.chars().allMatch(Character::isDigit)) {
            logger.warn("Phone number validation failed: contains non-numeric characters");
            return false;
        }

        logger.info("Phone number validation successful: {}", trimmedPhone);
        return true;
    }

    /**
     * Gets the country code used for validation
     * @return the country code
     */
    public String getCountryCode() {
        return COUNTRY_CODE;
    }

    /**
     * Gets the required length for valid phone numbers
     * @return the required length
     */
    public int getRequiredLength() {
        return REQUIRED_LENGTH;
    }
}