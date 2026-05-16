package br.edu.ifsudestemg.fl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gols")
public class Gol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_partida")
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "id_jogador_gol")
    private Jogador jogadorGol;

    @ManyToOne
    @JoinColumn(name = "id_jogador_assistencia")
    private Jogador jogadorAssistencia;

}