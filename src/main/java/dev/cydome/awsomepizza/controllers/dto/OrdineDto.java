package dev.cydome.awsomepizza.controllers.dto;

import dev.cydome.awsomepizza.dao.model.enumerator.Stato;
import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;

public record OrdineDto(
        Integer id,
        LocalDateTime dataOraInserimento,
        Stato stato,
        @Nonnull PizzaDto pizza
) {
}
