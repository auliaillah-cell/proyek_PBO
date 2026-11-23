package perikanan;

import java.time.LocalDate;

// Class IkanLemuru mewakili jenis Ikan Lemuru dan mewarisi seluruh data dari Ikan.
public class IkanLemuru extends Ikan {

    public IkanLemuru(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanLemuru(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Lemuru", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Lemuru adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

