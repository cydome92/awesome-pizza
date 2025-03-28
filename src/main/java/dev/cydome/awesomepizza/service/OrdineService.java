package dev.cydome.awesomepizza.service;

import dev.cydome.awesomepizza.controllers.dto.OrdineDto;
import dev.cydome.awesomepizza.dao.model.OrdineModel;
import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import dev.cydome.awesomepizza.dao.repository.OrdineRepository;
import dev.cydome.awesomepizza.dao.repository.PizzaRepository;
import dev.cydome.awesomepizza.exception.MissingPizzaException;
import dev.cydome.awesomepizza.exception.OrdineNotFoundException;
import dev.cydome.awesomepizza.exception.StatoOrdineConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrdineService {

    private final OrdineRepository repository;
    private final PizzaRepository pizzaRepository;

    public OrdineService(OrdineRepository repository, PizzaRepository pizzaRepository) {
        this.repository = repository;
        this.pizzaRepository = pizzaRepository;
    }

    public OrdineDto getNextOrdine() {
        return repository.findFirstByOrderByDataOraInserimentoDesc()
                .map(OrdineModel::toDto)
                .orElseThrow(OrdineNotFoundException::new);
    }

    public OrdineDto insertOrdine(OrdineDto ordine) {
        var pizza = pizzaRepository.findById(ordine.pizza().id())
                .orElseThrow(MissingPizzaException::new);
        var model = new OrdineModel(null, null, null, Stato.ATTESA, pizza);
        return repository.save(model)
                .toDto();
    }

    public void updateStato(int ordineId, Stato stato) {
        var ordine = repository.findById(ordineId)
                .orElseThrow(OrdineNotFoundException::new);
        if (ordine.getStato().equals(Stato.COMPLETATO))
            throw new StatoOrdineConflictException();
        ordine.setStato(stato);
        repository.save(ordine);
    }

    public void deleteOrdine(int ordineId) {
        var ordine = repository.findById(ordineId)
                .orElseThrow(OrdineNotFoundException::new);
        log.info("deleted ordine: {}", ordine);
        repository.deleteById(ordineId);
    }

}
