# CB-Partner — Development Workflow

**Status:** PROPOSED

## Master Lifecycle
IDEA → DISCUSS → RESEARCH → DECIDE → DECISION FREEZE → PLAN → PLAN FREEZE → TRELLO READY → BRANCH → IMPLEMENT → TEST → REVIEW → ACCEPT → CODE FREEZE → COMMIT → MERGE → DOCUMENT → CHECKPOINT.

## Phase Responsibilities
1. IDEA: capture outcome without prescribing implementation.
2. DISCUSS: clarify behavior, constraints, assumptions and edge cases. No code.
3. RESEARCH: collect evidence needed for decisions. No code.
4. DECIDE: select an approach and record significant decisions.
5. DECISION FREEZE: owner accepts; decision becomes constraint.
6. PLAN: inspect repository and create implementation contract.
7. PLAN FREEZE: owner approves; coding is authorized.
8. TRELLO READY: create/update actionable card.
9. BRANCH: start focused branch.
10. IMPLEMENT: execute frozen plan.
11. TEST: verify behavior and quality.
12. REVIEW: compare actual diff with frozen contract.
13. ACCEPT: resolve required findings.
14. CODE FREEZE: stop unrelated changes.
15. COMMIT/MERGE: preserve logical Git history.
16. DOCUMENT: update project memory.
17. CHECKPOINT: record known-good state.

## Restart Prevention
Classify problems before reacting: local bug, local refactor, architectural issue, requirement change, or invalid fundamental assumption. Prefer focused fixes and migration over restart.

## Small Changes
Trivial changes may skip formal research/ADR, but must retain appropriate scope, validation and Git discipline.

**This document is PROPOSED until explicitly frozen.**
## Controlled Unit Lifecycle
After PLAN FREEZE, work one implementation unit at a time: SELECT → IMPLEMENT → TEST → REVIEW → ACCEPT/FIX → FREEZE → RECORD → STOP. Do not batch units.

## Git Lifecycle
IMPLEMENTED → VERIFIED → COMMITTED → PUSHED → PR OPEN → REVIEWED → MERGED → SYNCED. These are separate states. The user receives a change report and exact Antigravity sync commands after material code changes.

## Post-Freeze Gate
Every meaningful freeze is a hard stop. Return the current state, records updated, remaining work, and one next prompt. Wait for the user's next objective.
