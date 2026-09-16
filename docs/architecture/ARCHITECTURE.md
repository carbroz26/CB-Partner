# CB-Partner — Frontend Architecture

Status: PROPOSED

Purpose: High-level authoritative frontend architecture.

Current direction: Kotlin Multiplatform, Compose Multiplatform, Clean Architecture, pure Store-based MVI, UDF, no ViewModel, multi-module design, Dependency Injection, and Gradle Convention Plugins.

Goals: clear dependency direction, testability, appropriate code sharing, explicit platform boundaries, predictable state ownership, maintainability, and controlled complexity.

Exact module graph and library choices are not frozen yet.

State management uses Store-based MVI and UDF. ViewModel is outside the intended architecture.

Material architecture changes require an accepted Decision Log entry before implementation.

To finalize after research: module graph, Store contract, DI, networking, persistence, navigation, error model, concurrency, testing, source sets, and build architecture.