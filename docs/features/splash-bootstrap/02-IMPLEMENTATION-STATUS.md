# SPLASH + BOOTSTRAP — IMPLEMENTATION STATUS

**Feature:** `splash-bootstrap`  
**Tracking ID:** `SPLASH-BOOTSTRAP-001`  
**Branch:** `feature/dynamic-ui`  
**Foundation status:** PARTIALLY FROZEN — ARCHITECTURE RECONCILIATION PLANNED  
**Remaining Bootstrap status:** DISCUSSION OPEN  
**Final feature freeze:** NOT YET FROZEN

## 1. Current State

The current Splash + Bootstrap foundation is implemented and usable, but its network/data boundary is being reconciled with the application-wide Clean Architecture before Dynamic implementation begins.

Current implemented behavior:

```text
Splash
  ↓
Bootstrap API
  ↓
Validate response
  ├── success → Bootstrap result available
  └── failure → error + Retry
```

The next implementation cycle is **Bootstrap foundation architecture correction**, not Dynamic implementation.

## 2. Completed Foundation

### Bootstrap / Data

- Bootstrap response decoding and mapping implemented.
- Invalid/incomplete success handling implemented.
- Transport/HTTP/API/serialization failure boundaries implemented.
- Configured local API base URL is `http://localhost:3000`.
- Bootstrap endpoint is `/api/v1/partner/config/bootstrap`.
- Required development request headers are implemented within the current platform-boundary constraints.

### Store / MVI

- Pure Store-based Bootstrap/Splash state flow implemented.
- Loading, Success and Failure states implemented.
- Retry behavior implemented.
- No ViewModel introduced.

### Splash UI

- Shared Compose Multiplatform Splash UI implemented in `commonMain`.
- Web-compatible Compose implementation verified.
- Splash loader/success/failure states implemented.
- Current UI remains at the Bootstrap completion boundary; it does not yet perform the future startup navigation flow.

## 3. Architecture Correction To Implement

The existing Bootstrap implementation must be reconciled with the application-wide architecture before Dynamic is implemented.

Target direction:

```text
:core
  ↓
common Ktor / serialization / logging / network infrastructure
  ↓
:data
  ↓
RemoteDataSource
  ↓
BootstrapRepositoryImpl
  ↓
:domain
  ↓
BootstrapRepository + GetBootstrapConfigUseCase
  ↓
Splash Store
  ↓
Splash UI
```

The same shared network/data infrastructure must later be reusable by Dynamic.

The target application-wide transport boundary is one reusable `RemoteDataSource`, not separate Bootstrap/Dynamic transport classes.

## 4. Seven-Point Implementation Plan

```text
1. Common Network Infrastructure
2. Common RemoteDataSource
3. Bootstrap API / DTO / Mapper
4. Bootstrap Repository + Use Case
5. Splash Store Integration
6. Splash UI Integration
7. Verification / Tests / Documentation / Freeze
```

The implementation plan is recorded in:

`docs/features/splash-bootstrap/01-IMPLEMENTATION-PLAN.md`

The current active planning point is:

**Point 1 — Common Network Infrastructure**

## 5. Dynamic Compatibility

Bootstrap is being corrected first, but the shared architecture must be suitable for Dynamic.

Future Dynamic should be able to reuse:

```text
HttpClient
Serialization
RemoteDataSource
DI infrastructure
Common transport/error boundary
```

No Dynamic renderer, registry, Dynamic model hierarchy or Dynamic feature implementation is part of the current Bootstrap foundation work.

## 6. Remaining Bootstrap Scope

The following remain intentionally incomplete:

- Persist the complete Bootstrap/config response locally.
- Offline startup policy.
- Cache freshness/TTL policy.
- Cache invalidation/version/revision policy.
- Backend configuration-change detection.
- ETag/conditional-request support, if adopted.
- Cache integrity and last-known-good recovery.
- Maintenance-mode precedence.
- Required/optional update behavior and precedence.
- Authentication/session startup decision.
- `nextScreen` resolution.
- Complete Login/OTP/Dashboard dynamic startup flow.
- Rendering complete cached configuration offline.
- Screen/template/schema compatibility and invalidation.
- Final startup decision precedence.

These remain discussion/planning scope and must not be pulled into the current network/data foundation implementation without an accepted scope change.

## 7. Verification

Before the final Bootstrap feature freeze, run the agreed automated verification, including applicable KMP tests and platform/build checks.

Additional tests will be required after cache/offline/startup behavior is planned and implemented.

## 8. Documentation State

```text
docs/features/splash-bootstrap/
├── 00-FEATURE-DISCUSSION.md  → PARTIALLY FROZEN / EXTENDED DISCUSSION OPEN
├── 01-IMPLEMENTATION-PLAN.md → PROPOSED — FOUNDATION PLAN UNDER REVIEW
└── 02-IMPLEMENTATION-STATUS.md → ARCHITECTURE RECONCILIATION PLANNED
```

## 9. Next Valid Action

Discuss and freeze the implementation approach for:

**Point 1 — Common Network Infrastructure**

After the point is accepted, continue with the approved implementation sequence without starting Dynamic-specific architecture work.
