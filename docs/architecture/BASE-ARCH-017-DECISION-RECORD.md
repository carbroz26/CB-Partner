# BASE-ARCH-017 — Runtime Infrastructure Implementation Boundary

**State:** DECISION_FROZEN  
**Owner approval:** 2026-09-30  
**Scope:** Frontend repository only

## 1. Purpose

BASE-ARCH-017 defines the architectural boundary for implementing the runtime technical infrastructure already selected by BASE-ARCH-015 and BASE-ARCH-016.

This decision does not introduce a new business architecture, does not re-decide previously frozen technologies, and does not authorize production source implementation.

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

The following remain implementation-plan details rather than new architecture decisions:

- concrete Ktor engine selection;
- client configuration;
- serialization installation/configuration;
- timeouts;
- logging configuration;
- response handling;
- retry behavior;
- platform-specific engine wiring.

These details must be specified in the implementation plan before implementation and must not be silently invented during coding.

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
- bootstrap DTO/schema;
- production source implementation.

## 6. Implementation Authorization

This decision is **architecture-frozen only**.

It does **not** authorize implementation.

The next required gate is a separately created implementation plan that must define:

1. exact affected modules/files;
2. exact dependency/plugin changes;
3. exact Ktor client/engine configuration;
4. exact Koin compiler-plugin usage and justification;
5. exact Navigation 3 integration changes;
6. exact runtime object-graph assembly;
7. tests and verification commands;
8. acceptance criteria;
9. prohibited changes;
10. reconciliation of any pre-existing BASE-ARCH-017 branch changes with this frozen boundary.

The implementation plan must be explicitly frozen before implementation begins.

## 7. Existing Branch Reconciliation Note

A pre-existing branch named `feature/base-arch-017-runtime-infrastructure` contains implementation commits that were created before BASE-ARCH-017 was decision-frozen.

Those changes are **not** authorized retroactively by this decision.

They must be reviewed against BASE-ARCH-015, BASE-ARCH-016, and this BASE-ARCH-017 boundary during the PLAN/RECONCILIATION stage before any merge or continued implementation.

No automatic merge, branch switch, or source correction is authorized by this freeze.

## 8. Frozen State

```text
BASE-ARCH-015 → DECISION_FROZEN
BASE-ARCH-016 → DECISION_FROZEN
BASE-ARCH-017 → DECISION_FROZEN
                 ↓
              PLAN
                 ↓
           PLAN FREEZE
                 ↓
        IMPLEMENTATION AUTHORIZATION
```

**Next valid action:** Enter PLAN for BASE-ARCH-017 after reviewing/reconciling the existing implementation branch against the frozen contracts.

## 9. PLAN Freeze — 2026-09-30

**State:** PLAN_FROZEN

The project owner accepted the four outstanding PLAN-stage decisions and the implementation plan is now frozen.

Frozen implementation-plan decisions:
- `:data` does not apply the Koin Compiler Plugin for BASE-ARCH-017 because the concrete implementation has no compiler-plugin requirement.
- Data owns the concrete application `HttpClient` through its Koin network module; initial configuration remains generic and minimal.
- Ktor `ktor-client-engine-defaults` is the multiplatform engine strategy; no project-owned `expect/actual` engine abstraction is introduced.
- The pre-existing `feature/base-arch-017-runtime-infrastructure` branch is reconciled by resulting implementation state rather than blindly preserving its 14-commit history.

Frozen implementation plan:
`docs/architecture/BASE-ARCH-017-IMPLEMENTATION-PLAN.md`

**Implementation authorization:** NOT GRANTED.

The next valid action is a separate explicit implementation-authorization decision.