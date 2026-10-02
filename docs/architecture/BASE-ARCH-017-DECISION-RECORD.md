# BASE-ARCH-017 — Runtime Infrastructure Implementation Boundary

**State:** CODE_FROZEN  
**Owner approval:** 2026-09-30  
**Review accepted:** 2026-10-02  
**Scope:** Frontend repository only

## 1. Purpose

BASE-ARCH-017 defines the architectural boundary for implementing the runtime technical infrastructure already selected by BASE-ARCH-015 and BASE-ARCH-016.

This decision does not introduce a new business architecture and does not re-decide previously frozen technologies.

## 2. Decision

BASE-ARCH-017 is accepted and frozen as a distinct architecture unit named:

**Runtime Infrastructure Implementation Boundary**

The boundary is:

```text
Core
├── coroutine infrastructure
├── serialization infrastructure
├── logging infrastructure
└── generic network infrastructure

Data
└── concrete data-layer network/client construction

Navigation
└── Navigation 3 runtime integration

Application Composition
└── runtime infrastructure assembly
```

The boundary is intentionally narrow. It translates the already-frozen BASE-ARCH-015 and BASE-ARCH-016 contracts into the approved implementation boundary without reopening those decisions.

## 3. Frozen Decisions

### A — BASE-ARCH-017 is a distinct architecture unit

**ACCEPTED / FROZEN**

BASE-ARCH-017 is a separate architecture unit because the accepted implementation boundary crosses Core, Data, Navigation, and Application Composition while remaining within the already-frozen technical contracts.

It does not replace or supersede BASE-ARCH-015 or BASE-ARCH-016.

### B — Runtime infrastructure ownership boundary

**ACCEPTED / FROZEN**

Ownership is:

- **Core:** generic coroutine, serialization, logging, and network infrastructure.
- **Data:** concrete data-layer network/client construction required by Data implementations.
- **Navigation:** Navigation 3 runtime integration.
- **Application Composition:** assembly of the runtime infrastructure into the application object graph.

No layer may absorb another layer's generic responsibility merely for implementation convenience.

### C — Koin Compiler Plugin placement

**ACCEPTED / FROZEN**

The Koin Compiler Plugin may be applied where a concrete implementation requires compiler-assisted Koin validation or generated/compiler integration.

It is **not** a mandatory blanket plugin for every module that declares Koin definitions.

For BASE-ARCH-017, `:data` does not apply the plugin because the concrete implementation uses ordinary Koin DSL and has no compiler-plugin requirement.

### D — Ktor HttpClient ownership

**ACCEPTED / FROZEN**

Core owns the generic networking capability/infrastructure boundary.

Data may own concrete HttpClient construction/composition when required by Data-layer implementation, provided that Data does not become the owner of generic networking infrastructure.

Concrete client configuration respects the Core/Data responsibility boundary.

### E — Ktor engine and client configuration

**ACCEPTED / FROZEN**

The implementation-plan decisions are:

- Ktor default engine strategy;
- common `HttpClient()` construction;
- no manual expect/actual engine abstraction;
- no business/API configuration in BASE-ARCH-017.

Other concrete client details remain implementation-plan concerns and must not be silently invented outside an authorized work item.

### F — Navigation 3 integration

**ACCEPTED / FROZEN**

Navigation 3 remains the previously selected and frozen navigation technology.

BASE-ARCH-017 does not reopen or re-decide Navigation 3.

BASE-ARCH-017 only defines the runtime integration boundary required to implement Navigation 3 within the existing `:navigation` responsibility.

## 4. Relationship to Previous Decisions

BASE-ARCH-015 remains the authority for the runtime dependency and technical-infrastructure contract.

BASE-ARCH-016 remains the authority for Koin composition and dependency-injection rules.

BASE-ARCH-017 defines only the implementation boundary between those contracts and the concrete runtime wiring.

Previously frozen decisions remain unchanged.

## 5. Explicit Non-Goals

BASE-ARCH-017 does not define or authorize:

- authentication;
- OTP;
- booking;
- payment;
- dashboard;
- SDUI internals;
- dynamic JSON;
- business-specific repositories or use cases;
- a new Gradle module;
- a standalone `:di` module;
- a global Store;
- ViewModel;
- a new navigation framework;
- a new universal networking abstraction without concrete need;
- automatic retry policy;
- backend API contracts;
- bootstrap DTO/schema.

## 6. Implementation Authorization

**IMPLEMENTATION AUTHORIZED — 2026-09-30**

The project owner explicitly authorized implementation of BASE-ARCH-017 after the implementation plan reached `PLAN_FROZEN`.

Authorization was limited to the frozen implementation plan:
`docs/architecture/BASE-ARCH-017-IMPLEMENTATION-PLAN.md`

Merge and code freeze were separate later gates.

## 7. Existing Branch Reconciliation

The pre-existing branch `feature/base-arch-017-runtime-infrastructure` contained implementation commits created before the BASE-ARCH-017 decision freeze.

Those changes were not retroactively authorized.

A new implementation branch was created directly from the current `development` head:

`feature/base-arch-017-runtime-infrastructure-implementation`

The pre-existing branch was not merged, rebased, or cherry-picked. Its intended useful changes were reconciled by resulting implementation state against the frozen plan.

## 8. Final Frozen State

```text
BASE-ARCH-015 → DECISION_FROZEN
BASE-ARCH-016 → DECISION_FROZEN
BASE-ARCH-017 → DECISION_FROZEN
                 ↓
              PLAN_FROZEN
                 ↓
        IMPLEMENTATION AUTHORIZATION
                 ↓
        RECONCILED IMPLEMENTATION
                 ↓
          TEST / VERIFICATION
                 ↓
       VERIFICATION PASSED
                 ↓
          REVIEW ACCEPTED
                 ↓
            CODE_FROZEN
```

## 9. Verification Record — 2026-10-02

Owner-environment verification was completed from `development` on Windows.

Final regression command:

```powershell
.\gradlew.bat :core:jvmTest :domain:jvmTest :data:jvmTest :navigation:jvmTest :feature:splash:jvmTest :feature:dynamic:jvmTest --no-daemon
```

Result:

```text
BUILD SUCCESSFUL
33 actionable tasks: 14 executed, 19 up-to-date
```

The Windows `iosSimulatorArm64Test` disabled-target warning is expected because iOS simulator tests require macOS. A non-blocking `ExperimentalCoroutinesApi` warning was also reported. Neither invalidates the successful JVM verification.

The build/test dependency issues found during verification were resolved using the relevant existing build-system/test fixes evidenced by the Splash branch. The Splash feature branch itself was not merged wholesale and BASE-ARCH-017 was not restarted.

## 10. Review Acceptance — 2026-10-02

**REVIEW ACCEPTED.**

The project owner accepted the BASE-ARCH-017 review after successful verification.

Review confirmed:

- frozen architecture boundary is preserved;
- no ViewModel/global Store was introduced;
- Core remains Koin-free;
- Data owns concrete HttpClient construction;
- Koin Compiler Plugin is not required by the implementation;
- Ktor default-engine strategy is preserved;
- Navigation 3 remains unchanged as the selected technology;
- no new `:di` module was introduced;
- no business, bootstrap, SDUI, or feature scope was introduced;
- required JVM regression verification passed;
- the non-blocking Windows/macOS limitation remains documented.

## 11. Code-Freeze Record — 2026-10-02

BASE-ARCH-017 is now **CODE_FROZEN**.

No further BASE-ARCH-017 implementation changes are authorized.

The code-freeze decision does not reopen BASE-ARCH-015, BASE-ARCH-016, or BASE-ARCH-017. Any future change to the frozen implementation requires a new separately authorized work item or an explicit reopening of the relevant decision gate.

The Splash feature branch is not a source of additional 017 implementation scope.

## 12. Implementation Record — 2026-09-30 / Finalized 2026-10-02

Implemented on:
`feature/base-arch-017-runtime-infrastructure-implementation`

The implementation was limited to the frozen plan and resulted in the approved runtime infrastructure state across Core, Data, and Navigation.

No application-composition source file was required, and no business, bootstrap, SDUI, ViewModel, Store, or new module scope was introduced.

**Final state:** CODE_FROZEN after successful verification and owner review acceptance on 2026-10-02.
