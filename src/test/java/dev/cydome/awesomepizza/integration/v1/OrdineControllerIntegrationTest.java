package dev.cydome.awesomepizza.integration.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cydome.awesomepizza.controllers.dto.OrdineDto;
import dev.cydome.awesomepizza.controllers.dto.PizzaDto;
import dev.cydome.awesomepizza.dao.model.IngredienteModel;
import dev.cydome.awesomepizza.dao.model.OrdineModel;
import dev.cydome.awesomepizza.dao.model.PizzaModel;
import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import dev.cydome.awesomepizza.dao.repository.IngredienteRepository;
import dev.cydome.awesomepizza.dao.repository.OrdineRepository;
import dev.cydome.awesomepizza.dao.repository.PizzaRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class OrdineControllerIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> db = new MySQLContainer<>(DockerImageName.parse("mysql:8.4"));

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Autowired
    OrdineRepository repository;
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    IngredienteRepository ingredienteRepository;

    final String basePath = "/api/v1/ordini";

    @BeforeAll
    static void beforeAll() {
        db.start();
    }

    @AfterAll
    static void afterAll() {
        db.stop();
    }

    @BeforeEach
    void initDb() {
        if (ingredienteRepository.count() == 0) {
            var ingrediente_1 = new IngredienteModel();
            ingrediente_1.setDescrizione("mozzarella");
            var ingrediente_2 = new IngredienteModel();
            ingrediente_2.setDescrizione("pomodoro");
            var ingrediente_3 = new IngredienteModel();
            ingrediente_3.setDescrizione("basilico");
            var ingrediente_4 = new IngredienteModel();
            ingrediente_4.setDescrizione("salame piccante");
            var ingrediente_5 = new IngredienteModel();
            ingrediente_5.setDescrizione("peperoncino");
            var ingrediente_6 = new IngredienteModel();
            ingrediente_6.setDescrizione("tonno");
            var ingrediente_7 = new IngredienteModel();
            ingrediente_7.setDescrizione("cipolla rossa");
            ingredienteRepository.saveAll(List.of(ingrediente_1, ingrediente_2, ingrediente_3, ingrediente_4, ingrediente_5, ingrediente_6, ingrediente_7));
            var pizza_1 = new PizzaModel();
            pizza_1.setNome("margherita");
            pizza_1.setIngredienti(List.of(ingrediente_1, ingrediente_2, ingrediente_3));
            var pizza_2 = new PizzaModel();
            pizza_2.setNome("diavola");
            pizza_2.setIngredienti(List.of(ingrediente_1, ingrediente_2, ingrediente_4, ingrediente_5));
            var pizza_3 = new PizzaModel();
            pizza_3.setNome("tonno e cipolla");
            pizza_3.setIngredienti(List.of(ingrediente_1, ingrediente_2, ingrediente_6, ingrediente_7));
            pizzaRepository.saveAll(List.of(pizza_1, pizza_2, pizza_3));
        }
    }

    @AfterEach
    void emptyRepository() {
        repository.deleteAll();
    }

    @Test
    void getNextOrdine() throws Exception {
        mvc.perform(get(basePath + "/next"))
                .andExpect(status().isNotFound());
        var pizza = pizzaRepository.findAll().getFirst();
        repository.save(new OrdineModel(null, LocalDateTime.now(), null, Stato.ATTESA, pizza));
        mvc.perform(get(basePath + "/next"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dataOraInserimento").isNotEmpty());
    }

    @Test
    void insertOrdine() throws Exception {
        var notFoundPizzaBody = new PizzaDto(-1, "not-found", List.of());
        var notFoundOrdineBody = new OrdineDto(null, null, Stato.IN_CARICO, notFoundPizzaBody);
        mvc.perform(put(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(notFoundOrdineBody)))
                .andExpect(status().isNotFound());
        var pizza = pizzaRepository.findBy(PageRequest.of(0, 20)).getContent().getFirst().toDto();
        var body = new OrdineDto(null, null, Stato.COMPLETATO, pizza);
        mvc.perform(put(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stato").value(Stato.ATTESA.name()))  //ignora quanto inviato
                .andExpect(jsonPath("$.dataOraInserimento").isNotEmpty());
    }

    @Test
    void updateOrdine() throws Exception {
        var pizza = pizzaRepository.findBy(PageRequest.of(0, 20)).getContent().getFirst();
        var ordine = new OrdineModel(null, LocalDateTime.now(), null, Stato.ATTESA, pizza);
        repository.save(ordine);
        int id = ordine.getId();
        mvc.perform(patch(basePath + "/" + id)
                        .queryParam("stato", Stato.IN_CARICO.name()))
                .andExpect(status().isNoContent());
        mvc.perform(patch(basePath + "/" + id)
                        .queryParam("stato", Stato.COMPLETATO.name()))
                .andExpect(status().isNoContent());
        assertNotNull(repository.findById(id).map(OrdineModel::getDataOraCompletamento));
        mvc.perform(patch(basePath + "/" + id)
                        .queryParam("stato", Stato.ATTESA.name()))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteOrdine() throws Exception {
        var pizza = pizzaRepository.findBy(PageRequest.of(0, 20)).getContent().getFirst();
        var ordine = new OrdineModel(null, LocalDateTime.now(), null, Stato.ATTESA, pizza);
        repository.save(ordine);
        mvc.perform(delete(basePath + "/" + ordine.getId() + 1))
                .andExpect(status().isNotFound());
        mvc.perform(delete(basePath + "/" + ordine.getId()))
                .andExpect(status().isNoContent());
    }

}
