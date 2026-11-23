
package perikanan;

// Mengimpor ArrayList untuk menyimpan kumpulan data ikan.
// Mengimpor HashSet dan Set untuk menghitung jenis ikan yang unik.
// Mengimpor Locale untuk mengatur format angka sesuai Indonesia.
// Mengimpor class untuk membaca dan menulis data ke file.
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Class ManajemenIkan digunakan untuk mengelola seluruh data ikan.
public class ManajemenIkan {

    // ArrayList digunakan untuk menyimpan objek ikan.
    // final berarti variabel daftarIkan tidak bisa diarahkan ke objek lain.
    private final ArrayList<Ikan> daftarIkan = new ArrayList<>();

    // Menentukan ID otomatis yang dimulai dari angka 1.
    private int idBerikutnya = 1;

    // Menentukan nama file untuk menyimpan data ikan secara permanen.
    private static final String FILE_DATA = "data_ikan.txt";

    // Menentukan nama file untuk menyimpan riwayat transaksi secara permanen.
    private static final String FILE_RIWAYAT = "riwayat_transaksi.txt";

    // Menentukan format tanggal yang ditampilkan kepada pengguna.
    private static final DateTimeFormatter FORMAT_TANGGAL =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Mengatur format angka menggunakan standar Indonesia.
    private static final Locale LOKAL_INDONESIA =
            Locale.forLanguageTag("id-ID");

    // Menyimpan seluruh transaksi secara berurutan menggunakan ArrayList.
    private final ArrayList<Transaksi> riwayatTransaksi = new ArrayList<>();

    // Constructor membaca data lama ketika aplikasi pertama kali dijalankan.
    public ManajemenIkan() {
        muatData();
        muatRiwayat();
    }

    // Mengambil daftar jenis ikan dari class Ikan.
    public String[] getDaftarJenisIkan() {
        return Ikan.getDaftarJenisIkan();
    }

    // Method untuk menambahkan data ikan baru.
    public void tambahIkan(String nama, int nomorJenis,
                           double hargaPerKg, double stokKg) {

        // Mengambil daftar pilihan jenis ikan.
        String[] jenisIkan = getDaftarJenisIkan();

        // Memeriksa apakah nomor jenis ikan sesuai dengan pilihan.
        if (nomorJenis < 1 || nomorJenis > jenisIkan.length) {
            System.out.println("Pilihan jenis ikan tidak tersedia.");
            return;
        }

        try {
            // Memastikan seluruh input memiliki nilai yang valid sebelum diproses.
            validasiInputTambah(nama, hargaPerKg, stokKg);

            // Mengambil jenis ikan berdasarkan nomor pilihan pengguna.
            String jenis = jenisIkan[nomorJenis - 1];

            // Mencari ikan dengan kombinasi nama dan jenis yang sama.
            Ikan ikanLama = cariIkanByNamaDanJenis(nama, jenis);

            // Jika data sudah ada, stok ditambahkan tanpa mengubah harga lama.
            if (ikanLama != null) {
                ikanLama.setStokKg(ikanLama.getStokKg() + stokKg);
                // Batch stok baru dianggap sebagai stok terbaru mulai hari ini.
                ikanLama.setTanggalMasuk(LocalDate.now());
                catatTransaksi(ikanLama, "Tambah Stok", null, null);
                simpanSemuaData();
                System.out.println("Ikan sudah terdaftar. Stok berhasil ditambahkan.");
                return;
            }

            // Membuat objek ikan baru menggunakan ID otomatis.
            Ikan ikanBaru = buatIkan(
                    idBerikutnya, nama,
                    jenis,
                    hargaPerKg, stokKg
            );

            // Menambahkan objek ikan ke dalam ArrayList.
            daftarIkan.add(ikanBaru);

            // Menambah ID untuk data ikan berikutnya.
            idBerikutnya++;

            // Mencatat transaksi penambahan data baru.
            catatTransaksi(ikanBaru, "Tambah Data", null, null);

            // Menyimpan data ikan dan riwayat agar tidak hilang saat aplikasi ditutup.
            simpanSemuaData();

            // Menampilkan pesan bahwa data berhasil ditambahkan.
            System.out.println("Data ikan berhasil ditambahkan dengan ID "
                    + ikanBaru.getId() + ".");

        } catch (IllegalArgumentException ex) {
            // Menangkap kesalahan jika data yang dimasukkan tidak valid.
            System.out.println("Data tidak valid: " + ex.getMessage());
        }
    }

    // Method untuk membuat objek ikan berdasarkan jenisnya.
    private Ikan buatIkan(int id, String nama, String jenis,
                          double hargaPerKg, double stokKg) {

        return buatIkan(id, nama, jenis, hargaPerKg, stokKg, LocalDate.now());
    }

    // Membuat objek ikan sambil mempertahankan tanggal masuk dari penyimpanan.
    private Ikan buatIkan(int id, String nama, String jenis,
                          double hargaPerKg, double stokKg,
                          LocalDate tanggalMasuk) {

        switch (jenis) {
            case "Ikan Tongkol":
                return new IkanTongkol(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Cakalang":
                return new IkanCakalang(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Tuna":
                return new IkanTuna(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Kembung":
                return new IkanKembung(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Tenggiri":
                return new IkanTenggiri(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Bawal Putih":
                return new IkanBawalPutih(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Kakap":
                return new IkanKakap(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Kerapu":
                return new IkanKerapu(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Baronang":
                return new IkanBaronang(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Layang":
                return new IkanLayang(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Selar":
                return new IkanSelar(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Teri":
                return new IkanTeri(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Bandeng":
                return new IkanBandeng(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Manyung":
                return new IkanManyung(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Pari":
                return new IkanPari(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Cumi":
                return new IkanCumi(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Lemuru":
                return new IkanLemuru(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Dorang":
                return new IkanDorang(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Tongkol Komo":
                return new IkanTongkolKomo(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            case "Ikan Sarden":
                return new IkanSarden(id, nama, hargaPerKg, stokKg, tanggalMasuk);
            default:
                throw new IllegalArgumentException("Jenis ikan tidak terdaftar: " + jenis);
        }
    }

    // Method untuk menampilkan seluruh data ikan.
    public void tampilkanIkan() {

        // Memeriksa apakah daftar ikan masih kosong.
        if (daftarIkan.isEmpty()) {
            System.out.println("Belum ada data ikan.");
            return;
        }

        // Menampilkan judul tabel data ikan.
        System.out.println("\nDATA IKAN");

        // Menampilkan nama kolom tabel.
        System.out.println("ID   | Nama                 | Jenis              | Harga/kg          | Stok Terbaru | Tgl Masuk  | Segar (hari) | Status Kesegaran | Terakhir Diubah | Deskripsi");

        // Menampilkan garis pembatas tabel.
        System.out.println("-----+----------------------+--------------------+-------------------+--------------+------------+--------------+------------------+-----------------+------------------------------");

        // Melakukan perulangan untuk membaca setiap objek ikan.
        for (Ikan ikan : daftarIkan) {

            // Menampilkan data ikan dengan format tabel.
            // printf digunakan untuk mengatur posisi dan format angka.
            System.out.printf(LOKAL_INDONESIA,
                    "%-4d | %-20s | %-18s | Rp %,13.2f | %,12.2f | %-10s | %-12d | %-16s | %-15s | %s%n",
                    ikan.getId(),
                    ikan.getNama(),
                    ikan.getJenis(),
                    ikan.getHargaPerKg(),
                    ikan.getStokTerbaru(),
                    ikan.getTanggalMasuk().format(FORMAT_TANGGAL),
                    ikan.getMasaSegarHari(),
                    ikan.statusKesegaran(),
                    tanggalTerakhirTransaksi(ikan.getId()),
                    ikan.deskripsiJenis()
            );
        }
    }

    // Method untuk mencari data ikan berdasarkan ID.
    public Ikan cariIkanById(int id) {

        // Memeriksa setiap data ikan dalam ArrayList.
        for (Ikan ikan : daftarIkan) {

            // Jika ID ikan sama dengan ID yang dicari.
            if (ikan.getId() == id) {
                return ikan;
            }
        }

        // Mengembalikan null jika data tidak ditemukan.
        return null;
    }

    // Mengembalikan salinan daftar ikan untuk pilihan pada form transaksi.
    public Ikan[] getDaftarIkan() {
        return daftarIkan.toArray(new Ikan[0]);
    }

    // Mengurangi stok ikan dan mencatatnya sebagai transaksi pengeluaran.
    public boolean kurangiStokIkan(int id, double jumlahPengurangan) {
        Ikan ikan = cariIkanById(id);
        if (ikan == null) {
            System.out.println("Data ikan tidak ditemukan.");
            return false;
        }
        if (Double.isNaN(jumlahPengurangan)
                || Double.isInfinite(jumlahPengurangan)
                || jumlahPengurangan <= 0) {
            System.out.println("Jumlah pengurangan harus lebih besar dari 0.");
            return false;
        }
        if (jumlahPengurangan > ikan.getStokKg()) {
            System.out.println("Stok tidak mencukupi. Stok tersedia hanya "
                    + ikan.getStokKg() + " kg.");
            return false;
        }

        double sisaStok = ikan.getStokKg() - jumlahPengurangan;
        if (sisaStok < 0) {
            System.out.println("Pengurangan stok dibatalkan karena hasilnya negatif.");
            return false;
        }
        ikan.setStokKg(sisaStok);
        catatTransaksi(ikan, "Pengurangan Stok", null, null);
        simpanSemuaData();
        System.out.println("Stok ikan berhasil dikurangi. Sisa stok: "
                + sisaStok + " kg.");
        return true;
    }

    // Method untuk mengubah data ikan berdasarkan ID.
    public boolean ubahIkan(int id, String nama, int nomorJenis,
                            double hargaPerKg, double stokKg) {

        // Mencari ikan berdasarkan ID.
        Ikan ikan = cariIkanById(id);

        // Jika data tidak ditemukan, proses dihentikan.
        if (ikan == null) {
            System.out.println("Data dengan ID " + id + " tidak ditemukan.");
            return false;
        }

        // Mengambil daftar jenis ikan yang tersedia.
        String[] jenisIkan = getDaftarJenisIkan();

        // Memeriksa apakah nomor jenis ikan valid.
        if (nomorJenis < 1 || nomorJenis > jenisIkan.length) {
            System.out.println("Pilihan jenis ikan tidak tersedia.");
            return false;
        }

        // Melakukan validasi terhadap data yang akan diubah.
        // Nama tidak boleh kosong.
        // Harga harus lebih dari 0.
        // Stok tidak boleh negatif.
        // NaN dan Infinity juga tidak diperbolehkan.
        if (nama == null || nama.trim().isEmpty()
                || Double.isNaN(hargaPerKg)
                || Double.isInfinite(hargaPerKg)
                || hargaPerKg <= 0
                || Double.isNaN(stokKg)
                || Double.isInfinite(stokKg)
                || stokKg < 0) {

            System.out.println("Data tidak valid. Nama wajib diisi, "
                    + "harga harus lebih dari 0, dan stok tidak boleh negatif.");
            return false;
        }

        // Mencegah perubahan nama dan jenis menjadi identitas data yang sudah ada.
        Ikan ikanDuplikat = cariIkanByNamaDanJenis(nama, jenisIkan[nomorJenis - 1]);
        if (ikanDuplikat != null && ikanDuplikat != ikan) {
            System.out.println("Ikan dengan nama dan jenis tersebut sudah terdaftar.");
            return false;
        }

        try {
            // Menyimpan harga lama untuk riwayat jika harga mengalami perubahan.
            String namaLama = ikan.getNama();
            String jenisLama = ikan.getJenis();
            double hargaLama = ikan.getHargaPerKg();
            double stokLama = ikan.getStokKg();

            // Membuat objek ikan baru dengan data yang sudah diperbarui.
            Ikan ikanBaru = buatIkan(
                    id, nama,
                    jenisIkan[nomorJenis - 1],
                    hargaPerKg, stokKg, ikan.getTanggalMasuk()
            );

            // Mengganti data lama dengan data ikan yang baru.
            daftarIkan.set(daftarIkan.indexOf(ikan), ikanBaru);

            // Mencatat perubahan harga jika harga lama dan harga baru berbeda.
            if (Double.compare(hargaLama, hargaPerKg) != 0) {
                catatTransaksi(ikanBaru, "Ubah Harga", hargaLama, hargaPerKg);
            } else if (!namaLama.equals(ikanBaru.getNama())
                    || !jenisLama.equals(ikanBaru.getJenis())
                    || Double.compare(stokLama, ikanBaru.getStokKg()) != 0) {
                // Tetap mencatat tanggal jika data berubah tanpa perubahan harga.
                catatTransaksi(ikanBaru, "Ubah Data", null, null);
            }

            // Menyimpan perubahan data ke file.
            simpanSemuaData();

            // Menampilkan pesan jika data berhasil diubah.
            System.out.println("Data ikan dengan ID " + id
                    + " berhasil diubah.");

            return true;

        } catch (IllegalArgumentException ex) {
            // Menangkap kesalahan jika data baru tidak valid.
            System.out.println("Data tidak valid: " + ex.getMessage());
            return false;
        }
    }

    // Mengubah stok saja tanpa mengubah nama, jenis, atau harga ikan.
    public boolean ubahStokIkan(int id, double stokKg) {
        Ikan ikan = cariIkanById(id);

        // Memastikan data dengan ID yang dimasukkan tersedia.
        if (ikan == null) {
            System.out.println("Data dengan ID " + id + " tidak ditemukan.");
            return false;
        }

        try {
            double stokLama = ikan.getStokKg();

            // Setter melakukan validasi agar stok tidak negatif.
            ikan.setStokKg(stokKg);
            if (Double.compare(stokLama, stokKg) != 0) {
                // Mencatat tanggal terakhir jika stok diubah melalui menu ubah data.
                catatTransaksi(ikan, "Ubah Stok", null, null);
            }
            simpanSemuaData();
            System.out.println("Stok ikan dengan ID " + id + " berhasil diubah.");
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println("Data tidak valid: " + ex.getMessage());
            return false;
        }
    }

    // Mengubah harga saja tanpa mengubah nama, jenis, atau stok ikan.
    public boolean ubahHargaIkan(int id, double hargaPerKg) {
        Ikan ikan = cariIkanById(id);

        // Memastikan data dengan ID yang dimasukkan tersedia.
        if (ikan == null) {
            System.out.println("Data dengan ID " + id + " tidak ditemukan.");
            return false;
        }

        try {
            // Menyimpan harga lama untuk dicatat di riwayat perubahan harga.
            double hargaLama = ikan.getHargaPerKg();

            // Setter melakukan validasi agar harga lebih besar dari nol.
            ikan.setHargaPerKg(hargaPerKg);
            catatTransaksi(ikan, "Ubah Harga", hargaLama, hargaPerKg);
            simpanSemuaData();
            System.out.println("Harga ikan dengan ID " + id + " berhasil diubah.");
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println("Data tidak valid: " + ex.getMessage());
            return false;
        }
    }

    // Method untuk menghapus data ikan berdasarkan ID.
    public boolean hapusIkan(int id) {

        // Mencari data ikan berdasarkan ID.
        Ikan ikan = cariIkanById(id);

        // Jika data tidak ditemukan, proses dihentikan.
        if (ikan == null) {
            System.out.println("Data dengan ID " + id + " tidak ditemukan.");
            return false;
        }

        // Menghapus objek ikan dari ArrayList.
        daftarIkan.remove(ikan);

        // Menyimpan isi ArrayList terbaru setelah data dihapus.
        simpanSemuaData();

        // Menampilkan pesan bahwa data berhasil dihapus.
        System.out.println("Data ikan dengan ID " + id
                + " berhasil dihapus.");

        return true;
    }

    // Method untuk menampilkan laporan dan ringkasan data ikan.
    public void tampilkanLaporan() {

        // Menampilkan seluruh data ikan terlebih dahulu.
        tampilkanIkan();

        // HashSet digunakan agar jenis ikan yang sama
        // hanya dihitung satu kali.
        Set<String> jenisTersimpan = new HashSet<>();

        // Variabel untuk menyimpan total seluruh stok ikan.
        double totalStokKg = 0;

        // Variabel untuk menghitung nilai seluruh stok ikan.
        double nilaiTotalStok = 0;

        // Melakukan perulangan untuk menghitung seluruh data ikan.
        for (Ikan ikan : daftarIkan) {

            // Menambahkan jenis ikan ke dalam HashSet.
            jenisTersimpan.add(ikan.getJenis());

            // Menjumlahkan seluruh stok ikan.
            totalStokKg += ikan.getStokKg();

            // Menghitung nilai stok berdasarkan harga dikali jumlah stok.
            nilaiTotalStok += ikan.getHargaPerKg() * ikan.getStokKg();
        }

        // Menampilkan judul ringkasan laporan.
        System.out.println("\nRINGKASAN LAPORAN");

        // Menampilkan jumlah seluruh data ikan.
        System.out.println("Jumlah data ikan       : " + daftarIkan.size());

        // Menampilkan jumlah jenis ikan yang berbeda.
        System.out.println("Jumlah jenis ikan unik : " + jenisTersimpan.size());

        // Menampilkan total stok ikan dalam kilogram.
        System.out.printf(LOKAL_INDONESIA,
                "Total stok             : %,.2f kg%n", totalStokKg);

        // Menampilkan nilai keseluruhan stok dalam rupiah.
        System.out.printf(LOKAL_INDONESIA,
                "Nilai seluruh stok     : Rp %,.2f%n", nilaiTotalStok);
    }

    // Menampilkan seluruh riwayat transaksi berdasarkan urutan pencatatan.
    public void tampilkanRiwayatTransaksi() {
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada riwayat transaksi.");
            return;
        }

        System.out.println("\nRIWAYAT TRANSAKSI");
        System.out.println("ID   | Nama Ikan           | Jenis Transaksi | Tanggal    | Harga Lama       | Harga Baru");
        System.out.println("-----+---------------------+-----------------+------------+------------------+------------------");

        for (Transaksi transaksi : riwayatTransaksi) {
            String hargaLama = formatHargaRiwayat(transaksi.getHargaLama());
            String hargaBaru = formatHargaRiwayat(transaksi.getHargaBaru());

            System.out.printf(LOKAL_INDONESIA,
                    "%-4d | %-19s | %-15s | %-10s | %-16s | %-16s%n",
                    transaksi.getIdIkan(),
                    transaksi.getNamaIkan(),
                    transaksi.getJenisTransaksi(),
                    transaksi.getTanggal().format(FORMAT_TANGGAL),
                    hargaLama,
                    hargaBaru);
        }
    }

    // Menyediakan data transaksi terstruktur untuk tampilan tabel pada GUI.
    public List<DataTransaksi> getRiwayatTransaksiData() {
        ArrayList<DataTransaksi> hasil = new ArrayList<>();
        for (Transaksi transaksi : riwayatTransaksi) {
            hasil.add(new DataTransaksi(
                    transaksi.getIdIkan(), transaksi.getNamaIkan(),
                    transaksi.getJenisTransaksi(), transaksi.getTanggal(),
                    transaksi.getHargaLama(), transaksi.getHargaBaru()));
        }
        return hasil;
    }

    // Memastikan input penambahan ikan memiliki nilai yang valid.
    private void validasiInputTambah(String nama, double hargaPerKg, double stokKg) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama ikan tidak boleh kosong.");
        }
        if (Double.isNaN(hargaPerKg) || Double.isInfinite(hargaPerKg)
                || hargaPerKg <= 0) {
            throw new IllegalArgumentException("Harga harus lebih besar dari 0.");
        }
        if (Double.isNaN(stokKg) || Double.isInfinite(stokKg) || stokKg < 0) {
            throw new IllegalArgumentException("Stok tidak boleh kurang dari 0.");
        }
    }

    // Mencari ikan berdasarkan nama dan jenis tanpa membedakan huruf besar kecil.
    private Ikan cariIkanByNamaDanJenis(String nama, String jenis) {
        String namaDicari = nama.trim();
        String jenisDicari = jenis.trim();

        for (Ikan ikan : daftarIkan) {
            if (ikan.getNama().trim().equalsIgnoreCase(namaDicari)
                    && ikan.getJenis().trim().equalsIgnoreCase(jenisDicari)) {
                return ikan;
            }
        }
        return null;
    }

    // Mengambil tanggal transaksi terakhir untuk satu ID ikan.
    private String tanggalTerakhirTransaksi(int idIkan) {
        LocalDate tanggalTerakhir = null;

        // Riwayat dibaca dari awal agar tanggal paling akhir dapat ditemukan.
        for (Transaksi transaksi : riwayatTransaksi) {
            if (transaksi.getIdIkan() == idIkan
                    && (tanggalTerakhir == null
                    || transaksi.getTanggal().isAfter(tanggalTerakhir))) {
                tanggalTerakhir = transaksi.getTanggal();
            }
        }

        // Data lama tanpa riwayat ditampilkan dengan tanda strip.
        if (tanggalTerakhir == null) {
            return "-";
        }
        return tanggalTerakhir.format(FORMAT_TANGGAL);
    }

    // Menambahkan satu transaksi ke ArrayList riwayat.
    private void catatTransaksi(Ikan ikan, String jenisTransaksi,
                                Double hargaLama, Double hargaBaru) {
        riwayatTransaksi.add(new Transaksi(
                ikan.getId(), ikan.getNama(), jenisTransaksi,
                LocalDate.now(), hargaLama, hargaBaru));
    }

    // Mengubah harga menjadi teks untuk ditampilkan pada riwayat.
    private String formatHargaRiwayat(Double harga) {
        if (harga == null) {
            return "-";
        }
        return String.format(LOKAL_INDONESIA, "Rp %,.2f", harga);
    }

    // Mengubah jenis lama menjadi salah satu jenis baru saat membaca data.
    private String normalisasiJenisData(String nama, String jenis) {
        for (String jenisBaru : getDaftarJenisIkan()) {
            if (jenisBaru.equalsIgnoreCase(jenis.trim())) {
                return jenisBaru;
            }
        }

        // Data versi lama hanya menyimpan kategori air asin atau air tawar.
        // Nama ikan yang dikenal dipetakan ke class baru agar data lama tetap terbaca.
        String namaKecil = nama == null ? "" : nama.trim().toLowerCase(Locale.ROOT);
        if (namaKecil.contains("tongkol")) return "Ikan Tongkol";
        if (namaKecil.contains("pari")) return "Ikan Pari";
        if (namaKecil.contains("bawal")) return "Ikan Bawal Putih";
        if (namaKecil.contains("patin") || namaKecil.contains("manyung")) {
            return "Ikan Manyung";
        }
        if (namaKecil.contains("teri")) return "Ikan Teri";
        if (namaKecil.contains("salmon") || namaKecil.contains("sarden")) {
            return "Ikan Sarden";
        }
        if (namaKecil.contains("udang") || namaKecil.contains("cumi")) {
            return "Ikan Cumi";
        }
        if ("Ikan Air Tawar".equalsIgnoreCase(jenis.trim())) {
            return "Ikan Bandeng";
        }
        if ("Ikan Air Asin".equalsIgnoreCase(jenis.trim())) {
            return "Ikan Tongkol";
        }
        return null;
    }

    // Membaca seluruh data ikan dari file penyimpanan.
    private void muatData() {
        File fileAktif = new File(FILE_DATA).getAbsoluteFile();
        muatDataDariFile(fileAktif);

        // Saat dijalankan dari workspace induk, baca juga file milik project.
        File fileProject = new File("tugas terakhir pbo", FILE_DATA)
                .getAbsoluteFile();
        if (!fileAktif.equals(fileProject)) {
            muatDataDariFile(fileProject);
        }
    }

    // Membaca satu file data dan menambahkan seluruh baris valid ke daftar ikan.
    private void muatDataDariFile(File file) {
        if (!file.exists()) {
            return;
        }

        try (BufferedReader pembaca = new BufferedReader(new FileReader(file))) {
            String baris;

            // Membaca file baris demi baris sampai mencapai akhir file.
            while ((baris = pembaca.readLine()) != null) {
                String[] bagian = baris.split("\\|", -1);

                // Data baru memiliki enam bagian; data lama lima bagian tetap didukung.
                if (bagian.length != 5 && bagian.length != 6) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(bagian[0]);
                    String nama = bagian[1];
                    String jenis = normalisasiJenisData(bagian[1], bagian[2]);
                    if (jenis == null) {
                        continue;
                    }
                    double hargaPerKg = Double.parseDouble(bagian[3]);
                    double stokKg = Double.parseDouble(bagian[4]);
                    LocalDate tanggalMasuk = bagian.length == 6
                            ? LocalDate.parse(bagian[5]) : LocalDate.now();

                    // Mencari data lama agar data duplikat dari file ikut digabung.
                    Ikan ikanLama = cariIkanByNamaDanJenis(nama, jenis);
                    if (ikanLama == null) {
                        // Jika dua file memiliki ID yang sama, buat ID baru
                        // agar pilihan pada form pengurangan tetap menunjuk data yang benar.
                        int idTersedia = id;
                        while (cariIkanById(idTersedia) != null) {
                            idTersedia = idBerikutnya++;
                        }
                        // Membuat kembali objek ikan dan memasukkannya ke ArrayList.
                        daftarIkan.add(buatIkan(
                                idTersedia, nama, jenis, hargaPerKg, stokKg, tanggalMasuk));
                        if (idTersedia >= idBerikutnya) {
                            idBerikutnya = idTersedia + 1;
                        }
                    } else {
                        // Menambahkan stok duplikat dan mempertahankan harga pertama.
                        ikanLama.setStokKg(ikanLama.getStokKg() + stokKg);
                        if (tanggalMasuk.isAfter(ikanLama.getTanggalMasuk())) {
                            ikanLama.setTanggalMasuk(tanggalMasuk);
                        }
                    }

                    // Menentukan ID berikutnya agar tidak sama dengan data lama.
                    if (id >= idBerikutnya) {
                        idBerikutnya = id + 1;
                    }
                } catch (IllegalArgumentException ex) {
                    // Melewati baris yang rusak atau tidak valid.
                }
            }
        } catch (IOException ex) {
            System.out.println("Data lama tidak dapat dibaca: " + ex.getMessage());
        }
    }

    // Membaca riwayat transaksi dari file penyimpanan.
    private void muatRiwayat() {
        File file = new File(FILE_RIWAYAT);

        // Jika file belum ada, riwayat dimulai dalam keadaan kosong.
        if (!file.exists()) {
            return;
        }

        try (BufferedReader pembaca = new BufferedReader(new FileReader(file))) {
            String baris;

            while ((baris = pembaca.readLine()) != null) {
                String[] bagian = baris.split("\\|", -1);

                // Setiap baris riwayat harus memiliki enam bagian data.
                if (bagian.length != 6) {
                    continue;
                }

                try {
                    int idIkan = Integer.parseInt(bagian[0]);
                    String namaIkan = bagian[1];
                    String jenisTransaksi = bagian[2];
                    LocalDate tanggal = LocalDate.parse(bagian[3]);
                    Double hargaLama = bagian[4].isEmpty()
                            ? null : Double.parseDouble(bagian[4]);
                    Double hargaBaru = bagian[5].isEmpty()
                            ? null : Double.parseDouble(bagian[5]);

                    riwayatTransaksi.add(new Transaksi(
                            idIkan, namaIkan, jenisTransaksi,
                            tanggal, hargaLama, hargaBaru));
                } catch (NumberFormatException | DateTimeParseException ex) {
                    // Melewati baris riwayat yang rusak atau tidak valid.
                }
            }
        } catch (IOException ex) {
            System.out.println("Riwayat transaksi tidak dapat dibaca: "
                    + ex.getMessage());
        }
    }

    // Menyimpan data ikan dan riwayat transaksi secara bersamaan.
    private void simpanSemuaData() {
        simpanData();
        simpanRiwayat();
    }

    // Menyimpan seluruh riwayat transaksi ke file.
    private void simpanRiwayat() {
        try (BufferedWriter penulis = new BufferedWriter(new FileWriter(FILE_RIWAYAT))) {
            for (Transaksi transaksi : riwayatTransaksi) {
                penulis.write(transaksi.getIdIkan() + "|"
                        + transaksi.getNamaIkan() + "|"
                        + transaksi.getJenisTransaksi() + "|"
                        + transaksi.getTanggal() + "|"
                        + (transaksi.getHargaLama() == null
                                ? "" : transaksi.getHargaLama()) + "|"
                        + (transaksi.getHargaBaru() == null
                                ? "" : transaksi.getHargaBaru()));
                penulis.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Riwayat transaksi belum tersimpan: "
                    + ex.getMessage());
        }
    }

    // Menyimpan seluruh isi ArrayList ke file penyimpanan.
    private void simpanData() {
        try (BufferedWriter penulis = new BufferedWriter(new FileWriter(FILE_DATA))) {
            // Menulis setiap ikan dalam satu baris dengan pemisah tanda garis.
            for (Ikan ikan : daftarIkan) {
                penulis.write(ikan.getId() + "|"
                        + ikan.getNama() + "|"
                        + ikan.getJenis() + "|"
                        + ikan.getHargaPerKg() + "|"
                        + ikan.getStokKg() + "|"
                        + ikan.getTanggalMasuk());
                penulis.newLine();
            }
        } catch (IOException ex) {
            // Memberi peringatan jika file tidak dapat disimpan.
            System.out.println("Data belum tersimpan ke file: " + ex.getMessage());
        }
    }

    // DTO read-only untuk menampilkan satu catatan transaksi pada JTable.
    public static final class DataTransaksi {
        private final int idIkan;
        private final String namaIkan;
        private final String jenisTransaksi;
        private final LocalDate tanggal;
        private final Double hargaLama;
        private final Double hargaBaru;

        private DataTransaksi(int idIkan, String namaIkan, String jenisTransaksi,
                              LocalDate tanggal, Double hargaLama, Double hargaBaru) {
            this.idIkan = idIkan;
            this.namaIkan = namaIkan;
            this.jenisTransaksi = jenisTransaksi;
            this.tanggal = tanggal;
            this.hargaLama = hargaLama;
            this.hargaBaru = hargaBaru;
        }

        public int getIdIkan() {
            return idIkan;
        }

        public String getNamaIkan() {
            return namaIkan;
        }

        public String getJenisTransaksi() {
            return jenisTransaksi;
        }

        public LocalDate getTanggal() {
            return tanggal;
        }

        public Double getHargaLama() {
            return hargaLama;
        }

        public Double getHargaBaru() {
            return hargaBaru;
        }
    }

    // Inner class lama untuk menyimpan satu catatan transaksi.
    private static class Transaksi {

        // Menyimpan ID ikan yang terkait dengan transaksi.
        private final int idIkan;
        // Menyimpan nama ikan saat transaksi dicatat.
        private final String namaIkan;
        // Menyimpan jenis transaksi.
        private final String jenisTransaksi;
        // Menyimpan tanggal transaksi.
        private final LocalDate tanggal;
        // Menyimpan harga sebelum perubahan jika transaksi berkaitan dengan harga.
        private final Double hargaLama;
        // Menyimpan harga sesudah perubahan jika transaksi berkaitan dengan harga.
        private final Double hargaBaru;

        // Constructor untuk mengisi seluruh informasi transaksi.
        private Transaksi(int idIkan, String namaIkan, String jenisTransaksi,
                          LocalDate tanggal, Double hargaLama, Double hargaBaru) {
            this.idIkan = idIkan;
            this.namaIkan = namaIkan;
            this.jenisTransaksi = jenisTransaksi;
            this.tanggal = tanggal;
            this.hargaLama = hargaLama;
            this.hargaBaru = hargaBaru;
        }

        // Getter untuk mengambil ID ikan.
        private int getIdIkan() {
            return idIkan;
        }

        // Getter untuk mengambil nama ikan.
        private String getNamaIkan() {
            return namaIkan;
        }

        // Getter untuk mengambil jenis transaksi.
        private String getJenisTransaksi() {
            return jenisTransaksi;
        }

        // Getter untuk mengambil tanggal transaksi.
        private LocalDate getTanggal() {
            return tanggal;
        }

        // Getter untuk mengambil harga lama.
        private Double getHargaLama() {
            return hargaLama;
        }

        // Getter untuk mengambil harga baru.
        private Double getHargaBaru() {
            return hargaBaru;
        }
    }
}
