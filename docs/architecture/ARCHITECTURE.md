# CB-Partner — Frontend Architecture

**Status:** PROPOSED
**Scope:** Frontend repository only

## Purpose
Authoritative high-level architecture. Detailed technology decisions belong in ADRs; feature implementation details belong in feature documentation.

## Architectural Direction
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Pure Store-based MVI
- UDF
- No ViewModel
- Multi-module architecture
- Dependency Injection
- Gradle Convention Plugins

## Goals
Testability, understandable dependency direction, appropriate code sharing, explicit platform boundaries, predictable state ownership, maintainability, and controlled complexity.

## Layering Principle
Dependencies must follow stable boundaries. Presentation must not directly depend on transport implementation. Domain/application behavior should remain independent of UI and transport details.

## Store Principle
The Store is the state-management boundary. UI sends events; Store coordinates state and effects according to the final accepted Store contract. ViewModel is not part of the architecture.

## Module Principle
Create modules for real boundaries: feature isolation, dependency control, platform separation, reusable capability, ownership, or build concerns. Do not create modules merely to maximize count.

## Platform Principle
Share code when it provides real value. Keep platform-specific APIs and behavior explicit and localized.

## Technology Selection
Every material library/tool choice requires a documented decision considering KMP support, maturity, maintenance, compatibility, testing, build impact, and project fit.

## Architecture Change
A material architecture change requires discussion, evidence where appropriate, an updated decision, explicit acceptance, and an implementation/migration plan.

## To Be Frozen
Exact module graph, Store contract, DI, networking, persistence, navigation, error model, concurrency model, testing structure, source-set strategy, and build conventions.

**This document is PROPOSED until reviewed and explicitly frozen.**
## Lifecycle Governance
Significant architecture is researched, decided, frozen, and implemented in small units. Store contract, module graph, DI, networking, build conventions, navigation, testing structure and platform strategy are not treated as frozen until accepted.

## No ViewModel
ViewModel is explicitly excluded. State ownership uses pure Store + MVI + UDF.

## Complexity Control
Patterns, Singleton lifetimes, abstractions and modules require concrete responsibility/lifecycle justification. Do not introduce them speculatively.
