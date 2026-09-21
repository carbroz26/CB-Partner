# CB-Partner — Decision Log

**Status:** ACTIVE — Base Architecture decisions frozen/documented through BASE-ARCH-013

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

## Acceptance

A decision is not accepted because AI recommends it. The project owner explicitly accepts it, then it becomes frozen.

## Reopening

Use concrete evidence: changed requirements, incompatibility, security issue, unacceptable performance, maintenance problem, or incorrect assumption. Record evidence and impact before replacing a decision.

## Current Documentation Checkpoint

BASE-ARCH-013 is now formally accepted and decision-frozen. Option A (direct Domain-facing ApplicationBootstrap contract) was selected by the project owner. The initial bootstrap does not require a separate use-case/repository abstraction chain. Implementation remains unauthorized until the separately frozen implementation plan exists.

**Repository source implementation remains unauthorized at this checkpoint.**
