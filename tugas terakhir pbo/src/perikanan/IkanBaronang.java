package perikanan;

import java.time.LocalDate;

// Class IkanBaronang mewakili jenis Ikan Baronang dan mewarisi seluruh data dari Ikan.
public class IkanBaronang extends Ikan {

    public IkanBaronang(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanBaronang(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Baronang", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Baronang adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

