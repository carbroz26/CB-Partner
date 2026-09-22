# BASE-ARCH-014 — Implementation Plan

**State:** PLAN_FROZEN  
**Decision:** BASE-ARCH-014 — Minimum Gradle/KMP/Compose/Build-Logic Foundation  
**Owner approval:** Explicitly approved 2026-09-22.  
**Implementation:** Authorized through unit-by-unit workflow.

## Objective
Establish the minimum reproducible Gradle/Kotlin Multiplatform/Compose Multiplatform foundation required to implement the already-frozen CB-Partner module architecture.

## Scope
- Gradle project skeleton/module registration
- version catalog
- included build-logic
- minimal type-oriented convention plugins
- KMP target configuration
- Compose configuration where consumed
- Android/iOS/Desktop application boundaries
- foundation-required dependency/module wiring
- minimal build/test baseline
- architecture/dependency verification
- durable documentation/status updates

Initial modules:
```
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

`build-logic` is an included Gradle build, not an architecture module.

## Out of scope
Authentication, OTP, booking, payment, dashboard, business workflows, SDUI internals, dynamic JSON, template/component/registry/renderer/action systems, real bootstrap/API implementation, backend DTOs/contracts, fake bootstrap, fake JSON, fake backend, ApplicationBootstrap implementation, Splash Store, Navigation implementation, Koin application graph, ViewModel, `:store`, `:mvi`, `:presentation-core`, `:di`, `:common`, `:shared`, unrelated refactoring/upgrades.

The later startup vertical slice will use the actual backend API and exact response supplied by the project owner. No fake bootstrap will be created.

## KMP target matrix
Initial shared targets:
- Android
- `iosArm64`
- `iosSimulatorArm64`
- JVM

Apply where required to `:core`, `:domain`, `:data`, `:navigation`, `:feature:splash`, `:feature:dynamic`. Use the standard/default KMP source-set hierarchy. No speculative targets or custom source sets without a new approved decision.

## Build logic
Included `build-logic` with exactly these initial conceptual conventions:
1. KMP shared-library
2. Compose Multiplatform
3. Android application
4. Desktop application

No library-specific convention plugins. Build logic centralizes repeated build mechanics, not application architecture.

## Version catalog
Use `gradle/libs.versions.toml`. The catalog owns coordinates/plugin declarations/versions; convention plugins own repeated build configuration. Exact compatible Kotlin/Gradle/AGP/Compose versions and plugin IDs are selected during implementation preparation.

## Dependency policy
Wire only dependencies genuinely required by the foundation. Frozen runtime libraries are introduced when their authorized implementation unit has a real consumer, including Ktor, kotlinx.serialization, kotlinx.coroutines, Kermit, Koin, Navigation 3, MVIKotlin, kotlinx.collections.immutable, and kotlinx-datetime. `kotlin.Result` needs no external dependency. Do not add libraries merely to make the project appear complete.

## Compose boundary
Compose is authorized for `:feature:splash` and `:feature:dynamic`. `:navigation` may use Compose only if its actual implementation requires it. `:core`, `:domain`, and `:data` remain Compose-free.

## Platform boundaries
`:androidApp` and `:desktopApp` remain thin entry points. `:iosApp` is the iOS/Xcode boundary; no artificial symmetric iOS convention is required. Platform apps contain no duplicated business/application architecture.

## Dependency direction
Preserve:
```
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
Forbidden dependencies from BASE-ARCH-002 remain forbidden.

## Ordered implementation units

### 014-01 — Gradle Project Skeleton
Root Gradle configuration, settings, wrapper/configuration as required, module registration, `gradle/`, included build-logic boundary. Verify project/settings load and modules are recognized.

### 014-02 — Version Catalog
Establish central version/dependency/plugin catalog. Verify aliases and versions resolve.

### 014-03 — Build Logic + Four Conventions
Implement included build-logic and approved convention types. Verify conventions resolve and apply to intended project types.

### 014-04 — KMP Target Configuration
Apply the approved non-platform-specific KMP target configuration and standard source-set hierarchy. The Android target is part of the approved matrix, but its required `compileSdk` configuration is intentionally deferred to 014-06. 014-04 must not configure `compileSdk` or other Android platform application details. Verify the shared KMP configuration that can be validated without crossing the 014-06 boundary.

### 014-05 — Compose Configuration
Apply Compose only to actual Compose modules. Verify Compose modules configure/compile and Core/Domain/Data remain Compose-free.

### 014-06 — Platform Application Boundaries
Configure Android, Desktop and iOS boundaries, including the minimum Android KMP `compileSdk` configuration required by the approved Android target. This `compileSdk` configuration is the only Android configuration moved from the 014-04 staging boundary; no other 014-04/014-06 scope is changed. Verify platform builds/integration where environment permits; report iOS limitations honestly.

### 014-07 — Dependency and Module Wiring
Wire only foundation-required dependencies and preserve the frozen graph. Verify dependency boundaries.

### 014-08 — Build/Test Baseline
Establish minimal shared testing/build verification. Record actual commands/results.

### 014-09 — Architecture Verification
Verify module structure, dependency direction, Compose boundary, absence of ViewModel/prohibited modules, and scope compliance.

## Unit workflow
```
SELECT → IMPLEMENT → VERIFY → REPORT → USER REVIEW → NEXT UNIT
```
Only one implementation unit is active at a time.

If a conflict with a frozen decision appears:
```
STOP → REPORT → DISCUSS → DECIDE → FREEZE → UPDATE PLAN → IMPLEMENT
```

## Verification requirements
Gradle configuration loads; build-logic loads; catalog resolves; conventions resolve; approved modules are recognized; shared modules compile for configured targets; platform verification is performed where supported; minimal testing works; dependency direction is verified; prohibited structures and scope expansion are absent; no fake bootstrap/JSON/backend is introduced.

## Acceptance criteria
Approved module structure exists; build-logic works; four conventions work; catalog works; target matrix works; default source-set hierarchy is preserved; Compose boundaries are correct; Android/Desktop/iOS boundaries are configured; dependency direction is preserved; Core/Domain/Data remain Compose-free; no ViewModel/prohibited modules/business/SDUI/fake bootstrap/JSON; required verification is run and recorded; durable records are synchronized.

## Prohibited changes
No silent architecture changes, speculative targets, library-specific conventions, business modules, application behavior, real backend implementation, fake backend/API, fake bootstrap/JSON, authentication/booking/payment/dashboard/SDUI, ViewModel, Store/MVI architecture modules, unrelated refactoring, or unauthorized dependency upgrades.

## Git gate
Use `main ← development ← feature/<module>`. Never implement directly on main/development. Before coding, inspect Git state, establish the authorized branch, and record it in Tracker. Commit/PR/merge/sync remain distinct states.

## Documentation
Maintain this plan, `BASE-ARCH-014-IMPLEMENTATION-STATUS.md`, Tracker, Current Status, Decision Log, and relevant architecture records. Trello must be synchronized when the required execution card exists; no card was found during freeze preparation, so no card ID is invented.

## Real-backend boundary
After BASE-ARCH-014 is implemented, verified, accepted and frozen, the next separately authorized startup vertical slice uses the actual backend endpoint and exact response supplied by the project owner. No fake bootstrap is used.

## Plan freeze
**PLAN_FROZEN.** Explicit owner approval received 2026-09-22. On 2026-09-22, the project owner explicitly resolved the 014-04/014-06 staging conflict by keeping the boundary and moving only the required Android `compileSdk` configuration into 014-06. No other BASE-ARCH-014 decision is changed. Implementation remains authorized through the unit-by-unit gates.