# CB-Partner — Terminal, Git & Build Commands

Status: PROPOSED

Purpose: Safe operational reference for developer and AI-assisted work. Exact Gradle task names must be verified after the real module structure exists.

## Inspect
pwd
ls
git status
git branch
git remote -v
git log --oneline --decorate -10

## Start work
git switch main
git pull --ff-only origin main
git switch -c feature/<short-name>

## Save work
git add <files>
git status
git diff --cached
git commit -m "feat: <description>"
git push -u origin feature/<short-name>

## Synchronize
git fetch origin
git pull --ff-only

## Review
git diff main...HEAD
git diff --stat main...HEAD
git log --oneline main..HEAD

git diff --check

## Merge flow
Push branch → review diff → run checks → merge through approved GitHub workflow → switch main → git pull --ff-only origin main → git branch -d feature/<name>

## Safe undo
git restore <file>
git restore --staged <file>
git revert <commit>
git reflog

Avoid destructive reset and force-push until the exact consequence is understood.

## Gradle
./gradlew tasks
./gradlew tasks --all
./gradlew build
./gradlew check
./gradlew test
./gradlew clean
./gradlew --stop

Windows equivalents use gradlew.bat.

## Android
Verify actual tasks first with ./gradlew tasks --all. Typical task patterns may include :<module>:assembleDebug and :<module>:test. Do not invent module names.

## Desktop
Verify actual tasks first. A Compose Multiplatform desktop application commonly exposes a run task, but the final task name must be taken from the repository.

## iOS
Use the configured Gradle/KMP tasks plus the project's Xcode project/workspace. Verify schemes with xcodebuild -list. Build with the approved scheme/configuration. Simulator/device operations depend on final signing and target setup.

## AI safety
Before AI: git status.
After AI: git status; git diff --stat; git diff.
Before commit: git diff --check; git status.
After commit: git log --oneline -3; git status.

## Project-specific commands
To be filled after the actual KMP/Compose module structure is established.
## Mandatory Antigravity Handoff
The AI must return exact commands after material repository changes. Never give a generic command when the actual Git state is known.

### Post-Merge Main Sync
```bash
git fetch origin
git switch main
git pull --ff-only origin main
```

### Existing Module Branch
```bash
git fetch origin
git switch feature/<module>
git pull --ff-only origin feature/<module>
```

Before giving commands, confirm the target branch and whether the remote branch was pushed/merged. After synchronization verify `git status` and relevant build/test commands.

### Git Rules
main is the protected stable branch. Use feature/<module> for active module/feature work by default. Keep meaningful implementation units as focused commits. Tags mark meaningful milestones. Record branch, commit, PR and tag in Tracker/Module Status when applicable.
