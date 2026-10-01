# CB-Partner — Git Branching Workflow

**Status:** FROZEN
**Scope:** Entire CB-Partner frontend repository

## 1. Permanent Branches

CB-Partner has exactly two permanent branches:

### `main`

Production branch.

- Contains production-ready features only.
- No direct feature development.
- A feature reaches `main` only after implementation, verification, review, code freeze, and integration gates are complete.

### `development`

Integration branch.

- Receives completed and frozen architecture/features.
- Is the starting point for the next working branch.
- Is not the production branch.

## 2. Working Branches

Every active task gets one clearly named working branch created from `development`.

### Architecture

```text
architecture/<clear-name>
```

Examples:

```text
architecture/runtime-infrastructure
architecture/navigation-foundation
```

### Feature

```text
feature/<clear-name>
```

Examples:

```text
feature/splash-config-bootstrap
feature/dynamic-runtime
```

### Bug Fix

```text
fix/<clear-name>
```

### Hotfix

```text
hotfix/<clear-name>
```

## 3. Naming Rules

The branch name must make the active work understandable without opening Git history.

Do not use generic branches such as:

- `work`
- `test`
- `temp`
- `dev2`
- `feature2`
- `implementation2`
- `new`
- `final`

Do not create multiple branch variants for the same task such as `-implementation`, `-new`, or `-final`. If the implementation plan changes, update the authorized task/plan instead.

## 4. Lifecycle

```text
development
     ↓
architecture/<name> OR feature/<name>
     ↓
DISCUSS
     ↓
RESEARCH
     ↓
DECIDE
     ↓
DECISION FREEZE
     ↓
PLAN
     ↓
PLAN FREEZE
     ↓
IMPLEMENT
     ↓
TEST
     ↓
REVIEW
     ↓
ACCEPT
     ↓
CODE FREEZE
     ↓
COMMIT / PUSH / PR
     ↓
development
     ↓
production-ready feature complete
     ↓
main
```

## 5. Important State Separation

These states are different and must never be treated as synonyms:

```text
IMPLEMENTED ≠ VERIFIED ≠ REVIEWED ≠ FROZEN ≠ MERGED ≠ SYNCED
```

A working branch may exist while implementation is incomplete. A completed implementation is not automatically verified. A verified feature is not automatically merged. A merge to `development` is not automatically a production release.

## 6. Current Recovery Branches

Historical architecture/recovery branches may exist from earlier work, including BASE-ARCH-017 branches. They must not become the naming pattern for future work.

The current BASE-ARCH-017 implementation branch remains governed by its existing lifecycle. New work must use the official naming convention in this document.

## 7. Next Product Feature

After BASE-ARCH-017 completes its verification/review/integration gates, the first product vertical slice will use:

```text
feature/splash-config-bootstrap
```

Feature name:

**FEATURE-001 — Splash + Config/Bootstrap**

The branch will be created from the current `development` state after the BASE-ARCH-017 integration gate is complete.

## 8. Productive Vertical-Slice Rule

Once the required base architecture contracts are frozen, the project should prioritize small real product slices rather than speculative additional base-architecture work.

The intended first slice is:

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

This document defines Git workflow only. It does not authorize implementation of FEATURE-001 by itself.
