# Design Patterns and OOAD

The posting asks for "understanding of OOAD and software design patterns" — and pattern questions are a staple of Java interviews at every level. The staff-level twist: you are not asked to *recite* patterns, you are asked to justify them ("why is this a Strategy and not a switch?"), spot them in frameworks you already use, and know when a pattern is overkill.

Each wired exercise here implements one pattern in a payments scenario. The point of every one is the *why* paragraph in its Javadoc — read it before coding.

## PATT-01: Strategy — Interchangeable Fee Policies

Priority: `P0`

Scenario: Each card product prices differently (flat, percentage, percentage-with-minimum), and checkout code must not care which.

Build:

- Implement the three `FeePolicy` strategies and their selection in `FeeService` (`exercises/patt01/`).

How: `FeeServiceTest` is the spec — delete `@Disabled`, make it green.

Acceptance criteria:

- All tests pass.
- You can articulate Strategy vs a `switch`: independent testability, Open/Closed, runtime swapping — and when the `switch` is actually fine (few stable cases; note Java's sealed types + pattern matching make that a safe choice too).

Interview prompts:

- Where does Spring hand you Strategy without naming it? (Injecting an interface with multiple implementations.)
- When is Strategy over-engineering?
- How would you add a market-specific override without touching existing policies?

## PATT-02: Builder — Assembling an Immutable Request

Priority: `P0`

Scenario: A payment request has required fields, optional fields, and defaults; telescoping constructors and setters both fail it.

Build:

- Implement the fluent `PaymentRequest.Builder` (`exercises/patt02/`): order-free setters, defaults, validation at `build()` naming the offending field.

How: `PaymentRequestTest` is the spec.

Acceptance criteria:

- All tests pass.
- You can say why validation belongs in `build()` (one choke point; no half-valid object ever exists) and how this connects to `ONB-01`'s "make invalid states unrepresentable."

Interview prompts:

- Builder vs constructor vs setters vs records — when each?
- Where have you seen Builder in the wild? (`UriComponentsBuilder`, `HttpRequest.newBuilder`, Lombok `@Builder`.)
- How do records with static factories reduce the need for builders?

## PATT-03: Observer — Decoupled Event Notification

Priority: `P1`

Scenario: Ledger, notifications, and analytics all care when a transaction changes; the authorization code should not know any of them exist.

Build:

- Implement `TransactionEventBus` (`exercises/patt03/`): subscribe/unsubscribe/publish, subscription order preserved, one throwing listener never blocks the rest.

How: `TransactionEventBusTest` is the spec.

Acceptance criteria:

- All tests pass.
- You can trace the line from this bus → Spring's `ApplicationEventPublisher` → a real message broker (`DIST-*`), and say what changes at each step (process boundary, delivery guarantees, ordering).

Interview prompts:

- What goes wrong when observers do slow or failing work inline?
- Observer vs message queue — when do you cross that line?
- How does Spring's application event mechanism relate to this pattern?

## PATT-04: Decorator — Layering Behavior Around a Client

Priority: `P0`

Scenario: Logging and retry apply to any payment gateway; neither belongs inside the gateway, and subclassing would fix the combination at compile time.

Build:

- Implement `LoggingGateway` and `RetryingGateway` (`exercises/patt04/`); the stacking-order test is the heart of the exercise — log-around-retry observes one outcome, retry-around-log observes every attempt.

How: `GatewayDecoratorTest` is the spec.

Acceptance criteria:

- All tests pass.
- You can explain that Spring AOP proxies (`@Transactional` in `SPR-04`, `@Cacheable` in `SPR-07`) *are* generated decorators — same pattern, framework-made.

Interview prompts:

- Decorator vs inheritance for cross-cutting behavior?
- Why does decorator stacking order matter — give a real example.
- Connect this to how `@Transactional` works under the hood.

## PATT-05: Template Method — A Fixed Skeleton With Hooks

Priority: `P1`

Scenario: Every settlement file format goes parse → validate → apply, but each format parses and applies differently; no subclass may reorder the sequence.

Build:

- Implement the `final` template method `SettlementFileProcessor#process` (`exercises/patt05/`): blank lines ignored, invalid records rejected without applying, outcomes counted.

How: `SettlementFileProcessorTest` is the spec (it even asserts the method is `final` — that finality *is* the pattern).

Acceptance criteria:

- All tests pass.
- You can compare Template Method (inheritance) with Strategy (composition) and state the modern default: prefer composition; reach for Template Method when the *sequence* is the invariant being protected.

Interview prompts:

- Template Method vs Strategy — same problem, different mechanism. When each?
- Where do Spring and JUnit use Template Method? (`JdbcTemplate` callbacks, test lifecycle.)
- Why "prefer composition over inheritance," and when is that rule wrong?

## PATT-06: Patterns You Already Built — an OOAD Review

Priority: `P1` (written exercise)

Scenario: The strongest pattern answer in an interview is recognizing patterns in your own work, unprompted.

Build (note under `notes/`):

- Go back through this repo and name the patterns already in it: `JAVA-02` (State), `JAVA-03`'s `ValidatorPipeline` (Chain of Responsibility / Composite), `CONC-04` (State again, wearing a resilience hat), `ONB-01`/`JAVA-01` keys (Value Object), `SPR-01`'s service interface (Facade over the domain), Spring's proxies (Decorator/Proxy), `PATT-01..05` themselves.
- For each: one sentence on *why that pattern earned its place* — what concrete problem it solved.
- Add a SOLID pass: for each of the five principles, point at one place in the repo that honors it and one hypothetical change that would violate it.

Acceptance criteria:

- At least six pattern sightings with justifications.
- The SOLID section uses this repo's code, not textbook examples.

Interview prompts:

- Tell me about a time a design pattern genuinely improved a design — and one where it was ceremony.
- Which SOLID principle do you see violated most often in real codebases?
- How do you talk a teammate out of an unnecessary abstraction?
