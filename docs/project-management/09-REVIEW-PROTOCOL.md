# CB-Partner — Review Protocol

**Status:** PROPOSED

## Purpose
Review verifies that actual work satisfies the approved requirement and plan without turning review into uncontrolled redesign.

## Review Sequence
1. Scope
2. Functional correctness
3. Architecture
4. Store/MVI/UDF
5. KMP/platform compatibility
6. Lifecycle, concurrency and errors
7. Tests
8. Maintainability
9. Security/privacy where relevant
10. Documentation

## Store/MVI/UDF Checks
- Store owns the agreed state flow.
- UI renders state and emits events.
- Business logic is outside composables.
- Events flow inward and state flows outward.
- Side effects are controlled and testable.
- No ViewModel is introduced.

## Findings
BLOCKER — cannot accept.
REQUIRED — must fix.
OPTIONAL — useful but not required.
FUTURE — separate backlog item.

## Review Rules
Review the actual Git diff and tests, not only the final result. Compare with the frozen plan. Do not request unrelated cleanup. Do not automatically implement review findings.

## Acceptance
Acceptance occurs after blockers/required findings are resolved and verification passes.

**This document is PROPOSED until explicitly frozen.**