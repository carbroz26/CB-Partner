# Splash + Bootstrap — Feature Discussion

**Feature:** `splash-bootstrap`
**Tracking ID:** `SPLASH-BOOTSTRAP-001`
**Discussion Status:** FROZEN
**Implementation Status:** NOT STARTED
**Branch:** `feature/splash-config-bootstrap`

## 1. Feature Status

### DONE / DECIDED
- Splash is the initial visible screen.
- Splash shows a loader while Bootstrap is running.
- Bootstrap is called from the Splash startup flow.
- Current Bootstrap base URL is `https://localhost:300`.
- Current Bootstrap endpoint is `/api/v1/partner/config/bootstrap`.
- Known platform/app/build headers are defined below.
- The supplied successful Bootstrap response is the current known response contract.
- Frontend failure handling must cover transport, HTTP, API/application, serialization, and unknown failures.
- Invalid/incomplete successful data is treated as a bootstrap failure rather than successful startup.
- Successful Bootstrap is represented as success only; no downstream navigation is implemented now.
- Retry is available from the current failure state and retries Bootstrap.
- Maintenance, update, authentication, next-screen and dynamic-screen behavior are deferred.

### PENDING / BACKEND-DEPENDENT
- Exact Bootstrap request method/body is not currently supplied by the backend. The frontend must isolate this unknown and must not invent it.
- Exact backend error-response payload/mapping is not currently supplied. The frontend failure boundary is defined, but backend-specific error-code mapping remains open until the backend contract is supplied.

These two items are intentionally accepted as external-contract dependencies and do not block the current frontend implementation plan.

### CURRENT IMPLEMENTATION SCOPE
The complete current Splash + Bootstrap implementation is:

```text
Application start
    ↓
Splash + loader
    ↓
Bootstrap request
    ↓
Receive response
    ├── Success → represent Bootstrap success; remain in current scope
    └── Failure → represent error + Retry
```

The implementation covers the complete current feature from startup through Bootstrap result handling and tests. It is not divided into independently frozen units.

### DEFERRED / LATER
- Maintenance-mode decision/UI
- Required-update decision/UI
- Optional-update decision/UI
- Authentication decision
- `nextScreen` routing
- Dynamic template/screen rendering
- Downstream screen API calls
- Final startup/navigation architecture for those later flows

## 2. Frozen Architecture Boundaries

This feature follows the already-frozen BASE-ARCH-008–013 and BASE-ARCH-017 boundaries. Those architecture decisions are not reopened here.

Required architectural direction:
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Pure Store-based MVI/UDF
- No ViewModel
- Existing module boundaries
- Approved runtime/network infrastructure

## 3. API Contract

### Base URL

```text
https://localhost:300
```

This is configuration, not a permanent hard-coded production endpoint.

### Endpoint

```text
/api/v1/partner/config/bootstrap
```

Current complete URL:

```text
https://localhost:300/api/v1/partner/config/bootstrap
```

### Headers

```text
X-CarBroz-Platform: ANDROID
X-CarBroz-App-Version: 1.0.0
X-CarBroz-Build-Number: 1
```

### Request method/body

Unknown from the current backend information. Do not infer it from the returned `nextScreen.method`; that field belongs to the deferred downstream screen contract.

The implementation plan must isolate the request construction so the exact backend method/body can be supplied later without architectural redesign.

## 4. Successful Response Contract

The known successful response is:

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

The returned `nextScreen` is parsed/retained as response data but is not acted upon in this feature.

## 5. Error Contract Boundary

The backend has not yet supplied an exact error payload. Therefore the frontend implementation must not invent backend JSON fields or error codes.

The frontend must nevertheless model these failure categories:

- Transport/network failure
- HTTP failure
- API/application failure when an error response is received
- Serialization/malformed response failure
- Unknown/unexpected failure

When the exact backend error contract is supplied, backend-specific mapping can be added within the existing boundary.

## 6. Invalid / Incomplete Success

A response that is technically successful at the transport/API layer but lacks required Bootstrap data is not considered a valid Bootstrap success.

The frontend should convert such a response into a failure state and expose the current error/retry behavior. It must not manufacture missing values or navigate using incomplete data.

## 7. Current Splash Behavior

The current UI intentionally remains minimal:

- Show Splash.
- Show loader while Bootstrap is loading.
- On success, represent Bootstrap completion and remain within this feature's current scope.
- On failure, show an appropriate error state and Retry action.

No maintenance/update/authentication/next-screen UI is implemented now.

## 8. Successful Handoff Boundary

For this feature, the handoff boundary ends at a successfully received and validated Bootstrap result.

There is no navigation to `partner_login` or another destination in this implementation.

## 9. Existing Repository Context

The feature branch contains the previously accepted Domain Bootstrap contract work. The complete implementation plan must reconcile that existing work with the current feature contract and frozen architecture boundaries without reopening those decisions.

No separate Unit 1/Unit 2 freeze is used. Domain, Data/API, Store, UI, startup wiring and tests are implementation units inside this one feature.

## 10. Feature Documentation

All significant features/modules use a dedicated documentation directory:

```text
docs/features/splash-bootstrap/
├── 00-FEATURE-DISCUSSION.md
├── 01-IMPLEMENTATION-PLAN.md
└── 02-IMPLEMENTATION-STATUS.md
```

`00-FEATURE-DISCUSSION.md` records the complete feature contract and decisions. `01-IMPLEMENTATION-PLAN.md` records the complete implementation plan. `02-IMPLEMENTATION-STATUS.md` records implementation, testing, review and final freeze status.

## 11. Discussion Freeze

This feature discussion is now **FROZEN**.

The two backend-dependent unknowns are explicitly isolated and accepted as external dependencies; they do not reopen the architecture or block creation of the implementation plan.

The next workflow state is immediately:

**COMPLETE IMPLEMENTATION PLAN → PLAN_FROZEN → IMPLEMENT COMPLETE FEATURE**

No additional feature discussion phase or unit-level freeze is required before planning or implementation.
