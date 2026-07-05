window.springReviewData = {
  title: "Spring Review Desk",
  learnerSnapshot: {
    profile:
      "5 years of Java experience, strong legacy instincts, some Spring Boot CRUD exposure, still building the mental model for the container and annotations.",
    currentTheme:
      "Architecture instincts are solid and the right failure categories are showing up faster. The next lift is precision: Spring rollback traps, concurrency-safe idempotency, and environment-safe bean wiring."
  },
  keepDoing: [
    {
      title: "Layer boundaries",
      detail:
        "You consistently separate controller, service, and repository responsibilities in the right direction."
    },
    {
      title: "Constructor injection instinct",
      detail:
        "You already prefer explicit dependencies and can spot why giant constructors are usually a smell."
    },
    {
      title: "Business logic placement",
      detail:
        "You understand that business decisions belong in services while repositories stay focused on persistence."
    },
    {
      title: "Transaction skepticism",
      detail:
        "You are starting to spot that '@Transactional' does not magically roll back external API calls or messaging side effects."
    },
    {
      title: "Bean resolution checklist",
      detail:
        "Your startup debugging flow for missing beans is becoming more structured and Spring-specific."
    },
    {
      title: "External side-effect awareness",
      detail:
        "You now consistently recognize that payment, Kafka, and database writes do not become one safe unit just because '@Transactional' exists."
    },
    {
      title: "Race-condition awareness",
      detail:
        "You are starting to see that duplicate requests can overlap in time, not just repeat later."
    }
  ],
  priorityFocus: [
    {
      title: "What a bean actually is",
      why:
        "Your answers still blur together object lifetime, scope, and container ownership.",
      upgrade:
        "Remember: a bean is not just a reusable object. It is an object Spring discovers or receives, creates, wires, post-processes, and optionally proxies."
    },
    {
      title: "When annotations matter",
      why:
        "You know annotations exist, but not yet how different ones change discovery, wiring, persistence, or transactions.",
      upgrade:
        "Treat annotations as instructions to infrastructure. Some register beans, some mark persistence models, and some wrap behavior through proxies."
    },
    {
      title: "Transactional rollback traps",
      why:
        "This is still the weakest area: when rollback fails unexpectedly, the first Spring-specific suspects are not coming to mind yet.",
      upgrade:
        "Memorize the four defaults to inspect first: exception type, swallowed exceptions, self-invocation/proxy bypass, and whether the object/method is actually Spring-intercepted."
    },
    {
      title: "Concurrency-safe idempotency",
      why:
        "You now see that retries can duplicate work, but you still underweight races, unique constraints, and database-level protection.",
      upgrade:
        "When clients retry, think idempotency key plus a uniqueness guarantee and a concurrency-safe write path, not only a service-layer lookup."
    }
  ],
  mentalModels: [
    {
      title: "Should Spring manage this object?",
      summary:
        "Spring should usually manage shared application components, not every object in the codebase.",
      remember: [
        "Good bean candidates: controllers, services, repositories, config classes, clients, reusable helpers.",
        "Usually not beans: DTOs, value objects, request models, entities used as data, short-lived calculation objects.",
        "If the object benefits from DI, lifecycle hooks, proxies, or app-wide reuse, Spring managing it may help."
      ],
      watchFor: [
        "Do not confuse '@Entity' with a service-style Spring bean.",
        "If you need runtime-specific constructor values for a one-off object, plain Java is often better."
      ]
    },
    {
      title: "Stereotype annotations",
      summary:
        "'@Component', '@Service', and '@Repository' all create Spring-managed components, but they communicate different intent.",
      remember: [
        "'@Component' is the generic stereotype.",
        "'@Service' marks business/application behavior.",
        "'@Repository' marks persistence code and can participate in exception translation.",
        "'@Controller' or '@RestController' is the HTTP-facing stereotype, not '@Component'."
      ],
      watchFor: [
        "They do not have completely different lifecycles just because the annotation label changes."
      ]
    },
    {
      title: "Why DI exists",
      summary:
        "Dependency injection is not mainly about saving keystrokes or avoiding 'new'.",
      remember: [
        "DI reduces coupling between collaborators.",
        "DI makes testing easier because dependencies can be swapped.",
        "DI centralizes wiring so lifecycle, proxies, and configuration can be applied consistently."
      ],
      watchFor: [
        "If wiring is broken, Spring usually fails fast at startup rather than silently saving you from all runtime errors."
      ]
    },
    {
      title: "What '@Transactional' really depends on",
      summary:
        "Transactions in Spring are commonly applied through proxies, so the way methods are invoked matters.",
      remember: [
        "A transactional method wraps the operation in a transaction boundary.",
        "Self-invocation inside the same class may bypass the proxy and skip expected transactional behavior.",
        "Transactions govern transactional resources, not every side effect like emails or external HTTP calls."
      ],
      watchFor: [
        "Do not assume every exception rolls back the same way.",
        "Do not assume plain objects created with 'new' get transactional behavior."
      ]
    },
    {
      title: "'@Bean' vs '@Component'",
      summary:
        "Both create Spring-managed objects, but they solve different ownership problems.",
      remember: [
        "Use '@Component' when the class is yours, can be scanned, and does not need special construction logic.",
        "Use '@Bean' when you need to construct a third-party SDK object, pass config values, build retry policies, or centralize infrastructure wiring.",
        "A wrapper you own can still be a '@Component' even if it delegates to a third-party client created by a '@Bean' method."
      ],
      watchFor: [
        "Do not think '@Bean' means 'leave everything to Spring'. A '@Bean' method is usually the explicit, hands-on option."
      ]
    },
    {
      title: "Retries and duplicate writes",
      summary:
        "When a client retries after a timeout, the safest fix is usually idempotency at the business operation boundary.",
      remember: [
        "A thin controller should pass a request identifier or idempotency key into the service layer.",
        "The service and database should cooperate to recognize and reject or reuse duplicate work.",
        "Unique constraints and deduplication logic are often more reliable than hoping timeouts or retries behave nicely."
      ],
      watchFor: [
        "Do not treat duplicate writes as mainly a controller or rate-limiting problem when the real issue is operation safety."
      ]
    },
    {
      title: "Transactions vs external effects",
      summary:
        "A database transaction can protect database work, but it does not automatically make remote payment calls and Kafka publishes behave atomically.",
      remember: [
        "If you charge a card and later roll back the database transaction, the charge may still stand.",
        "If Kafka publish fails after payment succeeds, you can still have an inconsistent system unless you design for recovery.",
        "Patterns like outbox, compensation, and explicit state transitions exist because these systems fail independently."
      ],
      watchFor: [
        "Do not assume catching an exception and returning a failure response means the transaction model is now safe."
      ]
    },
    {
      title: "Rollback traps checklist",
      summary:
        "When a transactional method does not roll back, Spring usually has a handful of repeat-offender causes.",
      remember: [
        "Checked exceptions do not always trigger rollback by default.",
        "If the exception is caught and swallowed, Spring may see a normal method completion and commit.",
        "Self-invocation can bypass the transactional proxy.",
        "If the object or method is not actually Spring-intercepted, the annotation may do nothing."
      ],
      watchFor: [
        "Do not jump first to repository implementation issues when the failure pattern points to proxy and exception behavior."
      ]
    }
  ],
  rounds: [
    {
      id: "round-1",
      label: "Round 1",
      score: "5.5/12",
      headline:
        "Strong architecture instincts, but the Spring container model was still mostly fuzzy.",
      strengths: [
        "You separated controller, service, and repository responsibilities in a healthy way.",
        "You understood constructor injection as more explicit and stable.",
        "You kept business logic distinct from database access."
      ],
      recurringMisses: [
        "DI was framed too much as a safeguard against forgetting to initialize an object.",
        "Bean lifecycle was reduced to 'lives through the app' instead of container-managed creation and hooks.",
        "Boot auto-configuration was treated as mostly annotation-driven instead of condition-driven.",
        "'@Component' was confused with HTTP-facing annotations."
      ],
      nextSteps: [
        "Rehearse what the container does at startup: scan, register, create, inject, post-process.",
        "Practice explaining Boot using classpath, properties, and conditional configuration.",
        "Keep separating service-layer rules from repository persistence work."
      ],
      corrections: [
        {
          title: "Dependency injection",
          yourTake:
            "Spring injects dependencies so we avoid runtime issues when someone forgets to initialize an object.",
          correction:
            "DI is mainly about reducing coupling, centralizing wiring, improving testability, and letting infrastructure manage lifecycle and proxies.",
          takeaway:
            "Think less 'Spring saves me from forgetting new' and more 'my class should not own wiring concerns.'",
          tags: ["di", "container", "testing"]
        },
        {
          title: "What '@Service' triggers",
          yourTake:
            "Spring scans, sees the service annotation, checks where it is used, creates it, and injects it.",
          correction:
            "Spring first registers a bean definition during component scanning, then creates the bean inside the application context, resolves constructor dependencies, and injects it into other beans.",
          takeaway:
            "The container is built first; usage wiring follows from that container.",
          tags: ["annotations", "startup", "beans"]
        },
        {
          title: "Bean lifecycle",
          yourTake:
            "A Spring lifecycle means the object stays available through the app, while normal objects die when they fall out of scope.",
          correction:
            "The key difference is not only duration. A bean has container-managed creation, injection, post-processing, initialization, scope handling, and optional destruction callbacks.",
          takeaway:
            "A bean is an object the container owns, not just an object that lives longer.",
          tags: ["beans", "lifecycle", "scope"]
        },
        {
          title: "Boot auto-configuration",
          yourTake:
            "Boot configures the app based on annotations developers provide.",
          correction:
            "Boot mostly reacts to libraries on the classpath, application properties, existing beans, and conditional rules. Annotations are only one part of the picture.",
          takeaway:
            "When Boot feels magical, ask what dependency, property, or condition made it decide that setup.",
          tags: ["boot", "autoconfiguration", "classpath"]
        },
        {
          title: "Stereotype annotations",
          yourTake:
            "'@Component' is HTTP, '@Service' is business logic, '@Repository' is DB logic.",
          correction:
            "'@Component' is the generic managed stereotype. HTTP belongs to '@Controller' or '@RestController'. '@Repository' marks persistence and can support exception translation.",
          takeaway:
            "These annotations mostly communicate role and integration, not wildly different lifecycles.",
          tags: ["annotations", "stereotypes", "mvc"]
        }
      ]
    },
    {
      id: "round-2",
      label: "Round 2",
      score: "4/8",
      headline:
        "You reasoned well about design smells, but proxy-based behavior and bean ownership still need sharper edges.",
      strengths: [
        "You recognized that manually using 'new' drops DI and lifecycle support.",
        "You identified that not every object belongs in the container.",
        "You called out that a service with too many dependencies is often a design smell."
      ],
      recurringMisses: [
        "The difference between '@Component', '@Bean', and plain Java creation was still unclear.",
        "Self-invocation and proxy traps around '@Transactional' were not yet part of the model.",
        "The bean injection debugging checklist was still too narrow.",
        "Explicit configuration was tied to app logic examples rather than infrastructure wiring examples."
      ],
      nextSteps: [
        "Practice explaining who creates the object in each case: component scan, bean method, or plain code.",
        "Remember that many Spring features only apply because the object came from the container.",
        "Build a standard debug checklist for missing or null dependencies."
      ],
      corrections: [
        {
          title: "Manually instantiating a service",
          yourTake:
            "You lose dependency injection and lifecycle methods.",
          correction:
            "You also lose proxy-based features like '@Transactional', '@Async', '@Cacheable', and any container-applied advice or configuration.",
          takeaway:
            "If Spring did not create the object, Spring usually cannot enhance it.",
          tags: ["beans", "proxies", "transactions"]
        },
        {
          title: "'@Component' vs '@Bean' vs plain object",
          yourTake:
            "The difference is mostly the lifecycle methods and what is associated with them.",
          correction:
            "'@Component' is auto-discovered and created by scanning. A '@Bean' method explicitly creates an object that Spring then manages. A plain object created inside a method is just normal Java unless handed to Spring.",
          takeaway:
            "Ask first: who creates this object, and does it live in the container?",
          tags: ["annotations", "beans", "configuration"]
        },
        {
          title: "Transactional self-invocation",
          yourTake:
            "Not sure.",
          correction:
            "A transactional method calling another transactional method in the same class often bypasses the Spring proxy, so the inner annotation may not apply the way people expect.",
          takeaway:
            "Method interception usually happens at the bean boundary, not automatically on every internal method call.",
          tags: ["transactions", "proxies", "aop"]
        },
        {
          title: "Convention over configuration",
          yourTake:
            "Boot gives prebuilt conventions and lifecycles for common operations.",
          correction:
            "In a CRUD app, convention over configuration means Boot can see Spring Data JPA, a Postgres driver, and datasource properties, then set up the DataSource, JPA, repositories, and embedded server without you writing much wiring code.",
          takeaway:
            "Boot works because the framework can infer the common case and only asks you to override when needed.",
          tags: ["boot", "crud", "autoconfiguration"]
        },
        {
          title: "When to configure explicitly",
          yourTake:
            "Use custom logic for things like sensitive data handling and logging safety.",
          correction:
            "A stronger Spring answer is to use explicit configuration when a third-party class cannot be annotated, when infrastructure wiring should be centralized, or when implicit annotation-based setup becomes too opaque.",
          takeaway:
            "Explicit config is often about ownership and clarity of infrastructure, not only about custom business logic.",
          tags: ["configuration", "annotations", "infrastructure"]
        }
      ]
    },
    {
      id: "round-3",
      label: "Round 3",
      score: "3.5/5",
      headline:
        "Your tradeoff reasoning is getting stronger, especially around transactions and service design, but explicit bean registration choices still need tightening.",
      strengths: [
        "You treated the DiscountCalculator question as a tradeoff instead of forcing one rule everywhere.",
        "You correctly rejected the idea that '@Transactional' can roll back external HTTP or Kafka side effects.",
        "You recognized that a giant service constructor might indicate overly broad responsibilities."
      ],
      recurringMisses: [
        "The debugging answer was good but still too light on interface implementations, conditional beans, profiles, and whether the missing dependency is created by Spring at all.",
        "The '@Bean' versus '@Component' distinction is still inverted for third-party client setup.",
        "You leaned quickly toward 'service is doing too much' without first entertaining that orchestration-heavy services can be valid."
      ],
      nextSteps: [
        "Practice a standard missing-bean checklist: is the dependency a bean, is it scanned or declared, is the right profile active, and are qualifiers or conditions involved.",
        "Get comfortable saying '@Bean' is the explicit construction tool for infrastructure and SDK clients.",
        "When evaluating large services, separate orchestration complexity from true responsibility sprawl."
      ],
      corrections: [
        {
          title: "Manual helper vs Spring bean",
          yourTake:
            "Manual instantiation is probably right because the calculator is short-lived, but it could become a component if reused broadly.",
          correction:
            "That is mostly good. The sharper decision rule is whether the calculator is just a pure helper with no collaborators or config, versus a reusable collaborator that benefits from DI, configuration, testing seams, or shared policy.",
          takeaway:
            "Short-lived alone does not decide it. Ownership, dependencies, and reuse patterns decide it.",
          tags: ["beans", "design", "components"]
        },
        {
          title: "Missing FraudClient bean",
          yourTake:
            "Check the annotation, package location, duplicates, bean properties, then logs.",
          correction:
            "A stronger Spring debugging flow is: confirm the consuming class itself is Spring-managed, confirm 'FraudClient' has an implementation or '@Bean' definition, confirm component scanning reaches it, confirm profiles or conditional config are not excluding it, then check qualifiers or duplicate candidates and finally inspect startup logs.",
          takeaway:
            "Always ask first whether Spring knows how to create the dependency at all.",
          tags: ["debugging", "beans", "annotations"]
        },
        {
          title: "Transactional safety claim",
          yourTake:
            "No. Transactions only work on resources that can be rolled back. Kafka and HTTP actions cannot be rolled back the same way.",
          correction:
            "This is the right core answer. The stronger version adds that you may save the order, fail the charge, or charge the card and still fail the database commit or event publish, so you need patterns like outbox, compensation, retries, or sagas.",
          takeaway:
            "Good instinct: database transactions do not automatically unify external side effects into one atomic operation.",
          tags: ["transactions", "distributed-systems", "design"]
        },
        {
          title: "Service with 11 dependencies",
          yourTake:
            "The service is doing too much work and should maybe be decoupled into different services or components.",
          correction:
            "That may be true, but do not assume it immediately. Some orchestration services legitimately coordinate many collaborators. First ask whether the class represents one business workflow, whether mappers or helpers are inflating the count, and whether dependencies cluster around sub-responsibilities.",
          takeaway:
            "Treat a huge constructor as a smell worth investigating, not automatic proof of bad design.",
          tags: ["design", "services", "smells"]
        },
        {
          title: "Third-party SDK wiring",
          yourTake:
            "Prefer '@Component'; '@Bean' is for when we leave everything to Spring.",
          correction:
            "This is backwards. For a third-party client with API keys, timeouts, and retry policy, '@Bean' in a configuration class is usually the right choice because you are explicitly constructing and configuring that object. '@Component' is better when Spring can just scan and instantiate your own class directly.",
          takeaway:
            "'@Bean' is the explicit infrastructure wiring tool; '@Component' is the discoverable class stereotype.",
          tags: ["configuration", "beans", "third-party"]
        }
      ]
    },
    {
      id: "round-4",
      label: "Round 4",
      score: "2/5",
      headline:
        "Your bean-startup debugging is getting sharper, but duplicate-write prevention, rollback failure analysis, and incremental refactoring still need stronger Spring instincts.",
      strengths: [
        "You gave a solid first-pass checklist for a missing bean at startup.",
        "You correctly pushed back on the idea that '@Transactional' by itself should make code dramatically slower.",
        "You continue to think in terms of separation of concerns, even when the refactoring plan was incomplete."
      ],
      recurringMisses: [
        "The duplicate orders problem was treated like a timeout/transaction issue instead of an idempotency and data-integrity issue.",
        "You still need a sharper checklist for why a transactional rollback may not happen.",
        "The controller refactor plan focused only on moving repository calls instead of carving out business rules and orchestration into services incrementally."
      ],
      nextSteps: [
        "Practice answering duplicate-write problems with idempotency keys, unique constraints, and service-layer deduplication.",
        "Memorize the top Spring rollback traps: exception type, self-invocation, proxy boundaries, and swallowed exceptions.",
        "For messy controllers, start by extracting one business use case into a service while keeping the endpoint contract stable."
      ],
      corrections: [
        {
          title: "Missing PaymentGateway bean",
          yourTake:
            "Check whether the consuming class is Spring-managed, whether there is an implementation or '@Bean', whether scanning reaches it, whether profiles or qualifiers are involved, then inspect logs.",
          correction:
            "This is a strong answer. The only extra detail to add is checking whether a recent refactor changed package boundaries, active profiles, or conditional auto-configuration so the bean stopped being registered.",
          takeaway:
            "This is becoming a strength: you are starting with how Spring would create the dependency, not just staring at the failing injection point.",
          tags: ["debugging", "beans", "startup"]
        },
        {
          title: "Duplicate orders after retry",
          yourTake:
            "Add '@Transactional' in the service and maybe rate limiting/backoff so the timeout returns an error response.",
          correction:
            "The stronger fix is idempotency. If the frontend retries the same logical order, the backend should recognize that request and avoid inserting a second order. Typical fixes are an idempotency key, a unique business constraint, or service logic that safely reuses the first result.",
          takeaway:
            "Transactions help consistency inside one attempt. Idempotency protects you across repeated attempts.",
          tags: ["idempotency", "transactions", "data-integrity"]
        },
        {
          title: "Are transactions slow?",
          yourTake:
            "Transactional uses a proxy and should not make code much slower. Ask the team to look again.",
          correction:
            "That is directionally fair, but the better answer is to investigate what changed inside the transaction: longer transaction scope, lazy loading, extra queries, lock contention, remote calls inside the transaction, or a more expensive isolation level.",
          takeaway:
            "Do not defend or dismiss the annotation in the abstract. Inspect what work now lives inside the transaction boundary.",
          tags: ["transactions", "performance", "debugging"]
        },
        {
          title: "Rollback did not happen",
          yourTake:
            "Not sure.",
          correction:
            "First Spring-specific suspects are: the exception was checked and not configured for rollback, the exception was caught/swallowed, the call happened through self-invocation inside the same class, the object was not Spring-managed, or the method visibility/proxy setup prevented interception.",
          takeaway:
            "When rollback surprises you, think proxy boundary plus exception behavior before anything else.",
          tags: ["transactions", "aop", "debugging"]
        },
        {
          title: "Incremental controller refactor",
          yourTake:
            "Move repository calls to the repository layer first.",
          correction:
            "Repository calls already belong there. The higher-value first move is to extract one endpoint's business flow into a service method, move validation/business rules there, keep the controller thin, and do it one use case at a time without changing the whole system at once.",
          takeaway:
            "In an ugly Spring MVC project, the first safe win is usually controller-to-service extraction, not wholesale rewiring.",
          tags: ["refactoring", "controllers", "services"]
        }
      ]
    },
    {
      id: "round-5",
      label: "Round 5",
      score: "3/8",
      headline:
        "You can now spot several real failure modes in a Spring order flow, but concurrency-safe idempotency, transactional nuance, and long-term recovery architecture are still the biggest gaps.",
      strengths: [
        "You immediately noticed that the idempotency key being optional is dangerous in a retried write flow.",
        "You correctly recognized that payment can succeed while later database or Kafka work fails.",
        "You noticed that the service is overloaded with too many responsibilities in one transactional method.",
        "You continued to call out that Kafka and other external side effects are not protected the same way as database work."
      ],
      recurringMisses: [
        "You still lean more on service logic than on database-backed guarantees like unique constraints and race-safe write patterns.",
        "The staging startup issue was identified as configuration-related, but the profile mismatch was not fully pinned down.",
        "Latency analysis missed the most likely new slow points: fraud calls, payment timeouts, Kafka inside the request path, extra audit writes, and transaction contention.",
        "The immediate remediation plan was missing safe partial steps like disabling risky retries, adding a uniqueness guard, and fixing exception/rollback behavior.",
        "The long-term cleanup answer drifted into operational recovery work instead of Spring/application architecture improvements."
      ],
      nextSteps: [
        "Practice reasoning about duplicate requests as a race condition, not only as a missing header problem.",
        "Memorize that '@Profile(\"prod\")' means staging may have no bean unless another bean definition exists for that environment.",
        "When diagnosing latency, inspect every new external call and every new piece of work inside the transaction boundary.",
        "Separate 'incident recovery actions' from 'architecture cleanup plan' when answering senior-level system questions."
      ],
      corrections: [
        {
          title: "Top design flaws",
          yourTake:
            "Optional idempotency key, service doing too many things, Kafka should not be inside the transaction, and the repository/key handling looks off.",
          correction:
            "This was a good start. The stronger version adds: no database uniqueness guarantee around idempotency, external payment and Kafka side effects mixed into one request transaction, swallowed payment exceptions that can still commit partial state, and too much orchestration in one method.",
          takeaway:
            "You are seeing the right categories now. The next step is to name the exact failure mode each flaw creates.",
          tags: ["design", "idempotency", "transactions"]
        },
        {
          title: "How duplicates happen",
          yourTake:
            "Frontend retries and the idempotency key is optional, so duplicates can slip through.",
          correction:
            "That is correct but incomplete. Even with the same key, two concurrent requests can both read 'no existing order' before either insert commits. Without a unique constraint or locking strategy, both can proceed, both can charge, and both can create rows or partial rows.",
          takeaway:
            "Idempotency is not just about having a key. It must be race-safe under concurrency.",
          tags: ["idempotency", "concurrency", "data-integrity"]
        },
        {
          title: "Charged but no order visible",
          yourTake:
            "Payment can succeed and then later Kafka/audit/status steps can fail, so the charge stands while the rest does not.",
          correction:
            "This is a solid answer. One extra nuance: a payment failure path is also unsafe because the exception is swallowed and a failure response is returned, which can still commit earlier database writes depending on the state changes already made.",
          takeaway:
            "You now have the core distributed-systems instinct: remote side effects can outlive transaction outcomes.",
          tags: ["transactions", "distributed-systems", "payments"]
        },
        {
          title: "Why staging fails to start",
          yourTake:
            "It seems related to configuration and maybe prod config being wrong.",
          correction:
            "The likely issue is simpler: the only FraudClient bean is declared under '@Profile(\"prod\")', so staging has no FraudClient bean unless another staging/default implementation exists. First check active profiles and bean definitions for non-prod environments.",
          takeaway:
            "This is a Spring bean registration problem driven by profile scoping.",
          tags: ["profiles", "beans", "configuration"]
        },
        {
          title: "Latency jump",
          yourTake:
            "If the idempotency key is null, maybe the whole table is checked.",
          correction:
            "Possible, but the stronger suspects are the newly added fraud call, payment timeouts and retries, Kafka send in the request path, extra audit table writes, longer transaction lifetime, and database contention shown by the concurrent update / serialization errors.",
          takeaway:
            "When latency regresses after a deploy, start with every new network hop and every new unit of work added inside the transaction.",
          tags: ["performance", "transactions", "debugging"]
        },
        {
          title: "24-hour remediation plan",
          yourTake:
            "Make the key required and refactor so messaging happens after business logic.",
          correction:
            "Those are good pieces, but the first safe plan should usually include: require or generate an idempotency key, add a uniqueness constraint or equivalent guard, stop swallowing payment exceptions silently, disable or tame risky frontend retries, ensure Kafka publish does not control the DB transaction outcome, and patch the staging bean profile issue.",
          takeaway:
            "A strong hotfix plan reduces risk quickly without demanding a full rewrite.",
          tags: ["incident-response", "idempotency", "transactions"]
        },
        {
          title: "Long-term cleanup plan",
          yourTake:
            "Audit processed orders, refund duplicates, and repair missing orders one by one.",
          correction:
            "That is incident recovery work, not the architecture cleanup the question was asking for. The longer-term design plan would include explicit order states, outbox/event publishing strategy, clearer service boundaries, environment-safe bean configuration, race-safe idempotency storage, and better observability around retries and external calls.",
          takeaway:
            "Separate 'how we clean up the incident' from 'how we redesign the system so this class of incident is less likely again.'",
          tags: ["architecture", "recovery", "design"]
        }
      ]
    },
    {
      id: "round-6",
      label: "Weakness Drill",
      score: "2.5/6",
      headline:
        "You now recognize the race-condition shape of duplicate requests and the profile shape of missing beans, but rollback traps are still the least stable part of your Spring model.",
      strengths: [
        "You correctly saw that two requests with the same idempotency key can still race and both proceed.",
        "You moved toward locking/coordination thinking instead of only blaming retries.",
        "You knew '@Bean' plus configuration is the right general direction for a third-party SDK.",
        "You kept calling out that payment can succeed while later Kafka or database work fails."
      ],
      recurringMisses: [
        "The rollback answer still did not land on the core Spring suspects like checked exceptions, swallowed exceptions, self-invocation, and proxy/interception boundaries.",
        "The bean/profile answer was directionally right but still too broad; you need a more explicit menu of default bean, per-profile bean, or stub implementation choices.",
        "The third-party SDK wiring answer missed configuration properties and environment-specific config structure.",
        "The tonight plan for duplicates was still missing strong containment steps like calming retries and adding a DB-backed uniqueness guard."
      ],
      nextSteps: [
        "Lock in the rollback checklist until it comes out automatically.",
        "For environment wiring, practice answering with concrete options: default bean, profile-specific beans, or conditional/stub beans.",
        "For SDK integration, pair '@ConfigurationProperties' with '@Bean' and profile-aware settings.",
        "For duplicate writes, combine service logic with a database-enforced uniqueness rule."
      ],
      corrections: [
        {
          title: "Same idempotency key, two app instances",
          yourTake:
            "Both requests can see nothing and process at the same time. A lock around the key would help.",
          correction:
            "That is the right race-condition instinct. The stronger production answer is to combine the key with a database uniqueness guarantee or another shared coordination mechanism, because app-local locks do not protect across multiple instances.",
          takeaway:
            "Idempotency must be enforced in a place all instances share, usually the database or a dedicated coordination store.",
          tags: ["idempotency", "concurrency", "distributed-systems"]
        },
        {
          title: "Rollback did not happen",
          yourTake:
            "Maybe the DB write was elsewhere, the repository was not implemented properly, or Spring did not recognize the resource.",
          correction:
            "The first Spring-specific suspects should be: checked exception instead of unchecked, exception caught/swallowed, self-invocation bypassing the proxy, or the method/object not actually being intercepted by Spring.",
          takeaway:
            "When transactional rollback surprises you, think proxy and exception semantics before persistence plumbing.",
          tags: ["transactions", "aop", "debugging"]
        },
        {
          title: "Missing PaymentGateway outside prod",
          yourTake:
            "If different profiles are not required remove the profile, otherwise create one for each profile.",
          correction:
            "That is directionally right. The more complete answer is: either make one default bean for all environments, provide separate beans per profile like real vs stub, or use properties/conditional config when behavior differs but the type should always exist.",
          takeaway:
            "A clean Spring answer names the concrete registration strategy, not just 'fix the config'.",
          tags: ["profiles", "beans", "configuration"]
        },
        {
          title: "Third-party fraud SDK wiring",
          yourTake:
            "Use '@Bean' and possibly a configuration class because specific config is needed.",
          correction:
            "Good direction. The stronger Spring shape is: bind API key, endpoint, and timeout with '@ConfigurationProperties'; construct the SDK client in a '@Bean' method; and use profile-specific property values or profile-specific beans only when the actual implementation differs.",
          takeaway:
            "'@Bean' builds the client, '@ConfigurationProperties' feeds it, and profiles shape environment differences.",
          tags: ["configuration", "beans", "profiles"]
        },
        {
          title: "Payment succeeds, Kafka fails, transaction rolls back",
          yourTake:
            "Charge can stand, order state can mismatch; short term decouple Kafka and payment, long term better exception handling and states.",
          correction:
            "That is a fair answer. The stronger long-term design fix is explicit state transitions plus an outbox or equivalent reliable event publication strategy, with compensation or recovery for already-charged cases.",
          takeaway:
            "You have the right inconsistency instinct; now tie it to a standard recovery pattern.",
          tags: ["transactions", "outbox", "design"]
        },
        {
          title: "Tonight plan for duplicates",
          yourTake:
            "Leave orders pending, force idempotency key, refactor, reprocess, verify no duplicates.",
          correction:
            "A tighter tonight plan is: contain blast radius by calming retries or temporarily gating new writes if needed, require/generate idempotency key, add a DB-backed uniqueness guard, then verify with logs/DB queries/payment reports before broad re-enablement.",
          takeaway:
            "A hotfix plan starts with containment, then the smallest reliable guardrail, then verification.",
          tags: ["incident-response", "idempotency", "operations"]
        }
      ]
    }
  ]
};
