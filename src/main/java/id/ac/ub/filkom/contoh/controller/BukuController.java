package id.ac.ub.filkom.contoh.controller;

import id.ac.ub.filkom.contoh.service.BukuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Controller: terima request, panggil service, pilih template. */
@Controller
public class BukuController {

    private final BukuService bukuService;

    public BukuController(BukuService bukuService) {
        this.bukuService = bukuService;
    }

    // GET http://localhost:8080/buku?kata=java
    @GetMapping("/buku")
    public String daftarBuku(@RequestParam(required = false) String kata,
                             Model model) {
        model.addAttribute("daftarBuku", bukuService.cariBuku(kata));
        model.addAttribute("kata", kata);
        model.addAttribute("jumlahStok", bukuService.jumlahStok());
        return "buku";
    }

    @PostMapping("/buku")
    public String tambahBuku(@RequestParam String idBuku,
                             @RequestParam String judul,
                             @RequestParam int stok,
                             RedirectAttributes redirectAttributes) {
        try {
            bukuService.tambahBuku(idBuku, judul, stok);
            redirectAttributes.addFlashAttribute("pesan", "Buku berhasil ditambahkan.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("pesanError", ex.getMessage());
        }
        return "redirect:/buku";
    }

    @PostMapping("/buku/{idBuku}/ubah")
    public String ubahBuku(@PathVariable String idBuku,
                           @RequestParam String judul,
                           @RequestParam int stok,
                           RedirectAttributes redirectAttributes) {
        try {
            bukuService.ubahBuku(idBuku, judul, stok);
            redirectAttributes.addFlashAttribute("pesan", "Data buku berhasil diperbarui.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("pesanError", ex.getMessage());
        }
        return "redirect:/buku";
    }

    @PostMapping("/buku/{idBuku}/hapus")
    public String hapusBuku(@PathVariable String idBuku,
                            RedirectAttributes redirectAttributes) {
        try {
            bukuService.hapusBuku(idBuku);
            redirectAttributes.addFlashAttribute("pesan", "Buku berhasil dihapus.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("pesanError", ex.getMessage());
        }
        return "redirect:/buku";
    }
}
