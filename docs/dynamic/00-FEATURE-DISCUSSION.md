# Dynamic UI — Feature Discussion

## Status

**DYNAMIC-01 — FROZEN**  
**DYNAMIC-02 — FROZEN**  
**DYNAMIC-03 — DISCUSSION IN PROGRESS**

Overall Dynamic UI feature discussion remains **IN PROGRESS**. DYNAMIC-01 and DYNAMIC-02 are frozen; DYNAMIC-03 through DYNAMIC-07 are not yet frozen.

---

# Scope

This document is the single discussion document for the complete Dynamic UI feature.

All seven Dynamic architecture discussions will be recorded here. We will not create a separate discussion document for each topic.

The implementation is a fresh design for CB-Partner. The previous SDUI project/reference is used only to understand the fixed JSON contract and intended behavior. Existing classes, registries, builders, and implementation structure will not be copied.

The fixed backend JSON format is not being redesigned as part of this discussion.

Initial Dynamic vocabulary for implementation is intentionally small:

```text
Template
└── stack_template

Component
└── stack_component

Section
└── stack_section

Group
└── stack_group

Elements
├── text
└── image
```

Additional definitions will be added later when the actual backend response requires them.

---

# DYNAMIC-01 — Dynamic Container + Dynamic Screen Lifecycle

## 1. Purpose

The Dynamic system is the main runtime UI system after Bootstrap. It must be able to receive a backend-defined destination, load the corresponding Dynamic screen configuration, render it using registered Dynamic definitions, and move between backend-defined destinations without creating native frontend screen classes such as LoginScreen, OtpScreen, or DashboardScreen.

The Dynamic Container is the permanent host for the current Dynamic destination.

---

## 2. Dynamic Container

There is one Dynamic Container responsible for displaying the current Dynamic destination.

Conceptually:

```text
App
 │
 ├── Splash
 │
 └── DynamicContainer
       │
       └── Current Dynamic Screen
```

The Dynamic Container does not know business screen names such as Login, OTP, Dashboard, Profile, Booking, or Settings.

It works with Dynamic destinations and Dynamic screen configurations.

---

## 3. Dynamic Destination

A Dynamic Destination represents one backend-defined destination that can be placed in the Dynamic navigation stack.

Conceptually it contains:

```text
DynamicDestination
├── templateId
├── templateType
├── screen/config API information
└── other information defined by the fixed JSON contract
```

The exact fields and JSON structure remain governed by the fixed backend contract. This discussion does not redesign that contract.

---

## 4. Bootstrap → Dynamic handoff

Bootstrap is responsible for obtaining the initial configuration information needed to enter the Dynamic experience.

The configuration response provides information for the next Dynamic destination, including the template type, unique template ID, and the API information required to load the actual screen configuration.

The lifecycle is:

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

Bootstrap does not become the owner of the ongoing Dynamic screen lifecycle. Once the Dynamic experience starts, Dynamic owns the subsequent Dynamic destination lifecycle.

---

## 5. Template Type vs Template ID

These two values have different responsibilities and must not be confused.

### templateType

`templateType` determines **how the destination is rendered**.

Example:

```text
stack_template
```

The Dynamic runtime uses the Template Registry to find the registered implementation for that type.

Later, other registered template types can be added.

### templateId

`templateId` identifies **which particular Dynamic destination/screen instance is being represented**.

Each backend-provided destination has its own unique template ID for Dynamic navigation/back-stack identity.

Therefore:

```text
templateType
    ↓
Which Template renderer?

 templateId
    ↓
Which Dynamic destination?
```

This distinction is a fundamental DYNAMIC-01 rule.

---

## 6. Navigation model

The agreed model is the **Destination / Navigation Stack model**.

We do not simply replace one template in a single state variable and separately invent back behavior.

The Dynamic navigation stack contains Dynamic destinations.

Example:

```text
Navigation Stack

┌────────────────────────┐
│ templateId = A         │
├────────────────────────┤
│ templateId = B         │
├────────────────────────┤
│ templateId = C         │ ← current
└────────────────────────┘
```

The Dynamic Container displays the current destination.

The container itself does not own navigation decisions. Navigation determines which Dynamic destination is current.

---

## 7. Login → OTP → Dashboard concept

There are no native Dynamic screen classes such as:

```text
LoginScreen
OtpScreen
DashboardScreen
```

Instead, these business experiences are represented by backend-defined Dynamic destinations.

Conceptually:

```text
Destination A
    ↓
Destination B
    ↓
Destination C
```

For example:

```text
Login
templateId = login_123
        ↓
OTP
 templateId = otp_456
        ↓
Dashboard
 templateId = dashboard_789
```

Dynamic itself only needs the destination information and the fixed JSON contract. It does not need frontend classes named after these business screens.

---

## 8. Loading the actual Dynamic screen

The configuration/bootstrap response provides the destination information and the API required to obtain the actual screen configuration.

The runtime then loads the screen API for the current destination.

Conceptually:

```text
DynamicDestination
       ↓
Screen API
       ↓
Dynamic Screen JSON
       ↓
Decode
       ↓
Render
```

The actual UI tree is supplied by the screen response according to the fixed JSON contract.

The current implementation will initially support only the small registered vocabulary defined in this document's scope.

---

## 9. Template responsibility

A Template determines **how a Dynamic screen is rendered**.

It does not determine navigation.

For example:

```text
Dynamic Destination
       │
       ├── templateId   → destination identity
       │
       └── templateType → renderer selection
```

The Template Registry resolves `templateType` to the registered Template implementation.

For the initial implementation, only `stack_template` is required.

---

## 10. Dynamic Container lifecycle

The agreed lifecycle is:

```text
                         SPLASH
                            │
                            │ Bootstrap
                            ▼
                 Initial DynamicDestination
                            │
                            ▼
                   Navigation / Current
                            │
                            ▼
                  ┌──────────────────┐
                  │ Dynamic Container │
                  └────────┬─────────┘
                           │
                           ▼
                  Load Current Destination
                           │
                           ▼
                        Backend
                           │
                           ▼
                    Dynamic Screen JSON
                           │
                           ▼
                       Decode
                           │
                           ▼
                 Registered definitions
                           │
                           ▼
                  Template / UI rendering
                           │
                           ▼
                      Compose UI
                           │
                           │ user action
                           ▼
                         Action
                           │
                  ┌────────┴─────────┐
                  │                  │
             same destination   new destination
                  │                  │
                  ▼                  ▼
              update state      Navigation push
                                     │
                                     ▼
                              Dynamic Container
                                     │
                                     ▼
                               Load + Render
```

The exact action model will be designed in DYNAMIC-06.

---

## 11. Navigation and Back

Because Dynamic destinations are navigation entries, system Back can operate on the Dynamic navigation stack.

Example:

```text
Before:

Login Destination
OTP Destination  ← current
```

Back:

```text
OTP Destination is popped
        ↓
Login Destination becomes current
        ↓
Dynamic Container displays Login Destination
```

No business-screen-specific `if currentScreen == ...` logic is required.

---

## 12. Refresh / reload

Refreshing the current Dynamic destination does not require creating a new navigation destination.

Conceptually:

```text
Current Dynamic Destination
        ↓
Reload its screen API
        ↓
Receive new Dynamic Screen JSON
        ↓
Render updated UI
```

The navigation identity and the loaded screen configuration are therefore conceptually separate.

---

## 13. Unsupported Dynamic definitions

The backend may eventually send a Template, Component, Section, Group, or Element that the installed application does not currently support.

The Dynamic runtime must not guess an implementation and must not crash merely because a backend definition is unknown.

Conceptually:

```text
Backend definition
       ↓
Registry lookup
       ↓
Registered?
   ┌───┴───┐
  YES     NO
   │       │
   ▼       ▼
Render  Controlled unsupported handling
```

The exact fallback/error UI is intentionally not frozen in DYNAMIC-01. It will be addressed after the core Dynamic architecture discussions where appropriate.

---

## 14. Initial Dynamic vocabulary

The first implementation intentionally contains only enough definitions to prove the complete Dynamic pipeline:

```text
Template
└── stack_template

Component
└── stack_component

Section
└── stack_section

Group
└── stack_group

Elements
├── text
└── image
```

Later definitions are added incrementally when the real backend contract requires them.

The Dynamic architecture must not need to be redesigned when additional registered definitions are introduced.

---

## 15. Core separation of responsibilities

The following mental model is frozen for DYNAMIC-01:

```text
Navigation
    = WHERE

Dynamic Screen
    = WHAT

Template
    = HOW

Dynamic Container
    = WHERE IT IS DISPLAYED
```

More specifically:

```text
Navigation
    → owns the current Dynamic destination and stack

Dynamic Destination
    → identifies the backend-defined destination

Dynamic Screen
    → contains the backend-defined UI configuration

Template
    → determines the rendering structure

Dynamic Container
    → displays the current Dynamic destination
```

---

# DYNAMIC-01 — Frozen Decisions

1. **One permanent Dynamic Container** hosts the current Dynamic destination.

2. **Dynamic uses the Destination / Navigation Stack model**, not a simple template replacement model.

3. Every Dynamic destination has a backend-provided **unique `templateId`** used as its Dynamic destination/back-stack identity.

4. `templateType` determines **which registered Template renderer** is used.

5. The Dynamic destination contains the information required to obtain the actual screen JSON from its API according to the fixed backend contract.

6. The Dynamic navigation stack stores Dynamic destinations and therefore supports push/pop/back behavior without creating native Login/OTP/Dashboard screen classes.

7. The Dynamic Container renders the destination currently selected/current in the Dynamic navigation stack.

8. The actual UI tree comes from the Dynamic screen response:

```text
Screen
 → Template
 → Component
 → Section
 → Group
 → Element
```

9. Unknown/unregistered Dynamic definitions must be handled safely rather than causing the application to crash. Exact fallback behavior is deferred until the relevant discussion.

10. Initial implementation is intentionally limited to:

```text
stack_template
stack_component
stack_section
stack_group
text
image
```

11. Additional Dynamic definitions will be added incrementally when required by actual backend responses; adding definitions must not require redesigning the Dynamic core architecture.

---

# DYNAMIC-02 — Screen → Template → Component → Section → Group → Element

## Status

**FROZEN**

## Purpose

Define the exact Dynamic UI hierarchy and the responsibility of each level while keeping the hierarchy flexible enough for simple and complex backend-driven UIs without adding unnecessary structural layers.

## 1. Screen — Root Dynamic Configuration

Screen is the root Dynamic configuration.

A Screen owns exactly one Template and may also contain screen-level configuration such as Theme, Header, and Footer when provided by the fixed JSON contract.

Conceptually:

```text
Screen
├── screen-level configuration
├── Theme
├── Header / Footer (when provided)
└── Template
```

Theme is a Screen-level concern. Header and Footer are also Screen-level concerns rather than Template, Component, Section, Group, or Element concerns.

The exact Header/Footer structures are not expanded by DYNAMIC-02 unless required by the fixed contract.

## 2. Template — Complete Screen-Level Layout Strategy

Template represents the complete screen-level layout strategy.

A Screen contains exactly one Template.

Template does not represent business screens such as Login, OTP, or Dashboard. It represents the rendering/composition strategy for the complete Dynamic screen.

Example:

```text
Screen
└── stack_template
```

## 3. Component — Flexible Composition Block

A Template can contain multiple Components.

Component can contain multiple Elements, multiple Sections, or both Elements and Sections.

All of the following are legal:

```text
Component
├── Element
├── Element
└── Element
```

```text
Component
├── Section
├── Section
└── Section
```

```text
Component
├── Element
├── Section
├── Element
└── Section
```

Therefore Component does not require an XOR choice between Elements and Sections. Both child types may be present, and their backend-provided ordering is significant and must be preserved.

## 4. Section — Optional Composition Level

Section is an optional structural level used when the UI requires additional grouping/composition.

Section can contain multiple Elements, multiple Groups, or both Elements and Groups.

Examples:

```text
Section
├── Element
├── Element
└── Element
```

```text
Section
├── Group
├── Group
└── Group
```

```text
Section
├── Element
├── Group
├── Element
└── Group
```

Section does not require Groups to exist. A Section can directly contain Elements.

The backend-provided ordering of Elements and Groups is significant and must be preserved.

## 5. Group — Final Structural Grouping Level

Group is the final optional structural level before Elements.

A Group can contain one or multiple Elements.

```text
Group
├── Element
├── Element
└── Element
```

Group cannot contain another Group, Section, Component, Template, or Screen.

## 6. Element — Terminal UI Node

Element is the terminal UI node.

An Element has no Dynamic child nodes.

The initial implementation will contain:

```text
text
image
```

Additional Elements such as `input` or `button` will be introduced later when required by the actual backend contract.

## 7. Minimum Valid Structural Path

Every Dynamic screen always has the minimum structural path:

```text
Screen → Template → Component → Element
```

Therefore a simple screen can be:

```text
Screen
└── Template
    └── Component
        ├── Element
        ├── Element
        └── Element
```

Section and Group are optional structural levels and are introduced only when the backend configuration requires additional composition.

## 8. Legal Structural Paths

The core legal paths are:

```text
Screen → Template → Component → Element

Screen → Template → Component → Section → Element

Screen → Template → Component → Section → Group → Element
```

Because Components can contain both Elements and Sections, and Sections can contain both Elements and Groups, valid configurations can also combine these paths within the same Template.

For example:

```text
Screen
└── Template
    ├── Component
    │   ├── Element
    │   ├── Section
    │   │   ├── Element
    │   │   └── Group
    │   │       ├── Element
    │   │       └── Element
    │   └── Element
    │
    └── Component
        └── Element
```

## 9. Cardinality Rules

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

Only Template is singular within its Screen parent. All other structural node types can occur multiple times where their parent allows them.

## 10. Ordering

The order supplied by the backend is significant.

The renderer must preserve the order of:

- Components inside Template
- Elements and Sections inside Component
- Elements and Groups inside Section
- Elements inside Group

The Dynamic runtime must not reorder these nodes based on frontend implementation details.

## 11. Structural Hierarchy vs Visual Layout

DYNAMIC-02 defines the structural hierarchy only.

The existence of a Component, Section, or Group does not by itself define all visual behavior. Visual layout behavior and rendering details belong to the registered definitions and the rendering discussion in DYNAMIC-04.

This keeps hierarchy and rendering responsibilities separate.

---

## DYNAMIC-02 — Frozen Decisions

1. **Every Dynamic Screen has exactly one Template.**

2. **Every Template contains one or more Components.**

3. **A Component may contain multiple Elements, multiple Sections, or both.**

4. **A Section may contain multiple Elements, multiple Groups, or both.**

5. **A Group may contain one or more Elements only.**

6. **Element is terminal and has no Dynamic children.**

7. **The minimum valid Dynamic screen path is `Screen → Template → Component → Element`.**

8. **Section and Group are optional structural levels**, used only when the backend-defined UI requires additional composition.

9. **Only Template is singular within Screen; all other structural levels may occur multiple times where legally allowed.**

10. **Backend ordering is significant and must be preserved during rendering.**

11. **Screen owns screen-level concerns such as Theme and, when provided, Header/Footer.**

12. **Template is the complete screen-level layout strategy and is not a business-screen class.**

13. **Component and Section are flexible composition levels; they are not forced into an XOR-only child model.**

14. **Structural hierarchy and visual rendering behavior remain separate concerns.**

---

# DYNAMIC-03 — Registration System

**Status:** DISCUSSION IN PROGRESS

## Objective

Define the simplest clean registration and lookup system for Dynamic Templates, Components, Sections, Groups, and Elements.

The system must answer:

```text
Backend type name
        ↓
Is it registered?
        ↓
Yes → use its registered implementation
No  → controlled unsupported handling
```

The discussion will establish:

- What a registration represents
- Template registration
- Component registration
- Section registration
- Group registration
- Element registration
- Registry ownership
- How backend type names map to implementations
- How registration is performed at application startup
- How lookup works during Dynamic rendering
- How unknown/unregistered definitions are reported
- Whether one registry or separate registries are clearer
- How the initial definitions are registered
- How future definitions can be added without changing the Dynamic core

The implementation should remain simple and avoid a second SDUI language, duplicate registries, unnecessary factories, or an over-engineered plugin system.

---

# DYNAMIC-04 — Rendering System

**Status:** NOT STARTED

To define how registered Dynamic definitions become Compose UI.

---

# DYNAMIC-05 — Properties + State + Input Handling

**Status:** NOT STARTED

To define Dynamic properties, state, input values, and input handling.

---

# DYNAMIC-06 — Actions

**Status:** NOT STARTED

To define Dynamic actions including request, navigation, state, external URI, and other actions actually required by the fixed contract.

---

# DYNAMIC-07 — Dynamic Flow

**Status:** NOT STARTED

To define the complete backend-driven flow from one Dynamic destination to the next, including the relationship between Bootstrap configuration, actions, responses, and Dynamic navigation.

---

# Final Dynamic Architecture Decisions

**Not yet available.** This section will be completed only after DYNAMIC-01 through DYNAMIC-07 have been discussed and frozen.
