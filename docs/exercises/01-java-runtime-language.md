# Java Runtime and Language Depth

These exercises rebuild senior-level Java fluency: collections, object modeling, generics, modern Java features, JVM behavior, and debugging.

## JAVA-01: Hash-Based Collections Deep Dive

Priority: `P0`

Scenario: You need an in-memory idempotency cache keyed by merchant ID and request ID.

Build:

- Create an immutable key type using a record.
- Store request results in a `HashMap`.
- Add tests proving equivalent keys retrieve the same value.
- Add a deliberately broken key class with incorrect `equals`/`hashCode`, and write a test that exposes the bug.

Acceptance criteria:

- Equivalent keys work correctly.
- Broken keys demonstrate why the contract matters.
- Notes explain hash collisions and the Java 8 tree-bin behavior at a high level.

Interview prompts:

- How does `HashMap` find a value?
- What happens when many keys collide?
- What makes an object safe to use as a map key?

## JAVA-02: Domain Modeling With Records, Enums, and Sealed Types

Priority: `P0`

Scenario: Model a transaction authorization state machine.

Build:

- Define states such as `RECEIVED`, `APPROVED`, `DECLINED`, `REVERSED`.
- Define valid transitions.
- Reject invalid transitions with a domain exception.
- Use records for commands/events where they make the model clearer.
- Add tests for valid and invalid transitions.

Acceptance criteria:

- Invalid transitions cannot silently succeed.
- Tests describe the business rules.
- Notes explain where records help and where regular classes are better.

Interview prompts:

- When should a domain object be immutable?
- How do sealed classes help model finite variants?
- How do you prevent invalid states from existing?

## JAVA-03: Generics and Type Boundaries

Priority: `P1`

Scenario: Build a generic validator pipeline for different command types.

Build:

- Create a `Validator<T>` interface.
- Implement validators for at least two command records.
- Compose validators into a reusable pipeline.
- Add one example using `? extends` and one using `? super`.

Acceptance criteria:

- The pipeline is type-safe.
- Tests prove validators run in order.
- Notes explain type erasure and producer-extends-consumer-super.

Interview prompts:

- What is type erasure?
- Why are generic collections invariant?
- When do wildcards improve an API?

## JAVA-04: Exception Translation

Priority: `P1`

Scenario: Convert low-level failures into stable service-level errors.

Build:

- Define domain exceptions and service exceptions.
- Simulate a repository failure.
- Translate it into a meaningful service exception.
- Add tests that ensure implementation details do not leak.

Acceptance criteria:

- Callers see stable exceptions.
- Logs can still retain useful diagnostic information.
- Notes explain checked vs unchecked exception trade-offs.

Interview prompts:

- When should you define custom exceptions?
- How do exceptions affect API boundaries?
- How do you avoid swallowing useful debugging context?

## JAVA-05: JVM Memory and Leak Simulation

Priority: `P0`

Scenario: A service has a cache that grows until memory pressure causes latency spikes.

Build:

- Implement a naive unbounded cache.
- Write a test or small runner that demonstrates unbounded growth.
- Replace it with bounded eviction behavior.
- Add metrics or simple counters for hit/miss/eviction.

Acceptance criteria:

- The bounded version has a fixed maximum size.
- Tests prove eviction happens.
- Notes explain heap, GC pressure, object retention, and how to investigate memory growth.

Interview prompts:

- What causes memory leaks in Java services?
- How do you investigate high GC pauses?
- What tools or signals would you check first?

## JAVA-06: Streams Versus Loops

Priority: `P2`

Scenario: Process a list of transactions into grouped totals and suspicious records.

Build:

- Implement the logic once with loops and once with streams.
- Add tests proving equivalent output.
- Benchmark or reason about readability and allocation trade-offs.

Acceptance criteria:

- Both versions pass the same tests.
- Notes state which version you would keep and why.

Interview prompts:

- When do streams improve code?
- When do streams hide complexity?
- What performance pitfalls can streams introduce?

