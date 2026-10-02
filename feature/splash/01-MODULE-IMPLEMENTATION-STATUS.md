# Splash / Bootstrap — Module Implementation Status

**Module:** :feature:splash  
**Work Item:** SPLASH-BOOTSTRAP-001 — Implement Application Startup and Splash  
**State:** PLAN_FROZEN / TRELLO_READY  
**Canonical branch:** feature/splash-config-bootstrap  
**Last Updated:** 2026-10-02

## 1. Current State
The Splash/Bootstrap implementation plan is frozen and the Trello execution card is READY.

No source-code implementation has been performed as part of this documentation reconciliation.

BASE-ARCH-008–013 remain decision-frozen. BASE-ARCH-017 is CODE_FROZEN and must be reused as-is.

## 2. Current Gate
DISCUSS → RESEARCH → DECIDE → PLAN → PLAN_FROZEN → TRELLO_READY → IMPLEMENTATION AUTHORIZATION.

Implementation authorization is required before source changes. The module is not yet reported as implemented, tested, reviewed, accepted, or code-frozen.

## 3. Frozen Implementation Units
| Unit | Requirement | State | Files | Tests | Review | Freeze |
|---|---|---|---|---|---|---|
| 1 | Domain contract reconciliation | NOT STARTED | — | — | — | — |
| 2 | Bootstrap Data vertical slice | NOT STARTED | — | — | — | — |
| 3 | Splash Store integration | NOT STARTED | — | — | — | — |
| 4 | Splash UI integration | NOT STARTED | — | — | — | — |
| 5 | Verification and review | NOT STARTED | — | — | — | — |

Only one unit may move to implementation at a time.

## 4. Frozen Domain Contract
    interface ApplicationBootstrap {
        suspend operator fun invoke(): Result<BootstrapOutput>
    }

BootstrapOutput contains the approved config and startup/next-screen data from the module plan. Transport status/message/traceId are not normal domain output by default.

## 5. Known Backend Inputs
Known successful response: status/code/message envelope, config, startup authentication state, next-screen metadata, and traceId.

Known request headers: X-CarBroz-Platform: ANDROID; X-CarBroz-App-Version: 1.0.0; X-CarBroz-Build-Number: 1.

Important: startup.nextScreen.endpoint = /api/v1/partner/screen/auth_login is the next-screen endpoint, not the Bootstrap endpoint.

## 6. Backend Contract Blockers
Intentionally missing and not invented:
- Bootstrap endpoint/path;
- Bootstrap HTTP method;
- Bootstrap request body, if any;
- Bootstrap authentication requirement;
- additional required headers;
- backend error response schema.

These are contract inputs, not architecture decisions.

## 7. Existing Branch Reconciliation
The pre-existing feature/splash-config-bootstrap branch is a reference/reconciliation source.

The implementation plan requires controlled reconciliation rather than wholesale merge. Planned removals include the old repository/use-case chain and duplicate branch-local network infrastructure. DTOs, mapper, API, remote data source, failure model, Splash Store/UI, and tests are the retained/reconciled foundation.

The implementation branch must follow the canonical module branch rule and be based on current development when implementation is authorized.

## 8. Planned Test Coverage
- DTO serialization and nullable fields.
- Nested config/startup/next-screen mapping.
- DTO → domain mapping and transport metadata isolation.
- Request-header attachment.
- HTTP failure and malformed response handling.
- Store initial/loading/success/error/retry.
- Duplicate Start protection, cancellation, stale-attempt protection.
- Successful output and failure propagation.
- Applicable Gradle Wrapper regression checks.

No test may fabricate an undocumented backend error schema or contact a real backend endpoint.

## 9. Non-Goals
Authentication/OTP/login/registration; dashboard; booking/payment; next-screen API; SDUI renderer/template/component registry/dynamic JSON; persistence/cache/offline bootstrap; update installation; new modules; ViewModel/global Store; duplicate Ktor/network infrastructure; unrelated architecture/dependency changes.

## 10. Acceptance / Freeze Tracking
Before module completion: implementation authorization must be recorded; units implemented one at a time; tests executed; complete diff reviewed; frozen boundaries respected; review accepted; code freeze recorded; Tracker/Trello/Current Status synchronized; checkpoint recorded when applicable.

Until then the module remains PLAN_FROZEN / TRELLO_READY.

## 11. Documentation Reconciliation Checkpoint — 2026-10-02
This status document was created as part of the durable-record reconciliation requested for SPLASH-BOOTSTRAP-001.

Documentation-only checkpoint: the stale BASE-ARCH-017 Tracker state is reconciled to CODE_FROZEN; the Splash plan and status records are established from the already-frozen plan. No source code, architecture decision, branch, or Git history is changed by this definition.

## 12. Next Valid Action
Obtain explicit implementation authorization, then begin only Unit 1 — Domain Contract Reconciliation on the canonical Splash branch after verifying the branch is based on current development.

No implementation starts automatically from this status record.