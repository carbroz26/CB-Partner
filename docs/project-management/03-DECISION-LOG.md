# CB-Partner — Decision Log

**Status:** ACTIVE — Base Architecture decisions frozen/documented through BASE-ARCH-014; implementation plan frozen for BASE-ARCH-014; TRELLO-001 frozen.

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

## TRELLO-001 Freeze Record
**Decision:** Frontend Trello Operating Model  
**State:** DECISION_FROZEN  
**Owner approval:** Explicitly approved by the project owner on 2026-09-22.  
**Durable record:** docs/project-management/06-TRELLO-WORKFLOW.md

### Frozen model
- One authoritative frontend board: **CB-Partner — Frontend**.
- Workflow lists: **BACKLOG → READY → IN PROGRESS → DONE**.
- DISCOVERY, AI REVIEW, DEV REVIEW, TESTING, and BLOCKED are not permanent lists.
- Blocked work uses a Blocked label and explanation while remaining in its actual workflow stage.
- Trello tracks execution state; GitHub tracks code/branches/PRs; project docs track decisions/plans; the Project Tracker tracks durable project state.
- Cards use concise human-readable action/outcome names.
- Card descriptions remain lightweight: What, Done when, References, with additional context only when useful.
- Checklists are for concrete execution steps, not architecture records.
- Labels describe meaningful metadata, not workflow states.
- READY requires sufficient scope, decisions, plan/dependencies, acceptance criteria, and authorization where required.
- DONE requires acceptance, required checks/review, documentation/status updates, and Git/process completion.
- AI maintains execution tracking; the project owner retains decision, scope, freeze, and implementation authority.
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

No application source code has been implemented.

TRELLO-001 is now frozen and its authoritative board workflow is being synchronized.
