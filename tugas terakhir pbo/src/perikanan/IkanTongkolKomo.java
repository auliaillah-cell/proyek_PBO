package perikanan;

import java.time.LocalDate;

// Class IkanTongkolKomo mewakili jenis Ikan Tongkol Komo dan mewarisi seluruh data dari Ikan.
public class IkanTongkolKomo extends Ikan {

    public IkanTongkolKomo(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanTongkolKomo(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Tongkol Komo", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Tongkol Komo adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

