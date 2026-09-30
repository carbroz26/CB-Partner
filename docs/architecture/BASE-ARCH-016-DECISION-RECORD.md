# CB-Partner — BASE-ARCH-016 Decision Record

**Work Item:** BASE-ARCH-016 — Koin Composition Root & Dependency Injection Implementation Contract  
**State:** DECISION_FROZEN  
**Owner approval:** Explicitly accepted and frozen by the project owner.  
**Implementation state:** Not implemented by this decision record.

## Frozen Decisions

### 016-A — Koin Version and Compiler Plugin
**State:** ACCEPTED / FROZEN

Use the Koin 4.2.x line with the Koin Compiler Plugin compatible with the project's Kotlin 2.4.20 baseline. The accepted implementation baseline from the decision review is Koin 4.2.0 and Compiler Plugin 1.2.1. Do not use the deprecated Koin KSP processor as the project DI foundation.

### 016-B — Compiler Plugin and Constructor Injection
**State:** ACCEPTED / FROZEN

The Koin Compiler Plugin is used at the composition layer while constructor injection remains the primary dependency-design rule. Ordinary domain, data, Store, and infrastructure classes should not become Koin service-locator consumers without a separately justified framework-boundary need.

### 016-C — DI Module Organization
**State:** ACCEPTED / FROZEN

DI definitions remain associated with the module that owns the dependency. Application-level composition assembles the logical modules. No standalone `:di`, `:dependency-injection`, or equivalent module is introduced solely for Koin organization.

### 016-D — DI Graph Validation and Testing
**State:** ACCEPTED / FROZEN

Koin compiler validation is the primary DI graph-safety mechanism. Targeted application-composition/graph tests may be used where runtime assembly needs verification. Ordinary unit tests remain independently testable without requiring Koin. JVM-only verification APIs are not the architectural foundation for the KMP testing strategy.

### 016-E — Compose Integration
**State:** ACCEPTED / FROZEN

Koin Compose integration is introduced only when an actual Compose-layer injection requirement exists. Koin ViewModel artifacts and ViewModel-based integration are excluded because CB-Partner uses Pure Store and explicitly does not use ViewModel.

### 016-F — Platform Dependency Boundary
**State:** ACCEPTED / FROZEN

Platform-specific dependencies enter the shared dependency graph through the established platform application boundaries. Android and Desktop remain Gradle application boundaries; iOS remains the native/Xcode boundary established by BASE-ARCH-014. Shared code must not discover platform services through ad-hoc service-location.

### 016-G — Store Lifecycle Ownership
**State:** ACCEPTED / FROZEN

Koin provides dependencies but does not own Pure Store lifecycle. Store instances follow the frozen feature/application lifecycle and must not become global Koin singletons merely because Koin can provide them.

## Non-Goals

BASE-ARCH-016 does not authorize business features, authentication, booking, payment, dashboard, SDUI implementation, dynamic JSON/template/registry/renderer implementation, Navigation 3 implementation itself, Store implementation itself, Application Bootstrap implementation, ViewModel, a standalone `:di`/`:store`/`:mvi` module, or unrelated refactoring.

## Freeze Statement

BASE-ARCH-016 A–G are frozen. Any change requires the established reopening process and concrete evidence.
