# CB-Partner — Decision Log

**Status:** ACTIVE — Base Architecture decisions frozen/documented through BASE-ARCH-014; implementation plan frozen for BASE-ARCH-014; TRELLO-002 frozen.

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

TRELLO-001 is frozen. The Trello board has been migrated to the TRELLO-002 simple work model. 014-01 and 014-02 are represented as completed work; 014-03 is prepared in READY. Remaining approved work is represented as human-readable TO DO cards.
