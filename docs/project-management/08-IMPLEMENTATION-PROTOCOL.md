# CB-Partner — Implementation Protocol

**Status:** PROPOSED

## Preconditions
Implementation requires an understood requirement, accepted required decisions, approved implementation plan, READY Trello card, correct branch, and known repository state.

## AI Preflight
Read AI Guidelines, AI Context, AI Rules, Current Status, Decision Log, relevant architecture/feature docs. Inspect source, tests, build configuration and Git status. Confirm the request matches the frozen plan.

## Implementation Loop
1. Establish baseline.
2. Implement the smallest coherent increment.
3. Compile/check frequently.
4. Add/update tests.
5. Inspect diff.
6. Continue only while still within scope.

## Forbidden Silent Changes
Do not silently add material dependencies, change module boundaries, redesign architecture, refactor unrelated areas, rename broad APIs, or weaken tests.

## Deviation Protocol
STOP → identify conflict → explain impact → propose minimum alternatives → obtain approval → update plan/ADR → continue.

## Failure Handling
Classify failures as current-change, pre-existing, environment, or unrelated. Do not hide failures by deleting tests or weakening verification.

## Completion Report
Report changed files, delivered behavior, tests/checks and results, platform builds verified, deviations, known limitations, docs updated, and Git state.

## Hard Stop
Stop when the frozen contract cannot be safely implemented without a material change.

**This document is PROPOSED until explicitly frozen.**
## One-Unit Rule
Implementation selects exactly one approved unit. Before coding announce the unit and intended files/scope. After coding inspect all changed files and the diff, run required verification, update records, commit/push through the approved Git flow, and report the final state.

## Antigravity Handoff
After a remote Git change, return exact local commands for the user's Antigravity environment to fetch/pull/switch to the correct state. Never assume the local environment has synchronized or that local verification has run.

## No Silent Git Actions
Do not silently merge, tag, force-push, reset, overwrite user work, or clean unrelated changes.
