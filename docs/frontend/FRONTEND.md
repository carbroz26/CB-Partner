# CB-Partner — Frontend Engineering Guide

**Status:** PROPOSED
**Scope:** Frontend implementation conventions

## 1. UI Architecture
Compose Multiplatform is the UI technology direction. Composables render state and emit user events. They should not own business workflows, networking, persistence, or orchestration.

## 2. State
All feature state follows the accepted Store + MVI + UDF architecture. There is no ViewModel layer.

A feature should make its state, events/intents, and externally visible effects explicit.

## 3. Feature Boundaries
A feature should have a clear responsibility and public contract. Avoid creating a “common” abstraction until multiple real consumers justify it.

## 4. Domain and UI
UI models may differ from domain models. Mapping belongs at an intentional boundary rather than being spread through composables.

## 5. Platform
Common code is preferred when behavior is genuinely common. Platform-specific code remains explicit through appropriate KMP mechanisms.

## 6. Design System
The project should establish one shared theme/design system rather than allowing every screen to invent its own typography, spacing, colors, shapes, and components.

The design system must evolve from actual product needs; it should not become a giant component library before screens exist.

## 7. Navigation
Navigation will follow the approved architecture and must not become a second place for business state. Navigation decisions are frozen separately before the navigation foundation is implemented.

## 8. Resources
Resource, localization, image, font and icon handling must use the approved multiplatform strategy. Avoid platform-specific resource assumptions in shared code.

## 9. Accessibility
Features should account for semantic labels, touch targets, readable contrast, dynamic text where supported, keyboard/desktop interaction where relevant, and screen-reader behavior where applicable.

## 10. Performance
Avoid unnecessary recomposition, uncontrolled collection, excessive work on the main thread, and loading entire datasets when a smaller state is sufficient. Performance optimization must be evidence-driven.

## 11. Testing
Test state transitions and business behavior independently of UI where practical. UI tests cover important user-visible behavior. Platform compilation is part of KMP feature verification.

## 12. Code Review
Review UI for state ownership, lifecycle safety, platform correctness, accessibility, testability, and unnecessary abstraction.

## 13. Not Yet Frozen
Exact design system, navigation implementation, resource tooling, localization tooling, UI testing stack, and package/module conventions.

**This document is PROPOSED until explicitly frozen.**