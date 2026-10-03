# Dynamic UI — Feature Discussion

## Status

- DYNAMIC-01 — FROZEN
- DYNAMIC-02 — FROZEN
- DYNAMIC-03 — FROZEN
- DYNAMIC-04 — FROZEN
- DYNAMIC-05 — FROZEN
- DYNAMIC-06 — NOT STARTED
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

`templateType` answers **which registered Template renderer should render this destination?** `templateId` is the unique Dynamic destination identity used by the navigation/back-stack model.

Example:

```text
Login      templateId = login_123
   ↓
OTP        templateId = otp_456
   ↓
Dashboard  templateId = dashboard_789
```

Navigation is destination-driven. The frontend does not infer Login/OTP/Dashboard from a Template type. The backend provides the destination information required to load the next Dynamic screen.

The Dynamic navigation stack owns push/pop/back. The Dynamic Container displays the current destination. Refreshing the current destination reloads its current screen API without creating a new navigation entry.

Unknown/unregistered definitions are handled through controlled unsupported behavior rather than business-screen-specific fallback logic.

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

```text
Component
├── Element*
└── Section*
```

## Section

A Section may contain multiple Elements, multiple Groups, or both. Ordering is significant.

```text
Section
├── Element*
└── Group*
```

## Group

A Group can contain one or multiple Elements only. It cannot contain another Group, Section, Component, Template, or Screen.

## Element

Element is terminal and has no Dynamic children. Initial elements are `text` and `image`.

## Minimum and legal structure

Every Dynamic screen has the minimum path:

```text
Screen → Template → Component → Element
```

Optional structural levels allow:

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

DYNAMIC-02 defines structure only; visual rendering behavior belongs to the registered definitions and DYNAMIC-04/DYNAMIC-05.

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

## 1. One central DynamicRegistry

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

This gives one Dynamic entry point while keeping registration easy to find and understand.

## 2. Explicit registration

Registration is explicit and separated by node category:

```text
DynamicRegistry.templates.register(...)
DynamicRegistry.components.register(...)
DynamicRegistry.sections.register(...)
DynamicRegistry.groups.register(...)
DynamicRegistry.elements.register(...)
```

Exact Kotlin API naming is an implementation-plan concern.

There is no reflection, package scanning, or automatic discovery.

## 3. Typed lookup

Lookup is category-specific:

```text
resolveTemplate(type)
resolveComponent(type)
resolveSection(type)
resolveGroup(type)
resolveElement(type)
```

A Template lookup cannot accidentally resolve an Element definition.

## 4. Initial registrations

```text
Templates
└── stack_template

Components
└── stack_component

Sections
└── stack_section

Groups
└── stack_group

Elements
├── text
└── image
```

Future definitions use the same explicit registration mechanism.

## 5. Duplicate registration

Duplicate registration of the same type in the same node category is an error and must not silently replace an existing definition.

## 6. Unknown / unregistered definitions

When backend JSON contains an unknown type:

```text
Backend type
    ↓
Category registry lookup
    ↓
NOT FOUND
    ↓
Controlled unsupported result
```

The registry does not guess another definition or silently substitute a different type.

## 7. Registry responsibility boundary

`DynamicRegistry` owns only:

```text
register
lookup
known / unknown definition
```

It does not own:

```text
navigation
API calls
business logic
actions
screen state
rendered UI state
```

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

```text
StackTemplateDefinition  → renders stack template
StackComponentDefinition → renders stack component
StackSectionDefinition   → renders stack section
StackGroupDefinition     → renders stack group
TextDefinition           → renders text
ImageDefinition          → renders image
```

There is no giant renderer containing the complete UI implementation for every type.

A small Dynamic rendering mechanism resolves the definition and delegates rendering to it.

## 04.2 — Parent / Child Layout Responsibility

The same parent/child responsibility applies to Template, Component, Section, and Group.

```text
Parent
├── owns its own container/layout behavior
├── controls child placement/arrangement
└── provides available constraints to children

Child
├── owns its own content
└── controls its own legal size/alignment within the available constraints
```

A child never reaches upward to modify its parent's layout configuration.

A parent does not implement the child's concrete UI content.

Element is terminal and owns only its own content rendering.

## 04.3 — Size Model

Dynamic sizing is responsive and Compose-native. The fixed backend JSON remains authoritative.

Supported sizing concepts include:

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

The model also supports aspect ratio where a definition/property contract requires it.

The intent is that the same Dynamic JSON works across small phones, large phones, tablets/iPads, desktop windows, and web by responding to available constraints rather than device-specific branches.

Examples of the conceptual behavior:

```text
WRAP      → size to content
FILL      → consume available parent space
FIXED     → explicit bounded size
FRACTION  → consume a fraction of available parent space
```

The concrete fixed JSON property names and their exact Compose mapping are implementation-contract concerns, but the sizing behavior is frozen as the above model.

## 04.4 — Padding, Spacing & Arrangement

Padding belongs to the node that declares it and affects that node's content/children.

```text
Parent padding
    ↓
parent content area
    ↓
children are laid out inside it
```

Spacing and arrangement belong to the relevant parent/container and control how that parent's legal children are placed.

Examples include the fixed JSON concepts already present in the backend contract:

```text
orientation
verticalArrangement
horizontalArrangement
verticalAlignment
horizontalAlignment
spacing
padding
```

A child does not reinterpret the parent's spacing/arrangement.

## 04.5 — Responsive / Adaptive Behavior

Responsive behavior is constraint-based/adaptive, not device-specific.

```text
same Dynamic JSON
       ↓
different available constraints
       ↓
same definitions
       ↓
adaptive Compose layout
```

The renderer must not contain branches such as:

```text
if iPhone
if Android phone
if iPad
if desktop
```

Compose Multiplatform's existing measurement/layout system performs the actual responsive layout behavior. Dynamic supplies backend-driven constraints and legal layout configuration.

## 04.6 — Canonical Common Capability Model

We will not create a separate property class/framework for every property. Common behavior is defined once conceptually and implemented through small reusable Compose helpers/application logic. Each registered definition supports only the capabilities that make semantic sense for that definition.

```text
Dynamic Definition
        │
        ├── common capabilities
        │
        └── definition-specific capabilities
```

### Layout capabilities

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

### Visual capabilities

```text
background
shape
border
shadow / elevation
alpha / opacity
```

`background` supports normal/solid color and gradient forms according to the fixed backend contract.

`shape` supports the applicable shape model, including simple rounded/corner-radius behavior and a general shape model where the backend contract requires it.

`border` supports the applicable border configuration defined by the backend contract.

`shadow / elevation` supports the applicable model rather than reducing it to one hard-coded scalar.

### Content / presentation

Content-alignment behavior is available where meaningful. Definition-specific content remains owned by the concrete definition.

### Semantics

Semantic information such as `semanticRole` is treated as a Dynamic semantic capability and is not mixed into visual rendering.

### Interaction

Actions are intentionally not part of the common visual property system. Action behavior is a separate Dynamic discussion.

### Capability ownership rule

A common capability is reusable, but it is not automatically legal for every node.

```text
Common capability
       ↓
Definition supports it?
   ┌───┴───┐
  YES      NO
   ↓        ↓
apply    unsupported
```

This prevents meaningless properties such as container orientation on a Text element while allowing future definitions to reuse existing common behavior without redesigning the Dynamic architecture.

## 04.7 — Theme

Theme is a first-class Screen-level Dynamic concern.

The fixed JSON may provide:

```text
theme
statusBar
visual theme properties
background/gradient configuration
```

Theme is not treated as a Template/Component/Section/Group/Element node. It belongs to the Screen-level configuration and will be rendered/applied through the Dynamic screen composition boundary.

The Login JSON confirms that Theme can define screen-level visual behavior such as a gradient and status-bar configuration.

## DYNAMIC-04 — Frozen Decisions

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
11. Compose Multiplatform performs the actual measurement/layout behavior; Dynamic does not create a second responsive layout engine.
12. Common capabilities are reused without creating a property-class explosion or generic property framework.
13. Definitions opt into only the common capabilities that make semantic sense for their type.
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

Registration is frontend-owned and explicit. Backend JSON can select a registered definition but cannot dynamically register a renderer.

Duplicate registration is rejected.

Resolution is category-specific:

```text
resolveTemplate(type)
resolveComponent(type)
resolveSection(type)
resolveGroup(type)
resolveElement(type)
```

Unknown types return a controlled unsupported/not-registered result. The registry does not guess another type.

The registry does not own navigation, API calls, business logic, actions, screen state, or rendered UI state.

## 05.2 — Definition Structure

A Dynamic Definition is the frontend implementation of one registered Dynamic type.

The definition contract is intentionally small:

```text
BaseDynamicDefinition
│
├── type
├── supported capabilities
├── common capability application
└── definition-specific rendering
```

Concrete definitions include:

```text
StackTemplateDefinition
StackComponentDefinition
StackSectionDefinition
StackGroupDefinition
TextDefinition
ImageDefinition
```

The base definition provides reusable common Compose capability behavior. Concrete definitions remain responsible for their own unique properties, legal child rules, Compose structure, and rendering.

The base definition must not become a giant generic renderer or property framework.

Definitions may interpret their own defaults and definition-specific properties where required.

Definitions do not own:

```text
API
navigation
business logic
MVI state
repositories
action execution
global application state
```

## 05.3 — Parent → Child Rendering

A single central `DynamicRenderer` handles recursive rendering.

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

Parents do not directly instantiate concrete child Definitions.

### Template

```text
TemplateDefinition
      ↓
creates template layout
      ↓
DynamicRenderer.render(component)
      ↓
repeat for each Component in backend order
```

### Component

```text
ComponentDefinition
      ↓
creates component layout
      ↓
DynamicRenderer.render(element/section)
```

A Component may render Elements, Sections, or both according to the fixed hierarchy.

### Section

```text
SectionDefinition
      ↓
creates section layout
      ↓
DynamicRenderer.render(element/group)
```

### Group

```text
GroupDefinition
      ↓
creates group layout
      ↓
DynamicRenderer.render(element)
```

### Element

Element is terminal:

```text
ElementDefinition
      ↓
Compose content
```

No Dynamic child rendering occurs below an Element.

## Parent placement / child content rule

```text
Parent
   ↓
controls child placement / arrangement
   ↓
Child
   ↓
renders its own content within available constraints
```

Parent properties control parent layout. Child properties control child layout/content within the parent's available constraints.

A child cannot reach upward to modify parent layout configuration. A parent does not implement the child's concrete UI content.

## Unknown child

If a parent receives a child whose type is not registered:

```text
DynamicRenderer
      ↓
category registry lookup
      ↓
NOT REGISTERED
      ↓
controlled unsupported result
```

The parent does not crash merely because the backend introduced a type that this frontend version does not know.

## Invalid hierarchy

Unknown type and invalid structure are separate concerns.

```text
Unknown type
    → controlled not-registered handling

Known type + illegal parent/child relationship
    → invalid Dynamic structure
```

The exact support/validation behavior remains a later Dynamic discussion; DYNAMIC-05 only establishes that the renderer must not reinterpret an illegal hierarchy as a different legal node.

## Frozen DYNAMIC-05 decisions

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
14. The rendering architecture remains intentionally small: Registry + Definition + DynamicRenderer + Compose.

---

# Remaining Dynamic Discussions

## DYNAMIC-06 — NOT STARTED

Actions and interaction behavior.

This will cover the backend action model and how Dynamic UI interactions execute actions without mixing action execution into rendering.

Topics expected to include:

```text
request
navigate
present
dismiss
state
external_uri
sequence
```

and value/reference resolution, request execution, result handling, loading/error behavior, and security boundaries as required by the fixed backend contract.

## DYNAMIC-07 — NOT STARTED

Dynamic runtime state, validation/support compatibility, persistence/versioning/offline behavior, and final cross-platform/runtime concerns will be discussed according to the final seven-point Dynamic scope.

No implementation plan or production code is started from this discussion document until the complete Dynamic discussion is explicitly frozen.

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

The JSON example also confirms that the Dynamic system must support screen-level Theme, common layout/visual capabilities, definition-specific properties, leading/trailing accessories, element-level actions, validation, bindings, and backend-driven destinations. Those concerns are discussed in their respective Dynamic points rather than being hardcoded into business-screen classes.

---

# Discussion Workflow Rule

For each remaining Dynamic point:

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