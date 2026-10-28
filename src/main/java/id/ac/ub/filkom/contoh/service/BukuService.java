package id.ac.ub.filkom.contoh.service;

import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.repository.BukuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Service: tempat logika bisnis/aturan aplikasi. */
@Service
public class BukuService {

    private final BukuRepository bukuRepository;

    // Dependency injection lewat constructor
    public BukuService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    public List<Buku> cariBuku(String kata) {
        if (kata == null || kata.isBlank()) {
            return bukuRepository.findAll();
        }
        return bukuRepository.findByJudulContainingIgnoreCase(kata.trim());
    }

    public Buku tambahBuku(String idBuku, String judul, int stok) {
        validasi(idBuku, judul, stok);
        if (bukuRepository.existsById(idBuku.trim())) {
            throw new IllegalArgumentException("ID buku sudah digunakan");
        }
        return bukuRepository.save(new Buku(idBuku.trim(), judul.trim(), stok));
    }

    public Buku ubahBuku(String idBuku, String judul, int stok) {
        validasi(idBuku, judul, stok);
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan"));
        buku.perbarui(judul.trim(), stok);
        return bukuRepository.save(buku);
    }

    public void hapusBuku(String idBuku) {
        if (!bukuRepository.existsById(idBuku)) {
            throw new IllegalArgumentException("Buku tidak ditemukan");
        }
        bukuRepository.deleteById(idBuku);
    }

    public long jumlahStok() {
        return bukuRepository.findAll().stream().mapToInt(Buku::getStok).sum();
    }

    private void validasi(String idBuku, String judul, int stok) {
        if (idBuku == null || idBuku.isBlank()) {
            throw new IllegalArgumentException("ID buku wajib diisi");
        }
        if (judul == null || judul.isBlank()) {
            throw new IllegalArgumentException("Judul wajib diisi");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
    }
}
