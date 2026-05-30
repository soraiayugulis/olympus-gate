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

---

> **work-in-progress** 
by @_sysout
