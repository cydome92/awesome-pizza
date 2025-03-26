package dev.cydome.awesomepizza.dao.repository;

import dev.cydome.awesomepizza.dao.model.IngredienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<IngredienteModel, Integer> {
}
