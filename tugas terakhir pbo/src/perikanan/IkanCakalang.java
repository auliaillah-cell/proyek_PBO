package perikanan;

import java.time.LocalDate;

// Class IkanCakalang mewakili jenis Ikan Cakalang dan mewarisi seluruh data dari Ikan.
public class IkanCakalang extends Ikan {

    public IkanCakalang(int id, String nama, double hargaPerKg, double stokKg) {
        this(id, nama, hargaPerKg, stokKg, LocalDate.now());
    }

    public IkanCakalang(int id, String nama, double hargaPerKg, double stokKg,
                  LocalDate tanggalMasuk) {
        super(id, nama, "Ikan Cakalang", hargaPerKg, stokKg, tanggalMasuk);
    }

    @Override
    public String deskripsiJenis() {
        return "Ikan Cakalang adalah ikan air asin yang umum ditangkap nelayan.";
    }

    @Override
    public int getMasaSegarHari() {
        return 7;
    }
}

