# FoodRecipes
An application that allows users to easily find recipes with the necessary filtering and search features.

##  App GIF

 | Light Theme | Dark Theme |
 |:-:|:-:|
 | ![Fist](https://github.com/kocerenes/FoodRecipes/blob/master/gif/lightmode.gif?raw=true) | ![3](https://github.com/kocerenes/FoodRecipes/blob/master/gif/darkmode.gif?raw=true)
 
 
 ## Features 🕹
- 100% Kotlin-only
- Following MVVM Architectural Design Pattern
- Dark theme
- Multi-Activity
- Local Caching

 ## Tech stack
* ✅ MVVM with Clean Architecture
* ✅ [Kotlin Flow][31] - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
* ✅ [LiveData][23] - is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
* ✅ [Coroutines][51] - A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
* ✅ [Navigation Component][24] - Handle everything needed for in-app navigation. asynchronous tasks for optimal execution.
* ✅ [Safe-Args][25] - For passing data between destinations
* ✅ [Dagger-Hilt][93] - A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
* ✅ [ViewModel][17] - Easily schedule asynchronous tasks for optimal execution.
* ✅ [Retrofit][90]- Retrofit is a REST client for Java/ Kotlin and Android by Square inc under Apache 2.0 license. Its a simple network library that is used for network transactions. By using this library we can seamlessly capture JSON response from web service/web API.
* ✅ [OkHttp][23] - Doing HTTP efficiently makes your stuff load faster and saves bandwidth.
* ✅ [Coil][92] - An image loading library for Android backed by Kotlin Coroutines
* ✅ [View Binding][11] - a feature that allows you to more easily write code that interacts with views.
* ✅ [Data Binding][85] - The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
* ✅ [Lifecycle][22] - As a user navigates through, out of, and back to your app, the Activity instances in your app transition through different states in their lifecycle.
* ✅ [Room][12] - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* ✅ [DataStore][14] - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
* ✅ [Jsoup][13] - It provides a very convenient API for fetching URLs and extracting and manipulating data, using the best of HTML5 DOM methods and CSS selectors.


[11]: https://developer.android.com/topic/libraries/view-binding
[92]: https://coil-kt.github.io/coil/
[93]: https://developer.android.com/jetpack/compose/libraries#hilt
[51]: https://developer.android.com/kotlin/coroutines
[90]: https://square.github.io/retrofit/
[31]: https://developer.android.com/kotlin/flow
[22]: https://developer.android.com/guide/components/activities/activity-lifecycle
[17]: https://developer.android.com/jetpack/compose/state#viewmodel-state
[23]: https://square.github.io/okhttp/
[24]: https://developer.android.com/guide/navigation/navigation-getting-started
[25]: https://developer.android.com/guide/navigation/navigation-pass-data
[23]: https://developer.android.com/topic/libraries/architecture/livedata
[85]: https://developer.android.com/topic/libraries/data-binding
[12]: https://developer.android.com/jetpack/androidx/releases/room
[13]: https://jsoup.org/
[14]: https://developer.android.com/topic/libraries/architecture/datastore
