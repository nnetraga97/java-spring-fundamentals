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

The project already exists as a runnable Maven + Spring Boot app (see the top-level README for how to run it). Everything for one exercise lives in a folder named for its id, on both the code and test side:

```text
src/main/java/com/nick/javafundamentals/exercises/
  java01/  # JAVA-01 (worked reference example)
  java02/ java03/ java04/ java05/ java06/
  conc01/ conc03/ conc04/ conc06/
  dist02/ dist04/ dist06/
  spr01/   # SPR-01
  data04/  # DATA-04
  common/  # types shared by >1 exercise (e.g. Message for DIST-02 & DIST-04)
src/test/java/com/nick/javafundamentals/exercises/
  <same folders>/  # each exercise's test, plus test01..test04 placeholders
notes/             # one learning note per session (_template.md, plus a worked example)
```

Each exercise folder has a short `package-info.java` describing it. Code and test share the same package, so a test needs no import to reach the code it drives. `JAVA-01` is fully implemented as the reference for the expected shape (code + tests + note).

New exercises get a new `exercises/<id>/` folder. The Spring Boot app is a single evolving application; split into modules later only if the repo grows enough to need it.

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

