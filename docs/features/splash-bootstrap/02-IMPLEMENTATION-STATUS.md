# SPLASH + BOOTSTRAP — IMPLEMENTATION STATUS

**Feature:** `splash-bootstrap`  
**Tracking ID:** `SPLASH-BOOTSTRAP-001`  
**Branch:** `feature/splash-config-bootstrap`  
**Foundation status:** PARTIALLY FROZEN  
**Remaining Bootstrap status:** DISCUSSION OPEN  
**Final feature freeze:** NOT YET FROZEN

## 1. Current State

The current Splash + Bootstrap foundation is implemented and usable.

```text
Splash
  ↓
Bootstrap API
  ↓
Validate response
  ├── success → Bootstrap result available
  └── failure → error + Retry
```

This foundation is partially frozen. The complete Bootstrap/startup feature remains open because persistence, offline behavior, freshness/invalidation, maintenance, update, authentication and next-screen decisions are not yet implemented.

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

### Verification

The Web development application has been successfully built and served during the current implementation work. Bootstrap transport/configuration behavior has been exercised against the local backend, and the current Splash UI is visible.

## 3. Remaining Bootstrap Scope

The following are intentionally not complete:

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

These items are discussion/planning scope, not implementation defects in the current foundation.

## 4. Current Discussion Direction

The complete Bootstrap response is intended to be the persisted configuration package, including configuration needed for Login, OTP, Dashboard and future startup flows.

The cache must not be treated as permanently authoritative merely because it exists. When online, the application needs a backend validation mechanism to determine whether the cached configuration is still current.

The current preferred candidate is:

```text
Cached complete Bootstrap
        +
ETag / config revision
        ↓
Conditional backend validation
        ↓
304 / unchanged → keep cache
200 / changed   → validate new response → replace cache
```

This remains a discussion direction until the backend contract and final policy are frozen.

## 5. Tests / Verification Still Required

Before final Bootstrap freeze, run the agreed automated verification, including:

```text
:domain:jvmTest
:data:jvmTest
:feature:splash:jvmTest
```

and the applicable Web/Android build and runtime checks.

Additional tests will be required once cache/offline/startup behavior is planned and implemented.

## 6. Documentation Gate

The three feature documents are aligned to the current workflow:

```text
docs/features/splash-bootstrap/
├── 00-FEATURE-DISCUSSION.md  → PARTIALLY FROZEN / EXTENDED DISCUSSION OPEN
├── 01-IMPLEMENTATION-PLAN.md → NOT READY — EXTENDED DISCUSSION OPEN
└── 02-IMPLEMENTATION-STATUS.md → FOUNDATION PARTIALLY FROZEN
```

The current foundation can now be treated as a stable checkpoint while the remaining Bootstrap/startup design is discussed.

## 7. Next Workflow

Do not start another Bootstrap implementation cycle yet.

Continue with:

```text
DISCUSS remaining Bootstrap behavior
        ↓
RESEARCH where required
        ↓
DECIDE
        ↓
FREEZE remaining discussion
        ↓
CREATE / FREEZE implementation plan
        ↓
IMPLEMENT
        ↓
TEST
        ↓
FINAL BOOTSTRAP FREEZE
```
