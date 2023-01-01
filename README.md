# JetNote
JetNote is modern MVVM note android application written by kotlin language and [jetpack-compose](https://github.com/JetBrains/compose-jb) toolkit. The app dealing with Room for Database, Dagger-Hilt dependency injection, Coroutine the non-blocking programming, Navigation-Compose, DataStore, ExoPlayer to play the recorded media in the app, CameraX to take and save picture, And more to make it good app that inspired by google app **keep Notes**.

[![API](https://img.shields.io/badge/API-25%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=25)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-v1.7.0-blue.svg)](https://kotlinlang.org)
[![Kotlin Coroutines Version](https://img.shields.io/badge/Coroutines-v1.6.4-blue.svg)](https://kotlinlang.org/docs/reference/coroutines-overview.html)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Android Platform](https://img.shields.io/badge/Platform-Android-green.svg?style=flat)](https://www.android.com/)
[![Android CI](https://github.com/City-Zouitel/JetNote/actions/workflows/android.yml/badge.svg)](https://github.com/City-Zouitel/JetNote/actions/workflows/android.yml)
[![CodeFactor](https://www.codefactor.io/repository/github/city-zouitel/jetnote/badge)](https://www.codefactor.io/repository/github/city-zouitel/jetnote)

## Screenshots

<p float="left">
  <img src="screenshots/Screenshot_20221206-134054_JetNote.jpg" width="32%" />
  <img src="screenshots/Screenshot_20221206-134218_JetNote.png" width="32%" />
  <img src="screenshots/Screenshot_20221206-134300_JetNote.png" width="32%" />
</p>

### Chuck it out [Project manager](https://github.com/orgs/City-Zouitel/projects/1/views/1) to see what's going on and what's commingup in this project.

## Libraries
### [Kotlinx-coroutines](https://github.com/Kotlin/kotlinx.coroutines) 
A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.

### [Navigation](https://developer.android.com/jetpack/compose/navigation) 
The Navigation component provides support for Jetpack Compose applications. You can navigate between composables while taking advantage of the Navigation component’s infrastructure and features.

### [Room](https://developer.android.com/jetpack/androidx/releases/room) 
The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

### [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.

### [Dependency-injection-with-hilt](https://developer.android.com/training/dependency-injection/hilt-android)
Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project. Doing manual dependency injection requires you to construct every class and its dependencies by hand, and to use containers to reuse and manage dependencies.

### [Sketchbook](https://github.com/GetStream/sketchbook-compose) 
Jetpack Compose canvas library that helps you draw paths, images on canvas with color pickers and palettes.

### [accompanist](https://github.com/google/accompanist) 
Accompanist is a group of libraries that aim to supplement Jetpack Compose with features that are commonly required by developers but not yet available.
<details>

1. **Permissions:** A library that provides Android runtime permissions support for Jetpack Compose.

2. **Pager:** A library that provides utilities for building paginated layouts in Jetpack Compose, similar to Android's ViewPager.

3. **Navigation-Animation:** A library which provides Compose Animation support for Jetpack Navigation Compose.

4. **SwipeRefresh:** A library that provides a layout implementing the swipe-to-refresh UX pattern, similar to Android's SwipeRefreshLayout.

5. **Flow Layouts:** A library that adds Flexbox-like layout components to Jetpack Compose.
</details>

### [ExoPlayer](https://exoplayer.dev/)
ExoPlayer is an application level media player for Android. It provides an alternative to Android’s MediaPlayer API for playing audio and video both locally and over the Internet.

### [CameraX](https://developer.android.com/jetpack/androidx/releases/camera)
CameraX is an addition to Jetpack that makes it easier to add camera capabilities to your app. The library provides a number of compatibility fixes and workarounds to help make the developer experience consistent across many devices.

### [Timber](https://github.com/JakeWharton/timber) 
This is a logger with a small, extensible API which provides utility on top of Android's normal Log class.

### [Coil](https://coil-kt.github.io/coil/compose/) 
An image loading library for Android backed by Kotlin Coroutines.


## The App Hierarchy

```
                          *****************************
                          |        UI Controller      |
                          *****************************
                                        |
                                        |
                          *****************************
                          |        View Module        |
                          *****************************
                                        |
                                        |
                          *****************************
                          |         Repository        |
                          *****************************
                                        |
                      --------------------------------------
                      |                                     |
          *****************************      *****************************
          |            Room           |      |        ...........        |
          *****************************      *****************************
```

## License
```text
Copyright 2022 The Android Open Source Project
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
