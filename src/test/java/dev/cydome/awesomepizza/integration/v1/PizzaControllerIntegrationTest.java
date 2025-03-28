package dev.cydome.awesomepizza.integration.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cydome.awesomepizza.dao.repository.PizzaRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class PizzaControllerIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> db = new MySQLContainer<>(DockerImageName.parse("mysql:8.4"));

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Autowired
    PizzaRepository repository;

    final String basePath = "/api/v1/pizze";

    @BeforeAll
    static void beforeAll() {
        db.start();
    }

    @AfterAll
    static void afterAll() {
        db.stop();
    }

    @Test
    void getPizze() throws Exception {
        mvc.perform(get(basePath)
                        .queryParam("page", "0")
                        .queryParam("size", "20")
                        .queryParam("sort", "nome,asc"))
                .andExpect(status().isOk());
    }

}
