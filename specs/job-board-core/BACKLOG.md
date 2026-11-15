# Backlog: job-board-core

Items are USER STORIES in the parseable format below — keep the heading
shape `## US<n> — <story>` and the field lines intact so `wave_plan` can
parse them. Write the story as: "As a <role>, I want <goal>, so that
<value>" — the title after the dash IS the story.

```
## US<n> — As a <role>, I want <goal>, so that <value>
- Scenarios: @w1, @w2
- Depends: US1, US3        (optional; omit for wave-1 items)
- Size: S|M|L
- Notes: <one line>
```

Order stories by user value; dependencies express technical order only.

## US1 — As a job seeker, I want the app to ship sample job listings I can browse, so that the job board works without a backend
- Scenarios: @w1
- Size: M
- Notes: assets/jobs.json + Job/JobRepository/JobFilter + JUnit4 tests; android-json test dep

## US2 — As a job seeker, I want a searchable job list on the home screen, so that I can find relevant openings quickly
- Scenarios: @w2
- Depends: US1
- Size: M
- Notes: RecyclerView + adapter + item layout inside MainActivity Container; live filter; empty state

## US3 — As a job seeker, I want to open a job and read its full details, so that I can decide whether to apply
- Scenarios: @w3
- Depends: US2
- Size: S
- Notes: JobDetailActivity + layout + manifest; intent extras carry the Job
