package dev.cydome.awesomepizza.dao.model;

import dev.cydome.awesomepizza.controllers.dto.OrdineDto;
import dev.cydome.awesomepizza.dao.model.enumerator.Stato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordini")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @CreatedDate
    private LocalDateTime dataOraInserimento;
    @LastModifiedDate
    private LocalDateTime dataOraCompletamento;
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @ManyToOne
    @JoinColumn(name = "id_pizza")
    private PizzaModel pizza;

    public OrdineDto toDto() {
        return new OrdineDto(
                id,
                dataOraInserimento,
                stato,
                pizza.toDto()
        );
    }

}
