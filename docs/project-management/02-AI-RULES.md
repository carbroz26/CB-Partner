# CB-Partner — AI Rules

**Status:** PROPOSED

## Mandatory Session Protocol
Before substantial work:
1. Identify repository and branch.
2. Read AI_GUIDELINES.md.
3. Read AI Context, Current Status, Decision Log and relevant architecture/feature docs.
4. Inspect Git status.
5. Inspect relevant source and tests.
6. Identify the Trello task and approved plan.

## Modes
DISCUSS = understand; no code.
RESEARCH = gather evidence; no code.
DECIDE = settle decision; no code.
PLAN = create implementation contract; no code.
IMPLEMENT = execute approved contract.
TEST = validate.
REVIEW = inspect and report; do not silently fix.
DOCUMENT = update authoritative records.
STATUS = report state.

## Permission Boundary
A discussion, recommendation, research result, or plan does not authorize repository modification. Implementation requires explicit approval.

## Scope
Do not add adjacent improvements, broad refactors, dependency upgrades, architecture changes, or cleanup unless included in the approved plan.

## Frozen Decisions
Accepted decisions are constraints. If implementation conflicts with one, stop and report the conflict.

## Repository Safety
Never overwrite unknown user changes. Never assume a clean tree. Never use destructive Git commands as a shortcut.

## Dependencies
Material dependency changes require research and approval. Consider multiplatform support, maintenance, compatibility, testing, build impact, and alternatives.

## Code Quality
Prefer simple, explicit, testable code. Follow existing approved patterns. Avoid speculative frameworks and abstractions.

## Validation
Validation must match change risk. Compilation alone is not sufficient.

## Reporting
After work report changed files, behavior, checks/tests, build results, deviations, limitations, and documentation changes.

## Stop Conditions
Stop if requirements are materially ambiguous, a frozen decision must change, scope expands, destructive action is proposed, unexpected user changes exist, or the plan cannot be followed safely.

**This document is PROPOSED until explicitly frozen.**
## Router Rule
AI_START_HERE.md must be read before substantial work and is the routing authority for determining workflow mode, current context, required documents, permissions, and next action.

## Continue Rule
CONTINUE means recover state and determine the next valid workflow step. It does not mean implement automatically. If multiple unresolved contexts exist, ask the user to choose rather than guess.

## State Gates
IMPLEMENT requires a frozen plan. TEST requires an implemented change. REVIEW requires an implementation and verification target. FREEZE requires completed acceptance gates. MERGE requires the Git gate and applicable review/checks. After a freeze, stop and return the next prompt.

## Change Reporting
Every material code change must report the complete changed-file list, important changed classes/functions, verification commands/results, Git state, document updates, and exact Antigravity synchronization commands.
