# Testing and Code Quality

These exercises build confidence in unit tests, Spring slices, integration tests, maintainable design, and review discipline.

## TEST-01: Domain Unit Tests Without Spring

Priority: `P0`

Scenario: Authorization decisions should be testable without loading the framework.

Build:

- Create domain services as plain Java classes.
- Add tests for approval, decline, validation failure, and edge cases.
- Avoid mocks where simple values are enough.

Acceptance criteria:

- Tests run fast.
- No Spring context is required.
- Notes explain why domain tests should be framework-light.

Interview prompts:

- What should be unit tested?
- When do mocks hurt test quality?
- How do fast tests influence design?

## TEST-02: Web Layer Slice Test

Priority: `P1`

Scenario: Validate API behavior without starting the full application.

Build:

- Add `@WebMvcTest` for a controller.
- Test validation failures, success, and error response shape.
- Mock the service boundary.

Acceptance criteria:

- Tests focus only on web behavior.
- Service behavior is not duplicated in controller tests.

Interview prompts:

- When do you use `@WebMvcTest`?
- What should a controller test assert?
- How do you avoid brittle JSON tests?

## TEST-03: Database Integration Test With Testcontainers

Priority: `P0`

Scenario: Repository behavior depends on real database constraints.

Build:

- Add Testcontainers for PostgreSQL.
- Test unique constraints, indexes, and transaction behavior.
- Avoid relying only on H2 for database-specific behavior.

Acceptance criteria:

- Tests run against a real database engine.
- Constraint failures are covered.
- Notes explain H2 vs PostgreSQL trade-offs.

Interview prompts:

- Why use Testcontainers?
- What should integration tests cover?
- How do you keep integration tests reliable?

## TEST-04: Contract Test for Error Responses

Priority: `P1`

Scenario: API clients rely on stable error fields.

Build:

- Define a stable error response format.
- Add tests for validation, conflict, unauthorized, and unexpected errors.
- Ensure no stack traces or internal class names leak.

Acceptance criteria:

- Error shape is consistent.
- Sensitive internals do not leak.

Interview prompts:

- What belongs in an API error contract?
- How do you avoid leaking implementation details?
- How do you test negative paths well?

## TEST-05: Code Review Checklist Exercise

Priority: `P2`

Scenario: Review your own branch as if it were written by a teammate.

Build:

- Create `notes/code-review-YYYY-MM-DD.md`.
- Review correctness, readability, testing, security, operability, and performance.
- Identify at least three improvements.
- Make one improvement and explain why it was chosen.

Acceptance criteria:

- The review finds real issues or risks.
- At least one improvement is implemented.

Interview prompts:

- What do you look for beyond style?
- How do you give feedback without taking over?
- How do you raise team standards?

