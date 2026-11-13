# ADR 0001 — Initial architecture

Date: 2026-11-13 (baseline recorded retroactively)
Status: accepted (describes what is built)

## Context

A job-board Android app needing listings, details, push notifications,
and ads — built as a learning/practice project.

## Decisions

1. **Plain Activity architecture.** Screens are Activities
   (`Splash/SignIn/SignUp/ForgotPassword/JobProviderMain`) with no MVVM
   or DI. Rationale: era-typical Android sample; simplicity.
2. **OneSignal for push, Firebase for ads.** Third-party SDKs configured
   via `manifestPlaceholders` in `app/build.gradle`. Rationale: fastest
   path to notifications + monetization without a backend.
3. **Support library, frozen toolchain.** `android.support.*`, SDK 26,
   minSdk 15, Java. Rationale: period sample; modernization deferred
   (consistent with sibling Android repos).
4. **No backend in repo.** Job data and auth endpoints are external —
   the app is a client only. Rationale: scope was the Android client.

## Consequences / open questions

- minSdk 15 / target 26 blocks modern APIs and Play requirements —
  modernization is a separate project.
- OneSignal 3.8.3 + firebase-ads 12.0.1 are pinned old; upgrades need
  manifest/Gradle coordination.
- No tests beyond template — behavior is unverified by automation.
- Data/auth endpoints are implicit in code — documenting them is future
  work if the backend contract matters.
