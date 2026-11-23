package perikanan;

import java.time.LocalDate;

// Class IkanSelar mewakili jenis Ikan Selar dan mewarisi seluruh data dari Ikan.
public class IkanSelar extends Ikan {

    public IkanSelar(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanSelar(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Selar", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Selar adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

