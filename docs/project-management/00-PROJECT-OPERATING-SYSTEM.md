# CB-Partner — Project Operating System

Status: PROPOSED

Purpose: Defines how the frontend project is planned, discussed, researched, implemented, reviewed, frozen, and evolved.

Core principles:
1. Build progressively.
2. Prefer small, reversible changes.
3. Do not restart because of localized problems.
4. Solve current requirements before speculative future requirements.
5. Document significant decisions.
6. AI is an engineering partner, not the autonomous architect.
7. Every meaningful task has scope, out-of-scope items, and acceptance criteria.
8. Prefer vertical feature slices when practical.
9. Frontend and backend remain separate projects and repositories.
10. Test before completion and preserve known-good checkpoints.
11. Put unrelated improvements into backlog.
12. Complexity must be justified by a current requirement.

Lifecycle:
IDEA → DISCUSS → RESEARCH → DECIDE → DECISION FREEZE → PLAN → PLAN FREEZE → TRELLO → BRANCH → IMPLEMENT → TEST → REVIEW → ACCEPT → CODE FREEZE → COMMIT → MERGE → DOCUMENT → CHECKPOINT.

Freeze states:
- Decision Freeze: accepted and recorded decision.
- Plan Freeze: approved implementation contract.
- Code Freeze: accepted implementation with verification complete.
- Checkpoint: known-good Git state recorded.

AI must stop when a frozen decision or plan must change.

This document is PROPOSED until explicitly frozen.