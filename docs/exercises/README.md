# Exercise Workflow

This catalog is meant to be used as a coding workout board, not as a passive study plan.

## Difficulty Ladder

Every exercise, ordered by the background it assumes. Climb in order if you're building from scratch; jump in wherever you're comfortable. Levels don't equal *worth* — `ONB-01` and `WAR-05` teach the same idea (idempotent, leak-proof design) at opposite ends of the ladder.

**Level 1 — Beginner** (new to Java or returning after years away):

- `ONB-01..04` — the [on-ramp](00-beginner-onramp.md): types that protect you, money math, stack traces, running the app
- `JAVA-01` (read the worked example), `JAVA-02`, `JAVA-06`, `TEST-01`

**Level 2 — Core backend** (comfortable in Java, learning to build services well):

- `JAVA-03`, `JAVA-04`, `JAVA-05`
- `CONC-01`, `CONC-03`, `CONC-05`
- `SPR-01`, `SPR-02`, `SPR-03`, `SPR-05`
- `DATA-01`, `DATA-02`, `DATA-03`, `DATA-06`
- `TEST-02`, `TEST-03`, `TEST-04`
- `PROD-01`, `PROD-02`

**Level 3 — Senior/staff** (production judgment, distributed correctness, trade-off fluency):

- `CONC-02`, `CONC-04`, `CONC-06`
- `SPR-04`, `SPR-06`, `SPR-07`, `SPR-08`
- `DATA-04`, `DATA-05`, `DATA-07`
- `TEST-05`
- `DIST-01..06`
- `PROD-03..06`
- `DESIGN-01..06`
- `WAR-01..06` — the [war-room drills](09-production-war-rooms.md), best done after their hands-on siblings

Prerequisite rule of thumb: within a topic, do the lower-numbered levels first; across topics, `CONC-01` before any `DIST-*`, `DATA-01/02` before `DATA-04+`, and `SPR-01` before other `SPR-*`.

On any given day:

1. Create a new branch (or run `./scripts/start-exercise.sh <ID> <slug>` to branch and seed a note in one step).
2. Pick 3 to 4 exercises from one topic or a mixed set.
3. Implement code and tests.
4. Add a short learning note.
5. Commit the branch.

Stuck mid-exercise? Use the [progressive hints](../guides/hints.md) (one at a time) and the [glossary](../guides/glossary.md) for unfamiliar terms — that beats abandoning the session.

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
  onb01/ onb02/  # beginner on-ramp (ONB-01, ONB-02)
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

