# SPLASH + BOOTSTRAP — IMPLEMENTATION STATUS

**Feature:** `splash-bootstrap`  
**Tracking ID:** `SPLASH-BOOTSTRAP-001`  
**Branch:** `feature/splash-config-bootstrap`  
**Plan:** `01-IMPLEMENTATION-PLAN.md` — PLAN_FROZEN  
**Feature freeze:** NOT YET FROZEN

## Current State

**IMPLEMENTATION IN PROGRESS**

The complete feature is being implemented continuously. There are no unit-level freezes.

## Completed in This Implementation Pass

### Domain

- Existing direct `ApplicationBootstrap` contract retained.
- Existing `BootstrapOutput`, `BootstrapConfig`, and `StartupConfig` contracts retained.
- Added `BootstrapFailure` for frontend failure classification.

### Data/API

- Added serializable Bootstrap response DTOs.
- Added DTO → Domain mapping.
- Added incomplete-success validation.
- Added HTTP/API/serialization/transport failure mapping.
- Added Bootstrap remote data source.
- Added direct `ApplicationBootstrap` Data implementation.
- Added Koin Bootstrap module.
- Added configured base URL and endpoint:
  - Base: `https://localhost:300`
  - Endpoint: `/api/v1/partner/config/bootstrap`
- Added known headers:
  - `X-CarBroz-Platform: ANDROID`
  - `X-CarBroz-App-Version: 1.0.0`
  - `X-CarBroz-Build-Number: 1`

### Splash Store

- Added pure Store-style state/intent contract.
- Added Loading, Success and Failure states.
- Added retry behavior.
- Added cancellation/close behavior.

### Splash UI

- Added loader state.
- Added Bootstrap success state.
- Added failure message and Retry action.

### Tests

Added tests for:

- Bootstrap response mapping.
- Known request headers and endpoint.
- HTTP failure.
- Invalid/incomplete success.
- Serialization failure.
- API failure mapping.
- Splash Store success lifecycle.
- Splash Store failure → retry lifecycle.

## Explicitly Deferred / Not Implemented

These remain intentionally outside the current behavior even though fields exist in the successful response:

- Maintenance-mode decision behavior.
- Required/optional update behavior.
- Authentication startup decision.
- `nextScreen` navigation.
- Login screen.
- Dynamic template rendering.
- Downstream screen API calls.
- Final backend-specific request body/method contract if the backend later changes it.
- Final backend-specific error-response schema beyond the currently available `status/code/message` fields.

## Verification

Local Gradle verification is still required after the repository implementation pass:

```text
:domain:jvmTest
:data:jvmTest
:feature:splash:jvmTest
```

Then perform the complete repository build/test verification appropriate to the available host.

## Review Gate

After implementation and tests:

1. Inspect the complete feature diff.
2. Compare every change with `00-FEATURE-DISCUSSION.md` and `01-IMPLEMENTATION-PLAN.md`.
3. Verify BASE-ARCH-008–013 and BASE-ARCH-017 boundaries.
4. Verify no ViewModel, repository/use-case chain, navigation decision tree, or invented backend contract was introduced.
5. Update this document with final results.
6. Only then mark the feature FROZEN.
