// Menentukan package tempat class IkanAirAsin berada.
package perikanan;

// Mengimpor LocalDate untuk constructor saat membaca data dari file.
import java.time.LocalDate;

// Class IkanAirAsin mewarisi atribut dan method dari class Ikan.
public class IkanAirAsin extends Ikan {

    // Constructor untuk membuat data ikan yang hidup di air asin.
    public IkanAirAsin(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    // Constructor yang mempertahankan tanggal masuk dari data tersimpan.
    public IkanAirAsin(int id, String nama, double hargaPerKg, double stokKg,
                       LocalDate tanggalMasuk) {
        // Memanggil constructor class induk dengan jenis Ikan Air Asin.
        super(id, nama, "Ikan Air Asin", hargaPerKg, stokKg, tanggalMasuk);
    }

    // Mengimplementasikan method abstrak dari class Ikan.
    @Override
    public String deskripsiJenis() {
        // Mengembalikan deskripsi khusus untuk ikan air asin.
        return "Ikan yang hidup di air asin.";
    }

    // Ikan air asin ditetapkan tetap segar selama tujuh hari.
    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}
