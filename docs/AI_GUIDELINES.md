# CB-Partner — AI Guidelines

**Status:** PROPOSED

## Mission
Help build CB-Partner progressively without allowing AI speed to create uncontrolled architecture, scope drift, or repeated project restarts.

## First Rule
Before substantial work, read the project context, operating system, AI rules, current status, accepted decisions, and relevant technical documentation. Then inspect the actual repository.

## Conversation Modes
Use exactly one primary mode:
- DISCUSS
- RESEARCH
- DECIDE
- PLAN
- IMPLEMENT
- TEST
- REVIEW
- DOCUMENT
- STATUS

Never interpret DISCUSS/RESEARCH/DECIDE/PLAN as permission to edit code.

## Implementation Authorization
Only an explicit IMPLEMENT request against an approved/frozen plan authorizes code changes.

## Frozen Constraints
- No ViewModel.
- Store-based MVI + UDF.
- Frontend/backend remain separate.
- Follow accepted architectural decisions.
- Do not silently expand scope.

## AI Response Contract
For planning: state objective, scope, architecture impact, files/modules, steps, tests, risks, and acceptance criteria.
For implementation: confirm plan, make scoped changes, validate, inspect diff, and report.
For review: report findings without automatically changing code.
For research: distinguish source facts, interpretation, recommendation, and uncertainty.

## Stop and Ask
Stop when requirements conflict, a frozen decision must change, a material dependency is needed unexpectedly, scope expands, a destructive operation is proposed, or repository state is unsafe.

## Anti-Overengineering
Do not create abstractions because they might be useful later. First prove the requirement. Prefer the smallest architecture that preserves the frozen boundaries.

## Anti-Drift
If implementation begins differing from the approved plan:
STOP → explain → obtain decision → update plan/docs → continue.

## Session Handoff
When ending a substantial task, ensure Current Status, decisions, feature documentation, Trello state, and checkpoint information are updated as applicable.

**This document is PROPOSED until explicitly frozen.**
## Mandatory Router
AI_START_HERE.md is the first operational document. It determines state recovery, user intent, required documents, workflow gates and next prompt.

## CONTINUE
CONTINUE means recover state from the Project Tracker and relevant status documents. It is not permission to code.

## Controlled Output
Before material repository actions state ABOUT TO DO. After each material action state DONE. At every stop/freeze return CURRENT STATE, RECORDS UPDATED, NEXT ACTION and NEXT PROMPT. After a freeze also return POST-FREEZE CHOICE and wait.
