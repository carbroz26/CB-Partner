# CB-Partner — Trello Workflow

**Status:** FROZEN  
**Scope:** Frontend Trello workspace only  
**Frozen:** 2026-09-22  
**Decision:** TRELLO-001 — Frontend Trello Operating Model

## Purpose
Trello is the visual execution tracker for the CB-Partner frontend. It answers what work exists, what is ready, what is currently being worked on, and what is complete.

Trello is not the source of truth for architecture decisions, research conclusions, implementation contracts, detailed implementation plans, or source code. Those remain in project documentation and GitHub.

## Authoritative Board
- Board: **CB-Partner — Frontend**
- Workspace: **CarBroz**
- URL: https://trello.com/b/QcuvaDbi/cb-partner-frontend

The previous **CarBroz Test** board is non-authoritative and must not be used for CB-Partner frontend execution tracking.

## Board Lists
**BACKLOG → READY → IN PROGRESS → DONE**

### BACKLOG
Known work that is not currently ready to start.

### READY
Work that has passed the required preparation and authorization gates and may be started.

### IN PROGRESS
The currently active work.

### DONE
The work item has completed its required acceptance, verification, review, documentation, and Git/process requirements. DONE does not mean merely “code was written.”

## What Is Not a Permanent List
The following are not permanent workflow lists:
- DISCOVERY
- AI REVIEW
- DEV REVIEW
- TESTING
- BLOCKED

They are handled by the project operating process, card metadata, labels, checklists, comments, documents, and Tracker state as appropriate.

A dedicated list may be introduced later only if a real operational handoff or queue repeatedly requires it.

## Blocked Work
Blocked work remains in the list representing its actual workflow stage and receives a **Blocked** label plus a clear explanation of what is blocked, why it is blocked, and what dependency or decision is required.

A BLOCKED list is not required initially.

## Card Naming
Trello titles are human-readable and action-oriented.

Use:
**<Direct Action or Outcome>**

Examples:
- Set Up Gradle Project Structure
- Add Version Catalog
- Configure Build Conventions
- Configure Kotlin Multiplatform Targets
- Configure Compose Multiplatform
- Set Up Platform App Modules
- Wire Module Dependencies
- Add Build and Test Baseline
- Verify Architecture Boundaries

Do not use internal identifiers such as BASE-ARCH-014 — 014-01 as the primary title. Internal IDs belong in descriptions, linked documents, branches, and the Project Tracker.

## Card Content
Cards should be lightweight enough to remain useful.

Default structure:

### What
Short description of the work.

### Done when
Concrete acceptance criteria.

### References
Links/references to the relevant architecture decision, implementation plan, Tracker, GitHub branch/PR, or other durable record.

Add context, scope, dependencies, or testing notes only when they materially help execution. Do not duplicate entire architecture documents or implementation plans inside Trello cards.

## Checklists
Use checklists for concrete execution steps when they make the card easier to complete.

Do not use checklists as a replacement for architecture decisions, implementation plans, durable project status, or the Project Tracker.

## Labels
Use labels for meaningful metadata, not workflow states.

Initial label vocabulary may include:
- Architecture
- Implementation
- Research
- Bug
- Documentation
- Blocked

Create/use a label when it provides real value. Do not create labels for Ready, Testing, Frozen, AI Review, or similar workflow states.

## Definition of READY
A card is READY when, proportional to its size:
- the work is understood;
- scope is clear;
- required architecture decisions are resolved;
- required implementation plan exists;
- dependencies are known;
- acceptance criteria exist;
- implementation authorization exists where required.

Small tasks do not require an unnecessarily large planning ceremony.

## Definition of DONE
A card is DONE when:
- the acceptance criteria pass;
- required testing/checks pass;
- required review is complete;
- required durable documentation/status is updated;
- Git/process requirements are complete for that work item;
- no unauthorized changes remain.

## Trello, GitHub, and Documentation
- **Trello:** what work exists and its execution state.
- **GitHub:** source code, branches, commits, pull requests, and code review.
- **Project docs:** why/how, architecture decisions, implementation plans, operating rules, and durable status.
- **Project Tracker:** durable project-level recovery and execution ledger.

The same work item may be referenced across these systems, but information should not be unnecessarily duplicated.

## Card Creation Rules
Create a card for a meaningful independently trackable outcome, such as an implementation unit, meaningful bug, research task, architecture/process decision, significant documentation work, or meaningful technical maintenance.

Do not create cards for individual classes/files, every commit, tiny corrections, every conversation/checklist item, or work that cannot be independently tracked.

## AI Responsibility
The AI is responsible for maintaining Trello execution tracking, including:
- creating cards when meaningful tracked work begins;
- choosing clear human-readable names;
- maintaining concise descriptions and relevant checklists;
- moving cards through the approved lists;
- applying/removing relevant labels;
- recording references to durable docs and GitHub work;
- keeping Trello aligned with actual execution state;
- marking work DONE only after its completion requirements are satisfied.

Trello maintenance does not grant the AI authority to make product or architecture decisions.

## User Responsibility
The project owner retains authority over product decisions, architecture decisions, scope approval, freezes, implementation authorization, and reopening frozen decisions.

## No Automatic Next Task
When a work item reaches its required completion/freeze state, stop. Do not automatically start the next Trello card. The next task requires an explicit authorized continuation through the project operating workflow.

## State Synchronization
Synchronize Trello when a meaningful work item becomes READY, starts, becomes blocked, reaches review/verification milestones, is accepted, or is completed.

The Project Tracker remains the durable recovery ledger.

## Frozen Decision
TRELLO-001 is the approved frontend Trello operating model. Changes to this model require a new discussion/research/decision cycle and explicit approval; do not silently alter the workflow.
