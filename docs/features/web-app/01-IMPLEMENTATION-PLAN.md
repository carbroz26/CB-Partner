# WEB-APP-001 — IMPLEMENTATION PLAN

**Status:** PLAN_FROZEN

## Scope

Transform `desktopApp` into `webApp` and configure Kotlin/Wasm browser execution with Compose Multiplatform.

## Implementation

1. Rename the application boundary from `:desktopApp` to `:webApp`.
2. Replace the Desktop convention plugin with a Web application convention plugin.
3. Configure `wasmJs { browser(); binaries.executable() }`.
4. Add the Web entry point using `ComposeViewport`.
5. Add browser HTML/CSS viewport resources.
6. Reuse Data, Bootstrap, Koin, and Splash modules.
7. Add `wasmJs` to shared KMP library targets so the Web application can consume common modules.
8. Verify Ktor's existing default-engine configuration resolves for Wasm.
9. Preserve the known Bootstrap endpoint and headers; do not invent a Web backend contract.
10. Remove unused Desktop convention infrastructure.
11. Run Web compilation/distribution and existing regression tests.
12. Verify browser startup reaches Splash and Bootstrap.
13. Update implementation status and freeze after review.

## Completion Criteria

- `:webApp` is included.
- `:desktopApp` is absent.
- Wasm browser build succeeds.
- Web entry point renders.
- Splash renders.
- Bootstrap is invoked.
- Existing JVM tests remain green.
- No ViewModel or business architecture changes are introduced.
