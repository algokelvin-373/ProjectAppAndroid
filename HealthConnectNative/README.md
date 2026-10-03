# HealthConnectNative

Sample/demo integrating Android Health Connect APIs natively.

## Tech Stack
- Java
- Android Views (XML) + AppCompat
- Health Connect Client (androidx.health.connect)
- ConstraintLayout

## Project Structure
- `app/` - Main module
- `HealthConnectManager.java` - Helper for availability & permission contract
- `MainActivity.java` - UI untuk request permission
- `PermissionsRationaleActivity.java` - Rationale activity (sesuai Health Connect guide)

## Prerequisites
- Android 11+ (API 30+) recommended; app target/min sesuai config
- [Health Connect](https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata) ter-install di emulator/device (untuk testing penuh)

## How to Run
1. Open in Android Studio
2. Sync Gradle
3. Run `app` di emulator/device
4. Pastikan Health Connect tersedia (status akan ditampilkan di UI)
5. Tekan "Request Permission" untuk request READ/WRITE (Steps, Heart Rate, Exercise Session, Total Calories Burned)

## Health Connect Permissions
Defined in Manifest:
- `android.permission.health.READ_HEART_RATE`, `WRITE_HEART_RATE`
- `android.permission.health.READ_STEPS`, `WRITE_STEPS`

Activity alias untuk Android 14+ (`VIEW_PERMISSION_USAGE`) dan rationale untuk Android 13- juga disiapkan.

## Notes
- Minimal example: cek availability, request permissions via `PermissionController.createRequestPermissionResultContract()`
- `PermissionsRationaleActivity` ditambahkan untuk memenuhi kebutuhan Health Connect rationale/usage view
- Gunakan device/emulator dengan Health Connect terpasang untuk hasil maksimal

## Changelog
- Add PermissionsRationaleActivity (fix missing class referenced in manifest)
- Cleanup manifest (remove redundant uses-sdk)
- Update README to match Java implementation