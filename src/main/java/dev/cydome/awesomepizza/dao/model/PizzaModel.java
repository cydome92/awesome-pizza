package dev.cydome.awesomepizza.dao.model;

import dev.cydome.awesomepizza.controllers.dto.PizzaDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
