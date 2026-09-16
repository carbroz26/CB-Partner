# CB-Partner — Do Not Do

Status: PROPOSED

Project: Do not restart because of localized problems; do not throw away working code; do not allow endless architecture discussion without a freeze point; do not use Trello as architecture source of truth.

AI: do not let AI autonomously redesign architecture, expand a frozen task, add dependencies simply because they are popular, guess critical requirements, modify unrelated files, or merge unreviewed AI-generated code.

Architecture: do not introduce ViewModel; do not bypass Store/MVI/UDF; do not create generic abstractions without demonstrated need; do not build speculative infrastructure; do not put business logic in Compose UI; do not leak transport DTOs through every layer; do not silently change accepted decisions.

Development/Git: do not mix unrelated refactoring with feature work; do not mark complete because it compiles only; do not skip relevant tests; do not commit unrelated changes together; do not force-push or use destructive reset without understanding the impact; do not normally work directly on main.