# CB-Partner — Frontend API Integration Contract

Status: PROPOSED
Scope: Frontend repository only.

Purpose: Defines how this frontend integrates with the backend. The backend repository remains authoritative for the actual API specification.

Principles: UI does not call HTTP directly; transport DTOs remain inside the data/integration boundary; domain models do not depend on transport DTOs; serialization is explicit and testable; authentication follows the agreed backend contract; API errors are mapped into appropriate frontend/domain representations.

For each endpoint eventually record/link: method, path, authentication, request, response, errors, pagination, uploads/downloads, and versioning.

Not yet frozen: client library, environments/base URLs, authentication, DTO conventions, error mapping, retry policy, and pagination.