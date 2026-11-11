# GetJobsApp

An Android job-board app: browse job listings, view details, and receive push
notifications for new openings.

## What it does

`GetJobsApp` (`com.thephoenixit.getjobsapp`) presents a list of job postings,
lets users open a posting to see its details, and uses OneSignal to deliver
notifications about new jobs. Ads are served through Firebase.

## Tech stack

- **Language:** Java
- **Min SDK:** 15 · **Target/compile SDK:** 26
- **UI:** Android Support Library (`appcompat`, `constraint-layout`)
- **Push:** OneSignal 3.8.3
- **Ads:** Firebase Ads 12.0.1
- **Forms:** Android Saripaar

## Project layout

```
app/
  src/main/java/com/thephoenixit/getjobsapp/
    app/      # application class
    utils/    # helpers
    views/    # activities/fragments and adapters
  src/main/res/
  build.gradle
build.gradle
settings.gradle
```

## Building

The project targets SDK 26 and has no Gradle wrapper checked in. Open it in
Android Studio (or run Gradle with `ANDROID_HOME` pointing at an SDK that has
API 26 installed):

```bash
gradle :app:assembleDebug
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
gradle :app:testDebugUnitTest
```

## Status

Legacy sample app. It uses the pre-AndroidX support library and an old SDK;
modernizing it would require an AndroidX migration and a dependency upgrade.
