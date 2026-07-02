# Observability and Production Debugging

These exercises train production ownership: metrics, logs, traces, health checks, latency debugging, and incident response.

## PROD-01: Actuator Health and Readiness

Priority: `P0`

Scenario: A service should only receive traffic when dependencies are ready.

Build:

- Add Actuator.
- Expose health, liveness, and readiness endpoints.
- Add a custom health indicator for a dependency.
- Restrict sensitive endpoints.

Acceptance criteria:

- Health behavior is observable by endpoint.
- Sensitive actuator data is not broadly exposed.
- Notes explain liveness vs readiness.

Interview prompts:

- What makes a good health check?
- How do liveness and readiness differ?
- What should not be exposed publicly?

## PROD-02: Correlation IDs and Structured Logging

Priority: `P1`

Scenario: You need to trace one request across logs.

Build:

- Add a request filter that creates or propagates a correlation ID.
- Include the ID in logs and response headers.
- Redact sensitive fields.
- Add tests for propagation.

Acceptance criteria:

- Every request has a correlation ID.
- Sensitive values are not logged.

Interview prompts:

- What should logs include?
- What should logs never include?
- How do correlation IDs help incident response?

## PROD-03: Metrics for a Critical API

Priority: `P0`

Scenario: You need to detect latency, error rate, and business outcome changes.

Build:

- Add counters for success, decline, validation failure, and downstream failure.
- Add timers for request latency.
- Keep metric tags low cardinality.
- Add notes for alerts and dashboards.

Acceptance criteria:

- Metrics are named consistently.
- Tags cannot explode cardinality.
- Notes define useful alerts.

Interview prompts:

- What metrics would you expose for a critical backend API?
- What is high-cardinality metric risk?
- How do you debug p99 latency?

## PROD-04: Slow Query Investigation

Priority: `P1`

Scenario: A search endpoint becomes slow as data grows.

Build:

- Create a query that becomes inefficient.
- Capture timing or explain plan.
- Add an index or query rewrite.
- Document before and after.

Acceptance criteria:

- The performance issue is measurable.
- The fix is justified by query behavior.

Interview prompts:

- How do you investigate slow database calls?
- What signals separate application latency from database latency?
- How can an index make writes slower?

## PROD-05: Incident Runbook

Priority: `P2`

Scenario: A production API has elevated error rate and latency.

Build:

- Create a runbook under `notes/runbook-critical-api.md`.
- Include detection, immediate mitigation, diagnosis, communication, and follow-up.
- Add a post-incident review template.

Acceptance criteria:

- The runbook is actionable under pressure.
- It includes customer impact and rollback thinking.

Interview prompts:

- How do you handle a production incident?
- What belongs in a post-incident review?
- How do you prevent repeat incidents?

