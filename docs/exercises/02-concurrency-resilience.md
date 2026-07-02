# Concurrency and Resilience

These exercises focus on correctness under parallel execution, timeouts, retries, and failure isolation.

## CONC-01: Thread-Safe Idempotency Registry

Priority: `P0`

Scenario: Multiple requests with the same idempotency key arrive at the same time.

Build:

- Implement a registry that returns the first computed result for a key.
- Use `ConcurrentHashMap` and `computeIfAbsent` or a deliberate alternative.
- Simulate 50 concurrent callers.
- Prove the expensive computation only runs once.

Acceptance criteria:

- The result is stable for duplicate callers.
- The computation count is exactly one for the same key.
- Notes explain race conditions and atomic map operations.

Interview prompts:

- Why is check-then-act unsafe?
- What does `computeIfAbsent` guarantee?
- How would this change with a database-backed idempotency table?

## CONC-02: Thread Pool Sizing

Priority: `P1`

Scenario: A service mixes CPU-bound validation with blocking downstream calls.

Build:

- Create separate executors for CPU-bound and I/O-bound work.
- Simulate latency and throughput under different pool sizes.
- Add notes explaining the observed behavior.

Acceptance criteria:

- CPU and blocking work do not share the same executor.
- Notes explain why pool sizing depends on workload.

Interview prompts:

- How do you size a thread pool?
- What happens when queues are unbounded?
- How do virtual threads change the conversation?

## CONC-03: Timeout and Retry Wrapper

Priority: `P0`

Scenario: A downstream risk service sometimes hangs or fails.

Build:

- Create a client wrapper with timeout handling.
- Add bounded retries with jitter.
- Avoid retrying non-retryable failures.
- Add tests using a fake downstream client.

Acceptance criteria:

- Retry count is bounded.
- Timeout failures are surfaced clearly.
- Non-retryable failures are not retried.

Interview prompts:

- When are retries dangerous?
- Why should retries have jitter?
- How do retries interact with idempotency?

## CONC-04: Circuit Breaker State Machine

Priority: `P1`

Scenario: Repeated downstream failures should temporarily stop calls.

Build:

- Implement a simple circuit breaker with `CLOSED`, `OPEN`, and `HALF_OPEN`.
- Track failures and recovery attempts.
- Add tests for state transitions.

Acceptance criteria:

- Calls fail fast while the circuit is open.
- A successful half-open call closes the circuit.
- Notes explain circuit breaker trade-offs.

Interview prompts:

- What is the difference between timeout, retry, circuit breaker, and bulkhead?
- How do you choose thresholds?
- What metrics should a circuit breaker expose?

## CONC-05: Deadlock Investigation

Priority: `P2`

Scenario: Two locks are acquired in inconsistent order.

Build:

- Create a small program that can deadlock.
- Fix it by enforcing lock ordering or reducing lock scope.
- Add notes showing how you would detect this from a thread dump.

Acceptance criteria:

- The fixed version cannot deadlock through inconsistent lock ordering.
- Notes include the investigation path.

Interview prompts:

- How do you debug a deadlock?
- What does a blocked thread look like?
- How do you design to avoid lock ordering bugs?

