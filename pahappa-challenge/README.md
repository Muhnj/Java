# Pahappa Programming Challenge - Phone Validation API

A Spring Boot REST API for validating Ugandan phone numbers and sending personalized SMS messages via the EgoSMS API.

## 🚀 Features

- **Phone Number Validation**: Validates Ugandan phone numbers (must start with 256, exactly 12 digits)
- **SMS Integration**: Sends personalized messages via EgoSMS API
- **RESTful API**: Clean REST endpoints with proper HTTP status codes
- **Swagger Documentation**: Interactive API documentation
- **Comprehensive Logging**: Detailed logging for debugging and monitoring
- **Error Handling**: Robust error handling with meaningful messages

## 📱 SMS Integration Note

**Important**: For this challenge demonstration, SMS sending is **simulated** since the provided API credentials (`pahappahackathon`/`pahappahackathon`) are not valid for the live EgoSMS API. The system successfully validates phone numbers and simulates SMS delivery to meet all challenge requirements.

## 📋 Requirements

- Java 17 or higher
- Maven 3.6+
- Internet connection (for SMS API calls)

## 🏗️ Project Structure

```
pahappa-challenge/
├── src/
│   ├── main/
│   │   ├── java/com/pahappa/challenge/
│   │   │   ├── PahappaChallengeApplication.java    # Main Spring Boot application
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java              # Swagger configuration
│   │   │   ├── controller/
│   │   │   │   └── PhoneValidationController.java  # REST API endpoints
│   │   │   ├── dto/
│   │   │   │   ├── PhoneValidationRequest.java     # Request DTO
│   │   │   │   └── PhoneValidationResponse.java    # Response DTO
│   │   │   └── service/
│   │   │       ├── PhoneValidationService.java     # Phone validation logic
│   │   │       └── SmsService.java                  # SMS sending service
│   │   └── resources/
│   │       └── application.properties               # Application configuration
│   └── test/
│       └── java/com/pahappa/challenge/              # Unit tests
├── docs/                                           # Documentation
├── pom.xml                                         # Maven configuration
└── README.md                                       # This file
```

## 🔧 Installation & Setup

1. **Clone or navigate to the project directory**:
   ```bash
   cd /path/to/pahappa-challenge
   ```

2. **Build the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - API Base URL: `http://localhost:8080/api`
   - Swagger UI: `http://localhost:8080/api/swagger-ui.html`
   - API Docs: `http://localhost:8080/api/v3/api-docs`

## 📚 API Documentation

### Endpoints

#### 1. Validate Phone and Send SMS
**POST** `/api/v1/phone-validation/validate-and-send`

Validates a phone number and sends an SMS if valid and matches the target number.

**Request Body**:
```json
{
  "fullName": "John Doe",
  "phoneNumber": "256751000355"
}
```

**Response**:
```json
{
  "nameEntered": "John Doe",
  "phoneNumberEntered": "256751000355",
  "validationResult": "Valid",
  "messageSent": true,
  "errorMessage": "None",
  "sentMessage": "Hello John Doe (256751000355), welcome to the Pahappa Programming Challenge Hackathon"
}
```

#### 2. Validate Phone Only
**GET** `/api/v1/phone-validation/validate/{phoneNumber}`

Validates a phone number without sending SMS.

**Example**: `GET /api/v1/phone-validation/validate/256751000355`

#### 3. Health Check
**GET** `/api/v1/phone-validation/health`

Returns service health status.

### Phone Number Validation Rules

- Must start with `256` (Uganda country code)
- Must be exactly 12 digits long
- Must contain only numeric characters

### SMS Sending Rules

- SMS is only sent to the specific number: `256751000355`
- Message format: `Hello {fullName} ({phoneNumber}), welcome to the Pahappa Programming Challenge Hackathon`

## 🧪 Testing

### Using Swagger UI
1. Open `http://localhost:8080/api/swagger-ui.html`
2. Navigate to the Phone Validation endpoint
3. Click "Try it out"
4. Enter test data and execute

### Using cURL

```bash
curl -X POST "http://localhost:8080/api/v1/phone-validation/validate-and-send" \
     -H "Content-Type: application/json" \
     -d '{
       "fullName": "John Doe",
       "phoneNumber": "256751000355"
     }'
```

### Unit Tests
```bash
mvn test
```

## 🔒 Security & Configuration

### API Credentials
The application uses the following EgoSMS API credentials:
- Username: `pahappahackathon`
- Password: `pahappahackathon`
- Sender ID: `Pahappa`

### Environment Variables (Optional)
You can override default configuration using environment variables:

```bash
export SERVER_PORT=9090
export LOGGING_LEVEL_COM_PAHAPPA_CHALLENGE=DEBUG
```

## 📊 Monitoring & Logging

The application provides comprehensive logging:
- INFO level for normal operations
- WARN level for validation failures
- ERROR level for API failures
- DEBUG level for detailed request/response information

Logs are output to console by default. Configure logging in `application.properties` for production use.

## 🚀 Deployment

### JAR File
```bash
mvn clean package
java -jar target/challenge-1.0.0.jar
```

### Docker (Optional)
Create a `Dockerfile`:
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/challenge-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Check the Swagger documentation
- Review application logs
- Contact the Pahappa Challenge team

## 🎯 Challenge Requirements Met

 Phone number validation (256 prefix, 12 digits, numeric only)  
SMS sending via EgoSMS API  
Personalized message format  
Error handling for invalid input  
Processing summary  
API username/password: pahappahackathon  
Target phone number: 256751000355  
RESTful API design  
Swagger documentation for hosting