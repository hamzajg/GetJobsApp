# Validation — job-board-core

Spec: `specs/job-board-core/SPEC.md` · Examples: `EXAMPLES.feature` ·
Waves: `WAVES.md` · Gates: `GATES.md`

## Traceability

| AC | Criterion | Scenario(s) | Wave(s) | Test evidence |
|----|-----------|-------------|---------|---------------|
| 1 | The repository loads the bundled dataset into Job objects with all contract fields populated | Load bundled job listings | @w1 | `jobs/JobRepositoryTest.java`, `jobs/JobFilterTest.java` |
| 2 | A missing, empty, or malformed dataset yields an empty list — never a crash | Malformed dataset degrades gracefully | @w1 | `jobs/JobRepositoryTest.java`, `jobs/JobFilterTest.java` |
| 3 | Keyword search matches title, company, skills, or location case-insensitively and returns only matches | Search filters listings | @w1 | `jobs/JobFilterTest.java` |
| 4 | The job-seeker home screen lists jobs and filters them live as the user types | Browse and filter the job list | @w2 | ~ weak token-match only (`JobFilterTest`, `ReferralApiTest` mention job/filter tokens — no UI-level test) |
| 5 | Tapping a listing opens a detail screen showing every job field | Open job detail | @w3 | — none |
| 6 | `sh gradlew clean test --no-daemon` and `assembleDebug` pass on JDK 17 | (build gate — verified per wave) | — | clean `test` + `assembleDebug` BUILD SUCCESSFUL locally on JDK 17 per wave; CI Android unit tests green on PRs #7, #8, #9 |

## Scenario coverage

- Scenarios: 5 total — 3 with strong automated evidence, 1 with weak
  token-match evidence only, 1 with no automated evidence.
- ✓ `Load bundled job listings` (@w1) — covered by `JobRepositoryTest`
  (dataset parsing, field population, contract field names).
- ✓ `Malformed dataset degrades gracefully` (@w1) — covered by
  `JobRepositoryTest` (missing/empty/malformed JSON → empty list, no
  throw).
- ✓ `Search filters listings` (@w1) — covered by `JobFilterTest`
  (case-insensitive match across title/company/skills/location,
  empty result on no match).
- ~ `Browse and filter the job list` (@w2) — **no automated UI evidence.**
  The RecyclerView/adapter/`EditText` wiring in `MainActivity` is JVM-
  untestable without Robolectric (not a dependency). The domain logic it
  delegates to (`JobRepository.search` → `JobFilter`) is covered by @w1
  tests; the UI itself is pending human gate **G1**.
- ✗ `Open job detail` (@w3) — **no automated evidence.**
  `JobDetailActivity` + manifest entry + intent-extra hand-off are
  verified by build only; pending human gate **G1**.

## Demo script (manual walkthrough — pending G1)

Gate G1 (on-device smoke test, human-owned) requires installing the debug
APK and walking the @w2/@w3 scenarios. Suggested walkthrough covering all
scenarios:

1. Build and install:
   ```bash
   sh gradlew assembleDebug --no-daemon   # JDK 17, SDK 34
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```
2. **Browse and filter the job list** (@w2): launch the app, sign in as a
   job seeker (sign-in is a stub — any credentials route to
   `MainActivity`). The home screen should show a search box above a
   `RecyclerView` listing every job from `assets/jobs.json` (~10
   listings). Type a keyword (e.g. a skill or city from the dataset):
   the list filters live as you type. Type a nonsense string: the
   empty-state message appears.
3. **Open job detail** (@w3): tap any job row → `JobDetailActivity`
   opens showing title, company, designation, salary, description,
   qualification, skills, address, vacancy count, and date.
4. **Load bundled job listings / Malformed dataset** (@w1): implicitly
   exercised on every launch — the list renders only if
   `JobRepository` parsed `assets/jobs.json` correctly. (Graceful-
   degradation path is unit-tested; not reproducible on-device without
   modifying the asset.)
5. Press back from detail → returns to the filtered list with search
   text preserved.

Record the result on gate G1 in `GATES.md`/`LOOP.md` (oracle-owned — do
not hand-edit `LOOP.md`; `ship_loop` records the waiver/evidence).

## Gaps

- **UI scenarios lack automated coverage (expected).** @w2 and @w3 are
  Activity/RecyclerView flows; `sh gradlew test` runs JVM-only unit tests
  and CI has no emulator. This is by design — GATES.md declares human
  gate **G1 — On-device smoke test** (blocking, owner: product-owner)
  precisely because of this. The feature is *not* fully validated until
  G1 is walked on a device/emulator.
- **`~` weak token-match on AC4** is a ship_validate heuristic artifact,
  not evidence: `ReferralApiTest`/`JobFilterTest` share tokens ("job",
  "filter") with the scenario but no test exercises the list UI.
- Sample dataset is bundled sample data (`assets/jobs.json`), not the
  remote `viaviweb.in` API — per SPEC.md decisions, out of scope.
