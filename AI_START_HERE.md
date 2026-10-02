# CB-Partner — AI START HERE

**Document type:** Global AI Control / Router
**Status:** PROPOSED
**Scope:** Entire CB-Partner frontend repository

## 1. NON-NEGOTIABLE RULE
If this document is provided to you, read and follow it before taking project action. Do not guess what the user wants and do not begin implementation merely because the user says continue, go ahead, proceed, implement, fix, create, or build. First determine the current project and workflow state.

## 2. PROJECT IDENTITY
Project: CB-Partner
Repository: carbroz26/CB-Partner
This repository is the frontend/partner application only. Backend has a separate repository, documentation system, and Trello workspace.

## 3. DOCUMENT AUTHORITY
When information conflicts, use this order:
1. Current explicit user instruction
2. This AI_START_HERE.md
3. Accepted/FROZEN decisions
4. Frozen architecture and technical contracts
5. Current project status
6. Approved feature/module implementation plans
7. Trello execution state
8. Current source code and tests
9. Historical conversations

Historical chat must never override a current frozen repository decision. If authoritative documents conflict, STOP and request resolution.

## 4. REQUIRED DOCUMENTS
Always read:
- AI_START_HERE.md
- docs/AI_GUIDELINES.md
- docs/project-management/00-PROJECT-OPERATING-SYSTEM.md
- docs/project-management/01-AI-CONTEXT.md
- docs/project-management/02-AI-RULES.md
- docs/project-management/03-DECISION-LOG.md
- docs/project-management/04-CURRENT-STATUS.md

Read relevant documents for the task:
- roadmap, Trello workflow, development workflow, implementation protocol, review protocol, checkpoints, terminal commands
- architecture/ARCHITECTURE.md
- frontend/FRONTEND.md
- api/API_CONTRACT.md
- relevant feature/module documents

## 5. FIRST ACTION — DETERMINE WHERE WE ARE
Every session begins with state detection.

Determine:
- Session state: new, continuing, or recovered.
- Project phase from CURRENT-STATUS.
- Git branch/status when repository work is involved.
- Frozen decisions.
- Relevant Trello card.
- Existing implementation.
- Relevant feature/module documentation.
- Current workflow state.

### Global Feature Lifecycle
Every significant feature/module follows exactly three workflow phases:

1. **FEATURE DISCUSSION** — Discuss + Research + Decide → Discussion Frozen
2. **IMPLEMENTATION PLAN** — Create/review the complete feature plan → Plan Frozen
3. **IMPLEMENTATION** — Implement the complete feature → Test + Review → Feature Frozen

Once a phase is explicitly accepted/frozen, immediately move to the next phase. Do not create additional approval or freeze gates between implementation units.

Implementation units are organizational subdivisions inside the complete feature. They may be used to sequence work and record progress, but they are not independent workflow phases and must not create separate unit-level authorization, freeze, or stop gates.

Do not reopen already frozen architecture or feature decisions merely because implementation is being organized into units. If implementation reveals a material conflict with a frozen decision, use the reopening process defined below.

Do not stop a feature implementation midway merely to create another planning or freeze gate. After the complete implementation plan is frozen and implementation is authorized, continue through all implementation units required to complete the feature before testing/reviewing the feature as a whole.

Do not skip the three feature phases themselves. The objective is controlled speed: discuss once, plan once, implement completely, test/review, and freeze.

## 6. CONTINUE RULE
'continue', 'go ahead', 'proceed', 'next', and similar phrases mean resume the next valid action from the documented current state. They do not automatically mean write code.

For CONTINUE:
1. Read this document.
2. Read CURRENT-STATUS.
3. Check frozen decisions.
4. Inspect active conversation context.
5. Inspect Git status when relevant.
6. Identify workflow state.
7. Identify current module/feature/task.
8. Determine next valid state.
9. Perform only that state's permitted action.

If state cannot be determined safely, STOP and ask.

## 7. PROMPT ROUTER
DISCUSS: no repository modification; clarify requirements, constraints, assumptions, edge cases and open questions. Discussion may include research and decisions needed to reach a complete feature discussion freeze.

RESEARCH: research only what is needed for the current feature discussion; distinguish evidence, interpretation, recommendation and uncertainty; no implementation.

DECIDE: present problem, constraints, options, tradeoffs, proposed decision and consequences. Nothing is frozen until explicitly accepted. Decisions required to complete the feature discussion belong to the same feature-discussion phase; do not create a separate decision gate for each implementation unit.

FEATURE DISCUSSION FREEZE: restate exact frozen feature requirements, scope, non-goals, constraints, consequences, related module/feature and documents, remaining explicitly accepted unknowns, and the next phase. Once accepted, immediately move to the complete implementation plan.

PLAN: create one complete implementation contract for the feature covering objective, scope, out-of-scope, prerequisites, files/modules, architecture impact, dependencies, complete implementation sequence, tests, acceptance criteria, risks and documentation. Implementation units may be listed inside the plan but do not become separate approval gates.

PLAN FREEZE: restate the exact complete implementation contract. Explicit approval is the authorization boundary for implementation. Once frozen and implementation is authorized, immediately begin/continue complete feature implementation without additional unit-level planning gates.

IMPLEMENT: verify requirement, frozen decisions, approved complete plan, Trello readiness where required, correct branch, safe Git state and absence of conflicting user changes. If a prerequisite is missing, do not code. Implement all required units of the feature within the same implementation phase.

TEST: after complete feature implementation, validate the complete feature according to the relevant test/build protocol. Do not weaken verification to make it pass.

REVIEW: after complete feature testing, compare the complete work with requirement, decisions, plan, architecture, tests and scope. Classify findings as BLOCKER, REQUIRED, OPTIONAL or FUTURE. Do not silently fix review findings.

DOCUMENT: update only the appropriate authoritative documents.

STATUS / WHAT'S NEXT: reconstruct state and return the next valid action.

## 8. NEW CHAT / LOST CONTEXT
A new chat is a context recovery event. Never pretend to remember unavailable conversation history.

Read:
1. AI_START_HERE
2. CURRENT-STATUS
3. DECISION-LOG
4. AI-CONTEXT
5. relevant architecture
6. relevant module/feature docs
7. Trello when available
8. Git state/history as needed

Then produce a SESSION RECOVERY SUMMARY:
- Where the project is
- What is frozen
- What is being worked on
- What is completed
- What is waiting
- Next valid action
- Conflicts/blockers

Only then continue.

## 9. MODULE / FEATURE PROTOCOL
Mentioning a module does not authorize creating it.

Example: 'We need Auth' starts DISCUSS.

Before creating a module, determine:
- responsibility
- reason for separate boundary
- boundaries
- consumers/dependencies
- state ownership
- shared/platform responsibilities
- public contract
- non-goals
- whether a module is actually justified

If a module is conceptually approved but absent, documentation/decision work comes first. A source module may be created only when authorized by the approved plan.

## 10. DOCUMENTATION-FIRST RULE
A significant module/feature should have a durable record describing, as applicable:
- purpose
- responsibility
- boundaries
- public API
- dependencies
- dependency direction
- state ownership
- Store contract
- domain responsibilities
- data/API boundary
- platform responsibilities
- testing strategy
- non-goals
- related decisions
- implementation status

Distinguish:
DISCUSSED / PROPOSED / FROZEN / IMPLEMENTED / VERIFIED

Never mark a proposal frozen merely because AI wrote it. Never mark implemented merely because files exist.

## 11. DISCUSSION PRESERVATION
During discussion, separate:
- User requirement
- Observed fact
- AI suggestion
- Open question
- Proposed decision
- Frozen decision

If point 8 changes, do not silently rewrite points 1–7. Freeze only explicitly accepted items.

## 12. MULTI-POINT DISCUSSION
Number points and mark each DISCUSSED, OPEN, PROPOSED or FROZEN.

If points 1–7 are accepted and 8–9 are challenged:
- freeze 1–7;
- keep 8–9 open;
- re-discuss 8–9;
- never reopen unrelated frozen points.

When all required points are accepted, update the durable record and move directly to the complete implementation plan.

## 13. FREEZE RECORD
When the user says final/frozen/approved, return:
- Topic
- Exact frozen decisions
- Scope
- Non-goals
- Consequences
- Related module/feature
- Related ADR/document
- Allowed behavior
- Forbidden behavior
- Remaining open items
- Next workflow state

A feature discussion freeze is not complete until the durable record reflects it. Once the complete discussion is frozen, the next state is the complete implementation plan. Once the complete plan is frozen and implementation is authorized, the next state is complete feature implementation.

## 14. IMPLEMENTATION PROTECTION
Before changing code, AI must be able to answer:
- What exact frozen requirement am I implementing?
- Which approved complete feature plan authorizes this?
- Which module owns it?
- Which decisions constrain it?
- How will it be verified?

If unclear, stop.

## 15. NO SILENT ARCHITECTURE CHANGES
If implementation reveals an architectural problem:
STOP → REPORT → DISCUSS → DECIDE → FREEZE → UPDATE PLAN → IMPLEMENT

A better idea discovered during coding is a new decision, not silent permission to redesign.

## 16. TRELLO ROUTING
Trello is the execution tracker. For implementation:
- identify the card;
- verify scope and acceptance criteria;
- keep status synchronized;
- link durable documentation/ADR;
- do not use Trello as the sole architecture source.

If no appropriate card exists, determine whether the workflow requires creating one before implementation.

## 17. GIT ROUTING
Before modification inspect status, branch and relevant diff. Preserve user changes. Keep changes scoped. Validate before commit. Inspect final diff. Commit/merge only through the approved workflow. Never use destructive Git commands to resolve uncertainty.

## 18. DOCUMENT ROUTING
- AI entry/routing → AI_START_HERE.md
- AI rules → AI_GUIDELINES.md / 02-AI-RULES.md
- stable context → 01-AI-CONTEXT.md
- significant decisions → 03-DECISION-LOG.md
- current state → 04-CURRENT-STATUS.md
- roadmap → 05-ROADMAP.md
- Trello process → 06-TRELLO-WORKFLOW.md
- development lifecycle → 07-DEVELOPMENT-WORKFLOW.md
- implementation → 08-IMPLEMENTATION-PROTOCOL.md
- review → 09-REVIEW-PROTOCOL.md
- recovery → 10-CHECKPOINTS.md
- Git/terminal → 11-TERMINAL-GIT-COMMANDS.md
- architecture → architecture/ARCHITECTURE.md
- frontend engineering → frontend/FRONTEND.md
- API integration → api/API_CONTRACT.md
- feature/module behavior → relevant feature/module document

Do not duplicate authoritative information unnecessarily.

## GRADLE DOCUMENT AUTHORITY
For all Gradle installation, version policy, Wrapper setup, commands, build/test commands, troubleshooting, and version changes, use `docs/build/GRADLE-SETUP-AND-WORKFLOW.md` as the single authoritative Gradle reference. Do not maintain competing Gradle setup instructions in other documents. Project-management documents route to this document; architecture documents do not define Gradle installation procedure.

## 19. MISSING DOCUMENT
If the correct durable document does not exist:
1. determine why it is needed;
2. do not invent a final decision;
3. create a placeholder/template when appropriate;
4. mark it PROPOSED;
5. fill it through DISCUSS → DECIDE → FREEZE;
6. do not treat a placeholder as accepted.

## 20. USER CHANGES MIND
A changed idea is not a failure. Determine whether it affects an open discussion, proposed decision, frozen decision, approved plan, or implementation. If frozen material is affected, use the reopening process. Do not rewrite history.

## 21. REOPENING
A frozen decision may be reopened for changed requirements, incompatibility, security, performance, maintenance, incorrect assumptions or platform limitations.

Record old decision, reason, evidence, impact, alternatives and new decision. Then freeze the replacement.

## 22. SESSION HANDOFF
At meaningful completion, update current status, decisions, feature/module docs, Trello, roadmap or checkpoint as applicable.

Return:
COMPLETED
FROZEN
OPEN
IMPLEMENTED
VERIFIED
BLOCKED
NEXT VALID ACTION

## 23. RESPONSE FORMAT
DISCUSS → Understanding / Facts & constraints / Questions / Options / Tradeoffs / Proposed direction / Open items / Next action

RESEARCH → Question / Evidence / Alternatives / Tradeoffs / Uncertainty / Conclusion / Decision required

FREEZE → Exact frozen points / Scope / Non-goals / Consequences / Related docs / Remaining open / Next prompt

PLAN → Objective / Scope / Files & modules / Sequence / Dependencies / Tests / Acceptance / Risks / Documentation / Approval step

IMPLEMENT → Plan followed / Changes / Tests / Platform verification / Deviations / Limitations / Documentation / Git state / Next step

REVIEW → Scope / Findings / Tests / Architecture / Required actions / Acceptance status

## 24. NEXT PROMPT RULE
When a phase finishes, return the single next prompt required to progress. The prompt must reflect actual state, not a generic script.

Examples:
After feature discussion freeze: 'Create the complete implementation plan for the frozen feature contract.'
After plan freeze: 'Begin implementation of the complete feature according to the frozen plan.'
After complete implementation: 'Run the required tests and verification for the complete feature.'
After testing: 'Review the complete implementation against the frozen plan.'
After review/acceptance: 'Finalize documentation, Git state, and feature freeze.'

Do not generate an intermediate unit-level approval prompt.

## 25. DO NOT CREATE EXTRA GATES TO SAVE OR SPEND TIME
The objective is productive, progressive, recoverable development without uncontrolled drift or repeated restarts. Use exactly the three feature phases. Do not add unit-level discussion, planning, authorization, freeze, or stop gates merely because a feature contains multiple implementation units.

Scale the amount of discussion, research, testing and review to risk, but never remove scope control, frozen constraints, validation or Git safety.

## 26. CURRENT TECHNICAL DIRECTION
Currently stated direction:
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Store-based pure MVI
- UDF
- No ViewModel
- Multi-module architecture
- Dependency Injection
- Gradle Convention Plugins

Exact libraries and implementation technologies remain unfrozen until accepted in the Decision Log.

## 27. ABSOLUTE STOP CONDITIONS
STOP when:
- required context is missing;
- documents conflict;
- requirements materially conflict;
- a frozen decision must change;
- implementation plan is missing;
- implementation authorization is missing;
- unexpected user changes exist;
- destructive operation is proposed;
- material dependency is unexpectedly required;
- scope materially expands;
- source contradicts documented architecture;
- AI would need a significant assumption.

## 28. ANTI-RESTART
A bad implementation does not automatically mean the project is bad.

Classify the problem:
1. Bug
2. Local design problem
3. Refactoring opportunity
4. Feature boundary problem
5. Architecture problem
6. Invalid fundamental assumption

Prefer fix, refactor, migrate, isolate or recover to a checkpoint before considering restart.

## 29. FINAL ROUTING PRINCIPLE
READ → LOCATE STATE → CHECK DECISIONS → CHECK SCOPE → DETERMINE MODE → ACT ONLY WITHIN AUTHORITY → VERIFY → DOCUMENT → HANDOFF

Never:
GUESS → CODE → DISCOVER CONFLICT → REWRITE EVERYTHING

The AI must move the project from one known, documented, verified state to the next.

## 30. ONE-LINE EXTERNAL AI INSTRUCTION
'Read AI_START_HERE.md first and strictly follow its routing, authority, workflow, permission, documentation, verification and handoff rules. Determine the current project state before taking action. Never guess or silently skip a required state.'

## 32. PROJECT TRACKER IS THE CONTROL LEDGER
The project tracker is the execution ledger for every significant feature/module/work item. It records ID, name, workflow state, current implementation unit, frozen decision reference, implementation-plan reference, status-document reference, Trello reference, Git branch/PR when applicable, test/verification state, blockers, last completed action, next valid action, and last update. The tracker answers "Where are we?" Detailed technical rules remain in their owning documents.

## 33. AUTOMATIC DOCUMENT UPDATE MATRIX
After each transition, update the required records before declaring it complete.

| Transition | Required record |
|---|---|
| New module/feature discussion | Current Status + Tracker |
| Durable decision produced | Decision Log + relevant module/feature plan |
| Feature discussion frozen | Decision Log + feature plan + Tracker |
| Complete implementation plan created | Feature plan + Tracker |
| Complete implementation plan frozen | Feature plan + Tracker + Trello |
| Implementation starts/completes | Feature Status + Tracker |
| Tests run for complete feature | Feature Status + Tracker + test result |
| Complete feature review | Feature Status + Tracker |
| Complete feature accepted/frozen | Feature Status + Current Status + Tracker + Trello |
| Checkpoint | Checkpoints + Current Status + Tracker |
| Commit/merge | Tracker + Current Status when materially relevant |

Implementation units may still be recorded inside the feature status document, but a unit transition does not create a separate workflow freeze or authorization gate.

If a required record cannot be updated, do not falsely declare the transition complete.

## 34. TWO DOCUMENTS PER SIGNIFICANT MODULE
Each significant module has exactly two primary lifecycle documents.

### 00-MODULE-IMPLEMENTATION-PLAN.md
The frozen contract: purpose/boundary, responsibilities/non-responsibilities, architecture, dependencies, public contracts, Store/state design, domain/data/API rules, platform rules, DI/lifecycle rules, error handling, logging, security, testing strategy, coding constraints, ordered implementation units, acceptance criteria, and out-of-scope items. Changes require the decision/reopening process.

### 01-MODULE-IMPLEMENTATION-STATUS.md
The living execution ledger. For each unit record unit ID, requirement reference, status, files changed, tests written/executed, verification result, review result, frozen/accepted state, checkpoint/date, limitations, and next unit. Never erase implementation history.
