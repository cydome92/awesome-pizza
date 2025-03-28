package dev.cydome.awesomepizza.unit.repository;

import dev.cydome.awesomepizza.config.TestcontainersConfiguration;
import dev.cydome.awesomepizza.dao.model.OrdineModel;
import dev.cydome.awesomepizza.dao.model.PizzaModel;
import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import dev.cydome.awesomepizza.dao.repository.OrdineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class OrdineRepositoryTest {

	@Autowired
	OrdineRepository repository;
	@Autowired
	TestEntityManager entityManager;

	@Test
	void findFirstByOrderByDataOraInserimentoDesc() {
		var pizza = new PizzaModel();
		pizza.setNome("margherita");
		entityManager.persist(pizza);
		var ordine = new OrdineModel();
		ordine.setStato(Stato.ATTESA);
		ordine.setPizza(pizza);
		entityManager.persist(ordine);
		var rs = repository.findFirstByOrderByDataOraInserimentoDesc();
		assertFalse(rs.isEmpty());
		repository.deleteAll();
		assertTrue(repository.findAll().isEmpty());

	}

}