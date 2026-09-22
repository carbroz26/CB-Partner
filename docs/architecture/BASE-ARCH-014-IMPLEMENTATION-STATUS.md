# BASE-ARCH-014 — Implementation Status

**State:** VERIFIED — 014-01 THROUGH 014-06 VERIFIED  
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

## Implementation units
| Unit | Description | State |
|---|---|---|
| 014-01 | Gradle Project Skeleton | ACCEPTED |
| 014-02 | Version Catalog | ACCEPTED |
| 014-03 | Build Logic + Four Conventions | ACCEPTED |
| 014-04 | KMP Target Configuration | ACCEPTED |
| 014-05 | Compose Configuration | ACCEPTED |
| 014-06 | Platform Application Boundaries | ACCEPTED |
| 014-07 | Dependency and Module Wiring | PENDING |
| 014-08 | Build/Test Baseline | PENDING |
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

014-07, 014-08, and 014-09 remain pending. No next implementation unit is started by this checkpoint.
