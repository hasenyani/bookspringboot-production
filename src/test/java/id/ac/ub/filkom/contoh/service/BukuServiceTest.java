package id.ac.ub.filkom.contoh.service;

import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.repository.BukuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BukuServiceTest {

    @Mock
    private BukuRepository repository;

    private BukuService service;

    @BeforeEach
    void setUp() {
        service = new BukuService(repository);
    }

    @Test
    void menambahBukuNormalisasiData() {
        when(repository.existsById("B004")).thenReturn(false);
        when(repository.save(any(Buku.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Buku saved = service.tambahBuku(" B004 ", " Clean Code ", 2);

        assertEquals("B004", saved.getIdBuku());
        assertEquals("Clean Code", saved.getJudul());
        verify(repository).save(saved);
    }

    @Test
    void menambahBukuMenolakStokNegatif() {
        assertThrows(IllegalArgumentException.class,
                () -> service.tambahBuku("B004", "Clean Code", -1));
    }

    @Test
    void mengubahBukuMenggantiJudulDanStok() {
        Buku buku = new Buku("B004", "Judul lama", 1);
        when(repository.findById("B004")).thenReturn(Optional.of(buku));
        when(repository.save(buku)).thenReturn(buku);

        Buku updated = service.ubahBuku("B004", "Judul baru", 5);

        assertEquals("Judul baru", updated.getJudul());
        assertEquals(5, updated.getStok());
    }
}
