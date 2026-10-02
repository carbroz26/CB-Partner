# Splash + Bootstrap — Implementation Plan

**Feature:** `splash-bootstrap`  
**Tracking ID:** `SPLASH-BOOTSTRAP-001`  
**Plan Status:** NOT READY — EXTENDED DISCUSSION OPEN  
**Discussion:** PARTIALLY FROZEN — EXTENDED DISCUSSION OPEN  
**Branch:** `feature/splash-config-bootstrap`

## 1. Current Implemented Foundation

The already implemented scope is:

```text
Application start
    ↓
Shared Compose Splash UI
    ↓
Bootstrap request
    ↓
Decode + validate
    ├── Success → Bootstrap result available
    └── Failure → Error + Retry
```

This foundation is partially frozen and is not being re-planned here.

## 2. Remaining Scope — Not Yet Planned

The remaining Bootstrap/startup work must be planned only after the extended discussion is frozen.

Expected scope:

1. Persist the complete validated Bootstrap/config response.
2. Define shared KMP cache storage and metadata.
3. Define cache freshness/invalidation rules.
4. Define online validation of cached configuration.
5. Define backend configuration revision/version and possible ETag support.
6. Define offline startup behavior.
7. Define cache integrity and last-known-good recovery.
8. Define maintenance and required/optional update precedence.
9. Define authentication/session precedence.
10. Resolve `nextScreen` and the dynamic startup flow.
11. Define how complete Login/OTP/Dashboard configuration contained in Bootstrap is rendered from cache.
12. Define schema/template compatibility and cache invalidation.
13. Define final startup decision precedence.

## 3. Architectural Constraints

The remaining implementation must continue to use the already frozen architecture:

- Kotlin Multiplatform.
- Compose Multiplatform shared UI.
- Clean Architecture.
- Pure Store-based MVI/UDF.
- No ViewModel.
- Existing module boundaries.
- Existing approved runtime/network infrastructure.
- Shared/common code must remain compatible with Android, iOS and Web.
- Persistence/cache abstractions must not become platform-specific business logic.

No new architecture is introduced by this discussion.

## 4. Configuration Cache Direction

The current discussion direction is to cache the **complete validated Bootstrap/config response**, including configuration needed for Login, OTP, Dashboard and future startup flows.

The client should not use:

```text
cache exists → never call backend
```

Instead, when online, the client must have a defined mechanism to validate whether the cached configuration is still current.

A preferred candidate is:

```text
cached ETag/revision
        ↓
conditional Bootstrap request
        ↓
304 → keep cache
200  → validate new config → replace cache
```

`configVersion`/revision and ETag support are not frozen until the backend contract is confirmed.

## 5. Offline Direction

When the backend is unavailable, a valid last-known-good cached configuration may be used according to the final offline policy.

The implementation must distinguish:

```text
offline startup/configuration rendering
        ≠
offline business operations
```

No assumption should be made that the entire business application becomes offline-capable merely because Bootstrap is cached.

## 6. Non-Goals Until Discussion Freeze

Do not implement the remaining cache/startup behavior yet, including:

- Persistence implementation.
- Cache TTL/freshness logic.
- ETag/revision logic.
- Offline fallback rules.
- Maintenance/update precedence.
- Authentication precedence.
- `nextScreen` navigation.
- Dynamic screen/template rendering.
- Login/OTP/Dashboard startup flow.
- Screen/config cache invalidation.

## 7. Planning Gate

The next implementation plan must be created only after `00-FEATURE-DISCUSSION.md` is explicitly frozen for the remaining scope.

Required workflow:

```text
DISCUSS
   ↓
RESEARCH where required
   ↓
DECIDE
   ↓
FREEZE DISCUSSION
   ↓
UPDATE THIS IMPLEMENTATION PLAN
   ↓
PLAN FREEZE
   ↓
IMPLEMENT
   ↓
TEST
   ↓
FINAL FREEZE
```

Until then, this document is intentionally **NOT READY** and must not be treated as implementation authorization.
