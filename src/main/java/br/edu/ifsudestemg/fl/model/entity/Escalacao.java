package br.edu.ifsudestemg.fl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "escalacoes")
public class Escalacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private Jogador jogador;

}