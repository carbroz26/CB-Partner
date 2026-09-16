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

Workflow:
IDEA → DISCUSS → RESEARCH → DECIDE → DECISION FREEZE → PLAN → PLAN FREEZE → TRELLO READY → BRANCH → IMPLEMENT → TEST → REVIEW → ACCEPT → CODE FREEZE → COMMIT → MERGE → DOCUMENT → CHECKPOINT

Do not skip a required state because the user used a short prompt.

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
DISCUSS: no repository modification; clarify requirements, constraints, assumptions, edge cases and open questions.

RESEARCH: research only what is needed; distinguish evidence, interpretation, recommendation and uncertainty; no implementation.

DECIDE: present problem, constraints, options, tradeoffs, proposed decision and consequences. Nothing is frozen until explicitly accepted.

FREEZE: restate exact frozen points, scope, non-goals, consequences, related module/feature and documents; then update the durable record when authorized.

PLAN: create an implementation contract covering objective, scope, out-of-scope, prerequisites, files/modules, architecture impact, dependencies, sequence, tests, acceptance criteria, risks and documentation.

PLAN FREEZE: restate the exact implementation contract. Explicit approval is the authorization boundary for implementation.

IMPLEMENT: verify requirement, frozen decisions, approved plan, Trello readiness where required, correct branch, safe Git state and absence of conflicting user changes. If a prerequisite is missing, do not code.

TEST: validate according to the relevant test/build protocol. Do not weaken verification to make it pass.

REVIEW: compare actual work with requirement, decisions, plan, architecture, tests and scope. Classify findings as BLOCKER, REQUIRED, OPTIONAL or FUTURE. Do not silently fix review findings.

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

When all required points are accepted, update the durable record.

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

A freeze is not complete until the durable record reflects it.

## 14. IMPLEMENTATION PROTECTION
Before changing code, AI must be able to answer:
- What exact frozen requirement am I implementing?
- Which approved plan authorizes this?
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
When a stage finishes, return the single next prompt required to progress. The prompt must reflect actual state, not a generic script.

Examples:
After discussion: 'Research the open decisions above and return the evidence.'
After research: 'Create the proposed decision from this research.'
After freeze: 'Create the implementation plan for the frozen contract.'
After plan: 'Review and freeze this implementation plan.'
After implementation: 'Run the required tests and verification.'
After testing: 'Review the implementation against the frozen plan.'
After acceptance: 'Finalize documentation, Git state, and checkpoint.'

## 25. DO NOT SKIP STATES TO SAVE TIME
The objective is progressive, recoverable, reviewable development without uncontrolled drift or repeated restarts. Scale ceremony to risk, but never remove scope control, frozen constraints, validation or Git safety.

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

## 31. STATUS
This document is PROPOSED. Once frozen, changes to it are process changes and must themselves follow the project decision/freeze process.
