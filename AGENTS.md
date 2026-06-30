# iBank — Agent Guide

## Project Overview
- Single-module Android app (moving to multi-module), package `dev.esteki.ibank`
- 4 screens: Home (implemented), Search, Message, Settings (stubs)
- Design specs: `specs/ui/` (SVG files)
- No remote/data layer yet — abstract layer only

## Tech Stack (versions matter)
- Kotlin 2.4.0, AGP 9.2.1, JVM 21
- Jetpack Compose + Material3 (BOM 2026.06.00)
- Navigation 3 (`androidx.navigation3`) — NOT old `androidx.navigation`
- Hilt for DI (with KSP 2.3.9, NOT kapt — kapt incompatible with AGP 9.x)
- Coil 3 for image loading
- Kotlin Serialization (NOT Gson/Moshi)
- Testing: JUnit4, MockK, Google Truth, Turbine

## Build Commands
- `./gradlew assembleDebug` — build debug
- `./gradlew testDebugUnitTest` — run unit tests
- `./gradlew connectedDebugAndroidTest` — run instrumented tests

## Architecture

**Pattern:** Clean Architecture + MVI + Multi-module

**Module structure (target):**
- `core-presentation` — theme, shared composables, base ViewModels
- `core-data` — repository interfaces, data source interfaces, models
- `feature-<name>` — one module per feature (e.g., `feature-home`, `feature-search`)

**MVI per feature:**
- `*Screen.kt` — composable, renders UI state, emits intents
- `*ViewModel.kt` — processes intents, produces single `@Stable` UI state
- `*Module.kt` — Hilt `@InstallIn(ViewModelComponent::class)` provides initial state
- UI state: `@Stable` data class, exposed via `StateFlow`, collected with `collectAsStateWithLifecycle()`

**Clean Architecture layers:**
- UseCase → Repository (interface) → LocalDataSource / RemoteDataSource
- No remote implementations yet — use fake/stub data sources

## Key Conventions
- Theme: use `MaterialTheme.iTypography` (custom `AppTypography`) for design-spec text styles, NOT `MaterialTheme.typography`
- Layout direction forced LTR in `IBankTheme` (no RTL)
- Dynamic color disabled by default
- Routes: `BottomRoute` sealed class with `@Serializable` (Navigation 3 requires kotlinx.serialization)
- Compose tests: `createComposeRule()` from `v2` package
- Unit tests: MockK for mocking, Turbine for testing Flow emissions
- No linting config yet (no ktlint/detekt)
- **No hilt-navigation-compose** — incompatible with Navigation3. Use `viewModel()` from `lifecycle-viewmodel-navigation3`

## Gotchas
- Navigation 3 uses `NavDisplay` + `entryProvider`, NOT `NavHost` from old navigation
- `BottomRoute` must be `@Serializable` for Navigation 3 to work
- `SnapshotStateList` for back stack (Compose state), NOT `mutableStateListOf` in ViewModel
- `compileSdk` uses new `release(37)` syntax with `minorApiLevel`
