# Splash / Bootstrap — Module Implementation Plan

**Module:** :feature:splash  
**Work Item:** SPLASH-BOOTSTRAP-001 — Implement Application Startup and Splash  
**State:** PLAN_FROZEN / TRELLO_READY  
**Scope:** Frontend repository only  
**Canonical branch:** feature/splash-config-bootstrap

## 1. Purpose
Complete the first real Splash → Bootstrap API vertical slice using the already-frozen BASE-ARCH-008–013 startup/bootstrap boundaries and the CODE_FROZEN BASE-ARCH-017 runtime infrastructure.

This plan is an implementation contract. It does not reopen architecture decisions.

## 2. Frozen Inputs
- BASE-ARCH-008 through BASE-ARCH-013.
- BASE-ARCH-017 — Runtime Infrastructure Implementation Boundary.
- AI_START_HERE.md and the frozen project workflow documents.
- Trello card: SPLASH-BOOTSTRAP-001 — Implement Application Startup and Splash.

The existing feature/splash-config-bootstrap implementation is a reconciliation/reference source, not an authority over frozen decisions.

## 3. Scope
### In scope
- Reconcile existing Splash/bootstrap work with the frozen architecture.
- Establish the direct ApplicationBootstrap contract.
- Map the supplied backend bootstrap success response into domain output.
- Implement the real Bootstrap request only when its missing backend request contract is supplied.
- Reuse BASE-ARCH-017 Ktor/DI runtime infrastructure.
- Integrate bootstrap execution into the Pure Store-based Splash lifecycle.
- Preserve cancellation, retry, duplicate-start protection, and stale-attempt protection.
- Add focused DTO, mapping, API/data, and Store tests.
- Synchronize module status, tracker, and relevant execution records.

### Out of scope
- Authentication, OTP, login/registration implementation.
- Dashboard, booking, payment.
- Next-screen API implementation.
- SDUI renderer, templates, component registry, dynamic JSON architecture.
- Persistence/cache/offline bootstrap.
- Update installation.
- New Gradle modules.
- New networking architecture or duplicate Ktor infrastructure.
- ViewModel, global Store, business repositories/use cases.
- Backend implementation or backend documentation changes.
- Unrelated refactoring or dependency upgrades.

## 4. Architecture Boundary
Backend JSON → Bootstrap DTO → Mapper → BootstrapOutput → ApplicationBootstrap → Splash Store → Effect/controlled continuation → Navigation.

Ownership: Splash Store → Domain ApplicationBootstrap contract → Data implementation → Core / BASE-ARCH-017 network infrastructure.

:feature:splash must not depend directly on :data. The Store does not construct an HttpClient, call Ktor, or navigate directly from API/domain code.

## 5. Domain Contract
Public application contract:

    interface ApplicationBootstrap {
        suspend operator fun invoke(): Result<BootstrapOutput>
    }

BootstrapOutput contains:
- config.version
- config.maintenance.enabled, title?, message?
- config.update.required, optional, minimumVersion, latestVersion, storeUrl?
- config.features.registrationEnabled, individualPartnerEnabled, organizationPartnerEnabled
- startup.authenticated
- startup.nextScreen.screenId, templateId, templateType, endpoint, method, authentication

Transport envelope fields status, message, and traceId do not automatically become domain output.

## 6. Supplied Backend Success Contract
The known successful response contains status 200, code SUCCESS, the config/startup data described above, and traceId.

The nested startup.nextScreen.endpoint value /api/v1/partner/screen/auth_login is the next-screen endpoint, not the Bootstrap endpoint. Never document or implement it as the Bootstrap request path.

Known request headers:
- X-CarBroz-Platform: ANDROID
- X-CarBroz-App-Version: 1.0.0
- X-CarBroz-Build-Number: 1

These are request metadata, not Splash domain state.

## 7. Backend Contract Inputs Still Missing
Do not invent:
- Bootstrap endpoint/path.
- Bootstrap HTTP method.
- Bootstrap request body, if any.
- Bootstrap authentication requirement.
- Additional required request headers.
- Backend error response schema.

If supplied backend information materially changes the frozen plan, stop and update/re-approve the plan before implementation.

## 8. Data Layer
Retain/reconcile BootstrapResponseDto and nested DTOs, BootstrapMapper, BootstrapApi, BootstrapRemoteDataSource, BootstrapRemoteDataSourceImpl, and BootstrapFailure.

Add/reconcile ApplicationBootstrapImpl.

Remove the old repository/use-case chain because BASE-ARCH-013 freezes the direct application bootstrap contract:
- BootstrapRepository
- BootstrapRepositoryImpl
- GetBootstrapConfigUseCase
- GetBootstrapConfigUseCaseTest

Remove duplicate branch-local network infrastructure:
- HttpClientFactory
- NetworkConfig

Reuse BASE-ARCH-017 runtime Ktor/DI infrastructure. Do not introduce a second HttpClient factory or network abstraction.

## 9. Splash Store Lifecycle
Initial → Start → Loading → ApplicationBootstrap().
Success → Success state → Effect/controlled continuation.
Failure → Error state → Retry intent → new attempt.

Requirements: one active bootstrap attempt; structured concurrency; cancellation support; stale-attempt protection; retry through Intent; reducer-driven state; success output propagation; no direct Data dependency; no direct Ktor/API access; no direct navigation from bootstrap implementation.

startup.nextScreen is destination metadata, not itself a NavigationCommand.

## 10. UI Boundary
SplashScreen renders Store state and emits intents. It must not contain networking/business logic, create transport objects, or bypass the Store.

UI changes are limited to the Splash lifecycle required by this vertical slice.

## 11. Ordered Implementation Units
### Unit 1 — Domain Contract Reconciliation
Reconcile the direct ApplicationBootstrap → Result<BootstrapOutput> contract, existing domain models, and removal of the repository/use-case path.

### Unit 2 — Bootstrap Data Vertical Slice
Reconcile DTOs, mapper, API, remote source, failure handling, and ApplicationBootstrapImpl; reuse BASE-ARCH-017; attach known request metadata; implement only the supplied backend request contract; add focused Data tests.

### Unit 3 — Splash Store Integration
Connect the Store to ApplicationBootstrap; implement loading/success/error/retry; enforce duplicate-start, cancellation, and stale-attempt protections; add Store tests.

### Unit 4 — Splash UI Integration
Reconcile SplashScreen with Store state/intents/effects while keeping networking/business logic out of UI.

### Unit 5 — Verification and Review
Run focused tests and required regression/build checks, inspect the complete diff, and review against BASE-ARCH-008–013, BASE-ARCH-017, this plan, and all non-goals.

Only one implementation unit may be active at a time.

## 12. Planned Reconciliation Inventory
| Existing item | Action |
|---|---|
| Bootstrap DTOs | RETAIN |
| BootstrapMapper | MODIFY |
| BootstrapApi | MODIFY |
| BootstrapRemoteDataSource | RETAIN |
| BootstrapRemoteDataSourceImpl | MODIFY |
| BootstrapFailure | MODIFY |
| ApplicationBootstrapImpl | ADD/RECONCILE |
| BootstrapRepository | REMOVE |
| BootstrapRepositoryImpl | REMOVE |
| GetBootstrapConfigUseCase | REMOVE |
| GetBootstrapConfigUseCaseTest | REMOVE |
| Branch-local HttpClientFactory | REMOVE |
| Branch-local NetworkConfig | REMOVE |
| SplashContract | MODIFY |
| SplashStore | MODIFY |
| SplashScreen | MODIFY |
| SplashStoreTest | MODIFY |
| Module build files | MODIFY only where required |

The old branch is not to be merged wholesale.

## 13. Testing Strategy
DTO/serialization: supplied success response, nullable fields, nested config/startup/next-screen, malformed response.

Mapping: DTO → BootstrapOutput, preservation of known fields, transport metadata isolation.

API/Data: known request headers, exact supplied HTTP contract, HTTP failure handling, malformed response handling, no real backend in unit tests, no fabricated error schema.

Store: initial/loading/success/error/retry, duplicate Start protection, cancellation, stale-attempt protection, success/failure propagation.

Regression: required Gradle Wrapper checks with Windows iOS simulator limitation recorded rather than suppressed.

## 14. Acceptance Criteria
1. BASE-ARCH-008–013 remain unchanged and respected.
2. BASE-ARCH-017 remains CODE_FROZEN and is reused rather than duplicated.
3. ApplicationBootstrap is the direct domain bootstrap contract.
4. BootstrapOutput contains the approved application-facing bootstrap data.
5. No Splash → Data dependency exists.
6. No repository/use-case chain remains for this bootstrap path.
7. DTO → Mapper → Domain output flow is preserved.
8. Known request headers are supported.
9. Bootstrap endpoint/method/body/auth/error schema is implemented only from supplied backend facts.
10. nextScreen.endpoint is not confused with the Bootstrap endpoint.
11. Splash uses Pure Store MVI/UDF with no ViewModel.
12. Duplicate-start, cancellation, retry, and stale-attempt behavior are tested.
13. Transport metadata does not become normal domain state without an explicit requirement.
14. No auth/SDUI/booking/payment/next-screen API implementation is introduced.
15. Required tests and regression checks pass.
16. Review finds no blocker or unauthorized scope.
17. Module Status, Tracker, and relevant Trello state are synchronized.

## 15. Documentation / Execution Records
- feature/splash/00-MODULE-IMPLEMENTATION-PLAN.md
- feature/splash/01-MODULE-IMPLEMENTATION-STATUS.md
- docs/project-management/12-PROJECT-TRACKER.md
- docs/project-management/04-CURRENT-STATUS.md
- Trello card: SPLASH-BOOTSTRAP-001 — Implement Application Startup and Splash

## 16. Plan Freeze
Plan state: PLAN_FROZEN.
Implementation authorization is a separate gate. This documentation update does not authorize source-code implementation.

Material deviation requires: STOP → REPORT → DISCUSS → DECIDE → UPDATE PLAN → RE-APPROVE → IMPLEMENT.