package dev.cydome.awsomepizza.service;

import dev.cydome.awsomepizza.controllers.dto.OrdineDto;
import dev.cydome.awsomepizza.dao.model.OrdineModel;
import dev.cydome.awsomepizza.dao.model.enumerator.Stato;
import dev.cydome.awsomepizza.dao.repository.OrdineRepository;
import dev.cydome.awsomepizza.dao.repository.PizzaRepository;
import dev.cydome.awsomepizza.exception.MissingPizzaException;
import dev.cydome.awsomepizza.exception.OrdineNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class OrdineService {

    private final OrdineRepository repository;
    private final PizzaRepository pizzaRepository;

    public OrdineService(OrdineRepository repository, PizzaRepository pizzaRepository) {
        this.repository = repository;
        this.pizzaRepository = pizzaRepository;
    }

    public OrdineDto insertOrdine(OrdineDto ordine) {
        var pizza = pizzaRepository.findById(ordine.pizza().id())
                .orElseThrow(MissingPizzaException::new);
        var model = new OrdineModel(null, LocalDateTime.now(ZoneId.of("Europe/Rome")), Stato.ATTESA, pizza);
        return repository.save(model)
                .toDto();
    }

    public void updateStato(int ordineId, Stato stato) {
        var ordine = repository.findById(ordineId)
                .orElseThrow(OrdineNotFoundException::new);

    }

}
