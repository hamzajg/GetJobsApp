# Waves — job-board-core

Each wave IS an iteration: a time-boxed, independently shippable
increment — one branch, one PR, green tests, demoable outcome.
Do not start wave N+1 until wave N merges.

## Wave 1 — iteration 1

Scenarios proven by this wave:
- Load bundled job listings
- Malformed dataset degrades gracefully
- Search filters listings

| Item | Title | Size | Deps | Notes |
|------|-------|------|------|-------|
| US1 | As a job seeker, I want the app to ship sample job listings I can browse, so that the job board works without a backend | M | — | assets/jobs.json + Job/JobRepository/JobFilter + JUnit4 tests; android-json test dep |

## Wave 2 — iteration 2

Scenarios proven by this wave:
- Browse and filter the job list

| Item | Title | Size | Deps | Notes |
|------|-------|------|------|-------|
| US2 | As a job seeker, I want a searchable job list on the home screen, so that I can find relevant openings quickly | M | US1 | RecyclerView + adapter + item layout inside MainActivity Container; live filter; empty state |

## Wave 3 — iteration 3

Scenarios proven by this wave:
- Open job detail

| Item | Title | Size | Deps | Notes |
|------|-------|------|------|-------|
| US3 | As a job seeker, I want to open a job and read its full details, so that I can decide whether to apply | S | US2 | JobDetailActivity + layout + manifest; intent extras carry the Job |
