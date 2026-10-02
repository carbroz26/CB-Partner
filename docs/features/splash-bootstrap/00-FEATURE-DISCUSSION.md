# Splash + Bootstrap — Feature Discussion

**Feature:** `splash-bootstrap`
**Status:** DISCUSSION — IN PROGRESS
**Tracking ID:** `SPLASH-BOOTSTRAP-001`
**Branch:** `feature/splash-config-bootstrap`

## 1. Purpose

Define the complete in-scope Splash + Bootstrap feature contract before creating the complete implementation plan.

The current implementation scope is intentionally limited to showing Splash, showing a loader, calling the Bootstrap API, receiving and handling the response on the frontend, and completing the current bootstrap result handling without implementing the later startup/next-screen flow.

## 2. Frozen Architecture Boundaries

This feature must follow the already-frozen architecture decisions and runtime infrastructure, including BASE-ARCH-008–013 and BASE-ARCH-017.

Do not reopen those architecture decisions as part of this feature discussion.

The feature follows the repository's frozen direction:
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Store-based pure MVI/UDF
- No ViewModel
- Existing module boundaries
- Approved runtime/network infrastructure

## 3. Current In-Scope Behavior

For this implementation:

```text
Application start
    ↓
Splash screen
    ↓
Loader
    ↓
Call Bootstrap API
    ↓
Receive response
    ↓
Handle bootstrap result on frontend
    ↓
Remain within the current Splash/bootstrap scope
```

The current feature does **not** implement the later destination-screen/startup flow.

## 4. Bootstrap API Contract — Current Known Information

### Base URL

For the current implementation, use the configurable base URL:

```text
https://localhost:300
```

This is intentionally a base URL so it can be changed later without redesigning the feature.

### Endpoint

```text
/api/v1/partner/config/bootstrap
```

Current complete URL:

```text
https://localhost:300/api/v1/partner/config/bootstrap
```

### Known request headers

```text
X-CarBroz-Platform: ANDROID
X-CarBroz-App-Version: 1.0.0
X-CarBroz-Build-Number: 1
```

### Request method/body

The exact Bootstrap request method/body contract is not currently supplied by the backend.

Do not infer the method/body from the `nextScreen.method` field in the successful response. The downstream screen contract is outside the current scope.

The implementation must isolate this unknown rather than inventing a backend contract.

## 5. Successful Response

The currently supplied successful response is the known Bootstrap response shape:

```json
{
  "status": 200,
  "code": "SUCCESS",
  "message": "Partner bootstrap completed",
  "data": {
    "config": {
      "version": "1",
      "maintenance": {
        "enabled": false,
        "title": null,
        "message": null
      },
      "update": {
        "required": false,
        "optional": false,
        "minimumVersion": "1.0.0",
        "latestVersion": "1.0.0",
        "storeUrl": null
      },
      "features": {
        "registrationEnabled": true,
        "individualPartnerEnabled": true,
        "organizationPartnerEnabled": true
      }
    },
    "startup": {
      "authenticated": false,
      "nextScreen": {
        "screenId": "partner_login",
        "templateId": "tpl_7K2M9Q",
        "templateType": "form_template",
        "endpoint": "/api/v1/partner/screen/auth_login",
        "method": "GET",
        "authentication": "NONE"
      }
    }
  },
  "traceId": "req-1"
}
```

This response is sufficient for the current bootstrap data contract. Its future maintenance/update/authentication/next-screen behavior is explicitly deferred.

## 6. Error Handling Decision

The exact backend error response payload has not yet been supplied.

The frontend must still provide a complete error-handling boundary for the current feature. It should be able to represent and present appropriate frontend behavior for failures such as:

- transport/network failure
- HTTP failure
- API/application failure when an error code is received
- serialization/malformed-response failure
- unexpected/unknown failure

The implementation must **not invent or hard-code an undocumented backend error-response schema**.

When the exact backend error contract is later provided, it will be reconciled against this feature contract.

For the current feature, failure should remain within the Splash/bootstrap state and expose an appropriate frontend error/retry behavior rather than allowing an invalid result to propagate as a successful bootstrap.

## 7. Success / Startup Decision Scope

For this feature implementation, do not implement the full startup decision tree.

The following are explicitly deferred:
- maintenance-mode flow
- required-update flow
- optional-update flow
- authentication decision
- next-screen routing
- dynamic screen rendering
- downstream screen API calls

The current feature only needs to successfully receive the Bootstrap response and handle the result within the agreed current Splash scope.

## 8. Invalid / Incomplete Successful Response

If an HTTP/API response is received but required Bootstrap data is missing, null, malformed, or otherwise cannot be safely interpreted, the frontend should treat it as a Bootstrap failure rather than propagate invalid data.

For the current scope, the user-facing behavior remains the simple Splash error/retry state. Detailed business handling of individual malformed fields is deferred unless required by the implementation plan.

This establishes a safe boundary without inventing a backend error contract.

## 9. Current Splash UI Behavior

The current UI requirement is intentionally minimal:

- Show Splash.
- Show loader while Bootstrap is in progress.
- Do not add maintenance/update/authentication/next-screen UI yet.
- If Bootstrap fails, expose an appropriate error/retry state according to the frontend failure model.

Later UI and startup behavior will be discussed separately when that scope is intentionally opened.

## 10. Successful Bootstrap Handoff

For now, successful Bootstrap does not navigate to or implement the returned `nextScreen`.

The downstream startup/navigation behavior is deferred to a later feature decision.

## 11. Existing Implementation Context

The branch already contains previously accepted Domain-level Bootstrap contract work. That existing work is part of the feature context and must be reconciled against this complete feature discussion and the eventual complete implementation plan.

No source-code changes are authorized by this discussion record.

## 12. Documentation Lifecycle Decision

Every significant feature/module must have a dedicated directory under `docs/features/`.

For this feature:

```text
docs/features/splash-bootstrap/
├── 00-FEATURE-DISCUSSION.md
├── 01-IMPLEMENTATION-PLAN.md
└── 02-IMPLEMENTATION-STATUS.md
```

`00-FEATURE-DISCUSSION.md` is the durable record of the complete feature discussion. After this discussion is frozen, `01-IMPLEMENTATION-PLAN.md` becomes the durable complete implementation contract. `02-IMPLEMENTATION-STATUS.md` is maintained during implementation, testing and review.

## 13. Open Decisions

The following remain open before the complete feature discussion can be frozen:

1. **Bootstrap request method/body** — exact backend method/body is still unknown.
2. **Exact backend error payload** — exact error response schema is still unknown; frontend handling boundary is defined, backend mapping remains open.
3. **Precise current success terminal state** — the current requirement is to show/handle successful bootstrap without implementing next-screen flow; the exact terminal Store/UI state should be finalized in the implementation plan.
4. **Exact retry presentation** — frontend failure/retry behavior is required, but the exact current UI treatment can be finalized in the complete implementation plan without introducing future startup behavior.

The following are **not open for this feature's current scope** and are explicitly deferred: maintenance/update decision flow, authentication routing, next-screen navigation, dynamic rendering, and downstream screen API behavior.

## 14. Discussion Freeze Criteria

The feature discussion can be frozen when the remaining current-scope decisions above are explicitly accepted or intentionally recorded as backend-dependent unknowns with safe frontend boundaries.

After discussion freeze, the workflow must immediately move to:

**Complete Splash + Bootstrap Implementation Plan**

No separate Unit 1/Unit 2 discussion or freeze is permitted.
