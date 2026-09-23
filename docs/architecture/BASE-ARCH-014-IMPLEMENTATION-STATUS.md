# BASE-ARCH-014 — Implementation Status

**State:** IMPLEMENTING — 014-08 IN PROGRESS  
**Plan:** PLAN_FROZEN  
**Owner approval:** 2026-09-22  
**Scope:** Minimum Gradle/KMP/Compose/Build-Logic Foundation

## Current state
014-01 — Gradle Project Skeleton is **VERIFIED and accepted**.

014-02 — Version Catalog is **VERIFIED and accepted**.

014-03 — Build Logic + Four Conventions is **VERIFIED and accepted**.

014-04 — KMP Target Configuration is **VERIFIED and accepted**.

014-06 — Platform Application Boundaries is now **VERIFIED and accepted**. The owner explicitly approved Android `compileSdk = 36` for this unit.

014-05 — Compose Configuration is **VERIFIED and accepted**.

014-07 — Dependency and Module Wiring is **VERIFIED and accepted**.

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
| 014-08 | Build/Test Baseline | IMPLEMENTING |
| 014-09 | Architecture Verification | PENDING |

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

The Android-KMP configuration follows the current Android/Kotlin guidance for the `com.android.kotlin.multiplatform.library` plugin and its `kotlin { android { ... } }` DSL. Official guidance confirms the plugin is the supported Android target integration for KMP libraries, and the current compatibility range includes the project's Kotlin 2.4.20 / AGP 9.3.x combination. citeturn2search0turn0search1

## Verification state

Remote source inspection and local verification confirm the intended 014-04, 014-05, and 014-06 changes are present.

Local Gradle verification completed successfully on 2026-09-23. `gradle projects` and targeted JVM compilation both returned **BUILD SUCCESSFUL**.

The targeted JVM compilation verified `:core`, `:domain`, `:data`, `:navigation`, `:feature:splash`, and `:feature:dynamic`. The Windows iOS simulator disabled-target warning is expected because simulator tests require macOS.

014-04, 014-05, and 014-06 are now VERIFIED and accepted.

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
- 014-05 Compose configuration

## Git
Branch: `feature/base-arch-014`

014-06 implementation commits:
- `ca651a7e62d949110b2454ea94a0e540f171a26f` — configure Android KMP platform boundary
- `bc0cfa85198abac8cda3adc524fafe055085267a` — configure Android application compile sdk

## Trello
- **Configure Kotlin Multiplatform Targets** — 014-04, IN PROGRESS / verification pending.
- **Set Up Platform App Modules** — 014-06, IN PROGRESS / implementation complete, verification pending.

## Gate
014-01 through 014-06 are VERIFIED and accepted.

014-07 is verified and accepted. 014-08 and 014-09 remain pending. 014-08 requires separate explicit authorization.

Implementation scope for 014-07 is limited to frozen foundation module dependencies; no runtime library, DI, Store, Navigation implementation, bootstrap, business, or SDUI work is introduced.


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

The Windows iOS simulator disabled-target warning remains an expected host limitation and is not a verification failure.

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

## Gate Update

014-01 through 014-07 are now **VERIFIED and ACCEPTED**.

014-08 — Build/Test Baseline is **IMPLEMENTING**.

014-09 — Architecture Verification is **PENDING**.

**Next authorization:** 014-08 has been explicitly authorized by the project owner on 2026-09-23 and is now active. Verification and acceptance are pending.


## 014-08 Implementation Start — 2026-09-23

**State:** IMPLEMENTING

Owner authorization was explicitly received for BASE-ARCH-014-08 — Build/Test Baseline.

Authorized scope:
- establish minimal shared testing/build verification;
- run and record actual Gradle test/build commands and results;
- preserve the frozen module architecture and dependency graph;
- no new runtime architecture, business logic, backend/bootstrap, SDUI, ViewModel, or prohibited modules.

Implementation begins with verification of the existing minimal foundation. No code change is authorized unless required by a concrete baseline failure and kept within this unit's scope.

**Next action:** run the 014-08 baseline verification commands and report actual results.
