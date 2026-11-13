# Architecture — GetJobsApp

Current-state map. Descriptive, not aspirational; see `docs/adr/` for why.

## Shape

```
GetJobsApp/                    Android Gradle app (Java)
├── app/
│   ├── build.gradle           compileSdk 26, minSdk 15, target 26;
│   │                          OneSignal 3.8.3, firebase-ads 12.0.1,
│   │                          appcompat + constraint-layout;
│   │                          manifestPlaceholders for OneSignal IDs
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/thephoenixit/getjobsapp/
│           ├── app/MyApp.java            Application (SDK init)
│           ├── views/
│           │   ├── SplashActivity.java
│           │   ├── SignInActivity.java
│           │   ├── SignUpActivity.java
│           │   ├── ForgotPasswordActivity.java
│           │   └── JobProviderMainActivity.java   job list/details
│           └── utils/                     helpers
└── .github/workflows/ci.yml   setup-android + JDK → gradlew test
```

## Flows

- **Launch:** `MyApp` (Application) → `SplashActivity` → auth gate →
  `SignIn`/`SignUp`/`ForgotPassword` or `JobProviderMainActivity`.
- **Jobs:** main activity lists postings → detail view; data source in
  utils/activities (no repository layer).
- **Notifications:** OneSignal SDK (init via manifest placeholders).
- **Ads:** Firebase Ads SDK.

## State

Local/remote job data via activities; auth likely remote (check
activities for endpoints). No local DB abstraction visible.

## Toolchain

JDK 8-era, Android Gradle Plugin of the support-library era, SDK 26.
Pre-AndroidX.
