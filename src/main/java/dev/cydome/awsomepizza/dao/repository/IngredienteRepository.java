package dev.cydome.awsomepizza.dao.repository;

import dev.cydome.awsomepizza.dao.model.IngredienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<IngredienteModel, Integer> {
}
