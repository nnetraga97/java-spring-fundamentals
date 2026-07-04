# Staff Java and Spring Exercises

This repository is a hands-on exercise workbook that takes you from Java/Spring beginner to staff-level backend interview readiness.

It focuses on high-signal backend interview topics: Java runtime depth, concurrency, Spring Boot internals, REST APIs, persistence, transactions, messaging, observability, production debugging, and system design trade-offs — with a strong bias toward *real production failure modes*, because that is what staff-level interviewers probe. It also covers the two extra areas the target role names explicitly: the Adobe AEM stack (Sling, JCR, OSGi — runnable via AEM Mocks, no AEM license needed) and design-pattern/OOAD fluency.

## Pick Your Track

- **New to Java or Spring (or rusty):** read [Getting Started](docs/guides/getting-started.md) (zero-assumption setup), then do the [Beginner On-Ramp](docs/exercises/00-beginner-onramp.md) (`ONB-01..04`). Keep the [Glossary](docs/guides/glossary.md) open in a tab and use the [progressive hints](docs/guides/hints.md) when stuck — one hint at a time.
- **Comfortable with Java, building depth:** follow the [difficulty ladder](docs/exercises/README.md#difficulty-ladder) through the main catalog, starting from the worked example `JAVA-01`.
- **Interview soon:** do the Suggested First Track below, run the [Production War Rooms](docs/exercises/09-production-war-rooms.md) drills out loud, and calibrate your answers against [What Interviewers Actually Listen For](docs/interview/what-interviewers-expect.md).

## Getting Started

The repository ships as a runnable Maven + Spring Boot project so you can start an exercise in seconds instead of scaffolding a build first.

Requirements: a JDK (21+; developed on JDK 25). Maven is bundled via the wrapper, so you do not need Maven installed.

```bash
./mvnw verify          # compile + run all tests
./mvnw spring-boot:run # start the app (local profile, in-memory H2)
./mvnw test -Dtest=IdempotencyCacheTest   # run one exercise's tests
```

Layout:

```text
src/main/java/com/nick/javafundamentals/exercises/<id>/  # production code, one folder per exercise
src/test/java/com/nick/javafundamentals/exercises/<id>/   # tests, same folder name as the code
notes/                                                     # one learning note per session
docs/exercises/                                            # the exercise catalog
```

Everything for one exercise sits in a folder named for its id, on both the code and
test side. `exercises/java02/` is `JAVA-02`; `exercises/spr01/` is `SPR-01`. Because
the code and test share the same package name, the test needs no import to reach the
code it drives. Two exceptions: `exercises/common/` holds types shared by more than
one exercise (currently just `Message`, used by `DIST-02` and `DIST-04`), and the
Spring context smoke test stays at the test root.

A lightweight self-review artifact now lives at
`src/main/resources/static/spring-review/`. Run `./mvnw spring-boot:run` and open
`http://localhost:8080/spring-review/` to review round-by-round Spring feedback and
corrected mental models in the browser.

Each exercise folder has a short `package-info.java` describing it. The **worked
reference example** is `JAVA-01` — see `exercises/java01/IdempotencyCache.java`, its
test `IdempotencyCacheTest`, and the matching note in `notes/`. Use it as the
template for the shape of an exercise: production code, focused tests, and a short
trade-off note.

Start a new exercise quickly:

```bash
./scripts/start-exercise.sh JAVA-03 generics-validator
# creates branch exercise/YYYY-MM-DD-generics-validator and a note stub
```

## How the Exercises Are Wired (Test-Driven)

Most exercises come with a **spec test that is the answer key**, plus a **stub class**
whose methods throw `UnsupportedOperationException("TODO ...")`. The test starts
`@Disabled` so `./mvnw verify` is green until you choose to begin.

To do an exercise:

1. Find its test (e.g. `AuthorizationStateMachineTest` for `JAVA-02`) and **delete the
   `@Disabled(...)` line**.
2. Run it and watch it go red:

   ```bash
   ./mvnw test -Dtest=AuthorizationStateMachineTest
   ```

3. Fill in the stub methods until the test is green. The test tells you exactly what
   behavior is expected — that is how you know your solution is correct.

Three kinds of exercise:

- **Spec tests** — a full failing test is provided as the answer key:
  `ONB-01/02`, `JAVA-02..06`, `CONC-01/03/04/06`, `DIST-02/04/06`, `SPR-01`
  (`@WebMvcTest`), `DATA-04` (`@DataJpaTest`), `AEM-01..05` (AEM Mocks),
  `PATT-01..05`.
- **Testing placeholders** (`TEST-01..04`): a skeleton where *you* write the
  assertions — that is the skill being practiced (see `exercises/test01/Test01DomainUnitTests`).
- **Written / demonstrate exercises**: no pass/fail test. This covers `DESIGN-*`,
  investigation-style ones (deadlock, slow query, index design), and
  "demonstrate framework behavior" ones where there is no class to build
  (`DATA-01` dirty checking, `SPR-04` proxy trap, `DATA-03` N+1). The deliverable
  is a note or design doc under `notes/`.

`JAVA-01` is fully solved as the reference. Everything wired with a spec test keeps
`./mvnw verify` green until you remove its `@Disabled` and start.

## Daily Workflow

1. Create a branch for the day.

   ```bash
   git checkout -b exercise/YYYY-MM-DD-topic-name
   ```

2. Pick 3 to 4 exercises from the catalog.
3. Implement the exercises in code, starting with tests when practical.
4. Write a short note with what broke, what you learned, and how you would explain the trade-offs in an interview.
5. Commit the work.

   ```bash
   git add .
   git commit -m "Complete <topic> exercises"
   ```

## Catalog

- [Exercise Workflow and Difficulty Ladder](docs/exercises/README.md)
- [Beginner On-Ramp](docs/exercises/00-beginner-onramp.md)
- [Java Runtime and Language Depth](docs/exercises/01-java-runtime-language.md)
- [Concurrency and Resilience](docs/exercises/02-concurrency-resilience.md)
- [Spring Core, Boot, and REST APIs](docs/exercises/03-spring-core-rest.md)
- [Persistence, Transactions, and SQL](docs/exercises/04-persistence-transactions-sql.md)
- [Testing and Code Quality](docs/exercises/05-testing-code-quality.md)
- [Messaging and Distributed Systems](docs/exercises/06-messaging-distributed-systems.md)
- [Observability and Production Debugging](docs/exercises/07-observability-production.md)
- [System Design and Staff-Level Communication](docs/exercises/08-system-design-staff.md)
- [Production War Rooms](docs/exercises/09-production-war-rooms.md) — incident drills in "symptoms → investigate → root cause" form
- [AEM, Sling, JCR, and OSGi](docs/exercises/10-aem-sling-jcr.md) — the Adobe stack the target role requires, runnable via AEM Mocks
- [Design Patterns and OOAD](docs/exercises/11-design-patterns-ooad.md)

Guides:

- [Getting Started (zero assumptions)](docs/guides/getting-started.md)
- [Glossary](docs/guides/glossary.md)
- [Progressive Hints](docs/guides/hints.md)
- [What Interviewers Actually Listen For](docs/interview/what-interviewers-expect.md)

## Suggested First Track

For the current interview target, start with these exercises before broadening:

- `JAVA-01`, `JAVA-02`, `JAVA-05`
- `CONC-01`, `CONC-03`
- `SPR-01`, `SPR-02`, `SPR-04`
- `DATA-01`, `DATA-02`, `DATA-04`
- `TEST-01`, `TEST-03`
- `DIST-01`, `DIST-02`, `DIST-04`
- `PROD-01`, `PROD-03`
- `DESIGN-01`, `DESIGN-02`, `DESIGN-04`
- `WAR-01`, `WAR-02`, `WAR-05` (spoken drills — see [Production War Rooms](docs/exercises/09-production-war-rooms.md))
- `AEM-01..05` in order, then `AEM-06` (this req is AEM-heavy — see [the AEM track](docs/exercises/10-aem-sling-jcr.md))
- `PATT-01`, `PATT-04` (and read the *why* Javadoc of the rest — see [Design Patterns](docs/exercises/11-design-patterns-ooad.md))

That gives you a compact but serious path through the highest-probability Java/Spring staff interview material. (If any of those IDs assume knowledge you don't have yet, drop back to the [Beginner On-Ramp](docs/exercises/00-beginner-onramp.md) first — the ladder exists so nothing on this list feels like a cliff.)
