# Olympus Gate - Phase 2: Enhanced Selection & Local Models

## Status: In Planning
## Commit Authorization: Not Authorized
## Specification: [spec.md](../spec.md)
## Previous Phase: [phase-1-mvp.md](./phase-1-mvp.md)
## Agent Instructions: use @workflow:new-feature-sdd and @rules:cascade-response-spec

This document provides a detailed implementation plan for the enhanced selection phase, implementing sophisticated selection with scoring and local model support based on the Olympus Gate specification.

---

## Implementation Phases Overview

### Phase 0: Project Setup & Prerequisites (Prerequisite)
**Status:** Complete
**Reference:** [phase-0-prerequisites.md](./phase-0-prerequisites.md)

### Phase 1: MVP Implementation (High Priority)
**Status:** Complete
**Reference:** [phase-1-mvp.md](./phase-1-mvp.md)

### Phase 2: Enhanced Selection & Local Models (High Priority)
**Objective:** Sophisticated selection with scoring and local model support

**Duration:** 4 weeks
**Dependencies:** Phase 1
**Branch:** `feature/enhanced-selection-local-models`

---

## Phase 2: Enhanced Selection & Local Models

### 2.1 Implement Scoring Algorithm
**File:** `src/main/kotlin/com/olympusgate/domain/service/ScoringEngine.kt`

**Steps:**
1. Write failing test for complexity score calculation
2. Implement complexity scoring logic
3. Write test for cost score calculation
4. Implement cost scoring logic
5. Write test for capability score calculation
6. Implement capability scoring logic
7. Write test for final score calculation
8. Implement weighted score aggregation
9. Make all tests pass
10. Add configurable weights

**Tests:**
- Test: Calculate complexity score from token count
- Test: Calculate complexity score from structural complexity
- Test: Calculate cost score from base cost
- Test: Calculate cost score with budget constraints
- Test: Calculate capability score from context window
- Test: Calculate capability score from reasoning ability
- Test: Aggregate scores with weights
- Test: Override with priority
- Test: Configure custom weights

**Commits:**
- test: add complexity scoring tests
- feat: implement complexity scoring
- test: add cost scoring tests
- feat: implement cost scoring
- test: add capability scoring tests
- feat: implement capability scoring
- test: add score aggregation tests
- feat: implement weighted aggregation
- feat: add configurable weights

### 2.2 Update Model Selection Engine with Scoring
**File:** `src/main/kotlin/com/olympusgate/domain/service/ModelSelectionEngine.kt`

**Steps:**
1. Write failing test for scoring-based selection
2. Integrate ScoringEngine into ModelSelectionEngine
3. Replace rule-based selection with scoring-based
4. Keep rule-based as fallback
5. Make test pass
6. Add tests for hybrid selection (rules + scoring)
7. Refactor for clean code

**Tests:**
- Test: Select model with highest score
- Test: Score-based selection respects constraints
- Test: Fallback to rules when scoring fails
- Test: Hybrid selection (rules override scoring)
- Test: Handle equal scores
- Test: Prioritize local models when scores equal

**Commits:**
- test: add scoring-based selection tests
- feat: integrate ScoringEngine
- feat: replace rule-based with scoring-based
- feat: add rule-based fallback
- test: add hybrid selection tests
- refactor: clean up ModelSelectionEngine

### 2.3 Implement Ollama Provider Adapter
**File:** `src/main/kotlin/com/olympusgate/infrastructure/adapter/OllamaAdapter.kt`

**Steps:**
1. Write failing test for Ollama API call
2. Implement Ollama client using Spring WebClient
3. Add configuration for Ollama endpoint
4. Implement request serialization (Ollama format)
5. Implement response deserialization
6. Add error handling
7. Make test pass
8. Write integration tests with local Ollama

**Tests:**
- Test: Call Ollama API successfully
- Test: Handle connection errors
- Test: Handle model not found
- Test: Serialize request in Ollama format
- Test: Deserialize response correctly
- Test: Retry on transient errors
- Test: Integration with local Ollama

**Commits:**
- test: add Ollama API call tests
- feat: implement Ollama WebClient client
- feat: add Ollama endpoint configuration
- feat: implement Ollama request format
- feat: implement response deserialization
- feat: add error handling
- feat: add retry logic
- test: add integration tests

### 2.4 Implement LM Studio Provider Adapter
**File:** `src/main/kotlin/com/olympusgate/infrastructure/adapter/LMStudioAdapter.kt`

**Steps:**
1. Write failing test for LM Studio API call
2. Implement LM Studio client using Spring WebClient
3. Add configuration for LM Studio endpoint
4. Implement request serialization (OpenAI-compatible format)
5. Implement response deserialization
6. Add error handling
7. Make test pass
8. Write integration tests

**Tests:**
- Test: Call LM Studio API successfully
- Test: Handle connection errors
- Test: Handle model not found
- Test: Serialize request in OpenAI format
- Test: Deserialize response correctly
- Test: Retry on transient errors

**Commits:**
- test: add LM Studio API call tests
- feat: implement LM Studio WebClient client
- feat: add LM Studio endpoint configuration
- feat: implement OpenAI-compatible format
- feat: implement response deserialization
- feat: add error handling
- feat: add retry logic
- test: add integration tests

### 2.5 Implement vLLM Provider Adapter
**File:** `src/main/kotlin/com/olympusgate/infrastructure/adapter/VLLMAdapter.kt`

**Steps:**
1. Write failing test for vLLM API call
2. Implement vLLM client using Spring WebClient
3. Add configuration for vLLM endpoint
4. Implement request serialization (OpenAI-compatible format)
5. Implement response deserialization
6. Add error handling
7. Make test pass
8. Write integration tests

**Tests:**
- Test: Call vLLM API successfully
- Test: Handle connection errors
- Test: Handle model not found
- Test: Serialize request in OpenAI format
- Test: Deserialize response correctly
- Test: Retry on transient errors

**Commits:**
- test: add vLLM API call tests
- feat: implement vLLM WebClient client
- feat: add vLLM endpoint configuration
- feat: implement OpenAI-compatible format
- feat: implement response deserialization
- feat: add error handling
- feat: add retry logic
- test: add integration tests

### 2.6 Update Provider Adapter Factory
**File:** `src/main/kotlin/com/olympusgate/infrastructure/adapter/ProviderAdapterFactory.kt`

**Steps:**
1. Write failing test for local adapter creation
2. Register Ollama adapter
3. Register LM Studio adapter
4. Register vLLM adapter
5. Add provider type detection (cloud vs local)
6. Make test pass
7. Add health check for local providers

**Tests:**
- Test: Create Ollama adapter
- Test: Create LM Studio adapter
- Test: Create vLLM adapter
- Test: Detect cloud provider type
- Test: Detect local provider type
- Test: Health check for local providers

**Commits:**
- test: add local adapter tests
- feat: register Ollama adapter
- feat: register LM Studio adapter
- feat: register vLLM adapter
- feat: add provider type detection
- feat: add health check for local providers

### 2.7 Implement Streaming Support
**File:** `src/main/kotlin/com/olympusgate/api/controller/GenerateController.kt`

**Steps:**
1. Write failing test for streaming endpoint
2. Add POST /api/v1/generate/stream endpoint
3. Implement SSE streaming
4. Stream delta chunks
5. Include routing metadata in first chunk
6. Include metrics in final chunk
7. Make test pass
8. Add client disconnect handling

**Tests:**
- Test: Stream returns SSE format
- Test: First chunk includes routing metadata
- Test: Final chunk includes metrics
- Test: Handle client disconnect gracefully
- Test: Stream complete response
- Test: Handle provider errors during stream

**Commits:**
- test: add streaming endpoint tests
- feat: add /generate/stream endpoint
- feat: implement SSE streaming
- feat: include routing in first chunk
- feat: include metrics in final chunk
- feat: add disconnect handling

### 2.8 Implement Dynamic Configuration (PostgreSQL)
**File:** `src/main/kotlin/com/olympusgate/infrastructure/config/DatabaseConfigurationLoader.kt`

**Steps:**
1. Write failing test for database configuration loading
2. Implement configuration loader from PostgreSQL
3. Add configuration cache with Redis
4. Implement cache invalidation on update
5. Add hot-reload from database
6. Make test pass
7. Migrate from YAML to database

**Tests:**
- Test: Load models from database
- Test: Load rules from database
- Test: Cache configuration in Redis
- Test: Invalidate cache on update
- Test: Hot-reload from database
- Test: Fallback to YAML if database empty

**Commits:**
- test: add database config loading tests
- feat: implement database configuration loader
- feat: add Redis cache for configuration
- feat: add cache invalidation
- feat: add hot-reload from database
- feat: migrate from YAML to database

### 2.9 Implement Health Status Checking
**File:** `src/main/kotlin/com/olympusgate/domain/service/HealthCheckService.kt`

**Steps:**
1. Write failing test for health check
2. Implement credential validation for cloud providers
3. Implement balance check for cloud providers
4. Implement connectivity check for local providers
5. Add scheduled health checks
6. Update model health_status in database
7. Make test pass
8. Add health check endpoint

**Tests:**
- Test: Validate OpenAI credentials
- Test: Validate Anthropic credentials
- Test: Check OpenAI balance
- Test: Check Anthropic balance
- Test: Check Ollama connectivity
- Test: Check LM Studio connectivity
- Test: Check vLLM connectivity
- Test: Update health_status in database
- Test: Health check endpoint

**Commits:**
- test: add health check tests
- feat: implement credential validation
- feat: implement balance check
- feat: implement connectivity check
- feat: add scheduled health checks
- feat: update health_status in database
- feat: add health check endpoint

### 2.10 Implement Metrics Service
**File:** `src/main/kotlin/com/olympusgate/application/service/MetricsService.kt`

**Steps:**
1. Write failing test for metrics collection
2. Implement request metrics collection
3. Implement cost tracking
4. Implement model distribution tracking
5. Add metrics aggregation
6. Implement GET /api/v1/metrics endpoint
7. Make test pass
8. Add time-based filtering

**Tests:**
- Test: Collect request metrics
- Test: Track cost per request
- Test: Track model distribution
- Test: Aggregate metrics by time period
- Test: Filter metrics by date range
- Test: Metrics endpoint returns correct format

**Commits:**
- test: add metrics collection tests
- feat: implement request metrics collection
- feat: implement cost tracking
- feat: implement model distribution tracking
- feat: add metrics aggregation
- feat: implement /metrics endpoint
- feat: add time-based filtering

### 2.11 Update Model Configuration Schema
**File:** `src/main/kotlin/com/olympusgate/domain/model/ModelConfiguration.kt`

**Steps:**
1. Write failing test for new schema fields
2. Add provider_type field (cloud|local)
3. Add api_endpoint field
4. Add credentials object with api_key and auth_type
5. Add health_status object
6. Create database migration
7. Make test pass
8. Update admin endpoints to handle new fields

**Tests:**
- Test: Validate provider_type
- Test: Validate api_endpoint format
- Test: Validate credentials structure
- Test: Validate health_status structure
- Test: Migration applies successfully
- Test: Admin endpoints handle new fields

**Commits:**
- test: add schema validation tests
- feat: add provider_type field
- feat: add api_endpoint field
- feat: add credentials object
- feat: add health_status object
- feat: create database migration
- feat: update admin endpoints

### 2.12 Implement Model Tier Classification
**File:** `src/main/kotlin/com/olympusgate/domain/service/ModelTierClassifier.kt`

**Steps:**
1. Write failing test for tier classification
2. Implement tier classification logic
3. Classify Tier 1 (high performance cloud)
4. Classify Tier 2 (balanced cloud)
5. Classify Tier 3 (lightweight local)
6. Make test pass
7. Add tier metadata to model configuration

**Tests:**
- Test: Classify GPT-4 as Tier 1
- Test: Classify GPT-3.5 as Tier 2
- Test: Classify Ollama models as Tier 3
- Test: Classify custom models correctly
- Test: Handle unknown models

**Commits:**
- test: add tier classification tests
- feat: implement tier classification logic
- feat: classify Tier 1 models
- feat: classify Tier 2 models
- feat: classify Tier 3 models
- feat: add tier metadata

### 2.13 Write Integration Tests
**File:** `src/test/kotlin/com/olympusgate/integration/SelectionIntegrationTest.kt`

**Steps:**
1. Write failing integration test for scoring selection
2. Test complete flow with scoring
3. Test local model selection
4. Test cloud model selection
5. Test hybrid selection (cloud + local)
6. Test streaming flow
7. Test metrics collection
8. Make all tests pass

**Tests:**
- Test: Complete flow with scoring selection
- Test: Select local model when appropriate
- Test: Select cloud model when appropriate
- Test: Hybrid selection with both types
- Test: Streaming flow works end-to-end
- Test: Metrics are collected correctly
- Test: Health status affects selection

**Commits:**
- test: add scoring selection integration test
- test: add local model selection test
- test: add cloud model selection test
- test: add hybrid selection test
- test: add streaming integration test
- test: add metrics integration test

### 2.14 Update Documentation
**File:** `README.md`, `docs/api.md`, `docs/local-models.md`

**Steps:**
1. Update README with Phase 2 features
2. Update API documentation with streaming
3. Create local models setup guide
4. Update configuration guide for database
5. Add scoring algorithm documentation
6. Add troubleshooting for local models
7. Verify all documentation is accurate

**Tests:**
- Test: Documentation builds without errors
- Test: API examples are valid
- Test: Local model setup instructions work

**Commits:**
- docs: update README with Phase 2 features
- docs: update API documentation
- docs: create local models setup guide
- docs: update configuration guide
- docs: add scoring algorithm docs
- docs: add local models troubleshooting

### 2.15 Performance Testing
**File:** `src/test/kotlin/com/olympusgate/performance/SelectionPerformanceTest.kt`

**Steps:**
1. Write performance test for selection engine
2. Measure selection latency
3. Measure scoring calculation time
4. Optimize if latency > 100ms (P95)
5. Write performance test for streaming
6. Measure streaming latency
7. Make all tests pass

**Tests:**
- Test: Selection latency < 100ms (P95)
- Test: Scoring calculation < 50ms
- Test: Streaming first chunk < 200ms
- Test: Handle concurrent requests

**Commits:**
- test: add selection performance tests
- test: add scoring performance tests
- test: add streaming performance tests
- perf: optimize selection engine
- perf: optimize scoring calculation

---

## Implementation Notes

### Branch Strategy
- Phase 2 implemented in `feature/enhanced-selection-local-models` branch
- After completion, use @workflow:pepare-pr to create PR and merge to main
- Delete branch after merge (per global rules)
- Start next phase from updated main branch

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
- Integration tests with local model services (Ollama, LM Studio, vLLM)
- Performance tests for critical paths
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
- Phase 1: Complete (prerequisite)
- Phase 2: Depends on Phase 1

### Risk Mitigation
- Implement local adapters independently
- Use mock local services for testing when real services unavailable
- Add comprehensive health checking
- Monitor performance of scoring algorithm
- Keep backward compatibility with YAML config

### Success Criteria Check
- [ ] Scoring algorithm selects optimal models
- [ ] Local models (Ollama, LM Studio, vLLM) work
- [ ] Streaming responses work
- [ ] Dynamic configuration from PostgreSQL works
- [ ] Health status checking works
- [ ] Metrics endpoint works
- [ ] Selection latency < 100ms (P95)
- [ ] All tests pass (>90% coverage)
- [ ] Documentation is complete
- [ ] Local model integration works

---

## Authorization Required

This implementation plan is not ready for review. Please authorize before beginning implementation of Phase 2.
