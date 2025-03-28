package dev.cydome.awesomepizza.unit.service;

import dev.cydome.awesomepizza.controllers.dto.OrdineDto;
import dev.cydome.awesomepizza.controllers.dto.PizzaDto;
import dev.cydome.awesomepizza.dao.model.OrdineModel;
import dev.cydome.awesomepizza.dao.model.PizzaModel;
import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import dev.cydome.awesomepizza.dao.repository.OrdineRepository;
import dev.cydome.awesomepizza.dao.repository.PizzaRepository;
import dev.cydome.awesomepizza.exception.MissingPizzaException;
import dev.cydome.awesomepizza.exception.OrdineNotFoundException;
import dev.cydome.awesomepizza.exception.StatoOrdineConflictException;
import dev.cydome.awesomepizza.service.OrdineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@SpringJUnitConfig(OrdineService.class)
class OrdineServiceTest {

    @Autowired
    OrdineService service;
    @MockitoBean
    OrdineRepository repository;
    @MockitoBean
    PizzaRepository pizzaRepository;

    @Test
    void getNextOrdine_noMoreOrders() {
        when(repository.findFirstByOrderByDataOraInserimentoDesc())
                .thenReturn(Optional.empty());
        assertThrows(OrdineNotFoundException.class, () -> service.getNextOrdine());
    }

    @Test
    void getNextOrdine_success() {
        when(repository.findFirstByOrderByDataOraInserimentoDesc()).thenReturn(
                Optional.of(mock(OrdineModel.class, withSettings().defaultAnswer(RETURNS_SMART_NULLS))));    //altrimenti dataOraInserimento è null
        assertDoesNotThrow(() -> service.getNextOrdine());
    }

    @Test
    void insertOrdine_pizzaNotFound() {
        when(pizzaRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        var pizza = new PizzaDto(1, "diavola", List.of());
        var dto = new OrdineDto(null, null, null, pizza);
        assertThrows(MissingPizzaException.class, () -> service.insertOrdine(dto));
    }

    @Test
    void insertOrdine_success() {
        when(pizzaRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(PizzaModel.class)));
        when(repository.save(any(OrdineModel.class)))
                .thenReturn(mock(OrdineModel.class));
        var pizza = new PizzaDto(1, "diavola", List.of());
        var dto = new OrdineDto(null, null, null, pizza);
        assertDoesNotThrow(() -> service.insertOrdine(dto));
    }

    @Test
    void updateStato_ordineNotFound() {
        when(repository.findById(anyInt()))
                .thenReturn(Optional.empty());
        assertThrows(OrdineNotFoundException.class, () -> service.updateStato(1, Stato.IN_CARICO));
    }

    @Test
    void updateStato_statoConflict() {
        var rs = new OrdineModel();
        rs.setId(1);
        rs.setStato(Stato.COMPLETATO);
        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(rs));
        assertThrows(StatoOrdineConflictException.class, () -> service.updateStato(rs.getId(), Stato.IN_CARICO));
    }

    @Test
    void updateStato_success() {
        var rs = new OrdineModel();
        rs.setId(1);
        rs.setStato(Stato.ATTESA);
        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(rs));
        assertDoesNotThrow(() -> service.updateStato(rs.getId(), Stato.IN_CARICO));
    }

    @Test
    void deleteOrdine_notFound() {
        when(repository.findById(anyInt()))
                .thenReturn(Optional.empty());
        assertThrows(OrdineNotFoundException.class, () -> service.deleteOrdine(1));
    }

    @Test
    void deleteOrdine_success() {
        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(mock(OrdineModel.class)));
        assertDoesNotThrow(() -> service.deleteOrdine(1));
    }
}