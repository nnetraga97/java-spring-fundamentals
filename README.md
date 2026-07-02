# Staff Java and Spring Exercises

This repository is a hands-on exercise workbook for senior and staff-level Java backend interviews.

The first pass is intentionally focused on high-signal backend interview topics: Java runtime depth, concurrency, Spring Boot internals, REST APIs, persistence, transactions, messaging, observability, production debugging, and system design trade-offs.

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
