package dev.cydome.awesomepizza.service;

import dev.cydome.awesomepizza.controllers.dto.PizzaDto;
import dev.cydome.awesomepizza.dao.model.PizzaModel;
import dev.cydome.awesomepizza.dao.repository.PizzaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    private final PizzaRepository repository;

    public PizzaService(PizzaRepository repository) {
        this.repository = repository;
    }

    public Page<PizzaDto> getPizze(Pageable pageRequest) {
        return repository.findAll(pageRequest)
                .map(PizzaModel::toDto);
    }

}
