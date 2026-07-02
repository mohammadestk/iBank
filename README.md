<div align="center">

# iBank

**Modern Android Banking App**

Built with Jetpack Compose, Clean Architecture, and Material 3

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.0-7F52FF?logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202026.06-4285F4)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Clean%20Architecture-MVI-0B6E4F)](#architecture)
[![License](https://img.shields.io/badge/License-MIT-blue)](#license)

</div>

---

## Features

- **Home** — Balance card, quick actions, recent transactions
- **Search** — Filter chips, payee suggestions, real-time search
- **Messages** — Segmented tabs, alert cards, transaction notifications
- **Settings** — Profile, security toggles, preferences

## Architecture

```
┌─────────────────────────────────────────────┐
│                Feature Layer                │
│  Screen ← ViewModel ← UseCase              │
├─────────────────────────────────────────────┤
│                Domain Layer                 │
│  Repository Interface ← Domain Models       │
├─────────────────────────────────────────────┤
│                 Data Layer                  │
│  Repository Impl → LocalDataSource → Room   │
└─────────────────────────────────────────────┘
```

- **MVI** — Unidirectional data flow with `StateFlow`
- **Package by domain** — Account, Transaction, Message, Settings
- **Clean separation** — No framework leaks between layers

## Tech Stack

| Category | Library |
|----------|---------|
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation 3 |
| DI | Hilt (KSP) |
| Storage | Room |
| Images | Coil 3 |
| Serialization | kotlinx-serialization |
| Testing | JUnit4, MockK, Turbine, Truth |

## Design System

**Meridian** — A Material 3 banking design system with:

- **Emerald primary** (`#0B6E4F`) — Trust and growth
- **Gold tertiary** — Positive money highlights
- **Manrope + Inter + JetBrains Mono** — Display, body, and tabular numerals

> See the full spec at [`specs/ui/meridian-design-system.html`](specs/ui/meridian-design-system.html)

## Module Structure

```
iBank/
├── app                    # Entry point, navigation
├── core-presentation      # Meridian theme, shared composables
├── core-domain            # Models, repository interfaces, use cases
├── core-data              # Room DB, DAOs, repository implementations
├── feature-home/          # Home screen
├── feature-search/        # Search screen
├── feature-message/       # Messages screen
├── feature-settings/      # Settings screen
└── specs/                 # Design system specification
```

## Getting Started

```bash
# Build
./gradlew assembleDebug

# Test
./gradlew testDebugUnitTest

# Instrumented Test
./gradlew connectedDebugAndroidTest
```

## License

MIT
