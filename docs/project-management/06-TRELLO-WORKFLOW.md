# CB-Partner — Trello Workflow

**Status:** PROPOSED
**Scope:** Frontend Trello workspace only

## Purpose
Trello is the execution board. It answers what we are doing, why, status, and remaining work. It is not the source of truth for architecture or permanent decisions.

## Lists
1. BACKLOG
2. DISCOVERY
3. READY
4. IN PROGRESS
5. AI REVIEW
6. DEV REVIEW
7. TESTING
8. BLOCKED
9. DONE

## Card Types
Feature, Bug, Technical Task, Research, Architecture, Documentation, Maintenance.

## Card Template
Goal
Context
Scope
Out of Scope
Frozen Decisions / ADRs
Implementation Plan
Acceptance Criteria
Testing / Verification
Dependencies / Blockers
Branch
Related Documents

## Workflow
BACKLOG → DISCOVERY → READY → IN PROGRESS → AI REVIEW → DEV REVIEW → TESTING → DONE.
BLOCKED can be entered whenever progress is prevented by a real blocker.

## Definition of Ready
Requirement understood; required decisions resolved; scope explicit; acceptance criteria measurable; plan approved where required; dependencies identified.

## Definition of Done
Acceptance criteria pass; relevant checks pass; review complete; no unauthorized changes; documentation updated; Git work complete.

## Rules
- Never create a card for the entire application.
- Split work into independently reviewable outcomes.
- Link ADRs instead of duplicating architecture.
- Mark blockers explicitly.
- DONE means verified, not merely coded.

**This document is PROPOSED until explicitly frozen.**
## State Synchronization
Trello is execution tracking, not the technical source of truth. Each meaningful implementation unit should link to its Tracker/module references and record branch, acceptance criteria, testing state, and completion/freeze state. Synchronize Trello when a unit is selected, implemented, accepted/frozen, blocked, or completed.

## No Automatic Next Task
After a task reaches its required freeze/completion state, stop. Do not automatically start the next Trello card.
