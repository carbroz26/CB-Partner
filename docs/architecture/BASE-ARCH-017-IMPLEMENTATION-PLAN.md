# BASE-ARCH-017 — Runtime Infrastructure Implementation Plan

**State:** PLAN_FROZEN  
**Owner approval:** 2026-09-30  
**Scope:** Frontend repository only  
**Implementation authorization:** NOT GRANTED

## 1. Purpose

This document is the frozen implementation plan for BASE-ARCH-017 — Runtime Infrastructure Implementation Boundary.

It translates the already-frozen BASE-ARCH-015, BASE-ARCH-016, and BASE-ARCH-017 contracts into an exact implementation scope.

This document does not authorize implementation by itself. A separate explicit implementation-authorization gate is required after this plan freeze.

## 2. Frozen Inputs

The implementation must follow:

1. `docs/architecture/BASE-ARCHITECTURE.md`
2. `docs/architecture/BASE-ARCH-015-DECISION-RECORD.md`
3. `docs/architecture/BASE-ARCH-016-DECISION-RECORD.md`
4. `docs/architecture/BASE-ARCH-017-DECISION-RECORD.md`
5. `AI_START_HERE.md`
6. `docs/project-management/12-PROJECT-TRACKER.md`
7. `docs/project-management/04-CURRENT-STATUS.md`

Historical chat is not an implementation source.

## 3. Scope

### In scope

- Runtime dependency catalog entries required by the frozen contracts.
- Core coroutine, serialization, and logging runtime dependencies.
- Core generic networking capability required by the frozen contract.
- Data Koin definitions required to construct the concrete HTTP client.
- Ktor HttpClient construction.
- Ktor multiplatform engine-default strategy.
- Navigation 3 runtime/UI dependencies and integration foundation.
- Runtime infrastructure tests.
- Reconciliation of the pre-existing `feature/base-arch-017-runtime-infrastructure` changes.
- Required documentation/status updates after implementation and verification.

### Out of scope

- Authentication, OTP, booking, payment, dashboard.
- SDUI internals or dynamic JSON architecture.
- Backend API contracts.
- Bootstrap DTOs, schemas, or real bootstrap implementation.
- Business repositories/use cases.
- Feature Stores.
- ViewModel.
- A standalone `:di` module.
- A global Store.
- A universal `NetworkClient` abstraction without a concrete need.
- Business-specific HTTP configuration.
- Authentication headers or token handling.
- Retry policy.
- Business error mapping.
- Unrelated refactoring or cleanup.

## 4. Existing 017 Branch Reconciliation

The pre-existing branch `feature/base-arch-017-runtime-infrastructure` is 14 commits ahead and 5 commits behind `development`. Its changes were created before the BASE-ARCH-017 decision freeze and are therefore not retroactively authorized.

The branch must be reconciled by resulting implementation state, not by blindly preserving its commit history.

### Final reconciliation

| Change | Action | Final rule |
|---|---|---|
| Runtime dependency catalog | RETAIN / MODIFY | Keep only dependencies required by this plan |
| Core serialization plugin | RETAIN | Required for serialization usage |
| Core coroutines | RETAIN | Required runtime infrastructure |
| Core Kermit | RETAIN | Required logging infrastructure |
| Koin in Core | REMOVE | Core must remain DI-free |
| Koin Compiler Plugin in Data | REMOVE | No concrete 017 need exists |
| Koin Core in Data | RETAIN | Data owns its Koin definitions |
| Ktor Client Core | RETAIN | Data constructs the concrete client |
| Ktor engine defaults | RETAIN | Selected multiplatform engine strategy |
| Data NetworkModule | RETAIN / MODIFY | Final concrete client definition |
| NetworkModule tests | RETAIN / MODIFY | Verify the frozen client contract |
| Core RuntimeFoundationTest | RETAIN / MODIFY | Verify actual Core runtime contracts |
| Navigation 3 runtime | RETAIN | Previously frozen technology |
| Navigation 3 UI | RETAIN | Required Navigation 3 integration foundation |
| Coroutines test dependency | RETAIN | Required by tests |
| Koin test dependency | REMOVE | No remaining 017 test requirement |

No automatic merge, rebase, cherry-pick, or branch correction is part of the plan itself.

## 5. Exact Dependency Catalog

The implementation will retain the following BASE-ARCH-017 dependency baseline from the reconciled branch:

- Kotlin: `2.4.20`
- AGP: `9.3.0`
- Compose Multiplatform: `1.12.0`
- Koin: `4.2.0`
- Ktor: `3.6.0`
- kotlinx.coroutines: `1.11.0`
- kotlinx.serialization: `1.11.0`
- Kermit: `2.1.0`
- Navigation 3: `1.2.0-alpha04`

Required libraries:

- `io.insert-koin:koin-core`
- `io.ktor:ktor-client-core`
- `io.ktor:ktor-client-engine-defaults`
- `io.ktor:ktor-client-mock` for tests
- `org.jetbrains.kotlinx:kotlinx-coroutines-core`
- `org.jetbrains.kotlinx:kotlinx-coroutines-test` for tests
- `org.jetbrains.kotlinx:kotlinx-serialization-json`
- `co.touchlab:kermit`
- Navigation 3 runtime/UI artifacts selected by the existing catalog entries.

The Koin Compiler Plugin version/catalog capability is not applied to `:data` by this implementation.

## 6. Koin Compiler Plugin

### Decision

**REMOVE from `:data` for BASE-ARCH-017.**

The current Data implementation uses ordinary Koin DSL:

`module { single { HttpClient() } }`

There is no annotation-driven or generated Koin implementation requiring the compiler plugin.

### Rules

- `:core` remains Koin-free.
- `:data` uses `koin-core`.
- `:data` does not apply `alias(libs.plugins.koinCompiler)`.
- No annotations are introduced solely to justify the plugin.
- Compiler-plugin use remains available for a later implementation unit if an actual concrete need is established.

## 7. Ktor HttpClient Ownership

### Ownership

- Core owns the generic networking capability/infrastructure boundary.
- Data owns construction of the concrete application HttpClient.
- Application Composition assembles Data's network module.
- Feature and Domain code do not construct production HttpClient instances.

### Initial concrete definition

The reconciled Data module will provide one application-scoped client:

```kotlin
val networkModule = module {
    single { HttpClient() }
}
```

### Configuration boundary

BASE-ARCH-017 deliberately does not add business/API configuration.

The client must not acquire:

- API base URL;
- authentication;
- authorization/token handling;
- backend-specific headers;
- API error mapping;
- retry policy;
- business interceptors;
- bootstrap endpoint behavior.

Generic client configuration may only be added if required to satisfy the frozen runtime infrastructure contract and must remain generic.

## 8. Ktor Multiplatform Engine Strategy

### Decision

Use Ktor's `ktor-client-engine-defaults` dependency and construct the client through the common `HttpClient()` API.

No project-owned engine abstraction is introduced.

### Target strategy

| Target | Strategy |
|---|---|
| Android | Ktor curated default engine |
| Desktop/JVM | Ktor curated default engine |
| iOS/Native | Ktor curated default engine |
| Shared Data code | `HttpClient()` |
| Manual expect/actual engine factory | PROHIBITED |
| Manual platform engine abstraction | PROHIBITED |

No `ktor-client-android`, `ktor-client-okhttp`, `ktor-client-darwin`, or equivalent manually selected engine dependency is added by BASE-ARCH-017 unless a later concrete platform requirement explicitly reopens this decision.

The native iOS application remains the Xcode `iosApp` boundary and is not converted into a Gradle module.

## 9. Core Implementation Scope

### `:core/build.gradle.kts`

Add/retain:

- Kotlin serialization plugin.
- kotlinx.coroutines core.
- kotlinx.serialization JSON.
- Kermit.

Add coroutine test support only to `commonTest`.

No Koin dependency or Koin plugin.

### Core tests

`RuntimeFoundationTest.kt` may verify:

- serialization round-trip;
- coroutine test execution;
- only additional generic runtime contracts that are actually implemented.

Tests must not create business behavior or architecture abstractions merely for coverage.

## 10. Data Implementation Scope

### `:data/build.gradle.kts`

Add/retain:

- Kotlin serialization plugin if required by Data serialization usage.
- `:domain`.
- `:core`.
- Koin Core.
- Ktor Client Core.
- Ktor engine defaults.
- Ktor MockEngine for tests.
- kotlinx.coroutines test for tests.

Do not apply the Koin Compiler Plugin.

### `data/di/NetworkModule.kt`

Own the concrete HttpClient definition.

The application composition root assembles this module; Data does not start Koin itself.

### Data tests

`NetworkModuleTest.kt` must verify:

1. The network module resolves an HttpClient through Koin.
2. A MockEngine can execute a request without real network access.
3. Test-created clients are closed.
4. No real backend/API endpoint is contacted.

## 11. Navigation 3 Implementation Scope

### `:navigation/build.gradle.kts`

Add/retain the frozen Navigation 3 runtime/UI dependencies.

BASE-ARCH-017 does not re-decide Navigation 3.

Implementation must remain limited to the runtime integration foundation.

Do not introduce:

- business destinations;
- feature-specific routes beyond what is required for compilation/integration;
- authentication navigation;
- booking/payment navigation;
- SDUI navigation;
- a second navigation framework.

## 12. Application Composition Scope

The runtime infrastructure must be assembled through the already-frozen application composition boundary.

The implementation may wire:

- Data network module;
- Navigation runtime dependencies;
- Core runtime infrastructure;
- Koin application startup where required by the frozen DI contract.

The implementation must not create a new `:di` module.

No feature is allowed to own global runtime infrastructure.

## 13. Exact Files Expected to Change

Primary expected files:

```text
gradle/libs.versions.toml
core/build.gradle.kts
core/src/commonTest/kotlin/com/carbroz/cbpartner/core/RuntimeFoundationTest.kt
data/build.gradle.kts
data/src/commonMain/kotlin/com/carbroz/cbpartner/data/di/NetworkModule.kt
data/src/commonTest/kotlin/com/carbroz/cbpartner/data/di/NetworkModuleTest.kt
navigation/build.gradle.kts
```

Application-composition files may be added to the exact scope only if inspection of the existing composition root demonstrates that runtime assembly cannot be completed otherwise.

Any additional file requires an explicit implementation report explaining why it is necessary.

## 14. Verification Plan

### Structural

```powershell
.\gradlew.bat projects
```

### Core

```powershell
.\gradlew.bat :core:jvmTest
.\gradlew.bat :core:compileKotlinJvm
```

### Data

```powershell
.\gradlew.bat :data:jvmTest
.\gradlew.bat :data:compileKotlinJvm
```

### Navigation

```powershell
.\gradlew.bat :navigation:jvmTest
```

If Navigation has no test source set requiring execution, successful compilation/configuration is sufficient and must be recorded.

### Baseline regression

```powershell
.\gradlew.bat :core:jvmTest :domain:jvmTest :data:jvmTest :navigation:jvmTest :feature:splash:jvmTest :feature:dynamic:jvmTest
```

Additional platform verification may be run where supported by the host.

The existing Windows limitation for `iosSimulatorArm64Test` must remain documented: iOS simulator verification requires macOS.

## 15. Acceptance Criteria

BASE-ARCH-017 implementation is acceptable only when all are true:

1. The resulting code conforms to BASE-ARCH-015/016/017.
2. Core contains no Koin dependency or Koin compiler plugin.
3. Data contains Koin Core but does not apply the Koin Compiler Plugin.
4. Data owns the concrete HttpClient construction.
5. Ktor engine defaults are used for the multiplatform engine strategy.
6. No project-owned expect/actual networking abstraction is introduced.
7. No business/API configuration is introduced.
8. Navigation 3 dependencies are present in `:navigation` without introducing another navigation framework.
9. No standalone `:di` module is created.
10. No ViewModel or global Store is introduced.
11. Existing 017 branch changes are reconciled rather than blindly merged.
12. Required tests pass.
13. Targeted JVM compilation passes.
14. Existing foundation tests remain passing.
15. No unrelated files or architecture are changed.
16. Documentation/status records are synchronized after implementation verification.
17. Review finds no frozen-boundary violation.
18. A separate code-freeze/merge approval is obtained before integration into `development`.

## 16. Prohibited Changes

The implementation must stop and report rather than silently introduce:

- ViewModel.
- Global Store.
- `:di` Gradle module.
- Koin inside Core.
- blanket Koin Compiler Plugin application.
- universal NetworkClient abstraction without concrete need.
- manual expect/actual HTTP engine abstraction.
- manually selected platform engines without approved requirement.
- authentication/API configuration.
- business repositories/use cases.
- SDUI/dynamic JSON implementation.
- bootstrap implementation.
- unrelated refactoring.
- speculative platform abstractions.
- technology replacement.

## 17. Implementation Gate

The workflow is:

```text
BASE-ARCH-017 DECISION_FROZEN
        ↓
PLAN
        ↓
PLAN_FROZEN  ← current state
        ↓
IMPLEMENTATION AUTHORIZATION  ← separate owner approval required
        ↓
RECONCILED IMPLEMENTATION
        ↓
TEST / VERIFICATION
        ↓
REVIEW
        ↓
CODE_FREEZE
        ↓
MERGE TO development
```

**Current state:** PLAN_FROZEN.

**No source implementation is authorized by this document.**

## 18. Freeze Record

On 2026-09-30, the project owner accepted the four outstanding PLAN-stage decisions:

1. `:data` does not require the Koin Compiler Plugin for BASE-ARCH-017.
2. Data owns the concrete application HttpClient through the Koin network module; initial configuration remains generic and minimal.
3. Ktor `ktor-client-engine-defaults` is the multiplatform engine strategy; no project-owned expect/actual engine abstraction is introduced.
4. The pre-existing 017 branch is reconciled by resulting implementation state; its useful foundations are retained, while unjustified or obsolete wiring is removed or modified.

These decisions complete the PLAN gate.

**Implementation authorization remains separate and has not been granted.**
