# CB-Partner — Frontend Architecture

**Status:** DECISION-FROZEN ARCHITECTURE RECORD THROUGH BASE-ARCH-012  
**Scope:** Frontend repository only

## Purpose
Authoritative high-level architecture. Detailed Base Architecture research and frozen decisions are recorded in `BASE-ARCHITECTURE.md`. Feature implementation details belong in feature/module documentation.

## Current Architecture Direction
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Pure Store-based MVI
- UDF
- No ViewModel
- Multi-module architecture
- Dependency Injection
- Gradle Convention Plugins

## Base Architecture Decision Record

See:

`docs/architecture/BASE-ARCHITECTURE.md`

This record documents BASE-ARCH-001 through BASE-ARCH-012. All twelve units are decision-frozen; 001–003 are the frozen foundation decisions.

## Frozen Architecture Through BASE-ARCH-012

- Single `:core` technical foundation with package-level organization.
- Ktor Client, kotlinx.serialization, kotlinx.coroutines and Kermit selected for the relevant technical foundations.
- Kotlin `Result<T>` is the generic success/failure primitive.
- Compose Multiplatform Resources is the selected resource technology.
- Navigation 3 is the selected primary navigation direction.
- Single `:navigation` module.
- Koin is the selected primary DI technology with constructor injection and application composition root.
- Pure Store + MVI/UDF is the presentation architecture.
- MVIKotlin is the selected Store implementation/reference technology without adopting its surrounding architecture wholesale.
- ViewModel is explicitly excluded.
- Feature-owned Stores are the default.
- Platform apps are thin entry points.
- Application Root is the composition boundary.
- Startup initialization follows Platform Entry → Application Root → DI → Navigation → Splash Store → Bootstrap.
- Startup failure/retry belongs to Splash.
- Bootstrap is exposed through a Domain-facing abstraction and implemented in Data.
- Bootstrap returns a UI-independent `Result<T>`.
- Bootstrap lifecycle is owned by the Store and is cancellable.
- Business-specific authentication, booking, payment and SDUI internals remain outside this Base Architecture scope.

## Current Scope

BASE-ARCH-001–012 are decision-frozen. BASE-ARCH-001–003 are the frozen foundation decisions for structure, responsibilities/dependencies, and module granularity.

## Implementation Rule

This document and `BASE-ARCHITECTURE.md` are architecture inputs only. Application source implementation requires a separately frozen implementation plan.

## Change Governance

A material architecture change requires discussion, evidence where appropriate, an updated decision, explicit acceptance, and an implementation/migration plan.

## Complexity Control

Patterns, Singleton lifetimes, abstractions and modules require concrete responsibility/lifecycle justification. Do not introduce them speculatively.
