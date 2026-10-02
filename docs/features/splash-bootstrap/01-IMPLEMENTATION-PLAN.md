# Splash + Bootstrap — Complete Implementation Plan

**Feature:** `splash-bootstrap`
**Tracking ID:** `SPLASH-BOOTSTRAP-001`
**Plan Status:** PLAN DRAFT — READY FOR REVIEW
**Discussion:** FROZEN
**Branch:** `feature/splash-config-bootstrap`

## 1. Goal

Implement the complete currently scoped Splash + Bootstrap feature in one continuous implementation cycle:

```text
Application start
    ↓
Splash + loader
    ↓
Bootstrap API
    ↓
Decode + validate response
    ├── Success → Bootstrap success state; remain in current scope
    └── Failure → Error state + Retry
```

No downstream startup/navigation behavior is implemented in this feature.

## 2. Frozen Boundaries

Follow BASE-ARCH-008–013 and BASE-ARCH-017 exactly. Do not introduce ViewModel, alternate state-management architecture, new networking infrastructure, or architecture changes.

Use the existing modules and approved runtime infrastructure.

Implementation units are sequencing units only:
1. Domain contract reconciliation
2. Data/API implementation
3. Bootstrap Store/startup orchestration
4. Splash UI
5. Application startup wiring
6. Complete tests and verification

There is no unit-level authorization or freeze between these units.

## 3. API Inputs

### Base URL

```text
https://localhost:300
```

Must remain configurable.

### Endpoint

```text
/api/v1/partner/config/bootstrap
```

### Headers

```text
X-CarBroz-Platform: ANDROID
X-CarBroz-App-Version: 1.0.0
X-CarBroz-Build-Number: 1
```

### Unknowns intentionally isolated

- Bootstrap HTTP method/body are backend-dependent and must not be invented.
- Exact backend error JSON/error-code mapping is backend-dependent and must not be invented.

The implementation should isolate these at the API/data boundary so later contract updates are localized.

## 4. Domain

### Existing/reconciled contract

Use the existing accepted Domain Bootstrap contract on the feature branch where compatible with the frozen feature discussion:

- `BootstrapConfig`
- `MaintenanceConfig`
- `UpdateConfig`
- `FeatureConfig`
- `StartupConfig`
- `NextScreenConfig`
- Bootstrap success/output model
- Bootstrap failure model
- `ApplicationBootstrap` boundary where already established

Do not add future business behavior to these contracts.

### Repository/use-case boundary

Use the existing domain repository/use-case boundary where compatible with the frozen architecture. Domain must remain independent of Ktor and DTO/network details.

### Tests

Retain and/or correct the existing Domain tests so they compile against the final contract and verify use-case delegation and result behavior.

## 5. Data/API

Implement the Bootstrap API adapter using the existing Ktor/runtime infrastructure from BASE-ARCH-017.

Expected responsibilities:

- Build the Bootstrap request.
- Apply the configured base URL and endpoint.
- Add the known platform/app/build headers.
- Decode the known successful response into data DTOs.
- Map DTOs to Domain models.
- Map transport/HTTP/serialization/API failures into the Domain failure boundary.
- Reject incomplete successful data when required fields are absent.

Do not put business startup decisions in the Data layer.

### Expected file areas

Inspect existing files first and modify only the relevant files. Expected areas include:

```text
data/src/commonMain/kotlin/.../bootstrap/
data/src/commonTest/kotlin/.../bootstrap/
```

The exact class names/files must follow the repository's existing conventions and the already established Data/API structure rather than creating duplicate abstractions.

## 6. Store / MVI

Implement the Splash Bootstrap Store using the existing pure Store/MVI/UDF architecture.

The Store must expose states equivalent to:

```text
Initial
Loading
Success(bootstrap)
Failure(error)
```

and an intent/action equivalent to:

```text
LoadBootstrap
RetryBootstrap
```

The exact repository Store interfaces and naming must follow existing project conventions.

Responsibilities:
- Trigger Bootstrap exactly from the startup/Splash flow.
- Prevent UI from directly owning API calls.
- Convert domain result into deterministic UI state.
- Support retry from failure.
- Preserve the successful Bootstrap result for the current feature boundary.

Do not implement navigation or interpret maintenance/update/authentication/next-screen decisions.

## 7. Splash UI

Implement the current minimal Splash UI:

### Loading
- Splash content.
- Loader/progress indication.

### Success
- Remain on the current Splash/bootstrap completion boundary.
- No destination screen navigation.

### Failure
- User-visible error state.
- Retry action.

The UI must consume Store state and dispatch Store intents. It must not call the repository/API directly.

## 8. Startup Wiring

Connect application startup to the Splash Bootstrap flow using the existing application composition/navigation boundaries.

The startup sequence must be:

```text
Application start
→ Splash composition
→ Bootstrap Store initialization
→ LoadBootstrap
→ API call
→ Store state
→ UI
```

Do not add the later startup decision tree.

## 9. Validation Rules

A Bootstrap response is valid only when the required structure needed by the current feature is present.

If required data is missing or malformed:

```text
API response
→ validation failure
→ Bootstrap failure
→ Splash error state
→ Retry
```

Do not substitute arbitrary defaults for missing required backend data.

## 10. Error Handling

The frontend failure model must distinguish, as appropriate:

- Transport
- HTTP
- API/application
- Serialization
- Unknown

Because the exact backend error payload is unknown, the API implementation must not depend on undocumented error fields. When the backend supplies the final contract, only the backend-specific mapper should need adjustment.

The UI should present a safe user-facing error message and Retry action without exposing raw technical failure details unnecessarily.

## 11. Tests

Complete feature testing must cover the entire current scope.

### Domain tests
- Bootstrap use-case delegation.
- Successful Bootstrap result.
- Failure propagation where applicable.

### Data/API tests
Using the approved Ktor test infrastructure:
- Successful response decoding.
- Request URL/path construction.
- Known headers are sent.
- HTTP failure mapping.
- Transport failure mapping.
- Serialization failure mapping.
- API/application failure boundary without assuming undocumented backend payload fields.
- Invalid/incomplete success rejection.

### Store tests
- Initial → Loading.
- Loading → Success.
- Loading → Failure.
- Failure → Retry → Loading.
- Success retains Bootstrap result.
- No navigation decision is triggered by the Store.

### UI/startup tests
Where supported by the existing project test conventions:
- Splash renders loading state.
- Error state exposes Retry.
- Successful Bootstrap does not navigate to `nextScreen`.
- Startup invokes Bootstrap once for the normal load path.

## 12. Files / Classes to Inspect Before Editing

Before implementation, inspect the current branch and existing architecture for exact locations/names. The expected areas are:

```text
AI_START_HERE.md
docs/features/splash-bootstrap/00-FEATURE-DISCUSSION.md
domain/src/commonMain/.../bootstrap/
data/src/commonMain/.../bootstrap/
data/src/commonTest/.../bootstrap/
feature/splash/src/commonMain/...
feature/splash/src/commonTest/...
navigation/src/commonMain/...
androidApp/src/...
desktopApp/src/...
```

Do not create duplicate Store, repository, API client, DI, or navigation abstractions when an existing approved abstraction already serves the role.

## 13. Implementation Order

Implement continuously in this order:

1. Inspect and reconcile existing Domain Bootstrap code.
2. Complete Data/API request, DTO mapping, validation and failure mapping.
3. Complete Bootstrap Store/state/intents.
4. Complete Splash UI states and Retry.
5. Wire application startup.
6. Complete all relevant tests.
7. Run the complete verification suite.
8. Double-check the final diff against the frozen feature discussion and architecture decisions.
9. Update implementation status and freeze the feature only after successful review.

No source implementation begins until this plan is explicitly PLAN_FROZEN.

## 14. Explicit Non-Goals

Do not implement:

- Maintenance mode behavior
- Update prompts or store redirection
- Authentication flow
- Login screen
- `nextScreen` navigation
- Dynamic UI/template rendering
- Downstream screen API calls
- New architecture modules unrelated to Splash/Bootstrap
- Backend changes
- Invented backend request/error contracts

## 15. Completion Criteria

The feature is complete when:

- Splash appears at application startup.
- Loader is shown during Bootstrap.
- Bootstrap request uses the configured base URL and endpoint.
- Required known headers are sent.
- Successful response is decoded and represented in Domain/Store state.
- Invalid/incomplete success is rejected safely.
- Known failure categories are handled safely.
- Retry works.
- No future startup/navigation behavior is accidentally implemented.
- Complete relevant tests pass.
- Final diff contains only approved feature/documentation changes.
- Review confirms conformance to BASE-ARCH-008–013 and BASE-ARCH-017.
- `02-IMPLEMENTATION-STATUS.md` records final implementation/test/review status.

## 16. Next Workflow State

This document is currently **PLAN DRAFT — READY FOR REVIEW**.

After user acceptance, mark it **PLAN_FROZEN** and immediately begin the complete feature implementation. Do not introduce another unit-level planning or authorization gate.
