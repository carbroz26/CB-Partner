# CB-Partner — Project Tracker

**Status:** ACTIVE  
**Scope:** Frontend repository only  
**Purpose:** Single execution ledger for recovering and tracking current project state across AI sessions.

## Current Project State
- Phase: Base Architecture implementation
- Current module: Base Architecture
- Current work item: BASE-ARCH-017 — Runtime Infrastructure Implementation Boundary
- Workflow state: DECISION FROZEN — BASE-ARCH-017
- Current implementation unit: BASE-ARCH-017 decision frozen; implementation plan not yet frozen; production implementation not authorized
- Frozen decisions: BASE-ARCH-001 through BASE-ARCH-017
- Frozen process decision: TRELLO-002 — Simple Frontend Work Board
- BASE-ARCH-015 decision record: docs/architecture/BASE-ARCH-015-DECISION-RECORD.md
- BASE-ARCH-016 decision record: docs/architecture/BASE-ARCH-016-DECISION-RECORD.md
- BASE-ARCH-017 decision record: docs/architecture/BASE-ARCH-017-DECISION-RECORD.md
- Blockers: None identified
- Last completed action: BASE-ARCH-017 A–F accepted and frozen; documentation/status synchronization completed
- Next valid action: Enter PLAN for BASE-ARCH-017; reconcile the pre-existing 017 implementation branch against the frozen contracts before any implementation authorization
- Last updated: 2026-09-30

## Base Architecture Register
| ID | Unit | State | Durable Record | Implementation Authorization |
|---|---|---|---|---|
| BASE-ARCH-001 | Project and Gradle Module Structure | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-002 | Module Responsibilities and Dependency Graph | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-003 | Gradle Module Granularity | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-004 | Core Architecture and Technical Infrastructure | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-005 | Navigation and Application Composition | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-006 | Dependency Injection and Application Composition | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-007 | Feature and Pure Store Architecture | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-008 | Application Bootstrap and Startup Flow | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-009 | Application Root and Platform Composition Boundary | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-010 | Application Startup Lifecycle and Initialization Ordering | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-011 | Application Startup Failure, Retry and Recovery Boundary | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-012 | Application Startup State and Bootstrap Contract | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-013 | Application Bootstrap Data Flow and Layer Ownership | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | No separate plan |
| BASE-ARCH-014 | Minimum Gradle/KMP/Compose/Build-Logic Foundation | IMPLEMENTATION_VERIFIED / CODE_FROZEN | docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md | Plan frozen; owner acceptance complete; implementation frozen |
| BASE-ARCH-015 | Runtime Dependency & Technical Infrastructure Implementation Contract | DECISION_FROZEN | docs/architecture/BASE-ARCH-015-DECISION-RECORD.md | Decision frozen; implementation not authorized by this record |
| BASE-ARCH-016 | Koin Composition Root & Dependency Injection Implementation Contract | DECISION_FROZEN | docs/architecture/BASE-ARCH-016-DECISION-RECORD.md | Decision frozen; implementation not authorized by this record |
| BASE-ARCH-017 | Runtime Infrastructure Implementation Boundary | DECISION_FROZEN | docs/architecture/BASE-ARCH-017-DECISION-RECORD.md | Decision frozen; implementation not authorized; plan required |

## Process Decision Register
| ID | Decision | State | Durable Record | Owner Approval |
|---|---|---|---|---|
| TRELLO-002 | Simple Frontend Work Board | DECISION_FROZEN | docs/project-management/06-TRELLO-WORKFLOW.md | 2026-09-22 |

## Work Item Register
| ID | Module/Feature | Unit | State | Plan | Status | Branch | Trello | Blocker | Next Action |
|---|---|---|---|---|---|---|---|---|---|
| ARCH-DOC-001 | Base Architecture | Durable architecture documentation | COMPLETE | docs/architecture/BASE-ARCHITECTURE.md | Complete | docs/base-architecture-record | — | None | Complete |
| BASE-ARCH-013 | Base Architecture | Application Bootstrap Data Flow & Layer Ownership | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | Decision frozen; implementation not authorized by this record | docs/base-architecture-record | — | None | Continue only through separately authorized implementation work |
| BASE-ARCH-014 | Base Architecture | Minimum Gradle/KMP/Compose/Build-Logic Foundation | CODE FROZEN | docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md | 014-01 through 014-09 verified; T01 verified; owner accepted; implementation frozen | feature/base-arch-014 | Completed | None identified | No further 014 changes; proceed only through a new authorized work item |
| BASE-ARCH-015 | Base Architecture | Runtime Dependency & Technical Infrastructure Implementation Contract | DECISION_FROZEN | docs/architecture/BASE-ARCH-015-DECISION-RECORD.md | A–F accepted and frozen; no production implementation started | — | Not started automatically | None identified | Synchronization complete; select next authorized work item |
| BASE-ARCH-016 | Base Architecture | Koin Composition Root & Dependency Injection Implementation Contract | DECISION_FROZEN | docs/architecture/BASE-ARCH-016-DECISION-RECORD.md | A–G accepted and frozen; no production implementation started | — | Not started automatically | None identified | Select next authorized architecture work item or implementation unit |
| BASE-ARCH-017 | Base Architecture | Runtime Infrastructure Implementation Boundary | DECISION_FROZEN | docs/architecture/BASE-ARCH-017-DECISION-RECORD.md | A–F accepted and frozen; implementation plan required; pre-existing implementation branch requires reconciliation | feature/base-arch-017-runtime-infrastructure | Not started automatically | None identified | Enter PLAN; reconcile existing branch changes before implementation authorization |

## Trello State
- Authoritative board: **CB-Partner — Frontend**
- Workflow: **BACKLOG → TO DO → READY → IN PROGRESS → DONE**
- Completed BASE-ARCH-014 execution cards remain complete.
- BASE-ARCH-015, BASE-ARCH-016, and BASE-ARCH-017 do not automatically create or start Trello execution work.
- No automatic next-task transition is permitted.

## BASE-ARCH-014 Unit Register
| Unit | State | Verification | Notes |
|---|---|---|---|
| 014-01 | ACCEPTED | `gradle projects` and `gradle tasks` both BUILD SUCCESSFUL; working tree clean | Project skeleton/module registration verified locally |
| 014-02 | ACCEPTED | `gradle projects` and `gradle tasks` both BUILD SUCCESSFUL; Git clean and synchronized | Version Catalog verified and accepted by user on 2026-09-22 |
| 014-03 | ACCEPTED | Build-logic build verified; accepted | Build Logic + Four Conventions |
| 014-04 | ACCEPTED | Blocked previously by missing Android compileSdk; resolved through 014-06 | iOS ARM64, iOS Simulator ARM64, JVM; Android target/configuration supplied by 014-06 |
| 014-05 | ACCEPTED | `gradle projects` and targeted JVM compilation both BUILD SUCCESSFUL | Compose Configuration verified through actual feature module configuration/compilation |
| 014-06 | ACCEPTED | `gradle projects` and targeted JVM compilation both BUILD SUCCESSFUL | Android KMP target + Android application compileSdk 36; iOS/Desktop remain thin boundaries |
| 014-07 | ACCEPTED | Dependency reports and JVM compilation BUILD SUCCESSFUL | Foundation module dependency wiring only |
| 014-08 | VERIFIED | `gradle projects`, `-p build-logic build`, six `jvmTest` tasks, and six JVM compilation tasks all BUILD SUCCESSFUL | No source changes; Windows iOS simulator warning is expected host limitation |
| 014-09 | VERIFIED | Fresh repository architecture verification completed | Platform boundaries, dependency direction, Compose boundary, prohibited structures, scope compliance, and iOS/Xcode boundary verified; Windows host limitation recorded |

## Verification Record — 014-08 — 2026-09-23

Authorized baseline verification completed successfully on the project owner's Windows environment.

Commands:
- `gradle projects` → **BUILD SUCCESSFUL**; 5 actionable tasks.
- `gradle -p build-logic build` → **BUILD SUCCESSFUL**; 6 actionable tasks, all up-to-date.
- `gradle :core:jvmTest :domain:jvmTest :data:jvmTest :navigation:jvmTest :feature:splash:jvmTest :feature:dynamic:jvmTest` → **BUILD SUCCESSFUL**; 28 actionable tasks.
- `gradle :core:compileKotlinJvm :domain:compileKotlinJvm :data:compileKotlinJvm :navigation:compileKotlinJvm :feature:splash:compileKotlinJvm :feature:dynamic:compileKotlinJvm` → **BUILD SUCCESSFUL**; 15 actionable tasks.

The `iosSimulatorArm64Test` disabled-target warning is expected on Windows because simulator tests require macOS. No suppression configuration was added.

No source-code changes were required for 014-08.

## Verification Record — 014-06
Implementation completed on branch `feature/base-arch-014`.

Changed source files:
- `build-logic/src/main/kotlin/com/carbroz/cbpartner/buildlogic/KmpConventionPlugin.kt`
- `build-logic/src/main/kotlin/com/carbroz/cbpartner/buildlogic/AndroidApplicationConventionPlugin.kt`

Android `compileSdk = 36` is configured for both the shared Android-KMP target and the Android application convention.

Local verification completed successfully on 2026-09-23: `gradle projects` and targeted JVM compilation both returned **BUILD SUCCESSFUL**.

## Recovery Algorithm
1. Read AI_START_HERE.md.
2. Read this Tracker.
3. Read Current Status.
4. Identify current module/work item/state.
5. Read the relevant frozen implementation plan and status.
6. Inspect Git branch/status/log and relevant Trello state.
7. Reconcile contradictions before continuing.
8. Determine exactly one next valid action.
9. Do not implement unless state and authorization permit it.

## Freeze Rule
After a meaningful freeze or documentation checkpoint, update required records, report state, return one next prompt, and stop. Do not automatically start the next unit.

## Documentation Authority
The authoritative Base Architecture record is:
docs/architecture/BASE-ARCHITECTURE.md

The frozen BASE-ARCH-014 implementation contract is:
docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md

The frozen BASE-ARCH-015 runtime contract is:
docs/architecture/BASE-ARCH-015-DECISION-RECORD.md

The frozen BASE-ARCH-016 Koin/DI contract is:
docs/architecture/BASE-ARCH-016-DECISION-RECORD.md

Historical chat is not an implementation source.

## BASE-ARCH-014-07 Acceptance Checkpoint — 2026-09-23

**Checkpoint state:** 014-07 VERIFIED → ACCEPTED → CHECKPOINTED.

### Verified dependency graph
- :domain → :core
- :data → :domain, :core
- :navigation → :core
- :feature:splash → :domain, :core, :navigation
- :feature:dynamic → :domain, :core, :navigation

### Verification
- Five commonMainImplementation dependency reports: **BUILD SUCCESSFUL**.
- Targeted JVM compilation for :core, :domain, :data, :navigation, :feature:splash, and :feature:dynamic: **BUILD SUCCESSFUL**.
- Android KMP namespace/configuration issue resolved through the 014-06 platform-boundary convention.
- No prohibited modules, runtime architecture implementation, business logic, SDUI internals, fake bootstrap, or fake JSON introduced.

### State transition
IMPLEMENTING — 014-07 → VERIFIED → ACCEPTED → CHECKPOINTED.

014-08 is **AUTHORIZED and IN PROGRESS** as of 2026-09-23.

### Next valid action
Execute and record the frozen 014-08 Build/Test Baseline verification. Do not start 014-09.

## BASE-ARCH-014-08 Authorization — 2026-09-23

**State:** IMPLEMENTING

The project owner explicitly authorized 014-08 — Build/Test Baseline.

Scope:
- minimal shared testing/build verification;
- actual command/result recording;
- no architecture expansion or unrelated changes.

014-09 remains gated and must not start automatically.

## BASE-ARCH-014-09 Architecture Verification — 2026-09-23

**Review state:** VERIFIED — F02 resolved; final BASE-ARCH-014 owner acceptance pending.

Formal verification completed after the 014-08 records were reconciled and after following `AI_START_HERE.md` routing.

Verified:
- approved Gradle project hierarchy for the currently registered modules;
- build-logic and four approved convention plugins;
- frozen foundation dependency direction in inspected module build files;
- Compose boundary;
- absence of ViewModel/prohibited architecture modules;
- absence of business-specific/SDUI/fake-bootstrap scope expansion;
- 014-08 build/test baseline successful.

### Platform Boundary Clarification
The owner approved and froze the rule that every supported platform has an application boundary, but not every application boundary is a Gradle module.

- Android: `androidApp/` → `:androidApp` Gradle application.
- Desktop: `desktopApp/` → `:desktopApp` Gradle application.
- iOS: `iosApp/` → native Xcode application boundary; no `:iosApp` Gradle project.

The previous finding that treated the absence of `:iosApp` from Gradle as a discrepancy is superseded. Fresh 014-09 verification must inspect the actual iOS/Xcode boundary instead.

**Documentation state:** clarification complete.
**Source changes:** none.
**Next action:** perform fresh 014-09 architecture verification against the clarified rule.

## BASE-ARCH-014-09 F02 Resolution — 2026-09-23

**State:** VERIFIED — F02 resolved; final 014-09 owner acceptance remains part of the BASE-ARCH-014 final gate.

The owner authorized creation of the native iOS/Xcode application boundary after fresh 014-09 identified BASE-ARCH-014-09-F02.

Implemented under iosApp/:
- native Xcode project;
- Xcode workspace metadata;
- minimal UIKit application entry point;
- placeholder .gitkeep removed.

Frozen constraints preserved:
- no :iosApp Gradle project;
- no iOS Gradle convention plugin;
- no business/application architecture;
- no Store, Navigation, Koin, bootstrap, backend, or SDUI implementation.

Fresh verification confirms the iOS application boundary now exists and the complete inspected repository conforms to the clarified BASE-ARCH-014 platform-boundary rule. No new finding was identified.

Windows host limitation: native Xcode/macOS build verification could not be executed. Repository-level Xcode boundary verification was completed.

## BASE-ARCH-014-T01 — Gradle Wrapper + Git/Build Workflow — 2026-09-23

**State:** VERIFIED
**Owner authorization:** Explicitly granted on 2026-09-23.

Scope:
- add official Gradle Wrapper targeting Gradle 9.7.1;
- make clone → build → test → commit → push → PR → merge → sync operationally documented;
- route AI sessions through the exact terminal/Git command document.

Documentation portion completed:
- `AI_START_HERE.md` updated;
- `docs/project-management/11-TERMINAL-GIT-COMMANDS.md` expanded.

Official Gradle 9.7.1 Wrapper generation and verification are complete. The wrapper is committed in `0630982`, and the Wrapper-based baseline passed. No hand-created or fake Wrapper was used.

## BASE-ARCH-014 Final Implementation Verification Checkpoint — 2026-09-23

**State:** IMPLEMENTATION VERIFIED — OWNER ACCEPTANCE PENDING

Actual repository state on `feature/base-arch-014` has been reconciled with the execution records:

- 014-01 through 014-07 → ACCEPTED.
- 014-08 → VERIFIED.
- 014-09 → VERIFIED; F02 resolved.
- T01 → VERIFIED; Gradle 9.7.1 Wrapper and Wrapper-based baseline verified.
- Native iOS/Xcode application boundary exists under `iosApp/`; no `:iosApp` Gradle module exists.
- Working implementation branch: `feature/base-arch-014`.
- No architecture blocker identified.

The repository is now at the final owner acceptance/code-freeze gate. No new implementation unit starts automatically.

## BASE-ARCH-014 CODE FREEZE — 2026-09-23

**State:** FROZEN

The project owner explicitly accepted the complete BASE-ARCH-014 implementation after verification. BASE-ARCH-014 is now code-frozen on `feature/base-arch-014`.

Frozen verification state:
- 014-01 through 014-07 → ACCEPTED.
- 014-08 → VERIFIED.
- 014-09 → VERIFIED; F02 resolved.
- T01 → VERIFIED; Gradle 9.7.1 Wrapper and Wrapper-based baseline verified.
- No architecture blocker identified.

No further implementation changes are permitted under BASE-ARCH-014. Any new change must be handled as a new approved work item under the project workflow.

## BASE-ARCH-014 Post-Freeze Checkpoint — 2026-09-23

**State:** FROZEN — MERGE PREPARATION

BASE-ARCH-014 is owner-accepted and code-frozen on `feature/base-arch-014`.

Verified/frozen:
- 014-01 through 014-07 → ACCEPTED.
- 014-08 → VERIFIED.
- 014-09 → VERIFIED; F02 resolved.
- T01 → VERIFIED; Gradle 9.7.1 Wrapper and Wrapper-based baseline verified.
- Native iOS/Xcode application boundary exists under `iosApp/`; no `:iosApp` Gradle module.
- No architecture blocker identified.

No further implementation changes are permitted under BASE-ARCH-014. The next workflow action was PR/merge preparation from `feature/base-arch-014` to `development`; that merge is now complete.

## BASE-ARCH-014 PR — 2026-09-23

**PR:** #2 — Merge BASE-ARCH-014 foundation into development  
**State:** MERGED / VERIFIED  
**Head:** `feature/base-arch-014`  
**Base:** `development`

The frozen implementation was reviewed and merged. Post-merge verification was completed successfully.

## BASE-ARCH-015 Decision Freeze — 2026-09-30

**State:** DECISION_FROZEN

The project owner explicitly accepted and froze BASE-ARCH-015 A–F.

Durable record: `docs/architecture/BASE-ARCH-015-DECISION-RECORD.md`.

No production runtime implementation was started by this freeze. BASE-ARCH-015 is an architecture/implementation contract and must not be treated as automatic authorization for runtime dependency wiring.

## BASE-ARCH-016 Decision Freeze — 2026-09-30

**State:** DECISION_FROZEN

The project owner explicitly accepted and froze BASE-ARCH-016 A–G.

Durable record: `docs/architecture/BASE-ARCH-016-DECISION-RECORD.md`.

No production Koin/DI implementation was started by this freeze. BASE-ARCH-016 is an architecture/implementation contract and must not be treated as automatic authorization to modify production code.

### Frozen 016 boundary
- Koin 4.2.x + compatible Compiler Plugin baseline for Kotlin 2.4.20.
- Constructor injection remains primary.
- DI definitions remain with owning modules; no standalone `:di` module.
- Compiler validation is the primary DI graph-safety mechanism.
- Compose integration is added only for an actual consumer; no ViewModel Koin architecture.
- Platform dependencies enter through the established platform boundaries.
- Koin does not own Pure Store lifecycle.

## Documentation/Tracker Synchronization — 2026-09-30

**State:** COMPLETE

Synchronized records:
- `docs/project-management/04-CURRENT-STATUS.md`
- `docs/project-management/12-PROJECT-TRACKER.md`
- `docs/architecture/BASE-ARCH-015-DECISION-RECORD.md`
- `docs/architecture/BASE-ARCH-016-DECISION-RECORD.md`
- `docs/architecture/BASE-ARCH-017-DECISION-RECORD.md`
- `docs/architecture/BASE-ARCHITECTURE.md`

The decision state is now durable through BASE-ARCH-017. BASE-ARCH-017 has no implementation authorization; the pre-existing 017 implementation branch requires PLAN-stage reconciliation. No Trello execution work is started automatically.

## BASE-ARCH-017 PLAN FREEZE — 2026-09-30

**State:** PLAN_FROZEN

Frozen implementation plan:
`docs/architecture/BASE-ARCH-017-IMPLEMENTATION-PLAN.md`

The project owner accepted the four outstanding PLAN-stage decisions:
1. `:data` does not apply the Koin Compiler Plugin for 017.
2. Data owns the concrete application `HttpClient`; initial configuration remains generic and minimal.
3. `ktor-client-engine-defaults` is the multiplatform engine strategy; no project-owned `expect/actual` engine abstraction.
4. The pre-existing 017 branch is reconciled by resulting implementation state, not blindly merged by commit history.

**Implementation authorization:** NOT GRANTED.

### Next Valid Action
Obtain explicit implementation authorization for BASE-ARCH-017. Do not modify source, merge, rebase, switch, or continue the pre-existing 017 implementation branch until that authorization is granted.



## BASE-ARCH-017 Decision Freeze — 2026-09-30

**State:** DECISION_FROZEN

The project owner explicitly accepted and froze BASE-ARCH-017 A–F.

Durable record:
- `docs/architecture/BASE-ARCH-017-DECISION-RECORD.md`
- `docs/architecture/BASE-ARCHITECTURE.md`

Frozen boundary:
- Core owns generic coroutine, serialization, logging, and network infrastructure.
- Data may own concrete data-layer network/client construction without owning generic networking infrastructure.
- Navigation owns Navigation 3 runtime integration.
- Application Composition owns runtime infrastructure assembly.
- Koin Compiler Plugin usage is conditional on concrete implementation need; it is not a blanket module requirement.
- Ktor engine/client configuration remains implementation-plan detail.
- Navigation 3 remains previously frozen and is not re-decided by 017.

No production implementation is authorized by this freeze.

### Existing 017 branch reconciliation requirement

The pre-existing `feature/base-arch-017-runtime-infrastructure` branch contains implementation commits created before the 017 decision freeze. Those commits are not retroactively authorized. They must be reviewed/reconciled during PLAN before merge or continued implementation.

### Next valid action

Enter PLAN for BASE-ARCH-017. Do not merge, switch to, correct, or continue the pre-existing implementation branch until the plan/reconciliation gate is completed and implementation is separately authorized.
