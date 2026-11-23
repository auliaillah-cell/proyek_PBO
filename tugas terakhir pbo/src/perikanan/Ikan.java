// Menentukan package tempat class Ikan berada.
package perikanan;

// Mengimpor ArrayList untuk menyimpan daftar jenis ikan.
import java.util.ArrayList;
// Mengimpor Arrays untuk membuat daftar awal dari beberapa nilai.
import java.util.Arrays;
// Mengimpor LocalDate untuk menyimpan tanggal ikan masuk.
import java.time.LocalDate;
// Mengimpor ChronoUnit untuk menghitung umur ikan dalam hari.
import java.time.temporal.ChronoUnit;

// Class Ikan dibuat abstract karena menjadi class induk untuk berbagai jenis ikan.
public abstract class Ikan {

    // Menyimpan dua pilihan kategori ikan yang dapat dipilih pengguna.
    private static final ArrayList<String> DAFTAR_JENIS = new ArrayList<>(Arrays.asList(
        "Ikan Air Tawar", "Ikan Air Asin"
    ));

    // Menyimpan ID unik untuk setiap data ikan.
    private int id;
    // Menyimpan nama ikan.
    private String nama;
    // Menyimpan kategori tempat hidup ikan.
    private String jenis;
    // Menyimpan harga ikan untuk setiap kilogram.
    private double hargaPerKg;
    // Menyimpan jumlah stok ikan dalam satuan kilogram.
    private double stokKg;
    // Menyimpan tanggal terakhir ikan masuk atau stok terbaru ditambahkan.
    private LocalDate tanggalMasuk;

    // Constructor untuk mengisi seluruh data ikan sekaligus melakukan validasi.
    protected Ikan(int id, String nama, String jenis, double hargaPerKg, double stokKg) {
        this(id, nama, jenis, hargaPerKg, stokKg, LocalDate.now());
    }

    // Constructor lengkap untuk memuat data lama beserta tanggal masuknya.
    protected Ikan(int id, String nama, String jenis, double hargaPerKg,
                   double stokKg, LocalDate tanggalMasuk) {
        // Mengisi ID setelah memastikan nilainya valid.
        this.id = validasiId(id);
        // Mengisi nama setelah memastikan nama tidak kosong.
        this.nama = validasiNama(nama);
        // Mengisi jenis setelah memastikan jenis tersedia dalam daftar.
        this.jenis = validasiJenis(jenis);
        // Mengisi harga setelah memastikan nilainya lebih besar dari nol.
        this.hargaPerKg = validasiHarga(hargaPerKg);
        // Mengisi stok setelah memastikan nilainya tidak negatif.
        this.stokKg = validasiStok(stokKg);
        // Mengisi tanggal masuk setelah memastikan tanggal tersedia.
        this.tanggalMasuk = validasiTanggalMasuk(tanggalMasuk);
    }

    // Mengembalikan daftar jenis ikan dalam bentuk array agar mudah ditampilkan di menu.
    public static String[] getDaftarJenisIkan() {
        // Menyalin isi ArrayList menjadi array String baru.
        return DAFTAR_JENIS.toArray(new String[DAFTAR_JENIS.size()]);
    }

    // Getter untuk mengambil ID ikan.
    public int getId() {
        return id;
    }

    // Setter untuk mengubah ID ikan dengan validasi.
    public void setId(int id) {
        this.id = validasiId(id);
    }

    // Memeriksa agar ID ikan harus lebih besar dari nol.
    private static int validasiId(int id) {
        // Menolak ID nol atau bilangan negatif.
        if (id <= 0) {
            throw new IllegalArgumentException("ID harus lebih besar dari 0.");
        }
        // Mengembalikan ID yang sudah dinyatakan valid.
        return id;
    }

    // Getter untuk mengambil nama ikan.
    public String getNama() {
        return nama;
    }

    // Setter untuk mengubah nama ikan dengan validasi.
    public void setNama(String nama) {
        this.nama = validasiNama(nama);
    }

    // Memeriksa agar nama ikan tidak kosong atau null.
    private static String validasiNama(String nama) {
        // Menolak nilai null dan teks kosong setelah spasi dihapus.
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama ikan tidak boleh kosong.");
        }
        // Mengembalikan nama tanpa spasi di awal dan akhir.
        return nama.trim();
    }

    // Getter untuk mengambil kategori jenis ikan.
    public String getJenis() {
        return jenis;
    }

    // Setter untuk mengubah kategori jenis ikan dengan validasi.
    public void setJenis(String jenis) {
        this.jenis = validasiJenis(jenis);
    }

    // Memeriksa agar jenis ikan tersedia dalam daftar pilihan.
    private static String validasiJenis(String jenis) {
        // Menolak jenis null atau jenis yang tidak terdaftar.
        if (jenis == null || !jenisTerdaftar(jenis.trim())) {
            throw new IllegalArgumentException("Jenis ikan tidak terdaftar.");
        }
        // Mengembalikan jenis tanpa spasi di awal dan akhir.
        return jenis.trim();
    }

    // Getter untuk mengambil harga ikan per kilogram.
    public double getHargaPerKg() {
        return hargaPerKg;
    }

    // Setter untuk mengubah harga ikan dengan validasi.
    public void setHargaPerKg(double hargaPerKg) {
        this.hargaPerKg = validasiHarga(hargaPerKg);
    }

    // Memeriksa agar harga berupa angka yang sah dan lebih besar dari nol.
    private static double validasiHarga(double hargaPerKg) {
        // Menolak nilai NaN, tak terhingga, nol, dan bilangan negatif.
        if (Double.isNaN(hargaPerKg) || Double.isInfinite(hargaPerKg) || hargaPerKg <= 0) {
            throw new IllegalArgumentException("Harga harus lebih besar dari 0.");
        }
        // Mengembalikan harga yang sudah dinyatakan valid.
        return hargaPerKg;
    }

    // Getter untuk mengambil jumlah stok ikan dalam kilogram.
    public double getStokKg() {
        return stokKg;
    }

    // Setter untuk mengubah jumlah stok dengan validasi.
    public void setStokKg(double stokKg) {
        this.stokKg = validasiStok(stokKg);
    }

    // Mengembalikan stok terbaru yang tersimpan pada data ikan.
    public double getStokTerbaru() {
        return stokKg;
    }

    // Mengambil tanggal masuk atau tanggal stok terbaru diterima.
    public LocalDate getTanggalMasuk() {
        return tanggalMasuk;
    }

    // Mengubah tanggal masuk dengan validasi.
    public void setTanggalMasuk(LocalDate tanggalMasuk) {
        this.tanggalMasuk = validasiTanggalMasuk(tanggalMasuk);
    }

    // Menghitung berapa hari ikan telah tersimpan sejak tanggal masuk.
    public long getUmurHari() {
        return ChronoUnit.DAYS.between(tanggalMasuk, LocalDate.now());
    }

    // Menentukan apakah ikan masih berada dalam batas masa segarnya.
    public boolean isSegar() {
        return getUmurHari() <= getMasaSegarHari();
    }

    // Menghasilkan status yang mudah dibaca pada laporan.
    public String statusKesegaran() {
        return isSegar() ? "Masih Segar" : "Kurang Segar";
    }

    // Memeriksa agar stok berupa angka yang sah dan tidak negatif.
    private static double validasiStok(double stokKg) {
        // Menolak nilai NaN, tak terhingga, dan bilangan negatif.
        if (Double.isNaN(stokKg) || Double.isInfinite(stokKg) || stokKg < 0) {
            throw new IllegalArgumentException("Stok tidak boleh kurang dari 0.");
        }
        // Mengembalikan stok yang sudah dinyatakan valid.
        return stokKg;
    }

    // Memastikan tanggal masuk tidak null.
    private static LocalDate validasiTanggalMasuk(LocalDate tanggalMasuk) {
        if (tanggalMasuk == null) {
            throw new IllegalArgumentException("Tanggal masuk tidak boleh kosong.");
        }
        return tanggalMasuk;
    }

    // Memeriksa apakah jenis ikan tertentu ada dalam daftar pilihan.
    private static boolean jenisTerdaftar(String jenis) {
        // Mengulang seluruh jenis ikan yang tersimpan.
        for (String jenisTerpilih : DAFTAR_JENIS) {
            // Membandingkan jenis tanpa membedakan huruf besar dan kecil.
            if (jenisTerpilih.equalsIgnoreCase(jenis)) {
                // Mengembalikan true jika jenis ditemukan.
                return true;
            }
        }
        // Mengembalikan false jika jenis tidak ditemukan.
        return false;
    }

    // Method abstrak yang wajib dibuat ulang oleh class turunan.
    public abstract String deskripsiJenis();

    // Setiap class ikan menentukan sendiri berapa hari ikan tetap segar.
    public abstract int getMasaSegarHari();
}
