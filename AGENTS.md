# AGENTS.md — GetJobsApp

Guidance for AI agents (and humans) working in this repository.

## Project

An Android job-board app (`com.thephoenixit.getjobsapp`): browse job
listings, view details, receive OneSignal push notifications for new
openings, ads via Firebase. Java, minSdk 15 / compile+target SDK 26,
Android Support Library (pre-AndroidX).

## Commands

Verified 2026-11-13 against CI (`.github/workflows/ci.yml`) and
`app/build.gradle`.

| Task | Command |
|---|---|
| Unit tests (CI) | `sh gradlew test --no-daemon` |
| Build APK | `sh gradlew assembleDebug` |
| Install | `gradle :app:installDebug` |

Requires: Android SDK (platform 26), JDK 8-era toolchain per CI's
`setup-java` + `setup-android`. `manifestPlaceholders` in
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
app/build.gradle              SDK 26, OneSignal 3.8.3, firebase-ads 12.0.1,
                              appcompat + constraint-layout
```

Classic Activity-based Android app — no MVVM, no DI framework. Job data
source and OneSignal/Firebase wiring live in activities/utils; check
`MyApp.java` for SDK init.

## Conventions

- Java only, support-library imports (`android.support.*`).
- Activities + layouts; keep new screens consistent with existing
  Activity style.
- Third-party SDKs (OneSignal, Firebase Ads) initialized in
  Application/manifest — don't re-init per activity.
- KISS: legacy sample — no architecture layers.

## Landmines

- **Default branch is `master`, not `main`.**
- minSdk **15**, target **26** — very old; modern APIs need checks.
- OneSignal `3.8.3` and `firebase-ads:12.0.1` are pinned old versions.
- `manifestPlaceholders` carry real-ish IDs (`onesignal_app_id`) —
  treat as config, don't leak new secrets.
- Support library, not AndroidX — don't mix `androidx.*` imports.
- Gradle wrapper checked in — use `sh gradlew`, not system gradle.

## Agent rules

- Branch from `master`; name branches `feat/*`, `fix/*`, `chore/*`, `docs/*`.
- Docs-only changes still go through a PR; never commit to `master` directly.
- Verify before claiming: `sh gradlew test --no-daemon` and report the
  real result.
- Keep changes minimal and consistent with surrounding code. No drive-by
  refactors.
- Update AGENTS.md / docs/ARCHITECTURE.md when SDKs, SDK levels, or
  commands change.
