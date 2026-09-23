# CB-Partner — Decision Log

**Status:** ACTIVE — Base Architecture decisions frozen/documented through BASE-ARCH-014; BASE-ARCH-014 implementation verified pending final owner acceptance/code freeze; TRELLO-002 frozen.

## Purpose
Durable record of significant project decisions. It prevents the project from depending on old conversations to remember why a choice was made.

## Decision Classes
- Architecture
- Technology
- Product/technical contract
- API integration
- Quality/security
- Process

## Status
PROPOSED → ACCEPTED or REJECTED.  
ACCEPTED → SUPERSEDED only through the reopening process.

## Architecture Decision Record
Detailed Base Architecture research/freeze record:
docs/architecture/BASE-ARCHITECTURE.md

## Base Architecture Status
BASE-ARCH-001 through BASE-ARCH-014 → DECISION_FROZEN.

## Acceptance
A decision is not accepted because AI recommends it. The project owner explicitly accepts it, then it becomes frozen.

## Reopening
Use concrete evidence: changed requirements, incompatibility, security issue, unacceptable performance, maintenance problem, or incorrect assumption. Record evidence and impact before replacing a decision.

## TRELLO-001 Freeze Record — Superseded
**Decision:** Earlier Frontend Trello Operating Model  
**State:** SUPERSEDED  
**Superseded by:** TRELLO-002

The earlier model was replaced because the project owner clarified that Trello should remain a simple human-readable work board rather than a process/document tracker. The historical decision is retained here; the current model is documented in `docs/project-management/06-TRELLO-WORKFLOW.md`.

## TRELLO-002 Freeze Record
**Decision:** Simple Frontend Work Board  
**State:** DECISION_FROZEN  
**Owner approval:** Explicitly approved by the project owner on 2026-09-22.  
**Durable record:** docs/project-management/06-TRELLO-WORKFLOW.md

### Frozen model
- One authoritative frontend board: **CB-Partner — Frontend**.
- Lists: **BACKLOG → TO DO → READY → IN PROGRESS → DONE**.
- BACKLOG means possible/future work not yet committed.
- TO DO is the complete list of approved work that has been decided to be done.
- READY means approved and prepared work.
- IN PROGRESS means work actually being performed.
- DONE means work actually completed and verified.
- Cards represent actual human-readable project work, not architecture IDs or documents.
- Card structure is simple: title, short description, and concrete checklist/subtasks where useful.
- Trello does not contain architecture history, AI workflow, Git details, implementation plans, or document references as required card content.
- Project documentation remains responsible for architecture, decisions, technical constraints, implementation contracts, and AI/project process.
- GitHub remains responsible for source code and Git workflow.
- The AI maintains Trello execution state but does not invent project scope or architecture decisions.
- No automatic start of the next task after DONE.

This process decision does not change any frozen technical architecture decision.

## BASE-ARCH-014 Freeze Record
**Decision:** Minimum Gradle/KMP/Compose/Build-Logic Foundation  
**State:** DECISION_FROZEN  
**Owner approval:** Explicitly approved by the project owner on 2026-09-22.  
**Implementation plan:** docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md  
**Implementation plan state:** PLAN_FROZEN  
**Implementation authorization:** Granted through the approved unit-by-unit implementation workflow.

### Frozen foundation direction
- Included build-logic.
- Minimal type-oriented convention strategy.
- Central version catalog.
- Current KMP/AGP configuration.
- Default KMP source-set hierarchy unless concrete justification exists.
- Compose only where consumed.
- Minimal repeated testing/build baseline.
- Initial targets: Android, iosArm64, iosSimulatorArm64, JVM.
- Initial conventions: KMP shared-library, Compose Multiplatform, Android application, Desktop application.
- Foundation-required dependencies only; runtime architecture libraries are introduced when their real implementation unit has a consumer.
- No fake bootstrap or fake JSON.
- Later startup vertical slice uses the actual backend API and exact response supplied by the project owner.

### Implementation gate
The plan is frozen, but implementation remains unit-by-unit:
014-01 Gradle Project Skeleton → 014-02 Version Catalog → 014-03 Build Logic → 014-04 KMP Targets → 014-05 Compose → 014-06 Platform Boundaries → 014-07 Dependency Wiring → 014-08 Build/Test Baseline → 014-09 Architecture Verification.

No unit may silently change a frozen architecture decision.

## Current Documentation Checkpoint
BASE-ARCH-014 implementation planning is complete and the plan is frozen. The durable implementation plan and living status document have been created.

014-02 — Version Catalog was verified locally and explicitly accepted by the project owner on 2026-09-22. Acceptance authorizes progression to the next unit under the frozen plan; it does not authorize bypassing the required Trello READY/implementation preflight for 014-03.

No application source code has been implemented.

TRELLO-002 is frozen. TRELLO-001 is superseded. The Trello board has been migrated to the TRELLO-002 simple work model. 014-01 and 014-02 are represented as completed work; 014-03 is prepared in READY. Remaining approved work is represented as human-readable TO DO cards.


## BASE-ARCH-014-07 Acceptance Record

**Decision:** BASE-ARCH-014-07 — Dependency and Module Wiring  
**State:** ACCEPTED  
**Date:** 2026-09-23

The project owner supplied and reviewed the required local verification results. The implementation preserves the frozen dependency graph:

- :domain → :core
- :data → :domain, :core
- :navigation → :core
- :feature:splash → :domain, :core, :navigation
- :feature:dynamic → :domain, :core, :navigation

The five dependency reports and targeted JVM compilation all completed successfully. No out-of-scope runtime architecture or business implementation was introduced.

## BASE-ARCH-014-06 Namespace Boundary Record

**State:** ACCEPTED / FROZEN  
**Date:** 2026-09-23

The Android KMP namespace requirement is owned by the 014-06 platform boundary. The approved convention is deterministic and centrally derived from the Gradle module path using com.carbroz.cbpartner as the base package.

This correction resolved the Android KMP configuration failure without changing the 014-07 dependency decision, module boundaries, target matrix, or other BASE-ARCH-014 decisions.

The detailed namespace mapping is recorded in docs/architecture/BASE-ARCHITECTURE.md and the BASE-ARCH-014 implementation records.


## BASE-ARCH-014-09 Platform Boundary Clarification — 2026-09-23

**Decision:** Platform application boundary is distinct from Gradle module boundary.
**State:** DECISION_FROZEN
**Owner approval:** Explicitly approved by the project owner on 2026-09-23.
**Durable records:** docs/architecture/BASE-ARCHITECTURE.md; docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md; docs/architecture/BASE-ARCH-014-IMPLEMENTATION-STATUS.md

### Frozen rule
Every supported platform has an explicit application boundary, but not every platform application boundary is a Gradle module.

- Android: `androidApp/` is the platform application boundary and Gradle project `:androidApp`.
- Desktop: `desktopApp/` is the platform application boundary and Gradle project `:desktopApp`.
- iOS: `iosApp/` is the platform application boundary owned by Xcode and is not a Gradle project.

Therefore `:iosApp` must not be added to `settings.gradle.kts`, and no synthetic iOS Gradle convention is to be created merely for platform symmetry.

This clarification preserves application-boundary symmetry while allowing each platform to use its native build-system boundary. It does not reopen or change the selected KMP, Clean Architecture, MVI/UDF, Pure Store, no-ViewModel, multi-module, Navigation, DI, or dependency-direction decisions.

### 014-09 consequence
The previous 014-09 finding that treated the absence of `:iosApp` from the Gradle hierarchy as a discrepancy is superseded by this clarification. A fresh 014-09 verification must instead verify the actual `iosApp/` native/Xcode application boundary.


## BASE-ARCH-014-09 F02 Resolution Record — 2026-09-23

**Finding:** BASE-ARCH-014-09-F02 — missing native iOS/Xcode application boundary.
**State:** RESOLVED / VERIFIED.
**Owner authorization:** Explicitly granted on 2026-09-23.

The repository previously contained only iosApp/.gitkeep. The authorized correction established the native Xcode application boundary under iosApp/ without creating a Gradle :iosApp project or an iOS Gradle convention.

The fresh 014-09 verification confirmed:
- native Xcode project boundary exists;
- iOS remains outside the Gradle module hierarchy;
- Android and Desktop retain their Gradle application boundaries;
- shared module structure and frozen dependency direction remain unchanged;
- no prohibited architecture or business/SDUI scope was introduced.

The Windows verification host cannot execute Xcode/macOS builds. That limitation is recorded as an environment limitation, not as a repository architecture failure.

No new architecture finding was identified.


## BASE-ARCH-014-T01 — Gradle Wrapper and Git/Build Operational Workflow — 2026-09-23

**State:** DECISION_FROZEN
**Owner authorization:** Explicitly granted on 2026-09-23.

### Frozen operational rule
The project must have one recoverable operational path for repository lifecycle and build verification. The authoritative Git/terminal/build reference is `docs/project-management/11-TERMINAL-GIT-COMMANDS.md`, and `AI_START_HERE.md` must route AI sessions to it.

The required lifecycle is:

CLONE → VERIFY → FETCH → BRANCH → PULL/UPDATE → IMPLEMENT → BUILD/TEST → REVIEW → COMMIT → PUSH → PR/REVIEW → MERGE → FETCH/SYNC → BUILD/TEST.

The repository must use the official Gradle Wrapper for normal builds. BASE-ARCH-014-T01 targets Gradle 9.7.1 because the repository currently uses Android Gradle Plugin 9.3.0, whose documented minimum/default compatible Gradle version is 9.7.1.

This decision does not change the application architecture. It is a repository tooling and development-process decision.

### Implementation state
The documentation/routing portion and the official Wrapper implementation are complete. The Wrapper was generated by the Gradle wrapper task; its JAR was not manually fabricated. T01 is VERIFIED.


## BASE-ARCH-014-T01 Verification Record — 2026-09-23

**State:** VERIFIED

The official Gradle 9.7.1 Wrapper was generated, committed, and verified. Wrapper-based project hierarchy, build-logic build, shared JVM tests, and shared JVM compilation all completed successfully. The Windows iOS simulator disabled-target warning is an expected host limitation and was not suppressed.

The authoritative operational document records the verified baseline and Wrapper state. T01 is complete; no further tooling implementation is pending.


## BASE-ARCH-014-T01 Version Correction — 2026-09-23

**Authoritative Gradle version:** **9.7.1**

The earlier 9.5.0 wording in T01 was superseded by the project owner's explicit clarification that CB-Partner was using Gradle 9.7.1. The selected Wrapper target is therefore 9.7.1.

The single Gradle operational source of truth is docs/build/GRADLE-SETUP-AND-WORKFLOW.md. It covers Windows setup, Wrapper generation, commands, troubleshooting, version history, and the upgrade procedure.

9.5.0 remains only as historical context for the earlier compatibility-based proposal; it is not the CB-Partner baseline.


## BASE-ARCH-014 Final Implementation Checkpoint — 2026-09-23

**State:** IMPLEMENTATION VERIFIED — OWNER ACCEPTANCE PENDING

The complete implementation record has been reconciled with the repository state on `feature/base-arch-014`:
- 014-01 through 014-07 accepted;
- 014-08 verified;
- 014-09 verified and F02 resolved;
- T01 verified with Gradle 9.7.1 Wrapper and Wrapper-based baseline;
- no new architecture finding identified.

This checkpoint does not itself freeze the completed implementation. Final BASE-ARCH-014 code freeze remains subject to explicit project-owner acceptance.
