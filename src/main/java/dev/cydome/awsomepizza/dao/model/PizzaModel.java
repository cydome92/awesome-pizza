package dev.cydome.awsomepizza.dao.model;

import dev.cydome.awsomepizza.controllers.dto.PizzaDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pizze")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PizzaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    @ManyToMany
    @JoinTable(
            name = "pizze_ingredienti",
            joinColumns = {@JoinColumn(name = "id_pizza")},
            inverseJoinColumns = {@JoinColumn(name = "id_ingrediente")}
    )
    private List<IngredienteModel> ingredienti;

    public PizzaDto toDto() {
        return new PizzaDto(
                id,
                nome,
                ingredienti.stream()
                        .map(IngredienteModel::toDto)
                        .toList()

        );
    }

}
