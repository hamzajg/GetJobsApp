# AGENTS.md — GetJobsApp

Guidance for AI agents (and humans) working in this repository.

## Project

An Android job-board app (`com.thephoenixit.getjobsapp`): browse job
listings, view details, receive OneSignal push notifications for new
openings, ads via Firebase. Java, minSdk 24 / compile+target SDK 34, AndroidX,
AGP 8.7.3 / Gradle 8.9, JDK 17.

## Commands

Verified 2026-11-13 against CI (`.github/workflows/ci.yml`) and
`app/build.gradle`.

| Task | Command |
|---|---|
| Unit tests (CI) | `sh gradlew test --no-daemon` |
| Build APK | `sh gradlew assembleDebug` |
| Install | `gradle :app:installDebug` |

Requires: Android SDK platform 34 + build-tools 34.0.0, JDK 17. `manifestPlaceholders` in
`app/build.gradle` inject `onesignal_app_id` and
`onesignal_google_project_number` — check them before release builds.

## Architecture

```
app/src/main/java/com/thephoenixit/getjobsapp/
  app/MyApp.java              Application class
  views/
    SplashActivity.java       launch screen
    SignInActivity.java       login
    SignUpActivity.java       registration
    ForgotPasswordActivity.java
    JobProviderMainActivity.java  main job list/details
  utils/                      helpers
app/src/main/AndroidManifest.xml
app/build.gradle              SDK 34, OneSignal 5.10.2, firebase-ads 23.6.0,
                              appcompat + constraint-layout + material
```

Classic Activity-based Android app — no MVVM, no DI framework. Job data
source and OneSignal/Firebase wiring live in activities/utils; check
`MyApp.java` for SDK init.

## Conventions

- Java only, AndroidX imports (`androidx.*`, `com.google.android.material.*`).
- Activities + layouts; keep new screens consistent with existing
  Activity style.
- Third-party SDKs (OneSignal, Firebase Ads) initialized in
  Application/manifest — don't re-init per activity.
- KISS: legacy sample — no architecture layers.

## Landmines

- **Default branch is `master`, not `main`.**
- minSdk **24**, target **34** — bumped during the AGP 8 migration.
- OneSignal `5.10.2` init lives in `MyApp.onCreate` (`initWithContext` +
  coroutine-bridged `requestPermission`); `firebase-ads:23.6.0`.
- `manifestPlaceholders` carry real-ish IDs (`onesignal_app_id`) —
  treat as config, don't leak new secrets.
- AndroidX only — don't reintroduce `android.support.*` imports or
  pre-AndroidX artifacts (they break resource merging).
- Gradle wrapper checked in — use `sh gradlew`, not system gradle.

## Agent rules

- Branch from `master`; name branches `feat/*`, `fix/*`, `chore/*`, `docs/*`.
- Docs-only changes still go through a PR; never commit to `master` directly.
- Verify before claiming: `sh gradlew clean test --no-daemon` and
  `sh gradlew assembleDebug --no-daemon` on JDK 17; report the real result.
- Keep changes minimal and consistent with surrounding code. No drive-by
  refactors.
- Update AGENTS.md / docs/ARCHITECTURE.md when SDKs, SDK levels, or
  commands change.
