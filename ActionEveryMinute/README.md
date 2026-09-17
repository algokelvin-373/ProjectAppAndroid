# Action Every Minute

Project Android sederhana untuk menjalankan aksi berulang setiap 5 detik. Interval dibuat singkat agar mudah diamati saat belajar.

## Tujuan

- Memahami cara menjalankan proses berulang dengan `Timer` dan `TimerTask`.
- Memahami cara memperbarui tampilan dari background thread menggunakan `runOnUiThread`.
- Memahami cara mengontrol timer dengan tombol `Start`, `Stop`, dan `Reset`.
- Memahami pentingnya menghentikan timer saat lifecycle Activity berakhir.

## Konsep yang Dipelajari

- ViewBinding untuk mengakses komponen layout.
- `Timer.scheduleAtFixedRate()` untuk menjalankan aksi berkala.
- `runOnUiThread` untuk update UI dari task background.
- `timer.cancel()` untuk menghentikan proses timer.
- Lifecycle `onDestroy()` untuk membersihkan proses yang masih berjalan.

## Langkah Implementasi

1. Buat layout berisi teks status dan tiga tombol: `Start`, `Stop`, dan `Reset`.
2. Jalankan `Timer` saat tombol `Start` ditekan.
3. Update teks setiap 5 detik melalui `runOnUiThread`.
4. Hentikan timer saat tombol `Stop` ditekan.
5. Hentikan timer dan kembalikan hitungan ke awal saat tombol `Reset` ditekan.
6. Panggil `timer.cancel()` di `onDestroy()` agar timer tidak berjalan setelah Activity ditutup.

## Demo

- Saat aplikasi dibuka, teks menampilkan status awal.
- Tekan `Start` untuk mulai menghitung setiap 5 detik.
- Tekan `Stop` untuk menghentikan hitungan sementara.
- Tekan `Reset` untuk menghentikan timer dan mengembalikan teks ke status awal.

## Catatan Lifecycle

Timer berjalan di luar alur utama UI. Jika Activity ditutup tetapi timer tidak dihentikan, task masih bisa mencoba mengakses UI yang sudah tidak aktif. Karena itu, timer perlu dihentikan menggunakan `timer.cancel()` di `onDestroy()`.

## Version

- 1.0.0 : Create APK Project
