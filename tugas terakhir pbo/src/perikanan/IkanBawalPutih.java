package perikanan;

import java.time.LocalDate;

// Class IkanBawalPutih mewakili jenis Ikan Bawal Putih dan mewarisi seluruh data dari Ikan.
public class IkanBawalPutih extends Ikan {

    public IkanBawalPutih(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanBawalPutih(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Bawal Putih", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Bawal Putih adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

