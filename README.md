# Staff Java and Spring Exercises

This repository is a hands-on exercise workbook for senior and staff-level Java backend interviews.

The first pass is intentionally focused on high-signal backend interview topics: Java runtime depth, concurrency, Spring Boot internals, REST APIs, persistence, transactions, messaging, observability, production debugging, and system design trade-offs.

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
src/main/java/com/nick/javafundamentals/            # production code, one package per topic
src/test/java/com/nick/javafundamentals/exercises/  # tests, one folder per exercise id
notes/                                               # one learning note per session
docs/exercises/                                      # the exercise catalog
```

Tests are grouped by exercise, not by topic: each exercise's test lives in
`src/test/java/com/nick/javafundamentals/exercises/<id>/` (e.g. `exercises/java02/`,
`exercises/spr01/`). The production stubs it drives stay in their topic package
(`domain/`, `concurrency/`, …). So `exercises/java02/` tells you at a glance that
folder is `JAVA-02`.

Each topic package has a `package-info.java` listing the exercise IDs that live there. The **worked reference example** is `JAVA-01` — see `collections/IdempotencyCache.java`, its test `IdempotencyCacheTest`, and the matching note in `notes/`. Use it as the template for the shape of an exercise: production code, focused tests, and a short trade-off note.

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
  `JAVA-02..06`, `CONC-01/03/04/06`, `DIST-02/04/06`, `SPR-01` (`@WebMvcTest`),
  `DATA-04` (`@DataJpaTest`).
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

- [Exercise Workflow](docs/exercises/README.md)
- [Java Runtime and Language Depth](docs/exercises/01-java-runtime-language.md)
- [Concurrency and Resilience](docs/exercises/02-concurrency-resilience.md)
- [Spring Core, Boot, and REST APIs](docs/exercises/03-spring-core-rest.md)
- [Persistence, Transactions, and SQL](docs/exercises/04-persistence-transactions-sql.md)
- [Testing and Code Quality](docs/exercises/05-testing-code-quality.md)
- [Messaging and Distributed Systems](docs/exercises/06-messaging-distributed-systems.md)
- [Observability and Production Debugging](docs/exercises/07-observability-production.md)
- [System Design and Staff-Level Communication](docs/exercises/08-system-design-staff.md)

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

That gives you a compact but serious path through the highest-probability Java/Spring staff interview material.
