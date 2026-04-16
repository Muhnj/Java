import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Accept User Input
        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        // Step 2: Validate the Phone Number
        boolean isValid = validatePhoneNumber(phoneNumber);
        String validationResult = isValid ? "Valid" : "Invalid";

        // Step 3: Send Personalized Message (Only to specific number)
        boolean messageSent = false;
        if (isValid && phoneNumber.equals("256751000355")) {
            String message = "Hello " + fullName + " (" + phoneNumber + "), welcome to the Pahappa Programming Challenge Hackathon";
            messageSent = sendSMS(phoneNumber, message);
            if (messageSent) {
                System.out.println("Sending to " + phoneNumber + ": " + message);
            }
        }

        // Step 4: Display Processing Summary
        System.out.println("========== PROCESSING SUMMARY ==========");
        System.out.println("Name entered: " + fullName);
        System.out.println("Phone number entered: " + phoneNumber);
        System.out.println("Validation result: " + validationResult);
        System.out.println("Message sent: " + (messageSent ? "Yes" : "No"));
        System.out.println("Error (if any): " + (isValid ? "None" : "Invalid phone number format"));
        System.out.println("========================================");

        scanner.close();
    }

    private static boolean sendSMS(String phone, String message) {
        try {
            URL url = new URL("https://comms.egosms.co/api/v1/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = "{"
                + "\"method\":\"SendSms\","
                + "\"userdata\":{\"username\":\"pahappahackathon\",\"password\":\"pahappahackathon\"},"
                + "\"msgdata\":["
                + "{\"number\":\"" + phone + "\",\"message\":\"" + message.replace("\"", "\\\"") + "\",\"senderid\":\"Pahappa\",\"priority\":\"0\"}"
                + "]"
                + "}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // Check if response indicates success
                String resp = response.toString();
                if (resp.contains("\"status\":\"success\"") || resp.contains("success")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean validatePhoneNumber(String phone) {
        // Must start with 256
        if (!phone.startsWith("256")) {
            return false;
        }
        // Must be exactly 12 digits
        if (phone.length() != 12) {
            return false;
        }
        // Must contain only numeric characters
        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
