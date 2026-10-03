# CB-Partner Dynamic UI — Module Implementation Plan

> **Status:** PLAN FROZEN
>
> **Module:** `:feature:dynamic` container
>
> **Source of truth:** Frozen `DYNAMIC-01` → `DYNAMIC-07` discussion.
>
> This document contains exactly the seven agreed implementation points. It is an implementation sequence, not a new architecture discussion. Frozen Dynamic decisions must not be changed during implementation.

---

## Point 1 — Application Network / API Foundation

Establish the common application API/network foundation that Bootstrap and Dynamic both use. There is one application-level `RemoteDataSource` boundary and one centrally configured Ktor `HttpClient`; Dynamic does not create its own HTTP client, base networking stack, or duplicate API infrastructure. The base URL is centrally configured for normal application API requests. Dynamic request actions may provide the backend-defined endpoint path or a complete backend-defined URL according to the frozen request contract; the runtime must use the common client/network boundary rather than inventing endpoint knowledge. Configure Ktor engines through the already-frozen multiplatform runtime infrastructure and wire the common client through the existing DI composition root. Reconcile the existing Bootstrap networking implementation with this common foundation rather than keeping Bootstrap-specific HTTP infrastructure.

Implementation flow:

```text
Application DI
    ↓
Common HttpClient
    ↓
RemoteDataSource
    ↓
Bootstrap / Dynamic data flows
```

No second HTTP client, no Dynamic-specific networking layer, and no business endpoint knowledge in the common client.

---

## Point 2 — Dynamic Models & Data/Domain Flow

Create the Dynamic response/model architecture according to Clean Architecture. The API response remains an envelope containing status/code/message/data/traceId; `data` contains the Dynamic screen contract including screen metadata, template, components, sections, groups, elements, properties, theme, actions, bindings, validation, and references required by the frozen backend contract. Data-layer DTOs represent the serialized backend contract and map into domain Dynamic models. Domain models must not depend on Ktor or serialization implementation details. The model hierarchy preserves backend ordering and the complete Dynamic information needed by the runtime. No business-specific Login/OTP/Dashboard models are introduced.

Flow:

```text
Backend JSON
    ↓
Data DTOs
    ↓
Mapper
    ↓
Domain Dynamic models
    ↓
Dynamic runtime
```

---

## Point 3 — Dynamic Repository / Remote Data Flow

Implement the Dynamic repository/data flow using the common application networking foundation. The repository is the domain-facing boundary; its implementation lives in `:data` and uses the single application `RemoteDataSource`. Dynamic screen/destination requests, response decoding, DTO mapping, and request results remain inside the data/domain boundaries. The Dynamic feature consumes domain contracts and does not call Ktor directly. Bootstrap and Dynamic therefore share the same API client and remote-data infrastructure while keeping their repository/domain responsibilities separate.

Flow:

```text
Dynamic Store / use case
    ↓
Dynamic Repository
    ↓
RemoteDataSource
    ↓
Common HttpClient
    ↓
Backend
    ↓
DTO
    ↓
Domain model
```

No second network stack and no direct HTTP calls from definitions, renderers, or UI.

---

## Point 4 — Dynamic Registry & Definition Architecture

Implement one `DynamicRegistry` that internally separates registration and lookup for the five frozen categories: Template, Component, Section, Group, and Element. Do not create separate top-level registry systems. Each registered definition owns its own Compose rendering and supported capabilities. Initial registrations are `stack_template`, `stack_component`, `stack_section`, `stack_group`, `text`, and `image`. Registry resolution uses the backend type and the appropriate category. Unknown definitions are never guessed, substituted, silently ignored, or dynamically created. The registry contains rendering capability only; it does not own business behavior, networking, navigation, repositories, or MVI state.

Structure:

```text
DynamicRegistry
├── Template registrations
├── Component registrations
├── Section registrations
├── Group registrations
└── Element registrations
```

Initial definitions:

```text
stack_template
stack_component
stack_section
stack_group
text
image
```

---

## Point 5 — Dynamic Runtime / Renderer / MVI Store Integration

Integrate Dynamic models and definitions into the existing Pure MVI + UDF + Store architecture without ViewModel. The Dynamic Store owns Dynamic runtime state and coordinates screen loading, rendering state, bindings, node state, and runtime results. The renderer consumes the domain Dynamic model, resolves the correct registered definition through `DynamicRegistry`, and recursively renders the backend-defined tree. Definitions render themselves and delegate child rendering back through the Dynamic renderer. The Dynamic container is the feature UI boundary; business screens are not hardcoded. Parent/child rendering follows the frozen Dynamic hierarchy and state ownership decisions.

Flow:

```text
Dynamic Destination
    ↓
Dynamic Store
    ↓
Repository
    ↓
Dynamic Domain Model
    ↓
Dynamic Renderer
    ↓
DynamicRegistry
    ↓
Definition
    ↓
Compose UI
```

---

## Point 6 — Dynamic Actions / Interaction

Implement the frozen DYNAMIC-06 action system. Backend-defined actions are represented by Dynamic action models and executed by the Dynamic runtime through the Pure MVI/UDF flow. References such as binding, context, and response values are resolved by the existing Dynamic reference mechanism before execution. `request`, `navigate`, and `external_uri` remain distinct action types with their frozen contracts; request execution uses the common application networking infrastructure. Request validation/error handling follows DYNAMIC-06.4.2, and destination responses follow DYNAMIC-06.4.3. Action failures are isolated from rendering and follow the frozen DYNAMIC-07 recovery rules. No second action, network, or error framework is created for Dynamic.

Flow:

```text
UI event
    ↓
Dynamic Store / Action runtime
    ↓
Reference resolution
    ↓
Action
    ├── request
    ├── navigate
    └── external_uri
    ↓
Result / error
    ↓
State / Effect
    ↓
UI
```

---

## Point 7 — Integration, Testing & Final Dynamic Completion

Integrate all frozen Dynamic pieces and verify the complete backend-driven flow without introducing new architecture. Register the initial definitions, load and render the real Login and OTP JSON responses, verify bindings/context/response references, execute the configured request/navigation/external-URI actions, and verify destination responses. Verify the frozen DYNAMIC-07 behavior for unknown definitions, localized child failures, template-level failure, request/action failures, navigation failure, state preservation, deterministic recovery, diagnostics, and sensitive-data protection. The final end-to-end flow must demonstrate Bootstrap → Dynamic destination → Login → request → OTP → request → destination while keeping Login and OTP backend-driven rather than hardcoded native screens. Complete build/tests, review the changed files/classes, update implementation/status documentation, and freeze the completed Dynamic implementation.

Final verification flow:

```text
Bootstrap
    ↓
Dynamic Destination
    ↓
Login JSON
    ↓
Login rendered dynamically
    ↓
send_otp request
    ↓
OTP destination
    ↓
OTP JSON
    ↓
OTP rendered dynamically
    ↓
verify_otp request
    ↓
Destination response
    ↓
Next Dynamic screen
```

---

## Frozen Implementation Rules

The implementation follows the existing CB-Partner architecture: Clean Architecture, Kotlin Multiplatform / Compose Multiplatform, Pure MVI + UDF + Store, no ViewModel, multi-module boundaries, existing DI, common networking, and existing navigation. The implementation must not create a second networking layer, second registry architecture, second state-management system, second navigation system, or business-specific Dynamic screen classes. Do not reopen frozen DYNAMIC-01 → DYNAMIC-07 decisions during implementation. If the repository contradicts a frozen contract, stop and report the conflict instead of silently changing the architecture.

**Implementation plan state: FROZEN — exactly 7 points.**
