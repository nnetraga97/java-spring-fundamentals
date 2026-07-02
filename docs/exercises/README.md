# Exercise Workflow

This catalog is meant to be used as a coding workout board, not as a passive study plan.

On any given day:

1. Create a new branch.
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

## Recommended Repository Layout for Your Work

As you complete exercises, create code under:

```text
src/main/java/com/nick/javafundamentals/
src/test/java/com/nick/javafundamentals/
notes/
```

For Spring Boot exercises, you can either create a single Spring Boot app and evolve it, or create focused modules later if the repo grows.

Recommended initial package areas:

```text
domain/
collections/
concurrency/
api/
persistence/
messaging/
observability/
design/
```

## Definition of Done

An exercise is done when:

- The main behavior is implemented.
- Important edge cases are covered by tests or notes.
- You can explain the trade-offs out loud in 2 to 3 minutes.
- You commit the result with a clear message.

## Interview Note Template

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

