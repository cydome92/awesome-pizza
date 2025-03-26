package dev.cydome.awesomepizza.controllers;

import dev.cydome.awesomepizza.controllers.dto.PizzaDto;
import dev.cydome.awesomepizza.service.PizzaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/pizze")
public class PizzaController {

    private final PizzaService service;

    public PizzaController(PizzaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<PizzaDto> getPizze(Pageable pageable) {
        return service.getPizze(pageable);
    }

}
