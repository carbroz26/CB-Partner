# Splash + Bootstrap — Feature Discussion

**Feature:** `splash-bootstrap`  
**Tracking ID:** `SPLASH-BOOTSTRAP-001`  
**Discussion Status:** PARTIALLY FROZEN — EXTENDED DISCUSSION OPEN  
**Current Implementation Status:** FOUNDATION IMPLEMENTED / FOUNDATION ARCHITECTURE RECONCILIATION PLANNED  
**Implementation Branch:** `feature/dynamic-ui`

## 1. Current Partial Freeze

The implemented and verified foundation is:

```text
Application start
    ↓
Splash UI
    ↓
Bootstrap API request
    ↓
Receive + validate Bootstrap response
    ├── Failure → error + Retry
    └── Success → Bootstrap result available
```

This foundation is partially frozen. It is **not** the final Bootstrap/startup freeze.

Already established:
- Shared Compose Multiplatform Splash UI in `commonMain`.
- Pure Store/MVI/UDF flow; no ViewModel.
- Bootstrap request through the existing KMP runtime/network infrastructure.
- Local development API: `http://localhost:3000/api/v1/partner/config/bootstrap`.
- Transport, HTTP, API/application, serialization and unknown failure boundaries.
- Invalid/incomplete successful responses are rejected.
- Retry is supported.
- Bootstrap diagnostics use the existing logging infrastructure.

## 2. Bootstrap Is the Application Configuration Package

The product direction is that the Bootstrap/config API is not only a small startup response. It is the application's configuration package and can contain the information required to render the next application flows, including Login, OTP, Dashboard and future partner screens.

Conceptually:

```text
Bootstrap Config
├── configuration/version metadata
├── maintenance rules
├── update rules
├── feature flags
├── authentication/startup state
└── screen/configuration data
    ├── Login
    ├── OTP
    ├── Dashboard
    └── future dynamic flows
```

Therefore, when Bootstrap persistence is implemented, the intended cache boundary is the **complete validated Bootstrap/config response**, not only `nextScreen` metadata.

The exact backend JSON shape remains an external contract and must not be invented by the frontend.

## 3. Architecture Foundation Reconciliation — Accepted Implementation Direction

Before continuing into the remaining Bootstrap cache/startup behavior, the existing Bootstrap foundation will be reconciled with the application's shared Clean Architecture so that Bootstrap does not create a special networking architecture that Dynamic must later replace.

The accepted direction for implementation is:

```text
                    :core
                      │
          common network / serialization / logging
                      │
                      ▼
                    :data
                      │
              RemoteDataSource
                      │
          ┌───────────┴───────────┐
          │                       │
       Bootstrap              Future Dynamic
          │                       │
      Repository              Repository
          │                       │
          └───────────┬───────────┘
                      ▼
                   :domain
                      │
                 Use Cases
                      │
                      ▼
              Feature Stores
```

### 3.1 Common application network infrastructure

The Ktor `HttpClient`, serialization configuration, common transport behavior, target engine strategy and network dependency-injection ownership are application-wide infrastructure.

They must not be owned by Bootstrap.

### 3.2 One application-wide RemoteDataSource

The application will use one reusable remote data boundary rather than creating separate transport implementations for Bootstrap, Dynamic, Login or OTP.

Conceptually:

```text
Feature Repository
        ↓
RemoteDataSource
        ↓
Ktor HttpClient
        ↓
Backend
```

Feature-specific repositories remain responsible for feature semantics; the common RemoteDataSource remains responsible for reusable remote execution.

### 3.3 Normal Clean Architecture Bootstrap flow

Bootstrap will use the standard dependency direction:

```text
Splash Store
    ↓
GetBootstrapConfigUseCase
    ↓
BootstrapRepository
    ↓
BootstrapRepositoryImpl
    ↓
RemoteDataSource
    ↓
Ktor
```

The domain layer must not depend on Ktor, DTOs or data-layer implementations.

### 3.4 API response model direction

The common transport/data architecture must be compatible with the backend response envelope used by Bootstrap and future Dynamic responses:

```text
status
code
message
data
traceId
```

The envelope can be reusable at the API/data boundary while the contents of `data` remain feature-specific.

Future Dynamic models will not be implemented as part of Bootstrap foundation reconciliation.

### 3.5 Dynamic compatibility requirement

Bootstrap is implemented first, but shared infrastructure introduced or corrected here must be reusable by Dynamic.

Future Dynamic is expected to reuse:

```text
HttpClient
Serialization
RemoteDataSource
DI infrastructure
Common transport/error boundary
```

Dynamic-specific models, repositories/use cases, Store/runtime, registry and rendering remain future Dynamic scope.

## 4. Core Discussion — Persisting Bootstrap for Offline Startup

The latest valid Bootstrap response should be considered for persistent local storage so the application can still start from known configuration when the backend is temporarily unavailable.

The important distinction is:

```text
Cached complete Bootstrap/config
        ≠
All business operations are offline-capable
```

Offline startup/configuration rendering is one capability. Offline business operations are a separate future decision.

The persisted cache will need associated metadata such as:

```text
cached response
configVersion / revision
schemaVersion
cachedAt
ETag (if supported by backend)
app/platform compatibility metadata where required
```

The exact storage technology and metadata set are still open until the implementation plan is created.

## 5. Core Discussion — How Do We Know the Backend Changed?

The client cannot know that the backend configuration changed while it is completely offline. Some online validation mechanism is therefore required whenever freshness matters.

A strong candidate is HTTP conditional caching using an ETag, together with an explicit backend configuration version/revision.

Example:

```text
First successful response
    ↓
Server: ETag = "config-42"
    ↓
Save complete Bootstrap + ETag locally
```

On a later online startup:

```text
Local ETag = "config-42"
        ↓
GET Bootstrap
If-None-Match: "config-42"
        ↓
Backend unchanged?
    ├── YES → 304 Not Modified → keep cached response
    └── NO  → 200 + new response/ETag → validate + replace cache
```

A configuration revision/version is also useful for application-level visibility and cache metadata:

```text
configVersion 42 → configVersion 43
```

The important rule is that a version number by itself does **not** let an offline client discover a backend change. The client must communicate with the backend or receive another trusted invalidation signal.

ETag/conditional requests and the exact version/revision contract are therefore **preferred discussion directions, not yet frozen implementation requirements**, until the backend contract is confirmed.

## 6. Cache vs Backend — Intended Decision Model

The application should not use the simplistic rule:

```text
cache exists → never call backend
```

That would prevent backend configuration changes from reaching the client.

The intended model is:

```text
                 Application Start
                        ↓
                 Read local cache
                        ↓
              Network/backend available?
                 ┌──────┴──────┐
                NO            YES
                 │              │
                 ▼              ▼
          Use valid cache   Validate cache
                 │              │
                 │       ┌──────┴──────┐
                 │    unchanged      changed
                 │       │              │
                 │       ▼              ▼
                 │   Keep cache   Download new config
                 │                      ↓
                 │                  Validate
                 │                      ↓
                 │                 Replace cache
                 └──────────┬───────────┘
                            ↓
                  Startup policy evaluation
```

The exact online policy still needs to decide whether validation happens on every startup, after a freshness window, or through another approved strategy.

## 7. Cache Integrity / Replacement

Persisted Bootstrap data is configuration/control data and must be validated before use.

The implementation discussion must cover:
- malformed or corrupted cache;
- schema incompatibility;
- incomplete cached configuration;
- safe/atomic replacement of old configuration;
- cache migration when the local schema changes;
- avoiding permanent startup loops caused by invalid cache;
- handling an invalid newly downloaded response without destroying the last valid cache.

A key safety rule is:

```text
Download new config
    ↓
Validate completely
    ↓
Only then replace last-known-good cache
```

## 8. Maintenance and Required/Optional Update

Bootstrap already contains maintenance and update information. Their final precedence relative to cached data must be decided.

Questions remaining:
- Does required update always block startup?
- Does maintenance override normal cached configuration?
- Can stale cached configuration be used when the backend is unreachable?
- Which decisions require a fresh backend response?
- What happens when cached data says normal operation but the backend now requires maintenance/update?
- How are app version/build and platform considered?

These rules must become part of one startup policy rather than independent UI conditions.

## 9. Authentication and Next Screen

Bootstrap currently contains startup/authentication information and a `nextScreen` descriptor. The future startup flow must resolve authentication and the destination from the Bootstrap/config package.

The intended conceptual flow is:

```text
Bootstrap/config
    ↓
startup/authentication decision
    ↓
next screen / screen configuration
    ↓
Compose dynamic UI
```

The complete Login/OTP/Dashboard configuration can be part of the Bootstrap package and therefore can be available from the local cache for offline rendering, subject to schema/version validity.

Questions still open:
- How local session state and Bootstrap authentication state are reconciled.
- Which startup decisions require fresh backend validation.
- How cached screen configuration is invalidated when the backend changes it.
- How incompatible screen/template schema is handled.

## 10. Final Startup Precedence Still Open

The exact order is intentionally not frozen yet. The final policy must define precedence among:

```text
cache availability
network/backend availability
cache freshness
backend configuration revision/ETag
required update
maintenance
authentication/session state
nextScreen
cached screen configuration
```

Examples requiring explicit rules:
- required update vs maintenance;
- fresh backend config vs cached config;
- stale cache vs backend unavailable;
- local authenticated session vs Bootstrap unauthenticated state;
- cached configuration vs incompatible schema;
- offline startup vs authentication requirements.

## 11. Decisions Required Before Final Bootstrap Startup Freeze

The remaining startup discussion must explicitly decide and freeze:

- [ ] Complete Bootstrap response persistence boundary.
- [ ] Shared KMP persistence mechanism.
- [ ] Cache metadata and schema/version model.
- [ ] Online cache validation policy.
- [ ] Backend `configVersion`/revision contract.
- [ ] ETag/conditional-request support, if adopted.
- [ ] Cache invalidation/freshness rules.
- [ ] Offline startup policy.
- [ ] Cache integrity/recovery behavior.
- [ ] Maintenance precedence.
- [ ] Required/optional update precedence.
- [ ] Authentication/session precedence.
- [ ] `nextScreen` resolution.
- [ ] Complete screen/configuration cache behavior.
- [ ] Final startup decision precedence.

No implementation of these remaining startup decisions is authorized by this section until their discussion is frozen.

## 12. Documentation / Workflow State

Feature documentation:

```text
docs/features/splash-bootstrap/
├── 00-FEATURE-DISCUSSION.md
├── 01-IMPLEMENTATION-PLAN.md
└── 02-IMPLEMENTATION-STATUS.md
```

The current Splash + Bootstrap feature remains **PARTIALLY FROZEN**. The extended cache/offline/startup discussion remains **OPEN**.

The Bootstrap foundation architecture is being reconciled first so that the same application-wide network/data infrastructure can be reused by Dynamic.
