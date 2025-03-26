package dev.cydome.awesomepizza.controllers.dto;

import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;

public record OrdineDto(
        Integer id,
        LocalDateTime dataOraInserimento,
        Stato stato,
        @Nonnull PizzaDto pizza
) {
}
