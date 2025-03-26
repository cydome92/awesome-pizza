package dev.cydome.awsomepizza.dao.repository;

import dev.cydome.awsomepizza.dao.model.OrdineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<OrdineModel, Integer> {
}
