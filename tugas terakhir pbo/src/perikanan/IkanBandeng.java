package perikanan;

import java.time.LocalDate;

// Class IkanBandeng mewakili jenis Ikan Bandeng dan mewarisi seluruh data dari Ikan.
public class IkanBandeng extends Ikan {

    public IkanBandeng(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanBandeng(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Bandeng", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Bandeng adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

