package dev.cydome.awesomepizza.dao.model;

import dev.cydome.awesomepizza.controllers.dto.IngredienteDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
