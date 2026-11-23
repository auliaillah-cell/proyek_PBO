package perikanan;

import java.time.LocalDate;

// Class IkanTongkol mewakili jenis Ikan Tongkol dan mewarisi seluruh data dari Ikan.
public class IkanTongkol extends Ikan {

    public IkanTongkol(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanTongkol(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Tongkol", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Tongkol adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

