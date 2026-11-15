# GetJobsApp

An Android job-board app: browse job listings, view details, and receive push
notifications for new openings.

## What it does

`GetJobsApp` (`com.thephoenixit.getjobsapp`) presents a list of job postings,
lets users open a posting to see its details, and uses OneSignal to deliver
notifications about new jobs. Ads are served through Firebase.

## Job board

The seeker home screen (`MainActivity`, reached after sign-in) lists jobs
from a bundled sample dataset, `app/src/main/assets/jobs.json` (~10
listings), with live keyword search and an empty state. Tapping a row opens
`JobDetailActivity` with the full listing. The domain layer is pure Java in
`jobs/` (`Job`, `JobRepository`, `JobFilter`) and unit-tested on the JVM —
see `specs/job-board-core/` for the spec and validation notes. Sample data
only; the legacy remote API is not wired up.

## Tech stack

- **Language:** Java (AndroidX)
- **Min SDK:** 24 · **Target/compile SDK:** 34
- **UI:** AndroidX (`appcompat`, `constraint-layout`, `material`, `recyclerview`)
- **Push:** OneSignal 5.10.2
- **Ads:** Firebase Ads 23.6.0
- **Forms:** Android Saripaar

## Project layout

```
app/
  src/main/assets/jobs.json   # bundled sample job dataset
  src/main/java/com/thephoenixit/getjobsapp/
    app/      # application class
    jobs/     # pure-Java domain: Job, JobRepository, JobFilter
    utils/    # helpers
    views/    # activities/fragments and adapters
  src/main/res/
  build.gradle
build.gradle
settings.gradle
```

## Building

The Gradle wrapper is checked in (Gradle 8.9, AGP 8.7.3). It needs JDK 17
and an SDK with API 34 and build-tools 34.0.0 installed (`ANDROID_HOME` or
`local.properties`):

```bash
sh gradlew :app:assembleDebug
```

## Configuration

The OneSignal app id is set in `app/build.gradle`:

```groovy
manifestPlaceholders = [onesignal_app_id: "..."]
```

Replace it with your own OneSignal application id before publishing a build.
The Firebase configuration file (`google-services.json`) is not committed.

## Tests

```bash
sh gradlew test --no-daemon
```

CI installs API 34 / build-tools 34.0.0, sets up JDK 17 and runs the same task.

## Status

Legacy sample app on AndroidX (AGP 8.7.3, SDK 34, JDK 17). The job-seeker
list/detail flow ships sample data from `assets/jobs.json`; auth
(register/login/forgot-password) is still stubbed and there is no real
backend integration or notification handling wired up yet.

The OneSignal Gradle plugin was pinned to `0.8.1`, which is not on the plugin
portal (the first published version is `0.10.0`); it is no longer applied,
because the `manifestPlaceholders` it generated are already set by hand in
`app/build.gradle`. `cn.gavinliu.android.lib:ShapedImageView:0.8.5` is no
longer published anywhere, so the header avatar uses
`de.hdodenhof:circleimageview` instead.

The app is mostly scaffolding: `MainActivity` now hosts the job list (ad
banner + search + RecyclerView) and `JobDetailActivity` shows a listing's
fields; the sign-in screen is the only auth screen with real logic, and
`JobProviderMainActivity` remains an empty stub.
