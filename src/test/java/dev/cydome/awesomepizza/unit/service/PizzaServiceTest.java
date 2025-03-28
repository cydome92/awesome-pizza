package dev.cydome.awesomepizza.unit.service;

import dev.cydome.awesomepizza.dao.model.PizzaModel;
import dev.cydome.awesomepizza.dao.repository.PizzaRepository;
import dev.cydome.awesomepizza.service.PizzaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(PizzaService.class)
class PizzaServiceTest {

	@Autowired
	PizzaService service;
	@MockitoBean
	PizzaRepository repository;

	@Test
	void getPizze() {
		when(repository.findBy(any()))
				.thenReturn(new PageImpl<>(List.of(Mockito.mock(PizzaModel.class))));
		assertDoesNotThrow(() -> service.getPizze(PageRequest.of(0, 10)));
	}
}