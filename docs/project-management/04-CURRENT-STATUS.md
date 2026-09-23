# CB-Partner — Current Status

**Status:** ACTIVE  
**Last Updated:** 2026-09-23

## Current Phase
Phase 1 — Technical Foundation implementation.

## Current Objective
Implement and verify the frozen BASE-ARCH-014 minimum Gradle/KMP/Compose/build-logic foundation through the approved unit-by-unit workflow.

## Base Architecture State
BASE-ARCH-001 through BASE-ARCH-014 → DECISION_FROZEN.

## Process State
TRELLO-002 — Simple Frontend Work Board → DECISION_FROZEN.

## Current Workflow State
IMPLEMENTING — 014-09 Architecture Verification in progress.

## Current Work Item
BASE-ARCH-014 — Minimum Gradle/KMP/Compose/Build-Logic Foundation.

## Current Implementation Unit
014-01 through 014-07 are verified and accepted.

014-08 Build/Test Baseline is **VERIFIED**.

014-09 Architecture Verification is **IN PROGRESS**.

## What has been implemented

014-01:
- root Gradle settings/build configuration;
- included `build-logic` boundary;
- required module registrations;
- placeholder build files;
- root Gradle properties and ignore rules.

014-02:
- `gradle/libs.versions.toml` with central Kotlin, AGP, Compose versions and approved plugin aliases.

014-03:
- four approved convention plugins;
- intended module application of the conventions.

014-04:
- iOS ARM64;
- iOS Simulator ARM64;
- JVM;
- standard KMP target/source-set configuration.

014-06:
- Android KMP target added through `com.android.kotlin.multiplatform.library`;
- Android KMP `compileSdk = 36`;
- Android application `compileSdk = 36`;
- iOS and Desktop remain thin platform boundaries;
- no business/application behavior was introduced.

## 014-08 Verification

The authorized Build/Test Baseline was executed successfully on 2026-09-23:

- `gradle projects` → **BUILD SUCCESSFUL**; approved project hierarchy and included `:build-logic` confirmed.
- `gradle -p build-logic build` → **BUILD SUCCESSFUL**.
- Shared JVM tests for `:core`, `:domain`, `:data`, `:navigation`, `:feature:splash`, and `:feature:dynamic` → **BUILD SUCCESSFUL**; 28 actionable tasks.
- Shared JVM compilation for the same six modules → **BUILD SUCCESSFUL**; 15 actionable tasks.

The Windows iOS simulator disabled-target warning is expected because iOS simulator tests require macOS. No suppression property was added.

014-08 required no source-code changes.

## Verification State

014-01 through 014-07 verification is complete and accepted.

014-08 Build/Test Baseline verification is complete and verified.

014-09 Architecture Verification is the active verification gate.

The previous iOS discrepancy has been clarified by the owner-approved platform-boundary decision: `iosApp/` is an Xcode application boundary, not a Gradle module. Fresh 014-09 verification is still pending against this clarified rule.

## Explicitly Excluded
- Authentication, OTP, booking, payment, dashboard.
- SDUI internals/dynamic JSON.
- Real backend bootstrap implementation.
- Fake bootstrap.
- Fake JSON.
- Fake backend/API.
- ApplicationBootstrap/Store/Navigation/Koin implementation.
- ViewModel or speculative architecture modules.
- Unrelated refactoring.
- Runtime dependency wiring before its authorized consumer unit.

The later startup vertical slice will use the actual backend API and exact response supplied by the project owner.

## Trello Execution Tracking
Authoritative board: **CB-Partner — Frontend**.

Workflow:
**BACKLOG → TO DO → READY → IN PROGRESS → DONE**

Current Trello state:
- **DONE:** Set Up Gradle Project Structure; Add Version Catalog; Set Up Gradle Build Conventions.
- **IN PROGRESS:** Configure Kotlin Multiplatform Targets; Set Up Platform App Modules.
- **TO DO:** remaining approved foundation and architecture implementation work.
- **BACKLOG:** future/uncommitted work.

## Blocker
No architecture blocker identified for 014-08.

## Next Valid Action
Complete the formal BASE-ARCH-014-09 Architecture Verification against the clarified platform-boundary rule. Verify the actual `iosApp/` native/Xcode boundary; do not treat absence of `:iosApp` from Gradle as a violation. Do not modify source code as part of verification.

## Recovery
A new AI session must read `AI_START_HERE.md`, Tracker, this document, the frozen implementation plan/status, and relevant architecture records before acting.


## 014-08 Verification Checkpoint — 2026-09-23

**State:** VERIFIED

The project owner authorized 014-08 and the actual baseline commands completed successfully.

Verification evidence:
- Project hierarchy and included build verified with `gradle projects`.
- Build logic verified with `gradle -p build-logic build`.
- Six shared `jvmTest` tasks completed successfully.
- Six shared JVM compilation tasks completed successfully.
- Windows iOS simulator disabled-target warning recorded as expected host limitation.

No source-code changes were required for 014-08.

**Current state:** 014-08 VERIFIED; 014-09 Architecture Verification IN PROGRESS.

**Next action:** perform formal 014-09 architecture verification. Do not introduce implementation changes during review.


## 014-09 Architecture Verification — 2026-09-23

**State:** DOCUMENTATION CLARIFICATION COMPLETE — FRESH VERIFICATION PENDING

The project owner explicitly froze the platform-boundary rule: every supported platform has an application boundary, but not every application boundary is a Gradle module.

The clarified mapping is:
- Android: `androidApp/` → `:androidApp` Gradle application.
- Desktop: `desktopApp/` → `:desktopApp` Gradle application.
- iOS: `iosApp/` → native Xcode application boundary; no `:iosApp` Gradle project.

The architecture record and BASE-ARCH-014 plan/status documentation were updated to remove the stale expectation that `:iosApp` must appear in the Gradle hierarchy.

The prior 014-09 finding is therefore superseded. A fresh verification is required to inspect the actual `iosApp/` native/Xcode application boundary. Absence of `:iosApp` from `gradle projects` is expected and is not itself a violation.

No source code was changed during the clarification.

**Next valid action:** perform fresh BASE-ARCH-014-09 Architecture Verification against the clarified rule.


## 014-09 iOS Boundary Implementation — 2026-09-23

**State:** IMPLEMENTED AND VERIFIED — pending owner acceptance of the verification gate.

The project owner authorized correction of BASE-ARCH-014-09-F02. The native iOS/Xcode application boundary is now present under iosApp/.

Implemented:
- iosApp/CB-Partner.xcodeproj/project.pbxproj
- iosApp/CB-Partner.xcodeproj/project.xcworkspace/contents.xcworkspacedata
- iosApp/CBPartnerApp.swift
- removed iosApp/.gitkeep

The implementation did not add :iosApp to Gradle and did not add an iOS Gradle convention.

Fresh 014-09 repository verification confirms the clarified platform-boundary rule is now satisfied. No new architecture finding was identified.

The host is Windows, so Xcode/macOS build verification is not available in this environment. This limitation is recorded rather than hidden.

Next valid action: owner review/acceptance of BASE-ARCH-014-09. Do not start another architecture or implementation unit automatically.


## BASE-ARCH-014-T01 — Gradle Wrapper + Git/Build Workflow — 2026-09-23

**State:** IMPLEMENTING

Owner authorization was granted to add the Gradle Wrapper and to establish a complete operational Git/build command reference.

Completed in this step:
- `AI_START_HERE.md` now contains the standard repository Git/build lifecycle and Wrapper rule.
- `docs/project-management/11-TERMINAL-GIT-COMMANDS.md` now covers clone, inspect, fetch, pull, branch, commit, push, PR/merge flow, post-merge sync, Wrapper, build/test checks, safe undo, and Antigravity handoff.
- Gradle 9.7.1 is recorded as the current Wrapper target for the repository's AGP 9.3.0 configuration.

Remaining:
- Generate and verify the official Wrapper files using a compatible Gradle 9.7.1 executable.
- Run the baseline using `gradlew.bat` and record the result.

Current blocker: the user's Windows environment has no system `gradle`, and the repository currently has no Wrapper. No fake/custom Wrapper is permitted.


## BASE-ARCH-014-T01 Gradle Authority Correction — 2026-09-23

**Authoritative version:** Gradle 9.7.1.

The earlier 9.5.0 target has been superseded. The repository now has one dedicated Gradle source of truth at docs/build/GRADLE-SETUP-AND-WORKFLOW.md, and AI_START_HERE.md routes Gradle setup and command questions to it.

Documentation/routing is complete. The remaining T01 work is Windows provisioning, official Wrapper generation, Wrapper verification, and rerunning the baseline through gradlew.bat.
