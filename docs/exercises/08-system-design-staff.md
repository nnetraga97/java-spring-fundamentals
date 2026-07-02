# System Design and Staff-Level Communication

These exercises help you practice design clarity, trade-off communication, non-functional requirements, and technical leadership.

## DESIGN-01: Critical Request API Design

Priority: `P0`

Scenario: Design a low-latency API that validates a request, applies rules, persists state, and returns a decision.

Build:

- Write a design doc under `notes/design-critical-request-api.md`.
- Include requirements, non-functional requirements, APIs, data model, component diagram in text, failure modes, security, observability, and trade-offs.
- Add a 5-minute spoken walkthrough outline.

Acceptance criteria:

- The design names latency, availability, consistency, security, and auditability requirements.
- Trade-offs are explicit.

Interview prompts:

- What requirements would you clarify first?
- Which NFRs matter most?
- What would you defer and why?

## DESIGN-02: Idempotency Design

Priority: `P0`

Scenario: Clients may retry requests due to network failures.

Build:

- Design an idempotency strategy.
- Include key format, storage, TTL, response replay, conflict handling, and cleanup.
- Implement a small proof of concept if time allows.

Acceptance criteria:

- Duplicate requests return stable results.
- Conflicting request bodies are detected.
- Notes discuss in-memory, Redis, and database-backed approaches.

Interview prompts:

- How do you make POST safe to retry?
- What should happen if the same key has a different payload?
- Where should idempotency state live?

## DESIGN-03: High-Volume Search

Priority: `P1`

Scenario: Users need to search millions of records by customer, status, date range, and amount.

Build:

- Define query patterns.
- Choose indexes and pagination style.
- Discuss archival and retention.
- Include failure and scaling considerations.

Acceptance criteria:

- Indexes map to query patterns.
- Pagination choice is justified.

Interview prompts:

- How do you design for high-cardinality filters?
- When would you introduce a search store?
- How do retention requirements affect design?

## DESIGN-04: Event-Driven Workflow

Priority: `P0`

Scenario: A synchronous service needs to notify several downstream systems reliably.

Build:

- Design an event-driven workflow.
- Include event contracts, outbox, consumer idempotency, retries, dead-letter handling, and observability.
- Include a section on what can go wrong.

Acceptance criteria:

- The design does not lose events after database commit.
- Consumers can safely handle duplicates.
- Operational failure modes are visible.

Interview prompts:

- Why choose async communication?
- What consistency trade-offs are introduced?
- How do you operate a message-driven system?

## DESIGN-05: Staff-Level Design Review

Priority: `P1`

Scenario: A teammate proposes a service design with unclear boundaries and missing NFRs.

Build:

- Write a review note that identifies risks.
- Suggest improvements without rewriting the whole design.
- Separate blocking concerns from nice-to-have improvements.

Acceptance criteria:

- Feedback is specific and actionable.
- The review balances delivery, reliability, and maintainability.

Interview prompts:

- How do you lead without authority?
- How do you handle disagreement?
- What makes feedback useful?

## DESIGN-06: Behavioral Story Bank

Priority: `P2`

Scenario: Staff-level interviews probe ownership, ambiguity, mentorship, and judgment.

Build:

- Create `notes/behavioral-story-bank.md`.
- Add stories for production incident, ambiguous project, technical disagreement, mentorship, quality improvement, and trade-off.
- Use situation, task, action, result, reflection.

Acceptance criteria:

- Each story has concrete actions and measurable outcomes.
- Each story includes a reflection.

Interview prompts:

- Tell me about a time you improved reliability.
- Tell me about a time you disagreed on technical direction.
- Tell me about a time you mentored another engineer.

