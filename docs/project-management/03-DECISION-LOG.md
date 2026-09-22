# CB-Partner — Decision Log

**Status:** ACTIVE — Base Architecture decisions frozen/documented through BASE-ARCH-014

## Purpose
This is the durable record of significant project decisions. It prevents the project from depending on old conversations to remember why a choice was made.

## Decision Classes
- Architecture: module boundaries, layers, Store contract, state flow.
- Technology: libraries, frameworks, build tooling.
- Product/technical contract: behavior affecting implementation.
- API integration: authentication, mapping, errors, client behavior.
- Quality/security: testing, privacy, reliability, release.
- Process: AI and development governance.

## Status
PROPOSED → ACCEPTED or REJECTED.
ACCEPTED → SUPERSEDED only through the reopening process.

## Architecture Decision Record

The detailed Base Architecture research/freeze record is maintained in:

`docs/architecture/BASE-ARCHITECTURE.md`

That document is the durable implementation input for BASE-ARCH-001 through BASE-ARCH-012 and records research state, accepted/frozen decisions, rejected alternatives, deferred decisions, scope exclusions, and implementation consequences.

## Base Architecture Status

- BASE-ARCH-001 → DECISION_FROZEN
- BASE-ARCH-002 → DECISION_FROZEN
- BASE-ARCH-003 → DECISION_FROZEN
- BASE-ARCH-004 → DECISION_FROZEN
- BASE-ARCH-005 → DECISION_FROZEN
- BASE-ARCH-006 → DECISION_FROZEN
- BASE-ARCH-007 → DECISION_FROZEN
- BASE-ARCH-008 → DECISION_FROZEN
- BASE-ARCH-009 → DECISION_FROZEN
- BASE-ARCH-010 → DECISION_FROZEN
- BASE-ARCH-011 → DECISION_FROZEN
- BASE-ARCH-012 → DECISION_FROZEN
- BASE-ARCH-013 → DECISION_FROZEN
- BASE-ARCH-014 → DECISION_FROZEN

## Acceptance

A decision is not accepted because AI recommends it. The project owner explicitly accepts it, then it becomes frozen.

## Reopening

Use concrete evidence: changed requirements, incompatibility, security issue, unacceptable performance, maintenance problem, or incorrect assumption. Record evidence and impact before replacing a decision.

## BASE-ARCH-014 Freeze Record

**Decision:** Minimum Gradle/KMP/Compose/Build-Logic Foundation  
**State:** DECISION_FROZEN  
**Owner approval:** Explicitly approved by the project owner on 2026-09-22.  
**Implementation authorization:** Not granted by this architecture freeze.

**Scope:** Operationalize the frozen module architecture with the minimum reproducible build foundation.  
**Core principle:** Centralize repeated build mechanics; do not turn build-logic into a second application-architecture framework.

## Current Documentation Checkpoint

BASE-ARCH-013 is formally accepted and decision-frozen. Option A (direct Domain-facing ApplicationBootstrap contract) was selected by the project owner. The initial bootstrap does not require a separate use-case/repository abstraction chain.

BASE-ARCH-014 is formally accepted and decision-frozen. The project owner approved the minimum Gradle/KMP/Compose/build-logic foundation: an included build-logic build, minimal type-oriented conventions, central version catalog, current KMP/AGP configuration, default source-set hierarchy unless justified, Compose only where consumed, and a minimal repeated testing/build baseline. Exact versions, plugin IDs, implementation details and other explicitly deferred items remain open for implementation planning.

**Repository source implementation remains unauthorized at this checkpoint.**
