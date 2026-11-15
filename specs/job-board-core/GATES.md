# Gates: job-board-core

> Preconditions for phase transitions. Each `## G<n> — <title>` block is a
> gate. `ship_loop` refuses to advance past a phase while a blocking gate is
> pending. Definitions live here; status and evidence live in `LOOP.md`.
>
> Gate phases: `spec` (before writing the spec), `plan` (before planning
> waves), `wave:<n>` (before starting wave n), `validate` (before validation),
> `finish` (before closing the feature).
>
> Gate types: decision | artifact | external | technical | quality | data.
> Resolver: `human` (needs a person to decide/provide something) or `agent`
> (the agent can satisfy it). Check: `manual`, `file:<repo-relative-path>`, or
> `command:<exact command>` (the agent runs it and records the output as
> evidence — the oracle never executes it).

## G1 — On-device smoke test
- Before: finish
- Type: quality
- Owner: product-owner
- Resolver: human
- Blocking: yes
- Check: manual
- Why: UI scenarios (list render, live filter, detail navigation) cannot be
  exercised by `sh gradlew test` — local unit tests are JVM-only and CI has
  no emulator. A human must install the debug APK on a device/emulator and
  walk EXAMPLES.feature @w2/@w3 before the feature is called done.
