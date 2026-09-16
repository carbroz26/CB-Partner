# CB-Partner — Frontend API Integration Contract

**Status:** PROPOSED
**Scope:** Frontend repository only

## 1. Authority
The backend repository owns the authoritative API specification. This document defines frontend integration rules and records frontend-specific decisions.

## 2. Boundary
UI and Store code must not directly perform HTTP operations. Network access belongs behind the approved data/integration boundary.

## 3. Data Flow
A typical feature flow is:

UI Event → Store → Use Case / Application Logic → Repository → Data Source / API Client

and:

API Response → DTO → Mapper → Domain Model → Store State → UI.

The exact layers may be simplified when a real feature demonstrates that a layer has no useful responsibility.

## 4. DTO Rule
Transport DTOs represent the backend contract. They must not automatically become domain models. Mapping should isolate backend changes from the rest of the application.

## 5. Request/Response
For every integrated endpoint document:
- HTTP method
- path
- authentication
- request model
- response model
- error/status behavior
- pagination
- upload/download behavior
- required headers
- versioning assumptions

## 6. Errors
The frontend should translate transport failures into stable application/domain-facing error representations. UI should not need to understand raw HTTP implementation details.

## 7. Authentication
Authentication behavior must follow the backend contract and an accepted frontend security decision. Tokens/credentials must not be placed in UI state unnecessarily or logged.

## 8. Environments
Development, test/staging, and production endpoint configuration must be explicit. Secrets must not be committed to the repository.

## 9. Retries
Retry behavior must be deliberate. Do not automatically retry every failure, especially non-idempotent operations.

## 10. API Evolution
When backend changes affect frontend behavior, coordinate the contract change. Update this document only for frontend-relevant durable behavior; keep the backend specification in the backend repository.

## 11. Testing
Test serialization, mapping, repository behavior, error mapping, and important Store behavior without requiring live production services.

## 12. Current API Scope
The initial technical foundation may use one API endpoint as the first integration slice. The endpoint itself must be documented once its backend contract is available.

## 13. Not Yet Frozen
Client library, base URL strategy, authentication implementation, DTO naming, error taxonomy, retry policy, pagination conventions, and endpoint-specific contracts.

**This document is PROPOSED until explicitly frozen.**