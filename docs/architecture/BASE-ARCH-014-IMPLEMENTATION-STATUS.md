# BASE-ARCH-014 — Implementation Status

**State:** VERIFIED — 014-08 complete; 014-09 architecture verification in progress  
**Plan:** PLAN_FROZEN  
**Owner approval:** 2026-09-22  
**Scope:** Minimum Gradle/KMP/Compose/Build-Logic Foundation

## Current state
014-01 — Gradle Project Skeleton is **VERIFIED and accepted**.

014-02 — Version Catalog is **VERIFIED and accepted**.

014-03 — Build Logic + Four Conventions is **VERIFIED and accepted**.

014-04 — KMP Target Configuration is **VERIFIED and accepted**.

014-06 — Platform Application Boundaries is **VERIFIED and accepted**. The owner explicitly approved Android `compileSdk = 36` for this unit.

014-05 — Compose Configuration is **VERIFIED and accepted**.

014-07 — Dependency and Module Wiring is **VERIFIED and accepted**.

014-08 — Build/Test Baseline is **VERIFIED**. The authorized baseline commands completed successfully on 2026-09-23.

## Implementation units
| Unit | Description | State |
|---|---|---|
| 014-01 | Gradle Project Skeleton | ACCEPTED |
| 014-02 | Version Catalog | ACCEPTED |
| 014-03 | Build Logic + Four Conventions | ACCEPTED |
| 014-04 | KMP Target Configuration | ACCEPTED |
| 014-05 | Compose Configuration | ACCEPTED |
| 014-06 | Platform Application Boundaries | ACCEPTED |
| 014-07 | Dependency and Module Wiring | ACCEPTED |
| 014-08 | Build/Test Baseline | VERIFIED |
| 014-09 | Architecture Verification | IN PROGRESS |

## 014-08 Verification Record — 2026-09-23

**State:** VERIFIED

Authorized baseline verification was executed on the project owner's Windows environment.

### Commands and results

1. Project/module structure:
`gradle projects`
- **BUILD SUCCESSFUL**
- 5 actionable tasks: 1 executed, 4 up-to-date
- Confirmed approved modules:
  - `:androidApp`
  - `:core`
  - `:data`
  - `:desktopApp`
  - `:domain`
  - `:feature:splash`
  - `:feature:dynamic`
  - `:navigation`
- Confirmed included build: `:build-logic`

2. Build logic:
`gradle -p build-logic build`
- **BUILD SUCCESSFUL**
- 6 actionable tasks: 6 up-to-date

3. Shared JVM tests:
`gradle :core:jvmTest :domain:jvmTest :data:jvmTest :navigation:jvmTest :feature:splash:jvmTest :feature:dynamic:jvmTest`
- **BUILD SUCCESSFUL**
- 28 actionable tasks: 10 executed, 18 up-to-date

4. Shared JVM compilation:
`gradle :core:compileKotlinJvm :domain:compileKotlinJvm :data:compileKotlinJvm :navigation:compileKotlinJvm :feature:splash:compileKotlinJvm :feature:dynamic:compileKotlinJvm`
- **BUILD SUCCESSFUL**
- 15 actionable tasks: 10 executed, 5 up-to-date

### Host limitation

Gradle reports that `iosSimulatorArm64Test` is disabled because the current host is Windows and iOS simulator tests require macOS. This is an expected host limitation and is not treated as a verification failure. No suppression property was added.

### Scope result

014-08 introduced no code changes. The existing foundation passed the authorized build/test baseline. No runtime architecture, business logic, backend/bootstrap, SDUI, ViewModel, prohibited module, or unrelated change was introduced.

## 014-06 implementation record

Authorized scope:
- Android platform boundary
- Desktop platform boundary
- iOS platform boundary
- required Android KMP `compileSdk`
- thin platform application boundaries
- no business/application behavior

Implemented:
- `build-logic/src/main/kotlin/com/carbroz/cbpartner/buildlogic/KmpConventionPlugin.kt`
  - applies `com.android.kotlin.multiplatform.library`
  - configures the Android KMP target
  - sets `compileSdk = 36`
  - preserves `iosArm64`, `iosSimulatorArm64`, and `jvm`
- `build-logic/src/main/kotlin/com/carbroz/cbpartner/buildlogic/AndroidApplicationConventionPlugin.kt`
  - configures the Android application convention
  - sets `compileSdk = 36`

No minSdk, namespace, build variants, runtime dependencies, application behavior, Compose configuration, DI, Store, Navigation implementation, backend, or business architecture was added.

## Verification state

Remote source inspection and local verification confirm the intended 014-04, 014-05, and 014-06 changes are present.

014-04, 014-05, and 014-06 are VERIFIED and accepted.

## 014-03 implementation and acceptance

Implemented and accepted:
- `cbpartner.kmp`
- `cbpartner.compose`
- `cbpartner.android.application`
- `cbpartner.desktop.application`

The conventions remain minimal and type-oriented.

## Explicitly excluded
- authentication, booking, payment, dashboard
- SDUI internals/dynamic JSON
- real backend bootstrap implementation
- fake bootstrap/JSON/backend
- ApplicationBootstrap/Store/Navigation/Koin implementation
- ViewModel or speculative architecture modules
- runtime dependency wiring before its authorized consumer unit
- unrelated refactoring

## Git
Branch: `feature/base-arch-014`

014-06 implementation commits:
- `ca651a7e62d949110b2454ea94a0e540f171a26f` — configure Android KMP platform boundary
- `bc0cfa85198abac8cda3adc524fafe055085267a` — configure Android application compile sdk

## Trello
- **Configure Kotlin Multiplatform Targets** — 014-04, IN PROGRESS / verification pending.
- **Set Up Platform App Modules** — 014-06, IN PROGRESS / implementation complete, verification pending.

## Gate Update

014-01 through 014-07 are **VERIFIED and ACCEPTED**.

014-08 — Build/Test Baseline is **VERIFIED**.

014-09 — Architecture Verification is **IN PROGRESS**.

The next action is the formal 014-09 architecture verification against the clarified platform-boundary rule. No source-code changes are authorized by the verification step itself.

## 014-07 Verification and Acceptance — 2026-09-23

**State:** ACCEPTED

Implementation matches the frozen 014-07 dependency/module-wiring scope:
- :domain → :core
- :data → :domain, :core
- :navigation → :core
- :feature:splash → :domain, :core, :navigation
- :feature:dynamic → :domain, :core, :navigation

Verification completed:
- All five commonMainImplementation dependency reports: **BUILD SUCCESSFUL**.
- JVM compilation for :core, :domain, :data, :navigation, :feature:splash, and :feature:dynamic: **BUILD SUCCESSFUL**.
- Android KMP namespace/configuration failure previously observed on shared modules is resolved through the 014-06 platform-boundary namespace convention.
- No prohibited or out-of-scope implementation was introduced.

## 014-06 Namespace Correction Record — 2026-09-23

The Android KMP namespace requirement is recorded as part of 014-06.

The approved deterministic convention is centrally implemented in KmpConventionPlugin:
- :core → com.carbroz.cbpartner.core
- :domain → com.carbroz.cbpartner.domain
- :data → com.carbroz.cbpartner.data
- :navigation → com.carbroz.cbpartner.navigation
- :feature:splash → com.carbroz.cbpartner.feature.splash
- :feature:dynamic → com.carbroz.cbpartner.feature.dynamic

This correction is platform-boundary configuration only. It does not alter 014-07 dependency decisions, module boundaries, or the frozen BASE-ARCH-014 graph.

## 014-08 Authorization — 2026-09-23

The project owner explicitly authorized BASE-ARCH-014-08 — Build/Test Baseline.

The authorized verification completed successfully as recorded above. 014-08 is now VERIFIED and the workflow proceeds to the separately gated 014-09 architecture verification.


## 014-09 Architecture Verification — 2026-09-23

**Review state:** DOCUMENTATION CLARIFICATION COMPLETED — fresh verification pending

The project owner explicitly froze Option A: every supported platform has an application boundary, but not every application boundary is a Gradle module.

### Clarified platform-boundary rule
- Android: `androidApp/` → Gradle application project `:androidApp`.
- Desktop: `desktopApp/` → Gradle application project `:desktopApp`.
- iOS: `iosApp/` → native Xcode application boundary; **no `:iosApp` Gradle project**.
- No synthetic iOS Gradle module or iOS convention plugin may be introduced merely for symmetry.

### Documentation changes completed
The frozen architecture record, BASE-ARCH-014 implementation plan, and this implementation-status record were clarified so that platform application boundaries and Gradle modules are explicitly distinguished. The stale expectation that `:iosApp` must appear in the Gradle project hierarchy has been removed.

### Fresh verification requirement
014-09 must now be rerun against the clarified rule. The verification must check the actual iOS application boundary itself (native/Xcode boundary), rather than treating the absence of `:iosApp` from Gradle as a violation.

Until that fresh verification is completed, 014-09 remains **IN PROGRESS** and is not accepted.

**Source-code changes during this clarification:** None.
**Implementation scope:** Documentation/freeze-record clarification only.


## 014-09 iOS Boundary Implementation and Fresh Verification — 2026-09-23

**State:** VERIFIED — F02 RESOLVED; final 014-09 acceptance remains subject to project-owner review.

The project owner explicitly authorized implementation of the missing native iOS/Xcode application boundary identified as BASE-ARCH-014-09-F02.

### Implemented
- iosApp/CB-Partner.xcodeproj/project.pbxproj — minimal native Xcode application project.
- iosApp/CB-Partner.xcodeproj/project.xcworkspace/contents.xcworkspacedata — native Xcode workspace metadata.
- iosApp/CBPartnerApp.swift — minimal UIKit application entry point with no business/application behavior.
- Removed iosApp/.gitkeep because the real native boundary now exists.

### Explicitly not implemented
- No :iosApp Gradle project.
- No iOS Gradle convention plugin.
- No backend/bootstrap implementation.
- No Compose/Navigation/Koin/Store integration.
- No business or SDUI implementation.

### Fresh 014-09 verification
Verified against the clarified frozen architecture:
- iosApp/ now contains an actual Xcode project boundary rather than only .gitkeep.
- :iosApp remains absent from settings.gradle.kts, as required.
- No iOS Gradle convention exists in build-logic.
- Android and Desktop remain Gradle application boundaries.
- Shared Gradle module hierarchy remains unchanged.
- The previously reported F02 condition is resolved.
- The authorized 014-08 build/test baseline remains successful.

### Host limitation
The current verification host is Windows. A native Xcode build cannot be executed on this host because Xcode/macOS is required. Therefore iOS project-file/build validation is limited to repository/source-structure verification in this gate. No claim of an Xcode build success is made.

### Verification conclusion
BASE-ARCH-014-09-F02 is RESOLVED. No new architecture violation was identified during the fresh repository verification.

Source implementation was limited to the authorized iOS/Xcode application boundary.


## BASE-ARCH-014 Final Verification Checkpoint — 2026-09-23

**State:** FROZEN — OWNER ACCEPTED

### Verified units
- 014-01 → ACCEPTED
- 014-02 → ACCEPTED
- 014-03 → ACCEPTED
- 014-04 → ACCEPTED
- 014-05 → ACCEPTED
- 014-06 → ACCEPTED
- 014-07 → ACCEPTED
- 014-08 → VERIFIED
- 014-09 → VERIFIED; F02 RESOLVED
- T01 → VERIFIED

### T01 verification
- Gradle 9.7.1 is authoritative.
- Official Wrapper is implemented and committed in `0630982`.
- Wrapper verification passed.
- Wrapper-based BASE-ARCH-014 build/test baseline passed.

### 014-09 verification
- Native `iosApp/CB-Partner.xcodeproj` boundary exists.
- No `:iosApp` Gradle module exists.
- Android/Desktop remain Gradle application boundaries.
- No prohibited architecture, business, SDUI, fake bootstrap, or fake JSON scope was introduced.
- Windows host limitation prevents native Xcode build execution; repository-level boundary verification is complete.

### Final gate
No architecture blocker is identified. BASE-ARCH-014 is implementation-verified and is now waiting only for explicit project-owner acceptance before code freeze. No next architecture unit starts automatically.


## BASE-ARCH-014 Code Freeze — 2026-09-23

**State:** FROZEN

Owner acceptance received. BASE-ARCH-014 is complete and code-frozen. No additional source or architecture changes belong to this work item. Further changes require a new approved work item under AI_START_HERE.md workflow.
