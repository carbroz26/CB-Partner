# CB-Partner — BASE-ARCH-015 Decision Record

**Work Item:** BASE-ARCH-015 — Runtime Dependency & Technical Infrastructure Implementation Contract  
**Status:** DECISION_FROZEN  
**Scope:** Base runtime infrastructure only  
**Parent decisions:** BASE-ARCH-001 through BASE-ARCH-014  
**Implementation:** Not started  
**Freeze:** Approved by user

## Freeze Declaration

BASE-ARCH-015 A–F are explicitly accepted and frozen.

This freeze converts the selected runtime technologies into an implementation contract without changing the frozen architecture through BASE-ARCH-014.

The freeze does not authorize unrelated production work. Implementation must remain within the contracts below and follow the established project workflow.

## 015-A — Core Runtime Contracts

**Decision:** ACCEPT — FROZEN

### Contract

Use the already selected technical foundations only where a real consumer requires them:

- Ktor Client for networking.
- kotlinx.serialization for serialization.
- kotlinx.coroutines for asynchronous/concurrent work.
- Kermit for logging.
- Kotlin `Result<T>` as the generic success/failure primitive.

Core remains a technical foundation and does not become a business, Store, Navigation, or DI layer.

### Consequences

- Runtime infrastructure is introduced incrementally by actual consumers.
- No placeholder abstractions or dependency-only modules are added merely to make the project appear complete.
- Existing BASE-ARCH-014 scope remains unchanged.

## 015-B — Koin Composition

**Decision:** ACCEPT — FROZEN

### Contract

- Koin is the primary DI technology.
- Koin Compiler Plugin remains accepted.
- Constructor injection is the normal dependency mechanism.
- Application Composition Root is the central assembly boundary.
- Logical DI definitions may remain module-local and are assembled at application level.
- Tests should prefer constructor-based replacement and should not require Koin for ordinary unit tests.
- Store lifecycle and business lifecycle remain outside DI ownership.

### Consequences

- No standalone `:di` module is introduced solely for DI.
- No normal internal `get()` / `inject()` service-locator pattern.
- Store instances are not automatically singletons.

## 015-C — Navigation 3 Integration

**Decision:** ACCEPT — FROZEN

### Contract

Compose Multiplatform Navigation 3 remains the primary navigation direction established by BASE-ARCH-005.

`:navigation` owns navigation contracts/infrastructure, destination integration, back-stack integration, root orchestration, destination registration, required serialization integration, and platform navigation behavior.

The Store remains separate from navigation state. Navigation is represented as controlled one-off output/effect rather than Store State.

Exact Navigation 3 APIs, destination-registration mechanism, command/bridge API, and dependency version are implementation-level details and are not treated as a new architecture decision.

### Consequences

- No Decompose, Circuit, Voyager, or custom navigation framework is introduced as the primary architecture.
- Navigation implementation may proceed when the authorized runtime implementation work item requires it.

## 015-D — Pure Store / MVIKotlin

**Decision:** ACCEPT — FROZEN

### Contract

CB-Partner uses Pure Store + MVI/UDF as frozen by BASE-ARCH-007:

```text
UI
 ↓
Intent
 ↓
Store
 ↓
Executor
 ↓
Message
 ↓
Reducer
 ↓
State
 ↓
UI
```

MVIKotlin is the Store implementation/reference technology. Its surrounding architecture is not adopted wholesale.

The project does not use ViewModel.

Store lifecycle is feature/application-owned and follows the frozen lifecycle model. Navigation remains outside Store State.

### Validation baseline

The combined dependency proof resolved MVIKotlin `4.4.0` with Essenty `2.5.0` and Kotlin `2.4.20` on the validated JVM dependency graph.

### Consequences

- No separate `:store`, `:mvi`, or `:presentation-core` architecture is introduced merely to wrap MVIKotlin.
- Reducer and Store behavior remain independently testable.

## 015-E — Runtime Compatibility Baseline

**Decision:** ACCEPT — FROZEN

### Validated implementation baseline

The temporary compatibility proof resolved the combined JVM dependency graph against the project's selected toolchain baseline:

- Kotlin `2.4.20`
- Gradle `9.7.1`
- AGP `9.3.0`
- Compose Multiplatform `1.12.0`
- Koin `4.2.0` runtime candidate
- Ktor `3.6.0`
- kotlinx.coroutines `1.11.0`
- kotlinx.serialization `1.11.0`
- MVIKotlin `4.4.0`
- Essenty `2.5.0`

The proof completed successfully and Gradle aligned transitive Kotlin stdlib requests to `2.4.20` in the validated graph.

This is an implementation baseline, not a reason to add every dependency immediately to production modules.

### Consequences

- Runtime dependencies are added only when an authorized implementation unit has a real consumer.
- Dependency versions are centralized through the existing version catalog.
- Future upgrades require the normal architecture/build verification workflow rather than ad-hoc version changes.

## 015-F — Runtime Testing Boundaries

**Decision:** ACCEPT — FROZEN

### Contract

Testing follows the existing architecture boundaries:

- Pure reducers are unit tested directly.
- Store/executor behavior is tested without requiring live production services.
- Serialization and mapping behavior is tested independently.
- Repository behavior is tested with replaceable dependencies/fakes where appropriate.
- DI graph verification is separate from ordinary unit tests.
- UI testing remains separate from Store/domain tests.
- Platform-specific runtime behavior is verified at the appropriate platform boundary.

### Consequences

- Tests do not force production infrastructure into unit-test scope.
- Runtime infrastructure can be verified incrementally as implementation consumers are introduced.

## Cross-Decision Constraints

BASE-ARCH-015 does not authorize:

- ViewModel.
- A second lifecycle architecture.
- A standalone `:di`, `:store`, `:mvi`, or `:presentation-core` module without a separately justified architecture decision.
- Business features such as authentication, booking, payment, or dashboard implementation.
- SDUI implementation.
- Dynamic JSON/template/registry/renderer implementation.
- Fake production bootstrap/API infrastructure.
- Broad refactoring outside the authorized runtime infrastructure scope.
- Adding libraries without a real consumer.

## Freeze Gate

**Current state: DECISION_FROZEN.**

BASE-ARCH-015 A–F were explicitly accepted and frozen by the user.

Next workflow:

1. Synchronize the project architecture/status tracker.
2. Commit the documentation change on the authorized architecture branch.
3. Review/merge according to the project's established workflow.
4. Begin the next separately authorized implementation work item.

No unrelated production implementation is authorized by this record.
