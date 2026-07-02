# Persistence, Transactions, and SQL

These exercises target the questions senior backend engineers regularly face around JPA, Hibernate, transactions, data modeling, and query performance.

## DATA-01: JPA Entity Lifecycle and Dirty Checking

Priority: `P0`

Scenario: A service updates transaction status without explicitly calling save.

Build:

- Create an entity and repository.
- Load an entity in a transaction.
- Modify a managed field.
- Prove the change is flushed.
- Add a detached entity case that does not behave the same way.

Acceptance criteria:

- Tests demonstrate managed vs detached behavior.
- Notes explain persistence context and dirty checking.

Interview prompts:

- What is the JPA persistence context?
- When does Hibernate flush?
- What is dirty checking?

## DATA-02: Transaction Boundaries and Rollback Rules

Priority: `P0`

Scenario: A write succeeds, then a later operation fails.

Build:

- Create a service method with multiple database writes.
- Throw a runtime exception and prove rollback.
- Throw a checked exception and observe behavior.
- Configure rollback for the checked exception.

Acceptance criteria:

- Tests prove rollback behavior.
- Notes explain transaction rollback defaults.

Interview prompts:

- What causes a Spring transaction to roll back?
- How does propagation affect nested service calls?
- Where should transaction boundaries be placed?

## DATA-03: N+1 Query Detection and Fix

Priority: `P1`

Scenario: Fetching transactions also loads merchant details one row at a time.

Build:

- Model a relationship that can produce N+1 queries.
- Write a test or log inspection that exposes extra SQL.
- Fix with a fetch join, entity graph, or projection.
- Compare the trade-offs.

Acceptance criteria:

- The original issue is observable.
- The fix reduces query count.
- Notes explain when not to use eager loading globally.

Interview prompts:

- What causes N+1?
- How do fetch joins differ from entity graphs?
- Why can eager loading be dangerous?

## DATA-04: Optimistic Locking

Priority: `P0`

Scenario: Two concurrent requests attempt to update the same transaction.

Build:

- Add a version field.
- Simulate two concurrent updates.
- Prove one update fails with an optimistic locking error.
- Translate the error into a meaningful service response.

Acceptance criteria:

- Lost updates are prevented.
- Tests cover concurrent modification.
- Notes compare optimistic and pessimistic locking.

Interview prompts:

- What is a lost update?
- How does optimistic locking work?
- When would pessimistic locking be justified?

## DATA-05: Index Design for Query Patterns

Priority: `P1`

Scenario: You need fast lookup by merchant ID, status, and creation time.

Build:

- Create a table with realistic columns.
- Add queries for common access patterns.
- Propose indexes and explain column order.
- Use `EXPLAIN` if a real database is available.

Acceptance criteria:

- Notes tie each index to a query.
- You can explain why unused indexes are harmful.

Interview prompts:

- How do composite indexes work?
- What is cardinality?
- How do you read an execution plan?

## DATA-06: Cursor Pagination

Priority: `P2`

Scenario: Offset pagination becomes slow for deep pages.

Build:

- Implement offset pagination.
- Implement cursor pagination using created time plus ID.
- Add tests for stable ordering.
- Document edge cases.

Acceptance criteria:

- Cursor pagination handles new records predictably.
- Notes compare offset and cursor trade-offs.

Interview prompts:

- Why does deep offset pagination get slow?
- What makes a cursor stable?
- How do you paginate mutable result sets?

