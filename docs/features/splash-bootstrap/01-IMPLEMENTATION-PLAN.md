# Splash + Bootstrap — Implementation Plan

**Feature:** `splash-bootstrap`  
**Tracking ID:** `SPLASH-BOOTSTRAP-001`  
**Plan Status:** PROPOSED — FOUNDATION PLAN UNDER REVIEW  
**Discussion:** PARTIALLY FROZEN — EXTENDED STARTUP DISCUSSION REMAINS OPEN  
**Implementation Branch:** `feature/dynamic-ui`

## 1. Implementation Objective

Correct and complete the Bootstrap foundation so Bootstrap becomes the **first consumer of the application-wide Clean Architecture network/data infrastructure**, without creating a Bootstrap-only networking architecture that must later be replaced for Dynamic.

The implementation must preserve the frozen project direction:

```text
Kotlin Multiplatform
        ↓
Clean Architecture
        ↓
Pure Store-based MVI / UDF
        ↓
No ViewModel
```

Bootstrap is implemented first, but the network client, remote data boundary, API envelope handling and dependency-injection structure must be reusable by Dynamic and future application features.

## 2. Seven Implementation Points

The Bootstrap foundation will be implemented through exactly these seven implementation points:

```text
1. Common Network Infrastructure
2. Common RemoteDataSource
3. Bootstrap API / DTO / Mapper
4. Bootstrap Repository + Use Case
5. Splash Store Integration
6. Splash UI Integration
7. Verification / Tests / Documentation / Freeze
```

These are implementation sequence points, not separate feature architectures.

## 3. Point 1 — Common Network Infrastructure

Establish the application-wide network foundation before changing Bootstrap-specific data flow.

Responsibilities include:

- shared Ktor `HttpClient` ownership;
- common client configuration;
- target-appropriate Ktor engine strategy;
- common serialization configuration;
- common request/response transport behavior;
- common HTTP/network failure boundary;
- dependency-injection ownership of the client;
- no Bootstrap-specific HTTP client ownership.

The network layer must remain feature-agnostic.

It must not know about:

```text
Bootstrap
Dynamic
Login
OTP
Dashboard
```

## 4. Point 2 — Common RemoteDataSource

Replace the current Bootstrap-specific remote HTTP boundary with one application-wide reusable remote data boundary.

Target direction:

```text
Feature Repository
        ↓
RemoteDataSource
        ↓
Ktor HttpClient
        ↓
Backend
```

There must not be separate application-wide transport implementations such as:

```text
BootstrapRemoteDataSource
DynamicRemoteDataSource
LoginRemoteDataSource
OtpRemoteDataSource
```

Feature-specific repositories remain responsible for feature semantics; the common RemoteDataSource is responsible for reusable remote execution.

## 5. Point 3 — Bootstrap API / DTO / Mapper

Keep Bootstrap-specific API knowledge at the data boundary.

Target direction:

```text
Backend response
      ↓
API / DTO model
      ↓
Bootstrap mapper
      ↓
Bootstrap domain model
```

The response-envelope direction must support the same backend envelope shape that future Dynamic responses can use:

```text
status
code
message
data
traceId
```

A generic reusable API-envelope concept may be introduced where appropriate, while the contents of `data` remain feature-specific.

No Dynamic screen model or Dynamic renderer is implemented in this Bootstrap point.

## 6. Point 4 — Bootstrap Repository + Use Case

Restore the normal Clean Architecture dependency flow:

```text
Splash Store
    ↓
GetBootstrapConfigUseCase
    ↓
BootstrapRepository
    ↓
BootstrapRepositoryImpl
    ↓
RemoteDataSource
```

Responsibilities:

- `BootstrapRepository` belongs to the domain boundary;
- `BootstrapRepositoryImpl` belongs to data;
- `GetBootstrapConfigUseCase` belongs to domain;
- no Ktor dependency in domain;
- no networking dependency in the Store;
- no ViewModel.

The use case remains intentionally thin; it does not become a second orchestration framework.

## 7. Point 5 — Splash Store Integration

The Splash Store becomes the presentation/application-state consumer of the Bootstrap use case.

Target direction:

```text
Intent
  ↓
Splash Store
  ↓
GetBootstrapConfigUseCase
  ↓
Result
  ↓
State
```

The Store owns presentation state and lifecycle handling, not HTTP or DTO mapping.

Existing Pure MVI/UDF behavior and retry semantics remain intact unless a documented implementation conflict is discovered.

## 8. Point 6 — Splash UI Integration

Keep Splash UI platform-shared and state-driven.

Target direction:

```text
Splash UI
   ↓
Intent
   ↓
Store
   ↓
State
   ↓
UI
```

The UI must not directly access:

- Ktor;
- RemoteDataSource;
- Repository implementation;
- DTOs;
- DI container internals.

This point only reconnects the corrected Bootstrap flow to the existing Splash UI.

It does not implement the future Dynamic renderer or startup destination flow.

## 9. Point 7 — Verification / Tests / Documentation / Freeze

Verify the complete corrected Bootstrap foundation rather than only individual classes.

Verification must cover:

```text
core network
      ↓
data RemoteDataSource
      ↓
Bootstrap DTO / mapping
      ↓
Repository
      ↓
Use Case
      ↓
Splash Store
      ↓
Splash UI
```

Required automated verification will include the applicable existing KMP tests and platform/build checks.

Documentation must then record:

- implementation result;
- changed files/classes;
- tests and verification;
- deviations, if any;
- remaining Bootstrap startup scope;
- relationship to future Dynamic implementation.

## 10. Dynamic Compatibility Requirement

Although only Bootstrap is being implemented now, every shared architectural decision in this plan must remain usable by Dynamic.

Future Dynamic flow is expected to reuse:

```text
Common HttpClient
Common serialization
Common RemoteDataSource
Common DI infrastructure
Common API transport/error boundary
```

Dynamic-specific responsibilities will later include its own domain models, repositories/use cases where required, Dynamic Store/runtime behavior, registry and rendering system.

No Dynamic-specific implementation is part of this Bootstrap foundation work.

## 11. Explicit Non-Goals

This implementation plan does not implement:

- Dynamic module creation;
- Dynamic registry;
- Dynamic renderer;
- Dynamic screen models;
- Login/OTP/Dashboard Dynamic rendering;
- Dynamic actions;
- Dynamic bindings/references;
- Bootstrap persistence/cache;
- ETag/config revision behavior;
- final offline startup policy;
- maintenance/update precedence;
- authentication/session precedence;
- `nextScreen` resolution;
- final Bootstrap startup decision precedence.

Those remain separate scope and must not be silently pulled into this implementation.

## 12. Architecture Protection

The implementation must not create a Bootstrap-specific architecture that later requires replacement when Dynamic is started.

If implementation reveals a conflict with a frozen architecture decision, stop and follow the documented reopening process instead of silently changing the architecture.

## 13. Current Planning State

The seven-point implementation structure is now the proposed implementation contract for the Bootstrap foundation.

Point-by-point implementation approach will be reviewed in the active workflow before implementation begins.

The first point for implementation planning is:

**DYNAMIC/BOOTSTRAP FOUNDATION — POINT 1: Common Network Infrastructure**
