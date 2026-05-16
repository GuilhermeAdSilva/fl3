package br.edu.ifsudestemg.fl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resultados")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_mandante")
    private Equipe equipeMandante;

    @Column(name = "gols_mandante")
    private Integer golsMandante;

    @OneToOne
    @JoinColumn(name = "id_visitante")
    private Equipe equipeVisitante;

    @Column(name = "gols_visitante")
    private Integer golsVisitante;

    @Column(name = "prorrogacao")
    private Boolean prorrogacao;

    @Column(name = "penaltis")
    private Boolean penaltis;

    @Column(name = "penaltis_mandante")
    private Integer penaltisMandante;

    @Column(name = "penaltis_visitante")
    private Integer penaltisVisitante;
}