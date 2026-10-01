# CB-Partner — Development Workflow

**Status:** FROZEN

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
9. BRANCH: start one focused, clearly named working branch from `development`.
10. IMPLEMENT: execute frozen plan.
11. TEST: verify behavior and quality.
12. REVIEW: compare actual diff with frozen contract.
13. ACCEPT: resolve required findings.
14. CODE FREEZE: stop unrelated changes.
15. COMMIT/MERGE: preserve logical Git history and merge the completed task into `development` only after its required gates.
16. DOCUMENT: update project memory.
17. CHECKPOINT: record known-good state.

## Restart Prevention
Classify problems before reacting: local bug, local refactor, architectural issue, requirement change, or invalid fundamental assumption. Prefer focused fixes and migration over restart.

## Small Changes
Trivial changes may skip formal research/ADR, but must retain appropriate scope, validation and Git discipline.

## Controlled Unit Lifecycle
After PLAN FREEZE, work one implementation unit at a time: SELECT → IMPLEMENT → TEST → REVIEW → ACCEPT/FIX → FREEZE → RECORD → STOP. Do not batch units.

## Git Lifecycle
IMPLEMENTED → VERIFIED → COMMITTED → PUSHED → PR OPEN → REVIEWED → MERGED → SYNCED. These are separate states. The user receives a change report and exact Antigravity sync commands after material code changes.

## Official Branch Model

Permanent branches:

- `main` — production-ready code only.
- `development` — integration branch and source for new working branches.

Working branches:

- `architecture/<clear-name>` — architecture work.
- `feature/<clear-name>` — product feature work.
- `fix/<clear-name>` — bug fixes.
- `hotfix/<clear-name>` — urgent production fixes.

Branch names must describe the actual work. Do not create generic or numbered branches such as `work`, `test`, `temp`, `dev2`, `feature2`, `implementation2`, `new`, or `final`.

Do not create multiple implementation variants for the same task. A task has one focused working branch. Historical recovery branches may remain for their existing lifecycle but do not establish the naming convention for future work.

## Branch Promotion

```text
development
     ↓
focused working branch
     ↓
implementation → test → review → code freeze
     ↓
development
     ↓
production-ready feature complete
     ↓
main
```

Merging into `development` does not automatically mean the feature is production-ready. Merging into `main` is reserved for completed production-ready work.

## Next Product Working Branch

After BASE-ARCH-017 completes its verification/review/integration gates, the next product vertical slice uses:

`feature/splash-config-bootstrap`

This branch is created from the then-current `development` branch. It must not be created prematurely from an outdated `development` state.

## Productivity Rule

Once required base architecture contracts are frozen, prefer a small real product vertical slice over speculative additional base-architecture work.

The first planned product slice is:

**FEATURE-001 — Splash + Config/Bootstrap**

```text
Application Launch
      ↓
Splash UI
      ↓
Pure MVI / Store
      ↓
Config / Bootstrap API
      ↓
Domain boundary
      ↓
Data / Ktor
      ↓
Config response parsing
      ↓
Runtime configuration
      ↓
Dynamic application entry point
```

This document does not itself authorize feature implementation. The feature must still pass its own DISCUSS → DECIDE → PLAN → PLAN FREEZE → BRANCH → IMPLEMENT → TEST → REVIEW → ACCEPT → CODE FREEZE lifecycle.

## Post-Freeze Gate
Every meaningful freeze is a hard stop. Return the current state, records updated, remaining work, and one next prompt. Wait for the user's next objective.
