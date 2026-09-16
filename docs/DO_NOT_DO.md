# CB-Partner — Do Not Do

**Status:** PROPOSED

This is the project's explicit anti-pattern list.

## Process
- Do not restart the entire project because of one broken feature.
- Do not treat a clean rewrite as automatically better.
- Do not code before a meaningful plan is approved.
- Do not let discussion continue forever without a decision/freeze point.
- Do not use Trello as the permanent architecture source of truth.
- Do not let documentation become contradictory duplicate sources.

## AI
- Do not give AI unrestricted authority to redesign the project.
- Do not allow AI to silently change frozen decisions.
- Do not allow AI to silently expand scope.
- Do not accept generated code without review and validation.
- Do not let AI guess critical requirements.
- Do not overwrite unknown user changes.
- Do not merge unreviewed AI work.

## Architecture
- Do not introduce ViewModel.
- Do not bypass Store/MVI/UDF.
- Do not create a generic abstraction before a real use case exists.
- Do not add modules merely to make a diagram look cleaner.
- Do not add dependencies because they are trendy or popular.
- Do not put business/network/persistence logic in composables.
- Do not leak API DTOs throughout the application.
- Do not create platform abstractions that hide meaningful platform differences.
- Do not build infrastructure for hypothetical future requirements.

## Feature Work
- Do not implement the entire product in one task.
- Do not mix unrelated refactoring into a feature.
- Do not weaken acceptance criteria to make a task pass.
- Do not call a feature complete because it compiles.
- Do not skip relevant tests.

## Git
- Do not normally work directly on main.
- Do not force-push without explicit understanding and authorization.
- Do not use reset --hard as a first response to uncertainty.
- Do not commit unrelated changes together.

## Project Evolution
- Do not confuse a newly released library with a justified dependency.
- Do not reopen frozen decisions merely because another approach looks interesting.
- Do not turn every code-review suggestion into immediate work.
- Do not restart when migration or focused repair is practical.

**This document is PROPOSED until explicitly frozen.**
## Workflow Automation
- Do not treat CONTINUE as permission to code.
- Do not skip the router, Tracker or relevant module documents.
- Do not batch implementation units.
- Do not report DONE before required verification, documentation and Git states are complete.
- Do not omit the complete changed-file list after code changes.
- Do not omit Antigravity synchronization commands after material remote Git changes.
- Do not merge or tag silently.
- Do not automatically begin the next unit after a freeze.
