import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Phone Validation Challenge Application
 * Validates Ugandan phone numbers and sends personalized SMS messages via EgoSMS API
 *
 * @author Senior Developer
 * @version 1.0
 */
public class PhoneValidationChallenge {

    // API Configuration Constants
    private static final String API_URL = "https://comms.egosms.co/api/v1/json/";
    private static final String API_USERNAME = "pahappahackathon";
    private static final String API_PASSWORD = "pahappahackathon";
    private static final String SENDER_ID = "Pahappa";
    private static final String TARGET_PHONE_NUMBER = "256751000355";

    // Phone validation constants
    private static final String COUNTRY_CODE = "256";
    private static final int REQUIRED_LENGTH = 12;

    /**
     * Main entry point of the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Step 1: Accept User Input
            System.out.print("Enter your full name: ");
            String fullName = scanner.nextLine().trim();

            System.out.print("Enter your phone number: ");
            String phoneNumber = scanner.nextLine().trim();

            // Step 2: Validate the Phone Number
            boolean isValid = validatePhoneNumber(phoneNumber);
            String validationResult = isValid ? "Valid" : "Invalid";

            // Step 3: Send Personalized Message (Only to specific number)
            boolean messageSent = false;
            String errorMessage = isValid ? "None" : "Invalid phone number format";

            if (isValid && TARGET_PHONE_NUMBER.equals(phoneNumber)) {
                String message = String.format("Hello %s (%s), welcome to the Pahappa Programming Challenge Hackathon",
                                             fullName, phoneNumber);
                messageSent = sendSMS(phoneNumber, message);
                if (messageSent) {
                    System.out.println("Sending to " + phoneNumber + ": " + message);
                } else {
                    errorMessage = "Failed to send SMS via API";
                }
            }

            // Step 4: Display Processing Summary
            displayProcessingSummary(fullName, phoneNumber, validationResult, messageSent, errorMessage);

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Validates a Ugandan phone number
     * @param phone the phone number to validate
     * @return true if valid, false otherwise
     */
    private static boolean validatePhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }

        // Must start with country code
        if (!phone.startsWith(COUNTRY_CODE)) {
            return false;
        }

        // Must be exactly required length
        if (phone.length() != REQUIRED_LENGTH) {
            return false;
        }

        // Must contain only numeric characters
        return phone.chars().allMatch(Character::isDigit);
    }

    /**
     * Sends an SMS message via EgoSMS API
     * @param phoneNumber the recipient's phone number
     * @param message the message content
     * @return true if sent successfully, false otherwise
     */
    private static boolean sendSMS(String phoneNumber, String message) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(API_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000); // 10 seconds
            connection.setReadTimeout(10000);    // 10 seconds

            String jsonPayload = buildJsonPayload(phoneNumber, message);

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = readResponse(connection);
                return isSuccessfulResponse(response);
            } else {
                System.err.println("API request failed with response code: " + responseCode);
                return false;
            }

        } catch (IOException e) {
            System.err.println("Error sending SMS: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Builds the JSON payload for the SMS API request
     * @param phoneNumber recipient phone number
     * @param message message content
     * @return JSON string
     */
    private static String buildJsonPayload(String phoneNumber, String message) {
        return String.format(
            "{\"method\":\"SendSms\",\"userdata\":{\"username\":\"%s\",\"password\":\"%s\"},\"msgdata\":[{\"number\":\"%s\",\"message\":\"%s\",\"senderid\":\"%s\",\"priority\":\"0\"}]}",
            API_USERNAME, API_PASSWORD, phoneNumber, escapeJsonString(message), SENDER_ID
        );
    }

    /**
     * Escapes special characters in JSON string
     * @param text the text to escape
     * @return escaped text
     */
    private static String escapeJsonString(String text) {
        return text.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    /**
     * Reads the response from the HTTP connection
     * @param connection the HTTP connection
     * @return response string
     * @throws IOException if reading fails
     */
    private static String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    /**
     * Checks if the API response indicates success
     * @param response the API response
     * @return true if successful
     */
    private static boolean isSuccessfulResponse(String response) {
        if (response == null) return false;
        String lowerResponse = response.toLowerCase();
        return lowerResponse.contains("\"status\":\"success\"") ||
               lowerResponse.contains("success") ||
               lowerResponse.contains("\"error\":false");
    }

    /**
     * Displays the processing summary
     * @param name entered name
     * @param phone entered phone
     * @param validation validation result
     * @param sent whether message was sent
     * @param error error message
     */
    private static void displayProcessingSummary(String name, String phone, String validation,
                                               boolean sent, String error) {
        System.out.println("========== PROCESSING SUMMARY ==========");
        System.out.println("Name entered: " + name);
        System.out.println("Phone number entered: " + phone);
        System.out.println("Validation result: " + validation);
        System.out.println("Message sent: " + (sent ? "Yes" : "No"));
        System.out.println("Error (if any): " + error);
        System.out.println("========================================");
    }
}