# CB-Partner Dynamic UI — Module Implementation Plan

> **Status:** PLAN FROZEN
>
> **Module:** `:feature:dynamic` container
>
> **Source of truth:** Frozen `DYNAMIC-01` → `DYNAMIC-07` discussion document.
>
> This document contains **exactly seven implementation points**. The seven points are the agreed implementation sequence. The detail inside each point describes classes, packages, ownership, dependencies, execution flow, and verification coverage required by the already-frozen Dynamic discussion. It does **not** introduce new Dynamic architecture.
>
> The fixed backend JSON contract remains authoritative. Login, OTP, Dashboard, and future business screens remain backend-defined Dynamic destinations; no business-specific native screen architecture is introduced.

---

## Point 1 — Application Network / API Foundation

### Implementation responsibility

Establish the common application API/network foundation that both Bootstrap and Dynamic use. Dynamic must not create another HTTP client, another networking module, another base-URL system, or direct Ktor calls from the feature layer.

The implementation must reconcile the existing Bootstrap networking code with the frozen runtime-infrastructure architecture so that the application has one common remote-data boundary.

### Ownership

```text
:core
    generic network-related contracts/infrastructure only

:data
    concrete HttpClient construction
    RemoteDataSource implementation
    request execution

:feature:splash
    Bootstrap domain/data usage only

:feature:dynamic
    consumes repository/domain contracts
    never owns Ktor directly
```

The existing BASE-ARCH-017 boundary remains authoritative: Core owns generic infrastructure boundaries, Data owns concrete `HttpClient` construction, and application composition/DI assembles the object graph.

### Planned structure

```text
:data
└── network
    ├── NetworkConfig
    ├── RemoteDataSource
    └── DefaultRemoteDataSource

:data
└── di
    └── NetworkModule
```

Names may follow the existing repository conventions where equivalent classes already exist; the implementation must not create duplicate network abstractions.

### HttpClient

There is one application-level Ktor `HttpClient` instance provided through DI.

```text
Koin
  ↓
HttpClient
  ↓
RemoteDataSource
```

Use the already-frozen Ktor default-engine strategy. Do not introduce a manual expect/actual engine abstraction unless an already-frozen platform requirement explicitly requires it.

### Base URL and Dynamic URLs

Normal application requests use the centrally configured base URL.

Dynamic backend actions can provide either:

```text
relative endpoint
/api/v1/partner/...
```

or a backend-defined complete URL:

```text
https://...
```

The shared `RemoteDataSource` resolves the request correctly:

```text
relative endpoint
    ↓
central base URL + endpoint
    ↓
HttpClient
```

and:

```text
complete URL
    ↓
use supplied URL according to the frozen request/security contract
    ↓
HttpClient
```

The client does not contain knowledge of Login, OTP, Dashboard, or any business endpoint.

### DI

Koin remains the existing application DI mechanism. No new `:di` module is created. The Network module is composed through the existing application composition root.

The Koin Compiler Plugin is not introduced merely because Koin is used; BASE-ARCH-017 explicitly froze ordinary Koin DSL for this infrastructure.

### Bootstrap reconciliation

Remove the Bootstrap-specific network ownership and route Bootstrap through the same `RemoteDataSource` boundary. Bootstrap remains responsible for Bootstrap behavior, but not for owning a second HTTP infrastructure.

### Point 1 execution flow

```text
Application Composition
        ↓
Koin Network Module
        ↓
Single Ktor HttpClient
        ↓
RemoteDataSource
        ├── Bootstrap requests
        └── Dynamic requests/actions
```

### Must not be introduced

- Dynamic-specific `HttpClient`
- Bootstrap-specific HTTP client
- feature-level Ktor calls
- duplicate base URL configuration
- generic endpoint registry
- business API classes inside Core
- second DI module/system

---

## Point 2 — Dynamic Models & Data/Domain Flow

### Implementation responsibility

Create the Dynamic model architecture that represents the fixed backend response cleanly across Data → Domain → Dynamic runtime while preserving Clean Architecture boundaries.

The model design must reflect the actual Login and OTP JSON supplied for this feature and the complete frozen DYNAMIC-01 → DYNAMIC-06 contract.

### API envelope

The API response is represented as one response envelope containing:

```text
status
code
message
data
traceId
```

The `data` object contains the Dynamic screen contract.

### Model ownership

```text
:data
└── DTO / serialization models

:domain
└── Dynamic domain models / contracts

:feature:dynamic
└── UI/runtime consumption
```

Data DTOs may use `kotlinx.serialization`. Domain models must not depend on Ktor or serialization implementation details.

### Dynamic model hierarchy

The domain model must represent the frozen hierarchy:

```text
DynamicScreen
├── screenId
├── schemaVersion
├── targetApp
├── template
│   ├── id
│   ├── type
│   ├── properties
│   └── components
│       ├── elements
│       └── sections
│           ├── elements
│           └── groups
│               └── elements
└── theme
```

The actual model must preserve backend ordering.

### Template / Component / Section / Group / Element models

Create typed models for the actual fixed contract rather than one untyped map that forces every renderer to parse JSON manually.

Conceptually:

```text
DynamicScreen
DynamicTemplate
DynamicComponent
DynamicSection
DynamicGroup
DynamicElement
```

Elements must support the initial backend types:

```text
text
image
```

and the fixed JSON fields required by the supplied Login/OTP responses, including their properties, nested visual structures, semantic information, bindings, validation, and actions.

### Common property model

The model layer must represent the already-frozen common capability contract without creating a property-class explosion.

The model architecture must be capable of representing the fixed backend concepts already frozen in DYNAMIC-04, including applicable:

```text
size
width / height
min/max constraints
padding
alignment
arrangement
orientation
spacing
aspectRatio
background
shape
border
shadow / elevation
alpha / opacity
semanticRole
```

The model must preserve definition-specific properties where required by the fixed contract.

### Theme model

Theme remains a Screen-level model, including the fixed JSON's theme/status-bar/gradient information.

### Action model

The Dynamic model must carry backend actions as part of the Dynamic contract without mixing action execution into rendering.

The initial frozen action types used by the supplied JSON are:

```text
request
navigate
external_uri
```

### Binding / validation / reference models

The model layer must represent the fixed backend concepts used by Login/OTP, including:

```text
binding
validation
$binding
$context
$response
```

These are data contracts. Their resolution/execution belongs to the Dynamic runtime/action flow, not to DTO parsing.

### Mapping

```text
Backend JSON
    ↓
Data DTO envelope
    ↓
DTO → Domain mapper
    ↓
Dynamic domain model
    ↓
Dynamic runtime
```

No LoginModel, OtpModel, DashboardModel, or business-specific Dynamic model is created.

### Model tests

Tests must cover the supplied Login and OTP response shapes, including nested components/sections/groups/elements, properties, actions, bindings, validation, context/response references, theme, and traceId.

---

## Point 3 — Dynamic Repository / Remote Data Flow

### Implementation responsibility

Implement the Dynamic repository boundary and connect Dynamic screen/destination loading to the common application `RemoteDataSource`.

The Dynamic feature must never call Ktor directly.

### Ownership

```text
:domain
└── Dynamic repository interface

:data
├── Dynamic repository implementation
├── Dynamic remote request/response mapping
└── RemoteDataSource usage

:feature:dynamic
└── Store/use-case/runtime consumption
```

### Planned contracts

Conceptually:

```text
DynamicRepository
    └── load(destination/request information)
```

and:

```text
DefaultDynamicRepository
    └── RemoteDataSource
```

The exact method signatures follow the fixed destination/request models rather than inventing a second API abstraction.

### Destination request

A Dynamic destination contains the backend-provided information required to obtain its screen, including the frozen destination identity and API information:

```text
screenId
    ↓
templateId
    ↓
templateType
    ↓
endpoint
    ↓
method
    ↓
authentication
```

The frontend does not infer a destination from `templateType`.

### Repository flow

```text
Dynamic Store
    ↓
Dynamic Repository
    ↓
RemoteDataSource
    ↓
Ktor HttpClient
    ↓
Backend
    ↓
API response envelope
    ↓
DTO
    ↓
Domain model
    ↓
Dynamic Store
```

### Request result boundary

Repository/data code is responsible for transport/serialization/data mapping. Dynamic runtime is responsible for consuming the domain result and applying the frozen runtime/action behavior.

The request error model from DYNAMIC-06.4.2 is reused. A second Dynamic request-error system must not be introduced.

### Destination response boundary

The destination response contract from DYNAMIC-06.4.3 is reused. A destination response must become the next Dynamic destination according to the frozen response contract; the feature must not invent screen-specific routing rules.

### Navigation rule

`templateType` selects rendering capability only. It does not select where the application navigates.

A complete backend destination is represented as a Dynamic destination and passed to the existing navigation architecture.

### No direct network ownership

Definitions, renderer, DynamicRegistry, and Compose UI must never know how to call the backend.

---

## Point 4 — Dynamic Registry & Definition Architecture

### Implementation responsibility

Implement the frozen single `DynamicRegistry` architecture and the first concrete Dynamic definitions.

There is **one public DynamicRegistry**. It internally separates the five categories.

```text
DynamicRegistry
│
├── Template registrations
├── Component registrations
├── Section registrations
├── Group registrations
└── Element registrations
```

Do not create five independent public registry systems.

### Planned package structure

The concrete package location remains inside the Dynamic feature/container because these definitions are Dynamic rendering implementations.

Conceptually:

```text
feature/dynamic
└── src/commonMain/kotlin/.../dynamic
    ├── registry
    │   └── DynamicRegistry
    │
    └── definition
        ├── template
        │   └── StackTemplateDefinition
        ├── component
        │   └── StackComponentDefinition
        ├── section
        │   └── StackSectionDefinition
        ├── group
        │   └── StackGroupDefinition
        └── element
            ├── TextDefinition
            └── ImageDefinition
```

Equivalent existing package conventions may be used without changing ownership.

### Initial registrations

```text
Template  → stack_template
Component → stack_component
Section   → stack_section
Group     → stack_group
Element   → text
Element   → image
```

### Definition ownership

Every definition owns its own Compose rendering and supported capabilities.

```text
StackTemplateDefinition
    └── renders stack_template

StackComponentDefinition
    └── renders stack_component

StackSectionDefinition
    └── renders stack_section

StackGroupDefinition
    └── renders stack_group

TextDefinition
    └── renders text

ImageDefinition
    └── renders image
```

A definition does not own:

```text
networking
repository
navigation stack
business logic
MVI Store
API execution
```

### Base definition contract

If a shared definition abstraction is required by the frozen DYNAMIC-05 rendering contract, it remains intentionally small:

```text
BaseDynamicDefinition
├── type
├── supported capabilities
└── definition rendering
```

It must not become a universal property framework or giant renderer.

### Registration

Registration is explicit and frontend-owned.

```text
DynamicRegistry
    ↓
register template/component/section/group/element
```

No:

- reflection
- package scanning
- automatic discovery
- generic plugin framework
- dynamic backend registration
- silent duplicate replacement

Duplicate registration in one category is an error.

### Resolution

Resolution is category-specific:

```text
resolveTemplate(type)
resolveComponent(type)
resolveSection(type)
resolveGroup(type)
resolveElement(type)
```

An Element type cannot accidentally resolve as a Component type.

Unknown definitions return a controlled unsupported/not-registered result.

### Capability ownership

Common capabilities are reusable, but each definition opts into only the capabilities that are legal for that type. A capability being present in the common model does not make it automatically valid for every definition.

### Definition tests

Each initial definition receives focused rendering/registration tests, including registry lookup, duplicate registration, known type resolution, and unknown type behavior.

---

## Point 5 — Dynamic Runtime / Renderer / MVI Store Integration

### Implementation responsibility

Integrate the complete Dynamic model/registry/definition system into the existing Pure MVI + UDF + Store architecture and permanent Dynamic Container.

No ViewModel is introduced.

### Dynamic Container

There is one permanent Dynamic Container in `:feature:dynamic`.

It does not become:

```text
LoginScreen
OtpScreen
DashboardScreen
```

It renders the current backend-defined Dynamic destination.

### Destination / navigation state

The Dynamic runtime works with a destination/navigation-stack model.

A destination carries the complete backend-defined information required to identify and load that destination, including:

```text
screenId
templateId
templateType
endpoint
method
authentication
```

The full destination is what belongs in the navigation/back stack, not only `templateId`.

### Store ownership

The Dynamic Store owns Dynamic runtime state and follows the project's Pure MVI + UDF pattern.

Conceptually:

```text
Intent
  ↓
Store
  ↓
State
  ↓
Effect
  ↓
Renderer / Container
```

The Store coordinates:

- current destination
- screen loading
- loading/error/success state
- Dynamic screen model
- current bindings/runtime values
- node-level interaction state
- action results/effects
- navigation effects
- recovery/fallback state

The Store does not become a generic global Store.

### Renderer

A small central `DynamicRenderer` performs recursive definition resolution and rendering:

```text
DynamicRenderer
      ↓
category-specific DynamicRegistry lookup
      ↓
registered Definition
      ↓
Definition renders itself
      ↓
Definition delegates children to DynamicRenderer
```

Parent definitions never instantiate child definitions directly.

### Recursive rendering

```text
Template
  ↓ render Components
Component
  ├── render Elements
  └── render Sections
Section
  ├── render Elements
  └── render Groups
Group
  └── render Elements
Element
  └── terminal Compose content
```

Backend ordering is preserved.

### Parent/child layout

The frozen DYNAMIC-04/DYNAMIC-05 rule is preserved:

```text
Parent
    owns container/layout/placement

Child
    owns content and legal self-sizing
```

A child cannot mutate its parent layout configuration.

### Layout/capability implementation

The runtime must support the already-frozen Dynamic capability direction, including applicable:

```text
WRAP / FILL / FIXED / FRACTION
min/max constraints
padding
orientation
arrangement
spacing
alignment
aspect ratio
background
shape
border
shadow/elevation
alpha/opacity
semantic role
theme
```

No second responsive/layout engine is introduced. Compose Multiplatform remains responsible for measurement and constraints.

### Navigation

Dynamic navigation integrates with the existing Navigation architecture.

```text
backend navigate action
        ↓
DynamicDestination
        ↓
NavigationStore / existing navigation boundary
        ↓
Dynamic Container
        ↓
Dynamic screen loading
```

System Back pops the previous full Dynamic destination. Refresh reloads the current destination without creating a new stack entry.

The frontend never decides that `form_template` means Login or that another template means Dashboard.

### Point 5 runtime flow

```text
Bootstrap
   ↓
DynamicDestination
   ↓
Dynamic Navigation
   ↓
Dynamic Container
   ↓
Dynamic Store
   ↓
Dynamic Repository
   ↓
Dynamic Screen Model
   ↓
DynamicRenderer
   ↓
DynamicRegistry
   ↓
Definition
   ↓
Compose UI
```

---

## Point 6 — Dynamic Actions / Interaction

### Implementation responsibility

Implement the complete frozen DYNAMIC-06 action/runtime interaction contract without creating a second action language or second state-management system.

Actions are backend data. The frontend runtime interprets only the supported action types and contracts that have been frozen.

### Action model

The action model must support the frozen action contract, including the initial action types present in the Login/OTP JSON:

```text
request
navigate
external_uri
```

Action definitions remain separate from visual capability definitions.

### Interaction flow

```text
Compose user event
      ↓
Dynamic Store / action runtime
      ↓
Resolve references
      ↓
Execute frozen action contract
      ↓
Result / error
      ↓
State or Effect
      ↓
Compose UI
```

### State & cross-element interaction

The implementation must preserve the frozen DYNAMIC-06 state model and UDF ownership. Element interaction can update Dynamic runtime/binding state through the Store; rendering remains derived from state and backend definition.

No element directly mutates another element's UI state.

### References

Implement the frozen reference-resolution behavior for:

```text
$binding
$context
$response
```

Examples from the supplied JSON must work:

```text
$binding → mobileNumber / otp
$context → deviceId / authFlow.phoneNumber / legal URIs
$response → data.challengeId
```

Reference resolution is runtime behavior. DTO parsing must preserve the reference rather than executing it.

### Request action

Implement the frozen request contract including its backend-defined:

```text
method
endpoint
authentication
validate
body
responseMode
```

Request body values may be resolved from bindings, context, or previous responses according to the frozen reference contract.

The request uses the common application `RemoteDataSource`.

### Request error model

DYNAMIC-06.4.2 is reused exactly as frozen. The implementation must cover the complete frozen request-error contract, including controlled request failure representation, transport/server/response handling, error propagation, user-facing state, diagnostic information, and the frozen separation between request errors and rendering errors.

No second request error hierarchy is invented in DYNAMIC-07 or the feature UI.

### Destination response schema

DYNAMIC-06.4.3 is reused exactly as frozen. Destination responses are converted into the backend-defined next Dynamic destination according to the frozen schema and then passed through the Dynamic navigation/runtime flow.

The implementation must preserve the frozen distinction between ordinary request results and destination responses.

### Navigate action

A `navigate` action supplies the destination information required by the backend contract.

```text
screenId
templateId
templateType
endpoint
method
authentication
```

The runtime creates the full Dynamic destination and sends it through the existing navigation boundary.

### External URI action

`external_uri` remains a controlled capability. The URI is resolved from the backend reference contract where applicable and passed to the approved platform/navigation capability. It does not become arbitrary executable code.

### Action loading/state behavior

Loading, success, failure, and completion are represented through the Dynamic Store/UDF state/effect flow according to the frozen action contract. Rendering and action execution remain separate responsibilities.

### Action safety

Backend JSON cannot create executable frontend behavior. The action runtime can only interpret action types and fields explicitly supported by the frontend implementation.

### Action tests

Tests must cover:

```text
binding resolution
context resolution
response resolution
request action
request validation flag
request body resolution
request error model
destination response
navigate action
external_uri action
loading/result/error state
```

and the actual Login/OTP actions supplied in the JSON.

---

## Point 7 — Integration, Testing & Final Dynamic Completion

### Implementation responsibility

Complete the Dynamic implementation by integrating and verifying **all frozen DYNAMIC-07 Runtime Validation & Compatibility decisions and all previously frozen Dynamic behavior**. This point is not permission to create a new validation engine or compatibility framework.

The backend is responsible for sending a valid Dynamic structure. The frontend does **not** duplicate backend structural validation.

### DYNAMIC-07 runtime boundary

The runtime pipeline remains intentionally small:

```text
Parse
  ↓
Resolve registered definitions
  ↓
Apply supported capabilities
  ↓
Render
  ↓
Diagnose when runtime support is missing
  ↓
Fallback / recover according to the frozen boundary
```

There is no large frontend:

```text
StructuralValidator
CompatibilityEngine
MigrationEngine
Normalizer
FallbackEngine
RecoveryEngine
```

### Structural responsibility

The implementation must **not** create a second frontend SDUI structural-validation engine. The backend already guarantees the canonical hierarchy.

The frontend runtime simply consumes the fixed hierarchy:

```text
Screen
  ↓
Template
  ↓
Component
  ↓
Element / Section
  ↓
Element / Group
  ↓
Element
```

The frontend still remains safe when a received definition is unsupported or a runtime capability cannot be executed.

### Definition / capability resolution

The runtime distinguishes:

```text
UNKNOWN_DEFINITION
```

from:

```text
UNSUPPORTED_CAPABILITY
```

Unknown means the type is not registered. Unsupported means the registered definition exists but the requested capability/configuration is not supported by that definition.

The runtime never guesses, substitutes another definition, or silently downgrades a backend value.

### Unknown definition handling

Use the frozen category-specific behavior:

```text
Unknown Template
    → screen-level fallback

Unknown Component
    → component-level error/fallback

Unknown Section
    → section-level error/fallback

Unknown Group
    → group-level error/fallback

Unknown Element
    → element-level error/fallback
```

An unknown child must not destroy an otherwise valid screen. Template is the screen-level exception because it determines the screen layout strategy.

The runtime fallback UI is not itself registered as a Dynamic definition.

### Unsupported capability handling

A registered definition that receives an unsupported capability produces a controlled runtime result. The implementation must not automatically replace it with another value.

Compatibility mappings are allowed only when explicitly defined by a future/frozen contract; the runtime does not guess mappings.

### Runtime error model and diagnostics

Runtime errors remain structured rather than plain strings.

Diagnostic context may include, where available:

```text
reasonCode
level
type
nodeId
screenId
templateId
schemaVersion
traceId
capability
message
```

User-facing fallback content remains separate from diagnostic information.

### DYNAMIC-07.6 safety and recovery coverage

The implementation must verify every frozen runtime safety/recovery rule:

```text
Dynamic failure must not crash the application.

Child failures are isolated whenever safely possible.

Template failure switches the Dynamic screen to screen-level fallback.

Action failures are isolated from rendering.

No implicit or infinite retry exists.

Invalid runtime values use capability-specific safe handling; there is no universal silent default.

A child failure cannot mutate or redefine its parent's rendering configuration/ownership.

Unaffected Dynamic state is preserved during localized recovery.

Destination/navigation failure preserves the current valid screen.

Request failures follow DYNAMIC-06.4.2 rather than a second error system.

Recovery is deterministic; the runtime does not guess alternative definitions.

Recovery never mutates registry definitions, application architecture, or executable frontend behavior.
```

### DYNAMIC-07.7 Observability & debugging coverage

The implementation must include the frozen observability boundary established by DYNAMIC-07.7.

#### Observable lifecycle

Dynamic runtime lifecycle events must be observable through the existing logging/diagnostic infrastructure without creating a separate Dynamic logging framework.

The observable lifecycle covers the major runtime phases:

```text
screen/destination received
→ parsing/model availability
→ definition resolution
→ rendering
→ action/request execution
→ state transitions/results
→ fallback/recovery
```

#### Existing logging infrastructure

All Dynamic diagnostics use the existing application logging abstraction. No separate Dynamic logger system is created.

#### Structured diagnostic context

Diagnostics carry structured Dynamic context rather than only free-form messages. Where available, preserve identifiers such as:

```text
traceId
screenId
templateId
nodeId
type
level
schemaVersion
reasonCode
capability
```

#### Definition-resolution diagnostics

Unknown/unregistered Template, Component, Section, Group, or Element resolution must produce diagnostic information identifying the category/type/node and relevant screen context.

#### Action/request tracing

Dynamic actions and backend requests must be traceable through the existing logging infrastructure using the runtime context and trace identifiers available from the frozen contract. Tracing must not log sensitive request/response content by default.

#### State diagnostics

State diagnostics must identify meaningful Dynamic state transitions and failures without dumping entire application state or sensitive binding values into logs.

#### Sensitive-data protection

Sensitive data must not be logged. This includes values such as OTPs, phone numbers where sensitive, authentication material, request bodies containing sensitive values, tokens, secrets, and other protected runtime values.

Diagnostics must log safe metadata rather than raw sensitive payloads.

#### JSON/backend response diagnostics

Backend response diagnostics may record safe metadata such as status/code/schemaVersion/traceId and controlled parse/support failures. Full raw backend JSON must not be emitted into production logs or otherwise expose sensitive content.

#### Development/debug mode

Development/debug mode may expose richer diagnostic detail than production, but it must still respect sensitive-data protection. Debug logging is an observability mode, not a separate runtime architecture.

#### Diagnostic filtering/verbosity

The implementation uses the existing logging infrastructure's filtering/verbosity controls. Dynamic does not invent another logging configuration system.

#### Production observability boundary

Production logs contain only the approved operational/diagnostic information needed to understand Dynamic behavior and failures. Debug-only details remain outside the production boundary.

#### Final observability boundary

The final Dynamic observability implementation must make the runtime understandable without exposing protected data and without changing runtime behavior merely for diagnostics.

### Final Dynamic integration tests

The completed implementation must verify the real backend-driven Login and OTP flows using the supplied JSON contract.

At minimum:

```text
Bootstrap
    ↓
Dynamic destination
    ↓
Login response
    ↓
form_template
    ↓
stack_component / stack_section / stack_group
    ↓
text / image / input / button data
    ↓
render
    ↓
mobileNumber binding
    ↓
send_otp request
    ↓
OTP destination response
    ↓
OTP response
    ↓
OTP rendering
    ↓
otp binding
    ↓
$response challengeId
    ↓
verify_otp request
    ↓
destination response
    ↓
next Dynamic destination
```

### Verification coverage

The final test suite must cover the frozen Dynamic behavior across the common testable architecture and applicable platform verification surfaces, including:

```text
JSON decoding
DTO → Domain mapping
DynamicRegistry registration/lookup
initial definition registration
unknown definitions
capability support
recursive rendering selection
Dynamic Store/MVI state
bindings
context references
response references
actions
request execution
request errors
destination responses
navigation/back stack
external URI capability
runtime fallbacks
state preservation
observability/diagnostics
sensitive-data protection
```

Where the platform cannot execute a particular test on Windows, use the repository's existing documented multiplatform verification boundary; do not change architecture to work around a platform test limitation.

### Final acceptance

The Dynamic implementation is complete only when:

```text
all seven implementation points completed
        ↓
Login + OTP backend JSON rendered dynamically
        ↓
registered definitions resolve correctly
        ↓
actions execute through common infrastructure
        ↓
navigation is backend destination-driven
        ↓
unknown/unsupported definitions recover safely
        ↓
request/navigation/runtime failures are contained
        ↓
observability works without sensitive-data leakage
        ↓
required tests/build verification pass
        ↓
implementation status/review documentation updated
        ↓
Dynamic implementation freeze
```

No business-specific Login/OTP/Dashboard renderer is allowed as a shortcut.

---

## Frozen Implementation Rules

The implementation follows the existing CB-Partner architecture:

```text
Kotlin Multiplatform
Compose Multiplatform
Clean Architecture
MVI + UDF
Pure MVI Store
No ViewModel
Multi-module architecture
Gradle convention plugins
Koin DI
Ktor networking
Existing Navigation architecture
```

The implementation must preserve the following boundaries:

```text
Core
    generic infrastructure

Domain
    contracts + Dynamic domain models/repository interfaces

Data
    DTOs + mappers + repository implementations + RemoteDataSource

Navigation
    navigation runtime/integration

feature:splash
    Bootstrap feature behavior

feature:dynamic
    Dynamic Container + Store/runtime + Registry + Definitions + Renderer + Dynamic UI
```

The Dynamic feature is the runtime UI container, not a replacement for the application's common Data/Domain/networking architecture.

The implementation must not introduce:

- a second networking stack;
- a second `HttpClient`;
- a second public registry architecture;
- a second navigation system;
- ViewModel;
- a global Dynamic Store;
- a large frontend structural validator;
- a compatibility/migration engine;
- automatic renderer discovery;
- arbitrary backend code execution;
- business-specific native Login/OTP/Dashboard screen classes;
- a new universal property framework;
- a new Dynamic logging framework;
- silent definition substitution;
- silent property downgrade;
- implicit/infinite retry.

The backend JSON contract and all frozen DYNAMIC-01 → DYNAMIC-07 decisions remain authoritative. Implementation must not reopen those decisions.

If repository code contradicts a frozen contract, stop at that conflict and report it rather than silently changing the architecture.

**Implementation plan state: FROZEN — exactly 7 points.**
