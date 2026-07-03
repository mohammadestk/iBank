# iBank — Agent Guide

## Project Overview
- Multi-module Android app, package `dev.esteki.ibank`
- 4 screens: Home (implemented), Search, Message, Settings (stubs)
- Design system: `specs/ui/meridian-design-system.html` (Meridian — Material 3 Banking)

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
- `./gradlew connectedDebugAndroidTest \
    -x :core-domain:connectedDebugAndroidTest \
    -x :core-data:connectedDebugAndroidTest` — run instrumented tests

## Architecture

**Pattern:** Clean Architecture + MVI + Multi-module

**Module structure:**
- `core-presentation` — theme, shared composables
- `core-domain` — use cases, repository interfaces, domain models
- `core-data` — repository implementations, data sources, DI bindings
- `feature-<name>` — one module per feature (e.g., `feature-home`, `feature-search`)

**MVI per feature:**
- `*Screen.kt` — composable, renders UI state, emits intents
- `*ViewModel.kt` — processes intents, produces single `@Stable` UI state
- `*Module.kt` — Hilt `@InstallIn(ViewModelComponent::class)` provides initial state
- UI state: `@Stable` data class, exposed via `StateFlow`, collected with `collectAsStateWithLifecycle()`
- Result handling: `*Result` sealed interface (`Idle`, `Loading`, `Success`, `Failure`)

**Clean Architecture layers:**
- UseCase → Repository (interface) → LocalDataSource / RemoteDataSource
- No remote implementations yet — use fake/stub data sources

## Key Conventions
- Layout direction forced LTR in `IBankTheme` (no RTL)
- Dynamic color disabled by default
- Routes: `BottomRoute` sealed class with `@Serializable` (Navigation 3 requires kotlinx.serialization)
- Compose tests: `createComposeRule()` from `v2` package
- Unit tests: MockK for mocking, Turbine for testing Flow emissions
- Unit tests: Arrange-Act-Assert pattern (whenever possible)
- No linting config yet (no ktlint/detekt)
- Use `hiltViewModel()` from `hilt-navigation-compose` for ViewModel initialization
- **SOLID principles are mandatory** — every class, interface, enum, and object must follow SRP, OCP, LSP, ISP, and DIP
- **One type per file** — every class, enum, interface, and object must be in its own file
- **Packaging by entity or feature** — top-level packages are organized by domain entity (e.g., `account`, `message`) or feature (e.g., `feature-home`). Within each package, sub-packages are organized by responsibility (`model/`, `repository/`, `usecase/`)

## Gotchas
- Navigation 3 uses `NavDisplay` + `entryProvider`, NOT `NavHost` from old navigation
- `BottomRoute` must be `@Serializable` for Navigation 3 to work
- `SnapshotStateList` for back stack (Compose state), NOT `mutableStateListOf` in ViewModel
- `compileSdk` uses new `release(37)` syntax with `minorApiLevel`
