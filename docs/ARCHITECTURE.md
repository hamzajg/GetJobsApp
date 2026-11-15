# Architecture — GetJobsApp

Current-state map. Descriptive, not aspirational; see `docs/adr/` for why.

## Shape

```
GetJobsApp/                    Android Gradle app (Java, AndroidX)
├── app/
│   ├── build.gradle           compileSdk/target 34, minSdk 24, AGP 8.7.3;
│   │                          OneSignal 5.10.2, firebase-ads 23.6.0,
│   │                          appcompat + constraint-layout + material +
│   │                          recyclerview; testImpl android-json;
│   │                          manifestPlaceholders for OneSignal IDs
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── assets/jobs.json        bundled ~10-listing sample dataset
│       │   └── java/com/thephoenixit/getjobsapp/
│       │       ├── app/MyApp.java            Application (SDK init)
│       │       ├── jobs/                     pure-Java domain layer
│       │       │   ├── Job.java              model (contract field names)
│       │       │   ├── JobRepository.java    loads/caches jobs.json;
│       │       │   │                          parsing isolated for tests
│       │       │   └── JobFilter.java        case-insensitive keyword
│       │       │                              search (title/company/
│       │       │                              skills/location)
│       │       ├── views/
│       │       │   ├── SplashActivity.java
│       │       │   ├── SignInActivity.java   auth is a stub → routes by
│       │       │   │                          isJobProvider flag
│       │       │   ├── SignUpActivity.java
│       │       │   ├── ForgotPasswordActivity.java
│       │       │   ├── MainActivity.java     seeker home: search
│       │       │   │                          EditText + RecyclerView +
│       │       │   │                          empty state in Container
│       │       │   ├── JobAdapter.java       RecyclerView adapter
│       │       │   ├── JobDetailActivity.java  all job fields
│       │       │   └── JobProviderMainActivity.java  employer stub
│       │       └── utils/                     helpers (Constant, JsonUtils)
│       └── test/java/.../jobs/          JobRepositoryTest, JobFilterTest
└── .github/workflows/ci.yml   setup-android + JDK 17 → gradlew test
```

## Flows

- **Launch:** `MyApp` (Application) → `SplashActivity` → `SignInActivity`
  (stub auth) → `MainActivity` for seekers, `JobProviderMainActivity` for
  providers.
- **Job board (job-board-core):** `MainActivity` loads `assets/jobs.json`
  via `JobRepository` (cached), renders rows with `JobAdapter`, filters
  live through `JobFilter` as the user types in the search box; tapping a
  row opens `JobDetailActivity` with the job's fields. A missing/malformed
  dataset degrades to an empty list — never a crash.
- **Notifications:** OneSignal SDK (init via manifest placeholders).
- **Ads:** Firebase Ads SDK.

## State

Job data is a bundled asset (`assets/jobs.json`) parsed once by
`JobRepository` — no remote API, no local DB. The legacy `viaviweb.in`
PHP demo backend is unused. Auth/register/login are stubs.

## Toolchain

JDK 17, Android Gradle Plugin 8.7.3 / Gradle 8.9, compile+target SDK 34,
minSdk 24, AndroidX (appcompat/material/constraint-layout/recyclerview).
`com.vaadin.external.google:android-json` is a **test-only** dependency so
`org.json` works in local JVM unit tests.
