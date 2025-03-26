package dev.cydome.awsomepizza.dao.model;

import dev.cydome.awsomepizza.controllers.dto.OrdineDto;
import dev.cydome.awsomepizza.dao.model.enumerator.Stato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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
