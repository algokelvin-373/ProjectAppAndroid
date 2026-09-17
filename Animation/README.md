# Animation

Project Android sederhana untuk belajar animasi dasar menggunakan `ObjectAnimator`.

## Tujuan

- Memahami cara menjalankan animasi pada `View`.
- Memahami properti animasi dasar seperti `translationX`, `alpha`, `rotation`, `scaleX`, dan `scaleY`.
- Memahami cara membuat controller animasi agar kode lebih mudah dipakai ulang.
- Memahami penggunaan `Interpolator` untuk membuat gerakan lebih halus.

## Konsep yang Dipelajari

- ViewBinding untuk mengakses komponen layout.
- `ObjectAnimator.ofFloat()` untuk mengubah properti `View`.
- `AccelerateDecelerateInterpolator` untuk efek animasi yang lebih natural.
- Tombol `Start`, `Reverse`, dan `Reset` untuk mengontrol animasi.
- Demo tambahan untuk `Alpha`, `Rotation`, dan `Scale`.

## Daftar Demo

- `Translation01`: menggerakkan teks ke kanan.
- `Translation02`: menggerakkan teks ke kiri.
- `Alpha`: mengubah transparansi teks.
- `Rotation`: memutar teks.
- `Scale`: memperbesar teks.

## Langkah Implementasi

1. Buat layout berisi `TextView` dan tombol kontrol animasi.
2. Buat `AnimatorController` untuk menampung fungsi animasi reusable.
3. Jalankan animasi menggunakan `ObjectAnimator.ofFloat()`.
4. Tambahkan `Interpolator` agar animasi terlihat lebih halus.
5. Tambahkan tombol `Reset` untuk mengembalikan `View` ke kondisi awal.

## Catatan ObjectAnimator

`ObjectAnimator` bekerja dengan mengubah properti milik `View`, misalnya `translationX`, `alpha`, `rotation`, `scaleX`, dan `scaleY`. Nilai properti akan berubah selama durasi animasi yang ditentukan.

## Version

- 1.0.0 : Create APK Project
