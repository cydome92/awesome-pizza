package dev.cydome.awsomepizza.dao.model;

import dev.cydome.awsomepizza.controllers.dto.IngredienteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ingredienti")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IngredienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String descrizione;

    public IngredienteDto toDto() {
        return new IngredienteDto(id, descrizione);
    }

}
