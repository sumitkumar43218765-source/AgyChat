# DefaultProject — Android Kotlin Starter

Established stable stack ke saath default MVVM + Clean Architecture starter.

## Stack
- Gradle 8.7, AGP 8.6.1, Kotlin 1.9.24
- KSP only (no kapt)
- Jetpack Compose + Material3
- Hilt (DI)
- DataStore + Gson (Room ke bajaye — aarch64/AndroidIDE par unreliable hai)
- minSdk 24, targetSdk/compileSdk 34

## Structure (Clean Architecture)
```
presentation/   -> Compose UI, ViewModel, Theme
domain/         -> Repository interface, models
data/           -> Repository impl, DataStore (PreferencesManager)
di/             -> Hilt AppModule
```

## AndroidIDE mein use karne ke liye
1. Zip extract karo apne project folder mein.
2. AndroidIDE mein "Open Project" se folder open karo.
3. Gradle sync karo — pehli baar internet chahiye dependencies download karne ke liye.
4. Agar `gradlew` missing error aaye, Termux mein project folder ke andar:
   `gradle wrapper --gradle-version 8.7` chala do (system Gradle installed hona chahiye).
5. Run/Build karo — MainActivity default entry point hai.

## Rename karne ke liye
- `applicationId` / `namespace` app/build.gradle.kts mein change karo.
- Package `com.example.defaultproject` ko refactor karke apna naam do (AndroidIDE ka rename-package feature use kar sakte ho).

## Notes
- Counter ek dummy example hai jo dikhata hai DataStore + Gson pattern kaise use karna hai (`PreferencesManager.saveObject`/`getObject` complex objects ke liye).
- Launcher icon ek simple vector drawable hai (`res/drawable/ic_launcher.xml`) — baad mein apna asli icon daal sakte ho.
