# Visa Staff Software Engineer Java/Spring Interview Learning Plan

## Target Role Summary

This plan targets Visa's Staff Software Engineer Java/Spring role, with emphasis on backend service design, hands-on Java/Spring development, production ownership, and staff-level technical leadership.

The role signals the following preparation themes:

- Java backend depth: Java, Spring Boot, JPA/Hibernate, RESTful services, testing, and automation.
- Distributed systems: scalable/fault-tolerant components, microservices, event-driven architecture, Kafka or MQ, and non-functional requirements.
- Data: relational and NoSQL databases, SQL query optimization, transaction boundaries, and data-aware service design.
- Production engineering: observability, telemetry, on-call, incident response, CI/CD, Docker, Kubernetes, and cloud deployment.
- Security and quality: secure coding, design reviews, code reviews, automated tests, and vulnerability awareness.
- Staff-level behavior: translating functional requirements into system designs, mentoring engineers, raising engineering standards, and communicating trade-offs clearly.

Sources used:

- Visa requisition: https://visa.wd5.myworkdayjobs.com/en-US/Visa/job/Staff-Software-Engineer_REF078668W
- Indexed copy of the Java/Spring Boot requisition: https://jobs.georgiafintechacademy.org/companies/visa-2/jobs/62268094-staff-software-engineer-java-spring-boot
- Related Visa Staff Java/Spring posting with distributed systems details: https://builtin.com/job/staff-software-engineer-7-years-java-springboot/7929824
- Spring Boot docs: https://spring.io/projects/spring-boot and https://docs.spring.io/spring-boot/system-requirements.html
- Oracle Java downloads/support context: https://www.oracle.com/java/technologies/downloads/ and https://www.oracle.com/java/technologies/java-se-support-roadmap.html

## Assumptions

- You have roughly 5 years of engineering experience and do not need beginner Java syntax, CRUD tutorials, or "what is REST" level material.
- You are rusty in Java/Spring, so the plan rebuilds fluency through targeted implementation, debugging, and explanation drills.
- Time budget: 6 to 8 weeks, 8 to 12 hours per week. If the interview is sooner, use the crash track below.
- Practical stack: Java 17 or 21, Spring Boot 3.x as the main practice baseline, with awareness that Java 25 is the current LTS and Spring Boot 4.x exists. Most enterprise interviews will still value Java 17/21 and Spring Boot 3.x fluency more than memorizing newest APIs.

## Prep Strategy

Recommended approach: build one production-shaped backend while studying each concept in context.

Why this approach works:

- Staff interviews reward explanation through trade-offs, not trivia.
- Spring concepts stick faster when tied to working code.
- A payments-style capstone lets you practice the domain language Visa cares about: authorization, idempotency, fraud checks, resilience, auditability, and consistency.

Alternatives:

- Topic-only study: faster at first, but weaker for design and debugging conversations.
- LeetCode-heavy study: useful for fundamentals, but incomplete for this role unless paired with Spring, systems, and production ownership.
- Course-first study: structured, but too passive unless converted into code, notes, and mock interviews.

## Capstone Project

Build a "Payment Authorization Platform" over the prep period. Keep it small enough to finish, but realistic enough to discuss in an interview.

Core services:

- `merchant-service`: merchant registration, API keys, limits, and risk settings.
- `authorization-service`: payment authorization endpoint, idempotency keys, validation, fraud/risk decision, and transaction state machine.
- `ledger-service`: append-only ledger entries and reconciliation-friendly queries.
- `notification-service`: consumes events and simulates downstream integrations.

Suggested infrastructure:

- Java 21 or Java 17.
- Spring Boot 3.x.
- PostgreSQL for relational data.
- Redis for idempotency/cache experiments.
- Kafka or RabbitMQ for event-driven flows.
- Docker Compose for local development.
- Testcontainers for integration tests.
- Micrometer/Actuator for metrics and health.
- GitHub Actions or local CI script.

Do not overbuild the UI. This role is backend-heavy; REST APIs, tests, docs, and operational behavior matter more.

## Weekly Roadmap

### Week 1: Java Refresh for Senior Interviews

Goal: recover Java fluency and be able to explain language/runtime decisions.

Study:

- Collections internals: `HashMap`, `ConcurrentHashMap`, `ArrayList`, `LinkedList`, `TreeMap`.
- Equality and hashing: `equals`, `hashCode`, immutability, records.
- Generics: invariance, wildcards, type erasure, bounded types.
- Exceptions: checked vs unchecked, exception translation, meaningful domain exceptions.
- Streams and optionals: when they clarify code and when they obscure it.
- Modern Java: records, sealed classes, pattern matching, text blocks, switch expressions.
- JVM basics: heap, stack, GC, JIT, class loading, memory leaks, thread dumps.
- Concurrency: executors, futures, completable futures, locks, atomics, thread pools, virtual threads at a conceptual level.

Build:

- Implement a small transaction state machine using records/enums/sealed interfaces where appropriate.
- Write tests for invalid transitions.
- Add one concurrent idempotency-key simulation using `ConcurrentHashMap` or database uniqueness.

Interview drills:

- Explain how `HashMap` works and why bad `hashCode` hurts performance.
- Explain the Java memory model enough to discuss visibility and race conditions.
- Compare platform threads, thread pools, and virtual threads.
- Debug a memory leak from a growing cache.
- Explain why immutability helps in concurrent systems.

Deliverable:

- `notes/week-01-java-refresh.md`
- A working state-machine package with tests.

### Week 2: Spring Core and Spring Boot Internals

Goal: understand what Spring is doing for you and where the abstractions can fail.

Study:

- IoC container, beans, scopes, lifecycle, `@Configuration`, `@Bean`, component scanning.
- Dependency injection styles and why constructor injection is preferred.
- Auto-configuration, starters, conditional beans, configuration properties.
- Profiles, externalized configuration, environment variables, secrets hygiene.
- Spring Boot application startup flow.
- AOP and proxies: why self-invocation bypasses proxy behavior.
- Validation: Jakarta Bean Validation, request validation, service-level validation.
- Error handling: `@ControllerAdvice`, problem details, consistent error contracts.

Build:

- Create the `authorization-service`.
- Add request/response DTOs, validation, controller/service/repository layers.
- Use `@ConfigurationProperties` for limits and risk settings.
- Add structured error responses.

Interview drills:

- What happens after `SpringApplication.run()`?
- What is auto-configuration and how do you override it safely?
- Why can `@Transactional` fail on self-invocation?
- What is the difference between a Spring bean and a plain Java object?
- How would you design a consistent error response contract for public APIs?

Deliverable:

- `notes/week-02-spring-core.md`
- First REST endpoint with validation and error handling.

### Week 3: REST API Design, Security, and Testing

Goal: demonstrate production-grade API thinking.

Study:

- REST resource modeling, status codes, idempotency, pagination, filtering, versioning.
- API compatibility: additive vs breaking changes.
- Authentication vs authorization.
- Spring Security filter chain basics.
- JWT/OAuth2 resource server concepts.
- Secure coding: input validation, output encoding, secrets, least privilege, dependency vulnerabilities.
- Testing pyramid: unit, slice, integration, contract, end-to-end.
- Spring test slices: `@WebMvcTest`, `@DataJpaTest`, `@SpringBootTest`.
- MockMvc, Testcontainers, Mockito, AssertJ.

Build:

- Add idempotent `POST /authorizations`.
- Add `GET /authorizations/{id}`.
- Add basic authentication or JWT resource-server-style scaffolding.
- Add unit tests for domain logic, `@WebMvcTest` for API behavior, and Testcontainers integration tests for persistence.

Interview drills:

- How do you make POST idempotent?
- When do you return 400, 401, 403, 404, 409, 422, and 500?
- How do Spring Security filters work at a high level?
- What should be tested with unit tests vs integration tests?
- How do you test failure paths without brittle tests?

Deliverable:

- `notes/week-03-api-security-testing.md`
- End-to-end happy path and failure path tests.

### Week 4: Persistence, JPA/Hibernate, and SQL

Goal: handle senior-level persistence questions without hand-waving.

Study:

- JPA entity lifecycle: transient, managed, detached, removed.
- Persistence context, dirty checking, flushing.
- Transactions: propagation, isolation, rollback rules, read-only transactions.
- Lazy vs eager loading, N+1 queries, fetch joins, entity graphs.
- Optimistic vs pessimistic locking.
- Schema migration with Flyway or Liquibase.
- Indexes, query plans, cardinality, covering indexes, composite index order.
- SQL tuning basics: `EXPLAIN`, join strategy, pagination trade-offs.
- Relational vs NoSQL trade-offs.

Build:

- Add PostgreSQL persistence for authorizations and ledger entries.
- Add Flyway migrations.
- Add optimistic locking to a mutable aggregate.
- Create a deliberate N+1 query, detect it, and fix it.
- Add indexes for lookup by merchant, status, and created time.

Interview drills:

- Explain transaction isolation levels using payment examples.
- How does Hibernate dirty checking work?
- What causes N+1 and how do you detect/fix it?
- When would you choose optimistic vs pessimistic locking?
- How do you design a database table for high-volume transaction lookup?

Deliverable:

- `notes/week-04-persistence-sql.md`
- Database-backed service with migrations and query tests.

### Week 5: Distributed Systems and Messaging

Goal: speak clearly about reliability, consistency, and event-driven services.

Study:

- Synchronous vs asynchronous communication.
- Kafka/RabbitMQ/MQ concepts: topics, partitions, offsets, consumer groups, retries, dead-letter queues.
- Delivery semantics: at-most-once, at-least-once, effectively-once through idempotent consumers.
- Outbox pattern and transactional event publication.
- Saga/process manager patterns.
- Backpressure, retries, timeouts, circuit breakers, bulkheads.
- Consistency models and payment-specific trade-offs.

Build:

- Publish `AuthorizationApproved` and `AuthorizationDeclined` events.
- Add an outbox table and background publisher.
- Add `notification-service` consumer.
- Make the consumer idempotent.
- Add retry/dead-letter behavior.

Interview drills:

- How would you avoid losing events after a database commit?
- What does "exactly once" really mean in distributed systems?
- How do Kafka partitions affect ordering and scalability?
- How would you design retries without creating duplicate payments?
- How do you handle downstream service slowness?

Deliverable:

- `notes/week-05-distributed-messaging.md`
- Working async flow with idempotent event handling.

### Week 6: Observability, Performance, and Production Readiness

Goal: think like an owner of a high-availability service.

Study:

- Spring Boot Actuator: health, metrics, info, readiness/liveness.
- Micrometer metrics: counters, timers, gauges, tags, cardinality risks.
- Logging: correlation IDs, structured logs, sensitive data redaction.
- Tracing concepts: spans, propagation, sampling.
- Performance profiling: CPU, memory, allocation, DB latency.
- Thread dumps and heap dumps.
- Rate limiting and load shedding.
- SLOs, SLIs, error budgets.
- Incident response: detect, mitigate, communicate, follow-up.

Build:

- Add Actuator endpoints.
- Add request correlation ID.
- Add metrics for authorization latency, approval rate, decline reasons, and downstream publish failures.
- Add a simple load test script.
- Add a runbook for high latency and failed event publishing.

Interview drills:

- What metrics would you expose for a payment authorization API?
- How do you debug p99 latency?
- What is the difference between liveness and readiness?
- How do you prevent logs from leaking sensitive data?
- Walk through how you would handle a production incident.

Deliverable:

- `notes/week-06-prod-readiness.md`
- Runbook and metrics dashboard notes.

### Week 7: Cloud, Containers, CI/CD, and Engineering Leadership

Goal: connect code to delivery and staff-level execution.

Study:

- Docker image layering, JVM container memory, health checks.
- Kubernetes basics: deployments, services, config maps, secrets, probes, resource requests/limits.
- CI/CD pipeline stages: compile, test, static analysis, image build, security scan, deploy.
- Feature flags and rollback strategies.
- Design docs and architecture decision records.
- Code review at staff level: correctness, maintainability, security, operability, testability.
- Mentorship: raising standards without taking over.

Build:

- Add Dockerfiles and Docker Compose.
- Add a CI workflow or local `make verify` script.
- Add ADRs for persistence choice, messaging choice, and idempotency strategy.
- Review your own code as if reviewing a teammate's PR.

Interview drills:

- Design a CI/CD pipeline for a Spring Boot service.
- How do you roll back a bad deployment safely?
- How do resource limits affect JVM apps in Kubernetes?
- What do you look for in a staff-level code review?
- Tell me about a time you improved engineering standards.

Deliverable:

- `notes/week-07-delivery-leadership.md`
- `docs/adr/` records for major design decisions.

### Week 8: Interview Simulation and Gap Closure

Goal: convert knowledge into crisp interview performance.

Activities:

- Two Java/Spring deep-dive mock interviews.
- Two system design mocks.
- One behavioral/leadership mock.
- One debugging exercise: broken transaction, slow query, failing consumer, or security misconfiguration.
- Final capstone walkthrough in 20 minutes.

Finalize:

- One-page project architecture summary.
- One-page Visa role fit summary.
- STAR stories for ownership, ambiguity, mentorship, incident response, conflict, and technical trade-off.
- A short list of honest gaps and how you are addressing them.

## Crash Track: If the Interview Is Within 10 Days

Use this if time is short:

Day 1:

- Java collections, concurrency, JVM, modern Java.
- Drill 20 Java questions.

Day 2:

- Spring Boot internals: IoC, auto-configuration, configuration, validation, errors.
- Build one REST endpoint.

Day 3:

- Spring MVC, REST design, security basics, test slices.
- Add MockMvc and integration tests.

Day 4:

- JPA/Hibernate, transactions, N+1, locking, SQL optimization.
- Drill persistence questions.

Day 5:

- Kafka/MQ, outbox, idempotency, retries, distributed consistency.
- Design async payment event flow.

Day 6:

- Observability, Actuator, metrics, logs, tracing, incident response.
- Write a runbook.

Day 7:

- System design: payment authorization platform.
- Practice design aloud twice.

Day 8:

- Coding: Java data structure/problem-solving practice plus Spring debugging.

Day 9:

- Behavioral/staff stories.
- Prepare examples for mentorship, design leadership, ambiguity, production issue, and trade-off.

Day 10:

- Full mock interview.
- Review weak spots only.

## Senior Java Question Bank

Use these as active recall prompts. For each question, answer in 2 minutes, then deepen with examples.

### Java Language and Runtime

1. How does `HashMap` work internally? What changed in Java 8 regarding tree bins?
2. What makes a class safe to use as a map key?
3. Explain `equals`/`hashCode` contracts and a failure mode you have seen.
4. Compare `ArrayList`, `LinkedList`, `HashSet`, `TreeSet`, and `ConcurrentHashMap`.
5. What is type erasure? Why can it surprise people?
6. Explain producer extends / consumer super.
7. When should you use records? When should you avoid them?
8. How do sealed classes improve domain modeling?
9. What is the Java memory model?
10. What does `volatile` guarantee and not guarantee?
11. Compare `synchronized`, `ReentrantLock`, atomics, and concurrent collections.
12. How do you size a thread pool?
13. What are virtual threads good for? What are they not good for?
14. How would you debug a deadlock?
15. How would you debug high GC pauses?
16. What are common causes of memory leaks in Java services?
17. Explain strong, soft, weak, and phantom references at a practical level.
18. How does exception handling influence API boundaries?
19. When are streams less readable than loops?
20. How do you design immutable domain objects?

### Spring and Spring Boot

1. What problem does dependency injection solve?
2. Explain the Spring bean lifecycle.
3. What is auto-configuration?
4. How do conditional beans work conceptually?
5. What is the difference between `@Component`, `@Service`, `@Repository`, and `@Configuration`?
6. Why is constructor injection preferred?
7. How do profiles and configuration properties work?
8. What happens during Spring Boot startup?
9. How do Spring proxies affect `@Transactional`, `@Async`, and AOP?
10. Why does self-invocation bypass transactional behavior?
11. How would you structure controller, service, repository, and domain layers?
12. How do you design consistent API error responses?
13. How does request validation work?
14. What is the Spring Security filter chain?
15. How do you secure Actuator endpoints?
16. When would you use `@SpringBootTest` vs `@WebMvcTest`?
17. How do you test repositories reliably?
18. What should be mocked and what should be real in service tests?
19. How do you manage secrets in Spring configuration?
20. How do you safely override auto-configured beans?

### JPA, Hibernate, and Data

1. Explain the JPA persistence context.
2. What is dirty checking?
3. When does Hibernate flush?
4. Explain lazy loading and common pitfalls.
5. What causes `LazyInitializationException`?
6. What is the N+1 query problem?
7. How do fetch joins differ from entity graphs?
8. Explain transaction propagation modes you have actually used.
9. Explain isolation levels with examples.
10. What causes lost updates?
11. How does optimistic locking work?
12. When would pessimistic locking be justified?
13. How do you design indexes for query patterns?
14. How do you read an `EXPLAIN` plan?
15. Offset pagination vs cursor pagination: trade-offs?
16. How do migrations fit into CI/CD?
17. How do you handle schema changes with zero downtime?
18. When would NoSQL be a better fit?
19. How do you avoid leaking persistence models into API contracts?
20. What data should be immutable in a payment system?

### Distributed Systems and Production

1. How do you make a payment authorization request idempotent?
2. What is the outbox pattern?
3. How do you prevent duplicate event processing?
4. Explain at-least-once delivery and its implications.
5. How does Kafka partitioning affect ordering?
6. How would you handle a slow downstream dependency?
7. Compare timeout, retry, circuit breaker, and bulkhead.
8. How do you choose retry policies?
9. What is backpressure?
10. What is eventual consistency, and when is it acceptable?
11. How would you design auditability for payment flows?
12. How would you migrate a synchronous workflow to async?
13. What metrics should a payment service expose?
14. What logs should never contain?
15. How do you debug p99 latency?
16. What is a good health check?
17. How do liveness and readiness differ?
18. How do you run an incident review?
19. What makes a service operable?
20. How would you design for regional failure?

### Staff-Level Leadership

1. How do you turn ambiguous requirements into a design?
2. How do you identify non-functional requirements?
3. How do you communicate trade-offs to product and architecture stakeholders?
4. How do you lead design without being the manager?
5. How do you mentor junior engineers while keeping delivery moving?
6. How do you handle disagreement in a design review?
7. What do you look for in code reviews beyond style?
8. How do you raise quality standards on a team?
9. Tell me about a time you improved reliability.
10. Tell me about a time you made a technical trade-off you later revisited.

## System Design Prompts

Practice these aloud with a whiteboard or Markdown doc. Use the same structure every time: requirements, NFRs, APIs, data model, architecture, failure modes, observability, security, trade-offs.

1. Design a payment authorization API for merchants.
2. Design idempotency for duplicate payment requests.
3. Design a fraud/risk scoring integration with strict latency limits.
4. Design a transaction ledger with auditability and reconciliation.
5. Design a notification/eventing system for payment status changes.
6. Design rate limiting for merchant APIs.
7. Design a migration from a monolith to payment microservices.
8. Design a high-volume transaction search API.
9. Design a rollback strategy for a bad release in a payment service.
10. Design observability for an on-call-owned Spring Boot service.

## Answer Frameworks

Use these to sound senior and structured.

For Java/Spring deep dives:

1. Start with the mechanism.
2. Explain why it exists.
3. Give a production failure mode.
4. State how you would test or monitor it.
5. Mention trade-offs.

Example:

> `@Transactional` is usually applied through a Spring proxy. Calls that enter through the proxy get transaction advice, but self-invocation within the same class bypasses the proxy. In production, that can cause writes to happen outside the expected transaction boundary. I avoid this by keeping transactional methods at service boundaries, testing rollback behavior, and watching for persistence code hidden inside private helper flows.

For system design:

1. Clarify functional requirements.
2. Name non-functional requirements.
3. Define APIs and data contracts.
4. Draw components and data flow.
5. Discuss consistency, failure, security, and observability.
6. Call out what you would defer.

For behavioral questions:

1. Situation.
2. Task.
3. Action.
4. Result.
5. Reflection: what you would repeat or improve.

## Daily Practice Template

Use this 90-minute block on weekdays:

- 10 minutes: active recall from the question bank.
- 25 minutes: read docs or notes for one focused topic.
- 35 minutes: implement or test one small thing.
- 10 minutes: write what broke and how you fixed it.
- 10 minutes: answer one interview question aloud.

Weekend 3-hour block:

- 60 minutes: capstone implementation.
- 45 minutes: system design practice.
- 45 minutes: debugging/performance/testing drill.
- 30 minutes: notes cleanup and next-week planning.

## Quality Bar

By the end, you should be able to:

- Build and explain a Spring Boot REST service without tutorial scaffolding.
- Explain Spring Boot auto-configuration, dependency injection, transactions, testing, and security at a practical level.
- Debug common Java/Spring production issues: slow SQL, N+1, transaction boundary bugs, bad retries, high latency, memory pressure, and failing consumers.
- Design idempotent, observable, secure payment-style APIs.
- Discuss event-driven architecture, outbox, Kafka/MQ, retries, and consistency trade-offs.
- Lead a design conversation with clear trade-offs and non-functional requirements.
- Review code at a staff level: correctness, maintainability, testability, security, operability, and scalability.

## First Three Actions

1. Create a minimal Spring Boot service with one `POST /authorizations` endpoint and validation.
2. Write `notes/week-01-java-refresh.md` with answers to 10 Java question-bank prompts.
3. Schedule two mock interviews: one Java/Spring deep dive and one payment authorization system design.

