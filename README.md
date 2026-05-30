# Olympus Gate

An intelligent middleware for routing AI model requests. The system analyzes prompt, context, and metadata to automatically select the optimal model, balancing cost and capability.

## Stack

- **Language:** Kotlin
- **Framework:** Spring Boot
- **Database:** PostgreSQL, Redis
- **API:** Spring Web, Spring WebClient
- **Metrics:** Micrometer (Prometheus format)
- **Deployment:** Docker Compose
- **CI/CD:** GitHub Actions

## Overview

Olympus Gate is an AI model gateway that:
- Analyzes request characteristics (complexity, context, requirements)
- Selects optimal model based on cost vs capability rules
- Executes request on selected model
- Returns model information only OR full response
- Collects metrics for optimization

## Setup

### Prerequisites
- JDK 21
- Docker and Docker Compose
- Gradle 8.7+

### Local Development

1. Clone the repository:
```bash
git clone https://github.com/soraiayugulis/olympus-gate.git
cd olympus-gate
```

2. Start dependencies with Docker Compose:
```bash
docker-compose up -d postgres redis
```

3. Run the application:
```bash
./gradlew bootRun
```

4. Access the API:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Metrics: http://localhost:8080/actuator/prometheus

### Running Tests

```bash
./gradlew test
```

### Code Quality

```bash
./gradlew ktlintCheck
./gradlew detekt
```

---

> **work-in-progress** 
by @_sysout
