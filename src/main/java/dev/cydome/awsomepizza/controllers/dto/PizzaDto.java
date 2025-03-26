package dev.cydome.awsomepizza.controllers.dto;

import java.util.List;

public record PizzaDto(
        Integer id, //per la creazione
        String nome,
        List<IngredienteDto> ingredienti
) {
}
