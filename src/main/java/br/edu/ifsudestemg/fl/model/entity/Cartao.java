package br.edu.ifsudestemg.fl.model.entity;

import br.edu.ifsudestemg.fl.model.enums.CorCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CorCartao cor;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Jogador jogador;

}