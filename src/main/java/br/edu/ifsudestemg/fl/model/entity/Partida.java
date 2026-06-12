package br.edu.ifsudestemg.fl.model.entity;

import br.edu.ifsudestemg.fl.model.enums.StatusPartida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Torneio torneio;


    @Enumerated(EnumType.STRING)
    private StatusPartida status;
}
