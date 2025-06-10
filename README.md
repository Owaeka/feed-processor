# Feed Processor Microservice

## Overview

The Feed Processor is a Spring Boot microservice designed to standardize incoming sports betting data from multiple third-party feed providers. It acts as a normalization layer in the feed processing pipeline, transforming proprietary message formats into a unified internal structure.

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Git

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Owaeka/feed-processor.git
cd feed-processor
```

### Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Using Java
mvn clean package
java -jar target/feed-processor-1.0.0.jar
```

The application will start on `http://localhost:8080`

### Swagger

Swagger can be acessed on: `http://localhost:8080/swagger-ui/index.html`

### Run Tests

```bash
# Run all tests
mvn test

# Run tests with coverage report
mvn clean test jacoco:report
```

Coverage report will be available at `target/site/jacoco/index.html`