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

    @OneToOne
    private Equipe equipeMandante;

    private Integer golsMandante;

    @OneToOne
    private Equipe equipeVisitante;

    private Integer golsVisitante;

    private Boolean prorrogacao;

    private Boolean penaltis;

    private Integer penaltisMandante;

    private Integer penaltisVisitante;


    @Enumerated(EnumType.STRING)
    private StatusPartida status;
}
