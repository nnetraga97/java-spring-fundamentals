# Spring Core, Boot, and REST APIs

These exercises focus on Spring internals, clean API design, validation, security boundaries, and production-grade request handling.

## SPR-01: Minimal Spring Boot API With Clean Boundaries

Priority: `P0`

Scenario: Build a small authorization-style API without letting controllers own business logic.

Build:

- Create a Spring Boot app.
- Add a `POST /authorizations` endpoint.
- Keep DTOs, domain logic, service logic, and controller logic separate.
- Add request validation.
- Add a consistent error response.

Acceptance criteria:

- Invalid input returns a clear 4xx response.
- Domain logic is testable without Spring.
- Notes explain controller/service/domain boundaries.

Interview prompts:

- How do you structure a Spring Boot service?
- Why keep domain logic out of controllers?
- What belongs in an API error response?

## SPR-02: Configuration Properties and Profiles

Priority: `P0`

Scenario: Risk thresholds and service limits differ by environment.

Build:

- Add `@ConfigurationProperties` for configurable limits.
- Validate configuration on startup.
- Add local and test profiles.
- Write tests for binding and invalid configuration.

Acceptance criteria:

- Invalid configuration fails fast.
- Tests prove expected values bind correctly.
- Notes explain externalized configuration and secret hygiene.

Interview prompts:

- How does Spring Boot externalized configuration work?
- Why prefer typed configuration properties?
- How should secrets be handled?

## SPR-03: Auto-Configuration Observation

Priority: `P1`

Scenario: You need to explain what Spring Boot creates for you.

Build:

- Add Actuator or inspect the application context in a test.
- List key auto-configured beans for web, Jackson, validation, and data.
- Override one bean intentionally.
- Document what changed.

Acceptance criteria:

- You can identify framework-provided beans.
- You can explain how to override behavior safely.

Interview prompts:

- What is auto-configuration?
- How do conditional beans work conceptually?
- How do you debug unexpected bean creation?

## SPR-04: Transaction Proxy Trap

Priority: `P0`

Scenario: A method annotated with `@Transactional` is called from another method in the same class.

Build:

- Create a service that demonstrates self-invocation bypassing proxy behavior.
- Write a test that exposes the unexpected behavior.
- Refactor into a correct service boundary.
- Add notes explaining the proxy mechanism.

Acceptance criteria:

- The failing behavior is reproducible.
- The fixed design has a clear transactional boundary.

Interview prompts:

- Why can `@Transactional` fail with self-invocation?
- What is a Spring proxy?
- Where should transactions live?

## SPR-05: API Versioning and Compatibility

Priority: `P1`

Scenario: A response contract needs a new field without breaking existing clients.

Build:

- Implement one backward-compatible response change.
- Implement one intentionally breaking change behind a new version.
- Add tests for both versions.

Acceptance criteria:

- Old clients still receive expected fields.
- New clients can use the updated contract.
- Notes explain additive vs breaking changes.

Interview prompts:

- How do you evolve APIs safely?
- When should you version an endpoint?
- How do you communicate deprecations?

## SPR-06: Security Filter Chain Basics

Priority: `P1`

Scenario: Protect write endpoints while leaving health checks available.

Build:

- Add Spring Security.
- Permit health checks.
- Require authentication for write endpoints.
- Add tests for unauthorized, forbidden, and allowed requests.

Acceptance criteria:

- Security behavior is covered by tests.
- Actuator exposure is intentionally limited.

Interview prompts:

- What is the Spring Security filter chain?
- What is the difference between authentication and authorization?
- How do you secure operational endpoints?

## SPR-07: Caching and Cache Invalidation

Priority: `P1`

Scenario: A hot read (e.g. merchant risk profile) is expensive and called on every authorization.

Build:

- Add `@Cacheable` to the expensive read with a bounded cache (Caffeine).
- Add `@CacheEvict` (or `@CachePut`) on the write path so stale data cannot linger.
- Configure a TTL and a maximum size.
- Add tests proving the second call is served from cache and that a write invalidates it.

Acceptance criteria:

- Cache hits avoid the expensive call.
- A write makes the next read observe fresh data.
- Notes explain cache stampede, TTL vs eviction, and local vs distributed caches.

Interview prompts:

- Where does caching help and where does it hurt correctness?
- How do you prevent a cache stampede on a hot key?
- When would you move from a local cache to Redis?

## SPR-08: JWT Resource Server

Priority: `P1`

Scenario: Write endpoints must be called only with a valid signed token; roles gate specific actions.

Build:

- Configure the app as an OAuth2 resource server that validates JWTs.
- Require a scope/authority for write endpoints; leave health public.
- Extract claims (subject, scopes) into the security context.
- Add tests for missing token, expired/invalid token, insufficient scope, and success.

Acceptance criteria:

- Invalid or missing tokens are rejected before controllers run.
- Authorization is enforced by scope/role, not just authentication.
- Notes explain JWT validation (signature, issuer, expiry) and stateless auth trade-offs.

Interview prompts:

- How does a resource server validate a JWT without calling the auth server every time?
- What are the risks of stateless tokens (revocation, expiry)?
- Where do you enforce authorization — filter, method, or both?

