package perikanan;

import java.time.LocalDate;

// Class IkanTenggiri mewakili jenis Ikan Tenggiri dan mewarisi seluruh data dari Ikan.
public class IkanTenggiri extends Ikan {

    public IkanTenggiri(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanTenggiri(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Tenggiri", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Tenggiri adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

