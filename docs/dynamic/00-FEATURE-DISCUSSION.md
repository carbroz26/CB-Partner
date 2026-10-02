# Dynamic UI — Feature Discussion

## Status

- DYNAMIC-01 — FROZEN
- DYNAMIC-02 — FROZEN
- DYNAMIC-03 — FROZEN
- DYNAMIC-04 — DISCUSSION IN PROGRESS
- DYNAMIC-05 — NOT STARTED
- DYNAMIC-06 — NOT STARTED
- DYNAMIC-07 — NOT STARTED

This is the single discussion document for the complete Dynamic UI feature. All seven discussions are recorded here. The implementation is a fresh CB-Partner design; the previous SDUI project/reference is used only to understand the fixed JSON contract and intended behavior. The fixed backend JSON format is not being redesigned here.

Initial vocabulary:

```text
Template  -> stack_template
Component -> stack_component
Section   -> stack_section
Group     -> stack_group
Elements  -> text, image
```

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

A Dynamic destination contains the backend-defined information required to identify and load the destination, including `templateType`, unique `templateId`, and screen/config API information according to the fixed JSON contract.

`templateType` answers **which registered Template renderer?** `templateId` answers **which Dynamic destination/back-stack entry?**.

Example:

```text
Login      templateId = login_123
   ↓
OTP        templateId = otp_456
   ↓
Dashboard  templateId = dashboard_789
```

The Dynamic navigation stack owns push/pop/back. The Dynamic Container displays the current destination. Refreshing a current destination reloads its screen API without creating a new navigation entry.

Unknown/unregistered definitions must be handled safely; exact fallback presentation is deferred to the relevant discussion.

Frozen DYNAMIC-01 principles:

1. One permanent Dynamic Container.
2. Destination / Navigation Stack model.
3. Unique backend `templateId` identifies a Dynamic destination/back-stack entry.
4. `templateType` selects the registered Template renderer.
5. Destination contains information required to obtain the actual screen JSON.
6. Dynamic navigation supports push/pop/back without business-screen-specific frontend classes.
7. Dynamic Container renders the current destination.
8. Actual UI comes from the backend Dynamic Screen JSON.
9. Unknown definitions have controlled unsupported handling.
10. Initial definitions are `stack_template`, `stack_component`, `stack_section`, `stack_group`, `text`, `image`.
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

Theme, Header and Footer are Screen-level concerns.

## Template

Template is the complete screen-level layout strategy. A Screen has exactly one Template. Template does not mean Login, OTP, Dashboard, etc.; it defines the rendering/composition strategy.

## Component

A Template contains multiple Components. A Component may contain multiple Elements, multiple Sections, or both. Ordering supplied by the backend is significant.

```text
Component
├── Element*
└── Section*
```

Both child collections may be present.

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

Element is terminal and has no Dynamic children. Initial elements are `text` and `image`; additional elements are added when the actual backend contract requires them.

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

DYNAMIC-02 defines structure only; visual rendering behavior belongs to the registered definitions and DYNAMIC-04.

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
13. Component and Section are flexible composition levels, not XOR-only levels.
14. Structural hierarchy and rendering behavior remain separate.

---

# DYNAMIC-03 — Registration System

## Status

**FROZEN**

## Objective

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

There is one central public `DynamicRegistry` for the Dynamic system.

Conceptually:

```text
DynamicRegistry
├── Templates
├── Components
├── Sections
├── Groups
└── Elements
```

Internally it remains strongly typed by node category:

```text
DynamicRegistry
├── templates: Map<String, TemplateDefinition>
├── components: Map<String, ComponentDefinition>
├── sections: Map<String, SectionDefinition>
├── groups: Map<String, GroupDefinition>
└── elements: Map<String, ElementDefinition>
```

Exact Kotlin implementation types are an implementation-plan concern.

## 2. Explicit registration

Registration is explicit and simple:

```text
registry.registerTemplate(...)
registry.registerComponent(...)
registry.registerSection(...)
registry.registerGroup(...)
registry.registerElement(...)
```

Initial built-in definitions are explicitly registered during application/runtime setup.

There is no reflection, package scanning, or automatic discovery.

## 3. What is registered

The registry stores a definition/implementation capable of handling a backend node type. It does not store rendered UI instances or screen instances.

Conceptually:

```text
Registration
├── type = "stack_template"
└── definition/renderer = StackTemplate
```

The exact implementation shape is deferred to the implementation plan.

## 4. Typed lookup

Lookup is typed by node category:

```text
findTemplate("stack_template")
findComponent("stack_component")
findSection("stack_section")
findGroup("stack_group")
findElement("text")
```

There is no giant untyped `get(type)` returning an arbitrary object.

## 5. Initial registrations

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

## 6. Duplicate registration

Duplicate registration of the same type in the same node category is an error and must not silently replace an existing definition.

```text
registerTemplate("stack_template", A)
registerTemplate("stack_template", B)
```

must result in a clear registration error.

## 7. Unknown / unregistered definitions

When backend JSON contains an unknown type:

```text
Backend type
    ↓
DynamicRegistry lookup
    ↓
NOT FOUND
    ↓
Controlled unsupported result
```

The registry does not guess another definition or silently substitute a different type. Exact unsupported UI/error presentation is deferred to the relevant Dynamic discussion.

## 8. No reflection / discovery

The initial design does not use reflection, annotation discovery, package scanning, or automatic class discovery. Registered definitions are visible through explicit application setup.

## 9. No generic factory/plugin hierarchy

The architecture does not introduce a large generic factory/provider/adapter/plugin hierarchy merely to perform registration. The registry remains the clear owner of type-to-definition registration and lookup.

## 10. Registry responsibility boundary

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

## DYNAMIC-03 — Frozen Decisions

1. One central public `DynamicRegistry`.
2. Registry is internally strongly typed by Template, Component, Section, Group, and Element.
3. Registration is explicit.
4. Registry stores definitions/implementations, not rendered instances.
5. Lookup is typed by node category.
6. Duplicate registration is an error.
7. Unknown/unregistered types produce a controlled unsupported result.
8. Initial registrations are `stack_template`, `stack_component`, `stack_section`, `stack_group`, `text`, and `image`.
9. Future definitions use the same explicit registration mechanism.
10. No reflection, package scanning, automatic discovery, generic plugin framework, or unnecessary factory hierarchy.
11. Registry owns registration and lookup only.

---

# DYNAMIC-04 — Rendering System

**Status: DISCUSSION IN PROGRESS**

## 1. Definition-Owned Rendering

The agreed direction is that each registered Template, Component, Section, Group, and Element definition owns the Compose rendering for its own node type.

Conceptually:

```text
StackTemplateDefinition
    → renders stack template

StackComponentDefinition
    → renders stack component

StackSectionDefinition
    → renders stack section

StackGroupDefinition
    → renders stack group

TextDefinition
    → renders text

ImageDefinition
    → renders image
```

The system must not become one giant renderer containing every Dynamic type in a large `when`/type switch.

## 2. Small DynamicRenderer

A small central `DynamicRenderer` remains responsible only for resolving the registered definition and delegating rendering.

```text
DynamicRenderer
    ↓
DynamicRegistry lookup
    ↓
registered Definition
    ↓
Definition renders itself
```

`DynamicRenderer` does not own the actual UI implementation of each Dynamic type.

## 3. Render Data

Definitions receive the decoded backend node data required to render their own node.

Conceptually:

```text
Definition
    + NodeData
    + RenderContext
    ↓
Compose UI
```

The exact Kotlin data/definition interfaces remain an implementation-plan concern.

## 4. RenderContext

A small `DynamicRenderContext` is used to provide the runtime rendering capability required for child composition.

It may provide access to the registry/renderer and other strictly rendering-related runtime context required by the final implementation.

It must not become a general-purpose application service container.

## 5. Parent / Child Rendering Responsibility

**FROZEN**

The same parent/child responsibility applies consistently to **Template, Component, Section, and Group**.

```text
Parent
├── owns its own container/layout behavior
├── decides how its legal children are arranged
└── provides the available constraints to its children

Child
├── owns its own content
└── may declare its own legal size/alignment constraints
```

This is a general Dynamic rendering rule, not a special case for any one node type.

### Template

Template owns its own screen-level layout/container behavior and decides how its Components are arranged.

### Component

Component owns its own container/layout behavior and decides how its legal Elements and Sections are arranged.

### Section

Section owns its own container/layout behavior and decides how its legal Elements and Groups are arranged.

### Group

Group owns its own container/layout behavior and decides how its Elements are arranged.

### Element

Element is terminal. It owns its own content rendering and its own legal size/alignment constraints but has no Dynamic children to arrange.

## 6. Parent Controls Placement; Child Controls Itself

The parent-child relationship is therefore:

```text
Parent
   ↓
controls child placement / arrangement
   ↓
Child
   ↓
renders its own content within the available constraints
```

A child does not reach upward to modify its parent's layout configuration.

A parent does not implement the child's concrete UI content.

This keeps composition predictable and allows the same rendering model to work recursively through the complete Dynamic hierarchy.

## 7. Responsive Layout Direction

Dynamic UI must be flexible across different available sizes, including small phones, large phones, tablets, iPads, desktop windows, and web layouts supported by the application.

The preferred direction is **constraint-based/adaptive layout**, not device-specific UI branches.

Conceptually:

```text
same Dynamic JSON
       ↓
different available constraints
       ↓
same registered definitions
       ↓
adaptive Compose layout
```

The renderer must not contain device-specific logic such as:

```text
if iPhone
if Android phone
if iPad
if desktop
```

Layout should respond to available space and the legal layout capabilities of the node definition.

## 8. Layout Capability Direction

Dynamic nodes may need layout information such as:

```text
size
min/max constraints
padding
alignment
arrangement/spacing
```

However, these capabilities are not automatically exposed to every node. The registered definition determines which layout capabilities are legal for that node type.

The exact supported properties and their mapping to the fixed backend JSON contract will be decided in the remaining DYNAMIC-04 discussion before implementation planning.

## 9. Compose Responsibility

Dynamic should use Compose Multiplatform's existing layout and measurement capabilities rather than creating a second responsive layout engine.

Dynamic defines the backend-driven composition and legal layout configuration; Compose performs the actual measurement and layout behavior.

## 10. Fixed JSON Contract

The fixed backend JSON format remains authoritative.

DYNAMIC-04 must map the existing JSON layout/size/alignment information into the new rendering architecture rather than inventing a second Dynamic layout language.

The exact mapping of existing JSON properties to Compose layout behavior remains to be discussed.

## DYNAMIC-04 — Frozen Decisions So Far

1. Registered Template/Component/Section/Group/Element definitions own their own Compose rendering.
2. A small central `DynamicRenderer` resolves definitions through `DynamicRegistry` and delegates rendering.
3. Definitions receive their node data and a small `DynamicRenderContext`.
4. Template, Component, Section, and Group all follow the same parent/child rendering responsibility model.
5. A parent owns its own container/layout behavior and child arrangement.
6. A child owns its own content and may declare its own legal size/alignment constraints.
7. A child never reaches upward to modify parent layout configuration.
8. Element is terminal and renders its own content; it has no Dynamic children to arrange.
9. Responsive behavior is constraint-based/adaptive rather than device-specific.
10. Dynamic uses Compose Multiplatform's existing measurement/layout capabilities rather than creating a second responsive layout engine.
11. The fixed backend JSON contract remains authoritative.
12. The exact layout/size/alignment property mapping is still under DYNAMIC-04 discussion.

## Remaining DYNAMIC-04 Discussion

Next we will define the exact layout model:

```text
1. width / height
2. minWidth / maxWidth
3. minHeight / maxHeight
4. fill / wrap / fixed / fractional sizing
5. padding
6. spacing / arrangement
7. alignment
8. responsive/adaptive behavior
9. mapping of the fixed JSON layout properties to Compose
```

No implementation plan or code is started until DYNAMIC-04 is fully discussed and frozen.

---

# DYNAMIC-05 — Properties + State + Input Handling

**Status: NOT STARTED**

To define Dynamic properties, state, input values, and input handling.

# DYNAMIC-06 — Actions

**Status: NOT STARTED**

To define Dynamic actions including request, navigation, state, external URI, and other actions required by the fixed contract.

# DYNAMIC-07 — Dynamic Flow

**Status: NOT STARTED**

To define the complete backend-driven flow between Dynamic destinations, including Bootstrap configuration, actions, responses, and Dynamic navigation.

---

# Final Dynamic Architecture Decisions

Not yet available. This will be completed only after DYNAMIC-01 through DYNAMIC-07 are discussed and frozen.
