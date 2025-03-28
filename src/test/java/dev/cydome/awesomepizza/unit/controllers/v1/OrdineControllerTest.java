package dev.cydome.awesomepizza.unit.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cydome.awesomepizza.controllers.dto.OrdineDto;
import dev.cydome.awesomepizza.controllers.dto.PizzaDto;
import dev.cydome.awesomepizza.controllers.v1.OrdineController;
import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import dev.cydome.awesomepizza.exception.MissingPizzaException;
import dev.cydome.awesomepizza.exception.OrdineNotFoundException;
import dev.cydome.awesomepizza.exception.StatoOrdineConflictException;
import dev.cydome.awesomepizza.service.OrdineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdineController.class)
class OrdineControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    OrdineService service;

    final String basePath = "/api/v1/ordini";

    @Test
    void getNextOrdine_notFound() throws Exception {
        doThrow(OrdineNotFoundException.class)
                .when(service)
                .getNextOrdine();
        mvc.perform(get(basePath + "/next"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNextOrdine_found() throws Exception {
        when(service.getNextOrdine())
                .thenReturn(mock(OrdineDto.class));
        mvc.perform(get(basePath + "/next"))
                .andExpect(status().isOk());
    }

    @Test
    void insertOrdine_missingPizza() throws Exception {
        var body = new OrdineDto(null, null, Stato.ATTESA,
                new PizzaDto(1, "test", List.of()));
        doThrow(MissingPizzaException.class)
                .when(service)
                .insertOrdine(any());
        mvc.perform(put(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }

    @Test
    void insertOrdine_success() throws Exception {
        var body = new OrdineDto(null, null, Stato.ATTESA,
                new PizzaDto(1, "test", List.of()));
        when(service.insertOrdine(any()))
                .thenReturn(mock(OrdineDto.class));
        mvc.perform(put(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateStatoOrdine_notFound() throws Exception {
        doThrow(OrdineNotFoundException.class)
                .when(service)
                .updateStato(anyInt(), any());
        mvc.perform(patch(basePath + "/1")
                        .queryParam("stato", Stato.COMPLETATO.name()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStatoOrdine_conflict() throws Exception {
        doThrow(StatoOrdineConflictException.class)
                .when(service)
                .updateStato(anyInt(), any());
        mvc.perform(patch(basePath + "/1")
                        .queryParam("stato", Stato.COMPLETATO.name()))
                .andExpect(status().isConflict());
    }

    @Test
    void updateStatoOrdine_success() throws Exception {
        doNothing().when(service)
                .updateStato(anyInt(), any());
        mvc.perform(patch(basePath + "/1")
                        .queryParam("stato", Stato.COMPLETATO.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrdine_notFound() throws Exception {
        doThrow(OrdineNotFoundException.class)
                .when(service)
                .deleteOrdine(anyInt());
        mvc.perform(delete(basePath + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOrdine_success() throws Exception {
        doNothing().when(service)
                .deleteOrdine(anyInt());
        mvc.perform(delete(basePath + "/1"))
                .andExpect(status().isNoContent());
    }

}