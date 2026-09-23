# CB-Partner — Gradle Setup and Workflow

**Status:** AUTHORITATIVE  
**Scope:** Frontend repository  
**Last Updated:** 2026-09-23

## 1. Purpose

This is the single authoritative operational document for Gradle in CB-Partner.

It defines:
- selected Gradle version;
- system Gradle bootstrap/update setup;
- Gradle Wrapper setup;
- Windows environment configuration;
- normal Gradle commands;
- CB-Partner build/test commands;
- Android/Desktop/iOS operational commands;
- troubleshooting;
- version upgrade procedure;
- Gradle version history.

Do not create competing Gradle setup instructions in other project-management documents.

## 2. Authoritative Version

**CB-Partner Gradle version: 9.7.1**

| Item | Value |
|---|---|
| Selected Gradle | **9.7.1** |
| Wrapper target | **9.7.1** |
| Windows normal build | `.gradlew.bat` |
| macOS/Linux normal build | `./gradlew` |
| System Gradle | Bootstrap/update only |
| Project build authority | Gradle Wrapper |

### Version rationale

Gradle 9.7.1 is the selected CB-Partner baseline because it is the Gradle version previously used by this project and is now explicitly restored as the project tooling baseline.

Gradle 9.5.0 was previously documented during AGP compatibility research. It is **superseded** and is not the selected CB-Partner version.

The final 9.7.1 baseline remains subject to execution verification against the repository's actual Kotlin, AGP, Compose, JDK, Android SDK, and build-logic configuration. Do not claim compatibility merely from documentation until the project build is run.

## 3. Core Rule

After the Wrapper exists:

```text
System Gradle
    ↓
bootstrap / generate / update Wrapper only

Gradle Wrapper 9.7.1
    ↓
all normal CB-Partner builds, tests, checks and CI
```

Do not use a different system Gradle for ordinary project builds.

Do not hand-create, replace, or fake the Wrapper JAR.

## 4. Required Repository Wrapper Files

A completed Wrapper setup must contain:

```text
gradlew
gradlew.bat
gradle/
└── wrapper/
    ├── gradle-wrapper.jar
    └── gradle-wrapper.properties
```

The Wrapper files must be committed to Git.

The Wrapper is not considered implemented until:
1. the files exist;
2. `gradlew.bat --version` reports Gradle 9.7.1;
3. `gradlew.bat projects` succeeds;
4. the approved BASE-ARCH-014 baseline is rerun successfully;
5. the result is recorded in project status/tracker documentation.

## 5. Windows — Install System Gradle for Bootstrap

System Gradle is only required while generating or updating the Wrapper.

Recommended project bootstrap location:

```powershell
C:\Gradle\gradle-9.7.1
```

After installing/extracting Gradle 9.7.1:

### Current PowerShell session

```powershell
$env:GRADLE_HOME = "C:\Gradle\gradle-9.7.1"
$env:Path = "$env:GRADLE_HOME\bin;$env:Path"
gradle --version
```

Expected version:

```text
Gradle 9.7.1
```

### Verify Java

```powershell
java -version
```

Use the JDK version required by the repository's current AGP/Kotlin configuration. Do not silently change the project's JDK requirement while setting up Gradle.

### Persistent Windows environment variables

For a persistent user-level setup:

```powershell
[Environment]::SetEnvironmentVariable("GRADLE_HOME", "C:\Gradle\gradle-9.7.1", "User")
```

Add the Gradle bin directory to the user PATH through Windows Environment Variables, or update it using the approved local workstation procedure.

After changing persistent variables, open a new PowerShell session and verify:

```powershell
$env:GRADLE_HOME
gradle --version
```

Do not create duplicate Gradle entries for multiple versions without documenting which one is active.

## 6. Generate the Official Wrapper

From the repository root, after system Gradle 9.7.1 is available:

```powershell
gradle :wrapper --gradle-version 9.7.1 --distribution-type bin
```

Then verify:

```powershell
.\gradlew.bat --version
```

The reported Gradle version must be:

```text
9.7.1
```

Then use the Wrapper for all remaining project commands.

## 7. Wrapper Verification

```powershell
.\gradlew.bat --version
.\gradlew.bat projects
```

Inspect the generated files:

```powershell
Get-ChildItem -Force -Name gradlew*
Get-ChildItem -Force -Name gradle\wrapper
git status --short
```

Before committing:

```powershell
git diff --check
git diff --stat
git diff
```

## 8. CB-Partner Build Health Baseline

Run from repository root.

### Project hierarchy

```powershell
.\gradlew.bat projects
```

### Build logic

```powershell
.\gradlew.bat -p build-logic build
```

### Shared JVM tests

```powershell
.\gradlew.bat :core:jvmTest :domain:jvmTest :data:jvmTest :navigation:jvmTest :feature:splash:jvmTest :feature:dynamic:jvmTest
```

### Shared JVM compilation

```powershell
.\gradlew.bat :core:compileKotlinJvm :domain:compileKotlinJvm :data:compileKotlinJvm :navigation:compileKotlinJvm :feature:splash:compileKotlinJvm :feature:dynamic:compileKotlinJvm
```

These are the BASE-ARCH-014 shared baseline commands unless the active implementation plan explicitly requires additional verification.

## 9. General Gradle Commands

### Version

```powershell
.\gradlew.bat --version
```

### Available tasks

```powershell
.\gradlew.bat tasks
.\gradlew.bat tasks --all
```

### Projects

```powershell
.\gradlew.bat projects
```

### Build

```powershell
.\gradlew.bat build
```

### Check

```powershell
.\gradlew.bat check
```

### Tests

```powershell
.\gradlew.bat test
```

### Clean

Use only when justified:

```powershell
.\gradlew.bat clean
```

Do not use `clean` as a routine fix for an unexplained failure. Diagnose the failure first.

## 10. Useful Gradle Diagnostics

```powershell
.\gradlew.bat projects
.\gradlew.bat tasks --all
.\gradlew.bat --scan
```

Use the exact task exposed by the repository rather than guessing task names.

For a specific task:

```powershell
.\gradlew.bat <task>
```

For more logging when diagnosing a failure:

```powershell
.\gradlew.bat <task> --info
.\gradlew.bat <task> --stacktrace
```

Use `--debug` only when the additional logging is actually required.

## 11. Android Commands

First inspect available tasks:

```powershell
.\gradlew.bat tasks --all
```

Then run the exact Android task required by the active implementation plan.

Do not invent task names.

Android SDK, compileSdk, emulator/device, and signing configuration are separate concerns from Gradle version selection.

## 12. Desktop Commands

Inspect the available Compose Desktop tasks:

```powershell
.\gradlew.bat tasks --all
```

Run the exact task exposed by the repository and required by the active plan.

Do not assume a task name before inspecting the actual project.

## 13. iOS Commands

The iOS application boundary is:

```text
iosApp/
└── CB-Partner.xcodeproj/
```

There is intentionally **no** `:iosApp` Gradle module.

Gradle is used for shared KMP code. Native iOS application build verification is performed through Xcode on macOS.

On macOS:

```bash
xcodebuild -list -project iosApp/CB-Partner.xcodeproj
```

Use the approved Xcode scheme/configuration from the iOS implementation plan.

A Windows Gradle run must never be described as successful iOS/Xcode build verification.

## 14. Git Commands Around Gradle Changes

Before setup:

```powershell
git status
git branch --show-current
git diff --stat
```

After Wrapper generation:

```powershell
git status
git diff --stat
git diff --check
git diff
```

Before commit:

```powershell
git diff --check
git status
git diff --cached
```

After commit:

```powershell
git log --oneline -3
git status
```

## 15. Gradle Version Upgrade Procedure

A Gradle version change is a repository tooling decision, not an ad-hoc local setup change.

Required order:

1. Identify why the version must change.
2. Inspect Kotlin, AGP, Compose, JDK and Gradle compatibility requirements.
3. Record the proposed version in the Decision Log.
4. Obtain explicit owner approval when the selected project baseline changes.
5. Update this document first.
6. Update project-management routing documents.
7. Provision the selected system Gradle only for Wrapper generation/update.
8. Run the official Wrapper task.
9. Verify the Wrapper reports the selected version.
10. Run `projects`, build-logic build, tests, compilation and the implementation-specific verification.
11. Record actual results.
12. Review the diff.
13. Commit the focused tooling change.
14. Push through the approved branch/PR workflow.

Never change the project Gradle version only because a local machine happens to have another version installed.

## 16. Troubleshooting

### `gradle` is not recognized

Check:

```powershell
$env:GRADLE_HOME
$env:Path
java -version
gradle --version
```

If necessary, configure the current session:

```powershell
$env:GRADLE_HOME = "C:\Gradle\gradle-9.7.1"
$env:Path = "$env:GRADLE_HOME\bin;$env:Path"
gradle --version
```

### `gradlew.bat` is missing

The repository does not yet have the official Wrapper. Provision system Gradle 9.7.1 and run:

```powershell
gradle :wrapper --gradle-version 9.7.1 --distribution-type bin
```

Do not create a fake Wrapper JAR.

### Wrapper reports the wrong version

Inspect:

```powershell
.\gradlew.bat --version
Get-Content gradle\wrapper\gradle-wrapper.properties
```

Do not proceed with normal project work until the Wrapper version matches the authoritative project version.

### Build fails after a Gradle version change

Do not immediately downgrade or add compatibility flags.

Capture:

```powershell
.\gradlew.bat <task> --stacktrace
.\gradlew.bat <task> --info
```

Then compare the failure with the project's frozen dependency/tooling decisions.

### iOS simulator target is disabled on Windows

This is expected for iOS simulator testing because the simulator toolchain requires macOS.

Do not add `kotlin.native.ignoreDisabledTargets=true` merely to hide the warning.

## 17. Version History

| Date | Version | State | Notes |
|---|---|---|---|
| Before 2026-09-23 | 9.7.1 | Historical project usage | Existing project baseline referenced by owner |
| 2026-09-23 | 9.5.0 | SUPERSEDED | Temporary compatibility-based proposal during Wrapper research |
| 2026-09-23 | **9.7.1** | **AUTHORITATIVE** | Owner-selected CB-Partner baseline; Wrapper target |

## 18. Current BASE-ARCH-014-T01 State

**Documentation:** implemented.  
**Selected version:** Gradle 9.7.1.  
**Wrapper:** pending generation on the user's Windows environment.  
**Build verification with Wrapper:** pending.  
**Fake/custom Wrapper:** forbidden.

Next operational step:

```powershell
$env:GRADLE_HOME = "C:\Gradle\gradle-9.7.1"
$env:Path = "$env:GRADLE_HOME\bin;$env:Path"
gradle --version
gradle :wrapper --gradle-version 9.7.1 --distribution-type bin
.\gradlew.bat --version
```

Then run the BASE-ARCH-014 build/test baseline and record the actual results before marking T01 complete.

## 19. Related Documents

- `AI_START_HERE.md` — AI routing and Gradle authority.
- `docs/project-management/11-TERMINAL-GIT-COMMANDS.md` — concise operational Git/terminal lifecycle.
- `docs/project-management/03-DECISION-LOG.md` — durable tooling decisions.
- `docs/project-management/04-CURRENT-STATUS.md` — current project state.
- `docs/project-management/12-PROJECT-TRACKER.md` — execution ledger.
- `docs/architecture/BASE-ARCH-014-IMPLEMENTATION-PLAN.md` — frozen BASE-ARCH-014 implementation contract.
