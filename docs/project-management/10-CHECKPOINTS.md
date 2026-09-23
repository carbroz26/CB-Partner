# CB-Partner — Checkpoints

**Status:** PROPOSED

## Purpose
Checkpoints are recovery anchors. They make experimentation safe because a known-good project state is always recoverable.

## When to Create
Create a checkpoint after meaningful stable milestones, especially after project foundation, architecture validation, first vertical slice, and release-ready milestones.

## Record
- Checkpoint ID
- Date
- Git commit
- Milestone
- Completed work
- Verification performed
- Known limitations
- Documentation state
- Recovery notes if relevant

## Naming
CP-001, CP-002, CP-003, etc.

## Initial Milestones
CP-001 Project Constitution Frozen
CP-002 Architecture Frozen
CP-003 Build/Repository Foundation
CP-004 Core Technical Foundation
CP-005 First Vertical Slice
CP-006 First Release Candidate

These are placeholders, not commitments.

## Recovery
When something breaks: stop → inspect status/diff → identify last known-good checkpoint → classify failure → fix forward or revert deliberately → verify → create a new checkpoint when stable.

Never delete the project merely because a checkpoint is easier to recover from.

**This document is PROPOSED until explicitly frozen.**
## Checkpoint and Git State
A checkpoint records exact Git commit and, when applicable, branch/tag/PR state, plus Tracker/module status so a new AI session can recover without conversation history.


## CP-003 — Build/Repository Foundation Checkpoint

**State:** ESTABLISHED  
**Date:** 2026-09-23  
**Git branch:** feature/base-arch-014  
**Checkpoint baseline commit:** 730ecbab247214b803d7a047ade93a7bb906efd2

### Milestone
BASE-ARCH-014-07 — Dependency and Module Wiring accepted and checkpointed.

### Completed work
- BASE-ARCH-014 units 014-01 through 014-07 are verified and accepted.
- Foundation module dependency wiring matches the frozen BASE-ARCH-002 dependency graph.
- Android KMP namespace configuration is recorded under the frozen 014-06 platform boundary.
- The namespace correction does not change module boundaries or 014-07 dependency decisions.
- No business logic, SDUI internals, fake bootstrap, fake JSON, Store/MVI implementation, DI implementation, or unrelated refactoring was introduced.

### Verification performed
- Five commonMainImplementation dependency reports completed with **BUILD SUCCESSFUL**.
- JVM compilation completed successfully for :core, :domain, :data, :navigation, :feature:splash, and :feature:dynamic.
- Android KMP task/configuration discovery succeeds after the namespace correction.
- Windows iOS simulator disabled-target warning remains an expected host limitation.

### Known limitations
- iOS simulator execution is not available on the Windows development host.
- 014-08 Build/Test Baseline has not been started.
- 014-09 Architecture Verification has not been started.
- The checkpoint records the Git state on feature/base-arch-014; no merge to development is implied.

### Documentation state
- BASE-ARCH-014 implementation plan remains PLAN_FROZEN.
- BASE-ARCH-014 implementation status records 014-07 as ACCEPTED.
- Current Status and Project Tracker record the 014-07 acceptance checkpoint.
- Decision Log records the 014-07 acceptance and 014-06 namespace boundary decision.

### Recovery
For recovery, start from this checkpoint commit on feature/base-arch-014, then read AI_START_HERE.md, the Project Tracker, Current Status, BASE-ARCH-014 implementation plan/status, and the architecture record before any further action.

**Checkpoint result:** stable and ready for a separate 014-08 authorization gate.


## CP-004 — BASE-ARCH-014 Foundation Freeze

**State:** ESTABLISHED
**Date:** 2026-09-23
**Git branch:** `feature/base-arch-014`

### Milestone
BASE-ARCH-014 — Minimum Gradle/KMP/Compose/Build-Logic Foundation — owner accepted and code frozen.

### Completed work
- 014-01 through 014-07 accepted.
- 014-08 build/test baseline verified.
- 014-09 architecture verification completed; F02 resolved.
- T01 Gradle 9.7.1 Wrapper and Wrapper-based baseline verified.
- Native iOS/Xcode application boundary established without a `:iosApp` Gradle module.

### Verification
Shared JVM tests and compilation passed; build-logic and project hierarchy checks passed. Windows iOS simulator/Xcode execution remains a host limitation.

### Known limitations
- Native Xcode/macOS build verification has not been executed on the Windows host.
- No new BASE-ARCH-014 implementation work is permitted after this freeze.

### Documentation state
Tracker, Current Status, Decision Log, and BASE-ARCH-014 implementation status record owner acceptance and code freeze.

### Recovery
Start from the frozen `feature/base-arch-014` branch state, then read `AI_START_HERE.md`, Tracker, Current Status, BASE-ARCH-014 plan/status, and architecture records before any new action.

### Next action
Prepare PR/merge from `feature/base-arch-014` to `development`.


## CP-004 PR Review State — 2026-09-23

**State:** PR OPEN — REVIEW PENDING
**PR:** #2 — `feature/base-arch-014` → `development`

The frozen BASE-ARCH-014 implementation is submitted for review. No further implementation changes are authorized until the review/merge gate is resolved.
