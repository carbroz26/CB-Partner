# CB-Partner — AI Context

**Status:** PROPOSED
**Scope:** Frontend repository
**Repository:** carbroz26/CB-Partner

## Project Identity
CB-Partner is the partner/service-provider frontend for a doorstep car-washing platform. This is a standalone frontend project. The backend has a separate repository, documentation system, and Trello workspace.

## Product Context
The application serves service partners who perform doorstep car-washing services. Exact workflows are discovered and frozen feature-by-feature. AI must not invent business workflows.

## Technical Direction
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Pure Store-based MVI
- UDF
- No ViewModel
- Multi-module architecture
- Dependency Injection
- Gradle Convention Plugins

These are the current direction. Exact libraries and detailed contracts become binding only when accepted in the Decision Log.

## Targets
Android, iOS, and Desktop are intended targets where configured. Platform-specific requirements must be documented rather than assumed.

## Session Entry
A new AI session reads AI_GUIDELINES.md, AI-RULES, CURRENT-STATUS, DECISION-LOG, and relevant architecture/feature documents, then inspects the actual repository and Git state.

## Authority
The current user's explicit decision is authoritative for active work. Accepted project decisions are durable technical authority. Previous conversations are historical context and cannot override current documentation.

## Do Not Assume
Do not assume DI, networking, navigation, persistence, API behavior, product rules, module names, package names, release dates, or platform behavior until documented.

## Update Rule
Keep this document short and stable. It is project context, not a task history.

**This document is PROPOSED until explicitly frozen.**