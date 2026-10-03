# CB-Partner Dynamic UI — Module Implementation Plan

> **Status:** REVIEW DRAFT — not frozen and not an implementation mandate yet.
>
> **Module:** `:feature:dynamic`
>
> **Discussion source:** `docs/dynamic/00-FEATURE-DISCUSSION.md` (to be relocated to `docs/features/dynamic/` as part of the documentation reorganization).
>
> **Purpose:** Translate the fully frozen Dynamic architecture discussion into a concrete implementation sequence without reopening the frozen architecture.

---

## 1. Implementation Goal

Implement the backend-driven Dynamic UI runtime for CB-Partner as one generic feature system.

The implementation must support the frozen flow:

```text
Bootstrap
   ↓
DynamicDestination
   ↓
Navigation
   ↓
Dynamic Container
   ↓
Screen API
   ↓
Dynamic Screen JSON
   ↓
Parse / Map
   ↓
Registry Resolution
   ↓
Definition Rendering
   ↓
Dynamic State / Actions
   ↓
Compose UI
```

The frontend must not introduce native Login/OTP/Dashboard screen classes or infer business navigation from template types.

---

## 2. Frozen Inputs

This plan is derived from the frozen Dynamic discussion:

- DYNAMIC-01 — Dynamic Container + Dynamic Screen Lifecycle
- DYNAMIC-02 — Screen → Template → Component → Section → Group → Element
- DYNAMIC-03 — Registration System
- DYNAMIC-04 — Layout, Visual Capabilities & Definition Rendering Direction
- DYNAMIC-05 — Dynamic Definition, Registry & Parent → Child Rendering
- DYNAMIC-06 — Dynamic Actions & Interaction
- DYNAMIC-07.1 — Dynamic Structure Validation
- DYNAMIC-07.2 — Definition / Capability Resolution
- DYNAMIC-07.3 — Unknown / Unsupported Definitions
- DYNAMIC-07.4 — Runtime Error Reporting & Diagnostics
- DYNAMIC-07.5 — Fallback Strategy
- DYNAMIC-07.6 — Dynamic Runtime Safety & Recovery
- DYNAMIC-07.7 — Dynamic Observability & Debugging

The implementation must not reopen these architectural decisions during implementation planning.

---

## 3. Existing Module Boundary

Current module:

```text
feature/dynamic/
└── build.gradle.kts
```

Current dependencies are intentionally small:

```text
:domain
:core
:navigation
```

No new dependency/library is added by this plan unless a separately reviewed decision explicitly requires it.

The Dynamic module remains responsible for the live Dynamic screen lifecycle, rendering coordination, Dynamic state/action runtime, and Dynamic-specific runtime behavior. Networking infrastructure remains in the existing data/core boundaries. Navigation remains integrated through `:navigation`.

---

## 4. Target Runtime Boundary

The implementation should remain intentionally simple:

```text
Dynamic Container
      ↓
Dynamic Screen Store / runtime state
      ↓
Destination screen loading
      ↓
Dynamic response decoding/mapping
      ↓
Dynamic screen model
      ↓
Dynamic Renderer
      ↓
Dynamic Registry
      ↓
Definition
      ↓
Compose
```

Interaction follows:

```text
Compose event
      ↓
Dynamic Action Runtime
      ↓
Reference resolution
      ↓
request / navigate / present / dismiss / state / external_uri / sequence
      ↓
Dynamic State / Navigation / Data infrastructure
      ↓
reactive rendering
```

Do not introduce unnecessary chains such as a generic command framework, migration engine, plugin framework, second validation engine, or duplicate networking layer.

---

## 5. Implementation Units

Implementation will be executed in small reviewable units. Each unit must compile and have focused tests before the next unit is started.

### UNIT-01 — Dynamic Core Models

Implement the minimum models required by the frozen contract:

- `DynamicDestination`
- Dynamic screen model
- Template model
- Component model
- Section model
- Group model
- Element model
- screen-level Theme/configuration models required by the fixed JSON contract
- request/action models required by DYNAMIC-06
- structured runtime/request error models required by DYNAMIC-06 and DYNAMIC-07

Rules:

- models represent the fixed backend contract;
- no business-screen-specific models;
- no speculative fields;
- preserve backend ordering;
- preserve `templateId`, `templateType`, `screenId`, endpoint, method, and authentication semantics.

Tests:

- model construction;
- equality/value behavior where applicable;
- representative fixed JSON decoding/mapping cases.

---

### UNIT-02 — Dynamic Screen Decoder / Mapping

Implement the response boundary that converts the fixed API response into Dynamic runtime models.

Flow:

```text
API response envelope
      ↓
 data
      ↓
Dynamic decoder
      ↓
Dynamic screen model
```

Responsibilities:

- decode the fixed JSON;
- preserve screen/template/node properties;
- preserve backend ordering;
- map backend `nextScreen` into `DynamicDestination` where applicable;
- normalize only what the frozen contract explicitly requires.

Do not create a second backend validation engine.

Tests:

- valid screen JSON;
- nested Component → Section → Group trees;
- Theme;
- actions;
- references;
- destination response;
- malformed/unsupported decoding results.

---

### UNIT-03 — Dynamic Registry

Implement the frozen registry structure:

```text
DynamicRegistry
├── TemplateRegistry
├── ComponentRegistry
├── SectionRegistry
├── GroupRegistry
└── ElementRegistry
```

Implement:

- explicit registration;
- category-specific lookup;
- duplicate registration rejection;
- controlled unknown-definition result.

Initial definitions:

```text
stack_template
stack_component
stack_section
stack_group
text
image
```

No reflection, scanning, generic plugin framework, or automatic discovery.

Tests:

- successful registration;
- lookup by category;
- duplicate registration;
- unknown type;
- wrong-category lookup.

---

### UNIT-04 — Dynamic Definition Contracts

Implement the small definition contract established by DYNAMIC-05.

Planned definitions:

```text
BaseDynamicDefinition
StackTemplateDefinition
StackComponentDefinition
StackSectionDefinition
StackGroupDefinition
TextDefinition
ImageDefinition
```

Each concrete definition owns its Compose rendering and supported capabilities.

The base definition remains small and reusable. It must not become a universal property framework.

Definitions must not own:

- navigation;
- networking;
- repositories;
- business logic;
- MVI Store ownership;
- action execution;
- global application state.

Tests:

- supported capability declarations;
- definition-specific configuration;
- legal child handling;
- unsupported capability behavior.

---

### UNIT-05 — Dynamic Renderer

Implement one central recursive `DynamicRenderer`.

Flow:

```text
DynamicRenderer
      ↓
category registry lookup
      ↓
Dynamic Definition
      ↓
Compose rendering
      ↓
child → DynamicRenderer
```

Responsibilities:

- resolve the correct category;
- preserve backend order;
- delegate rendering to definitions;
- recursively render legal children;
- produce controlled runtime failure for unknown/unsupported definitions;
- preserve parent/child responsibility.

No parent definition directly constructs concrete child definitions.

Tests:

- complete legal tree;
- mixed Component children;
- mixed Section children;
- Group terminal behavior;
- unknown child behavior;
- unknown Template behavior;
- rendering order.

---

### UNIT-06 — Dynamic State Store / Runtime State

Implement destination-scoped Dynamic state using the project's Pure MVI + UDF + Store architecture.

State must support the frozen concepts required by DYNAMIC-06:

```text
fields / bindings
node states
visible
enabled
selected
loading
expanded
timer/countdown
request lifecycle
response context
```

There must be one canonical owner for Dynamic field/runtime state. Avoid duplicated local canonical state, secondary form stores, or ViewModels.

Tests:

- field value changes;
- node state changes;
- cross-element state updates;
- preservation of unaffected state;
- request lifecycle state.

---

### UNIT-07 — Reference Resolution

Implement centralized deterministic resolution for:

```text
$literal
$binding
$context
$response
```

Rules:

- recursive resolution where the contract requires it;
- type-safe consumption;
- missing/invalid references produce structured errors;
- no invented defaults that silently change behavior;
- references read values; actions perform effects.

Tests:

- literal;
- binding;
- context;
- response;
- nested references;
- missing reference;
- invalid type.

---

### UNIT-08 — Action Runtime

Implement the frozen action vocabulary:

```text
request
navigate
present
dismiss
state
external_uri
sequence
```

Events include the frozen base events:

```text
onClick
onLongClick
onValueChange
onFocus
onSubmit
```

Responsibilities:

- receive an event;
- resolve its Action;
- resolve references;
- execute the defined action;
- update Dynamic state or navigation through the appropriate boundary;
- isolate action failures from rendering;
- execute sequence children in defined order.

Do not put action execution inside individual render definitions.

Tests:

- each action type;
- event-to-action dispatch;
- reference resolution before action;
- sequence ordering/failure behavior;
- action failure isolation.

---

### UNIT-09 — Request / Destination Integration

Integrate `request` with the existing data/network infrastructure.

The Dynamic runtime coordinates; it does not implement another HTTP client.

Request support includes the frozen contract:

```text
method
endpoint
authentication
validate
headers
query
body
responseMode
```

Success:

```text
responseMode = none
    → action completes

responseMode = destination
    → DynamicDestination
    → Navigation
```

Errors follow DYNAMIC-06.4.2.

Destination contains:

```text
screenId
templateId
templateType
endpoint
method
authentication
```

Tests:

- successful request;
- validation-before-request;
- `responseMode = none`;
- `responseMode = destination`;
- structured request error;
- failed destination resolution;
- current destination preservation.

---

### UNIT-10 — Dynamic Navigation Integration

Integrate DynamicDestination with the existing navigation architecture.

Rules:

- store the complete DynamicDestination in the Dynamic navigation stack;
- push/pop/back remain navigation responsibilities;
- `templateType` never determines business destination;
- refresh reuses the current destination;
- backend navigate actions supply the destination explicitly.

Tests:

- push;
- pop;
- system/back integration;
- destination restoration;
- refresh;
- navigate action.

---

### UNIT-11 — Fallback / Error Boundary / Runtime Safety

Implement the frozen DYNAMIC-07 runtime behavior.

Boundaries:

```text
Template failure
    → screen-level fallback

Component failure
    → component-level fallback

Section failure
    → section-level fallback

Group failure
    → group-level fallback

Element failure
    → element-level fallback
```

Requirements:

- Dynamic failures do not crash the application;
- child failures remain localized;
- valid siblings continue;
- template failure stops normal screen rendering;
- no infinite retry;
- invalid runtime values use capability-specific safe handling;
- unaffected state is preserved;
- destination failure preserves the current valid screen;
- recovery is deterministic;
- registry/architecture/executable behavior is never mutated at runtime.

Tests:

- unknown definition;
- unsupported capability;
- invalid reference;
- rendering failure boundary;
- template failure;
- child failure;
- navigation failure;
- request failure;
- no retry loop;
- state preservation.

---

### UNIT-12 — Runtime Diagnostics / Observability

Implement the frozen DYNAMIC-07.4 and DYNAMIC-07.7 observability boundary using the existing logging infrastructure.

Diagnostics must support the frozen context where available:

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

Observability must distinguish:

```text
user-facing fallback
        ≠
diagnostic information
```

The implementation must respect the frozen sensitive-data protection and production/debug boundaries from DYNAMIC-07.7.

Tests:

- lifecycle diagnostics;
- definition-resolution diagnostics;
- action/request tracing;
- state diagnostics;
- JSON/backend diagnostics;
- filtering/verbosity;
- debug-mode behavior;
- production boundary;
- sensitive-data redaction.

---

### UNIT-13 — Dynamic Container / Feature Composition

Integrate the complete Dynamic runtime into the permanent Dynamic Container.

Final runtime composition:

```text
Bootstrap destination
      ↓
Navigation
      ↓
Dynamic Container
      ↓
Dynamic Store
      ↓
Screen load
      ↓
Decode
      ↓
Render
      ↓
Interaction
```

No native business screen branches are introduced.

Tests:

- startup into first Dynamic destination;
- screen loading;
- render;
- interaction;
- navigation;
- back;
- refresh;
- failure/fallback flow.

---

## 6. Initial File / Package Plan

The exact final package names must be verified against the existing repository conventions before implementation. The expected responsibility groups are:

```text
feature/dynamic/src/commonMain/kotlin/...
├── container/
├── model/
├── runtime/
│   ├── store/
│   ├── action/
│   ├── reference/
│   ├── destination/
│   └── error/
├── registry/
├── definition/
│   ├── template/
│   ├── component/
│   ├── section/
│   ├── group/
│   └── element/
├── renderer/
└── diagnostics/
```

This is a planning boundary, not authorization to create every directory/class blindly. Before each unit, the actual repository is inspected and only required files are created.

---

## 7. Test Strategy

Dynamic must be testable primarily through common/JVM tests wherever the behavior is platform-independent.

Required coverage includes:

```text
JSON decoding
model mapping
registry
definition resolution
capability handling
recursive rendering contract
state
references
actions
requests
request errors
destination handling
navigation coordination
fallbacks
runtime recovery
diagnostics
sensitive-data protection
```

Platform verification should cover the supported Compose targets through the project's existing test/build workflow. Platform-specific UI verification remains at the genuine platform boundary.

---

## 8. Explicitly Out of Scope for This Plan

Unless a frozen contract is later amended, do not add:

- native LoginScreen / OtpScreen / DashboardScreen classes;
- ViewModel;
- second MVI architecture;
- second navigation system;
- second HTTP client/network layer;
- generic plugin framework;
- reflection/package scanning;
- giant property/capability framework;
- automatic backend-to-frontend code generation;
- arbitrary backend code execution;
- automatic type substitution;
- automatic property downgrade;
- hidden retry loops;
- runtime mutation of registry definitions;
- business-specific screen branching;
- speculative backend fields;
- unrelated refactoring/dependency upgrades.

---

## 9. Implementation Order

The recommended execution order is:

```text
UNIT-01  Models
   ↓
UNIT-02  Decode / Mapping
   ↓
UNIT-03  Registry
   ↓
UNIT-04  Definitions
   ↓
UNIT-05  Renderer
   ↓
UNIT-06  Dynamic State Store
   ↓
UNIT-07  Reference Resolution
   ↓
UNIT-08  Action Runtime
   ↓
UNIT-09  Request / Destination
   ↓
UNIT-10  Navigation Integration
   ↓
UNIT-11  Fallback / Safety
   ↓
UNIT-12  Diagnostics / Observability
   ↓
UNIT-13  Container / End-to-End Composition
   ↓
Verification
   ↓
Review
   ↓
Code Freeze
```

Each unit should be implemented only after its file/class boundary is reviewed and accepted as part of the frozen implementation plan.

---

## 10. Implementation Safety Rules

1. Do not implement before this plan is frozen.
2. Do not reopen DYNAMIC-01 through DYNAMIC-07 frozen decisions during implementation unless a new explicit architecture discussion is created.
3. Do not modify unrelated modules merely to make Dynamic code convenient.
4. Reuse existing `:core`, `:domain`, `:data`, and `:navigation` boundaries.
5. Do not add a library without a separate decision.
6. Do not create speculative classes or abstractions.
7. Keep each implementation unit small enough to review and test independently.
8. Report the exact files/classes changed after each implementation unit.
9. Run the relevant Gradle tests/build verification after each unit or defined batch.
10. Stop and report if the existing repository contradicts a frozen Dynamic contract instead of silently changing the architecture.

---

## 11. Plan Status

**Current state: REVIEW DRAFT**

Next planning work:

1. Verify the complete current `:feature:dynamic` repository structure.
2. Verify the existing `:navigation`, `:domain`, `:data`, and `:core` contracts that Dynamic must consume.
3. Reconcile the proposed units with actual existing code.
4. Produce the final file/class list per unit.
5. Define exact verification commands.
6. Review the complete implementation plan.
7. Freeze the plan.

Implementation authorization is a separate step after `PLAN_FROZEN`.
