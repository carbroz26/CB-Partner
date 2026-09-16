# CB-Partner — Project Operating System

**Status:** PROPOSED — under review  
**Scope:** Frontend repository only  
**Purpose:** The operating constitution for planning, discussing, researching, deciding, implementing, testing, reviewing, freezing, documenting, and evolving CB-Partner.

> **Authority rule:** This document defines the development process. Technical decisions belong in the Decision Log and Architecture documentation. Trello manages execution. Git manages code history. No single conversation is the permanent source of truth.

## 01. Project Boundary
CB-Partner is the frontend application for the partner/service-provider side of the doorstep car-washing platform. This repository is frontend only. The backend is a separate project, repository, documentation set, and Trello workspace.

## 02. Project Goal
Build progressively as a maintainable, testable, production-oriented application rather than repeatedly rebuilding from scratch. The goal is a reliable product through controlled increments, not maximum architecture or code volume.

## 03. Core Technical Direction
Current intended direction:
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Pure Store-based MVI
- UDF
- No ViewModel
- Multi-module architecture
- Dependency Injection
- Gradle Convention Plugins
Specific libraries and detailed designs become frozen only through accepted decisions.

## 04. Progressive Development
Each completed feature should leave the repository in a usable and understandable state. Do not wait for a theoretically perfect architecture before producing working software.

## 05. No Restart / No Scratch Rule
A problem is not automatically a reason to create a new project. Identify scope, blocking impact, smallest safe correction, focused task, and recovery point. Full restart requires explicit justification and approval.

## 06. Complexity Budget
Every abstraction, module, dependency, framework, pattern, or infrastructure component needs a current reason. Future possibilities alone are not sufficient justification.

## 07. AI Is a Collaborator, Not the Architect
AI may research, explain, propose, inspect, implement approved work, test, review, diagnose, and update documentation. The human owner approves significant product and architecture decisions.

## 08. Repository Modification Permission
DISCUSS, RESEARCH, DECIDE, and PLAN do not authorize code modification. Repository modification happens only in an explicit IMPLEMENT phase after plan approval.

## 09. Source-of-Truth Hierarchy
When information conflicts:
1. Explicit current user decision
2. Accepted/frozen project decision
3. Current architecture documentation
4. Current feature/implementation documentation
5. Current repository code and tests
6. Trello execution state
7. Previous conversation history
8. AI assumptions

Conversation memory is context, never stronger than a current documented decision.

## 10. Documentation Is Project Memory
The project must remain understandable after a conversation ends, context changes, a new AI agent is used, or months pass. Important knowledge belongs in repository documentation.

## 11. AI Session Start Protocol
Before substantial work AI must identify the repository; read AI Guidelines, AI Context, AI Rules, Current Status, relevant accepted decisions, and relevant architecture/feature documents; inspect current code; and inspect Git status before modifying anything.

## 12. Conversation Modes
Use explicit modes:
- DISCUSS — explore without coding.
- RESEARCH — investigate evidence without coding.
- DECIDE — finalize a decision without coding.
- PLAN — create an implementation contract without coding.
- IMPLEMENT — execute an approved contract.
- TEST — validate implementation.
- REVIEW — inspect against the contract.
- STATUS — report project state.
- DOCUMENT — update authoritative records.

## 13. Prompt Contract
Prompts should identify:
START: mode, objective, task/card, constraints, documents to follow, permission to modify repository.
CONTEXT: current requirement, existing behavior, decisions, constraints, expected output.
END: deliverable, acceptance criteria, explicit stop condition, and whether approval is required before proceeding.
This makes AI work reproducible across sessions.

## 14. Discussion Phase
DISCUSS clarifies requirements, assumptions, constraints, trade-offs, and questions. It produces understanding and candidate directions, not code.

## 15. Research Phase
When evidence is needed, prioritize official documentation, official repositories, high-quality production examples, respected engineering sources, and community discussion when useful. Distinguish facts, recommendations, project reasoning, and assumptions. Research does not automatically become a decision.

## 16. Decision Phase
A decision follows understanding of the problem and alternatives. Significant decisions record context, decision, alternatives, rationale, consequences, migration implications, and related decisions in the Decision Log.

## 17. Decision Freeze
Explicit user acceptance makes a significant decision ACCEPTED/FROZEN. AI must follow it. A conflict must be reported. Reopening requires explicit change discussion.

## 18. Plan Phase
Before meaningful implementation create an implementation contract covering objective, scope, out of scope, affected modules/files, data/state flow, API implications, architecture implications, steps, testing, acceptance criteria, risks, and documentation changes.

## 19. Plan Freeze
The plan is frozen only after explicit approval. Implementation follows it. If a material problem is discovered, stop and update/re-approve the plan rather than silently redesigning.

## 20. Trello Execution
Every meaningful implementation task should have a frontend Trello card. Trello tracks task, status, scope, acceptance criteria, branch, decision references, plan, and testing. It does not replace architecture or decision documents.

## 21. Definition of Ready
A task is READY when the requirement is understood, scope and out-of-scope are explicit, required decisions are frozen, dependencies are known, and acceptance criteria exist.

## 22. Branch Discipline
Normal feature work happens on a focused branch. Typical flow: main → feature branch → review → main. Do not normally develop directly on main.

## 23. Implementation Phase
Implementation executes the frozen plan. Stay within scope, follow accepted architecture, use existing patterns where appropriate, avoid speculative abstractions, preserve unrelated work, and keep changes reviewable.

## 24. Deviation Protocol
If implementation requires a material deviation: stop; identify the affected requirement/decision/plan; explain the problem; propose the smallest change; obtain approval; update the appropriate document; then continue. No silent architectural drift.

## 25. Feature Slicing
Prefer small vertical slices that prove real product behavior. Avoid enormous tasks such as building the whole partner application in one iteration. The first technical vertical slice should be deliberately small so architecture is validated against reality.

## 26. Store / MVI / UDF Rule
State management is pure Store-based MVI with UDF:
UI Event → Store → State/Effect → UI.
The Store is the state-management boundary. UI renders state and emits events. Business logic does not live inside composables. ViewModel is explicitly not part of this architecture. The exact Store contract must be frozen before the shared state-management foundation is implemented.

## 27. Multi-Module Rule
Modules exist for meaningful boundaries such as dependency isolation, ownership, build boundaries, reusable capability, platform separation, or feature isolation. Do not maximize module count merely because a Clean Architecture diagram contains many boxes.

## 28. Platform Rule
Share code where it creates genuine value. Keep platform-specific behavior explicit and localized. Android, iOS, and Desktop are first-class targets where configured. Do not force platform behavior into unnatural abstractions.

## 29. Dependency Rule
Before adding a material dependency evaluate the actual problem, multiplatform support, maintenance, maturity, testing, build/binary impact, alternatives, and project fit. Popularity alone is not justification.

## 30. API Boundary Rule
UI does not directly perform networking. Transport concerns belong in the data/integration boundary. DTOs are not universal application models. Domain/application logic remains independent of transport implementation. The backend repository remains authoritative for the backend API contract.

## 31. Testing Gate
Testing is part of implementation. Depending on the change, use unit tests, Store/state-transition tests, domain tests, data/API mapping tests, integration tests, UI tests, platform compilation/build checks, and manual verification where appropriate. A successful build alone is not completion.

## 32. Review Gate
Review compares actual implementation with frozen requirements, plan, architecture, rules, tests, and Git diff. Findings are BLOCKER, REQUIRED, OPTIONAL, or FUTURE. Optional improvements do not automatically block completion.

## 33. Code Freeze
Code freeze occurs when acceptance criteria pass, required checks pass, review is complete, required findings are resolved, no unauthorized scope changes remain, and required documentation is updated. No unrelated cleanup is added after this point.

## 34. Commit / Merge Gate
Before commit inspect diff/status, remove unrelated changes, run applicable validation, and use a meaningful commit. Before merge push, review, pass checks, verify scope, and use the approved Git workflow.

## 35. Documentation Update Gate
After accepted implementation update only authoritative documents affected by the change: architecture decisions, current status, roadmap/checkpoints, API contract, workflow rules, or feature documentation as appropriate. Avoid duplicate sources of truth.

## 36. Checkpoint / Recovery System
Major stable milestones receive a checkpoint with ID, date, Git commit, milestone, completed work, verification, known limitations, and documentation state. When something breaks, return to a known-good checkpoint if necessary. The checkpoint system exists to prevent restarting from zero.

## 37. Change Management / Reopening Frozen Decisions
A frozen decision may be reopened for concrete evidence such as a new requirement, technical limitation, unacceptable performance, platform incompatibility, security issue, significant maintenance problem, or incorrect original assumption.
Process: Problem → Evidence → Impact → Alternatives → Decision → Updated ADR → Migration Plan → Implementation.

## 38. Definition of Done + Anti-Drift Rule
A task is DONE only when behavior exists, scope is satisfied, acceptance criteria pass, relevant validation passes, frozen decisions are followed, review is complete, required documentation is updated, Git state is understood, and commit/merge is complete as required.

If work drifts from requirement, plan, architecture, or scope:
STOP → IDENTIFY DRIFT → DOCUMENT → DECIDE → UPDATE PLAN IF APPROVED → CONTINUE.

Never solve project uncertainty by silently writing more code.

# Standard Project Lifecycle
IDEA → DISCUSS → RESEARCH → DECIDE → DECISION FREEZE → PLAN → PLAN FREEZE → TRELLO READY → BRANCH → IMPLEMENT → TEST → REVIEW → ACCEPT → CODE FREEZE → COMMIT → MERGE → DOCUMENT → CHECKPOINT.

Small changes may skip unnecessary phases, but the principles of scope, decision control, validation, and traceability still apply.

# Golden Rule
**Never let the speed of AI exceed the speed of human understanding.**

AI can generate code extremely quickly. This process exists so code generation does not become uncontrolled architecture generation, and architecture changes do not become repeated project restarts.

**Document status remains PROPOSED until the user reviews and explicitly freezes it.**
