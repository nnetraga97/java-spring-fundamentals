# Production War Rooms

Incident drills. Each one starts the way real incidents start — a page and some confusing symptoms — and your job is to *talk through* the investigation the way you would in an interview ("we're seeing X in production, walk me through it"). Interviewers at payments companies love these because they separate people who have operated services from people who have only built them.

How to run a drill:

1. Read only the **Situation** and **Symptoms**. Do not open the spoiler.
2. Out loud (really — out loud), work the investigation: what you'd check first, what each finding would rule in or out, what you'd do to stop the bleeding vs fix the cause.
3. Write your answer as a note under `notes/` (mitigation, root cause hypothesis, fix, prevention).
4. Open the spoiler and compare. Score yourself against "what the interviewer is listening for."

The deliverable is always a note — there is no code to make green. Several drills have a hands-on sibling exercise if you want to build the fix.

## WAR-01: The Pool Is Gone

Priority: `P0`

Situation: 9:20 AM Monday, traffic is ramping. Pages fire: p99 latency went from 120ms to 30s, then requests started failing outright.

Symptoms:

- Errors are `SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after 30000ms`.
- CPU on the service is *low*. Database CPU is also unremarkable.
- One dashboard line stands out: active DB connections flat at exactly 10.
- A new "export merchant statements" feature shipped Friday.

Work the investigation before opening the spoiler.

<details><summary>Root cause, fix, and what the interviewer is listening for</summary>

**Root cause pattern:** connection pool exhaustion — all 10 connections are held, everyone else queues for 30s and times out. Flat-at-max active connections with low CPU everywhere is the signature. The Friday export feature holds a connection for the entire slow export (or opens a transaction and then calls a slow external service inside it), so a handful of export requests starve the whole service.

**Mitigation vs fix:** mitigate first — disable/rate-limit the export feature, or restart to release connections (buys minutes, not a fix). Fix: don't hold a connection across slow work (stream the export, or fetch data then release before formatting), keep transactions short, set `connectionTimeout` lower so failures are fast and visible, and alert on pool utilization *before* saturation.

**Listening for:** distinguishing mitigation from root-cause fix; knowing that "make the pool bigger" mostly moves the cliff (and can hurt the database); asking "what holds a connection and for how long"; mentioning pool utilization metrics and leak detection (`leakDetectionThreshold`). Hands-on sibling: `DATA-07`.
</details>

## WAR-02: The Retry Storm

Priority: `P0`

Situation: the fraud-scoring dependency had a 30-second blip. It recovered. Your service did not — and now fraud-scoring is down *again*, harder.

Symptoms:

- Downstream team reports 8× their normal traffic, all from your service.
- Your latency and thread usage climbed during the blip and never came back down.
- Your service retries failed scoring calls 5 times, no delay between attempts.
- Timeouts on the scoring call are set to 30s; your callers time out at 10s.

<details><summary>Root cause, fix, and what the interviewer is listening for</summary>

**Root cause pattern:** a retry storm / metastable failure. Every failure multiplies traffic ×5 with no backoff, so a brief blip becomes a self-sustaining overload — the downstream can't recover because you keep hammering it. The timeout inversion makes it worse: your caller gives up at 10s, but you keep waiting 30s and *still retry* work nobody is waiting for.

**Fix:** bounded retries with exponential backoff **and jitter**; a circuit breaker so persistent failure turns into fast failure; timeout budgets that shrink down the call chain (caller 10s → your call ~2-3s per attempt); consider retry budgets (e.g. retries may add at most 10% extra traffic). Idempotency on the scored operation so retries are safe at all.

**Listening for:** "retries are a multiplier on load, applied at the worst possible time"; jitter (synchronized retries create waves); the timeout-budget inversion; circuit breaker as the escape valve; the phrase "is this request even still wanted?". Hands-on siblings: `CONC-03`, `CONC-04`.
</details>

## WAR-03: The Slow Leak

Priority: `P0`

Situation: since Tuesday's deploy, the service gets slow after ~6 hours of uptime, then OOMs overnight. A restart "fixes" it. It's Thursday and ops is restarting it on a cron.

Symptoms:

- Heap usage climbs steadily all day and GC time climbs with it; full GCs get frequent, each reclaiming less.
- Latency degrades in step with GC time.
- Tuesday's change: "added an in-memory cache of merchant risk profiles to cut database load."
- Load is normal.

<details><summary>Root cause, fix, and what the interviewer is listening for</summary>

**Root cause pattern:** an unbounded cache — a `HashMap` keyed by something with high cardinality (merchant × day, or request-scoped keys that never repeat) with no size bound and no TTL. Retained objects grow until the heap is mostly live data; GC runs constantly and reclaims little (that "reclaiming less each time" detail is the tell).

**Investigation:** confirm with a heap dump (`jmap`/`jcmd GC.heap_dump`, or `-XX:+HeapDumpOnOutOfMemoryError`) and look at the dominator tree — the cache will own most of the heap. GC logs show old-gen occupancy never dropping after full GC.

**Fix:** bounded cache with eviction (Caffeine: `maximumSize` + TTL), plus hit/miss/eviction metrics so sizing is data-driven. The cron-restart is the canonical anti-pattern to name and retire.

**Listening for:** "heap climbing + full GCs reclaiming less = retention, not allocation rate"; heap dump + dominator tree as the tool; why the fix is *bounding*, not a bigger heap (a bigger heap just delays the OOM and lengthens GC pauses). Hands-on siblings: `JAVA-05`, `SPR-07`.
</details>

## WAR-04: The Stampede at Midnight

Priority: `P1`

Situation: every night at 00:00 UTC there is a 90-second latency spike and a burst of database load, then everything settles. It's getting worse each week as traffic grows.

Symptoms:

- The spike is periodic and aligned to the clock, not to traffic.
- Database QPS bursts ×40 at the spike; the queries are all the same query for the same handful of hot rows.
- The service caches exchange-rate data with a TTL of 24 hours, loaded... at deploy time, which was midnight UTC.

<details><summary>Root cause, fix, and what the interviewer is listening for</summary>

**Root cause pattern:** cache stampede (thundering herd). All entries share one expiry moment; when they lapse, every in-flight request misses simultaneously and each one independently recomputes the same expensive value — ×40 duplicate work against the same hot keys.

**Fix (layered):** per-key request coalescing so only one loader runs and others wait (Caffeine's loading cache does this per key; `computeIfAbsent` is the same idea — `CONC-01`!); TTL jitter so entries don't expire together; refresh-ahead (`refreshAfterWrite`) so hot keys are renewed in the background and users never see the miss; serve-stale-while-revalidating as a policy decision for data like FX rates.

**Listening for:** clock-aligned periodicity → expiry/cron, not traffic; the connection between stampede protection and idempotent single-flight computation; TTL jitter as the cheap 80% fix; the correctness question ("how stale is acceptable?") asked explicitly. Hands-on siblings: `SPR-07`, `CONC-01`.
</details>

## WAR-05: The Double Charge

Priority: `P0`

Situation: support escalates: several customers were charged twice for the same order during last night's brief network incident. Finance wants to know how many, and product wants it to never happen again.

Symptoms:

- Duplicated charges cluster exactly in the 4-minute window of packet loss between the API gateway and your service.
- Client apps are configured to retry POSTs on timeout.
- Your `POST /charge` endpoint creates a new charge row per request. There is a `charge_id` — generated server-side, *after* the insert.
- Some duplicates came milliseconds apart; others, seconds.

<details><summary>Root cause, fix, and what the interviewer is listening for</summary>

**Root cause pattern:** non-idempotent POST + client retries. The client's request succeeded server-side, the *response* was lost, the client retried, and the server treated the retry as a brand-new charge. This is the default behavior of every system that has not explicitly designed for it.

**Fix:** client sends an idempotency key (per logical operation, e.g. per checkout attempt); the server stores key → outcome with a uniqueness guarantee (unique constraint or atomic insert — *this* is where the race between two in-flight duplicates is decided, milliseconds apart in the symptoms) and replays the stored response for duplicates. Same key with a *different* payload → 422, not a replay. TTL and cleanup policy for the key store. Immediate remediation: reconcile the window, refund duplicates proactively.

**Listening for:** "the response was lost, not the request — the server can't tell a retry from a new order without help"; where the idempotency state lives (DB unique constraint vs Redis, durability trade-offs); the conflicting-payload case; the concurrent-duplicate race; and mentioning reconciliation as the finance-facing answer. Hands-on siblings: `JAVA-01`, `CONC-01`, `DIST-02`, `DESIGN-02`.
</details>

## WAR-06: The Invisible p99

Priority: `P1`

Situation: a big merchant complains that "about 1% of API calls take over 3 seconds." Your average latency dashboard shows a healthy 45ms and nobody was paged.

Symptoms:

- Average: 45ms. p50: 30ms. p99: 3.4s. (The average was the only panel anyone watched.)
- The slow 1% is spread across endpoints, not concentrated in one.
- Slow requests correlate loosely with time: small clusters every few minutes.
- Infrastructure metrics look fine at the 1-minute average granularity.

<details><summary>Root cause, fix, and what the interviewer is listening for</summary>

**This one is deliberately open-ended** — cross-endpoint, periodic-ish tail latency has a shortlist of usual suspects, and the interviewer wants to watch you enumerate and *discriminate*, not guess one answer:

- **GC pauses** — check GC logs / pause-time metrics; pauses freeze every endpoint at once. Clusters every few minutes fits.
- **Thread-pool or connection-pool queueing** — brief saturation adds queue time to whoever arrives during the burst; check pool queue depth at fine granularity (1-minute averages hide 3-second bursts — that symptom line is bait).
- **A periodic background job** (cache refresh, scheduled report) stealing CPU/connections on a timer.

The method is the answer: get *per-request* visibility (traces — `PROD-06`) on the slow 1%, see where the 3s actually goes (GC-safepoint gap vs waiting-for-connection vs downstream), then fix that. Fixes range from tuning GC/heap, to right-sizing pools, to moving the batch job.

**Listening for:** refusing to trust averages (and knowing *why* percentiles can't be averaged); asking for finer-grained data instead of theorizing over 1-minute rollups; the "all endpoints at once = shared resource" deduction; tracing as the tool that ends the guessing. Hands-on siblings: `PROD-03`, `PROD-06`.
</details>

## Writing Your Own Drills

After a few of these you'll recognize the template: a shared resource (pool, heap, downstream, lock), a multiplier (retries, fan-out, herd), and a signal that was there all along. Turn incidents from your own career into drills in the same format — those are the stories behavioral interviews want anyway (`DESIGN-06`).
