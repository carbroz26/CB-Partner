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

- Preserved the existing Bootstrap endpoint and known headers.
- Updated the configured Bootstrap base URL from `https://localhost:300` to the actual local backend port `https://localhost:3000`.
- This is a local environment/configuration correction only; the Bootstrap endpoint and request contract remain unchanged.
- Bootstrap coroutine cancellation is rethrown instead of being incorrectly converted into a transport failure.

## Runtime Diagnostics

Added explicit Bootstrap runtime diagnostics to the shared remote data source so Web browser verification can confirm the request lifecycle in the browser console. Diagnostics currently report:

- Bootstrap request URL.
- HTTP response status.
- HTTP failure status.
- Successful response parsing.
- Serialization failure.
- Transport failure and its message.

These logs are diagnostic only and do not change the Bootstrap contract or architecture.

## Runtime Verification

Web build and browser startup have now been verified successfully:

- `:webApp:wasmJsBrowserDevelopmentRun` starts the Kotlin/Wasm development server.
- Browser loads the generated `webApp.js` and Wasm assets.
- Splash renders.
- Bootstrap request is attempted.
- Initial request failed with `net::ERR_CONNECTION_REFUSED` because the configured local port was `300` while the local backend uses `3000`.
- The port correction above is now applied.
- Next verification is to rerun/reload the Web app with the backend running on `https://localhost:3000` and verify Bootstrap success/failure/retry behavior.

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
3. Bootstrap request succeeds against the local backend on port 3000.
4. Success state renders when the backend responds successfully.
5. Failure state renders when the request fails.
6. Retry starts a new Bootstrap request.
7. Browser CORS/TLS behavior is recorded without changing the backend contract.

## Freeze Criteria

The feature remains NOT FROZEN until build, tests, browser runtime verification, documentation review, and final architecture review are complete.
