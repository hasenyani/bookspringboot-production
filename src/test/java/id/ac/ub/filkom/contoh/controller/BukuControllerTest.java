package id.ac.ub.filkom.contoh.controller;

import id.ac.ub.filkom.contoh.repository.BukuRepository;
import id.ac.ub.filkom.contoh.service.BukuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BukuController.class)
@Import(BukuService.class)
class BukuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BukuRepository bukuRepository;

    @Test
    void halamanDaftarBukuBerhasilDirender() throws Exception {
        when(bukuRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/buku"))
                .andExpect(status().isOk())
                .andExpect(view().name("buku"));
    }
}
