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


## Frontend Trello Board Ownership

The authoritative frontend execution board is:

- Board: **CB-Partner — Frontend**
- Workspace: **CarBroz**
- Board URL: https://trello.com/b/QcuvaDbi/cb-partner-frontend

The previous **CarBroz Test** board was temporary and must not be used for CB-Partner frontend execution tracking. Existing active cards should be migrated to the authoritative frontend board when applicable.

## AI Responsibility for Trello Execution Tracking

For CB-Partner frontend work, the AI is responsible for maintaining Trello execution tracking as part of the project workflow. The AI must:

- create the Trello card when a concrete implementation/research task reaches the point where execution tracking is required;
- choose the card name using the project work-item identifier and concise task name;
- populate the card with goal, context, scope, out of scope, frozen decisions/ADRs, implementation plan, acceptance criteria, testing/verification, dependencies/blockers, branch, and related documents when applicable;
- place the card in the correct workflow list;
- update the card/list state when the work transitions through READY, IN PROGRESS, AI REVIEW, DEV REVIEW, TESTING, BLOCKED, and DONE;
- keep the branch and durable-document references synchronized with the card;
- never create duplicate cards for the same active work item unless explicitly required;
- stop after a work item reaches its required freeze/completion state and wait for the next authorized task.

The user remains the authority for project decisions, scope approval, architecture freezes, and implementation authorization. Trello maintenance is an execution-record responsibility of the AI and must not be treated as permission to make architectural or product decisions.

## Trello Board Standard

The frontend board should use the following workflow lists:

BACKLOG → DISCOVERY → READY → IN PROGRESS → AI REVIEW → DEV REVIEW → TESTING → DONE

BLOCKED may be used whenever progress is prevented by a real blocker.
