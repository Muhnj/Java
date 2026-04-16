#!/bin/bash

# Pahappa Challenge Build and Run Script

echo "🏗️  Building Pahappa Challenge API..."

# Clean and compile
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "🚀 Starting the application..."
    mvn spring-boot:run
else
    echo "❌ Build failed!"
    exit 1
fi