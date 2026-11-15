# Spec: job-board-core

> Spec-driven development artifact. This file is the contract: implementation
> is done when every acceptance criterion maps to a scenario in
> `EXAMPLES.feature` and every scenario has a passing check.

## Problem

GetJobsApp promises a job board — browse listings, view details — but the
job-seeker flow dead-ends: `SignInActivity` routes seekers to
`MainActivity`, whose layout is an ad banner over an empty `Container`
FrameLayout (the original fragment-based job UI was never ported), and
`JobProviderMainActivity` is an empty stub. The only data path is a remote
PHP demo backend (`viaviweb.in`) that is unreliable and outside our
control. The app's core value — seeing jobs — does not exist.

## Users

- Job seekers signing in to browse openings, search, and read details.

## Scope (in)

- A bundled sample dataset `app/src/main/assets/jobs.json` (~10 realistic
  listings) using the field names already defined in `Constant` (`id`,
  `job_name`, `job_company_name`, `job_designation`, `job_salary`,
  `job_desc`, `job_qualification`, `job_skill`, `job_address`,
  `job_vacancy`, `job_date`, `job_image`) so it matches the existing API
  contract.
- Pure-Java domain layer (unit-testable on JVM):
  - `Job` model in `utils/` or a new `jobs/` package.
  - `JobRepository` — loads and parses the asset once, caches the list;
    parsing isolated so tests can inject JSON text without Android assets.
  - `JobFilter` — case-insensitive keyword match across title, company,
    skills, and location.
- Job list UI inside `MainActivity`'s existing `Container`: `RecyclerView`
  + item layout + search `EditText` + empty-state view. Tapping a row
  opens `JobDetailActivity`.
- `JobDetailActivity` + layout + manifest entry showing all job fields.
- JUnit4 unit tests for model parsing, repository degradation, and filter.
  `com.vaadin.external.google:android-json` added as **test-only**
  dependency so `org.json` works in local unit tests.

## Scope (out)

- Remote API integration (`viaviweb.in` PHP demo), real auth
  (register/login/forgot-password stubs), apply-job flow, provider-side
  job management, favorites/persistence, ads behaviour changes.
- Architecture refactors (MVVM/DI) — legacy Activity style is kept per
  AGENTS.md ("KISS: legacy sample — no architecture layers").

## Acceptance criteria

| # | Criterion | Scenario(s) |
|---|-----------|-------------|
| 1 | The repository loads the bundled dataset into Job objects with all contract fields populated | Load bundled job listings @w1 |
| 2 | A missing, empty, or malformed dataset yields an empty list — never a crash | Malformed dataset degrades gracefully @w1 |
| 3 | Keyword search matches title, company, skills, or location case-insensitively and returns only matches | Search filters listings @w1 |
| 4 | The job-seeker home screen lists jobs and filters them live as the user types | Browse and filter the job list @w2 |
| 5 | Tapping a listing opens a detail screen showing every job field | Open job detail @w3 |
| 6 | `sh gradlew clean test --no-daemon` and `assembleDebug` pass on JDK 17 | (build gate — verified per wave) |

## Decisions

- **Embedded sample dataset, not the remote API.** The `viaviweb.in` PHP
  demo is unreliable and outside our control; a bundled `assets/jobs.json`
  keeps the feature self-contained, demoable offline, and honest about
  being sample data. The `JobRepository` asset-loading seam is where a
  real API swaps in later — same contract fields.
- **Pure-Java domain, thin Activities.** Parsing/filtering live in
  framework-free classes so JUnit4 can cover them without Robolectric
  (not currently a dependency). UI stays in the legacy Activity style.
- **Job list lives in `MainActivity`'s `Container`** — the seeker home
  already routes there after sign-in; no new navigation graph needed.
  `JobProviderMainActivity` (employer side) is untouched.
