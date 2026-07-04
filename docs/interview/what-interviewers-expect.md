# What Interviewers Actually Listen For

The same question gets asked at every level — what changes is the answer that impresses. This doc shows, per topic, the difference between a solid mid-level answer and the staff-level answer, plus the follow-up traps interviewers use to find your depth limit. Use it two ways: to calibrate your notes after each exercise, and as a final review the week of the interview.

A pattern worth internalizing first: **staff answers volunteer trade-offs, failure modes, and operational consequences without being asked.** Mid-level answers are correct; staff answers are correct *and* show you've been burned.

## "How does HashMap work?" (JAVA-01)

- **Mid:** hashCode picks a bucket, equals resolves collisions, resize at load factor.
- **Staff:** all of that, plus: what actually breaks in production — a mutable key that changes after insertion is *lost* (lookup walks the wrong bucket); a bad hashCode degrades to O(n) per bucket (Java 8 tree-bins cap it at O(log n) for Comparable keys); and the practical rule "map keys are immutable value objects, records make this free."
- **Traps:** "What if two unequal objects share a hashCode?" (fine — that's a collision). "What if equal objects have different hashCodes?" (broken — the contract, and *this* direction is the dangerous one). "Why must you not mutate a key?"

## Concurrency bugs (CONC-01)

- **Mid:** explains race conditions, knows `synchronized` and `ConcurrentHashMap`.
- **Staff:** names *check-then-act* as the shape of the bug, reaches for atomic compound operations (`computeIfAbsent`, `putIfAbsent`, CAS) before locks, and immediately asks how the guarantee survives more than one JVM — "in-process atomicity doesn't help behind a load balancer; now it's a database unique constraint or Redis."
- **Traps:** "Is `ConcurrentHashMap` enough to make *your code* thread-safe?" (no — it makes single map operations atomic, not your read-modify-write sequences). "What does `computeIfAbsent` guarantee if the function throws?" "Two service instances — now what?"

## Retries and timeouts (CONC-03, WAR-02)

- **Mid:** retry transient failures a bounded number of times with exponential backoff.
- **Staff:** retries are a *load multiplier applied during failure* — the worst possible time — so: jitter (or clients synchronize into waves), retry budgets, circuit breakers for persistent failure, timeout budgets that shrink down the call chain, and the prerequisite question "is this operation idempotent? If not, retrying is how you double-charge someone."
- **Traps:** "Your caller times out at 10s; your downstream call at 30s — what's wrong?" "When is a retry actively harmful?" "Retry on HTTP 400? On 503? On connection-refused vs read-timeout?" (the last one: connection-refused means the request never arrived — safe-ish; read-timeout means it may have been processed — idempotency required.)

## `@Transactional` (SPR-04, DATA-02)

- **Mid:** wraps the method in a transaction; rolls back on exceptions.
- **Staff:** rolls back on *unchecked* exceptions **by default** — checked exceptions commit unless you set `rollbackFor`; it works through a *proxy*, so self-invocation silently skips it (as do private and final methods); transaction boundaries belong on the service method that forms the unit of work, and everything inside them should be fast — no HTTP calls inside a transaction while a pool connection is pinned (see WAR-01).
- **Traps:** "Method A calls method B in the same class, B has `@Transactional` — what happens?" "A checked exception is thrown — commit or rollback?" "What does `REQUIRES_NEW` do to the outer transaction's connection?" (it holds *both* — a pool-exhaustion accelerant.)

## N+1 and JPA (DATA-01, DATA-03)

- **Mid:** lazy relation in a loop fires a query per row; fix with fetch join.
- **Staff:** frames it as a *detection* problem first ("invisible at dev data sizes — I assert query counts in tests or watch SQL logs"), knows three fixes and when each fits (fetch join — but it breaks pagination with collection joins; entity graphs; DTO projection which avoids entity overhead entirely for read paths), and refuses the tempting global fix ("eager loading everywhere trades N+1 for loading the object graph every time").
- **Traps:** "Why not make everything eager?" "Fetch join with pagination — what goes wrong?" (Hibernate paginates in memory — the classic.) "When would you *not* use entities for a read?"

## Design patterns and OOAD (PATT-01..06)

- **Mid:** can define Strategy, Builder, Observer, Decorator, Singleton and sketch the UML.
- **Staff:** *justifies* rather than recites — "this is a Strategy because the variation is one algorithm and I need it swappable per market; if it were three stable cases I'd use a sealed type and a switch"; spots patterns in the frameworks in front of them (`@Transactional` = generated Decorator, `JdbcTemplate` = Template Method, Spring events = Observer); and volunteers the failure mode of patterns — ceremony, indirection, AbstractSingletonProxyFactoryBean jokes — because knowing when *not* to is the senior half of the skill.
- **Traps:** "Show me this pattern in Spring." "Strategy vs Template Method — same problem, pick one and defend it." "Your teammate wrapped one `if` in a pattern with four classes — what do you say in review?" "Which SOLID principle does this violate?" (asked about *your* code from earlier in the interview.)

## AEM, Sling, and OSGi (AEM-01..08)

The stack this role actually names. The interviewer is usually testing whether you understand the *architecture*, not whether you've memorized console URLs.

- **Mid:** knows AEM is a CMS with author/publish tiers, components, templates, and a Dispatcher cache; can write a Sling Model.
- **Staff:** explains the three-layer architecture in one breath (JCR stores a content tree → Sling resolves URLs to resources and lets `sling:resourceType` pick the renderer → OSGi runs it all as swappable services); frames the big idea as "content drives routing — the opposite of Spring MVC"; knows why path-bound servlets are discouraged (they bypass resource resolution *and its access control*); treats the Dispatcher as both cache and security allowlist and can walk a stale-content incident (activation → invalidation → stat files → CDN on top); and talks about migrations as idempotent, resumable, section-by-section jobs with URL preservation — never big-bang.
- **Traps:** "Walk me through `GET /content/help/fees.json` end to end." "Why did `adaptTo` return null and how do you debug it?" "An editor says publish 'didn't work' — where do you look, in order?" (author replication queue → publish content → Dispatcher invalidation → CDN.) "What belongs in the JCR and what belongs in a database?" (content vs transactional data — putting a ledger in JCR is the wrong answer they're fishing for.)

## Idempotency (DESIGN-02, WAR-05)

The signature payments-company topic. Expect it.

- **Mid:** client sends an idempotency key; server stores it and skips duplicates.
- **Staff:** covers the full lifecycle unprompted — key scope (per logical operation, not per HTTP attempt), atomic reservation (unique constraint — two concurrent duplicates race, the database decides), storing the *response* for replay, same-key-different-payload → reject with 422, TTL/cleanup, and what happens if the server crashes *between* the business write and recording the key (answer: make them one transaction, or accept and reconcile).
- **Traps:** "Where does the key store live and what happens when Redis loses it?" "Same key, different amount?" "The two duplicate requests arrive 2ms apart — walk me through both threads."

## Messaging (DIST-01, DIST-02, DIST-04)

- **Mid:** knows producers/consumers, at-least-once delivery, DLQs exist.
- **Staff:** "exactly-once delivery doesn't exist end-to-end; you build exactly-once *effects* from at-least-once delivery + idempotent consumers"; the outbox pattern for the dual-write problem (DB committed, publish failed); ordering is per-partition-key only, and choosing the key is a real design decision (hot-key skew); DLQs need an *operational story* — alerting, inspection, replay — or they're a write-only graveyard.
- **Traps:** "The consumer crashes after the side effect but before the ack — what happens?" "Why can't the broker just deduplicate for me?" "What do you do with 10k messages in the DLQ after an outage?" (fix the cause, then replay — which is why handlers must stay idempotent even for replays.)

## Observability (PROD-02, PROD-03, WAR-06)

- **Mid:** logs, metrics, dashboards; knows p99 exists.
- **Staff:** designs signals *for the questions you'll ask at 3 AM* — RED metrics per endpoint, percentiles not averages (and knows percentiles can't be re-averaged across hosts), low-cardinality tags (a user-id tag = time-series explosion), correlation IDs propagated end-to-end, and traces as the tool that turns tail-latency guessing into reading. Also: alert on symptoms (SLO burn), not causes (CPU).
- **Traps:** "Average latency is 45ms, users complain of 3s — how?" "What tag would blow up your metrics system?" "Log line vs span — when is each the right tool?"

## System design (DESIGN-01..05)

- **Mid:** draws a reasonable architecture with the right boxes.
- **Staff:** interrogates requirements *first* (write QPS? read/write ratio? consistency needs? latency budget? what's the cost of being wrong — money movement changes every answer); states NFRs as numbers; names failure modes of their own design unprompted ("this outbox poller is my bottleneck at X msg/s; here's the sharding path"); makes reversible decisions cheaply and flags irreversible ones; and *scopes* — "for v1 I'd do the boring thing, here's the trigger for the fancy thing."
- **Traps:** "Your database is down — what does the user see?" "Which of your boxes falls over first at 10× traffic?" "You have two weeks and half the team — what do you cut?"

## Behavioral / staff signals (DESIGN-05, DESIGN-06)

Staff behavioral rounds probe *scope and judgment*, not just competence: influence without authority, disagreement handled with data and grace, mentorship with a measurable outcome, and ownership past the edge of your job description ("saw it, wasn't mine, fixed the system anyway"). Prepare 6–8 stories in situation–action–result–**reflection** form; the reflection ("what I'd do differently") is the staff marker most candidates skip. If you can, make one story a production incident you owned end to end — it feeds every question from "tell me about a hard bug" to "tell me about pressure."

## The Meta-Skill

In every technical answer, run this order: **direct answer → key trade-off → failure mode → how you'd verify in production.** Two sentences each. It's the difference between "knows the material" and "I'd trust them with the pager," and it is trainable — which is what the notes in this repo (`notes/_template.md`, "How I would explain this in an interview") are for.
