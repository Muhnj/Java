# Pahappa Challenge API Documentation

## Overview
This API provides phone number validation and SMS sending functionality for the Pahappa Programming Challenge.

## Base URL
```
http://localhost:8080/api
```

## Authentication
No authentication required for this challenge implementation.

## Endpoints

### POST /v1/phone-validation/validate-and-send
Validates a phone number and sends an SMS message if valid.

#### Request
```json
{
  "fullName": "string",
  "phoneNumber": "string"
}
```

#### Response
```json
{
  "nameEntered": "string",
  "phoneNumberEntered": "string",
  "validationResult": "Valid|Invalid",
  "messageSent": boolean,
  "errorMessage": "string",
  "sentMessage": "string"
}
```

#### Example Request
```bash
curl -X POST "http://localhost:8080/api/v1/phone-validation/validate-and-send" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "phoneNumber": "256751000355"
  }'
```

#### Example Response
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

### GET /v1/phone-validation/validate/{phoneNumber}
Validates a phone number without sending SMS.

#### Response
```json
{
  "nameEntered": "",
  "phoneNumberEntered": "string",
  "validationResult": "Valid|Invalid",
  "messageSent": false,
  "errorMessage": "string",
  "sentMessage": null
}
```

### GET /v1/phone-validation/health
Health check endpoint.

#### Response
```json
{
  "status": "UP",
  "service": "Pahappa Challenge API"
}
```

## Phone Validation Rules
- Must start with `256`
- Must be exactly 12 digits
- Must contain only numeric characters

## SMS Rules
- SMS is only sent to phone number: `256751000355`
- Message format: `Hello {fullName} ({phoneNumber}), welcome to the Pahappa Programming Challenge Hackathon`

## Error Codes
- `200`: Success
- `400`: Bad Request (invalid input)
- `500`: Internal Server Error

## Rate Limiting
No rate limiting implemented for this challenge.

## Support
For API support, refer to the Swagger UI at `/swagger-ui.html` or contact the development team.