# CB-Partner — BASE-ARCH-015 Decision Record

**Work Item:** BASE-ARCH-015 — Runtime Dependency & Technical Infrastructure Implementation Contract  
**State:** DECISION_FROZEN  
**Owner approval:** Explicitly accepted and frozen by the project owner.  
**Implementation state:** Not implemented by this decision record.

## Frozen Decisions

### 015-A — Core Runtime Contracts
**State:** ACCEPTED / FROZEN

Use the selected runtime foundations only when a real consumer requires them: Ktor Client, kotlinx.serialization, kotlinx.coroutines, Kermit, and Kotlin `Result<T>`. Runtime libraries are not added merely to make the project appear complete.

### 015-B — Koin Composition
**State:** ACCEPTED / FROZEN

Koin is the primary DI technology. Constructor injection is primary. The Application Composition Root assembles the dependency graph. DI definitions may remain module-local and are assembled at application level. Store instances are not automatic singletons and Koin does not own Store lifecycle. No standalone `:di` module is required by this decision.

### 015-C — Navigation 3 Integration
**State:** ACCEPTED / FROZEN

Compose Multiplatform Navigation 3 remains the primary navigation direction. Navigation remains separated from Store State and Pure Store lifecycle. Exact API-level implementation details are implementation-planning concerns and do not reopen the architecture decision.

### 015-D — Pure Store / MVIKotlin
**State:** ACCEPTED / FROZEN

CB-Partner uses Pure Store + MVI/UDF. MVIKotlin is the Store implementation/reference technology without adopting its surrounding architecture wholesale. ViewModel is not used. Store lifecycle remains feature/application-owned.

### 015-E — Runtime Compatibility Baseline
**State:** ACCEPTED / FROZEN

The selected implementation baseline is Kotlin 2.4.20, Gradle 9.7.1, AGP 9.3.0, Compose Multiplatform 1.12.0, with the runtime technology versions established during BASE-ARCH-015 research/validation. Dependency versions remain centralized and runtime libraries are introduced only with an authorized real consumer.

### 015-F — Runtime Testing Boundaries
**State:** ACCEPTED / FROZEN

Reducers, Store/executor behavior, serialization/mapping, repository behavior, and DI/application composition are tested according to their architectural boundaries. Ordinary unit tests should not require live production services or Koin merely to exercise pure behavior. Platform-specific runtime behavior is verified at the appropriate platform boundary.

## Non-Goals

BASE-ARCH-015 does not authorize authentication, booking, payment, dashboard, SDUI internals, dynamic JSON/template/registry/renderer implementation, fake bootstrap/backend infrastructure, ViewModel, speculative architecture modules, or unrelated refactoring.

## Freeze Statement

BASE-ARCH-015 A–F are frozen. Any change to these decisions requires the established reopening process and concrete evidence.
