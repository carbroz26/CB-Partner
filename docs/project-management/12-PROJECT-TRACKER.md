# CB-Partner — Project Tracker

**Status:** ACTIVE  
**Scope:** Frontend repository only  
**Purpose:** Single execution ledger for recovering and tracking current project state across AI sessions.

## Current Project State
- Phase: Base Architecture implementation
- Current module: Base Architecture
- Current work item: BASE-ARCH-014 — Minimum Gradle/KMP/Compose/Build-Logic Foundation
- Workflow state: IMPLEMENTING — 014-09 Architecture Verification
- Current implementation unit: 014-09 — Architecture Verification (in progress)
- Frozen decisions: BASE-ARCH-001 through BASE-ARCH-014
- Frozen process decision: TRELLO-002 — Simple Frontend Work Board
- Frozen implementation plan: docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md
- Implementation status: docs/architecture/BASE-ARCH-014-IMPLEMENTATION-STATUS.md
- Blockers: 014-09 fresh verification pending after platform-boundary clarification
- Last completed action: Owner-approved clarification that platform application boundary ≠ Gradle module boundary
- Next valid action: Complete fresh 014-09 Architecture Verification; do not modify source code during verification
- Last updated: 2026-09-23

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
| BASE-ARCH-014 | Minimum Gradle/KMP/Compose/Build-Logic Foundation | DECISION_FROZEN | docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md | Plan frozen; implementation authorized |

## Process Decision Register
| ID | Decision | State | Durable Record | Owner Approval |
|---|---|---|---|---|
| TRELLO-002 | Simple Frontend Work Board | DECISION_FROZEN | docs/project-management/06-TRELLO-WORKFLOW.md | 2026-09-22 |

## Work Item Register
| ID | Module/Feature | Unit | State | Plan | Status | Branch | Trello | Blocker | Next Action |
|---|---|---|---|---|---|---|---|---|---|
| ARCH-DOC-001 | Base Architecture | Durable architecture documentation | COMPLETE | docs/architecture/BASE-ARCHITECTURE.md | Complete | docs/base-architecture-record | — | None | Complete |
| BASE-ARCH-013 | Base Architecture | Application Bootstrap Data Flow & Layer Ownership | DECISION_FROZEN | docs/architecture/BASE-ARCHITECTURE.md | Decision frozen; implementation not authorized by this record | docs/base-architecture-record | — | None | Continue only through separately authorized implementation work |
| BASE-ARCH-014 | Base Architecture | Minimum Gradle/KMP/Compose/Build-Logic Foundation | IMPLEMENTING — 014-09 | docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md | 014-01 through 014-08 verified; 014-09 in progress | feature/base-arch-014 | Set Up Platform App Modules — IN PROGRESS | None identified | Complete 014-09 Architecture Verification |

## Trello State
- Authoritative board: **CB-Partner — Frontend**
- Workflow: **BACKLOG → TO DO → READY → IN PROGRESS → DONE**
- **DONE:** Set Up Gradle Project Structure; Add Version Catalog; Set Up Gradle Build Conventions.
- **IN PROGRESS:** Set Up Platform App Modules.
- **Completed verification:** Configure Kotlin Multiplatform Targets; 014-05 Compose Configuration; 014-06 Platform Application Boundaries.
- **TO DO:** remaining approved foundation and architecture implementation work.
- **BACKLOG:** future/uncommitted work.
- No automatic next-task transition is permitted.

## BASE-ARCH-014 Unit Register
| Unit | State | Verification | Notes |
|---|---|---|---|
| 014-01 | ACCEPTED | `gradle projects` and `gradle tasks` both BUILD SUCCESSFUL; working tree clean | Project skeleton/module registration verified locally |
| 014-02 | ACCEPTED | `gradle projects` and `gradle tasks` both BUILD SUCCESSFUL; Git clean and synchronized | Version Catalog verified and accepted by user on 2026-09-22 |
| 014-03 | ACCEPTED | Build-logic build verified; accepted | Build Logic + Four Conventions |
| 014-04 | ACCEPTED | Blocked previously by missing Android compileSdk; now ready for re-verification after 014-06 | iOS ARM64, iOS Simulator ARM64, JVM; Android target/configuration now supplied by 014-06 |
| 014-05 | ACCEPTED | `gradle projects` and targeted JVM compilation both BUILD SUCCESSFUL | Compose Configuration verified through actual feature module configuration/compilation |
| 014-06 | ACCEPTED | `gradle projects` and targeted JVM compilation both BUILD SUCCESSFUL | Android KMP target + Android application compileSdk 36; iOS/Desktop remain thin boundaries |
| 014-07 | ACCEPTED | Dependency reports and JVM compilation BUILD SUCCESSFUL | Foundation module dependency wiring only |
| 014-08 | VERIFIED | `gradle projects`, `-p build-logic build`, six `jvmTest` tasks, and six JVM compilation tasks all BUILD SUCCESSFUL | No source changes; Windows iOS simulator warning is expected host limitation |
| 014-09 | IN PROGRESS | Formal architecture verification in progress | Module structure, dependency direction, Compose boundary, prohibited structures, and scope compliance |

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

**Review state:** REQUIRED finding — not accepted.

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

**State:** VERIFIED — pending owner acceptance.

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

**State:** IMPLEMENTING
**Owner authorization:** Explicitly granted on 2026-09-23.

Scope:
- add official Gradle Wrapper targeting Gradle 9.7.1;
- make clone → build → test → commit → push → PR → merge → sync operationally documented;
- route AI sessions through the exact terminal/Git command document.

Documentation portion completed:
- `AI_START_HERE.md` updated;
- `docs/project-management/11-TERMINAL-GIT-COMMANDS.md` expanded.

Wrapper generation remains pending because the current Windows environment has no system Gradle and the repository has no Wrapper yet. The next action is to provision a compatible Gradle 9.7.1 executable and generate the official Wrapper; do not hand-create the Wrapper JAR.


## BASE-ARCH-014-T01 — Gradle 9.7.1 Authority Update — 2026-09-23

**State:** IMPLEMENTING

- Authoritative CB-Partner Gradle version: **9.7.1**.
- Dedicated Gradle source of truth: docs/build/GRADLE-SETUP-AND-WORKFLOW.md.
- AI_START_HERE.md and 11-TERMINAL-GIT-COMMANDS.md route Gradle setup/commands to the dedicated document.
- 9.5.0 is superseded historical context, not the selected project version.
- Remaining: provision Gradle 9.7.1 on Windows, generate the official Wrapper, verify it, rerun the build/test baseline, and record actual results.
