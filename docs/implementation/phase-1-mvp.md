# Olympus Gate - Phase 1: MVP Implementation

## Status: In Planning
## Commit Authorization: Not Authorized
## Specification: [spec.md](../spec.md)
## Previous Phase: [phase-0-prerequisites.md](./phase-0-prerequisites.md)
## Next Phase: [phase-2-enhanced-selection.md](./phase-2-enhanced-selection.md)
## Agent Instructions: use @workflow:new-feature-sdd and @rules:cascade-response-spec

This document provides a detailed implementation plan for the MVP phase, implementing a functional gateway with 2 cloud providers and rule-based selection based on the Olympus Gate specification.

---

## Implementation Phases Overview

### Phase 0: Project Setup & Prerequisites (Prerequisite)
**Status:** Complete
**Reference:** [phase-0-prerequisites.md](./phase-0-prerequisites.md)

### Phase 1: MVP Implementation (High Priority)
**Objective:** Functional gateway with 2 cloud providers and rule-based selection

**Duration:** 4 weeks
**Dependencies:** Phase 0
**Branch:** `feature/mvp-implementation`

### Phase 2: Enhanced Selection & Local Models (High Priority)
**Objective:** Sophisticated selection with scoring and local model support
**Reference:** [phase-2-enhanced-selection.md](./phase-2-enhanced-selection.md)

**Duration:** 4 weeks
**Dependencies:** Phase 1
**Branch:** `feature/enhanced-selection-local-models`

---

## Phase 1: MVP Implementation

### 1.1 Implement Domain Models
**File:** `src/main/kotlin/com/olympusgate/domain/model/ModelConfiguration.kt`

**Status:** DONE

**Steps:**
1. Write failing test for ModelConfiguration data class
2. Define ModelConfiguration data class with required fields
3. Add validation annotations
4. Create SelectionRule data class
5. Create Request data class
6. Create Response data class
7. Make test pass
8. Write additional tests for edge cases

**Tests:**
- Test: ModelConfiguration validates required fields
- Test: ModelConfiguration accepts valid input
- Test: ModelConfiguration rejects invalid cost values
- Test: SelectionRule validates conditions
- Test: Request validates prompt format
- Test: Response serializes correctly

**Commits:**
- test: add domain model validation tests
- feat: implement ModelConfiguration data class
- feat: implement SelectionRule data class
- feat: implement Request data class
- feat: implement Response data class
- refactor: add validation annotations

### 1.2 Implement Model Configuration Repository
**File:** `src/main/kotlin/com/olympusgate/infrastructure/persistence/ModelConfigurationRepository.kt`

**Status:** DONE

**Steps:**
1. Write failing test for repository operations
2. Create database migration for models table
3. Implement ModelConfigurationRepository interface
4. Implement JPA repository
5. Add CRUD operations
6. Make test pass
7. Write integration tests with Testcontainers

**Tests:**
- Test: Save model configuration
- Test: Find model by ID
- Test: Find all enabled models
- Test: Update model configuration
- Test: Delete model configuration
- Test: Find models by provider

**Commits:**
- test: add repository operation tests
- feat: create models table migration
- feat: implement ModelConfigurationRepository interface
- feat: implement JPA repository
- feat: add CRUD operations
- test: add integration tests

### 1.3 Implement Selection Rule Repository
**File:** `src/main/kotlin/com/olympusgate/infrastructure/persistence/SelectionRuleRepository.kt`

**Status:** DONE
1. Write failing test for rule repository
2. Create database migration for rules table
3. Implement SelectionRuleRepository interface
4. Implement JPA repository
5. Add query methods for rule matching
6. Make test pass
7. Write integration tests

**Tests:**
- Test: Save selection rule
- Test: Find rule by ID
- Test: Find rules by priority
- Test: Find matching rules for request
- Test: Update rule
- Test: Delete rule

**Commits:**
- test: add rule repository tests
- feat: create rules table migration
- feat: implement SelectionRuleRepository interface
- feat: implement JPA repository
- feat: add rule matching queries
- test: add integration tests

### 1.4 Implement Request Analyzer
**File:** `src/main/kotlin/com/olympusgate/domain/service/RequestAnalyzer.kt`

**Status:** DONE
1. Write failing test for token counting
2. Implement token estimation logic
3. Write test for complexity detection
4. Implement complexity analysis
5. Write test for task type detection
6. Implement task type detection
7. Make all tests pass
8. Refactor for clean code

**Tests:**
- Test: Estimate token count accurately
- Test: Detect code complexity
- Test: Detect reasoning complexity
- Test: Classify task type (code, qa, summarization)
- Test: Handle empty prompts
- Test: Handle very long prompts

**Commits:**
- test: add token estimation tests
- feat: implement token estimation
- test: add complexity detection tests
- feat: implement complexity analysis
- test: add task type detection tests
- feat: implement task type detection
- refactor: clean up RequestAnalyzer

### 1.5 Implement Model Selection Engine (Rule-Based) - DONE
**File:** `src/main/kotlin/com/olympusgate/domain/service/ModelSelectionEngine.kt`

**Steps:**
1. Write failing test for rule-based selection
2. Implement rule matching logic
3. Implement priority-based selection
4. Implement budget constraint checking
5. Implement capability matching
6. Make test pass
7. Write tests for edge cases
8. Add fallback logic

**Tests:**
- Test: Select model based on token count rule
- Test: Select model based on priority rule
- Test: Respect budget constraints
- Test: Match required capabilities
- Test: Handle no matching rules
- Test: Fallback to default model
- Test: Exclude specific models

**Commits:**
- test: add rule-based selection tests
- feat: implement rule matching logic
- feat: implement priority-based selection
- feat: implement budget constraint checking
- feat: implement capability matching
- feat: add fallback logic
- test: add edge case tests

### 1.6 Implement OpenAI Provider Adapter
**File:** `src/main/kotlin/com/olympusgate/infrastructure/adapter/OpenAIAdapter.kt`

**Steps:**
1. Write failing test for OpenAI API call
2. Implement OpenAI client using Spring WebClient
3. Add authentication with API key
4. Implement request serialization
5. Implement response deserialization
6. Add error handling
7. Make test pass
8. Write integration tests with mock server

**Tests:**
- Test: Call OpenAI API successfully
- Test: Handle authentication errors
- Test: Handle rate limit errors
- Test: Handle invalid responses
- Test: Retry on transient errors
- Test: Serialize request correctly
- Test: Deserialize response correctly

**Commits:**
- test: add OpenAI API call tests
- feat: implement OpenAI WebClient client
- feat: add API key authentication
- feat: implement request serialization
- feat: implement response deserialization
- feat: add error handling
- feat: add retry logic
- test: add integration tests

### 1.7 Implement Anthropic Provider Adapter
**File:** `src/main/kotlin/com/olympusgate/infrastructure/adapter/AnthropicAdapter.kt`

**Steps:**
1. Write failing test for Anthropic API call
2. Implement Anthropic client using Spring WebClient
3. Add authentication with API key
4. Implement request serialization (Anthropic format)
5. Implement response deserialization
6. Add error handling
7. Make test pass
8. Write integration tests

**Tests:**
- Test: Call Anthropic API successfully
- Test: Handle authentication errors
- Test: Handle rate limit errors
- Test: Handle invalid responses
- Test: Retry on transient errors
- Test: Serialize request in Anthropic format
- Test: Deserialize response correctly

**Commits:**
- test: add Anthropic API call tests
- feat: implement Anthropic WebClient client
- feat: add API key authentication
- feat: implement Anthropic request format
- feat: implement response deserialization
- feat: add error handling
- feat: add retry logic
- test: add integration tests

### 1.8 Implement Provider Adapter Factory
**File:** `src/main/kotlin/com/olympusgate/infrastructure/adapter/ProviderAdapterFactory.kt`

**Steps:**
1. Write failing test for adapter factory
2. Create ProviderAdapter interface
3. Implement factory to create adapters by provider type
4. Register OpenAI adapter
5. Register Anthropic adapter
6. Make test pass
7. Add error handling for unknown providers

**Tests:**
- Test: Create OpenAI adapter
- Test: Create Anthropic adapter
- Test: Throw error for unknown provider
- Test: Adapters implement common interface

**Commits:**
- test: add adapter factory tests
- feat: create ProviderAdapter interface
- feat: implement adapter factory
- feat: register OpenAI adapter
- feat: register Anthropic adapter
- feat: add error handling

### 1.9 Implement Generate API Endpoint
**File:** `src/main/kotlin/com/olympusgate/api/controller/GenerateController.kt`

**Steps:**
1. Write failing test for POST /api/v1/generate
2. Create GenerateController
3. Add request validation
4. Call ModelSelectionEngine
5. Call appropriate ProviderAdapter
6. Format response
7. Make test pass
8. Add error handling

**Tests:**
- Test: Generate endpoint returns 200
- Test: Validate request format
- Test: Return model information when return_mode=model_only
- Test: Return full response when return_mode=response
- Test: Handle invalid requests
- Test: Handle provider errors
- Test: Include routing metadata in response
- Test: Include metrics in response

**Commits:**
- test: add generate endpoint tests
- feat: create GenerateController
- feat: add request validation
- feat: integrate ModelSelectionEngine
- feat: integrate ProviderAdapter
- feat: format response
- feat: add error handling

### 1.10 Implement Admin Endpoints for Model Management
**File:** `src/main/kotlin/com/olympusgate/api/controller/AdminModelController.kt`

**Steps:**
1. Write failing test for POST /api/v1/admin/models
2. Implement create model endpoint
3. Write test for PUT /api/v1/admin/models/{id}
4. Implement update model endpoint
5. Write test for DELETE /api/v1/admin/models/{id}
6. Implement delete model endpoint
7. Write test for GET /api/v1/models
8. Implement list models endpoint
9. Make all tests pass
10. Add authentication middleware

**Tests:**
- Test: Create model configuration
- Test: Update model configuration
- Test: Delete model configuration
- Test: List all models
- Test: List only enabled models
- Test: Validate model configuration on create
- Test: Return 401 without authentication

**Commits:**
- test: add admin create model tests
- feat: implement create model endpoint
- test: add admin update model tests
- feat: implement update model endpoint
- test: add admin delete model tests
- feat: implement delete model endpoint
- test: add list models tests
- feat: implement list models endpoint
- feat: add authentication middleware

### 1.11 Implement YAML Configuration Loader
**File:** `src/main/kotlin/com/olympusgate/infrastructure/config/YamlConfigurationLoader.kt`

**Steps:**
1. Write failing test for YAML loading
2. Create YAML schema for model configuration
3. Create YAML schema for selection rules
4. Implement YAML loader
5. Add hot-reload capability
6. Make test pass
7. Create sample configuration files

**Tests:**
- Test: Load models from YAML
- Test: Load rules from YAML
- Test: Validate YAML schema
- Test: Hot-reload on file change
- Test: Handle invalid YAML

**Commits:**
- test: add YAML loading tests
- feat: create YAML schema for models
- feat: create YAML schema for rules
- feat: implement YAML loader
- feat: add hot-reload capability
- feat: create sample configuration files

### 1.12 Implement Basic Logging
**File:** `src/main/kotlin/com/olympusgate/infrastructure/logging/RequestLogger.kt`

**Steps:**
1. Write failing test for request logging
2. Implement structured request logging
3. Log selected model
4. Log estimated cost
5. Log latency
6. Make test pass
7. Add correlation ID

**Tests:**
- Test: Log request with correlation ID
- Test: Log selected model
- Test: Log estimated cost
- Test: Log latency
- Test: Logs are structured JSON

**Commits:**
- test: add request logging tests
- feat: implement structured request logging
- feat: log selected model
- feat: log estimated cost
- feat: log latency
- feat: add correlation ID

### 1.13 Implement API Key Authentication
**File:** `src/main/kotlin/com/olympusgate/infrastructure/security/ApiKeyAuthFilter.kt`

**Steps:**
1. Write failing test for API key validation
2. Implement API key extraction from header
3. Validate API key against environment variable
4. Add authentication filter
5. Make test pass
6. Add unauthorized response handling

**Tests:**
- Test: Accept valid API key
- Test: Reject invalid API key
- Test: Reject missing API key
- Test: Return 401 for unauthorized
- Test: Allow public endpoints

**Commits:**
- test: add API key validation tests
- feat: implement API key extraction
- feat: implement API key validation
- feat: add authentication filter
- feat: add unauthorized handling

### 1.14 Implement Rate Limiting
**File:** `src/main/kotlin/com/olympusgate/infrastructure/security/RateLimiter.kt`

**Steps:**
1. Write failing test for rate limiting
2. Implement rate limiter using Redis
3. Add rate limit by API key
4. Add rate limit by IP
5. Make test pass
6. Configure rate limits

**Tests:**
- Test: Allow requests under limit
- Test: Block requests over limit
- Test: Reset rate limit after window
- Test: Different limits per API key
- Test: Rate limit by IP

**Commits:**
- test: add rate limiting tests
- feat: implement Redis rate limiter
- feat: add rate limit by API key
- feat: add rate limit by IP
- feat: configure rate limits

### 1.15 Write End-to-End Tests
**File:** `src/test/kotlin/com/olympusgate/e2e/GenerateE2ETest.kt`

**Steps:**
1. Write failing E2E test for complete flow
2. Test request to generate endpoint
3. Verify model selection
4. Verify provider call
5. Verify response format
6. Make test pass
7. Add E2E test for admin endpoints
8. Add E2E test for error scenarios

**Tests:**
- Test: Complete generate flow
- Test: Admin create model flow
- Test: Admin update model flow
- Test: Error handling flow
- Test: Authentication flow
- Test: Rate limiting flow

**Commits:**
- test: add E2E generate flow test
- test: add E2E admin flow tests
- test: add E2E error handling tests
- test: add E2E authentication tests
- test: add E2E rate limiting tests

### 1.16 Update Documentation
**File:** `README.md`, `docs/api.md`

**Steps:**
1. Update README with MVP features
2. Create API documentation
3. Add setup guide for cloud providers
4. Add configuration guide
5. Add troubleshooting section
6. Verify all documentation is accurate

**Tests:**
- Test: Documentation builds without errors
- Test: API examples are valid
- Test: Setup instructions work

**Commits:**
- docs: update README with MVP features
- docs: create API documentation
- docs: add cloud provider setup guide
- docs: add configuration guide
- docs: add troubleshooting section

---

## Implementation Notes

### Branch Strategy
- Phase 1 implemented in `feature/mvp-implementation` branch
- After completion, use @workflow:pepare-pr to create PR and merge to main
- Delete branch after merge (per global rules)
- Start Phase 2 from updated main branch

### Commit Strategy
- Follow TDD: Write failing test first, then implementation using @skills:tdd-expert
- Make granular commits for each sub-task using @workflow:pre-commit
- Use conventional commit format as in @workflow:commit-message-convention
- Each commit should be independently testable
- Commit frequently to avoid large diffs

### Testing Strategy
- Write failing test before implementation (TDD) @skills:tdd-expert
- Unit tests for individual components
- Integration tests with Testcontainers for database and Redis
- E2E tests for complete flows
- Mock external API calls (OpenAI, Anthropic)
- All tests must pass before commit
- All linting must pass before commit
- Before commit, invoke @workflow:pre-commit

### Code Quality Standards
- Maintain Hexagonal Architecture pattern
- Follow Kotlin idiomatic practices using @skills:kotlin-spring-dev
- Ensure all tests pass before committing
- Use conventional commit format
- Update documentation with each phase
- Follow global rules for branch management
- Always follow @rules:cascade-response-spec

### Dependencies Between Phases
- Phase 0: Complete (prerequisite)
- Phase 1: Depends on Phase 0
- Phase 2: Depends on Phase 1

### Risk Mitigation
- Implement cloud adapters independently
- Use mock servers for external API testing
- Keep configuration simple (YAML-based)
- Add comprehensive error handling
- Monitor for regressions after each sub-task

### Success Criteria Check
- [ ] POST /api/v1/generate works with OpenAI
- [ ] POST /api/v1/generate works with Anthropic
- [ ] Model selection uses rules correctly
- [ ] Admin endpoints for model management work
- [ ] YAML configuration loads correctly
- [ ] API key authentication works
- [ ] Rate limiting works
- [ ] All tests pass (>90% coverage)
- [ ] Documentation is complete
- [ ] Demo with 2 models works

---

## Authorization Required

This implementation plan is not ready for review. Please authorize before beginning implementation of Phase 1.
