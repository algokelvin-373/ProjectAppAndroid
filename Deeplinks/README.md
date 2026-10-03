# Deeplinks

App contoh untuk mendemonstrasikan implementasi Android Deep Links (App Links HTTPS dan Custom Scheme). Project ini menampilkan informasi deep link yang diterima (host, path, segment code, query parameter) langsung di UI.

## Tech Stack

- Java
- Android Views (XML) + AppCompat
- ConstraintLayout
- Deep Links / App Links

## Fitur

- Menangani **App Links** (HTTPS): `https://example.com/promo/{code}?ref={ref}`
- Menangani **Custom Scheme** untuk testing lokal:
  - `algokelvin://promo/{code}?ref={ref}`
  - `deeplink://app/...`
- Menampilkan hasil parsing deep link ke UI (Data, Host, Path, Code, Ref)
- Mendukung `launchMode="singleTask"` + `onNewIntent()` agar deep link tetap ter-handle saat activity sudah berjalan

## Struktur Project

```text
app/src/main
├── java/com/algokelvin/deeplinktrain
│   └── MainActivity.java
├── res/
│   ├── layout/activity_main.xml
│   ├── values/strings.xml, colors.xml, themes.xml
│   ├── drawable/, mipmap-*/
│   └── xml/backup_rules.xml, data_extraction_rules.xml
└── AndroidManifest.xml
```

## Intent Filters

Terdapat 3 intent-filter pada `MainActivity`:

1. **HTTPS App Link** (`autoVerify="true"`)
   - Scheme: `https`
   - Host: `example.com`
   - PathPrefix: `/promo`

2. **Custom Scheme 1**
   - Scheme: `algokelvin`
   - Host: `promo`

3. **Custom Scheme 2**
   - Scheme: `deeplink`
   - Host: `app` (bisa dikombinasikan dengan path `/promo/...`)

## Cara Testing

### 1. Custom Scheme (via ADB)

```bash
adb shell am start -a android.intent.action.VIEW \
  -d "algokelvin://promo/ABC?ref=ig" \
  com.algokelvin.deeplinktrain
```

```bash
adb shell am start -a android.intent.action.VIEW \
  -d "deeplink://app/promo/XYZ?ref=yt" \
  com.algokelvin.deeplinktrain
```

```bash
adb shell am start -a android.intent.action.VIEW \
  -d "https://example.com/promo/SALE50?ref=email" \
  com.algokelvin.deeplinktrain
```

### 2. App Links (HTTPS)

Untuk `autoVerify` bekerja penuh, domain perlu menyediakan [assetlinks.json](https://developer.android.com/training/app-links/verify-android-applinks#publish-assetlinks). Untuk keperluan latihan/demo, `example.com` aman untuk testing manual via intent/ADB.

### 3. Via Browser/App Lain

Bisa juga menguji dengan klik link yang sesuai (misal dari notes/email) ke device yang ter-install app ini.

## Cara Menjalankan

1. Buka project di Android Studio
2. Sync Gradle
3. Run `app` di emulator/device (minSdk 24, compileSdk 36, targetSdk 35)
4. Coba salah satu perintah ADB di atas, atau jalankan biasa dari launcher untuk melihat state "no deep link data"

## Catatan

- Menggunakan `launchMode="singleTask"` agar activity tidak dibuat ulang saat menerima deep link.
- `setIntent(intent)` dipanggil di `onNewIntent()` agar `getIntent()` berikutnya konsisten.
- Parsing deep link bersifat defensif (cek null, cek segment count).
- UI menampilkan semua komponen deep link agar mudah di-debug.

## Changelog

- **1.0.0** : Initial DeepLinks sample
- **Upgrade** : Tambah custom schemes (`algokelvin://`, `deeplink://`), improve UI (tampilkan info deep link), refactor parsing dengan logging & defensive checks, update README dokumentasi testing