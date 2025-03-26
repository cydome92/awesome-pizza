package dev.cydome.awesomepizza.controllers;

import dev.cydome.awesomepizza.controllers.dto.OrdineDto;
import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import dev.cydome.awesomepizza.service.OrdineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ordini")
public class OrdineControllerV1 {

    private final OrdineService service;

    public OrdineControllerV1(OrdineService service) {
        this.service = service;
    }

    @GetMapping("/next")
    public OrdineDto getNextOrdine() {
        return service.getNextOrdine();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdineDto insertOrdine(@RequestBody OrdineDto body) {
        return service.insertOrdine(body);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatoOrdine(@PathVariable int id, @RequestParam Stato stato) {
        service.updateStato(id, stato);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrdine(@PathVariable int id) {
        service.deleteOrdine(id);
    }

}
