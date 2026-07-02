# Exercise Workflow

This catalog is meant to be used as a coding workout board, not as a passive study plan.

On any given day:

1. Create a new branch (or run `./scripts/start-exercise.sh <ID> <slug>` to branch and seed a note in one step).
2. Pick 3 to 4 exercises from one topic or a mixed set.
3. Implement code and tests.
4. Add a short learning note.
5. Commit the branch.

## Recommended Branch Names

Use branch names that make the topic obvious:

```bash
git checkout -b exercise/java-hashmap-idempotency
git checkout -b exercise/spring-rest-validation
git checkout -b exercise/jpa-transactions-locking
git checkout -b exercise/kafka-outbox-idempotent-consumer
```

## Project Layout

The project already exists as a runnable Maven + Spring Boot app (see the top-level README for how to run it). Add exercise code under the matching topic package:

```text
src/main/java/com/nick/javafundamentals/
  collections/    # JAVA-01 (worked example lives here), JAVA-05, JAVA-06
  domain/         # JAVA-02, JAVA-03, JAVA-04
  concurrency/    # CONC-01..CONC-06
  api/            # SPR-01, SPR-05, SPR-06, SPR-08, TEST-02, TEST-04, PROD-02
  persistence/    # DATA-01..DATA-07, SPR-04
  messaging/      # DIST-01..DIST-06
  observability/  # PROD-01, PROD-03, PROD-04, PROD-06
  design/         # DESIGN-* proof-of-concept code (most DESIGN work is written notes)
src/test/java/com/nick/javafundamentals/
notes/            # one learning note per session (_template.md, plus a worked example)
```

Each package has a `package-info.java` that lists the exercises it owns. `JAVA-01` is fully implemented as the reference for the expected shape (code + tests + note).

The Spring Boot app is a single evolving application; split into modules later only if the repo grows enough to need it.

## Definition of Done

An exercise is done when:

- The main behavior is implemented.
- Important edge cases are covered by tests or notes.
- You can explain the trade-offs out loud in 2 to 3 minutes.
- You commit the result with a clear message.

## Optional Interview Note Template

Create a note under `notes/` after each practice session:

```markdown
# YYYY-MM-DD - Topic

## Exercises

- EXERCISE-ID: short title

## What I built

## What broke

## Key trade-offs

## How I would explain this in an interview

## Follow-up gaps
```

## Priority Levels

- `P0`: Do these first for the current backend staff interview target.
- `P1`: Strong follow-up exercises after P0.
- `P2`: Useful broadening for future senior/staff roles.

