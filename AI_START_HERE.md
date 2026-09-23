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

## 32. PROJECT TRACKER IS THE CONTROL LEDGER
The project tracker is the execution ledger for every significant feature/module/work item. It records ID, name, workflow state, current implementation unit, frozen decision reference, implementation-plan reference, status-document reference, Trello reference, Git branch/PR when applicable, test/verification state, blockers, last completed action, next valid action, and last update. The tracker answers "Where are we?" Detailed technical rules remain in their owning documents.

## 33. AUTOMATIC DOCUMENT UPDATE MATRIX
After each transition, update the required records before declaring it complete.

| Transition | Required record |
|---|---|
| New module/feature discussion | Current Status + Tracker |
| Durable decision produced | Decision Log + relevant module/feature plan |
| Decision frozen | Decision Log + module plan + Tracker |
| Implementation plan created | Module plan + Tracker |
| Implementation plan frozen | Module plan + Tracker + Trello |
| Unit selected | Module Status + Tracker + Trello |
| Coding starts/completes | Module Status + Tracker |
| Tests run | Module Status + Tracker + test result |
| Review completes | Module Status + Tracker |
| Unit accepted/frozen | Module Status + Tracker + Trello |
| Module complete | Module Status + Current Status + Tracker + Trello |
| Checkpoint | Checkpoints + Current Status + Tracker |
| Commit/merge | Tracker + Current Status when materially relevant |

If a required record cannot be updated, do not falsely declare the transition complete.

## 34. TWO DOCUMENTS PER SIGNIFICANT MODULE
Each significant module has exactly two primary lifecycle documents.

### 00-MODULE-IMPLEMENTATION-PLAN.md
The frozen contract: purpose/boundary, responsibilities/non-responsibilities, architecture, dependencies, public contracts, Store/state design, domain/data/API rules, platform rules, DI/lifecycle rules, error handling, logging, security, testing strategy, coding constraints, ordered implementation units, acceptance criteria, and out-of-scope items. Changes require the decision/reopening process.

### 01-MODULE-IMPLEMENTATION-STATUS.md
The living execution ledger. For each unit record unit ID, requirement reference, status, files changed, tests written/executed, verification result, review result, frozen/accepted state, checkpoint/date, limitations, and next unit. Never erase implementation history.

## 35. ONE IMPLEMENTATION UNIT AT A TIME
After a module plan is frozen:
SELECT → IMPLEMENT → TEST → REVIEW → ACCEPT/FIX → FREEZE → RECORD → STOP

After the unit is frozen, update Module Status, Tracker, Trello, Current Status when materially relevant, and checkpoint when required. Then STOP. Never automatically implement the next unit.

## 36. GLOBAL CODING QUALITY GATE
Every code change follows applicable frozen engineering standards. Verify as applicable: cohesive/single-responsibility classes; intentional ownership/lifecycle; immutability; safe nullability; clear naming; minimal public API; dependency direction and DI; appropriate class lifetime; Singleton only when justified by lifecycle/resource/ownership; no unnecessary design patterns; no speculative abstractions; no hidden global state; structured error handling; coroutine/concurrency/thread-safety correctness; logging/security rules; Compose state/recomposition rules; platform boundaries; testability; maintainability.

If a desired coding rule is not frozen, propose it instead of silently making it permanent.

## 37. TESTING GATE
Compiling is not sufficient. For every unit, determine required verification from the frozen plan and testing standards. As applicable: build/compile, unit tests, Store/state tests, domain/use-case tests, repository/data tests, serialization/API tests, UI tests, platform tests, regression tests, failure/edge-case tests, lint/format/static checks. Record what actually ran and the result. Never claim an unrun test passed.

## 38. CONTROLLED PROGRESS NOTIFICATION
During repository work, keep the user informed.

Before each material action:
ABOUT TO DO: <action>

After it:
DONE: <result>

For coding, announce the unit, intended files/scope, and intended change. After coding, show the relevant changed code/diff or precise file summary, test commands/results, and any deviation immediately. Do not expose hidden chain-of-thought; provide operational progress, decisions, changed files, commands/results, and diffs.

## 39. NO UNAUTHORIZED BATCH WORK
Do not silently implement multiple units, create unrelated modules, refactor unrelated code, upgrade dependencies, alter architecture, modify frozen decisions, touch backend, change unrelated Trello cards, or merge code. Material scope expansion requires authorization.

## 40. AUTOMATION FAILURE RULE
This process reduces omissions but cannot honestly guarantee zero mistakes. If a required tool/action is unavailable: STOP; identify the missing action; perform it manually if authorized and possible; otherwise mark BLOCKED and tell the user exactly what remains.

## 41. POST-FREEZE GATE
After any meaningful freeze, STOP. Do not automatically start the next feature, module, unit, dependency change, or refactor.

Return FREEZE COMPLETE with what was frozen, records updated, Tracker state, Trello state, current state, and remaining work. Then ask what objective the user wants next. Offer: continue this module; discuss another aspect; start another module; start another feature; research; review; status; end session. Provide one recommended copy/paste prompt but do not execute it automatically.

## 42. MANDATORY RESPONSE FOOTER
Every substantial workflow response ends with:
CURRENT STATE — topic/module, workflow state, current unit, frozen items, open items.
RECORDS UPDATED — documents, Tracker, Trello, Git when applicable.
NEXT ACTION — one precise process action.
NEXT PROMPT — one copy/paste-ready prompt.
At a freeze boundary also include POST-FREEZE CHOICE and wait for the user's objective.

## 43. USER OVERRIDE DOES NOT BYPASS GATES
"Just do it" does not authorize silently skipping required decision, planning, testing, review, documentation, or Git safety gates. Identify the minimum missing authorization instead.

## 44. DEFINITION OF DONE
A work item is DONE only when applicable: requirement understood; scope known; decisions frozen; approved plan exists; correct unit selected; code implemented; required tests executed; review completed; acceptance/freeze recorded; Module Status updated; Tracker updated; Trello synchronized; Git state recorded; checkpoint/documentation completed; user notified; next action returned. If an applicable condition is false, do not report DONE.

## 45. SINGLE SOURCE FOR "WHERE ARE WE?"
CURRENT-STATUS + Tracker + relevant Module Status + Git + Trello must agree. If they disagree, STOP and reconcile before material work.

## 46. STATUS
This document is PROPOSED. Once frozen, changes to it are process changes and must themselves follow the project decision/freeze process.

## 47. MANDATORY GIT / ANTIGRAVITY CONTROL GATE
Every code change is tracked through an explicit Git lifecycle. The AI must never treat code written as the end of a task.

PREPARE → IMPLEMENT → VERIFY → RECORD → COMMIT → PUBLISH/PR → MERGE → SYNC.

For every material code change, verify the authorized branch, implement only the authorized scope, inspect the complete changed-file list and diff, run required checks, update Module Status/Tracker/Trello, commit the focused unit, push/open or update the PR when required, merge only after required review/checks and authorization, and then provide exact Antigravity synchronization commands.

## 48. BRANCHING STRATEGY
Use branches to isolate development work. GitHub documents branches as a way to isolate feature/fix work and recommends pull requests and protection for important branches. citeturn0search2turn0search6

Default:
- main = protected, stable source of truth.
- One active module/feature branch for an authorized module/feature, based on the current main or explicitly authorized integration base.
- Task-level commits live on that module branch.
- Do not work directly on main.
- Do not create a new branch for every tiny edit unless the task is independently reviewable or the plan requires it.

Suggested names: feature/<module>, fix/<module>-<short-description>, chore/<short-description>, docs/<short-description>.
The exact branch name must be recorded in the Tracker before implementation.

## 49. COMMIT STRATEGY
Commits represent meaningful, reviewable units. A module follows: module branch → task/implementation-unit commits → verification → PR → merge → optional milestone tag. Unrelated refactors must not be mixed into the commit.

## 50. TAGGING STRATEGY
Tags are milestones, not save points. Use them for meaningful milestones such as module acceptance/freeze, release candidates, project releases, or another explicitly defined milestone. Record tag name and purpose in Tracker and Module Status. Never move an existing tag silently.

## 51. REQUIRED CHANGE REPORT
After every material code change, return:
- CHANGE: implementation unit/task ID, branch, commit/PR if created, purpose.
- FILES CHANGED: full relative path of every added/modified/deleted file and reason.
- CODE REVIEW: key classes/functions, behavior, dependencies/configuration, unrelated-change confirmation.
- VERIFICATION: exact commands run, results, tests/checks, known failures.
- RECORDS: Module Status, Tracker, Trello, Git/PR/tag.
- ANTIGRAVITY SYNC: exact commands appropriate to the final Git state. Never invent a branch, commit, merge, tag, or SHA.

Typical post-merge synchronization when returning to main:
git fetch origin
git checkout main
git pull --ff-only origin main

If the user needs a module branch, return branch-specific commands instead of assuming main.

## 52. GIT SAFETY GATE
Before push/merge/tag: verify branch, working tree, changed files, diff, tests/checks, task scope, and that unrelated user changes will not be overwritten. Never use destructive commands such as hard reset, force push, or deletion to clean up without explicit authorization.
GitHub protected branches can require pull requests, reviews, status checks, conversation resolution, signed commits, linear history, and can block force pushes/deletion. These protections should be configured for important branches rather than relying only on AI behavior. citeturn0search0turn0search3

## 53. MERGE IS A DISTINCT STATE
Track separately: IMPLEMENTED → VERIFIED → COMMITTED → PUSHED → PR OPEN → REVIEWED → MERGED → SYNCED.
IMPLEMENTED does not mean MERGED.

## 54. LOCAL ANTIGRAVITY IS PART OF THE WORKFLOW
Antigravity is the user's local execution/synchronization environment. When the AI changes the repository remotely, it must report what changed, branch/commit/PR/tag state, exact local synchronization commands, and local verification commands. It must never assume those commands were executed.

## 55. MANDATORY AI ROUTER / STATE RECOVERY
AI_START_HERE.md is the first document for every new AI session and every request that may involve project work. It is a router, not a replacement for module documentation.

### 55.1 Recover Before Acting
1. Identify repository/project.
2. Read this document completely.
3. Read `docs/project-management/12-PROJECT-TRACKER.md`.
4. Read `04-CURRENT-STATUS.md`.
5. Determine current module/work item and workflow state.
6. Read only the relevant module Implementation Plan/Status and required technical/process documents.
7. Inspect actual Git branch/status/diff and relevant Trello state.
8. Reconcile contradictions before acting.

### 55.2 Interpret the User Request
Classify the request as one primary intent:
DISCUSS, RESEARCH, DECIDE, FREEZE, PLAN, IMPLEMENT, TEST, REVIEW, DOCUMENT, STATUS, CONTINUE, or RECOVER.

The user's words are intent signals, not permission to bypass gates.

### 55.3 CONTINUE Protocol
If the user says `CONTINUE`, `continue Auth`, or gives an otherwise underspecified continuation request:
- recover state from Tracker first;
- identify the active module/work item;
- read its status and frozen plan;
- determine the next valid workflow state;
- check whether user approval is required;
- return the next action and a copy/paste prompt;
- do not automatically implement the next unit.

If more than one unresolved context exists, ask the user to select one.

### 55.4 State-to-Document Routing
| Detected state/intent | Required next reads |
|---|---|
| New discussion | Tracker + relevant project context |
| Existing module discussion | Tracker + module Plan + module Status |
| Research | Tracker + relevant architecture/technical docs + authoritative external sources when needed |
| Decision | Decision Log + relevant technical docs |
| Plan | Decision Log + Architecture + module context |
| Implementation | Frozen module Plan + Module Status + coding/testing rules + Tracker |
| Testing | Module Plan + Module Status + testing/review rules + Tracker |
| Review | Module Plan + Module Status + actual Git diff + testing rules + Tracker |
| Freeze | relevant Plan/Status + acceptance/review evidence + Tracker + Git/Trello state |
| Continue | Tracker first, then state-specific documents |
| New chat/recovery | AI_START + Tracker + Current Status, then current module documents |

### 55.5 Authorization Gates
- DISCUSS/RESEARCH/DECIDE/PLAN: no source-code modification.
- IMPLEMENT: requires a frozen applicable plan and explicit implementation authorization.
- TEST: requires an implementation target.
- REVIEW: requires an implementation target and actual diff/evidence.
- FREEZE: requires acceptance evidence and all required records.
- MERGE/TAG/PUSH: requires Git safety checks and applicable authorization.
- After meaningful FREEZE: STOP.

### 55.6 Automatic State Interpretation
The router must never assume that the last conversation message describes the current state. State comes from the Tracker plus authoritative project/module records and actual Git/Trello evidence.

Examples:
- Plan not frozen + user says Implement → route to plan freeze; do not code.
- Unit implemented but not verified + user says Continue → route to verification.
- Unit verified but not committed → route to commit.
- PR open and awaiting review → route to review.
- Unit merged but local workspace not synced → route to Antigravity synchronization.
- Module has no pending units and is frozen → route to post-freeze choice; do not invent new work.
- Contradictory records → route to reconciliation; do not continue implementation.

### 55.7 Mandatory Response Contract
At each workflow boundary return:
CURRENT STATE
RECORDS UPDATED
NEXT ACTION
NEXT PROMPT

After a meaningful freeze additionally return:
FREEZE COMPLETE
POST-FREEZE CHOICE

During repository work announce:
ABOUT TO DO
then after the action:
DONE

For every material code change also return the complete changed-file list, important changed classes/functions, verification results, Git state, and exact Antigravity sync commands.

### 55.8 Post-Freeze Choice
After freezing a unit/module, offer:
1. Continue this module
2. Discuss another aspect
3. Start another module
4. Start another feature
5. Research a technical decision
6. Review existing work
7. Check project status
8. End session

Wait for the user's objective. Do not automatically proceed.

### 55.9 No Hidden Context Dependency
A new AI must be able to recover the current workflow without access to prior chat messages. If durable information exists only in conversation history, document it before claiming recovery is complete.


## 56. THREE-TIER BRANCH STRATEGY

The repository uses three branch levels:

main ← development ← feature/<module>

- main = production/stable branch. It receives changes only when the relevant product feature is production-ready and complete acceptance criteria are satisfied.

- development = integration branch. Completed/frozen module work is merged here for integration and regression verification.

- feature/<module> = active module branch. Example: feature/authentication or feature/network. Module implementation work occurs here.

Never develop directly on main or development.



## 57. MODULE BRANCH LIFECYCLE

New module: development → feature/<module> → discussion → plan freeze → implementation units → test/review/freeze each unit → module acceptance → PR to development → integration verification.

A frozen module does not mean its branch is permanently closed, nor that AI should switch back to it automatically.



## 58. FROZEN MODULE REACTIVATION RULE

After a module is frozen and merged into development, remain in the current working context unless a new task actually requires changing that module.

If a later issue belongs to Network while Authentication is current: identify the issue as Network-owned; stop Authentication workflow; check Tracker for Network branch/state; switch/create feature/network only as authorized; read Network Plan + Status; create a Network task/unit if required; implement/test/review through normal gates; merge Network to development; then return to the requested context only when the Network task is complete or explicitly paused.

Never switch branches merely because another module exists. Branch switching must be caused by an authorized work item.

If a module branch was already merged/deleted, create a new issue branch from current development according to Git protocol; do not resurrect stale state without checking Tracker/Git history.



## 59. DEVELOPMENT INTEGRATION GATE

A module branch may merge into development only when applicable implementation units are accepted/frozen, required tests/checks pass, documentation is synchronized, and required review is complete.

Track separately: MODULE_FROZEN → PR_TO_DEVELOPMENT → REVIEWED → MERGED_TO_DEVELOPMENT → INTEGRATION_VERIFIED.

After integration, run applicable cross-module/regression verification before treating the integrated state as stable.



## 60. MAIN RELEASE GATE

main is not a module integration branch.

A module such as Authentication is not merged to main merely because Authentication is frozen. The relevant product feature must be production-ready according to its frozen acceptance criteria.

Example: Authentication (Login + OTP + session/auth state + error/retry handling + required tests + review + production acceptance) → development → release review → main.

The exact definition of production-ready must be frozen for the feature/release. Do not invent missing acceptance criteria at merge time.



## 61. MAIN MERGE SAFETY

Before development → main: confirm release/feature scope; all required feature/module acceptance criteria; integration/regression verification; documentation/Tracker/Trello; PR/diff and Git status; repository protections/reviews/checks; required authorization; merge commit/tag/release milestone. Then provide Antigravity commands to synchronize main.

Never merge a single completed module to main solely because that module is complete.



## 62. BRANCH SWITCH RESPONSE REQUIREMENT

Whenever a task requires a branch switch, explicitly report:

BRANCH SWITCH REQUIRED

Current: <branch>

Target: <branch>

Reason: <authorized work item>

Tracker: <reference/state>

Then verify the target branch before making changes.

When no branch switch is required, do not switch branches merely for convenience.
## 63. STANDARD REPOSITORY GIT + BUILD LIFECYCLE

The repository workflow must be recoverable from documentation without relying on chat history.

CLONE → VERIFY → FETCH → CREATE/SWITCH BRANCH → PULL/UPDATE → IMPLEMENT → BUILD/TEST → REVIEW DIFF → COMMIT → PUSH → PR/REVIEW → MERGE → FETCH → SYNC LOCAL BRANCH → BUILD/TEST AGAIN.

### Initial clone
After cloning, verify the remote URL, current branch, working-tree status, required project files, and Gradle Wrapper availability.

    git clone <repository-url>
    cd CB-Partner
    git remote -v
    git status
    git branch --show-current
    Get-ChildItem -Force -Name

### Before starting work
    git fetch origin
    git status
    git branch -vv
    git log --oneline --decorate -10

Use only the authorized branch workflow. Never overwrite unexpected user changes.

### Branch workflow
    git fetch origin
    git switch development
    git pull --ff-only origin development
    git switch -c feature/<module>

Do not develop directly on main or development.

### Inspect before commit
    git status
    git diff --check
    git diff --stat
    git diff

### Commit and publish
    git add <files>
    git diff --cached
    git commit -m "<type>: <description>"
    git push -u origin feature/<module>

Never force-push without explicit authorization.

### Fetch, pull and merge
git fetch origin updates remote-tracking references without changing the working tree.
Use git pull --ff-only for safe fast-forward synchronization.
Normal module flow is feature/<module> → PR/review → development → integration verification → release review → main.
Do not use local git merge merely for convenience when the approved workflow requires a Pull Request.

### Post-merge synchronization
    git fetch origin
    git switch development
    git pull --ff-only origin development
    git status

After an approved production merge:

    git fetch origin
    git switch main
    git pull --ff-only origin main
    git status

Then run the required integration/build verification.

### Gradle Wrapper rule
The repository must use the Gradle Wrapper for normal builds. Required files are gradlew, gradlew.bat, gradle/wrapper/gradle-wrapper.jar, and gradle/wrapper/gradle-wrapper.properties.
Windows uses .\gradlew.bat; macOS/Linux uses ./gradlew.
The current AGP 9.3.0 configuration requires Gradle 9.7.1 as the minimum/default compatible version. BASE-ARCH-014-T01 therefore targets Gradle 9.7.1.
Do not replace the official Wrapper with a custom script or manually invented JAR.

### Build/test baseline
After clone or synchronization, execute the exact build/test commands required by the frozen plan. Never claim a test passed unless it actually ran. Record Windows iOS simulator limitations instead of suppressing them.

### Antigravity handoff
When AI changes repository state remotely, return exact local synchronization and verification commands for the actual final Git state. Never invent a branch, commit, merge, tag, or SHA.

## 64. GRADLE VERSION AUTHORITY
**Authoritative CB-Partner Gradle version: 9.7.1.**

- Project Wrapper target: Gradle 9.7.1.
- Normal project builds use `.\\gradlew.bat` on Windows or `./gradlew` on macOS/Linux.
- System Gradle is bootstrap/update tooling only; it is not the normal project build path.
- Gradle 9.5.0 was previously documented as a compatibility baseline and is superseded; it is not the selected CB-Partner version.
- All Gradle setup and command details live in `docs/build/GRADLE-SETUP-AND-WORKFLOW.md`.
