# Splash + Bootstrap — Feature Discussion

**Feature:** `splash-bootstrap`
**Tracking ID:** `SPLASH-BOOTSTRAP-001`
**Discussion Status:** PARTIALLY FROZEN — EXTENDED DISCUSSION OPEN
**Current Implementation Status:** IN PROGRESS
**Branch:** `feature/splash-config-bootstrap`

## 1. Feature Status

### DONE / DECIDED FOR CURRENT SCOPE
- Splash is the initial visible screen.
- Splash shows a loader while Bootstrap is running.
- Bootstrap is called from the Splash startup flow.
- Current local Bootstrap base URL is `http://localhost:3000`.
- Current Bootstrap endpoint is `/api/v1/partner/config/bootstrap`.
- Known platform/app/build headers are defined below.
- The supplied successful Bootstrap response is the current known response contract.
- Frontend failure handling must cover transport, HTTP, API/application, serialization, and unknown failures.
- Invalid/incomplete successful data is treated as a Bootstrap failure rather than successful startup.
- Retry is available from the current failure state and retries Bootstrap.
- Splash UI is implemented as shared Compose Multiplatform UI in `commonMain`.
- Bootstrap diagnostics are available through the existing logging infrastructure.
- The current Bootstrap success boundary is intentionally not the final application-navigation boundary.

### PENDING / BACKEND-DEPENDENT
- Exact Bootstrap request method/body is not currently supplied by the backend. The frontend must isolate this unknown and must not invent it.
- Exact backend error-response payload/mapping is not currently supplied. The frontend failure boundary is defined, but backend-specific error-code mapping remains open until the backend contract is supplied.

These items remain external-contract dependencies and do not reopen the already-frozen base architecture.

## 2. Current Partial Freeze Boundary

The currently implemented and verified portion is:

```text
Application start
    ↓
Splash UI
    ↓
Bootstrap request
    ↓
Receive + validate Bootstrap response
    ├── Failure → error + Retry
    └── Success → Bootstrap result available
```

This portion is considered **partially frozen** as the current working Bootstrap foundation.

It is **not the final Bootstrap/startup feature freeze** because the startup decision model after Bootstrap success is still under discussion.

The following topics are intentionally reopened for discussion before the next implementation plan:

- Bootstrap persistence and offline startup
- Cache freshness and invalidation
- Cached response versus backend response selection
- Backend configuration/version changes
- Maintenance mode
- Required/optional update handling
- Authentication decision
- `nextScreen` resolution
- Complete downstream screen JSON/configuration for login, OTP, dashboard, and future screens
- Startup ordering and precedence between these decisions

No implementation plan for these topics is frozen yet.

## 3. Frozen Architecture Boundaries

This feature follows the already-frozen BASE-ARCH-008–013 and BASE-ARCH-017 boundaries. Those architecture decisions are not reopened here.

Required architectural direction:
- Kotlin Multiplatform
- Compose Multiplatform
- Clean Architecture
- Pure Store-based MVI/UDF
- No ViewModel
- Existing module boundaries
- Approved runtime/network infrastructure

The remaining Bootstrap discussion must fit these boundaries rather than introducing a separate architecture for caching, startup routing, or dynamic screens.

## 4. API Contract

### Base URL

```text
http://localhost:3000
```

This is local development configuration, not a permanent hard-coded production endpoint.

### Endpoint

```text
/api/v1/partner/config/bootstrap
```

Current local development URL:

```text
http://localhost:3000/api/v1/partner/config/bootstrap
```

### Headers

```text
X-CarBroz-Platform: ANDROID
X-CarBroz-App-Version: 1.0.0
X-CarBroz-Build-Number: 1
```

The platform value must ultimately be supplied by the actual platform boundary rather than being hard-coded into shared business logic.

### Request method/body

Unknown from the current backend information. Do not infer it from the returned `nextScreen.method`; that field belongs to the deferred downstream screen contract.

The implementation must isolate request construction so the exact backend method/body can be supplied later without architectural redesign.

## 5. Successful Response Contract

The known successful response is:

```json
{
  "status": 200,
  "code": "SUCCESS",
  "message": "Partner bootstrap completed",
  "data": {
    "config": {
      "version": "1",
      "maintenance": {
        "enabled": false,
        "title": null,
        "message": null
      },
      "update": {
        "required": false,
        "optional": false,
        "minimumVersion": "1.0.0",
        "latestVersion": "1.0.0",
        "storeUrl": null
      },
      "features": {
        "registrationEnabled": true,
        "individualPartnerEnabled": true,
        "organizationPartnerEnabled": true
      }
    },
    "startup": {
      "authenticated": false,
      "nextScreen": {
        "screenId": "partner_login",
        "templateId": "tpl_7K2M9Q",
        "templateType": "form_template",
        "endpoint": "/api/v1/partner/screen/auth_login",
        "method": "GET",
        "authentication": "NONE"
      }
    }
  },
  "traceId": "req-1"
}
```

The returned `nextScreen` is currently parsed/retained as response data but is not yet acted upon.

## 6. Error Contract Boundary

The backend has not yet supplied an exact error payload. Therefore the frontend implementation must not invent backend JSON fields or error codes.

The frontend must nevertheless model these failure categories:

- Transport/network failure
- HTTP failure
- API/application failure when an error response is received
- Serialization/malformed response failure
- Unknown/unexpected failure

When the exact backend error contract is supplied, backend-specific mapping can be added within the existing boundary.

## 7. Invalid / Incomplete Success

A response that is technically successful at the transport/API layer but lacks required Bootstrap data is not considered a valid Bootstrap success.

The frontend should convert such a response into a failure state and expose the current error/retry behavior. It must not manufacture missing values or navigate using incomplete data.

## 8. Current Splash Behavior

The current UI intentionally remains focused on Bootstrap:

- Show Splash.
- Show loader while Bootstrap is loading.
- On success, represent Bootstrap completion and keep the current scope boundary.
- On failure, show an appropriate error state and Retry action.

Maintenance/update/authentication/next-screen behavior is not yet part of the frozen implementation.

## 9. OPEN DISCUSSION — Bootstrap Persistence / Offline Startup

The Bootstrap response is startup configuration and may contain enough information to determine the next application flow. We now need to decide whether the application should persist the validated Bootstrap result locally.

### Questions to resolve

1. Should the latest valid Bootstrap response be persisted locally?
2. Which parts should be persisted:
   - complete Bootstrap JSON;
   - normalized domain data;
   - both raw response and normalized data;
   - metadata such as fetch time and backend config version?
3. What local persistence mechanism belongs to the shared KMP layer?
4. Should cached Bootstrap data survive application restart and device reboot?
5. What happens on first launch when there is no cached Bootstrap response and the backend is unreachable?
6. What happens when cached data exists but the backend is unreachable?
7. Is stale cached data allowed to start the application?
8. Are some decisions, such as maintenance or required update, allowed to come only from a fresh backend response?

### Important boundary

Persisting Bootstrap does not automatically mean that the complete application is offline-capable. If Bootstrap points to a downstream screen whose JSON, images, templates, or other resources are not cached, the application cannot assume that screen is available offline.

The offline strategy therefore needs to distinguish:

```text
Cached Bootstrap configuration
        ≠
Complete cached application flow
```

This distinction must be resolved before implementation.

## 10. OPEN DISCUSSION — Cached Response vs Backend Response

A central startup decision is how to choose between cached Bootstrap data and a newly fetched backend response.

The desired behavior is to avoid unnecessary startup network dependency while still allowing backend configuration changes to take effect.

Possible policy dimensions to discuss:

### A. Always network when online

```text
Start
 ↓
Try backend
 ├── success → use backend + update cache
 └── failure → use valid cache if policy permits
```

Advantages/disadvantages need to be evaluated against startup speed, freshness, offline support, and backend availability.

### B. Cache-first with freshness window

```text
Start
 ↓
Read cache
 ↓
Cache fresh enough?
 ├── yes → use cache
 └── no → request backend
```

This requires a clearly defined freshness policy.

### C. Cache-first with background revalidation

```text
Start
 ↓
Use cache immediately
 ↓
Refresh backend in background
 ↓
If configuration changed → apply the new startup decision
```

This requires careful handling of screen transitions so a configuration change cannot unexpectedly interrupt an active flow.

### D. Version/revision comparison

The backend may provide a configuration revision/version that can be compared with locally persisted metadata.

However, the client cannot know that the backend version changed without some communication with the backend unless another trusted invalidation mechanism exists.

Therefore the discussion must clarify whether the backend will provide:

- configuration version/revision;
- minimum supported app version;
- cache TTL/max-age;
- explicit invalidation signal;
- ETag/conditional-request support;
- another startup validation mechanism.

No one of these mechanisms is selected yet.

## 11. OPEN DISCUSSION — Cache Metadata / Invalidation

If Bootstrap is persisted, the cache should not be treated as an unqualified permanent source of truth.

Potential metadata that may need consideration:

```text
cachedBootstrap
cachedAt
configVersion / revision
appVersion
platform
schemaVersion
```

Potential invalidation causes:

- local cache expiration;
- application/schema incompatibility;
- backend configuration revision change;
- app version change;
- platform change;
- explicit backend invalidation;
- corrupted or incomplete persisted data;
- security-sensitive configuration requiring fresh validation.

These are discussion items only. The exact metadata and invalidation rules must be decided before implementation.

## 12. OPEN DISCUSSION — Maintenance Mode

The Bootstrap response already contains:

```json
"maintenance": {
  "enabled": false,
  "title": null,
  "message": null
}
```

We need to decide:

- Does maintenance always block application startup?
- Does maintenance override cached Bootstrap data?
- Can a cached non-maintenance response be used when the backend is currently unavailable?
- If the backend previously reported maintenance but the current backend cannot be reached, which state is authoritative?
- Is maintenance global or platform/app-version specific?
- Can authenticated users receive a different maintenance behavior?
- What UI is required for maintenance mode?
- Can retry re-check maintenance without restarting the app?

The precedence between fresh maintenance state and cached configuration must be explicitly defined.

## 13. OPEN DISCUSSION — Required / Optional Update

The Bootstrap response already contains:

```json
"update": {
  "required": false,
  "optional": false,
  "minimumVersion": "1.0.0",
  "latestVersion": "1.0.0",
  "storeUrl": null
}
```

We need to decide:

- What exactly constitutes a required update?
- Does `required=true` always block the application?
- Which version is compared: installed app version, build number, or both?
- Does required update override cached Bootstrap data?
- Can optional update be dismissed?
- How long is an optional-update dismissal remembered?
- Is the store URL platform-specific?
- What happens when update information is stale and the backend cannot be reached?
- What happens if the minimum version is greater than the installed version but the cached response says update is not required?

The final precedence between required update, maintenance, cache, and normal startup must be decided as one startup policy rather than as independent UI conditions.

## 14. OPEN DISCUSSION — Authentication Decision

Bootstrap currently returns:

```json
"authenticated": false
```

and a `nextScreen` descriptor.

The application needs a defined startup decision model for at least:

```text
authenticated = false
    → authentication flow

authenticated = true
    → authenticated/startup flow
```

Questions:

- How is authentication state determined locally?
- Does the Bootstrap response remain authoritative?
- What happens if the local session says authenticated but Bootstrap says unauthenticated?
- What happens if Bootstrap cannot be reached but a valid local session exists?
- Which credentials/session data are allowed to be used offline?
- Does authentication expiry require a fresh backend check?

No authentication implementation should be added to the current Splash scope until these rules are decided.

## 15. OPEN DISCUSSION — `nextScreen` and Complete Screen JSON

The current Bootstrap response identifies a next screen:

```json
"nextScreen": {
  "screenId": "partner_login",
  "templateId": "tpl_7K2M9Q",
  "templateType": "form_template",
  "endpoint": "/api/v1/partner/screen/auth_login",
  "method": "GET",
  "authentication": "NONE"
}
```

This descriptor is not itself the complete screen definition.

The later architecture needs to define the complete flow:

```text
Bootstrap
    ↓
nextScreen descriptor
    ↓
screen/config API
    ↓
complete JSON response
    ↓
template/registry resolution
    ↓
Compose UI
```

The downstream JSON may describe screens such as:

- Login
- OTP
- Partner registration
- Dashboard
- Other dynamic partner flows

The discussion must determine:

1. Which screen data belongs in Bootstrap versus a downstream screen API?
2. Should Bootstrap ever contain the complete first-screen JSON?
3. Should the frontend cache downstream screen JSON separately from Bootstrap?
4. Can a cached screen JSON be rendered without a network connection?
5. How are template/schema versions handled?
6. How are screen JSON changes invalidated?
7. How are incompatible templates handled safely?
8. How does navigation remain within the existing Pure Store/MVI/UDF architecture?

The existing deferred dynamic architecture remains the target direction; this discussion only defines the startup boundary required to reach it safely.

## 16. OPEN DISCUSSION — Startup Decision Precedence

The above topics cannot be implemented independently because their order affects application behavior.

A final startup decision tree needs to be defined, conceptually similar to:

```text
Application start
    ↓
Read local state/cache
    ↓
Determine network availability / backend reachability
    ↓
Determine whether fresh Bootstrap is required
    ↓
Bootstrap result or permitted cached result
    ↓
Required update?
    ↓
Maintenance?
    ↓
Authentication/session decision
    ↓
Resolve nextScreen
    ↓
Load/cache complete screen JSON
    ↓
Render next flow
```

The exact order is **NOT DECIDED** yet.

In particular, we must explicitly decide precedence for conflicts such as:

- required update vs maintenance;
- maintenance vs cached normal configuration;
- fresh Bootstrap vs cached Bootstrap;
- authenticated local session vs Bootstrap `authenticated=false`;
- stale cache vs backend unavailable;
- cached screen JSON vs changed backend screen JSON;
- offline mode vs authentication requirements.

These precedence rules should become a single documented startup policy instead of being scattered across individual features.

## 17. OPEN DISCUSSION — Offline Capability Boundary

The intended direction is that the application should be able to make useful startup decisions from persisted data when the backend is temporarily unavailable.

However, offline capability must be explicitly classified:

### Bootstrap offline capability

Can the application load the last known valid Bootstrap configuration without network access?

### Screen offline capability

Can the application load a previously cached login/OTP/dashboard screen definition without network access?

### Business-operation offline capability

Can the user actually perform business operations offline, or does offline support only cover startup/configuration rendering?

These are separate capabilities and must not be conflated.

## 18. Data Safety / Cache Integrity Discussion

Persisted configuration is application control data and should be treated as untrusted persisted state from the perspective of schema compatibility.

The implementation discussion must cover:

- schema/version compatibility;
- malformed/corrupted persisted JSON;
- safe cache deletion and replacement;
- atomic update of cached configuration;
- migration when the local cache schema changes;
- avoiding partially written configuration;
- handling incompatible cached templates;
- avoiding permanent startup loops caused by invalid cache.

No persistence technology or serialization format is frozen here yet beyond the existing KMP architecture and serialization direction.

## 19. Current Handoff Boundary

The currently partially frozen handoff is:

```text
Splash
  → Bootstrap request
  → validated Bootstrap result
```

The future complete handoff must eventually become:

```text
Splash
  → Bootstrap/cache decision
  → startup policy
  → maintenance/update/auth decision
  → nextScreen resolution
  → downstream screen JSON
  → dynamic screen/template rendering
```

That larger flow is **not yet frozen**.

## 20. Discussion Decisions Required Before Planning

Before creating the next implementation plan, the following decisions must be explicitly reviewed and frozen:

- [ ] Bootstrap persistence model
- [ ] Cache storage location/mechanism within KMP boundaries
- [ ] Cache metadata
- [ ] Cache freshness/TTL policy
- [ ] Cache invalidation/versioning policy
- [ ] Online startup policy
- [ ] Offline startup policy
- [ ] Cached-vs-backend precedence
- [ ] Maintenance precedence
- [ ] Required-update precedence
- [ ] Optional-update behavior
- [ ] Authentication startup decision
- [ ] `nextScreen` resolution
- [ ] Downstream screen JSON contract boundary
- [ ] Screen JSON caching strategy
- [ ] Template/schema versioning
- [ ] Startup decision precedence
- [ ] Offline capability boundary
- [ ] Cache integrity/recovery behavior

Only after these decisions are frozen should the implementation plan be created.

## 21. Feature Documentation

The feature documentation remains:

```text
docs/features/splash-bootstrap/
├── 00-FEATURE-DISCUSSION.md
├── 01-IMPLEMENTATION-PLAN.md
└── 02-IMPLEMENTATION-STATUS.md
```

`00-FEATURE-DISCUSSION.md` records the current contract and discussion decisions.
`01-IMPLEMENTATION-PLAN.md` will be updated/created only after the new discussion is frozen.
`02-IMPLEMENTATION-STATUS.md` records implementation, testing, review and final freeze status.

## 22. Current Discussion State

The initial Splash + Bootstrap foundation is **PARTIALLY FROZEN**.

The extended startup/configuration discussion is now **OPEN**.

No new persistence, offline, maintenance, update, authentication, navigation, or dynamic-screen implementation should be started from this discussion document until the corresponding decisions are reviewed and frozen.

The next workflow step is:

**DISCUSS → RESEARCH where required → DECIDE → FREEZE DISCUSSION → CREATE IMPLEMENTATION PLAN**
