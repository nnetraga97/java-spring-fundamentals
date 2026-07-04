# Beginner On-Ramp

Start here if you are new to Java, new to Spring, or coming back after time away. These exercises assume nothing beyond "I can edit a file and run a command." Each one still teaches something a payments interviewer genuinely cares about — there is no throwaway FizzBuzz here.

If any word in an exercise is unfamiliar, look it up in the [Glossary](../guides/glossary.md). If you get stuck, use the [progressive hints](../guides/hints.md) — read one hint at a time, not all of them.

Before your first exercise, read [Getting Started](../guides/getting-started.md) — it walks through installing a JDK, running the tests, and reading a failing test without panic.

## ONB-01: A Card Number That Cannot Leak Into Logs

Priority: `P0` (if you are new — otherwise skip ahead)

Scenario: A teammate wrote `log.info("card: " + cardNumber)` and it shipped. In a payments company, that is a compliance incident (PCI DSS forbids storing or logging full card numbers), not a code-style nit. The durable fix is a *type* whose `toString()` is already masked, so careless logging is harmless.

Build:

- Implement `MaskedPan.of(rawPan)` in `exercises/onb01/`: strip spaces and dashes, validate 12–19 digits, reject bad input.
- Implement `masked()` (all digits become `*` except the last four) and `lastFour()`.
- Make `toString()` return the masked form.

How: open `MaskedPanTest`, delete the `@Disabled` line, run
`./mvnw test -Dtest=MaskedPanTest`, and make it green.

Acceptance criteria:

- All tests in `MaskedPanTest` pass.
- You can explain why fixing this with a type beats fixing it with a code-review rule.

Interview prompts:

- Why is "make invalid states unrepresentable" better than "be careful"?
- Where else would a masking type help (exception messages, debug endpoints, crash dumps)?
- What is the difference between validation and sanitization?

What this trains for later: `JAVA-01` and `JAVA-02` use the same idea — small immutable types that enforce their own rules.

## ONB-02: Money Without Floating-Point Bugs

Priority: `P0` (if you are new — otherwise skip ahead)

Scenario: In floating point, `0.1 + 0.2` is `0.30000000000000004`. Across millions of transactions those errors compound, and reconciliation (checking that money in equals money out) fails by real cents. Payment systems therefore use `BigDecimal` or integer cents — never `double`.

Build:

- Implement `Money.of(amount, currency)` in `exercises/onb02/`: parse with `BigDecimal`, reject nulls and more-than-two decimal places, normalize scale so `"10"` equals `"10.00"`.
- Implement `add` (same currency only — mismatches throw `CurrencyMismatchException`) and `times`.

How: open `MoneyTest`, delete the `@Disabled` line, run
`./mvnw test -Dtest=MoneyTest`, and make it green.

Acceptance criteria:

- All tests in `MoneyTest` pass.
- You can say in one sentence why `double` is wrong for money.

Interview prompts:

- Why does `0.1 + 0.2 != 0.3` with doubles?
- Why reject sub-cent input instead of rounding it?
- What are the trade-offs of `BigDecimal` vs storing integer cents?

What this trains for later: `JAVA-06` processes transactions built from values like this; `DATA-*` persists them.

## ONB-03: Read a Stack Trace Like a Map

Priority: `P0` (if you are new — otherwise skip ahead)

Scenario: Half of debugging is reading the error you already have. Beginners scroll past stack traces; experienced engineers read them top-down and know where to stop.

Build (no new code — you break existing code on purpose):

1. Open the worked example `exercises/java01/IdempotencyCache.java` and its test `IdempotencyCacheTest` (this one is already implemented and passing).
2. Break it: change `putIfAbsent` to always overwrite (`store.put(key, result); return result;`).
3. Run `./mvnw test -Dtest=IdempotencyCacheTest` and read the failure **from the top**: which test failed, what was expected, what was actual, which line of *your* code appears first in the trace.
4. Now make it throw instead: have `find` call `store.get(null)`... or simpler, throw `new IllegalStateException("boom")` at the top of `find`. Run again and trace the exception from the throw site up to the test.
5. Revert your changes (`git checkout -- src/main/java/com/nick/javafundamentals/exercises/java01/`).

Acceptance criteria:

- A note under `notes/` describing: how you find *your* frame in a trace full of framework frames, and the difference between an assertion failure and an exception.

Interview prompts:

- A test fails in CI with a 200-line stack trace. What do you look at first?
- What is the difference between the exception message, the type, and the trace?
- What does "Caused by:" mean?

## ONB-04: Start the App and Watch It Work

Priority: `P0` (if you are new — otherwise skip ahead)

Scenario: You will spend your career reading logs of running services. Start with this one.

Build (terminal only):

1. Run `./mvnw spring-boot:run` and read the startup log: which port it binds, which profile is active, how long startup took.
2. In a second terminal, confirm the service is healthy without reading any code:

   ```bash
   curl -s localhost:8080/actuator/health
   ```

3. Now POST to the `SPR-01` endpoint (which is still an unimplemented stub — that is on purpose):

   ```bash
   curl -s -X POST localhost:8080/authorizations \
     -H 'Content-Type: application/json' \
     -d '{"merchantId":"m-1","amountCents":2500,"currency":"USD"}'
   ```

   You get a 500, and the application log shows an `UnsupportedOperationException`
   with a `TODO SPR-01` message. Find that stack trace in the log and identify
   which class and line threw it — this is ONB-03 applied to a running service.
4. Send *invalid* input (delete `merchantId` from the body) and notice you now get
   a **400** instead. Validation runs before the controller method, so it works
   even though the controller body is a stub. That ordering is a real interview
   topic.
5. Stop the app with Ctrl-C and notice the shutdown log lines.

Acceptance criteria:

- You can explain what happened between "curl" and "response" in 4–5 steps (HTTP request → validation → controller → exception handler → response serialization).
- You can explain why the invalid request returned 400 while the valid one returned 500.
- A short note listing one thing in the startup log you did not expect.

Interview prompts:

- What happens when a Spring Boot app starts?
- How would you check whether a service is up without reading code?
- What is in an HTTP request besides the body?

## Where to Go Next

When these feel comfortable, move to the main track — the [difficulty ladder](README.md#difficulty-ladder) tells you the order. Your next three: `JAVA-01` (read the worked example properly), `JAVA-02` (first real stub exercise), `TEST-01` (write your own assertions).
