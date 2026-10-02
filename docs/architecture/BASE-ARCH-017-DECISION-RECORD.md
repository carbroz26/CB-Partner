# BASE-ARCH-017 — Runtime Infrastructure Implementation Boundary

**State:** DECISION_FROZEN  
**Owner approval:** 2026-09-30  
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

The boundary is intentionally narrow. It exists to translate the already-frozen BASE-ARCH-015 and BASE-ARCH-016 contracts into an implementation plan without reopening those decisions.

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

In particular, applying it to `:data` must be justified by the concrete implementation need established in the implementation plan. It must not be added merely because `:data` contains a Koin module.

### D — Ktor HttpClient ownership

**ACCEPTED / FROZEN**

Core owns the generic networking capability/infrastructure boundary.

Data may own concrete HttpClient construction/composition when required by Data-layer implementation, provided that Data does not become the owner of generic networking infrastructure.

Concrete client configuration must respect the Core/Data responsibility boundary.

### E — Ktor engine and client configuration

**ACCEPTED / FROZEN**

The following are implementation-plan details:

- concrete Ktor engine selection;
- client configuration;
- serialization installation/configuration;
- timeouts;
- logging configuration;
- response handling;
- retry behavior;
- platform-specific engine wiring.

These details must be specified in the implementation plan and must not be silently invented during coding.

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

Authorization is limited to the frozen implementation plan:
`docs/architecture/BASE-ARCH-017-IMPLEMENTATION-PLAN.md`

This authorization does **not** authorize merge to `development` or code freeze. Those remain later gates.

## 7. Existing Branch Reconciliation

The pre-existing branch `feature/base-arch-017-runtime-infrastructure` contained implementation commits created before the BASE-ARCH-017 decision freeze.

Those changes were not retroactively authorized.

A new implementation branch was created directly from the current `development` head:

`feature/base-arch-017-runtime-infrastructure-implementation`

The pre-existing branch was not merged, rebased, or cherry-picked. Its intended useful changes were reconciled by resulting implementation state against the frozen plan.

## 8. Frozen State

```text
BASE-ARCH-015 → DECISION_FROZEN
BASE-ARCH-016 → DECISION_FROZEN
BASE-ARCH-017 → DECISION_FROZEN
                 ↓
              PLAN
                 ↓
           PLAN_FROZEN
                 ↓
        IMPLEMENTATION AUTHORIZATION
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

## 9. Implementation Record — 2026-09-30

**State:** IMPLEMENTATION COMPLETED — VERIFICATION PENDING

Implemented on:
`feature/base-arch-017-runtime-infrastructure-implementation`

Implementation scope was limited to the seven files defined by the frozen plan.

No application-composition source file was required, and no business, bootstrap, SDUI, ViewModel, Store, or new module scope was introduced.

Verification remains a separate gate and has not been represented as passed.
