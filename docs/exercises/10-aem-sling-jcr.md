# AEM, Sling, JCR, and OSGi

The target role lists 3+ years of Adobe AEM as a core qualification, so this track exists even though AEM itself is proprietary. The trick that makes it hands-on anyway: the open-source **AEM Mocks** framework (`io.wcm.testing.aem-mock`) plus Adobe's published `aem-sdk-api` give the tests a real in-memory Sling/JCR/OSGi environment. You write the same code you would write inside AEM — the container is mocked, the APIs are the real ones.

**The 60-second architecture** (memorize this — it is the first AEM interview question):

- **JCR** (Java Content Repository, implemented by Apache Jackrabbit Oak) is the storage: everything — pages, assets, config — is a *tree of nodes with properties*, not tables.
- **Apache Sling** is the web framework on top: every URL resolves to a *resource* (usually a JCR node), and the resource's `sling:resourceType` — not a route table — decides which script or servlet renders it. **Content drives routing**; this is the single biggest mental difference from Spring MVC.
- **OSGi** (Apache Felix) is the runtime underneath: code ships as bundles, services are declared components wired by `@Reference`, and configuration is injected at activation and changeable at runtime.
- **Dispatcher** sits in front: an Apache-module cache/security layer that serves most traffic without touching AEM.

Spring translation table: `@Service` → `@Component`, `@Autowired` → `@Reference`, `@ConfigurationProperties` → OSGi config + `@Activate`, `@RestController` + route → servlet registered by *resource type*, database + JPA → JCR + resources.

## AEM-01: JCR Basics — Content Is a Tree

Priority: `P0` (for this role)

Scenario: Merchant help-center articles must be stored in the content repository, at paths like `/content/help/cards/declined-payments`.

Build:

- Implement `ArticleContentRepository` in `exercises/aem01/`: `saveArticle` (create intermediate nodes, set `title`/`body` properties, save the session; re-saving updates) and `findTitle` (Optional-returning property read).

How: open `ArticleContentRepositoryTest`, delete `@Disabled`, run `./mvnw test -Dtest=ArticleContentRepositoryTest`, make it green.

Acceptance criteria:

- All tests pass.
- You can explain node vs property, and why "create the path as you go" replaces schema migration.

Interview prompts:

- How does JCR content storage differ from a relational schema?
- What is `session.save()` actually committing?
- Where would you *not* use the JCR (high-write transactional data — that stays in a database; AEM stores content, not ledgers)?

## AEM-02: Sling Resources — Everything Is a Resource

Priority: `P0` (for this role)

Scenario: A help-center overview page must list its child articles — and only the articles, skipping folders and other components.

Build:

- Implement `ArticleResourceReader` in `exercises/aem02/`: `titleOrDefault` via `ValueMap` and `articleTitles` filtering children by `sling:resourceType`.

How: `ArticleResourceReaderTest` is the spec.

Acceptance criteria:

- All tests pass.
- You can explain what `sling:resourceType` is for and how `adaptTo` fits the picture.

Interview prompts:

- What is a Sling resource, and how does a request URL become one?
- What does `resource.adaptTo(Node.class)` / `adaptTo(ValueMap.class)` do?
- Why is filtering by resource type better than filtering by path convention?

## AEM-03: Sling Models — Typed Views of Content

Priority: `P0` (for this role)

Scenario: The article component's HTL template needs a typed Java object instead of raw ValueMap lookups scattered through markup.

Build:

- Complete `ArticleModel` in `exercises/aem03/`: the `@Model`/`@ValueMapValue` wiring is provided (read it); implement `displayTitle()` (null-safe fallback) and `teaser()` (truncate body to 50 chars with ellipsis).

How: `ArticleModelTest` is the spec — note how the test registers the model class and uses `adaptTo`.

Acceptance criteria:

- All tests pass.
- You can explain `adaptables`, injection strategy, and why adaptation returns `null` (not an exception) when a model can't be created — a classic AEM debugging gotcha.

Interview prompts:

- What problem do Sling Models solve over scriptlets/ValueMap code?
- What does `adaptables = Resource.class` vs `SlingHttpServletRequest.class` change?
- Why did `adaptTo` return null, and how do you debug it?

## AEM-04: Sling Servlets — Registered by Resource Type, Not URL

Priority: `P0` (for this role)

Scenario: The single-page help center fetches articles as JSON: `GET /content/help/fees.json`.

Build:

- Implement `ArticleJsonServlet#doGet` in `exercises/aem04/`: JSON with escaped title/body, `application/json` content type, 404 for an article with no title. The OSGi registration by `sling.servlet.resourceTypes` is provided — read it closely.

How: `ArticleJsonServletTest` is the spec.

Acceptance criteria:

- All tests pass.
- You can explain why binding servlets to resource types (not paths) is the Sling default, and why path-bound servlets are discouraged in AEM (they bypass resource resolution and its access control).

Interview prompts:

- Walk through what happens between `GET /content/help/fees.json` and your `doGet`.
- Resource-type binding vs path binding — trade-offs?
- How do selectors and extensions (`.teaser.json`) change dispatch?

## AEM-05: OSGi Services — Components, Config, References

Priority: `P0` (for this role)

Scenario: Ops needs to toggle a maintenance banner on the help center at runtime — no deploy, just a config change in the Felix console.

Build:

- Implement `MaintenanceBannerService` in `exercises/aem05/`: bind typed OSGi config in `@Activate`, expose `isEnabled()`/`banner()` (enabled + non-blank message required). The consuming component `HelpCenterHeaderProvider` (`@Reference`) is provided — read it.

How: `MaintenanceBannerServiceTest` is the spec.

Acceptance criteria:

- All tests pass.
- You can map OSGi concepts onto their Spring equivalents from memory.

Interview prompts:

- What is an OSGi bundle / component / service, in one sentence each?
- How does OSGi configuration differ from a properties file baked into the jar?
- What happens to consumers when a referenced service goes away (dynamic lifecycle)?

## AEM-06: Dispatcher — The Cache in Front of Everything

Priority: `P1` (written exercise)

Scenario: Marketing publishes a pricing update; some visitors see the old page for hours. Separately, a pentest finds `/crx/de` reachable from the internet.

Build (note under `notes/`):

- Explain the publish-tier request flow: browser → CDN → Dispatcher → publish instance.
- Cover: what the Dispatcher caches and what it never caches (query strings, POSTs), cache invalidation on activation (`/invalidate` + stat files), and `/filter` rules as the security allowlist that should have blocked `/crx/de`.
- Include the classic incident: activation succeeded but stale content persists — walk the diagnosis (stat file level? invalidation reached the farm? CDN layered on top with its own TTL?).

Acceptance criteria:

- The note explains both the caching and the security role.
- You can answer "why was the old page served?" with a concrete checklist.

Interview prompts:

- Why does AEM need the Dispatcher at all?
- How does content activation invalidate the Dispatcher cache?
- What belongs in Dispatcher filters, and what happens when they are too permissive?

## AEM-07: Content Architecture — MSM and Language Copy

Priority: `P1` (written exercise)

Scenario: The help center must exist for 12 markets in 8 languages. Some content is global (card network rules), some market-specific (local regulations).

Build (note under `notes/`):

- Design the content tree: a language-master structure, live copies per market via **Multi Site Manager**, and where **Language Copy** + translation workflows fit.
- Explain inheritance: what a live copy inherits, what happens on local edit (inheritance break/cancellation), and how a rollout pushes blueprint changes.
- Name the failure modes: editors breaking inheritance everywhere, rollout conflicts, and orphaned language copies.

Acceptance criteria:

- The tree design shows blueprint → live copy relationships explicitly.
- Trade-offs of MSM vs simple copy-paste are stated.

Interview prompts:

- Blueprint vs live copy vs language copy — what does each solve?
- What happens when an editor edits a live copy locally and the blueprint later changes?
- How would you structure `/content` for multi-market, multi-language?

## AEM-08: Legacy Migration Into AEM

Priority: `P1` (written/design exercise — the posting names this explicitly)

Scenario: A legacy JSP-based merchant portal (2,000 pages, 40 GB of PDFs/images, hand-written HTML) must move into AEM without a big-bang cutover.

Build (design doc under `notes/`):

- Content inventory and mapping: legacy URL space → new content tree; what becomes a page, a component, a DAM asset, or gets dropped.
- Ingestion approach: CSV/crawler → content packages or Sling POST/AssetManager code; idempotent re-runs (migrations always run more than once).
- URL preservation: mapping/redirect strategy (`/etc/map`, Dispatcher rewrites) so SEO and bookmarks survive.
- Phased cutover: reverse-proxy coexistence of legacy and AEM during migration, section by section, with rollback per section.
- Validation: automated diffing of migrated vs source content, broken-link scans, editor sign-off workflow.

Acceptance criteria:

- The plan is incremental and reversible, never big-bang.
- Idempotent ingestion and URL preservation are addressed explicitly.

Interview prompts:

- How do you migrate a legacy site into AEM while it stays live?
- Why must migration jobs be idempotent and resumable?
- How do you decide what becomes a reusable component vs one-off content?

## Suggested Order

`AEM-01 → 02 → 03 → 04 → 05` builds each concept on the previous one (tree → resource → model → servlet → service). Do `AEM-06..08` as spoken/written drills afterwards — they are where staff-level AEM interviews actually live.
