# CB-Partner — Project Tracker

**Status:** PROPOSED
**Scope:** Frontend repository only
**Purpose:** Single execution ledger for recovering and tracking the current project state across AI sessions.

## How to Use
AI_START_HERE.md is the router. This tracker is the persisted state. Detailed technical truth remains in Decision Log, Architecture, Module Implementation Plans and Module Status documents. Trello tracks execution externally; Git tracks implementation history.

## Current Project State
- Phase: 0 — Documentation and process preparation
- Current module: None
- Current work item: Documentation system review
- Workflow state: DOCUMENTATION_REVIEW
- Current implementation unit: None
- Frozen decisions: None
- Blockers: None
- Last completed action: Workflow/Git control documents updated
- Next valid action: Review and freeze the documentation operating system
- Last updated: 2026-09-16

## State Vocabulary
DISCUSS → RESEARCH → DECIDE → DECISION_FROZEN → PLAN → PLAN_FROZEN → READY → IMPLEMENTING → IMPLEMENTED → VERIFIED → COMMITTED → PUSHED → PR_OPEN → REVIEWED → ACCEPTED → CODE_FROZEN → MERGED → SYNCED → CHECKPOINTED → COMPLETE

A state transition must satisfy its gate. Never skip a state merely because the code appears finished.

## Work Item Register
| ID | Module/Feature | Unit | State | Plan | Status | Branch | Commit | PR | Tag | Trello | Blocker | Next Action |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| DOC-001 | Project Operating System | Documentation system | DOCUMENTATION_REVIEW | 00-PROJECT-OPERATING-SYSTEM | — | — | — | — | — | — | None | Review/freeze documents |

## Module Register
| Module | Plan | Status | Current Unit | State | Branch | Frozen Units | Pending Units | Last Commit | Trello |
|---|---|---|---|---|---|---|---|---|---|

## Mandatory Transition Records
When a decision freezes: Decision Log + relevant plan + Tracker.
When a plan freezes: plan + Tracker + Trello.
When a unit is selected: Module Status + Tracker + Trello.
When code changes: Module Status + Tracker.
When tests run: Module Status + Tracker + result.
When reviewed/accepted: Module Status + Tracker.
When merged: Tracker + relevant status + Git state.
When synced locally: Tracker when applicable.
When a checkpoint is created: Checkpoints + Current Status + Tracker.

## Recovery Algorithm
1. Read AI_START_HERE.md.
2. Read this Tracker.
3. Read Current Status.
4. Identify current module/work item and state.
5. Read the relevant module Plan and Status.
6. Inspect Git branch/status/log and relevant Trello card.
7. Reconcile contradictions before continuing.
8. Determine exactly one next valid action.
9. Do not implement unless the state and authorization permit it.

## Change History
Do not erase history. Record material state transitions and references in the owning status documents and Git history.

## Freeze Rule
After a meaningful freeze, update all required records, report the frozen state, return one next prompt and stop. Do not automatically select the next unit.
