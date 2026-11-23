# Sistem Informasi Manajemen Perikanan

Aplikasi desktop berbasis console untuk mencatat dan mengelola ikan air tawar. Program dibuat dengan Java, `Scanner`, dan `ArrayList`, tanpa database atau framework tambahan. Seluruh kode berada dalam enam file Java pada package `perikanan`; sintaksnya kompatibel dengan JDK 8 atau lebih baru.

## Struktur Proyek

```text
SistemManajemenPerikanan/
|-- Source Packages/
|   `-- perikanan/
|       |-- Main.java
|       |-- Ikan.java
|       |-- Lele.java
|       |-- Nila.java
|       |-- Gurame.java
|       `-- ManajemenIkan.java
|-- Libraries/
`-- README.md
```

NetBeans mengelola folder proyek dan konfigurasi build-nya. Jika dibuat sebagai proyek Java with Ant, NetBeans akan menambahkan berkas konfigurasi pada folder `nbproject`.

## Fitur Aplikasi

1. **Tambah** data dengan ID otomatis, nama, salah satu dari 20 jenis, harga per kilogram, dan stok kilogram.
2. **Tampilkan** seluruh data beserta deskripsi jenis ikan.
3. **Ubah** nama, jenis, harga, dan stok berdasarkan ID.
4. **Hapus** data berdasarkan ID.
5. **Laporan** mencakup tabel data, jumlah data, jumlah jenis unik, total stok, dan nilai stok (`harga/kg x stok`). Ringkasan tetap tampil saat belum ada data.
6. **Keluar** menutup aplikasi dengan pesan penutup.

ID bertambah otomatis dan tidak dipakai ulang setelah data dihapus. Data hanya ada selama aplikasi berjalan, karena penyimpanannya berupa `ArrayList` di memori.

## Daftar Jenis Ikan

Daftar ini disimpan dalam `Ikan.java` dan ditampilkan sebagai pilihan bernomor saat menambah atau mengubah data.

1. Lele
2. Nila
3. Gurame
4. Ikan Mas
5. Patin
6. Bawal Air Tawar
7. Gabus
8. Mujair
9. Belut
10. Sidat
11. Tawes
12. Nilem
13. Sepat Siam
14. Betok
15. Toman
16. Baung
17. Jelawat
18. Belida
19. Arwana
20. Lais

## Fungsi Class

- `Main.java`: program utama, menu console, validasi input dengan `Scanner`, dan pemanggilan operasi manajemen.
- `Ikan.java`: abstract class dengan atribut umum, getter/setter tervalidasi, daftar jenis ikan, dan abstract method `deskripsiJenis()`.
- `Lele.java`: subclass `Ikan` dengan deskripsi khusus lele.
- `Nila.java`: subclass `Ikan` dengan deskripsi khusus nila.
- `Gurame.java`: subclass `Ikan` dengan deskripsi khusus gurame.
- `ManajemenIkan.java`: penyimpanan `ArrayList<Ikan>`, pembuatan ID, operasi tambah/tampil/ubah/hapus, dan perhitungan laporan. Untuk 17 jenis selain Lele, Nila, dan Gurame, kelas ini menggunakan implementasi anonim `Ikan` agar tidak perlu membuat file kelas tambahan.

## Empat Pilar OOP

1. **Enkapsulasi**: atribut `id`, `nama`, `jenis`, `hargaPerKg`, dan `stokKg` di `Ikan` bersifat `private`. Getter dan setter mengatur akses dan menolak data tidak valid, seperti nama kosong, jenis tidak terdaftar, harga tidak positif, atau stok negatif.
2. **Pewarisan**: `Lele`, `Nila`, dan `Gurame` menggunakan `extends Ikan` untuk mewarisi data dan perilaku umum.
3. **Polimorfisme**: ketiga subclass meng-override `deskripsiJenis()`. Data dikelola sebagai referensi `Ikan`, lalu `ikan.deskripsiJenis()` akan memanggil deskripsi sesuai jenis objek.
4. **Abstraksi**: `Ikan` adalah abstract class. Method `deskripsiJenis()` diwajibkan untuk diimplementasikan kelas turunan.

Contoh pemanggilan polimorfik di `ManajemenIkan`:

```java
for (Ikan ikan : daftarIkan) {
	System.out.println(ikan.deskripsiJenis());
}
```

## Membuat Proyek di Apache NetBeans 13

1. Pastikan JDK terpasang dan dikenali NetBeans.
2. Pilih **File > New Project**.
3. Pilih **Java with Ant > Java Application**, lalu klik **Next**.
4. Beri nama `SistemManajemenPerikanan`. Hilangkan centang **Create Main Class** jika pilihan itu tersedia, lalu klik **Finish**.
5. Klik kanan **Source Packages > New > Java Package**, kemudian beri nama package `perikanan`.
6. Buat enam Java Class bernama `Main`, `Ikan`, `Lele`, `Nila`, `Gurame`, dan `ManajemenIkan` di package tersebut. Salin kode dari keenam file sumber di folder `src/perikanan`.
7. Pastikan setiap file dimulai dengan `package perikanan;`. Jika perlu, buka **Project Properties > Run** dan pilih `perikanan.Main` sebagai Main Class.

## Menjalankan Aplikasi

- Klik kanan proyek dan pilih **Run**, atau tekan **F6**.
- Pilih menu angka `1` sampai `6`, lalu ikuti instruksi console.
- Harga dan stok menerima titik atau koma sebagai pemisah desimal. Jangan masukkan pemisah ribuan.
- Data tidak bertahan setelah aplikasi ditutup.

## Contoh Pengujian

1. Tambahkan `Lele Sangkuriang`, jenis nomor 1, harga `25000`, stok `10`.
2. Tambahkan ikan bernama `Nila Merah`, jenis nomor 2, harga `30000`, stok `5`.
3. Tampilkan data dan pastikan ID keduanya berbeda.
4. Ubah ID 1, termasuk jenisnya menjadi nomor 3 (Gurame), harga `26000`, stok `12`. Pastikan deskripsi yang tampil mengikuti jenis baru.
5. Buat laporan. Dengan dua data tersebut, hasilnya 2 data, 2 jenis unik, total stok 17 kg, dan nilai stok Rp 462.000,00.
6. Masukkan ID yang tidak tersedia saat mengubah atau menghapus; program harus menampilkan pesan bahwa data tidak ditemukan.
7. Coba nama kosong, harga nol, stok negatif, serta pilihan menu/jenis di luar rentang; program menolak input tersebut.
8. Hapus salah satu data dan pastikan data lain masih tampil.

## Contoh Tampilan

```text
========================================
 SISTEM INFORMASI MANAJEMEN PERIKANAN
========================================
1. Tambah Data Ikan
2. Tampilkan Data Ikan
3. Ubah Data Ikan
4. Hapus Data Ikan
5. Laporan Data Ikan
6. Keluar
========================================
Masukkan pilihan: 1
Masukkan nama ikan: Lele Sangkuriang

PILIH JENIS IKAN
1. Lele
2. Nila
3. Gurame
...
20. Lais
Masukkan pilihan jenis ikan: 1
Masukkan harga per kilogram (Rp): 25000
Masukkan jumlah stok (kg): 10
Data ikan berhasil ditambahkan dengan ID 1.
```

Contoh ringkasan laporan setelah data di atas dan data Nila dimasukkan:

```text
RINGKASAN LAPORAN
Jumlah data ikan       : 2
Jumlah jenis ikan unik : 2
Total stok             : 15,00 kg
Nilai seluruh stok     : Rp 400.000,00
```

## Dokumentasi Laporan Tugas

### 1. Judul Proyek

Sistem Informasi Manajemen Perikanan Sederhana Berbasis Console dengan Java.

### 2. Latar Belakang

Informasi nama, jenis, harga, dan stok ikan perlu dicatat agar mudah diperiksa dan diperbarui. Pencatatan manual dapat menyulitkan pencarian data serta perhitungan nilai persediaan. Aplikasi ini menyediakan pengelolaan sederhana melalui menu console.

### 3. Rumusan Masalah

1. Bagaimana mencatat data ikan air tawar tanpa database?
2. Bagaimana menambahkan, menampilkan, mengubah, dan menghapus data berdasarkan ID?
3. Bagaimana menerapkan empat pilar OOP dalam program sederhana?

### 4. Tujuan Proyek

Membuat aplikasi Java CLI untuk mengelola data ikan dengan ID otomatis, validasi input, operasi CRUD, dan laporan stok.

### 5. Manfaat Proyek

Aplikasi menjadi media latihan pengelolaan koleksi `ArrayList` dan penerapan OOP. Program juga membantu pencatatan sementara dan perhitungan nilai stok selama sesi aplikasi berjalan.

### 6. Spesifikasi Aplikasi

- Bahasa: Java; IDE sasaran: Apache NetBeans 13.
- Jenis aplikasi: desktop console/CLI.
- Package: `perikanan`; jumlah file Java: enam.
- Input: `Scanner`; penyimpanan: `ArrayList`; database dan framework: tidak digunakan.
- Versi sumber: kompatibel dengan JDK 8 atau lebih baru.

### 7. Penjelasan Fitur Aplikasi

Menu aplikasi menyediakan tambah, tampil, ubah, hapus, laporan, dan keluar. ID dibuat otomatis. Perubahan data mencakup nama, jenis, harga, dan stok. Laporan menampilkan seluruh ikan dan ringkasan jumlah data, jenis unik, total kilogram, serta nilai persediaan. Input kosong/tidak valid dan ID yang tidak ditemukan ditangani dengan pesan yang sesuai.

### 8. Daftar 20 Jenis Ikan Air Tawar

Lele, Nila, Gurame, Ikan Mas, Patin, Bawal Air Tawar, Gabus, Mujair, Belut, Sidat, Tawes, Nilem, Sepat Siam, Betok, Toman, Baung, Jelawat, Belida, Arwana, dan Lais. Daftar bernomor lengkap terdapat pada bagian **Daftar Jenis Ikan** di atas dan didefinisikan di `Ikan.java`.

### 9. Penjelasan Setiap Class Java

`Main` menangani menu dan input. `Ikan` menyimpan atribut serta perilaku umum. `Lele`, `Nila`, dan `Gurame` memberi contoh turunan dengan karakteristik masing-masing. `ManajemenIkan` mengelola koleksi, CRUD, ID, serta perhitungan laporan.

### 10. Struktur Folder Proyek

Enam file sumber berada di `src/perikanan/`, yang ditampilkan NetBeans sebagai **Source Packages > perikanan**. Proyek juga memiliki **Libraries** yang dikelola IDE; tidak ada file Java tambahan.

### 11. Penerapan Encapsulation

Atribut di `Ikan` dibuat `private` dan diakses melalui getter/setter. Setter memvalidasi ID positif, nama tidak kosong, jenis termasuk daftar, harga lebih dari nol, dan stok tidak negatif. Validasi ini mencegah nilai yang tidak sesuai tersimpan pada objek.

### 12. Penerapan Inheritance

Class `Lele`, `Nila`, dan `Gurame` mewarisi `Ikan` menggunakan `extends`. Constructor turunan memanggil constructor induk melalui `super(...)`.

### 13. Penerapan Polymorphism

`Lele`, `Nila`, dan `Gurame` meng-override method `deskripsiJenis()`. Saat objek disimpan sebagai `Ikan` dan method tersebut dipanggil, Java menjalankan implementasi yang sesuai dengan class objek.

### 14. Penerapan Abstraction

`Ikan` dideklarasikan `abstract` sehingga tidak dibuat sebagai objek langsung. Abstract method `deskripsiJenis()` memaksa class turunan menyediakan deskripsinya.

### 15. Panduan Menjalankan di Apache NetBeans 13

Buat **Java with Ant > Java Application**, buat package `perikanan`, lalu masukkan keenam class dari folder sumber. Tetapkan `perikanan.Main` sebagai Main Class bila diperlukan. Jalankan proyek menggunakan **Run** atau **F6**.

### 16. Contoh Pengujian Program

Tambahkan Lele dengan harga Rp 25.000/kg dan stok 10 kg serta Nila dengan harga Rp 30.000/kg dan stok 5 kg. Laporan yang diharapkan: 2 data, 2 jenis unik, 15 kg, dan nilai stok Rp 400.000,00. Ubah Lele menjadi Gurame seharga Rp 26.000/kg dengan stok 12 kg; laporan kemudian menunjukkan 17 kg dan Rp 462.000,00. Uji juga ID tidak ditemukan, pilihan di luar rentang, nama kosong, harga nol, dan stok negatif.

### 17. Kesimpulan

Aplikasi memenuhi kebutuhan dasar pengelolaan data ikan air tawar dalam satu sesi program. Enkapsulasi, pewarisan, polimorfisme, abstraksi, dan `ArrayList` digunakan tanpa database. Karena data disimpan di memori, data akan hilang saat aplikasi ditutup.
