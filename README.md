# Tiffin

## What I cut and why

To stay within the time limit, I prioritised completing a reliable flow for browsing kitchens, viewing details, opening the paywall, and saving the fake purchase state.

- I did not add delivery-slot selection. It is a useful paid feature, but it can be completed after the timed assignment without leaving one of the required screens half-finished.
- I kept the UI simple and did not spend time on animations or extensive visual polish. I focused on making every screen clear and functional first.
- I used a basic MVVM structure suitable for the current size of the app. I did not add dependency injection because manually creating the small number of dependencies kept the implementation easier to follow without affecting the required functionality.

## Overview

Tiffin is a small Jetpack Compose Android app for browsing daily tiffins from nearby home kitchens.

Tiffin is a single-module Jetpack Compose app with a kitchen list, kitchen details, a trial paywall, persistent purchase state, local JSON data, and Logcat analytics. It uses Kotlin, MVVM, minimum SDK 24, and compile SDK 36.

## Requirements

- Android Studio
- JDK 17
- Android SDK 36
- No API keys or backend configuration are required

## Build

From the repository root, run:

```bash
./gradlew assembleDebug
```

The debug APK is written to:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Task 2 - Fix what the agent wrote

### Problems

1. `items` is a normal mutable list created again on every recomposition, so Compose does not observe `addAll()` and the user may see an empty or reset list.

2. `LaunchedEffect(items)` uses that recreated list as its key, so a recomposition can restart the effect and make duplicate network requests.

3. `URL.readText()` performs blocking network work on the main thread, so a slow connection can freeze or crash the app and lead to a one-star review. In my Tiffin app, `KitchenRepository` performs the file read and JSON parsing on `Dispatchers.IO`, keeping that work off the main thread.

4. The live API key is shipped inside the APK, where it can be extracted and abused, exposing company data and potentially creating the unexpected bill mentioned in the assignment.

### Corrected version

```kotlin
class KitchenViewModel : ViewModel() {

    var kitchens by mutableStateOf<List<Kitchen>>(emptyList())
        private set

    init {
        loadKitchens()
    }

    fun loadKitchens() {
        viewModelScope.launch {
            kitchens = withContext(Dispatchers.IO) {
                runCatching {
                    // BASE points to our backend. The live API secret stays on the server.
                    val response = URL(BASE).readText()
                    parse(response)
                }.getOrDefault(emptyList())
            }
        }
    }
}

@Composable
fun KitchenList(
    vm: KitchenViewModel,
) {
    LazyColumn {
        items(
            items = vm.kitchens,
            key = { kitchen -> kitchen.id },
        ) { kitchen ->
            KitchenRow(kitchen)
        }
    }
}
```

This correction stays focused on the four problems in the supplied snippet. In a production implementation, I would expose loading, success, empty, and error states through a dedicated UI state and place the network implementation behind a repository. The production API secret would remain on a trusted server rather than being included anywhere in the Android APK.

## Task 3 — Release Note

### Checks before publishing

Before publishing, I would test the app on a Pixel 2 emulator running API 24, a Pixel 6 emulator running API 36, and my Samsung Galaxy M33 phone.

I would test the complete app flow from browsing kitchens to opening the detail screen and completing the fake purchase. I would pay particular attention to the third-launch paywall, error and retry states, navigation, and whether the purchased state remains saved after rotation, process recreation, force-stop, and reopening the app. I would also install the final APK on a clean device and perform one final smoke test.

### Most likely production problem

The most likely production problem is the saved subscription or launch-count state behaving incorrectly. This could show the paywall again to someone who has already subscribed, or prevent it from appearing for a free user.

For a production app, I would use Firebase Analytics to monitor important events such as paywall views and completed purchases. I would also use Firebase Crashlytics to monitor crashes, non-fatal errors, affected Android versions, and affected devices.

I would initially release the app to around 10–20% of users through a staged rollout. I would monitor the analytics, crashes, non-fatal errors, and user flow before gradually releasing it to everyone.

### Rollback plan

If a serious issue appeared at 11 PM, I would immediately stop the staged Play Store rollout so that more users would not receive the faulty version. I would check Firebase Crashlytics to find the error, stack trace, affected app version, and devices.

I would then restore the last known working commit, increase the app’s version code, build and smoke-test a hotfix, and publish it. Google Play cannot instantly downgrade users who already installed the faulty version, so the staged rollout would help limit the number of affected users while the hotfix is prepared.

### What I would build next

1. Add user accounts and favourites so users can save kitchens and access their preferences across devices.
2. Add delivery-slot selection for subscribed users because it is one of the promised paid benefits.
3. Load kitchens and weekly menus from a remote API with local caching so the content can be updated without releasing a new app version and can still be viewed during temporary network failures.

## Task 4 — AI Usage

### Tools used and code contribution

I used OpenAI Codex as a pair-programming and planning tool. I mainly used it to draft the Compose UI and navigation code because those parts followed patterns I had already implemented by hand in other applications. The usual structure involves observing state from a ViewModel, displaying the data in a `LazyColumn`, creating a reusable card composable, and passing click events to the navigation layer. Using Codex for this repetitive setup saved time.

I wrote the data models, UI states, repository, ViewModels, persistence logic, and the main business decisions myself. Codex acted as an anchor when I needed help with a particular code snippet, wanted to compare approaches, or encountered an error.

Roughly 50–60% of the first-draft code came from Codex suggestions, mainly in the UI and navigation layers. I manually integrated and reviewed that code, simplified parts where necessary, fixed incompatible dependency suggestions, and verified the final behaviour myself.

### My best prompt

The most useful prompt I sent was:

> we have to paste the corrected version in the readme we can cut some things like for now just make it a basic code that is conceptually right and works no need to follow good practices for example we can forget about uistates for now and just in the end of task 2 add notes like we should use Uistate for good code but as the code doesn't look that big i didnt include also we can directly call the viewmodel and add note that in real appllication we should add a repository

### One thing the agent got wrong

The agent initially generated a `KitchenRepository` that parsed the JSON manually using `JSONArray`, nested loops, and individual calls such as `getString()` and `getInt()`. The code would work, but it was unnecessarily long and did not match my goal of keeping the project simple and readable.

I caught this while reviewing the repository before treating the generated code as final. Since the JSON structure already matched my `Kitchen` and `DailyMenu` data classes, I realised Kotlin Serialization could perform the same mapping directly.

I added `@Serializable` to the data classes, configured `Json` with `ignoreUnknownKeys = true`, and replaced the manual parsing code with:

```kotlin
json.decodeFromString<List<Kitchen>>(jsonText)
```

### One thing I wrote by hand

I wrote both UI state files, `KitchenListUiState` and `SubscriptionUiState`, by hand. They were small enough that explaining every state to an AI agent and reviewing its output would have taken longer than writing them directly. Doing this myself also helped me clearly define the possible list states and understand how the launch count and subscription status determine whether the paywall should be shown.
