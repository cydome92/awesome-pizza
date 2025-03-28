package dev.cydome.awesomepizza.unit.controllers.v1;

import dev.cydome.awesomepizza.controllers.v1.PizzaController;
import dev.cydome.awesomepizza.service.PizzaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PizzaController.class)
class PizzaControllerTest {

    @Autowired
    MockMvc mvc;
    @MockitoBean
    PizzaService service;

    final String basePath = "/api/v1/pizze";

    @Test
    void getPizze() throws Exception {
        when(service.getPizze(any()))
                .thenReturn(new PageImpl<>(List.of()));
        mvc.perform(get(basePath)
                        .queryParam("page", "0")
                        .queryParam("size", "20")
                        .queryParam("sort", "nome,asc"))
                .andExpect(status().isOk());
    }
}