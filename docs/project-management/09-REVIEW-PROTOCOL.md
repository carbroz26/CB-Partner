# CB-Partner — Review Protocol

Status: PROPOSED

Review against the frozen implementation contract and project rules.

Review order: scope, correctness, architecture, Store/MVI/UDF, KMP/platform compatibility, lifecycle/concurrency/error handling, testing, maintainability, security/privacy, documentation.

MVI/UDF checks: Store owns agreed state flow; UI renders state and emits events; business logic stays outside composables; events flow inward and state flows outward; side effects are controlled/testable; no ViewModel.

Findings: BLOCKER, REQUIRED, OPTIONAL, FUTURE.

Review must not become uncontrolled redesign. Do not automatically implement findings.