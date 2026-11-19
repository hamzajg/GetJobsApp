# Validation — job-board-core

## Traceability

| AC | Criterion | Scenarios | Wave(s) | Test evidence |
|----|-----------|-----------|---------|---------------|
| 1 | The repository loads the bundled dataset into Job objects… | Load bundled job listings, Malformed dataset degrades gracefully, Search filters listings | 1 | JobFilterTest.java, JobRepositoryTest.java |
| 2 | A missing, empty, or malformed dataset yields an empty list… | Load bundled job listings, Malformed dataset degrades gracefully, Search filters listings | 1 | JobFilterTest.java, JobRepositoryTest.java |
| 3 | Keyword search matches title, company, skills, or location… | Load bundled job listings, Malformed dataset degrades gracefully, Search filters listings | 1 | JobFilterTest.java, JobRepositoryTest.java |
| 4 | The job-seeker home screen lists jobs and filters them live… | Browse and filter the job list | 2 | (weak) JobFilterTest.java |
| 5 | Tapping a listing opens a detail screen showing every job… | Open job detail | 3 | — |
| 6 | `sh gradlew clean test --no-daemon` and `assembleDebug`… | — | — | — |

## Scenario coverage

- Scenarios: 5 total, 3 with detected test evidence, 1 with weak (token-match) evidence only, 1 without
- ✓ `Load bundled job listings` (@w1)
- ✓ `Malformed dataset degrades gracefully` (@w1)
- ✓ `Search filters listings` (@w1)
- ~ `Browse and filter the job list` (@w2)
- ✗ `Open job detail` (@w3)

## Demo script (manual walkthrough)

1. **Load bundled job listings** — run the app, perform the scenario, observe the outcome.
2. **Search filters listings** — run the app, perform the scenario, observe the outcome.
3. **Browse and filter the job list** — run the app, perform the scenario, observe the outcome.
4. **Open job detail** — run the app, perform the scenario, observe the outcome.

## Gaps

- 1 scenario(s) lack detected test evidence — verify manually or add checks

Note: 'no evidence' means neither the scenario name nor a
token match was found in test files — verify by hand before
treating it as a real gap. '~' marks weak token-match evidence.

## Outcome follow-up

Optional; this is a post-release learning record, not a finish gate.
- Status: not yet measured
- Owner and review date: (record when known)
- Signal and observed result: (record after release; do not invent)
- Learning or next action: (record after review)