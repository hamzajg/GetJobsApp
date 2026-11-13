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

The Gradle 4.4 wrapper is checked in, matching AGP 3.1.0. It needs JDK 8 and
an SDK with API 26 and build-tools 27.0.3 installed (`ANDROID_HOME` or
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
sh gradlew :app:testDebugUnitTest
```

CI installs API 26 / build-tools 27.0.3, sets up JDK 8 and runs the same task.

## Status

Legacy sample app. It uses the pre-AndroidX support library and an old SDK;
modernizing it would require an AndroidX migration and a dependency upgrade.

The OneSignal Gradle plugin was pinned to `0.8.1`, which is not on the plugin
portal (the first published version is `0.10.0`); it is no longer applied,
because the `manifestPlaceholders` it generated are already set by hand in
`app/build.gradle`. `cn.gavinliu.android.lib:ShapedImageView:0.8.5` is no
longer published anywhere, so the header avatar uses
`de.hdodenhof:circleimageview` instead.

The app is mostly scaffolding: `MainActivity` only inflates a layout with an
ad view, and the sign-in screen is the only one with real logic. There is no
job list, detail screen or notification handling wired up yet.
