package perikanan;

import java.time.LocalDate;

// Class IkanKerapu mewakili jenis Ikan Kerapu dan mewarisi seluruh data dari Ikan.
public class IkanKerapu extends Ikan {

    public IkanKerapu(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanKerapu(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Kerapu", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Kerapu adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

