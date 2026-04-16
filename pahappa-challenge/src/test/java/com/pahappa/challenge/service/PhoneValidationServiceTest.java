package com.pahappa.challenge.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneValidationServiceTest {

    @InjectMocks
    private PhoneValidationService phoneValidationService;

    @Test
    void testValidPhoneNumber() {
        // Valid phone number: starts with 256, exactly 12 digits
        assertTrue(phoneValidationService.validatePhoneNumber("256751000355"));
    }

    @Test
    void testInvalidPhoneNumber_WrongPrefix() {
        // Invalid: doesn't start with 256
        assertFalse(phoneValidationService.validatePhoneNumber("255751000355"));
    }

    @Test
    void testInvalidPhoneNumber_WrongLength() {
        // Invalid: not exactly 12 digits
        assertFalse(phoneValidationService.validatePhoneNumber("25675100035"));
        assertFalse(phoneValidationService.validatePhoneNumber("2567510003555"));
    }

    @Test
    void testInvalidPhoneNumber_NonNumeric() {
        // Invalid: contains non-numeric characters
        assertFalse(phoneValidationService.validatePhoneNumber("25675100035a"));
    }

    @Test
    void testInvalidPhoneNumber_Null() {
        assertFalse(phoneValidationService.validatePhoneNumber(null));
    }

    @Test
    void testInvalidPhoneNumber_Empty() {
        assertFalse(phoneValidationService.validatePhoneNumber(""));
        assertFalse(phoneValidationService.validatePhoneNumber("   "));
    }

    @Test
    void testConstants() {
        assertEquals("256", phoneValidationService.getCountryCode());
        assertEquals(12, phoneValidationService.getRequiredLength());
    }
}