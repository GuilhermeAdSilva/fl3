package br.edu.ifsudestemg.fl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}