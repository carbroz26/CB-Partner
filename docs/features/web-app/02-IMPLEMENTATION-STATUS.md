# WEB APP — IMPLEMENTATION STATUS

**Feature:** Web Application  
**Tracking ID:** WEB-APP-001  
**Branch:** `feature/splash-config-bootstrap`  
**Discussion:** FROZEN  
**Implementation Plan:** PLAN_FROZEN  
**Implementation:** IN PROGRESS  
**Feature freeze:** NOT YET FROZEN

## Implemented

### Application Boundary

- Replaced Gradle `:desktopApp` inclusion with `:webApp`.
- Removed the Desktop application build file.
- Removed the Desktop convention plugin and registration.
- Added a dedicated Web application convention plugin.
- Configured Kotlin/Wasm `wasmJs` browser executable support.

### Shared KMP Targets

- Added `wasmJs` to the shared KMP convention target set so shared modules can be consumed by the Web application.

### Web Entry Point

Added:

`webApp/src/wasmJsMain/kotlin/com/carbroz/cbpartner/web/Main.kt`

The entry point:

- initializes Koin with existing network, bootstrap, and splash modules;
- creates the existing SplashStore;
- renders the existing SplashScreen;
- uses Compose Multiplatform `ComposeViewport`.

### Browser Resources

Added:

- `webApp/src/wasmJsMain/resources/index.html`
- `webApp/src/wasmJsMain/resources/styles.css`

The viewport is configured to fill the browser window.

### Bootstrap

The existing Bootstrap endpoint and known headers were preserved.

Additionally, Bootstrap coroutine cancellation is now rethrown instead of being incorrectly converted into a transport failure.

## Architecture Verification

The Web implementation does not introduce:

- ViewModel.
- New repository/use-case architecture.
- New navigation architecture.
- Backend changes.
- Invented Web API contracts.
- Duplicate Splash/Bootstrap implementations.

The Web application reuses the existing shared architecture.

## Verification Pending

Run locally:

```text
: webApp:wasmJsBrowserDevelopmentRun
: webApp:wasmJsBrowserDistribution

:core:jvmTest
:domain:jvmTest
:data:jvmTest
:navigation:jvmTest
:feature:splash:jvmTest
:feature:dynamic:jvmTest
```

Then verify in the browser:

1. Web application starts.
2. Splash renders.
3. Bootstrap request is attempted.
4. Success state renders when the backend responds successfully.
5. Failure state renders when the request fails.
6. Retry starts a new Bootstrap request.
7. Browser CORS/TLS behavior is recorded without changing the backend contract.

## Freeze Criteria

The feature remains NOT FROZEN until build, tests, browser runtime verification, documentation review, and final architecture review are complete.
