package dev.cydome.awesomepizza.dao.repository;

import dev.cydome.awesomepizza.dao.model.OrdineModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdineRepository extends JpaRepository<OrdineModel, Integer> {

    Optional<OrdineModel> findFirstByOrderByDataOraInserimentoDesc();

}
