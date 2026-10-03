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


# DYNAMIC-07 — Runtime Validation, Compatibility & Recovery

## Status

**IN PROGRESS — 07.1 through 07.6 FROZEN; 07.7 NOT STARTED**

DYNAMIC-07 defines the frontend Dynamic runtime boundary for safely consuming backend-driven Dynamic configuration. The goal is to keep the runtime simple and deterministic:

```text
Backend JSON
    ↓
Parse
    ↓
Validate / accept the fixed contract
    ↓
Resolve registered definitions
    ↓
Render
    ↓
Diagnose / fallback / recover safely
```

DYNAMIC-07 does not introduce a second SDUI system, a generic validation framework, a migration engine, or an automatic fallback/substitution engine. The backend contract remains authoritative.

---

## 07.1 — Dynamic Structure Validation

### Status

**FROZEN**

DYNAMIC-07.1 establishes the canonical Dynamic tree rules already frozen in DYNAMIC-02 and defines the frontend boundary for rejecting structurally invalid Dynamic trees before rendering.

Canonical legal structures include:

```text
Screen
└── Template
    └── Component
        └── Element

Screen
└── Template
    └── Component
        └── Section
            └── Element

Screen
└── Template
    └── Component
        └── Section
            └── Group
                └── Element
```

Structural rules:

- Screen has exactly one Template.
- Template contains one or more Components.
- Component may contain Elements, Sections, or both.
- Component cannot contain a Group directly.
- Section may contain Elements, Groups, or both.
- Section cannot contain Component or another Section.
- Group contains one or more Elements only.
- Group cannot contain Section, Group, Component, Template, or Screen.
- Element is terminal and has no Dynamic children.
- Required child collections cannot be empty.
- Backend ordering is preserved.

Structural validation is separate from registration/capability resolution:

```text
Structural validation
    ↓
"Is this tree legally shaped?"

Registry resolution
    ↓
"Do we know how to render these types?"
```

For the frozen frontend boundary, backend validation is authoritative. The frontend does not become a duplicate business/configuration validation engine. The runtime parses the fixed response, resolves registered definitions, and handles runtime/unsupported-definition failures.

For this point, hierarchy validation owns only structure. It does not absorb property validation, action validation, reference resolution, registration lookup, API errors, or Compose rendering.

If a Dynamic screen is structurally invalid, it is rejected before normal rendering and handled through the controlled screen-level Dynamic failure boundary. The runtime never repairs, restructures, inserts missing nodes, or guesses the backend's intent.

### Frozen 07.1 decisions

1. The canonical Screen → Template → Component → Section → Group → Element hierarchy is enforced as the Dynamic structural contract.
2. Screen has exactly one Template.
3. Template requires one or more Components.
4. Component may contain Elements and/or Sections, but not Groups directly.
5. Section may contain Elements and/or Groups, but not Components or Sections.
6. Group contains one or more Elements only.
7. Element is terminal.
8. Required child collections cannot be empty.
9. Backend child ordering is preserved.
10. Structure and registration/capability resolution remain separate responsibilities.
11. The frontend does not create a second backend-style validation engine.
12. Structurally invalid Dynamic screens are rejected before normal rendering.
13. The runtime never repairs, restructures, substitutes, or guesses an invalid tree.

---

## 07.2 — Definition / Capability Resolution

### Status

**FROZEN**

DYNAMIC-07.2 defines how the frontend determines whether a structurally valid Dynamic tree can actually be rendered.

The existing five category registries remain the rendering capability source:

```text
DynamicRegistry
│
├── TemplateRegistry
├── ComponentRegistry
├── SectionRegistry
├── GroupRegistry
└── ElementRegistry
```

Resolution follows the actual Dynamic tree:

```text
Screen
 ↓
TemplateRegistry
 ↓
ComponentRegistry
 ↓
SectionRegistry
 ↓
GroupRegistry
 ↓
ElementRegistry
```

Each registered definition owns its own Compose rendering and its supported capabilities. The registry answers who can render a type; the Definition answers how that type renders.

Unknown definitions are never guessed, silently substituted, dynamically invented, or ignored.

Frozen runtime behavior:

```text
Registered Template
    → normal rendering

Unknown Template
    → screen-level controlled fallback

Unknown Component
    → component-level error UI

Unknown Section
    → section-level error UI

Unknown Group
    → group-level error UI

Unknown Element
    → element-level error UI
```

Unknown child definitions do not destroy an otherwise valid screen. Template is the exception because it determines the screen-level layout strategy.

Runtime fallback/error UI is not itself a Dynamic definition. Final visual designs for fallback/error UI remain outside this point.

### Frozen 07.2 decisions

1. Five separate category registries remain the rendering capability source.
2. Every registered definition owns its Compose rendering.
3. Backend type is resolved through the correct category registry.
4. Unknown definitions are never guessed or silently substituted.
5. Unknown Template produces a screen-level controlled fallback.
6. Unknown Component/Section/Group/Element produces localized runtime error UI.
7. Unknown child definitions do not destroy the surrounding valid screen.
8. Runtime fallback/error UI is not itself a Dynamic definition.
9. Registry resolution remains separate from backend validation.
10. Final visual fallback designs are deferred and are not part of 07.2.

---

## 07.3 — Unknown / Unsupported Definitions

### Status

**FROZEN**

DYNAMIC-07.3 distinguishes between an unknown Dynamic definition and an unsupported capability of a known definition.

### Unknown Definition

The type is not registered in the correct registry:

```text
node.type
   ↓
correct registry
   ↓
lookup(type)
   ↓
no definition
   ↓
UNKNOWN_DEFINITION
```

The runtime reports the category/level, type, and node identity where available.

### Unsupported Capability

The definition is registered, but the requested configuration/capability is not supported by that definition.

Example:

```text
ImageDefinition ✓
    ↓
animation.kenBurns
    ↓
not supported
    ↓
UNSUPPORTED_CAPABILITY
```

These remain separate because they represent different compatibility/debugging conditions.

Runtime errors are structured rather than only free-form strings. Conceptually:

```text
DynamicRuntimeError
├── level
├── type
├── nodeId
├── reason
└── capability (when applicable)
```

Unknown or unsupported behavior is localized according to 07.2.

There is no silent substitution:

```text
unknown type
    ✕
    ↓
another type
```

There is also no automatic property downgrade. An unsupported backend capability does not silently become a different supported value.

Explicit compatibility mappings may be added in the future only when intentionally designed and documented. Compatibility is never guessed automatically.

### Frozen 07.3 decisions

1. Unknown Definition and Unsupported Capability are separate concepts.
2. Unknown means the type is not registered in the correct category registry.
3. Unsupported means the definition is registered but cannot execute the requested capability/configuration.
4. Runtime errors use stable reason codes rather than relying only on human-readable messages.
5. Unknown and unsupported behavior remains localized according to the 07.2 fallback boundary.
6. Unknown definitions are never silently substituted.
7. Unsupported properties/capabilities are never silently downgraded.
8. Explicit compatibility mappings are allowed only when deliberately designed later.
9. No large compatibility framework is introduced now.

---

## 07.4 — Runtime Error Reporting & Diagnostics

### Status

**FROZEN**

DYNAMIC-07.4 separates user-facing fallback/error UI from diagnostic information.

```text
Dynamic Runtime Error
        │
        ├── User-facing representation
        │       ↓
        │   small fallback/error UI
        │
        └── Diagnostic information
                ↓
             existing Logger
```

The user-facing representation must not expose internal registry/debug details unnecessarily.

Where available, diagnostic context includes:

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

`traceId` is preserved through the Dynamic runtime so a backend request/response can be correlated with a frontend Dynamic failure.

Stable reason codes remain separate from human-readable messages. Messages may change; reason codes are the machine-readable diagnostic contract.

Dynamic diagnostics use the existing project logging infrastructure. No separate Dynamic logging framework is introduced.

Dynamic errors are not automatically fatal. Child failures remain recoverable/localized according to 07.2. Template-level failures use the screen-level fallback boundary.

---

## 07.5 — Fallback Strategy

### Status

**FROZEN**

Fallback is a controlled runtime behavior, not a replacement Dynamic definition and not a mechanism for guessing what the backend intended.

The frozen fallback boundary is:

```text
Template failure
    ↓
screen-level fallback

Component failure
    ↓
component-level fallback/error UI

Section failure
    ↓
section-level fallback/error UI

Group failure
    ↓
group-level fallback/error UI

Element failure
    ↓
element-level fallback/error UI
```

A child fallback does not prevent valid siblings from rendering.

The fallback strategy does not:

- substitute one Dynamic definition for another;
- silently rewrite backend configuration;
- create/register definitions at runtime;
- convert unsupported properties into arbitrary supported values;
- expose internal technical diagnostics as the normal user-facing message.

The final visual design of these fallback surfaces is a separate implementation/design concern and is not being expanded into a second Dynamic definition system.

---

## 07.6 — Dynamic Runtime Safety & Recovery

### Status

**FROZEN**

DYNAMIC-07.6 defines how the runtime safely continues after Dynamic failures without introducing a second error-management framework.

### Frozen 07.6 decisions

1. **Rendering must never crash the application.** Dynamic failures are contained within the Dynamic boundary. Recovery is specific to the failed operation; there is no blanket global exception handler around the application.
2. **Node-level recovery is isolated.** Component, Section, Group, and Element failures are isolated to the affected node whenever safely possible, allowing valid siblings to continue rendering.
3. **Template failure uses screen-level recovery.** A template failure terminates normal rendering of that Dynamic screen and switches to the screen-level fallback.
4. **Action failures are isolated from rendering.** Failed `request`, `navigate`, `external_uri`, and related actions do not become rendering crashes and are handled through their defined action/error contracts.
5. **No automatic infinite retry.** The Dynamic runtime has no implicit or infinite retry loop. Any retry behavior must come from an explicit contract.
6. **Invalid runtime values use capability-specific safe handling.** Missing references, bindings, response values, unsupported values, and unexpected runtime values do not use one universal default that could silently corrupt behavior. The consuming capability determines safe handling.
7. **Parent remains authoritative.** A child failure cannot mutate or redefine the parent's layout configuration or ownership.
8. **Unaffected state is preserved.** Local recovery must not reset unrelated valid Dynamic state where it can be preserved safely.
9. **Navigation failure preserves the current valid screen.** If a destination cannot be safely resolved/fetched, the runtime does not leave the user on a blank/broken screen; the current valid destination remains active and the failure is diagnosed/handled.
10. **Request failure follows DYNAMIC-06.4.2.** 07.6 does not create a second request error system. Request failures remain structured according to the frozen Request Error Model.
11. **Recovery is deterministic.** The same defined Dynamic state and failure follows the same recovery contract. The runtime does not guess an alternative definition or layout.
12. **Recovery cannot mutate registry/architecture/executable behavior.** Runtime recovery may change runtime UI state, but it cannot modify registry definitions, application architecture, or executable frontend behavior.

### Frozen 07.6 boundary

```text
                 Dynamic Runtime
                       │
                       ▼
                    Render
                       │
              ┌────────┴────────┐
              │                 │
           Success            Failure
              │                 │
              ▼                 ▼
          Continue          Diagnose
                                │
                    ┌───────────┼───────────┐
                    │           │           │
                 Node       Template      Action
                failure      failure      failure
                    │           │           │
                    ▼           ▼           ▼
                fallback    screen       action
                             fallback     handling
                    │           │           │
                    └───────────┴───────────┘
                                │
                                ▼
                         Safe continuation
```

---

## DYNAMIC-07 Current Boundary

At this point:

```text
07.1 Structure Validation ............... FROZEN
07.2 Definition / Capability Resolution  FROZEN
07.3 Unknown / Unsupported Definitions . FROZEN
07.4 Runtime Error Reporting ........... FROZEN
07.5 Fallback Strategy ................. FROZEN
07.6 Runtime Safety & Recovery ......... FROZEN
07.7 Observability & Debugging ......... NOT STARTED
```

The combined frozen runtime boundary is:

```text
Backend Dynamic Response
        ↓
Parse
        ↓
Accept fixed backend contract
        ↓
Structural/runtime checks at the defined boundary
        ↓
Registry Resolution
        ↓
Definition / Capability handling
        ↓
Render
        ↓
Diagnose failures
        ↓
Localized or screen-level fallback
        ↓
Deterministic safe recovery
```

No frontend runtime mechanism may guess, silently substitute, silently downgrade, mutate backend definitions, mutate registry definitions, or create executable behavior dynamically.

DYNAMIC-07.7 remains the next discussion and is intentionally not frozen yet.

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
