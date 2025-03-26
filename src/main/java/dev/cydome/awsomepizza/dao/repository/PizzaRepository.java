package dev.cydome.awsomepizza.dao.repository;

import dev.cydome.awsomepizza.dao.model.PizzaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<PizzaModel, Integer> {

    @Override
    @EntityGraph(attributePaths = "ingredienti")
    Page<PizzaModel> findAll(Pageable pageRequest);

}
