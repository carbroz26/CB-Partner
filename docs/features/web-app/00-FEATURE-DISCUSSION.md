# WEB APP — FEATURE DISCUSSION

**Feature:** Web Application  
**Tracking ID:** WEB-APP-001  
**Status:** FROZEN

## Decision

Replace the existing Desktop application boundary with a Web application boundary.

The existing `desktopApp` directory/module is transformed into `webApp`; it is not retained as a supported application target.

## Frozen Decisions

- Web target: Kotlin/Wasm `wasmJs`.
- Browser application: Compose Multiplatform.
- `webApp` is the Web application boundary.
- Existing shared Clean Architecture remains unchanged.
- Existing Pure MVI + Store + UDF remains unchanged.
- ViewModel remains prohibited.
- Existing Splash + Bootstrap flow is reused.
- Android remains unchanged.
- iOS remains the native/Xcode boundary.
- Navigation 3 remains unchanged.
- Backend code is outside this frontend change.
- Existing Bootstrap API contract is not invented or silently changed.
- Browser runtime limitations such as CORS/TLS are treated as integration/environment concerns unless an explicit contract decision is made.

## Non-Goals

- Authentication.
- Login.
- Dynamic template rendering.
- Downstream navigation.
- Maintenance/update decisions.
- Backend changes.
- New business architecture.
