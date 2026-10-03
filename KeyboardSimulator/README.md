# Keyboard Simulator

Custom numeric keyboard simulator built for Android (Java + Kotlin mix). Project ini menampilkan multiple input fields yang bisa dialihkan, cross button untuk clear, dan custom digit keyboard (include layout) dengan toggle highlight.

## Tech Stack
- Java & Kotlin (mixed)
- Android Views (XML) + AppCompat
- View Binding (Kotlin)
- ConstraintLayout
- Custom UI (drawables, selectors)

## Struktur Project
```text
app/src/main
├── java/com/algokelvin/keyboardsimulator
│   ├── KeyboardController.java  (core logic: handle input, switch field, cross, backspace)
│   ├── MainJavaActivity.java     (Java entry: setup 3 fields)
│   └── MainActivity.kt           (Kotlin variant example)
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── include_keyboard.xml   (custom numeric keyboard)
│   ├── drawable/ (bg selectors, icons)
│   ├── values/ (strings, colors, dimens, styles)
│   └── mipmap-*/
└── AndroidManifest.xml
```

## Fitur
- Custom numeric keypad (0-9, Backspace, Cancel, OK)
- Switch antar input field (highlight active field)
- Clear per-field via cross icon
- Preserve text saat berpindah field
- Flexible setup via `KeyboardController` methods

## Cara Menjalankan
1. Buka project di Android Studio
2. Sync Gradle
3. Run `app` (launcher default: `MainJavaActivity`)
4. Ketuk field untuk fokus, gunakan keyboard untuk input/hapus

## Catatan
- Manifest saat ini set `MainJavaActivity` sebagai launcher (Java version). `MainActivity.kt` tersedia sebagai alternatif.
- OK button saat ini ada handler placeholder (bisa dikembangkan sesuai kebutuhan).
- View Binding di-enable (Kotlin path).

## Changelog
- 1.0.0 : Initial project
- Minor: Add OK button handling placeholder in controller, update README
