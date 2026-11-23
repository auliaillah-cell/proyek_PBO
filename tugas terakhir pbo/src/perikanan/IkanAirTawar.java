// Menentukan package tempat class IkanAirTawar berada.
package perikanan;

// Mengimpor LocalDate untuk constructor saat membaca data dari file.
import java.time.LocalDate;

// Class IkanAirTawar mewarisi atribut dan method dari class Ikan.
public class IkanAirTawar extends Ikan {

    // Constructor untuk membuat data ikan yang hidup di air tawar.
    public IkanAirTawar(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    // Constructor yang mempertahankan tanggal masuk dari data tersimpan.
    public IkanAirTawar(int id, String nama, double hargaPerKg, double stokKg,
                        LocalDate tanggalMasuk) {
        // Memanggil constructor class induk dengan jenis Ikan Air Tawar.
        super(id, nama, "Ikan Air Tawar", hargaPerKg, stokKg, tanggalMasuk);
    }

    // Mengimplementasikan method abstrak dari class Ikan.
    @Override
    public String deskripsiJenis() {
        // Mengembalikan deskripsi khusus untuk ikan air tawar.
        return "Ikan yang hidup di air tawar.";
    }

    // Ikan air tawar ditetapkan tetap segar selama tiga hari.
    @Override
    public int getMasaSegarHari() {
        return 3;
    }
}
