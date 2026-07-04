# Progressive Hints

Stuck on a wired exercise? Open **one hint at a time** (click to expand). Hint 1 is a nudge, hint 2 is the approach, hint 3 is nearly the answer. The learning happens between hints — give yourself ten minutes before opening the next one.

If a *term* is the blocker rather than the code, check the [glossary](glossary.md) first.

---

## ONB-01: MaskedPan

<details><summary>Hint 1 — nudge</summary>

Split the problem: first normalize the input (remove separators), then validate, then store only the clean digits. `masked()` and `lastFour()` become one-liners once the stored value is clean.
</details>

<details><summary>Hint 2 — approach</summary>

`rawPan.replace(" ", "").replace("-", "")` normalizes. Validate with `matches("\\d{12,19}")` — it handles "digits only" and "length 12–19" in one check (but check for `null` before calling anything on it). `"*".repeat(n)` builds the mask.
</details>

<details><summary>Hint 3 — near-solution</summary>

In `of`: null check → normalize → `matches("\\d{12,19}")` or throw `IllegalArgumentException` → `new MaskedPan(cleaned)`. `lastFour()` is `digits.substring(digits.length() - 4)`. `masked()` is `"*".repeat(digits.length() - 4) + lastFour()`. `toString()` returns `masked()`.
</details>

## ONB-02: Money

<details><summary>Hint 1 — nudge</summary>

`new BigDecimal("10.999").scale()` is 3. That is your "too many decimal places" check. `setScale(2)` normalizes `"10"` to `10.00`.
</details>

<details><summary>Hint 2 — approach</summary>

In `of`: reject nulls, parse with `new BigDecimal(amount)` (catch `NumberFormatException` and rethrow as `IllegalArgumentException` if you want friendlier errors), reject `scale() > 2`, then `setScale(2)` before constructing. `add`/`times` use `amount.add(...)` / `amount.multiply(BigDecimal.valueOf(quantity))` — BigDecimals are immutable, so build a new `Money`.
</details>

<details><summary>Hint 3 — near-solution</summary>

`add`: `if (!currency.equals(other.currency)) throw new CurrencyMismatchException(...)`, then `new Money(amount.add(other.amount), currency)`. `times`: multiplying two-decimal values by an int keeps scale 2, so `new Money(amount.multiply(BigDecimal.valueOf(quantity)), currency)` passes as-is.
</details>

## JAVA-02: AuthorizationStateMachine

<details><summary>Hint 1 — nudge</summary>

Don't write a chain of `if` statements per state. Ask each state what its legal next states are — the *data* should encode the rules, then `transitionTo` is three lines.
</details>

<details><summary>Hint 2 — approach</summary>

Give the enum the knowledge: a `Set<AuthorizationState>` of allowed targets per constant (or a `canTransitionTo(target)` method on `AuthorizationState`). Terminal states have an empty set. `transitionTo` checks membership, throws `InvalidTransitionException` otherwise, and returns a new machine (immutability keeps it thread-safe for free).
</details>

<details><summary>Hint 3 — near-solution</summary>

From the test: RECEIVED → {APPROVED, DECLINED}; APPROVED → {REVERSED}; DECLINED → {}; REVERSED → {}. In `transitionTo`: if the current state's allowed set doesn't contain `target`, `throw new InvalidTransitionException(...)`; else `return new AuthorizationStateMachine(target)`.
</details>

## JAVA-03: ValidatorPipeline

<details><summary>Hint 1 — nudge</summary>

The pipeline just holds the validators in order and concatenates whatever each one returns. Ordering and "collect *all* messages, don't stop at the first failure" are the whole exercise.
</details>

<details><summary>Hint 2 — approach</summary>

Store the validators in a `List<Validator<? super T>>`. `validate` loops in order, calls each validator, and appends its messages into one result list. `? super T` is why a `Validator<Object>` can participate in a `ValidatorPipeline<PaymentCommand>` — that's the consumer-super half of PECS.
</details>

## JAVA-04: AuthorizationService (exception translation)

<details><summary>Hint 1 — nudge</summary>

Catch the low-level exception, throw the service-level one — but keep the original as the *cause* so diagnostics survive. Losing the cause is the classic mistake the test likely checks.
</details>

<details><summary>Hint 2 — approach</summary>

`try { return repository...; } catch (RepositoryException e) { throw new AuthorizationServiceException("...stable message...", e); }` — the two-arg constructor exists precisely for the cause chain. Callers now depend on your exception type, not the repository's.
</details>

## JAVA-05: BoundedCache

<details><summary>Hint 1 — nudge</summary>

There is a JDK collection that already tracks insertion order and offers a hook that runs on every insert to decide whether to drop the oldest entry.
</details>

<details><summary>Hint 2 — approach</summary>

`LinkedHashMap` + override `removeEldestEntry(Map.Entry)` to return `size() > capacity`, bumping your eviction counter when it does. Alternatively do it manually: after `put`, if `map.size() > capacity`, remove `map.keySet().iterator().next()` (the oldest) and count it.
</details>

## JAVA-06: Loop vs Stream analyzers

<details><summary>Hint 1 — nudge</summary>

Loop version: `Map#merge(merchantId, amountCents, Long::sum)` inside a for-each. Stream version: there is a collector built for exactly "group by key, sum a long field."
</details>

<details><summary>Hint 2 — approach</summary>

`transactions.stream().collect(Collectors.groupingBy(Transaction::merchantId, Collectors.summingLong(Transaction::amountCents)))`. Both versions must produce equal maps — the test runs the same assertions against each.
</details>

## CONC-01: IdempotencyRegistry

<details><summary>Hint 1 — nudge</summary>

Any solution shaped "check if present, then compute, then put" has a gap between the check and the put where a second thread slips in. You need the check-and-insert to be one atomic operation.
</details>

<details><summary>Hint 2 — approach</summary>

`ConcurrentHashMap#computeIfAbsent(key, k -> computation.get())` — the mapping function runs at most once per key, other threads block until it finishes and then see the same value. That single line is the entire fix; the test's 50 concurrent callers verify the supplier ran exactly once.
</details>

## CONC-03: RetryExecutor

<details><summary>Hint 1 — nudge</summary>

A loop over attempts. The subtlety is the exception policy: one type means "try again (until attempts run out)", the other means "give up immediately." Get the order of catch blocks and the "last attempt" behavior right.
</details>

<details><summary>Hint 2 — approach</summary>

```java
for (int attempt = 1; attempt <= maxAttempts; attempt++) {
    try { return action.get(); }
    catch (NonRetryableException e) { throw e; }        // never retried
    catch (RetryableException e) {
        if (attempt == maxAttempts) throw e;            // budget exhausted
    }
}
```

Read the test for whether the final failure should be rethrown as-is or wrapped.
</details>

## CONC-04: CircuitBreaker

<details><summary>Hint 1 — nudge</summary>

Track three things: current state, consecutive-failure count, and *when* the circuit opened (use the injected clock, never `System.currentTimeMillis()` — the test controls time through that clock).
</details>

<details><summary>Hint 2 — approach</summary>

In `call`: if OPEN and `openMillis` hasn't elapsed → throw `CircuitOpenException` without invoking the action; if it has elapsed → move to HALF_OPEN and let one call through. On success: reset failures, close. On failure: increment; in CLOSED, trip to OPEN when the count reaches the threshold (recording the open time); in HALF_OPEN, any failure reopens.
</details>

## CONC-06: VirtualThreadFanOut

<details><summary>Hint 1 — nudge</summary>

`Executors.newVirtualThreadPerTaskExecutor()` in a try-with-resources, submit all tasks first, then collect results in submission order. Think about what should happen to task 3 if task 2 threw.
</details>

<details><summary>Hint 2 — approach</summary>

Submit all → get all: `invokeAll(tasks)` handles ordering for you, then unwrap each `Future`; rethrow the first `ExecutionException`'s cause. For the structured-concurrency variant, `StructuredTaskScope.ShutdownOnFailure` gives you sibling cancellation (`scope.fork` each task, `scope.join().throwIfFailed()`).
</details>

## SPR-01: AuthorizationController + ApiExceptionHandler

<details><summary>Hint 1 — nudge</summary>

Two separate TODOs: the controller method is a one-liner (delegate, return). The exception handler is where the real work is — turning Spring's validation exception into your stable `ApiError` shape.
</details>

<details><summary>Hint 2 — approach</summary>

In the handler: `@ExceptionHandler(MethodArgumentNotValidException.class)` on a method returning `ResponseEntity<ApiError>`. Pull field messages from `ex.getBindingResult().getFieldErrors()` (e.g. `field + ": " + defaultMessage`), and return `ResponseEntity.badRequest().body(new ApiError("VALIDATION_FAILED", messages))`.
</details>

## DATA-04: AuthorizationRecord optimistic locking

<details><summary>Hint 1 — nudge</summary>

The entity is missing one field with one annotation. Everything else (the concurrent-update simulation) is in the test.
</details>

<details><summary>Hint 2 — approach</summary>

Add `@Version private long version;` to the entity. JPA now includes `WHERE version = ?` in updates and bumps it on each write; the second concurrent writer's update matches zero rows and Hibernate raises `OptimisticLockingFailureException` — the lost update becomes a loud, handleable error.
</details>

## DIST-02: IdempotentConsumer

<details><summary>Hint 1 — nudge</summary>

The whole exercise is *when* you record the message id. Record before handling, and a crash mid-handler loses the message forever. Record after, and a crash means redelivery — a duplicate *attempt*, which is exactly what at-least-once promises.
</details>

<details><summary>Hint 2 — approach</summary>

If the id is already recorded → return `false` (skipped duplicate) without calling the handler. Otherwise run the handler; only if it completes normally, record the id and return `true`. If the handler throws, record nothing and let it propagate — the retry will run the handler again.
</details>

## DIST-04: RetryingDispatcher

<details><summary>Hint 1 — nudge</summary>

Combine CONC-03's retry loop with a terminal path: exhausted-retries and poison messages don't just throw, they land in `deadLetters()` with attempt count and reason.
</details>

<details><summary>Hint 2 — approach</summary>

Loop attempts: `PoisonMessageException` → dead-letter immediately with the attempts so far, stop. `TransientFailureException` → keep looping; when attempts hit `maxAttempts`, dead-letter with the final count and reason. Success → return with no dead letter. Whether `dispatch` also rethrows after dead-lettering: the test tells you.
</details>

## DIST-06: TokenBucketRateLimiter

<details><summary>Hint 1 — nudge</summary>

Don't refill on a timer — refill lazily. Each `tryAcquire`, compute how much time passed since that key's last refill and add the earned tokens (capped at capacity) before deciding.
</details>

<details><summary>Hint 2 — approach</summary>

Per key keep `(tokens, lastRefillMillis)`. On acquire: `elapsed = clock - lastRefill`; `earned = elapsed * refillPerSecond / 1000`; if `earned > 0`, `tokens = min(capacity, tokens + earned)` and advance `lastRefill` (by the time the earned tokens account for, or to now). Then: token available → decrement, `true`; else `false`. Use the injected clock — tests move time manually.
</details>

## AEM-01: ArticleContentRepository (JCR)

<details><summary>Hint 1 — nudge</summary>

Think of the path as segments to walk: start at `session.getRootNode()`, and for each segment either descend (`getNode`) or create (`addNode`). Once you're standing on the final node, `setProperty` twice and `session.save()`.
</details>

<details><summary>Hint 2 — approach</summary>

```java
Node node = session.getRootNode();
for (String segment : path.substring(1).split("/")) {
    node = node.hasNode(segment) ? node.getNode(segment) : node.addNode(segment);
}
node.setProperty("title", title); // setProperty overwrites, so re-saving updates for free
```

For `findTitle`: guard with `session.nodeExists(path)`, then `node.hasProperty("title")`, then `getProperty("title").getString()`.
</details>

## AEM-02: ArticleResourceReader (Sling resources)

<details><summary>Hint 1 — nudge</summary>

Both methods are one idea each: `titleOrDefault` is a single `ValueMap` call (the two-arg `get` takes the default); `articleTitles` is "iterate children, keep the ones whose type matches, map through titleOrDefault."
</details>

<details><summary>Hint 2 — approach</summary>

`resource.getValueMap().get("title", "Untitled")`. For the children: `parent.getChildren()` is iterable; filter with `child.isResourceType(ARTICLE_RESOURCE_TYPE)` (prefer it over comparing `getResourceType()` strings — it also understands type inheritance in real AEM), collect `titleOrDefault(child)` into a list.
</details>

## AEM-03: ArticleModel (Sling Models)

<details><summary>Hint 1 — nudge</summary>

The injection already happened by the time your getters run — `title` and `body` are either populated or `null` (OPTIONAL strategy). Both methods are plain null-safe Java; no Sling API needed.
</details>

<details><summary>Hint 2 — approach</summary>

`displayTitle`: `title != null ? title : "Untitled"`. `teaser`: null body → `""`; body longer than `TEASER_MAX_LENGTH` → `body.substring(0, TEASER_MAX_LENGTH) + "..."`; otherwise the body unchanged (exactly 50 chars gets no ellipsis — check `>` not `>=`).
</details>

## AEM-04: ArticleJsonServlet (Sling servlets)

<details><summary>Hint 1 — nudge</summary>

The resource is already on the request — `request.getResource()`. Read its ValueMap; no title → `response.sendError(404)` and return. Otherwise set the content type *before* writing, and build the JSON with the provided `jsonEscape`.
</details>

<details><summary>Hint 2 — approach</summary>

```java
ValueMap properties = request.getResource().getValueMap();
String title = properties.get("title", String.class);
if (title == null) { response.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
String body = properties.get("body", "");
response.setContentType("application/json");
response.setCharacterEncoding("UTF-8");
response.getWriter().write("{\"title\":\"" + jsonEscape(title) + "\",\"body\":\"" + jsonEscape(body) + "\"}");
```
</details>

## AEM-05: MaintenanceBannerService (OSGi)

<details><summary>Hint 1 — nudge</summary>

`@Activate` hands you the typed `Config`; your only job there is copying `config.enabled()` and `config.message()` into fields. The container calls it before anyone can call your getters, so the fields are always initialized.
</details>

<details><summary>Hint 2 — approach</summary>

Two fields (`boolean enabled`, `String message`), set in `activate`. `banner()`: `enabled && message != null && !message.isBlank() ? Optional.of(message) : Optional.empty()`. The `@Reference` test needs no extra code — registering your service first is what lets the mock container inject it into `HelpCenterHeaderProvider`.
</details>

## PATT-01: FeeService (Strategy)

<details><summary>Hint 1 — nudge</summary>

Each policy is one lambda. Store them in an `EnumMap<CardProduct, FeePolicy>` (or on the enum itself) and `feeCents` becomes a lookup + delegate.
</details>

<details><summary>Hint 2 — approach</summary>

`DEBIT`: `amount -> 25`. `CREDIT`: `amount -> amount * 2 / 100` (long division rounds down for you). `PREMIUM_CREDIT`: `amount -> Math.max(50, amount * 3 / 100)`. Selection: `policies.get(product).feeCents(amountCents)`.
</details>

## PATT-02: PaymentRequest.Builder (Builder)

<details><summary>Hint 1 — nudge</summary>

Give the builder one field per request field, pre-initialized to the defaults (`currency = "USD"`, `captureImmediately = true`). Every setter is two lines: assign, `return this`. All the thinking lives in `build()`.
</details>

<details><summary>Hint 2 — approach</summary>

In `build()`: `if (merchantId == null || merchantId.isBlank()) throw new IllegalStateException("merchantId is required");` then the same shape for `amountCents <= 0` (message must contain `amountCents`), then `return new PaymentRequest(merchantId, amountCents, currency, captureImmediately);`.
</details>

## PATT-03: TransactionEventBus (Observer)

<details><summary>Hint 1 — nudge</summary>

A `List<TransactionListener>` plus three short methods. The only subtlety is `publish`: each listener call goes in its own try/catch so one failure can't stop the loop.
</details>

<details><summary>Hint 2 — approach</summary>

`CopyOnWriteArrayList` is the classic listener-list choice (safe iteration while listeners change); a plain `ArrayList` also passes these tests. `publish`: `for (var l : listeners) { try { l.onEvent(event); } catch (RuntimeException e) { /* log in production */ } }`.
</details>

## PATT-04: LoggingGateway / RetryingGateway (Decorator)

<details><summary>Hint 1 — nudge</summary>

Each decorator does its extra thing and delegates to the wrapped `PaymentGateway`. Match the log format exactly: `"charge " + merchantId + " " + amountCents + " -> OK(" + auth + ")"` and `-> FAILED(message)`.
</details>

<details><summary>Hint 2 — approach</summary>

Logging: try the delegate; on success add the OK line and return; in `catch (RuntimeException e)` add the FAILED line with `e.getMessage()` and `throw e`. Retrying: `try { return delegate.charge(...); } catch (RuntimeException first) { return delegate.charge(...); }` — letting the second call's exception fly is exactly the spec.
</details>

## PATT-05: SettlementFileProcessor (Template Method)

<details><summary>Hint 1 — nudge</summary>

The whole method is one loop over `lines` with two counters. Follow the Javadoc's numbered steps literally — blank check first (`line.isBlank()` → `continue`), then parse, then validate, then apply.
</details>

<details><summary>Hint 2 — approach</summary>

```java
int processed = 0, rejected = 0;
for (String line : lines) {
    if (line.isBlank()) continue;
    T record = parseLine(line);
    if (!isValid(record)) { rejected++; continue; }
    apply(record);
    processed++;
}
return new ProcessingSummary(processed, rejected);
```

Don't remove `final` from the signature — one test checks it reflectively.
</details>

## TEST-01 … TEST-04 (you write the assertions)

<details><summary>General guidance</summary>

These are deliberately open: the skill is choosing what to assert. A decent rubric per test: one happy path, one boundary (empty, zero, max), one failure path asserting the *type* of failure, and — for the web/error contracts — assert the fields clients depend on (status code, error code) rather than exact full JSON strings, which makes tests brittle. Look at `Test01DomainUnitTests` for the intended shape.
</details>
