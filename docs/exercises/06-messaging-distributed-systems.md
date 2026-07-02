# Messaging and Distributed Systems

These exercises target event-driven architecture, idempotency, reliability, consistency, and failure handling.

## DIST-01: Transactional Outbox

Priority: `P0`

Scenario: A database write succeeds, but event publication fails before the message reaches the broker.

Build:

- Write domain data and an outbox row in the same transaction.
- Add a publisher that reads unpublished rows.
- Mark rows as published only after successful send.
- Add tests for failure and retry behavior.

Acceptance criteria:

- Domain state and outbox state are atomic.
- A failed publish can be retried.
- Notes explain the outbox pattern.

Interview prompts:

- How do you avoid losing events after commit?
- What trade-offs does the outbox pattern introduce?
- How do you monitor stuck outbox rows?

## DIST-02: Idempotent Consumer

Priority: `P0`

Scenario: A message may be delivered more than once.

Build:

- Create a consumer that records processed message IDs.
- Ignore duplicates safely.
- Add tests for repeated delivery.
- Include a failure case before and after marking processed.

Acceptance criteria:

- Duplicate messages do not duplicate side effects.
- Failure ordering is clearly handled.

Interview prompts:

- What does at-least-once delivery imply?
- Why is "exactly once" tricky?
- Where should idempotency state live?

## DIST-03: Partitioning and Ordering

Priority: `P1`

Scenario: Events for the same transaction must be processed in order, but total throughput needs to scale.

Build:

- Simulate events with keys.
- Route events by key to partitions or worker queues.
- Prove same-key events preserve ordering.
- Show different keys can process concurrently.

Acceptance criteria:

- Same-key ordering is preserved.
- Cross-key concurrency is possible.

Interview prompts:

- How do Kafka partitions affect ordering?
- What key would you choose?
- What happens if a hot key dominates traffic?

## DIST-04: Retry and Dead Letter Flow

Priority: `P0`

Scenario: Some messages fail temporarily; others are poison messages.

Build:

- Add retry attempts with bounded retry count.
- Send exhausted messages to a dead-letter store.
- Add metadata for failure reason and attempt count.
- Add tests for transient and permanent failures.

Acceptance criteria:

- Retry behavior is bounded.
- Poison messages stop blocking progress.
- Notes explain operational handling of dead-letter queues.

Interview prompts:

- How do you choose retry limits?
- How do you avoid retry storms?
- What should happen to dead-lettered messages?

## DIST-05: Saga Flow

Priority: `P1`

Scenario: A multi-step workflow spans authorization, ledger, and notification steps.

Build:

- Model a simple saga/process manager.
- Persist saga state.
- Add compensating behavior for a failed step.
- Add tests for success, retryable failure, and compensation.

Acceptance criteria:

- State transitions are explicit.
- Failed workflows are inspectable.

Interview prompts:

- What is a saga?
- When is compensation acceptable?
- How do you make long-running workflows observable?

## DIST-06: Distributed Rate Limiting

Priority: `P1`

Scenario: A per-merchant request limit must hold across several service instances, not just one JVM.

Build:

- Implement a token-bucket (or fixed/sliding-window) limiter in-process first.
- Make it correct across instances using a shared store (model Redis with a single shared map/atomic if a broker is not available).
- Handle the store being briefly unavailable (fail-open vs fail-closed decision).
- Add tests for burst, steady-state, and limit-exceeded behavior.

Acceptance criteria:

- Same-merchant traffic is limited consistently regardless of which instance serves it.
- The limiter degrades according to an explicit, documented policy when the store is down.
- Notes compare token bucket vs sliding window and local vs distributed enforcement.

Interview prompts:

- Why is a purely in-memory limiter wrong behind a load balancer?
- Token bucket vs sliding window — when does each fit?
- Do you fail open or fail closed when the rate-limit store is unavailable?

