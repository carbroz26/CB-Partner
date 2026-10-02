# Splash + Bootstrap — Feature Discussion

**Feature:** `splash-bootstrap`  
**Tracking ID:** `SPLASH-BOOTSTRAP-001`  
**Discussion Status:** PARTIALLY FROZEN — EXTENDED DISCUSSION OPEN  
**Current Implementation Status:** FOUNDATION IMPLEMENTED  
**Branch:** `feature/splash-config-bootstrap`

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

## 3. Core Discussion — Persisting Bootstrap for Offline Startup

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

## 4. Core Discussion — How Do We Know the Backend Changed?

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

## 5. Cache vs Backend — Intended Decision Model

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

## 6. Cache Integrity / Replacement

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

## 7. Maintenance and Required/Optional Update

Bootstrap already contains maintenance and update information. Their final precedence relative to cached data must be decided.

Questions remaining:
- Does required update always block startup?
- Does maintenance override normal cached configuration?
- Can stale cached configuration be used when the backend is unreachable?
- Which decisions require a fresh backend response?
- What happens when cached data says normal operation but the backend now requires maintenance/update?
- How are app version/build and platform considered?

These rules must become part of one startup policy rather than independent UI conditions.

## 8. Authentication and Next Screen

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

## 9. Final Startup Precedence Still Open

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

## 10. Decisions Required Before the Next Implementation Plan

Before planning the remaining Bootstrap/startup work, explicitly decide and freeze:

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

No implementation plan for these remaining decisions is frozen yet.

## 11. Documentation / Workflow State

Feature documentation:

```text
docs/features/splash-bootstrap/
├── 00-FEATURE-DISCUSSION.md
├── 01-IMPLEMENTATION-PLAN.md
└── 02-IMPLEMENTATION-STATUS.md
```

The current Splash + Bootstrap foundation remains **PARTIALLY FROZEN**. The extended cache/offline/startup discussion remains **OPEN**.

The next workflow for the remaining Bootstrap scope is:

**DISCUSS → RESEARCH where required → DECIDE → FREEZE DISCUSSION → IMPLEMENTATION PLAN → PLAN FREEZE → IMPLEMENT → TEST → FINAL FREEZE**
