# 2026-07-02 - Hash-based collections (JAVA-01)

> Filled-in example note matching the worked reference code in
> `src/main/java/com/nick/javafundamentals/exercises/java01/`.

## Exercises

- JAVA-01: Hash-based collections deep dive (idempotency cache)

## What I built

- `IdempotencyKey`, a record over `(merchantId, requestId)` for a correct,
  immutable, value-based map key.
- `IdempotencyCache<V>`, a single-threaded first-write-wins cache backed by a
  `HashMap`, with hit/miss counters.
- `BrokenKey`, a type that overrides `equals` but not `hashCode`, plus a test that
  shows a value stored under one instance cannot be found via an equal instance.

## What broke

- The broken-key test fails to retrieve the value: `map.get(lookup)` returns
  `null` even though `stored.equals(lookup)` is `true`. Root cause: identity
  `hashCode` sends equal keys to different buckets, so `get` never reaches the
  `equals` comparison.

## Key trade-offs

- Records are ideal for composite keys: immutability means a key cannot mutate
  after it is bucketed, and the generated `equals`/`hashCode` cover all components.
- `HashMap` locates a value by `hashCode` first (bucket), then `equals` (within the
  bucket). Break either half of the contract and lookups silently miss.
- Java 8+ converts a bucket to a red-black tree once it exceeds ~8 colliding
  entries (and the table is large enough), turning worst-case O(n) probing into
  O(log n) — this only helps if `hashCode` at least distributes; it does not rescue
  a missing `hashCode`.

## How I would explain this in an interview

"A HashMap finds a value in two steps: hash to a bucket, then walk the bucket
comparing with `equals`. A good key is immutable and implements both `equals` and
`hashCode` consistently over the same fields. The classic bug is overriding one but
not the other — equal objects then hash to different buckets and lookups miss. For
an idempotency key I'd use a record so both are generated correctly and the key
can't mutate underneath the map."

## Follow-up gaps

- CONC-01: make this thread-safe with `ConcurrentHashMap#computeIfAbsent` so 50
  racing callers compute the result exactly once.
- JAVA-05: add bounded eviction so the cache can't grow without limit.
