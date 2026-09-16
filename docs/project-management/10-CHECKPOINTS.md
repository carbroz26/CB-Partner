# CB-Partner — Checkpoints

**Status:** PROPOSED

## Purpose
Checkpoints are recovery anchors. They make experimentation safe because a known-good project state is always recoverable.

## When to Create
Create a checkpoint after meaningful stable milestones, especially after project foundation, architecture validation, first vertical slice, and release-ready milestones.

## Record
- Checkpoint ID
- Date
- Git commit
- Milestone
- Completed work
- Verification performed
- Known limitations
- Documentation state
- Recovery notes if relevant

## Naming
CP-001, CP-002, CP-003, etc.

## Initial Milestones
CP-001 Project Constitution Frozen
CP-002 Architecture Frozen
CP-003 Build/Repository Foundation
CP-004 Core Technical Foundation
CP-005 First Vertical Slice
CP-006 First Release Candidate

These are placeholders, not commitments.

## Recovery
When something breaks: stop → inspect status/diff → identify last known-good checkpoint → classify failure → fix forward or revert deliberately → verify → create a new checkpoint when stable.

Never delete the project merely because a checkpoint is easier to recover from.

**This document is PROPOSED until explicitly frozen.**
## Checkpoint and Git State
A checkpoint records exact Git commit and, when applicable, branch/tag/PR state, plus Tracker/module status so a new AI session can recover without conversation history.
