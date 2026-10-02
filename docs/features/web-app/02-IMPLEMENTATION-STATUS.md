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
- Splash UI remains in `commonMain` and uses Compose Multiplatform APIs only; no Android-specific UI code was introduced.

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

## Splash UI

The Splash presentation is being refactored to a proper shared Compose UI implementation rather than a large Canvas-based screen.

### Resource-based UI

Added the Compose Multiplatform resource dependency to `feature:splash` and moved user-facing/static presentation content into `commonMain/composeResources`:

- `values/strings.xml` for all Splash copy.
- `drawable/splash_car_wash.xml` for the shared vector car-wash illustration.

The illustration uses the Compose Multiplatform resource system with an Android XML vector drawable so the same resource remains usable across Android, iOS, and Web. No Android view/widget APIs are used by the Splash screen.

### UI Structure

The new Splash presentation separates responsibilities into small composables:

- `BrandHeader` — CarBroz Partner identity.
- Main responsive presentation — illustration, headline, and service message.
- `SplashStatus` — loading, success, and failure states.
- `SplashDecorations` — non-interactive background treatment.
- `CarBrozSplashTheme` — centralized Splash color scheme/design tokens, keeping color values out of the screen implementation.

The screen remains driven by the existing `SplashStore` state and intents. Retry continues to dispatch `RetryBootstrap`; no business behavior or navigation was added.

### Design Direction

The Splash UI follows the supplied reference direction without copying it literally:

- white/light surface with soft teal accents;
- CarBroz teal brand identity;
- clean partner-focused typography using shared Material 3 typography;
- car-wash illustration and subtle water/bubble decoration;
- responsive sizing for narrow and large windows;
- clear loading, success, and failure presentation.

No UI copy, illustration data, or brand color values are embedded directly inside the main `SplashScreen` composable.

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

Web development-server startup has previously been verified successfully:

- `:webApp:wasmJsBrowserDevelopmentRun` starts the Kotlin/Wasm development server.
- Browser loads the generated `webApp.js` and Wasm assets.
- Splash composition is loaded.
- Bootstrap request is initiated from the browser.
- The previous `https://localhost:3000` request failed because the backend is a plain HTTP server.
- Backend research confirmed CORS is configured with `@fastify/cors`, supports the required preflight/custom headers, and the backend listens on plain `http://localhost:3000`.
- The frontend Bootstrap URL is corrected to `http://localhost:3000`.
- The successful Splash state no longer renders the temporary Bootstrap-completed diagnostic message.

### Current Verification State

- The previous Canvas-based Splash implementation exposed a Wasm compilation error due to an invalid `Stroke` import.
- That Canvas implementation is being removed rather than patched in place.
- The replacement uses shared Compose resource-backed assets and Material 3 components.
- Compilation and browser visual verification of the replacement are still pending.

## Verification Plan

Run locally:

```text
Backend: http://localhost:3000
Web:     http://localhost:8080 (or the next available development-server port)
```

Then verify in the browser:

1. Web application starts.
2. Resource-backed Splash UI renders.
3. Browser sends Bootstrap request to `http://localhost:3000/api/v1/partner/config/bootstrap`.
4. Backend returns an HTTP response successfully.
5. Bootstrap response is parsed successfully.
6. Splash shows the intended success presentation.
7. Failure state renders when the request fails.
8. Retry starts a new Bootstrap request.
9. Kermit Bootstrap diagnostics confirm the request lifecycle.
10. The same `commonMain` Splash UI remains platform-neutral for Android and iOS.

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
