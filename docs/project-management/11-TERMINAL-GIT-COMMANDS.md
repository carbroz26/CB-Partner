# CB-Partner — Terminal, Git & Build Commands

Status: ACTIVE OPERATIONAL REFERENCE

Purpose: one exact operational reference for cloning, inspecting, branching, synchronizing, building, testing, committing, pushing, reviewing, merging, and returning to a synchronized local state.

## 1. Initial Clone
    git clone <repository-url>
    cd CB-Partner
    git remote -v
    git status
    git branch --show-current
    Get-ChildItem -Force -Name

Verify the Gradle Wrapper:
    Get-ChildItem -Name gradlew*
    Get-ChildItem -Force gradle\wrapper

Required Wrapper files:
    gradlew
    gradlew.bat
    gradle/wrapper/gradle-wrapper.jar
    gradle/wrapper/gradle-wrapper.properties

## 2. Before Any Work
    git status
    git fetch origin
    git branch -vv
    git log --oneline --decorate -10

Stop if unexpected local changes exist. Never discard user changes to make a clean tree.

## 3. Branch Strategy
    main ← development ← feature/<module>

Typical start:
    git fetch origin
    git switch development
    git pull --ff-only origin development
    git switch -c feature/<module>

Do not develop directly on main or development.

## 4. Continue Existing Branch
    git fetch origin
    git switch feature/<module>
    git pull --ff-only origin feature/<module>
    git status

Switch branches only when an authorized work item requires it.

## 5. Inspect Changes
    git status
    git diff --stat
    git diff
    git diff --check
    git log --oneline --decorate -10

Branch comparison:
    git diff development...HEAD
    git log --oneline development..HEAD

Use the integration base recorded by the Tracker.

## 6. Commit
    git status
    git diff --check
    git diff --stat
    git diff
    git add <files>
    git diff --cached
    git commit -m "<type>: <description>"
    git status

Commits must be focused and reviewable.

## 7. Push
    git branch --show-current
    git log --oneline -3
    git status
    git push -u origin feature/<module>

Later:
    git push

Never force-push without explicit authorization.

## 8. Fetch vs Pull
Fetch:
    git fetch origin

Safe fast-forward pull:
    git pull --ff-only origin development
    git pull --ff-only origin feature/<module>

Prefer explicit fetch + inspection before resolving divergence.

## 9. Pull Request / Merge Flow
feature/<module> → commit → push → Pull Request/review → merge to development → integration verification → release review → merge to main.
Do not locally merge merely for convenience when the approved workflow requires a Pull Request.

## 10. Post-Merge Development Sync
    git fetch origin
    git switch development
    git pull --ff-only origin development
    git status

Then run integration/build verification.

## 11. Post-Merge Main Sync
Only after an approved production merge:
    git fetch origin
    git switch main
    git pull --ff-only origin main
    git status

Then run release/integration verification.

## 12. Gradle Wrapper — Required
Use the Gradle Wrapper for normal builds. Gradle documents it as the recommended way to standardize Gradle versions across developers and CI.

Windows:
    .\gradlew.bat --version
    .\gradlew.bat projects

macOS/Linux:
    ./gradlew --version
    ./gradlew projects

Current CB-Partner target: Gradle 9.5.0. The repository uses AGP 9.3.0, for which Android Developers documents Gradle 9.5.0 as the minimum/default compatible version.

## 13. BASE-ARCH-014-T01 — Add Gradle Wrapper
Authorized scope: add the official Gradle Wrapper using Gradle 9.5.0.

When a compatible system Gradle 9.5.0 executable is available:
    gradle :wrapper --gradle-version 9.5.0 --distribution-type bin

Then verify:
    .\gradlew.bat --version
    .\gradlew.bat :wrapper --gradle-version 9.5.0
    .\gradlew.bat --version

Commit all generated Wrapper files. Do not hand-create or substitute the Wrapper JAR.

Current blocker: this Windows environment has no system gradle and the repository has no Wrapper yet. A compatible Gradle 9.5.0 executable must be provisioned before the official Wrapper can be generated.

## 14. Build Health Check
    .\gradlew.bat --version
    .\gradlew.bat projects
    .\gradlew.bat -p build-logic build

BASE-ARCH-014 shared JVM baseline:
    .\gradlew.bat :core:jvmTest :domain:jvmTest :data:jvmTest :navigation:jvmTest :feature:splash:jvmTest :feature:dynamic:jvmTest

    .\gradlew.bat :core:compileKotlinJvm :domain:compileKotlinJvm :data:compileKotlinJvm :navigation:compileKotlinJvm :feature:splash:compileKotlinJvm :feature:dynamic:compileKotlinJvm

Do not claim iOS/Xcode build success from Windows.

## 15. General Gradle Inspection
    .\gradlew.bat tasks
    .\gradlew.bat tasks --all
    .\gradlew.bat build
    .\gradlew.bat check
    .\gradlew.bat test

Run only tasks verified to exist in the actual repository.

## 16. Android
First inspect tasks:
    .\gradlew.bat tasks --all

Then run the exact task from the relevant implementation plan.

## 17. Desktop
First inspect tasks:
    .\gradlew.bat tasks --all

Then use the actual Compose Desktop task exposed by the repository.

## 18. iOS
iosApp/ is the native Xcode application boundary. There is intentionally no Gradle :iosApp module.
On macOS:
    xcodebuild -list -project iosApp/CB-Partner.xcodeproj

Use the approved Xcode scheme/configuration. Do not claim Xcode build verification from Windows.

## 19. Safe Undo
    git restore <file>
    git restore --staged <file>
    git revert <commit>
    git reflog

Do not use hard reset, force-push, or destructive branch deletion to resolve uncertainty without explicit authorization.

## 20. AI Safety Gate
Before AI work:
    git status
    git branch --show-current
    git diff --stat

After AI work:
    git status
    git diff --stat
    git diff --check
    git diff

Before commit:
    git diff --check
    git status

After commit:
    git log --oneline -3
    git status

## 21. Antigravity Handoff
Every material repository change must end with exact local synchronization and verification commands based on the actual final Git state.

Feature branch:
    git fetch origin
    git switch feature/<module>
    git pull --ff-only origin feature/<module>
    git status

Development after merge:
    git fetch origin
    git switch development
    git pull --ff-only origin development
    git status

Then run the applicable build/test commands.

## 22. Required Operational Order
CLONE → VERIFY REPOSITORY + WRAPPER → FETCH → SWITCH/CREATE AUTHORIZED BRANCH → PULL/UPDATE → IMPLEMENT → BUILD + TEST → DIFF + REVIEW → COMMIT → PUSH → PR/REVIEW → MERGE TO DEVELOPMENT → INTEGRATION VERIFY → RELEASE REVIEW → MERGE TO MAIN → FETCH + SYNC LOCAL → BUILD + TEST AGAIN.

This operational reference complements AI_START_HERE.md and the project operating system. Architecture decisions remain authoritative in the architecture documents.