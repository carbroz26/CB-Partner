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
- Corrected the local Bootstrap base URL to `http://localhost:3000`, matching the backend's actual plain HTTP server.
- The Bootstrap endpoint and request contract remain unchanged.
- Bootstrap coroutine cancellation is rethrown instead of being incorrectly converted into a transport failure.
- Added Kermit runtime diagnostics for request, HTTP response, parsing, serialization, and transport outcomes.

### Splash UI

- Removed the temporary `Bootstrap completed` diagnostic text from the successful Splash state.
- Replaced the placeholder Splash layout with a polished CarBroz Partner presentation built entirely with Compose Multiplatform.
- Added a CarBroz Partner brand header with teal visual identity.
- Added a custom Compose Canvas car-wash illustration with car, water/bubbles, and subtle decorative elements; no external image dependency was introduced.
- Added a light teal/white background treatment with responsive spacing.
- Added partner-focused messaging: premium car care and professional doorstep service.
- Added loading progress and preparation messaging while Bootstrap is running.
- Added a clear success presentation after Bootstrap completes.
- Preserved the existing failure message and Retry action on Bootstrap failure.
- Kept the Splash feature self-contained; no downstream navigation or `nextScreen` behavior has been introduced.

## Runtime Diagnostics

Bootstrap runtime diagnostics are emitted through the existing Kermit logging infrastructure with the `Bootstrap` tag. They report:

- Bootstrap request URL.
- HTTP response status.
- HTTP failure status.
- Successful response parsing.
- Serialization failure.
- Transport failure and its message.

These logs are diagnostic only and do not change the Bootstrap contract or architecture.

## Runtime Verification

Web build and browser startup have been verified successfully:

- `:webApp:wasmJsBrowserDevelopmentRun` starts the Kotlin/Wasm development server.
- Browser loads the generated `webApp.js` and Wasm assets.
- Splash composition is loaded.
- Bootstrap request is initiated from the browser.
- The previous `https://localhost:3000` request failed because the backend is a plain HTTP server.
- Backend research confirmed CORS is configured with `@fastify/cors`, supports the required preflight/custom headers, and the backend listens on plain `http://localhost:3000`.
- The frontend Bootstrap URL is now corrected to `http://localhost:3000`.
- The successful Splash state no longer renders the temporary Bootstrap-completed diagnostic message.
- The polished Splash UI implementation is now committed; final browser visual verification and compilation verification are pending.

## Verification Plan

Run locally:

```text
Backend: http://localhost:3000
Web:     http://localhost:8080 (or the next available development-server port)
```

Then verify in the browser:

1. Web application starts.
2. Polished Splash UI renders.
3. Browser sends Bootstrap request to `http://localhost:3000/api/v1/partner/config/bootstrap`.
4. Backend returns an HTTP response successfully.
5. Bootstrap response is parsed successfully.
6. Splash shows the intended success presentation for the current feature boundary.
7. Failure state renders when the request fails.
8. Retry starts a new Bootstrap request.
9. Kermit Bootstrap diagnostics confirm the request lifecycle.

After browser verification, run automated tests:

```text
:core:jvmTest
:domain:jvmTest
:data:jvmTest
:navigation:jvmTest
:feature:splash:jvmTest
:feature:dynamic:jvmTest
```

## Freeze Criteria

The feature remains NOT FROZEN until build, automated tests, browser runtime verification, documentation review, and final architecture review are complete.
