# Glossary

Plain-English definitions for every piece of jargon the exercise catalog uses, with a pointer to the exercise where you meet it. Skim it once, then come back whenever a term blocks you.

## Java and the JVM

- **JVM** — the Java Virtual Machine, the program that runs your compiled Java code. It manages memory for you (see *garbage collection*).
- **Heap** — the region of memory where objects live. When people say a service "leaks memory," they mean the heap keeps growing. (`JAVA-05`)
- **Garbage collection (GC)** — the JVM automatically frees objects nothing refers to anymore. "GC pressure" means so much garbage is created that collection pauses start hurting latency. (`JAVA-05`)
- **`equals`/`hashCode` contract** — two objects that are "equal" must return the same hash code, or hash-based collections like `HashMap` silently misbehave. (`JAVA-01`)
- **Record** — a concise Java class for immutable data: `record Point(int x, int y) {}` generates constructor, accessors, `equals`, `hashCode`, and `toString`. (`JAVA-01`, `JAVA-02`)
- **Immutable** — an object whose state cannot change after construction. Immutable objects are trivially thread-safe and safe to use as map keys. (`ONB-01`, `JAVA-02`)
- **Sealed types** — a class/interface that lists exactly which types may extend it, so the compiler knows every possible variant. (`JAVA-02`)
- **Generics / type erasure** — generics (`List<String>`) are checked at compile time but erased at runtime; the JVM just sees `List`. (`JAVA-03`)
- **Checked vs unchecked exceptions** — checked exceptions must be declared or caught (`IOException`); unchecked (`RuntimeException`) propagate freely. Modern services mostly use unchecked. (`JAVA-04`)
- **Stack trace** — the list of method calls active when an exception was thrown, printed top (throw site) to bottom (program start). (`ONB-03`)
- **`BigDecimal`** — exact decimal arithmetic. Used for money because `double` cannot represent most decimal fractions exactly. (`ONB-02`)

## Concurrency

- **Race condition** — two threads touch the same data and the result depends on timing. "Check-then-act" (`if (!map.containsKey(k)) map.put(k, v)`) is the classic form. (`CONC-01`)
- **`ConcurrentHashMap#computeIfAbsent`** — an atomic "get, or compute and insert" — the compute runs at most once per key even under concurrency. (`CONC-01`)
- **Thread pool** — a fixed set of worker threads that execute submitted tasks, so you don't create a thread per task. Sizing depends on whether work is CPU-bound or spends its time blocked on I/O. (`CONC-02`)
- **Virtual threads** — lightweight JVM-managed threads (Java 21). You can have millions, so blocking I/O no longer requires careful pool sizing. (`CONC-06`)
- **Pinning** — a virtual thread stuck to its carrier (real) thread, e.g. while inside a `synchronized` block on older JDKs, defeating the benefit. (`CONC-06`)
- **Deadlock** — thread A holds lock 1 and wants lock 2; thread B holds 2 and wants 1. Both wait forever. (`CONC-05`)
- **Timeout** — the maximum time you will wait for a call before giving up. Every network call needs one. (`CONC-03`)
- **Retry with backoff and jitter** — retrying a failed call after a growing delay (*backoff*) plus randomness (*jitter*) so thousands of clients don't retry in lockstep. (`CONC-03`, `WAR-02`)
- **Circuit breaker** — after enough consecutive failures, stop calling the failing dependency for a while (fail fast), then probe cautiously. States: CLOSED → OPEN → HALF_OPEN. (`CONC-04`)
- **Bulkhead** — isolating resources (e.g. separate thread pools) so one slow dependency can't consume everything.

## Spring

- **Bean** — an object Spring creates and manages for you.
- **Dependency injection (DI)** — instead of `new`-ing collaborators, you declare them as constructor parameters and Spring supplies them. Makes swapping real/fake implementations trivial in tests. (`SPR-01`)
- **Auto-configuration** — Spring Boot's "if the jar is on the classpath, configure it" behavior; why an empty Boot app already serves HTTP. (`SPR-03`)
- **DTO (Data Transfer Object)** — a plain object that defines your API's request/response shape, kept separate from internal domain objects. (`SPR-01`)
- **Controller / service / domain layering** — controllers translate HTTP, services orchestrate, domain objects hold business rules. Keeps business logic testable without a web server. (`SPR-01`)
- **Proxy** — Spring wraps your bean in a generated object to add behavior (transactions, caching). Calling a method *on yourself* skips the proxy — the famous `@Transactional` self-invocation trap. (`SPR-04`)
- **`@Transactional`** — wraps a method in a database transaction; commits on success, rolls back on runtime exceptions (by default — a favorite interview detail). (`DATA-02`, `SPR-04`)
- **Profile** — a named configuration set (`local`, `test`, `prod`) so the same jar behaves correctly per environment. (`SPR-02`)
- **Actuator** — Spring Boot's built-in operational endpoints: health, metrics, info. (`PROD-01`)
- **Slice test** — a test that starts only one layer, e.g. `@WebMvcTest` (web only) or `@DataJpaTest` (persistence only), for speed and focus. (`TEST-02`)
- **JWT / resource server** — a signed token carrying identity and permissions; a resource server validates the signature locally instead of calling the auth server per request. (`SPR-08`)

## Persistence

- **JPA / Hibernate** — the standard Java object-relational mapping: entities are classes mapped to tables; Hibernate is the dominant implementation.
- **Persistence context** — Hibernate's per-transaction memory of every entity it has loaded; the machinery behind dirty checking. (`DATA-01`)
- **Dirty checking** — modify a loaded ("managed") entity inside a transaction and Hibernate writes the change automatically on commit — no explicit `save` call. Detached entities don't get this. (`DATA-01`)
- **N+1 query problem** — loading a list with one query, then lazily loading a relation per row: 1 + N queries. Invisible locally, deadly at scale. (`DATA-03`)
- **Optimistic locking** — a version column; an update only succeeds if the version hasn't changed, otherwise someone else got there first and you get an exception instead of a *lost update*. (`DATA-04`)
- **Pessimistic locking** — taking a database lock up front (`SELECT ... FOR UPDATE`) so others wait. Safer, slower, can deadlock.
- **Connection pool (HikariCP)** — a small set of reused database connections. When all are busy, requests queue and then time out — *pool exhaustion*. (`DATA-07`, `WAR-01`)
- **Index / composite index** — a sorted structure for fast lookup. Composite index column order matters: an index on `(merchant_id, created_at)` helps `WHERE merchant_id = ?` but not `WHERE created_at = ?` alone. (`DATA-05`)
- **Cardinality** — how many distinct values a column has. High-cardinality columns filter well; low-cardinality ones (like a 3-value status) rarely justify an index alone.
- **Cursor pagination** — "give me rows after this (timestamp, id)" instead of `OFFSET 100000`, which forces the database to walk and discard 100k rows. (`DATA-06`)
- **Testcontainers** — a library that starts a real database in Docker for your tests, so you test against PostgreSQL behavior instead of H2's approximation. (`TEST-03`)

## Distributed Systems and Messaging

- **Idempotency** — doing an operation twice has the same effect as once. The core defense against retries and duplicate messages: "charge card" is not idempotent; "charge card for request X (if not already done)" is. (`ONB-01`'s big sibling: `JAVA-01`, `CONC-01`, `DIST-02`, `DESIGN-02`)
- **At-least-once delivery** — the broker guarantees delivery by being willing to deliver duplicates. Consumers must therefore be idempotent. (`DIST-02`)
- **Transactional outbox** — write the domain change *and* an "event to publish" row in the same database transaction, then publish from that table; solves "DB committed but event lost." (`DIST-01`)
- **Partition / ordering key** — messages with the same key go to the same partition, preserving their order; different keys process in parallel. (`DIST-03`)
- **Dead-letter queue (DLQ)** — where messages go after exhausting retries, so one *poison message* can't block everything behind it. (`DIST-04`)
- **Saga** — a multi-step workflow across services where each step has a *compensating action* to undo it, instead of one big distributed transaction. (`DIST-05`)
- **Rate limiting / token bucket** — capping request rates. A token bucket refills at a steady rate and allows short bursts up to its capacity. (`DIST-06`)
- **Fail open vs fail closed** — when a safety mechanism (rate limiter, auth cache) is itself down: allow traffic (open) or block it (closed)? A deliberate policy decision, not an accident. (`DIST-06`)

## Observability and Production

- **Liveness vs readiness** — liveness: "restart me if this fails"; readiness: "don't send me traffic yet." Confusing them causes restart loops or dropped traffic. (`PROD-01`)
- **Correlation ID** — a unique ID attached to a request at the edge and included in every log line and downstream call so you can follow one request across systems. (`PROD-02`)
- **MDC** — the per-thread map that Java logging frameworks use to stamp values (like correlation IDs) onto every log line automatically. (`PROD-02`)
- **Metrics vs logs vs traces** — metrics: cheap aggregated numbers over time (error rate); logs: discrete events with detail; traces: the timing tree of one request across services. (`PROD-03`, `PROD-06`)
- **p99 latency** — the response time that 99% of requests beat. Averages hide pain: an average of 50ms with a p99 of 4s means 1 in 100 users suffers. (`PROD-03`, `WAR-06`)
- **Cardinality explosion (metrics)** — tagging a metric with an unbounded value (user ID, URL with IDs) creates millions of time series and kills your metrics system. (`PROD-03`)
- **Cache stampede / thundering herd** — a hot cache entry expires and thousands of requests simultaneously hit the slow backend to rebuild it. (`SPR-07`, `WAR-04`)
- **TTL (time to live)** — how long a cache entry or record is valid before it expires.
- **Runbook** — the written playbook for handling a specific incident: detection, mitigation, diagnosis, communication. (`PROD-05`)
- **PCI DSS** — the card-industry security standard; the reason full card numbers (PANs) must never appear in logs. (`ONB-01`)
- **SLO / NFR** — Service Level Objective (target like "99.9% of requests under 200ms") / Non-Functional Requirement (latency, availability, consistency, security — the requirements that shape architecture). (`DESIGN-01`)
