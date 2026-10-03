# Dynamic UI — Feature Discussion

## Status

- DYNAMIC-01 — FROZEN
- DYNAMIC-02 — FROZEN
- DYNAMIC-03 — FROZEN
- DYNAMIC-04 — FROZEN
- DYNAMIC-05 — FROZEN
- DYNAMIC-06 — FROZEN
- DYNAMIC-07 — NOT STARTED

This is the single discussion document for the complete Dynamic UI feature. All seven discussions will be recorded here. The implementation is a fresh CB-Partner design; the previous SDUI project/reference is used only to understand the fixed JSON contract and intended behavior. The fixed backend JSON format is not being redesigned here.

Initial frontend vocabulary for the first implementation scope:

```text
Template  -> stack_template
Component -> stack_component
Section   -> stack_section
Group     -> stack_group
Elements  -> text, image
```

Future Template/Component/Section/Group/Element definitions will be added when the actual backend contract requires them.

---

# DYNAMIC-01 — Dynamic Container + Dynamic Screen Lifecycle

## Status

**FROZEN**

The Dynamic system is the main runtime UI system after Bootstrap. It uses one permanent Dynamic Container and a Destination / Navigation Stack model. There are no native LoginScreen, OtpScreen, DashboardScreen, etc.; these are backend-defined Dynamic destinations.

```text
Bootstrap
  ↓
Config API
  ↓
Dynamic Destination
  ↓
Dynamic Navigation
  ↓
Dynamic Container
  ↓
Screen API
  ↓
Dynamic Screen JSON
  ↓
Render
```

A Dynamic destination contains the backend-defined information required to identify and load the destination, including `templateType`, unique `templateId`, and the API information required by the fixed backend contract.

`templateType` answers which registered Template renderer should render this destination. `templateId` is the unique Dynamic destination identity used by the navigation/back-stack model.

Navigation is destination-driven. The frontend does not infer Login/OTP/Dashboard from a Template type. The backend provides the destination information required to load the next Dynamic screen.

The Dynamic navigation stack owns push/pop/back. The Dynamic Container displays the current destination. Refreshing the current destination reloads its current screen API without creating a new navigation entry.

Frozen DYNAMIC-01 principles:

1. One permanent Dynamic Container.
2. Destination / Navigation Stack model.
3. Unique backend `templateId` identifies a Dynamic destination/back-stack entry.
4. `templateType` selects the registered Template renderer.
5. Destination contains the information required to obtain the actual screen JSON.
6. Dynamic navigation supports push/pop/back without business-screen-specific frontend classes.
7. Dynamic Container renders the current destination.
8. Actual UI comes from backend Dynamic Screen JSON.
9. Unknown definitions have controlled unsupported handling.
10. Initial definitions are `stack_template`, `stack_component`, `stack_section`, `stack_group`, `text`, and `image`.
11. New definitions can be added without redesigning the Dynamic core.

---

# DYNAMIC-02 — Screen → Template → Component → Section → Group → Element

## Status

**FROZEN**

## Screen

Screen is the root Dynamic configuration. It owns exactly one Template and may contain screen-level configuration such as Theme, Header, and Footer when provided by the fixed JSON contract.

```text
Screen
├── screen-level configuration
├── Theme
├── Header / Footer (when provided)
└── Template
```

Theme, Header, and Footer are Screen-level concerns.

## Template

Template is the complete screen-level layout strategy. A Screen has exactly one Template. Template does not mean Login, OTP, Dashboard, etc.; it defines the rendering/composition strategy.

A Template can contain multiple Components.

## Component

A Component may contain multiple Elements, multiple Sections, or both. Ordering supplied by the backend is significant.

## Section

A Section may contain multiple Elements, multiple Groups, or both. Ordering is significant.

## Group

A Group can contain one or multiple Elements only. It cannot contain another Group, Section, Component, Template, or Screen.

## Element

Element is terminal and has no Dynamic children. Initial elements are `text` and `image`.

## Minimum and legal structure

Every Dynamic screen has the minimum path:

```text
Screen → Template → Component → Element
Screen → Template → Component → Section → Element
Screen → Template → Component → Section → Group → Element
```

Components can mix Elements and Sections; Sections can mix Elements and Groups.

## Cardinality

```text
Screen → Template       = exactly 1 Template
Template → Component    = 1..N Components
Component → Element     = 0..N Elements
Component → Section     = 0..N Sections
Section → Element       = 0..N Elements
Section → Group         = 0..N Groups
Group → Element         = 1..N Elements
Element → children      = none
```

Only Template is singular within Screen. All other structural nodes can occur multiple times where legally allowed. Backend ordering must be preserved.

Frozen DYNAMIC-02 decisions:

1. Screen has exactly one Template.
2. Template has one or more Components.
3. Component may contain Elements, Sections, or both.
4. Section may contain Elements, Groups, or both.
5. Group contains one or more Elements only.
6. Element is terminal.
7. Minimum path is Screen → Template → Component → Element.
8. Section and Group are optional.
9. Only Template is singular within Screen.
10. Backend ordering is preserved.
11. Theme/Header/Footer are Screen-level concerns when provided.
12. Template is not a business-screen class.
13. Component and Section are flexible composition levels and may contain their legal child types in combination.
14. Structural hierarchy and rendering behavior remain separate.

---

# DYNAMIC-03 — Registration System

## Status

**FROZEN**

Registration answers one simple question:

```text
Backend type name
      ↓
Is it registered?
      ↓
YES → use registered definition
NO  → controlled unsupported result
```

Registration covers Templates, Components, Sections, Groups, and Elements.

## Registry structure

There is one central public `DynamicRegistry` for the Dynamic system, with separate registries internally for each node category.

```text
DynamicRegistry
│
├── TemplateRegistry
├── ComponentRegistry
├── SectionRegistry
├── GroupRegistry
└── ElementRegistry
```

Registration is explicit and separated by category:

```text
DynamicRegistry.templates.register(...)
DynamicRegistry.components.register(...)
DynamicRegistry.sections.register(...)
DynamicRegistry.groups.register(...)
DynamicRegistry.elements.register(...)
```

There is no reflection, package scanning, or automatic discovery.

Lookup is category-specific:

```text
resolveTemplate(type)
resolveComponent(type)
resolveSection(type)
resolveGroup(type)
resolveElement(type)
```

A Template lookup cannot accidentally resolve an Element definition.

Initial registrations:

```text
Templates  -> stack_template
Components -> stack_component
Sections   -> stack_section
Groups     -> stack_group
Elements   -> text, image
```

Duplicate registration of the same type in the same category is an error and must not silently replace an existing definition.

When backend JSON contains an unknown type, the category registry returns a controlled unsupported/not-registered result. The registry does not guess another definition or silently substitute a different type.

`DynamicRegistry` owns only registration and lookup. It does not own navigation, API calls, business logic, actions, screen state, or rendered UI state.

Frozen DYNAMIC-03 decisions:

1. One central public `DynamicRegistry`.
2. Internally separate Template, Component, Section, Group, and Element registries.
3. Registration is explicit and category-specific.
4. Lookup is category-specific.
5. Duplicate registration is an error.
6. Unknown/unregistered types produce a controlled unsupported result.
7. Initial registrations are `stack_template`, `stack_component`, `stack_section`, `stack_group`, `text`, and `image`.
8. No reflection, package scanning, automatic discovery, generic plugin framework, or unnecessary factory hierarchy.
9. Registry owns registration and lookup only.

---

# DYNAMIC-04 — Layout, Visual Capabilities & Definition Rendering Direction

## Status

**FROZEN**

DYNAMIC-04 establishes how Dynamic definitions own rendering, how parent/child layout responsibility works, how responsive sizing works, and how common capabilities are reused without creating an unnecessary property framework.

## 04.1 — Definition-Owned Compose Rendering

Each registered Template, Component, Section, Group, and Element definition owns the Compose rendering for its own type.

There is no giant renderer containing the complete UI implementation for every type. A small Dynamic rendering mechanism resolves the definition and delegates rendering to it.

## 04.2 — Parent / Child Layout Responsibility

The same parent/child responsibility applies to Template, Component, Section, and Group.

```text
Parent
├── owns its own container/layout behavior
├── controls child placement/arrangement
└── provides available constraints to children

Child
├── owns its own content
└── controls its own legal size/alignment within available constraints
```

A child never reaches upward to modify its parent's layout configuration. A parent does not implement the child's concrete UI content. Element is terminal and owns only its own content rendering.

## 04.3 — Size Model

Dynamic sizing is responsive and Compose-native. Supported concepts are:

```text
WRAP
FILL
FIXED
FRACTION
```

with constraints where applicable:

```text
minWidth
maxWidth
minHeight
maxHeight
```

Aspect ratio is supported where a definition/property contract requires it. The same Dynamic JSON should work across small phones, large phones, tablets/iPads, desktop windows, and web by responding to available constraints rather than device-specific branches.

## 04.4 — Padding, Spacing & Arrangement

Padding belongs to the node that declares it and affects that node's content/children. Spacing, arrangement, orientation, and alignment belong to the relevant parent/container and control how that parent's legal children are placed.

## 04.5 — Responsive / Adaptive Behavior

Responsive behavior is constraint-based/adaptive, not device-specific. Compose Multiplatform's existing measurement/layout system performs actual responsive behavior; Dynamic does not create a second responsive layout engine.

## 04.6 — Canonical Common Capability Model

We will not create a separate property class/framework for every property. Common behavior is reused through small helpers/application logic while each registered definition supports only capabilities that make semantic sense for that definition.

Common layout capabilities include:

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
```

Common visual capabilities include:

```text
background
shape
border
shadow / elevation
alpha / opacity
```

Background supports normal/solid color and gradient forms. Shape supports the applicable simple/general shape model, including rounded/corner-radius behavior. Border and shadow/elevation support the applicable backend models.

Semantic information such as `semanticRole` is separate from visual rendering. Actions are separate from the common visual capability system.

A common capability is reusable but is not automatically legal for every node. Definitions opt into only the capabilities that make sense for their type.

## 04.7 — Theme

Theme is a first-class Screen-level Dynamic concern. The fixed JSON may provide theme, statusBar, and visual theme properties such as background/gradient configuration. Theme is not a Template/Component/Section/Group/Element node.

Frozen DYNAMIC-04 decisions:

1. Each registered Template/Component/Section/Group/Element definition owns its own Compose rendering.
2. Parent and child rendering responsibility is identical in principle across Template, Component, Section, and Group.
3. Parents own container/layout behavior and child placement.
4. Children own their content and legal self-sizing/alignment within available constraints.
5. Elements are terminal.
6. Size supports WRAP, FILL, FIXED, and FRACTION concepts with min/max constraints where applicable.
7. Aspect ratio is supported where applicable.
8. Padding belongs to the node declaring it.
9. Spacing/arrangement/orientation/alignment belong to the relevant parent/container.
10. Responsive behavior is constraint-based/adaptive rather than device-specific.
11. Compose Multiplatform performs actual measurement/layout behavior; Dynamic does not create a second responsive layout engine.
12. Common capabilities are reused without creating a property-class explosion or generic property framework.
13. Definitions opt into only common capabilities that make semantic sense for their type.
14. Background supports solid color and gradient forms according to the fixed contract.
15. Shape supports the applicable simple/general shape model.
16. Border supports the applicable border model.
17. Shadow/elevation supports the applicable model.
18. Semantic information remains a separate semantic capability.
19. Actions are not mixed into the common visual capability model.
20. Theme is a first-class Screen-level Dynamic concern.
21. The fixed backend JSON format remains authoritative.

---

# DYNAMIC-05 — Dynamic Definition, Registry & Parent → Child Rendering

## Status

**FROZEN**

DYNAMIC-05 defines how registered Dynamic definitions are represented, resolved, and recursively rendered into Compose UI.

## 05.1 — Registry Architecture

There is one central `DynamicRegistry` entry point with separate category registries:

```text
DynamicRegistry
│
├── TemplateRegistry
│     └── stack_template
│
├── ComponentRegistry
│     └── stack_component
│
├── SectionRegistry
│     └── stack_section
│
├── GroupRegistry
│     └── stack_group
│
└── ElementRegistry
      ├── text
      └── image
```

The separate registries make registration and lookup easy to find and understand while preserving one Dynamic entry point.

Registration is frontend-owned and explicit. Backend JSON can select a registered definition but cannot dynamically register a renderer. Duplicate registration is rejected.

Resolution is category-specific. Unknown types return a controlled unsupported/not-registered result. The registry does not guess another type.

## 05.2 — Definition Structure

A Dynamic Definition is the frontend implementation of one registered Dynamic type. The definition contract is intentionally small:

```text
BaseDynamicDefinition
├── type
├── supported capabilities
├── common capability application
└── definition-specific rendering
```

Concrete definitions include `StackTemplateDefinition`, `StackComponentDefinition`, `StackSectionDefinition`, `StackGroupDefinition`, `TextDefinition`, and `ImageDefinition`.

The base definition provides reusable common Compose capability behavior. Concrete definitions remain responsible for unique properties, legal child rules, Compose structure, and rendering. The base definition must not become a giant generic renderer or property framework.

Definitions do not own API, navigation, business logic, MVI state, repositories, action execution, or global application state.

## 05.3 — Parent → Child Rendering

A single central `DynamicRenderer` handles recursive rendering:

```text
DynamicRenderer
      ↓
resolve definition through the appropriate registry
      ↓
Definition
      ↓
Definition creates its Compose UI
      ↓
Definition delegates each legal child to DynamicRenderer
```

Parents do not directly instantiate concrete child Definitions. Parent definitions create their own Compose container/layout and delegate each child through `DynamicRenderer` in backend order.

```text
Template  → render Components
Component → render Elements / Sections
Section   → render Elements / Groups
Group     → render Elements
Element   → terminal Compose content
```

Parent properties control parent layout. Child properties control child layout/content within the parent's available constraints. A child cannot reach upward to modify parent layout configuration.

Unknown child types produce controlled unsupported/not-registered handling. Known but structurally illegal children are treated as invalid Dynamic structure rather than silently reinterpreted as another type.

Frozen DYNAMIC-05 decisions:

1. One central `DynamicRegistry` entry point with separate Template/Component/Section/Group/Element registries.
2. Each registered definition represents one Dynamic type and owns its Compose rendering.
3. Base definition provides only small reusable common capability behavior; it is not a generic property framework.
4. A single `DynamicRenderer` recursively resolves definitions and delegates rendering.
5. Parents create their own Compose container/layout and delegate children through `DynamicRenderer`.
6. Parents never directly instantiate concrete child Definitions.
7. Child node data is passed to the child Definition through the rendering flow.
8. Parent layout controls child placement; child layout/content controls its own behavior within available constraints.
9. Template, Component, Section, and Group use the same parent/child rendering principle.
10. Element is terminal.
11. Unknown types produce controlled unsupported/not-registered handling.
12. Known but structurally illegal children are treated as invalid Dynamic structure rather than silently reinterpreted.
13. Registry/Definitions/Renderer do not own navigation, API, business logic, MVI state, or action execution.
14. Rendering remains intentionally small: Registry + Definition + DynamicRenderer + Compose.

---

# DYNAMIC-06 — Dynamic Actions & Interaction

## Status

**FROZEN**

DYNAMIC-06 defines how backend-driven events/actions, state interaction, references, API requests, request errors, and destinations work without mixing action execution into rendering.

The fixed backend action/reference contract remains authoritative. The frontend runtime interprets the contract; it does not invent a second action language.

## 06.1 — Action Model

The fundamental distinction is:

```text
Event = WHEN something happens
Action = WHAT should happen
```

Supported common events are defined now rather than deferred:

```text
onClick
onLongClick
onValueChange
onFocus
onSubmit
```

The same event model can be extended later with additional backend-supported events without redesigning the action runtime.

An event resolves to one Action. When multiple actions must run for one event, the existing `sequence` action composes them explicitly.

Definitions expose events from their supported backend contract, but Definitions do not execute business actions themselves. The Action Runtime interprets and executes the action model.

Current action vocabulary discussed/frozen around the fixed contract includes:

```text
request
navigate
present
dismiss
state
external_uri
sequence
```

Rendering and action execution remain separate responsibilities.

## 06.2 — State & Cross-Element Interaction

Dynamic UI needs destination-scoped state for interactions that affect another element or parent/child UI.

Examples include:

```text
Resend OTP clicked
    ↓
timer/countdown state changes
    ↓
Resend element becomes enabled later
```

or:

```text
Element A action
    ↓
state change
    ↓
Element B visible/enabled/selected/loading/etc. changes
```

The canonical interaction path is:

```text
Event
  ↓
Action
  ↓
Dynamic State
  ↓
reactive UI rendering
```

State is destination-scoped and logically keyed so multiple elements can participate in the same Dynamic interaction state.

Supported interaction-state concepts include:

```text
visible
enabled
selected
loading
expanded
timer / countdown
```

No element holds a direct reference to another element or to a Compose node. Parent/child interaction also uses Action → State → reactive UI rather than direct UI references.

Dynamic UI state is separate from business/domain state.

## 06.3 — References & State Resolution

The fixed reference vocabulary is:

```text
$literal
$binding
$context
$response
```

Reference resolution is centralized and deterministic. References are resolved recursively and type-safely before their values are consumed by an action or property that supports references.

```text
$literal  → literal value
$binding  → current Dynamic input/binding value
$context  → current runtime/application context value
$response → relevant request response value
```

Missing or invalid references are errors; the runtime does not silently invent a value.

References read values. Actions perform mutations or effects. There is no generic `$state` reference that bypasses the defined model.

`$response` is request-response scoped and is available when the relevant request result exists.

## 06.4 — API / Navigation Actions

### 06.4.1 — `request` Action

The `request` action is backend-driven and carries the request information required by the fixed contract:

```text
method
endpoint
authentication
validate
headers (when supported)
query (when supported)
body (when supported)
responseMode
```

References are resolved before the request is validated/executed.

When `validate = true`, Dynamic validation runs before the request is sent. API/data infrastructure owns actual HTTP transport, authentication integration, serialization, and network concerns. The Dynamic Action Runtime coordinates the action but does not become an HTTP client implementation.

`responseMode` determines what a successful response means to the Dynamic action flow:

```text
destination → produce a DynamicDestination for navigation
none        → complete without a destination
```

Request execution is asynchronous. Request state is independently trackable and lifecycle-aware. Request execution does not directly manipulate Compose nodes or bypass the Dynamic navigation/state boundaries.

Failures are returned as structured Dynamic action/request failures rather than being thrown into rendering code as uncontrolled exceptions.

Frozen DYNAMIC-06.4.1 decisions:

1. `request` carries method, endpoint, authentication, validation, headers/query/body when supported, and responseMode.
2. References are resolved before validation/execution.
3. `validate = true` runs Dynamic validation before the request.
4. HTTP/auth/network/serialization infrastructure remains outside the renderer and action definition.
5. `responseMode = destination` produces a DynamicDestination on success.
6. `responseMode = none` completes without navigation destination.
7. Request execution is asynchronous.
8. Request lifecycle state is independently trackable.
9. Request execution does not directly manipulate Compose UI or navigation primitives.
10. Request failures are structured Dynamic failures.

### 06.4.2 — Request Error Model

The request result has a clear success/error boundary. Errors are normalized so transport/runtime exceptions do not leak as arbitrary exception types into Dynamic UI code.

The frozen error model is:

1. Request results are explicitly **Success** or **Error**.
2. Transport/runtime exceptions are normalized into the Dynamic request error model.
3. A request error has structured `type`, `code`, `message`, and optional `details` information.
4. Backend-provided `code` and `message` are preserved when available.
5. HTTP status is retained as metadata; it is not itself the complete Dynamic error model.
6. An error never causes automatic navigation.
7. When the current destination request fails, the current destination remains the active destination unless an explicit action changes it.
8. Request lifecycle is represented as `Idle → Loading → Success/Error` as applicable.
9. `responseMode` affects successful request handling; an error never becomes a destination merely because responseMode is `destination`.
10. Error handling must remain generic at the Dynamic infrastructure level and must not hardcode business-specific meanings.
11. Error presentation is separate from transport/error normalization; the renderer does not own global error policy.
12. The error model is extensible so additional structured metadata can be added without redesigning request execution.

### 06.4.3 — Destination Response Schema

A Destination is not the rendered screen. It is an instruction describing which Dynamic screen should be obtained next and how it should be obtained.

Canonical client model:

```text
DynamicDestination
├── screenId
├── templateId
├── templateType
├── endpoint
├── method
└── authentication
```

The backend may represent this object as `nextScreen`. The client maps that fixed backend field to the internal `DynamicDestination` model.

For example, the fixed send-OTP response provides:

```text
nextScreen
├── screenId
├── templateId
├── templateType
├── endpoint
├── method
└── authentication
```

The resulting flow is:

```text
Action
  ↓
DynamicDestination
  ↓
Navigation
  ↓
Fetch Screen
  ↓
Dynamic Screen JSON
  ↓
Template
  ↓
Render
```

The destination does not contain the rendered UI tree. The fetched Screen contains the complete screen configuration, including its Template, Components, Sections, Groups, Elements, Theme, and supported screen-level configuration.

Both a successful `request` with `responseMode = destination` and an explicit `navigate` action may produce a `DynamicDestination`.

`screenId` identifies the logical Dynamic screen. `templateId` identifies the concrete Dynamic destination/template instance used by navigation. `templateType` selects the registered Template definition. `endpoint`, `method`, and `authentication` describe how the screen is obtained.

Destination identity and Template identity are related but remain separate concepts. `templateId` is not itself the navigation mechanism; the complete DynamicDestination is the navigation entry.

A destination is validated before it is used. Missing required destination fields prevent navigation/fetch and produce a structured Dynamic configuration/request failure.

If `templateType` is not registered, the Dynamic system returns a controlled not-registered/configuration result rather than guessing a Template. The same principle applies to later Component, Section, Group, and Element resolution.

No speculative destination fields such as animation, transition, cache policy, preload policy, presentation mode, or restore policy are part of the frozen base model unless the actual backend contract later requires them.

Frozen DYNAMIC-06.4.3 decisions:

1. Destination is a navigation/fetch instruction, not a rendered screen.
2. Canonical destination fields are `screenId`, `templateId`, `templateType`, `endpoint`, `method`, and `authentication`.
3. Backend may represent a destination as `nextScreen`.
4. Client maps `nextScreen` to `DynamicDestination`.
5. Both `request` success and `navigate` may produce a `DynamicDestination`.
6. `DynamicDestination` does not contain UI tree data.
7. `DynamicDestination` is used to obtain a Screen.
8. Destination identity and Template identity remain separate concepts.
9. Destination must be validated before navigation/fetch.
10. Unknown/unregistered Template types produce a clear Dynamic configuration result.
11. A successful `request` with `responseMode = destination` hands its destination to the Dynamic navigation system.
12. A failed request does not create or replace a destination.
13. Destination navigation remains separate from rendering.
14. No speculative destination fields are added to the base contract.

## DYNAMIC-06 — Frozen Decisions

1. Events and Actions are separate concepts: Event is WHEN; Action is WHAT.
2. Common events include `onClick`, `onLongClick`, `onValueChange`, `onFocus`, and `onSubmit`.
3. One event resolves to one Action; multiple actions use the existing `sequence` action.
4. Action execution belongs to the Dynamic Action Runtime, not concrete Definitions or renderers.
5. Cross-element and parent/child interaction uses Action → Dynamic State → reactive UI.
6. Dynamic state is destination-scoped and separate from business/domain state.
7. Interaction state supports concepts such as visible, enabled, selected, loading, expanded, and timer/countdown.
8. No direct element-to-element or element-to-Compose-node references are used.
9. References are exactly `$literal`, `$binding`, `$context`, and `$response` for the frozen base model.
10. Reference resolution is centralized, deterministic, recursive, and type-safe.
11. Missing/invalid references are errors; references read values while Actions perform effects/mutations.
12. `request` supports the fixed request fields and responseMode model.
13. Dynamic validation occurs before a request when `validate = true`.
14. HTTP/auth/network/serialization infrastructure remains outside the Dynamic renderer/action model.
15. Request execution is asynchronous and independently trackable.
16. Request failures use the structured normalized error model.
17. Errors never automatically create navigation destinations.
18. A failed request leaves the current destination active unless an explicit action changes navigation.
19. `responseMode = destination` produces a DynamicDestination on success; `none` does not.
20. DynamicDestination contains `screenId`, `templateId`, `templateType`, `endpoint`, `method`, and `authentication` only in the frozen base model.
21. `nextScreen` is the backend representation mapped to DynamicDestination.
22. DynamicDestination is a navigation/fetch instruction, not a rendered screen.
23. Request success and navigate may produce the same DynamicDestination model.
24. Destination validation and registry resolution occur before using a destination.
25. Unknown/unregistered definitions produce controlled unsupported/configuration results and are never silently substituted.
26. Action execution, state, references, request handling, errors, navigation, and rendering remain separate responsibilities.

---

# DYNAMIC-07 — NOT STARTED

DYNAMIC-07 will be started only after the complete DYNAMIC-01 through DYNAMIC-06 discussion has been recorded and frozen. The exact DYNAMIC-07 scope will be discussed before any implementation planning is created.

---

# Fixed Backend JSON Reference

The supplied backend JSON structure remains fixed and authoritative for the Dynamic implementation. It demonstrates the currently known concepts including:

```text
API envelope
└── data
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
        ├── theme
        ├── statusBar
        └── properties
```

The Login / Send OTP / OTP examples also confirm backend-driven destinations through `nextScreen`, screen-level Theme, common layout/visual capabilities, definition-specific properties, leading/trailing accessories, element-level actions, validation, bindings, request response references, and destination-driven navigation.

The fixed JSON format is not being redesigned during this frontend discussion. The new frontend implementation will be designed cleanly from these contracts rather than copied from the previous project's class structure.

---

# Discussion Workflow Rule

For each Dynamic point:

```text
Discussion
   ↓
Freeze
   ↓
Update this document
   ↓
Next Dynamic point
```

After all seven Dynamic discussion points are frozen:

```text
Implementation Plan
   ↓
Freeze Plan
   ↓
Implementation
   ↓
Testing
   ↓
Final Freeze
```

No implementation code is introduced merely because a discussion decision has been frozen.
