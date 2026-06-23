package br.edu.ifsudestemg.fl.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Jogador jogadorGol;

    @ManyToOne
    private Jogador jogadorAssistencia;

    @Transient
    private String nomeTorneio;

}