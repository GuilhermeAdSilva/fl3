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
    @Column(name = "id")
    private Long id;

    @Column(name = "cor")
    private CorCartao cor;

    @ManyToOne
    @JoinColumn(name = "id_partida")
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "id_jogador")
    private Jogador jogador;

}