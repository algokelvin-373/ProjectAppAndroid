# Compose Text Image

Contoh sederhana menggabungkan **ImageView (XML)** dengan **Text (Jetpack Compose)** dalam 1 layout. Project ini menampilkan gambar dari drawable, kemudian menampilkan teks menggunakan `ComposeView` yang di-inflate lewat `ActivityMainBinding` (View Binding).

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- View System (XML) + View Binding
- AndroidX (Core, Lifecycle, Activity Compose)

## Fitur

- Menampilkan `ImageView` dari resource drawable (`@drawable/ic_compose`)
- Menampilkan teks "Hello for Jetpack Compose" menggunakan Jetpack Compose via `ComposeView`
- Menggunakan View Binding untuk mengakses view di layout XML

## Struktur Project

```text
app/src/main
├── java/com/algokelvin/imagetext
│   ├── MainActivity.kt
│   └── ui/theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── res/
│   ├── drawable/ic_compose.xml
│   ├── layout/activity_main.xml
│   ├── mipmap-*/ (launcher icons)
│   ├── values/strings.xml, colors.xml, themes.xml
│   └── xml/backup_rules.xml, data_extraction_rules.xml
└── AndroidManifest.xml
```

## Dependencies (Highlights)

- Compose BOM (`androidx.compose:compose-bom`)
- `androidx.activity:activity-compose`
- `androidx.ui`, `androidx.material3`
- `androidx.core.ktx`, `androidx.lifecycle.runtime.ktx`
- View Binding enabled (`viewBinding true` + `buildFeatures.compose true`)

## Cara Menjalankan

1. Buka project di **Android Studio** (Giraffe/Koala+ recommended)
2. Sync Gradle
3. Pilih emulator atau device fisik (minSdk 24, targetSdk 35)
4. Run `app` → akan tampil gambar + teks Compose di tengah layar

## Catatan

- Menggunakan `ComposeView` untuk embed Compose di layout XML (interop Compose–View)
- Sudah diset `kotlinCompilerExtensionVersion '1.5.1'` & Java 8 compatibility
- Versi: `versionCode 1`, `versionName "1.0.0"`

## Changelog

- **1.0.0** : Create APK Project