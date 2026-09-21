# CB-Partner — Base Architecture Decision Record

**Status:** DOCUMENTED — RESEARCH/FREEZE RECORD THROUGH BASE-ARCH-012  
**Scope:** Frontend repository only  
**Purpose:** Authoritative durable record of the Base Architecture research and decisions completed through BASE-ARCH-012. This document is implementation input. Implementation must follow the frozen decisions and must not reinterpret them from historical chat.

> **Important:** BASE-ARCH-001–003 are recorded as researched foundation work. BASE-ARCH-004–012 are recorded as decision-frozen. Exact implementation APIs, versions, and other items explicitly marked deferred remain open until the applicable implementation-planning stage.

---

## 1. Authority and Implementation Use

The implementation phase must use this document together with:

1. `AI_START_HERE.md`
2. `docs/project-management/12-PROJECT-TRACKER.md`
3. `docs/project-management/04-CURRENT-STATUS.md`
4. the approved implementation plan created after Base Architecture is complete
5. the relevant implementation-status document
6. actual Git/Trello state

Implementation MUST NOT rely on historical conversation as an architecture source.

If implementation reveals a conflict with a frozen decision:

**STOP → REPORT → DISCUSS → DECIDE → FREEZE → UPDATE PLAN → IMPLEMENT**

No silent architecture change is permitted.

---

# BASE-ARCH-001 — Project and Gradle Module Structure

**State:** RESEARCHED  
**Freeze state:** Not independently frozen in the current record.

## Research focus

- Large-project Kotlin Multiplatform / Compose Multiplatform structure.
- Current JetBrains/Kotlin project structure.
- Platform entry-point separation.
- Gradle multi-project organization.
- Convention plugins / `build-logic`.

## Research conclusion

The project should use a scalable multi-module Kotlin Multiplatform structure with explicit platform application entry points and shared architecture modules. Current JetBrains/Kotlin guidance supports separating shared code from Android, iOS and desktop entry points rather than blindly adopting an old single shared `composeApp` structure.

Gradle convention plugins belong in an included `build-logic` build for scalable build conventions.

## Direction carried forward

```text
CB-Partner/
├── androidApp/
├── iosApp/
├── desktopApp/
├── core/
├── domain/
├── data/
├── navigation/
├── feature/
│   ├── splash/
│   └── dynamic/
├── build-logic/
├── gradle/
└── docs/
```

No additional top-level `shared/` module is required merely to contain the architecture modules above.

---

# BASE-ARCH-002 — Module Responsibilities and Dependency Graph

**State:** RESEARCHED  
**Freeze state:** Not independently frozen in the current record.

## Responsibility model

- Platform apps: thin platform-specific entry points.
- Core: shared technical foundation.
- Domain: generic domain/business abstractions and logic; no current business-specific feature architecture.
- Data: generic data-access implementations.
- Feature: application-facing UI and Pure Store/presentation.
- Navigation: application navigation.
- DI: composition root/object graph; currently not a standalone Gradle module.
- Build logic: Gradle conventions in the included build.

## Dependency direction carried forward

```text
Feature → Domain
Feature → Core
Feature → Navigation

Data → Domain
Data → Core

Domain → Core

Navigation → Core

DI → Core
DI → Domain
DI → Data
DI → Navigation
DI → Feature

Platform Apps → shared/application modules
```

## Forbidden dependencies

```text
Feature -X-> Data
Domain -X-> Data
Domain -X-> Feature
Data -X-> Feature
Core -X-> Domain/Data/Feature/DI
Domain -X-> DI
Data -X-> DI
Feature -X-> DI
Domain -X-> Ktor/Compose UI/platform APIs
```

## Business-scope exclusion

Do not create base-architecture modules/folders for authentication, booking, payment, dashboard, OTP or other business capabilities at this stage. Those future capabilities must not dictate the base architecture.

---

# BASE-ARCH-003 — Gradle Module Granularity

**State:** RESEARCHED  
**Freeze state:** Not independently frozen in the current record.

## Direction carried forward

Initial Gradle modules:

```text
:core
:domain
:data
:navigation
:feature:splash
:feature:dynamic
:androidApp
:iosApp
:desktopApp
```

`build-logic/` is a separate included Gradle build.

## Module-granularity rules

- Avoid module explosion.
- `:core`, `:domain`, and `:data` initially remain single modules with packages.
- `:feature:splash` and `:feature:dynamic` are justified feature boundaries.
- Use `implementation` by default.
- Use `api` only when a public API genuinely requires it.
- Do not create `:di`, `:store`, `:mvi`, or `:presentation-core` modules without a later concrete boundary justification.
- Module boundaries must represent real ownership, dependency control, build concerns, platform separation, or feature isolation.

---

# BASE-ARCH-004 — Core Architecture and Technical Infrastructure

**State:** DECISION_FROZEN

## Accepted

- Single `:core` Gradle module.
- Package-level organization:
  - `common/`
  - `coroutine/`
  - `error/`
  - `logging/`
  - `network/`
  - `serialization/`
  - `time/`
- Ktor Client for networking foundation.
- `kotlinx.serialization` for serialization.
- `kotlinx.coroutines` for coroutine infrastructure.
- Kermit for logging.
- Kotlin `Result<T>` as the generic success/failure primitive.
- Technical error handling in Core; business errors outside Core.
- Compose Multiplatform Resources as the selected resource technology.
- Resources are generally owned by the consuming feature/module rather than centralized in Core.
- Prefer `kotlin.time`; use `kotlinx-datetime` only where needed.
- `kotlinx.collections.immutable` remains optional.
- `expect/actual` only for genuine platform-specific behavior.
- `implementation` by default; `api` only when public API requires it.

## Rejected

- Arrow as the baseline.
- Custom universal `AppResult`.
- Loading state inside `Result<T>`.
- Custom universal date/time abstraction.
- Giant utility framework.
- Separate Gradle module per Core package.
- Mandatory abstractions such as `NetworkClient`, `DispatcherProvider`, or `AppLogger` without a concrete need.

## Deferred

- Exact JSON configuration.
- Exact technical error hierarchy.
- Exact library/plugin versions for implementation planning.

## Core non-responsibilities

Core does not contain business-specific models, repositories, use cases, Feature UI, Store/MVI implementation, Navigation, DI, authentication, booking, payment, SDUI, dynamic JSON, or business-specific architecture.

---

# BASE-ARCH-005 — Navigation and Application Composition

**State:** DECISION_FROZEN

## Accepted

- Compose Multiplatform Navigation 3 is the primary navigation direction.
- Single `:navigation` module.
- Feature-owned destinations.
- Application-owned back stack.
- Store state and navigation state remain separate.
- UI → Store → controlled navigation flow.
- DI constructs dependencies.
- Application Root composes the application.
- Navigation owns navigation state.
- Platform apps remain thin entry points.
- Deep-link capability is accepted as a capability; exact routes are out of scope.
- Back handling belongs to the navigation system.

## Rejected as primary

- Decompose.
- Circuit.
- Voyager.
- Custom navigation framework.

These remain reference material where useful; they are not the selected primary architecture.

## Navigation responsibilities

`:navigation` owns:

- application navigation contracts/infrastructure
- destination integration
- back-stack integration
- navigation host/root orchestration
- destination registration
- serialization integration where required
- platform navigation behavior

It does NOT own:

- Feature presentation state
- Store logic
- business/domain logic
- repositories
- networking
- SDUI
- business models

## Deferred

- Exact Navigation 3 APIs.
- Exact destination-registration mechanism.
- Exact command/bridge API.
- Exact dependency version.

---

# BASE-ARCH-006 — Dependency Injection and Application Composition

**State:** DECISION_FROZEN

## Accepted

1. Koin is the primary DI technology.
2. Koin Compiler Plugin is accepted; exact versions remain implementation-planning work.
3. Constructor injection is the primary class design.
4. Application Composition Root is the central composition boundary.
5. Long-lived application infrastructure may be application scoped.
6. Store lifetime is NOT automatically singleton.
7. Feature-specific scopes are not automatic.
8. Platform dependencies are supplied through appropriate platform boundaries.
9. Unit tests should use constructor-based replacement and should not require Koin.
10. DI graph verification is separate from ordinary unit tests.
11. DI remains a composition mechanism, not an architectural/business layer.
12. Logical DI definitions may remain module-local with application-level assembly.

## Rejected

- Metro as primary.
- kotlin-inject as primary.
- Kodein as primary.
- A standalone `:di` Gradle module for now.
- Classes calling Koin `get()`/`inject()` internally as the normal dependency mechanism.
- One giant DI/god module.

## Lifecycle rule

DI does not own:

- Store state
- navigation state
- business logic
- UI state
- feature lifecycle

---

# BASE-ARCH-007 — Feature and Pure Store Architecture

**State:** DECISION_FROZEN

## Frozen Store model

```text
UI
 ↓
Intent
 ↓
Store
 ↓
Executor
 ↓
Message
 ↓
Reducer
 ↓
State
 ↓
UI
```

The Store is the feature's presentation-state and behavior boundary.

## Accepted

- Pure Store.
- MVI/UDF.
- MVIKotlin as the Store implementation/reference technology.
- CB-Partner owns its architecture; MVIKotlin surrounding architecture is not adopted wholesale.
- Intent.
- State.
- Message.
- Pure Reducer.
- Executor for asynchronous side effects.
- Effect as the project vocabulary for navigation/one-off output; MVIKotlin Label maps conceptually to Effect.
- Navigation outside Store State.
- Feature/application composition owns Store lifecycle.
- Store lifecycle: Created → Started/Active → Disposed.
- Store must not be recreated by Compose recomposition.
- Feature-level Stores.
- `feature:splash`.
- `feature:dynamic`.
- Feature → Domain/Core/Navigation.
- Reducer testing.
- Executor/Store testing.
- UI testing separately.

## Rejected

- ViewModel.
- Decompose lifecycle as a second lifecycle framework.
- Global Store.
- Store singleton by default.
- Store-to-Store communication as the default architecture.
- `:store`, `:mvi`, or `:presentation-core` modules.
- Feature → Data.
- UI → Data.
- Reducer → Data/network.
- Reducer side effects.
- Core → Store implementations.
- Koin as a requirement inside Store unit tests.

## Frozen conceptual flow

```text
                         FEATURE
                            │
             ┌──────────────┴──────────────┐
             │                             │
            UI                           STORE
             │                             │
          Intent ─────────────────────────►│
             │                       ┌─────┴─────┐
             │                       │           │
             │                   Executor      Reducer
             │                       │           ▲
             │                       ▼           │
             │                    Message ───────┘
             │                                   │
             ◄──────────────────── State ────────┘
             │
             └────── Effect ──────► Navigation
```

## Deferred

- Exact Store API.
- Exact StateFlow exposure/observation strategy.

---

# BASE-ARCH-008 — Application Bootstrap and Startup Flow

**State:** DECISION_FROZEN

## Frozen composition

```text
Platform App
    ↓
Application Root
    ↓
DI
    ↓
Navigation
    ↓
Splash
    ↓
application bootstrap
```

The scope intentionally excludes authentication, booking, payment, SDUI internals, dynamic JSON, and business-specific startup architecture.

---

# BASE-ARCH-009 — Application Root and Platform Composition Boundary

**State:** DECISION_FROZEN

## Frozen principles

- Platform applications are thin entry points.
- Application Root is the shared application composition boundary.
- Application Root composes DI, Navigation and Features.
- Application Root contains no business logic.
- Platform-specific APIs must not leak into shared architecture layers.
- No separate `:app` module is required for the current architecture.
- Shared application composition is kept separate from platform entry-point mechanics.

---

# BASE-ARCH-010 — Application Startup Lifecycle and Initialization Ordering

**State:** DECISION_FROZEN

## Frozen ordering

```text
Platform entry point
    ↓
Application Root
    ↓
initialize DI / dependency graph
    ↓
initialize application navigation
    ↓
create/start Splash Store
    ↓
perform application bootstrap
    ↓
success → controlled navigation onward
```

Startup initialization must not be scattered arbitrarily across composables or platform modules.

The architecture does not define business-specific authentication, booking, payment, or SDUI initialization here.

---

# BASE-ARCH-011 — Application Startup Failure, Retry and Recovery Boundary

**State:** DECISION_FROZEN

## Frozen rules

- Startup/bootstrap failure belongs to Splash.
- Splash Store owns startup failure state.
- Retry is initiated through Intent.
- Retry creates a new bootstrap attempt.
- At most one bootstrap attempt is active for a Store.
- Retry does not restart DI, Application Root, Navigation, or the application.
- Normal coroutine cancellation is not presented as a user-facing failure.
- Store disposal cancels owned bootstrap work.
- Only the current active attempt may update current Store state.
- Structured concurrency is required.
- Manual retry is the default.
- Automatic retry is not yet frozen.
- Exact timeout, retry count and backoff are deferred.
- Business-specific error classification is out of scope.

---

# BASE-ARCH-012 — Application Startup State and Bootstrap Contract

**State:** DECISION_FROZEN

## Frozen contract

Bootstrap has a Domain-facing abstraction.

```text
Splash Store
    ↓
Domain bootstrap contract
    ↓
Data implementation
    ↓
Core Network
    ↓
Result<BootstrapOutput>
    ↓
Executor
    ↓
Message
    ↓
Reducer
    ↓
State / Effect
    ↓
UI / Navigation
```

The illustrative contract discussed was:

```kotlin
interface ApplicationBootstrap {
    suspend operator fun invoke(): Result<BootstrapData>
}
```

The exact interface name/API is NOT frozen. The responsibility is frozen.

## Accepted

- Domain-facing bootstrap abstraction.
- Data implements the bootstrap abstraction.
- Kotlin `Result<T>` as operation outcome.
- Loading belongs to Splash State.
- Bootstrap output is UI-independent.
- Feature maps bootstrap outcome to presentation State/Effect.
- Failure is represented in Splash State.
- Retry is initiated through Intent.
- One active bootstrap attempt.
- Bootstrap is cancellable.
- Store owns bootstrap lifecycle.
- Structured concurrency.
- Bootstrap success may produce an Effect without requiring persistent success state.

## Rejected

- Splash depending directly on Data.
- Custom universal `AppResult`.
- Loading encoded inside `Result<T>`.
- Bootstrap returning Compose/UI objects.
- Bootstrap knowing Navigation.
- Bootstrap returning navigation commands.
- Bootstrap knowing SDUI internals.
- Bootstrap defining dynamic JSON.
- Universal error hierarchy in Core.
- Success requiring persistent UI state.
- Detached/global bootstrap coroutine.

## Frozen conceptual startup state

```text
StartupState
├── Loading
└── Failure
```

Success may transition through an internal message and then Effect → Navigation rather than remaining as persistent Splash UI state.

## Deferred

- Exact `BootstrapOutput`/`BootstrapData` fields.
- Exact Domain interface/API.
- Exact error hierarchy and error mapping.
- Exact timeout.
- Automatic retry.
- Retry count/backoff.
- Backend/API contract.
- Authentication/session persistence.
- Business-specific startup behavior.

## Explicitly out of scope

- Authentication
- OTP
- Booking
- Payment
- Dashboard
- SDUI internals
- Dynamic JSON
- business-specific repositories/use cases
- business-specific error models

---

# 13. Cross-Architecture Rules Frozen Through 012

The following rules must be preserved by later architecture and implementation work:

### Layer direction

```text
Platform Apps
    ↓
Application Root
    ↓
Feature / Navigation / DI composition

Feature → Domain
Feature → Core
Feature → Navigation

Data → Domain
Data → Core

Domain → Core

Navigation → Core

DI → Core, Domain, Data, Feature, Navigation
```

### Presentation

- Pure Store.
- MVI/UDF.
- No ViewModel.
- Reducer is pure.
- Executor owns asynchronous side effects.
- Navigation is outside Store State.
- Store lifecycle is explicit and composition-owned.

### Startup

- Platform entry point is thin.
- Application Root composes.
- DI initializes dependencies.
- Navigation initializes navigation.
- Splash owns bootstrap presentation state.
- Bootstrap is Domain-facing and UI-independent.
- Data implements bootstrap.
- Core provides technical infrastructure.
- Bootstrap cancellation follows Store lifecycle.
- Retry is a Splash Intent.

### Complexity control

- No speculative abstractions.
- No module explosion.
- No global Store.
- No god DI module.
- No business-specific architecture in Base Architecture.
- Deferred details remain deferred until the correct planning stage.

---

# 14. Explicit Base-Architecture Non-Goals

The following are intentionally NOT designed by BASE-ARCH-001–012:

- Authentication architecture.
- OTP architecture.
- Booking architecture.
- Payment architecture.
- Dashboard architecture.
- Business-specific repositories/use cases.
- Business-specific data sources.
- SDUI internals.
- Dynamic JSON schema.
- Template/component/section/group/element implementation.
- SDUI registry implementation.
- Server-driven action schema.
- Specific backend API contracts.
- Business-specific session/persistence model.

`feature:dynamic` is currently only the application-facing Dynamic boundary. The separate SDUI module and its internals will be designed later.

---

# 15. Deferred-Decision Register

The following items must NOT be silently invented during implementation:

- Exact Navigation 3 API/registration/bridge.
- Exact Store API.
- Exact StateFlow exposure strategy.
- Exact Koin/Kotlin/plugin versions.
- Exact bootstrap API/model.
- Exact BootstrapOutput fields.
- Exact technical/business error mapping.
- Timeout policy.
- Automatic retry policy.
- Retry count/backoff.
- Backend API contract.
- Persistence/session restoration.
- SDUI internals.

When one becomes necessary, create the appropriate research/decision/plan unit before implementation depends on it.

---

# 16. Implementation Contract

When Base Architecture is eventually complete, the implementation plan must translate this record into:

1. exact modules to create;
2. exact module dependencies;
3. exact package responsibilities;
4. exact library/plugin versions;
5. exact public interfaces;
6. exact Store contracts;
7. exact Application Root composition;
8. exact DI construction;
9. exact Navigation integration;
10. exact bootstrap implementation;
11. exact testing requirements;
12. exact implementation sequence;
13. acceptance criteria;
14. prohibited changes.

Until that implementation plan is explicitly frozen, **BASE-ARCH documentation is not authorization to write application source code.**

---

# 17. Current Status

```text
BASE-ARCH-001 → RESEARCHED
BASE-ARCH-002 → RESEARCHED
BASE-ARCH-003 → RESEARCHED
BASE-ARCH-004 → DECISION_FROZEN
BASE-ARCH-005 → DECISION_FROZEN
BASE-ARCH-006 → DECISION_FROZEN
BASE-ARCH-007 → DECISION_FROZEN
BASE-ARCH-008 → DECISION_FROZEN
BASE-ARCH-009 → DECISION_FROZEN
BASE-ARCH-010 → DECISION_FROZEN
BASE-ARCH-011 → DECISION_FROZEN
BASE-ARCH-012 → DECISION_FROZEN
```

**Next architecture unit:** BASE-ARCH-013 — Application Bootstrap Data Flow & Layer Ownership.

However, BASE-ARCH-013 must not begin until the project owner explicitly chooses to resume Base Architecture research after this documentation checkpoint.
