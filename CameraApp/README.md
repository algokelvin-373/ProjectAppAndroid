# Camera App

Project Android sederhana untuk belajar menampilkan preview kamera menggunakan `SurfaceView` dan API kamera lama `android.hardware.Camera`.

## Tujuan

- Memahami cara meminta permission kamera.
- Memahami cara menampilkan preview kamera dengan `SurfaceView`.
- Memahami lifecycle `SurfaceHolder` saat kamera dibuka dan dilepas.
- Memahami cara mengambil gambar sederhana dengan tombol `Capture`.

## Konsep yang Dipelajari

- Runtime permission untuk `CAMERA`.
- `SurfaceHolder.Callback` untuk menangani `surfaceCreated`, `surfaceChanged`, dan `surfaceDestroyed`.
- `Camera.open()`, `setPreviewDisplay()`, `startPreview()`, dan `release()`.
- Penyimpanan gambar ke folder private aplikasi menggunakan `getExternalFilesDir()`.
- Release kamera di `onPause()` agar resource kamera tidak tertahan.

## Langkah Implementasi

1. Tambahkan permission `CAMERA` di `AndroidManifest.xml`.
2. Buat layout berisi `SurfaceView`, tombol `Capture`, dan teks informasi rotasi.
3. Minta permission kamera saat aplikasi dibuka.
4. Jalankan preview saat permission sudah diberikan dan surface siap.
5. Ambil gambar dengan `camera.takePicture()`.
6. Simpan gambar ke folder private aplikasi.
7. Lepaskan kamera saat `onPause()` atau `surfaceDestroyed()`.

## Catatan Lifecycle

Kamera harus dilepas ketika Activity berhenti agar aplikasi lain tetap bisa memakai kamera. Gunakan pengecekan `null` sebelum memanggil `stopPreview()` atau `release()` supaya aplikasi tidak crash ketika kamera belum terbuka.

## Catatan API

Project ini memakai `android.hardware.Camera`, yaitu API lama. Ini tetap berguna untuk memahami konsep dasar preview kamera. Untuk project produksi atau tutorial lanjutan, gunakan CameraX.

## Version

- 1.0.0 : Create APK Project
