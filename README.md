# Fintrack

Fintrack is a small, school-exercise Android app that explores a modular UI in Jetpack Compose.  
Scope today is **UI only** with mocked data and simple navigation between screens.

---

## Goals

- Practice multi-module setup and Compose UI structure
- Keep features isolated behind their own Gradle modules
- Share theming and common UI across modules

---

## Project structure
app/
core/
ui/
feature/
account/
budget/

### app
- Single application module and navigation host
- Wires feature screens (Account, Budget) and applies the shared theme from `core:ui`
- Contains simple, local preview/mocked data only. No repositories, no networking

### core:ui
- Shared Compose theme (colors, typography, shapes) and small reusable UI elements
- Central place for design tokens and building blocks used by feature modules

### feature:account
- Account section UI (overview, settings-style screens)
- Pure Compose screens with preview and mocked state
- No data/domain layers; exposes composables and navigation entry

### feature:budget
- Budget section UI (creating and viewing a budget)
- Pure Compose screens with preview and mocked state
- No persistence or networking; exposes composables and navigation entry

---

## What’s implemented

- Multi-module Gradle structure (`app`, `core:ui`, `feature:account`, `feature:budget`)
- Compose UI with shared theme from `core:ui`
- Basic navigation between Account and Budget screens
- Mocked and preview data for UI-only development

---

## What’s not implemented (by design)

- Real data/domain layers (repositories, use cases)
- Networking or database
- Business logic beyond simple UI state

---

## Next step

Implement the **Dashboard/Home UI** as the landing screen after launch.  
It will route to Account and Budget. The module placement for Dashboard is not yet decided — start as a screen inside `app` and extract later if needed.

---

## How to run

1. Open the project in Android Studio
2. Sync Gradle and build the project
3. Run the `app` module on an emulator or device

---

## Repository

https://github.com/yogaboyCZ/Fintrack