package dev.cydome.awesomepizza.unit.repository;

import dev.cydome.awesomepizza.config.TestcontainersConfiguration;
import dev.cydome.awesomepizza.dao.model.IngredienteModel;
import dev.cydome.awesomepizza.dao.model.PizzaModel;
import dev.cydome.awesomepizza.dao.repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class PizzaRepositoryTest {

	@Autowired
	PizzaRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	void findBy_returnsIngredienti() {
		var pomodoro = new IngredienteModel();
		pomodoro.setDescrizione("pomodoro");
		entityManager.persist(pomodoro);
		var mozzarella = new IngredienteModel();
		mozzarella.setDescrizione("mozzarella");
		entityManager.persist(mozzarella);
		var basilico = new IngredienteModel();
		basilico.setDescrizione("basilico");
		entityManager.persist(basilico);
		var pizza = new PizzaModel();
		pizza.setNome("margherita");
		//List.of è immutable
		pizza.setIngredienti(Arrays.asList(pomodoro, mozzarella, basilico));
		entityManager.persist(pizza);
		var rs = repository.findBy(PageRequest.of(0, 1));
		assertFalse(rs.isEmpty());
		assertFalse(rs.getContent().getFirst().getIngredienti().isEmpty());
	}
}